package com.searchbox.framework.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DirectoryService {
	
	private static Logger logger = LoggerFactory.getLogger(DirectoryService.class);

	@Autowired
	ApplicationContext context;

	File tempDir;
	
	DirectoryService(){
		//this.tempDir = new File
	}
	
	public String getApplicationRelativePath(String fname){
		Resource tempDir = context.getResource("WEB-INF/temp/");
		File file;
		try {
			file = new File(tempDir.getFile().getAbsolutePath()+"/"+fname);
			logger.debug("Application absolutePath: " + context.getResource("").getFile().getAbsolutePath() );
			String relative = context.getResource("").getFile().toURI().relativize(file.toURI()).getPath();
			return relative;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getApplicationRelativePath(File file){
		try {
			logger.debug("Application absolutePath: " + context.getResource("").getFile().getAbsolutePath() );
			String relative = context.getResource("").getFile().toURI().relativize(file.toURI()).getPath();
			return relative;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean fileExists(String fname){
		Resource tempDir = context.getResource("WEB-INF/temp/");
		File newFile;
		try {
			newFile = new File(tempDir.getFile().getAbsolutePath()+"/"+fname);
			return newFile.exists();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public File createFile(String fname, String content){
		File file = this.createFile(fname);
		try {
			FileUtils.writeStringToFile(file, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	public File createFile(String fname) {
		Resource tempDir = context.getResource("WEB-INF/temp/");
		if(!tempDir.exists()){
			try {
				tempDir.getFile().mkdirs();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			File newFile =  new File(tempDir.getFile().getAbsolutePath()+"/"+fname);
			newFile.deleteOnExit();
			return newFile;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}