package com.pscs.moneyx.model;

import org.springframework.web.multipart.MultipartFile;

public class ImageUpload {

	String userId;
	String fileType;
	MultipartFile file;
	String filePath;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	} 
	
	
	
	
}
