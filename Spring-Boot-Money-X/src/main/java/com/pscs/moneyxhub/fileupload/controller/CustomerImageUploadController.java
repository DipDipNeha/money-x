/**
 * 
 */
package com.pscs.moneyxhub.fileupload.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pscs.moneyxhub.model.ImageUpload;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.services.CustomerBusinessService;
import com.pscs.moneyxhub.services.FileSystemStorageService;

/**
 * 
 */
@RestController
@CrossOrigin
@RequestMapping("/api/customer/")
public class CustomerImageUploadController {

	FileSystemStorageService fileSytemStorage;
	CustomerBusinessService customerProfileService;

	public CustomerImageUploadController(FileSystemStorageService fileSytemStorage,
			CustomerBusinessService customerProfileService) {

		this.fileSytemStorage = fileSytemStorage;
		this.customerProfileService = customerProfileService;
	}

	@PostMapping("/uploadImage")
	public ResponseEntity<ResponseData> uploadImage(@RequestParam("file") MultipartFile file,
			@RequestParam String fileType, @RequestParam String userId) {

		ResponseData apiResponse = new ResponseData();

		try {

			apiResponse.setResponseCode("99");
			apiResponse.setResponseMessage("Unable to process request");

			   if (file.isEmpty()) {
				   apiResponse.setResponseCode( "01");
				   apiResponse.setResponseMessage("File is empty");
	                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	            }
			
			
			String upfile = fileSytemStorage.saveFile(file);

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/download/")
					.path(upfile).toUriString();
			

			System.out.println("fileDownloadUri->" + fileDownloadUri);

			ImageUpload imageUpload = new ImageUpload();
			imageUpload.setFile(file);
			imageUpload.setFileType(fileType);
			imageUpload.setUserId(userId);
			imageUpload.setFilePath(fileDownloadUri);
			
			apiResponse = customerProfileService.uploadImage(imageUpload);
			

		} catch (Exception e) {
			e.printStackTrace();

			apiResponse.setResponseCode("01");
			apiResponse.setResponseMessage("Internal Error Occurred!" + e.getMessage());

		} finally {

		}

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

}
