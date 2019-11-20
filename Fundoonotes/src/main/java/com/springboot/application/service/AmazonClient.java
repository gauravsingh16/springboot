//package com.springboot.application.service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Date;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.pinpoint.model.CampaignStatus;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//@Service
//public class AmazonClient {
//
//	private AmazonS3 s3client;
////	@Value("${amazonProperties.endPointUrl}")
//	private String endpointUrl;
////	@Value("${amazonProperties.bucketName}")
//	private String bucketName;
////	@Value("${amazonProperties.accessKey}")
//	private String accessKey;
////	@Value("${amazonProperties.secretKey}")
//	private String secretKey;
//	
//	@PostConstruct
//	private void initializeAmazon() {
//		AWSCredentials credentials = new  BasicAWSCredentials(this.accessKey, this.secretKey);
//		this.s3client = new AmazonS3Client(credentials);
//	}
//	
//	private File convertMultiPartToFile(MultipartFile file) throws IOException {
//		File convFile= new File(file.getOriginalFilename());
//		FileOutputStream fos= new FileOutputStream(convFile);
//		fos.write(file.getBytes());
//		fos.close();
//		return convFile;
//	}
//	private String generateFileName(MultipartFile multiPart) {
//	    return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
//	}
//	private void uploadFileToBucket(String Filename, File file) {
//		s3client.putObject(new PutObjectRequest(bucketName,Filename, file)
//				.withCannedAcl(CannedAccessControlList.PublicRead));
//	}
//	public String uploadFile(MultipartFile multipartfile)
//	{
//		String fileUrl="";
//		try {
//			File file= convertMultiPartToFile(multipartfile);
//			String filename= generateFileName(multipartfile);
//			fileUrl= endpointUrl+"/"+bucketName+"/"+filename;
//			uploadFileToBucket(filename, file);
//			file.delete();
//		}catch(Exception e)
//		{
//			e.getStackTrace();
//		}
//		return fileUrl;
//	}
//	
//	public String deleteFileFromS3Bucket(String fileUrl) {
//		String  filename= fileUrl.substring(fileUrl.lastIndexOf("/")+1);
//		s3client.deleteObject(new DeleteObjectRequest(bucketName + "/",filename));
//		return "successfully deleted";
//	}
//	
//}
