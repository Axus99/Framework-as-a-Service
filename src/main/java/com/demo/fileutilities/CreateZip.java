package com.demo.fileutilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZip {

	 List<String> filesListInDir = new ArrayList<String>();

	 public void createZip(String email,String project,String path) 
	 {
		 File dir = new File(path+"/projects/"+email+"/"+project);
		 String zipDirName = path+"/sourcecode/"+email+"/"+project+".zip";
		 
		 CreateZip zp = new CreateZip();
		 zp.zipDirectory(dir, zipDirName);
	 }
	
	private void zipDirectory(File dir, String zipDirName) 
	{
		try 
		{
			populateFilesList(dir);
            //now zip files one by one
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipDirName);
            
            ZipOutputStream zos = new ZipOutputStream(fos);
            
            for(String filePath : filesListInDir)
            {
                System.out.println("Zipping "+filePath);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                zos.putNextEntry(ze);
                
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                
                byte[] buffer = new byte[1024];
                
                int len;
                
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        }
		catch (IOException e) 
		{
            e.printStackTrace();
        }
    }
	
	private void populateFilesList(File dir) throws IOException 
	{
        File[] files = dir.listFiles();
        for(File file : files)
        {
            if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else populateFilesList(file);
        }
    }
}