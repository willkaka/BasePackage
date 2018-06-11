package com.base.readfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import org.dom4j.io.SAXReader;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.*;   

public class ReadXMLFile {
    
    public static String getXmlFileInfo(String xmlFilePath, String itemAttribute){
        try{
            List allChildren = getXmlChildren(xmlFilePath);
            
            for (int i = 0; i < allChildren.size(); i++) {
                Element item = (Element)allChildren.get(i);
                System.out.println("itemName:"+item.getName());
                return item.getAttribute(itemAttribute).getValue();
            }            
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /*
     * 
    @Parameters: 
    */
    public static String getXmlFileInfo(String xmlFilePath, String itemName,String itemAttribute, String itemAttributeValue, String itemChildName){
        try{            
            List allChildren = getXmlChildren(xmlFilePath);
            
            for (int i = 0; i < allChildren.size(); i++) {
                Element item = (Element)allChildren.get(i);
                //System.out.println("item text:" + item.getText());
                if (item.getName().equals(itemName)) {
                    if (item.getAttribute(itemAttribute).getValue().equals(itemAttributeValue)){
                        return item.getChild(itemChildName).getText();
                    }
                }                
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getXmlFileInfo2(String xmlFilePath, String itemName,String itemAttribute1, String itemAttributeValue1,String itemAttribute2, String itemAttributeValue2, String itemChildName){
        try{
            List allChildren = getXmlChildren(xmlFilePath);
            for (int i = 0; i < allChildren.size(); i++) {
                Element item = (Element)allChildren.get(i);
                //System.out.println("item text:" + item.getText());
                if (item.getName().equals(itemName)) {
                    if (item.getAttribute(itemAttribute1).getValue().equals(itemAttributeValue1) &&
                            item.getAttribute(itemAttribute2).getValue().equals(itemAttributeValue2)){
                        return item.getChild(itemChildName).getText();
                    }
                }                
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static List getXmlChildren(String xmlFilePath){
        try{
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new File(xmlFilePath));
            Element foo = doc.getRootElement();
            List allChildren = foo.getChildren();
            return allChildren;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static org.dom4j.Document getXmlDocument(String xmlFilePath){
        SAXReader reader = new SAXReader(); 
        org.dom4j.Document document = null; 
        try { 
            //InputStream in = xmlTest01.class.getResourceAsStream(xmlFilePath); 
            File file = new File(xmlFilePath);
            if (xmlFilePath.isEmpty() || xmlFilePath == null || !file.exists()) return null;
            InputStream in = new FileInputStream(file);                        
            document = reader.read(in);             
        } catch (Exception e) { 
            System.out.println(e.getMessage()); 
            System.out.println("读取classpath下xmlFileName文件发生异常，请检查CLASSPATH和文件名是否存在！"); 
            e.printStackTrace(); 
        } 
        return document;
    }
    
    public static ArrayList<String> getXmlItemList(String xmlFilePath, String attr) {
        ArrayList<String> aList = new ArrayList<String>();
        org.dom4j.Element root = getXmlDocument(xmlFilePath).getRootElement(); // 根结点
        for (Iterator Node = root.elementIterator(); Node.hasNext();) {
            org.dom4j.Element subNode = (org.dom4j.Element) Node.next();
            aList.add(subNode.attributeValue(attr));
        }
        return aList;
    }
    
    public static ArrayList<String> getXmlItemAttrList(String xmlFilePath, String tableName, String attribute) {
        ArrayList<String> aList = new ArrayList<String>();
               
        org.dom4j.Element root = getXmlDocument(xmlFilePath).getRootElement(); // 根结点

        for (Iterator Node = root.elementIterator(); Node.hasNext();) {
            org.dom4j.Element subNode = (org.dom4j.Element) Node.next();
            //System.out.println("attr2:"+subNode.attributeValue("TableName"));
            if (subNode.attributeValue("TableName").equals(tableName) && subNode.attributeValue("TableName") != null) {
                //System.out.println("attr3:"+subNode.attributeValue("TableName"));
                for (Iterator thirdNode = subNode.elementIterator(); thirdNode.hasNext();) {
                    org.dom4j.Element subNode2 = (org.dom4j.Element) thirdNode.next();
                    //System.out.println("field:"+subNode2.getText());
                    aList.add(subNode2.attributeValue(attribute));
                }
                //break;
            }
        }
        return aList;
    }
    
    public static ArrayList<String> getXmlAllItemList(String xmlFilePath) {
        ArrayList<String> aList = new ArrayList<String>();

        org.dom4j.Element root = getXmlDocument(xmlFilePath).getRootElement(); // 根结点
        System.out.println("item:"+root.getName());
        
        for (Iterator Node = root.elementIterator(); Node.hasNext();) {
            org.dom4j.Element element = (org.dom4j.Element) Node.next();  //取下一层
            System.out.println("item:"+ element.getName());
            //element.get
            Node = element.elementIterator();
        }
        
        return aList;
    }
    
    public static void main(String arge[]) {
        ArrayList<String> fieldList = new ArrayList<String>();
        
        fieldList = getXmlItemList("./config/DataBase.xml","Env");
        
        for(String string : fieldList){ System.out.println("item:"+string);}
    }
}
