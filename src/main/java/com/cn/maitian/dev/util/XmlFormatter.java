package com.cn.maitian.dev.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class XmlFormatter {
    private static final Logger logger = LoggerFactory.getLogger(XmlFormatter.class);
    public Document format(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);
//            OutputFormat format = new OutputFormat(document);
//            format.setLineWidth(65);
//            format.setIndenting(true);
//            format.setIndent(2);
//            Writer out = new StringWriter();
//            XMLSerializer serializer = new XMLSerializer(out, format);
//            serializer.serialize(document);
            return document;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Document parseWxXmlFile(String in) {
        try {
            InputSource is = new InputSource(new StringReader(in));
            //漏洞
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            String FEATURE = null;
            try {
                FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
                dbf.setFeature(FEATURE, true);
                FEATURE = "http://xml.org/sax/features/external-general-entities";
                dbf.setFeature(FEATURE, false);
                FEATURE = "http://xml.org/sax/features/external-parameter-entities";
                dbf.setFeature(FEATURE, false);
                FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
                dbf.setFeature(FEATURE, false);
                dbf.setXIncludeAware(false);
                dbf.setExpandEntityReferences(false);
            } catch (ParserConfigurationException e) {
                logger.info("ParserConfigurationException was thrown. The feature '" +
                        FEATURE + "' is probably not supported by your XML processor.");
            }
            catch (Exception e) {
                logger.error("IOException occurred, XXE may still possible: " + e.getMessage());
            }
            DocumentBuilder safebuilder = dbf.newDocumentBuilder();
            return safebuilder.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public  Map<String,String> wxParseXml(String xmlSource,String tageName){
        Map<String,String> map = new HashMap<>();
        try{
            Document  document = new XmlFormatter().parseWxXmlFile(xmlSource);

            NodeList list = document.getElementsByTagName(tageName);

            int  len = list.getLength();
            for (int i = 0; i < len; i++) {
                NodeList elementList = list.item(0).getChildNodes();
                for(int j = 0 ;j<elementList.getLength();j++){
                    String nodeName = elementList.item(j).getNodeName();
                    String nodeValue = elementList.item(j).getTextContent();
                    map.put(nodeName,nodeValue);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
    public static void main(String[] args) throws Exception{
        String s = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" +
                "<return_msg><![CDATA[OK]]></return_msg>" +
                "<appid><![CDATA[wx5ed6c80d5b3b29a6]]></appid>" +
                "<mch_id><![CDATA[1507960231]]></mch_id>" +
                "<nonce_str><![CDATA[ROMUv4acoZ0aACpU]]></nonce_str>" +
                "<sign><![CDATA[ECD2E1C56DC210EBFE8B017B32FD5FAF]]></sign>" +
                "<result_code><![CDATA[SUCCESS]]></result_code>" +
                "<prepay_id><![CDATA[wx2516360815460578f57a01382215282228]]></prepay_id>" +
                "<trade_type><![CDATA[APP]]></trade_type>" +
                "</xml>";
            try{
                s ="<PARAM><DBID>35</DBID><SEQUENCE>atgtca</SEQUENCE><MAXNS>10</MAXNS><MINIDENTITIES>90</MINIDENTITIES><MAXEVALUE>10</MAXEVALUE><USERNAME>admin</USERNAME><PASSWORD>111111</PASSWORD><TYPE>P</TYPE><RETURN_TYPE>2</RETURN_TYPE></PARAM>";
                Document  document = new XmlFormatter().parseWxXmlFile(s);

                NodeList list = document.getElementsByTagName("PARAM");

                int  len = list.getLength();
                for (int i = 0; i < len; i++) {
                    NodeList elementList = list.item(0).getChildNodes();
                    for(int j = 0 ;j<elementList.getLength();j++){
                        String nodeName = elementList.item(j).getNodeName();
                        String nodeValue = elementList.item(j).getTextContent();
                        System.out.println(""+nodeName+":"+nodeValue);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        
    }
}