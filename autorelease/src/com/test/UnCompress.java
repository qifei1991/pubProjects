package com.test;

import org.junit.Test;

import com.utils.AppendFile;
import com.utils.DeCompressUtil;
import com.utils.TomcatStart;

public class UnCompress {
	
	public static void main(String[] args) throws Exception{
//		DeCompressUtil  de = new DeCompressUtil();
//		de.deCompress("D:/apache-tomcat-6.0.30-x64.zip", "D:/zipfile");
//		System.out.println(de.getRootDirectory());
//		String text = AppendFile.read("D:/zipfile/apache-tomcat-6.0.30/bin/startup.bat");
//		AppendFile.write("D:/zipfile/apache-tomcat-6.0.30/bin/startup.bat", false,"set CATALINA_HOME=D:/zipfile/apache-tomcat-6.0.30\r\n" + text);
//		TomcatStart.start("cmd /c start D:/zipfile/apache-tomcat-6.0.30/bin/startup.bat");
//		String text = AppendFile.read("D:/zipfile/apache-tomcat-6.0.30/bin/shutdown.bat");
//		AppendFile.write("D:/zipfile/apache-tomcat-6.0.30/bin/shutdown.bat", false,"set CATALINA_HOME=D:/zipfile/apache-tomcat-6.0.30\r\n" + text);
//		TomcatStart.close("cmd /c start D:/zipfile/apache-tomcat-6.0.30/bin/shutdown.bat");
	}
	@Test
	public void test1() throws Exception{
		DeCompressUtil  de = new DeCompressUtil();
		de.deCompress("D:/apache-tomcat-6.0.30-x64.rar", "D:/zipfile");
		System.out.println(de.getRootDirectory());
	}
	
	@Test
	public void test2() throws Exception{
		AppendFile appendFile = new AppendFile();
		TomcatStart tomcatStart = new TomcatStart();
		String text = appendFile.read("D:/zipfile/apache-tomcat-6.0.30/bin/startup.bat");
		appendFile.write("D:/zipfile/apache-tomcat-6.0.30/bin/startup.bat", false,"set CATALINA_HOME=D:/zipfile/apache-tomcat-6.0.30\r\n" + text);
		tomcatStart.start("cmd /c start D:/zipfile/apache-tomcat-6.0.30/bin/startup.bat");
	}
	
	@Test
	public void test3() throws Exception{
		AppendFile appendFile = new AppendFile();
		TomcatStart tomcatStart = new TomcatStart();
		String text = appendFile.read("D:/zipfile/apache-tomcat-6.0.30/bin/shutdown.bat");
		appendFile.write("D:/zipfile/apache-tomcat-6.0.30/bin/shutdown.bat", false,"set CATALINA_HOME=D:/zipfile/apache-tomcat-6.0.30\r\n" + text);
		tomcatStart.close("cmd /c start D:/zipfile/apache-tomcat-6.0.30/bin/shutdown.bat");
	}
	
	@Test
	public void test4() throws Exception{
		AppendFile appendFile = new AppendFile();
		appendFile.nioTransferCopy("D:/gzlyzy.war","D:/zipfile/apache-tomcat-6.0.30/webapps/");
	}
	
	@Test
	public void test5() throws Exception{
		DeCompressUtil  de = new DeCompressUtil();
		de.zip("F:/workspace/lnjjl/autorelease","D:/autorelease.zip");
	}

}
