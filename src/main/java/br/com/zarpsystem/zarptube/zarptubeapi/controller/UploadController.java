package br.com.zarpsystem.zarptube.zarptubeapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.zarpsystem.zarptube.zarptubeapi.service.VideoService;

@Controller
@RequestMapping("upload")
public class UploadController {
	private static final String INDEX = "redirect:index";
	
	@Autowired
	VideoService videoService;
	
	@GetMapping
	String tela(String nome) {
		return "upload";
	}
	
	@PostMapping
	String upload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
		videoService.upload(file);
		return INDEX;
	}
}
