package br.com.zarpsystem.zarptube.zarptubeapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.zarpsystem.zarptube.zarptubeapi.service.VideoService;

@Controller
@RequestMapping
public class IndexController {
	
	@Autowired
	VideoService videoService;
	
	@GetMapping
	String index(String nome, Model model) {
		model.addAttribute("videos", videoService.getVideos().keySet());
		return "index";
	}

	@GetMapping("index")
	String indexX(String nome, Model model) {
		model.addAttribute("videos", videoService.getVideos().keySet());
		return "index";
	}
}
