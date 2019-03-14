//package com.bridgelabz.fundoonotes.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.bridgelabz.fundoonotes.model.Files;
//import com.bridgelabz.fundoonotes.service.FilesService;
//
//@Controller
//@RequestMapping("/user/")
//public class FilesController {
//
//	@Autowired
//	private FilesService fileService;
//
//	@PostMapping("uploadFile")
//	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
//		Files files = fileService.storeFile(file);
//		if (files != null)
//			return new ResponseEntity<Void>(HttpStatus.OK);
//		return new ResponseEntity<String>("Email incorrect. Please enter valid email address present in database",
//				HttpStatus.NOT_FOUND);
//	}
//
//	@GetMapping("/downloadFile/{fileId}")
//	public ResponseEntity<Resource> downloadFile(@PathVariable int fileId) {
//		// Load file from database
//		Files files = fileService.getFile(fileId);
//
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(files.getType()))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + files.getName() + "\"")
//				.body(new ByteArrayResource(files.getPic()));
//	}
//
//}
