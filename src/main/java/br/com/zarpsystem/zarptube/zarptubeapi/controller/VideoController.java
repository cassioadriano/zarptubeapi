package br.com.zarpsystem.zarptube.zarptubeapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.zarpsystem.zarptube.zarptubeapi.service.VideoService;

@Controller
@RequestMapping("/video")
public class VideoController {
	
	private static final String VIDEO_PLAYER = "videoPlayer";

	@Autowired
	VideoService videoService;
	
	@GetMapping("/{nome:.+}")
	String video(@PathVariable String nome, Model model) {
		model.addAttribute("nome", nome);
		return VIDEO_PLAYER;
	}
		
}
