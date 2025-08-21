package com.pscs.moneyx.services;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pscs.moneyx.helper.FileUploadProperties;

import jakarta.annotation.PostConstruct;

@Service
public class FileSystemStorageService {
	private final Path dirLocation;

	public FileSystemStorageService(FileUploadProperties fileUploadProperties) {
		this.dirLocation = Paths.get(fileUploadProperties.getLocation()).toAbsolutePath().normalize();
	}

	@PostConstruct
	public void init() {

		try {
			Files.createDirectories(this.dirLocation);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String saveFile(MultipartFile file) {

		String fileName = null;
		try {
			fileName = file.getOriginalFilename();
			Path dfile = this.dirLocation.resolve(fileName);
			Files.copy(file.getInputStream(), dfile, StandardCopyOption.REPLACE_EXISTING);
			return fileName;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public Resource loadFile(String fileName) {

		Resource resource = null;
		try {
			Path file = this.dirLocation.resolve(fileName).normalize();
			resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return resource;
	}
}
