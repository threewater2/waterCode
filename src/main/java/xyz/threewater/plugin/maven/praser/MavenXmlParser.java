package xyz.threewater.plugin.maven.praser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xyz.threewater.utils.FileUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
//针对Maven xml文件的解析器
@Component
class MavenXmlParser {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private DocumentBuilder documentBuilder;
    private XPath xPath;

    @Value("${maven.repo}")
    private String repo;

    private MavenXmlParser(){
        try {
            xPath=XPathFactory.newInstance().newXPath();
            documentBuilder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String xpathExp="/project/dependencies/dependency";

    public TreeNode<String> parse(TreeNode<String> root,InputStream inputStream,String xpathExp) throws XmlParseException {
        try{
            Document document = documentBuilder.parse(inputStream);
            NodeList nodeList=(NodeList)xPath.compile(xpathExp).evaluate(document, XPathConstants.NODESET);
            MavenEnv mavenEnv=new MavenEnv(document,xPath);
            for(int i=0;i<nodeList.getLength();i++){
                Node node=nodeList.item(i);
                TreeNode<String> child=resolveNode(node,mavenEnv);
                if(child!=null) root.addChild(child);
            }
            return root;
        } catch (IOException e) {
            throw new XmlParseException("文件不存在，或者拒绝访问");
        } catch (XPathExpressionException e) {
            throw new XmlParseException("xpath语法错误");
        } catch (SAXException e) {
            throw new XmlParseException("sax异常");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public TreeNode<String> parse(TreeNode<String> root,String path,String xpathExp) throws XmlParseException{
        try {
            InputStream inputStream = new FileInputStream(path);
            return parse(root,inputStream,xpathExp);
        } catch (FileNotFoundException e) {
            throw new XmlParseException(path+"文件不存在");
        }
    }

    public TreeNode<String> parse(TreeNode<String> root,String path) throws XmlParseException{
        return parse(root,path,xpathExp);
    }

    /**
     * 解析每一个依赖
     * @param node dependency节点
     */
    private TreeNode<String> resolveNode(Node node,MavenEnv mavenEnv) throws XmlParseException {
        if(!node.hasChildNodes()) return new TreeNode<>("unknown");
        NodeList childNodes = node.getChildNodes();
        Map<String,String> valueMap=new HashMap<>();
        forEachNodeList(childNodes, valueMap);
        if(!isNeedParse(valueMap)) return null;
        String dependencyStr=getDependencyStr(mavenEnv,valueMap);
        TreeNode<String> parent= new TreeNode<>(dependencyStr);
        String filePath=getPathFromNodeList(mavenEnv,valueMap);
        if(!isNeedDeeper(filePath,valueMap)) return parent;
        deeperResolve(parent,filePath);
        return parent;
    }

    static void forEachNodeList(NodeList childNodes, Map<String, String> valueMap) {
        for(int i=0;i<childNodes.getLength();i++){
            Node childNode=childNodes.item(i);
            if(childNode.getNodeType()!=Node.ELEMENT_NODE)
                continue;
            String nodeName = childNode.getNodeName();
            String nodeText = childNode.getTextContent();
            valueMap.put(nodeName,nodeText);
        }
    }

    /**
     *  存在classifier标签不解析
     */
    private boolean isNeedParse(Map<String,String> valueMap){
        return valueMap.get(DependencyKeyWord.CLASSIFIER.toString()) == null;
    }

    /**
     *  目录不存在或者version标签没有的不继续深入
     */
    private boolean isNeedDeeper(String filePath,Map<String,String> valueMap){
        return FileUtil.exist(filePath) &&
               valueMap.get(DependencyKeyWord.VERSION.toString()) != null;
    }

    private String getDependencyStr(MavenEnv mavenEnv,Map<String,String> valueMap){
        String groupId=valueMap.get(DependencyKeyWord.GROUP_ID.toString());
        String artifactId=valueMap.get(DependencyKeyWord.ARTIFACT_ID.toString());
        String version=valueMap.get(DependencyKeyWord.VERSION.toString());
        version=mavenEnv.getVersion(version);
        return String.join(":",groupId,artifactId,version);
    }

    private void deeperResolve(TreeNode<String> parent,String pomPath) throws XmlParseException {
        parse(parent,pomPath,xpathExp);
    }

    private String getPathFromNodeList(MavenEnv mavenEnv,Map<String,String> valueMap){
        String path= getPath(mavenEnv,valueMap)+getPomName(mavenEnv,valueMap);
        logger.debug("pom xml path:{}",path);
        return path;
    }

    private String getPath(MavenEnv mavenEnv,Map<String,String> valueMap){
        String groupId=valueMap.get(DependencyKeyWord.GROUP_ID.toString());
        groupId=groupId.replace(".","/");
        String artifactId=valueMap.get(DependencyKeyWord.ARTIFACT_ID.toString());
        artifactId=artifactId.replace(".","/");
        String version=valueMap.get(DependencyKeyWord.VERSION.toString());
        version=mavenEnv.getVersion(version);
        return String.join("/",repo,groupId,artifactId,version)+"/";
    }

    private String getPomName(MavenEnv mavenEnv,Map<String,String> valueMap){
        String artifactId=valueMap.get(DependencyKeyWord.ARTIFACT_ID.toString());
        String version=valueMap.get(DependencyKeyWord.VERSION.toString());
        version=mavenEnv.getVersion(version);

        return String.join("-",artifactId,version)+".pom";
    }
}
