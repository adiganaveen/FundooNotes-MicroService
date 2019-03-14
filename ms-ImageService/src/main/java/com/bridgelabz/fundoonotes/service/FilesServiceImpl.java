//package com.bridgelabz.fundoonotes.service;
//
////import com.example.filedemo.exception.FileStorageException;
////import com.example.filedemo.exception.MyFileNotFoundException;
////import com.example.filedemo.model.DBFile;
////import com.example.filedemo.repository.DBFileRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.bridgelabz.fundoonotes.dao.FileRepository;
//import com.bridgelabz.fundoonotes.model.Files;
//
//import java.io.IOException;
//
//@Service
//public class FilesServiceImpl {
//
//	@Autowired
//	private FileRepository fileRepository;
//
//	public Files storeFile(MultipartFile file) throws IOException {
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		Files files = new Files(fileName, file.getContentType(), file.getBytes(), 1);
//		return fileRepository.save(files);
//	}
//
//	public Files getFile(int fileId) {
//		return fileRepository.findById(fileId).get();
//	}
//}
