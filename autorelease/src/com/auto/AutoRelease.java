package com.auto;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utils.AppendFile;
import com.utils.DeCompressUtil;
import com.utils.Dom4JforXML;
import com.utils.TomcatStart;

@Controller
public class AutoRelease {
	@Autowired
	DeCompressUtil deCompressUtil;
	
	@Autowired
	AppendFile appendFile;
	
	@Autowired
	Dom4JforXML dom4JforXML;
	
	@Autowired
	TomcatStart tomcatStart;
	
	public String releaseDirectory = "";
	/**
	 * @param sourceFile 要解压的目录
	 * @param destDir 解压到的目录
	 * @param serverpath server.xml文件路径
	 * @param serverport server.xml端口
	 * @param fileStart 启动tomcat路径
	 * @param fileEnd 关闭tomcat路径
	 * */
	@RequestMapping(value = "/autoReleaseService.do")
	public void autoReleaseService(){
		String destDir = "",rootDirectory="";
		//1.解压tomcat
		try {
			String sourceFile = "D:/apache-tomcat-6.0.30-x64.rar";
			destDir = "D:/zipfile";
			deCompressUtil.deCompress(sourceFile, destDir);
			rootDirectory = deCompressUtil.getRootDirectory();
			rootDirectory = destDir+File.separator+ rootDirectory;
			releaseDirectory = rootDirectory;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//2.修改端口号
		try {
			String serverpath = rootDirectory + File.separator +"conf/server.xml";
			String serverport = "9999";
			dom4JforXML.updatePort(serverpath, serverport);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//3.修改配置文件启动
		String fileStart = rootDirectory + File.separator + "bin/startup.bat";
		String fileEnd = rootDirectory + File.separator + "bin/shutdown.bat";
		String text = appendFile.read(fileStart);
		appendFile.write(fileStart, false,"set CATALINA_HOME="+rootDirectory+"\r\n" + text);
		
		text = appendFile.read(fileEnd);
		appendFile.write(fileEnd, false,"set CATALINA_HOME="+rootDirectory+"\r\n" + text);
		
	}
	
	/**
	 * @param warDirectory war包位置
	 * */
	public void releaseFile(String warDirectory) throws IOException{
		AppendFile appendFile = new AppendFile();
		//String warDirectory = "D:/gzlyzy.war";
		appendFile.nioTransferCopy(warDirectory,this.releaseDirectory + "/webapps/");
	}
	
	/**
	 * 启动tomcat服务
	 * */
	public void startTomcat(String fileStart) throws IOException{
		tomcatStart.start("cmd /c "+fileStart);
	}
	
	/**
	 * 关闭tomcat服务
	 * */
	public void closeTomcat(String fileEnd) throws IOException{
		tomcatStart.start("cmd /c start "+fileEnd);
	}
	
	/**
	 * 打包源代码(不含svn信息)
	 * @param sourcePath 源代码路径
	 * @param destPath 压缩后生成目录位置
	 * */
	public void zipSource(String sourcePath,String destPath){
		//deCompressUtil.zip("F:/workspace/lnjjl/gzlyzy","D:/gzlyzy.zip");
		deCompressUtil.zip(sourcePath,destPath);
	}
}
