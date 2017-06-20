package com.utils;

import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;  
import java.util.List;  
  
import org.dom4j.Attribute;  
import org.dom4j.Document;  
import org.dom4j.Element;  
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;  
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;

@Component
public class Dom4JforXML {  
      
    public void updatePort(String path,String port) throws Exception{  
        //创建SAXReader对象  
        SAXReader reader = new SAXReader();  
        //读取文件 转换成Document  
        Document document = reader.read(new File(path));  
        //获取根节点元素对象  
        Element root = document.getRootElement();  
        //遍历  
        listNodes(document,root,path,port);  
    }  
      
    //遍历当前节点下的所有节点  
    public void listNodes(Document document,Element node,String path,String port) throws IOException{  
        //System.out.println("当前节点的名称：" + node.getName());
        
        if(node.getName().equalsIgnoreCase("Connector")){
            //首先获取当前节点的所有属性节点  
            List<Attribute> list = node.attributes();
            for(Attribute attribute : list){
            	if(attribute.getName().equalsIgnoreCase("Port")){
            		attribute.setValue(port);
            		writeXml(document, path);
            		throw new RuntimeException();
            	}
                //System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  
            } 
        	return;
        }
        
        //遍历属性节点  
//        for(Attribute attribute : list){  
//            System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  
//        }  
        //如果当前节点内容不为空，则输出  
//        if(!(node.getTextTrim().equals(""))){  
//             System.out.println( node.getName() + "：" + node.getText());    
//        }  
        
        //同时迭代当前节点下面的所有子节点  
        //使用递归  
        Iterator<Element> iterator = node.elementIterator();  
        while(iterator.hasNext()){  
            Element e = iterator.next();  
            listNodes(document,e,path,port);  
        }  
    }  
    
    public void writeXml(Document document, String filePath) throws IOException {
        File xmlFile = new File(filePath);
        XMLWriter writer = null;
        try {
            if (xmlFile.exists())
                xmlFile.delete();
            writer = new XMLWriter(new FileOutputStream(xmlFile), OutputFormat.createPrettyPrint());
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}  