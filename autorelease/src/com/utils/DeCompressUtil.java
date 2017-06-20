package com.utils;  
  
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
import org.apache.tools.ant.Project;  
import org.apache.tools.ant.taskdefs.Expand;  
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.stereotype.Component;
  
import de.innosystec.unrar.Archive;  
import de.innosystec.unrar.rarfile.FileHeader;  
  
/**  
 * ZIP文件解压(要使用apache ant.jar以处理中文乱码)    
 * rar文件解压(要使用java-unrar-0.3.jar)    
 * @author 小谢  
 */  
@Component
public class DeCompressUtil {
	
	private String  rootDirectory;

	public String getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**  
     * 解压缩文件  
     * @param sourceFile 压缩文件绝对路径  
     * @param destDir 解压目录，解压后的文件将会放入此目录中，请使用绝对路径  
     * 只支持zip和rar格式的压缩包！   
     * @throws Exception  
     */  
    public void deCompress(String sourceFile, String destDir)  
            throws Exception {  
        // 保证文件夹路径最后是"/"或者"\"  
        char lastChar = destDir.charAt(destDir.length() - 1);  
        if (lastChar != '/' && lastChar != '\\') {  
            destDir += File.separator;  
        }  
        // 根据类型，进行相应的解压缩  
        String type = sourceFile.substring(sourceFile.lastIndexOf(".") + 1);  
        if (type.toLowerCase().equals("zip")) {  
            this.unzip(sourceFile, destDir);  
        } else if (type.toLowerCase().equals("rar")) {  
            this.unrar(sourceFile, destDir);  
        } else {  
            throw new Exception("只支持zip和rar格式的压缩包！");  
        }  
    }  
      
    /**  
     * 解压zip格式压缩包   
     * 对应的是ant.jar  
     */  
    private void unzip(String sourceZip, String destDir)  
            throws Exception {  
//        try {  
//            Project p = new Project();  
//            Expand e = new Expand();  
//            e.setProject(p);  
//            e.setSrc(new File(sourceZip));  
//            e.setOverwrite(false);  
//            e.setDest(new File(destDir));  
//            /*  
//             * ant下的zip工具默认压缩编码为UTF-8编码， 而winRAR软件压缩是用的windows默认的GBK或者GB2312编码  
//             * 所以解压缩时要制定编码格式  
//             */  
//            e.setEncoding("gbk");  
//            e.execute();  
//        } catch (Exception e) {  
//            throw e;  
//        }  
    	ZipFile zipFile = new ZipFile(sourceZip, "GBK"); // 以“GBK”编码创建zip文件，用来处理winRAR压缩的文件。
        Enumeration emu = zipFile.getEntries();
        if (emu.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) emu.nextElement();
            if (entry.isDirectory()) {
            	this.setRootDirectory(entry.getName().replace("/", ""));
            }
        }
    }  
  
    /**  
     * 解压rar格式压缩包。  
     * 对应的是java-unrar-0.3.jar  
     */  
    private void unrar(String sourceRar, String destDir)  
            throws Exception {  
        Archive a = null;  
        FileOutputStream fos = null;  
        try {  
            a = new Archive(new File(sourceRar));  
            FileHeader fh = a.nextFileHeader();  
            while (fh != null) {  
                // 如果解压fh是目录,直接生成目录即可,不用写入  
                if (!fh.isDirectory()) {  
                    // 处理中文乱码  
                    String fileName = fh.getFileNameW().trim();  
                    if(!existZH(fileName)){  
                        fileName = fh.getFileNameString().trim();  
                    }  
                    // 解压文件路径  
                    String destFileName = destDir + fileName;
                    String rootDirectory = fileName.substring(0, fileName.lastIndexOf(File.separator));
                    if(rootDirectory.indexOf("\\")<0){
                    	this.setRootDirectory(rootDirectory);
                    }
                    // 解压目录路径  
                    String destDirName = destFileName.substring(0, destFileName.lastIndexOf(File.separator));
                    
                    // 2创建文件夹  
                    File dir = new File(destDirName);  
                    if (!dir.exists() || !dir.isDirectory()) {  
                        dir.mkdirs();  
                    }  
                    // 3解压缩文件  
                    fos = new FileOutputStream(new File(destFileName));  
                    a.extractFile(fh, fos);  
                    fos.close();  
                    fos = null;  
                }  
                fh = a.nextFileHeader();  
                continue;  
            }  
            a.close();  
            a = null;  
        } catch (Exception e) {  
            throw e;  
        } finally {  
            if (fos != null) {  
                try {  
                    fos.close();  
                    fos = null;  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            if (a != null) {  
                try {  
                    a.close();  
                    a = null;  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
      
    /**  
     * 判断是否为中文  
     * @param str  
     * @return  
     */  
    private boolean existZH(String str) {  
        String regEx = "[\\u4e00-\\u9fa5]";  
        Pattern p = Pattern.compile(regEx);  
        Matcher m = p.matcher(str);  
        while (m.find()) {  
            return true;  
        }  
        return false;  
    } 
    
    
    /**  
     *  
     * @param file 要压缩的文件  
     * @param zipFile 压缩文件存放地方  
     */  
    public void zip(String sourcePath,String destPath) {
    	File file = new File(sourcePath);
    	File zipFile = new File(destPath);
        ZipOutputStream outputStream = null;  
        try {  
            outputStream = new ZipOutputStream(new FileOutputStream(zipFile));  
            zipFile(outputStream, file, "");  
            if (outputStream != null) {  
                outputStream.flush();  
                outputStream.close();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();
        } finally {  
            try {  
                outputStream.close();  
            } catch (IOException e) {  
            	e.printStackTrace();
            }  
        }  
    }  
  
    /**  
     *  
     * @param output ZipOutputStream对象  
     * @param file 要压缩的文件或文件夹  
     * @param basePath 条目根目录  
     */  
    private  void zipFile(ZipOutputStream output, File file, String basePath) {  
        FileInputStream input = null;  
        try {  
            // 文件为目录  
            if (file.isDirectory()) {  
                // 得到当前目录里面的文件列表  
                File list[] = file.listFiles();  
                basePath = basePath + (basePath.length() == 0 ? "" : "/")  
                        + file.getName();
                if(basePath.indexOf("svn")>-1){
                	return;
                }
                // 循环递归压缩每个文件  
                for (File f : list) {  
                    zipFile(output, f, basePath);  
                }  
            } else {  
                // 压缩文件  
                basePath = (basePath.length() == 0 ? "" : basePath + "/")  
                        + file.getName();
                if(basePath.indexOf("svn")>-1){
                	return;
                }
                // System.out.println(basePath);  
                output.putNextEntry(new ZipEntry(basePath));  
                input = new FileInputStream(file);  
                int readLen = 0;  
                byte[] buffer = new byte[1024 * 8];  
                while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {  
                    output.write(buffer, 0, readLen);  
                }  
            }  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        } finally {  
            // 关闭流  
            if (input != null) {  
                try {  
                    input.close();  
                } catch (IOException e) {  
                	e.printStackTrace();
                }  
            }  
        }  
    }
} 