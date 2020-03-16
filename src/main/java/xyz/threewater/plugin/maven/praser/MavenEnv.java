package xyz.threewater.plugin.maven.praser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;

class MavenEnv {
    private Map<String,String> properties=new HashMap<>();

    private static final String exp="/project/properties";

    public MavenEnv(Document document, XPath xPath){
        try {
            NodeList nodeList = (NodeList) xPath.evaluate(exp, document, XPathConstants.NODESET);
            setProperties(nodeList);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private void setProperties(NodeList nodeList){
        if(nodeList.getLength()<=0) return;
        nodeList=nodeList.item(0).getChildNodes();
        MavenXmlParser.forEachNodeList(nodeList, properties);
    }

    public String getVersion(String exp){
        if(exp==null) return "unknown";
        exp=exp.replace("${","").replace("}","");
        String value=properties.get(exp);
        if(value==null) return exp;
        return value;
    }
}
