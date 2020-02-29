package xyz.threewater.plugin.maven.praser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

//针对Maven xml文件的解析器
public class MavenXmlParser {

    private static String xpathExp="/project/dependencies/dependency";

    private static NodeList resolve(InputStream inputStream,String xpathExp) throws XmlParseException {
        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            XPath xPath = XPathFactory.newInstance().newXPath();
            return (NodeList)xPath.compile(xpathExp).evaluate(document, XPathConstants.NODESET);
        } catch (ParserConfigurationException e) {
            throw new XmlParseException("xpath解析器配置错误");
        } catch (IOException e) {
            throw new XmlParseException("文件不存在，或者拒绝访问");
        } catch (XPathExpressionException e) {
            throw new XmlParseException("xpath语法错误");
        } catch (SAXException e) {
            throw new XmlParseException("sax异常");
        }
    }

    public static TreeNode<String> parse(InputStream inputStream,String xpathExp) throws XmlParseException {
        NodeList nodeList = resolve(inputStream, xpathExp);
        TreeNode<String> root=new TreeNode<>("dependencies");
        for(int i=0;i<nodeList.getLength();i++){
            Node node=nodeList.item(i);
            TreeNode<String> child=resolveNode(node);
            root.addChild(child);
        }
        return root;
    }

    public static TreeNode<String> parse(String path,String xpathExp) throws XmlParseException{
        try {
            InputStream inputStream = new FileInputStream(path);
            return parse(inputStream,xpathExp);
        } catch (FileNotFoundException e) {
            throw new XmlParseException("文件不存在");
        }
    }

    public static TreeNode<String> parse(String path) throws XmlParseException{
        return parse(path,xpathExp);
    }

    /**
     * 解析每一个依赖
     * @param node dependency节点
     */
    private static TreeNode<String> resolveNode(Node node) {
        if(!node.hasChildNodes()) return new TreeNode<>("unknown");
        NodeList childNodes = node.getChildNodes();
        List<String> nodeList=new LinkedList<>();
        for(int i=0;i<childNodes.getLength();i++){
            Node childNode=childNodes.item(i);
            if(childNode.getNodeType()!=Node.ELEMENT_NODE)
                continue;
            nodeList.add(childNode.getTextContent());
        }
        String dependencyStr=String.join(":",nodeList);
        return new TreeNode<>(dependencyStr);
    }

    public static String getXpathExp() {
        return xpathExp;
    }

    public static void setXpathExp(String xpathExp) {
        MavenXmlParser.xpathExp = xpathExp;
    }
}
