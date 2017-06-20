package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;  
import java.io.IOException;  
import java.nio.channels.FileChannel;

import org.springframework.stereotype.Component;
  
/** 
 * 
 * @author malik 
 * @version 2011-3-10 下午10:49:41 
 */  
@Component
public class AppendFile {  
      
	 // 读取指定路径文本文件  
    public String read(String filePath) {  
        StringBuilder str = new StringBuilder();  
        BufferedReader in = null;  
        try {  
            in = new BufferedReader(new FileReader(filePath));  
            String s;  
            try {  
                while ((s = in.readLine()) != null)  
                    str.append(s + '\n');  
            } finally {  
                in.close();  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return str.toString();  
    }  
  
    // 写入指定的文本文件，append为true表示追加，false表示重头开始写，  
    //text是要写入的文本字符串，text为null时直接返回  
    public void write(String filePath, boolean append, String text) {  
        if (text == null)  
            return;  
        try {  
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath,  
                    append));  
            try {  
                out.write(text);  
            } finally {  
                out.close();  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    
    public void nioTransferCopy(String sourcePath, String targetPath) throws IOException {
    	File source = new File(sourcePath);
    	File target = new File(targetPath+source.getName());
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {  
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            inStream.close();  
            in.close();  
            outStream.close();  
            out.close();  
        }  
    }
  
    public static void main(String[] args) {  
        
//        AppendFile.write("d://text.txt", false,  
//                "ygl");  
       
//        String test = AppendFile.read("d://text.txt");
//        AppendFile.write("d://text.txt", false,"tt\r\n" + test);
    }  
} 