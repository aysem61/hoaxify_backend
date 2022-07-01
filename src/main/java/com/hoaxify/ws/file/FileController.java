package com.hoaxify.ws.file;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
	
	private final FileService fileService;
	
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/api/1.0/hoax-attachments")
	public FileAttachment saveHoaxAttachment(MultipartFile file) {
		return fileService.saveHoaxAttachment(file);
	}
}
