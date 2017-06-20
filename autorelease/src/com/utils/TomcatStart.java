package com.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class TomcatStart {
	// 测试方法
	public void main(String[] args) throws Exception {
		//TomcatStart.start();
	}

	public void start(String s) throws IOException {
		Runtime r=Runtime.getRuntime();
		Process p=null;
		try{
		//String s="cmd /c start D:/zipfile/apache-tomcat-6.0.30/bin/startup.bat";
		p=r.exec(s); 
		r.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
		}catch(Exception e){ 
		System.out.println("错误:"+e.getMessage()); 
		e.printStackTrace(); 
		}
	}
	
	public void close(String s) throws IOException {
		Runtime r=Runtime.getRuntime();
		Process p=null;
		try{
		//String s="cmd /c start D:/zipfile/apache-tomcat-6.0.30/bin/startup.bat";
		p=r.exec(s); 
		r.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
		}catch(Exception e){ 
		System.out.println("错误:"+e.getMessage()); 
		e.printStackTrace(); 
		}
	}
}