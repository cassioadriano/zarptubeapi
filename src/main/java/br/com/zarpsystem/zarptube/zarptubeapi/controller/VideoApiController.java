package br.com.zarpsystem.zarptube.zarptubeapi.controller;

import java.io.File;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.zarpsystem.zarptube.zarptubeapi.Utils;
import br.com.zarpsystem.zarptube.zarptubeapi.service.MultipartFileSender;
import br.com.zarpsystem.zarptube.zarptubeapi.service.VideoService;

@RestController
@RequestMapping("/api/video")
public class VideoApiController {
	
	@Autowired
	Utils utils;
	
	@Autowired
	VideoService videoService;

	/*@RequestMapping(method = RequestMethod.GET, value = "/{nomeVideo:.+}")
	public StreamingResponseBody streamVideo(@PathVariable String nomeVideo) throws FileNotFoundException {
		File videoFile = videoService.getVideo(nomeVideo);
		final InputStream videoFileStream = new FileInputStream(videoFile);

		return (os) -> {
			try {
				utils.lerEscrever(videoFileStream, os);
			} catch (ClientAbortException caex) {
				log.warn("Cliente cancelou a conexão");
			} finally {
				videoFileStream.close();
				os.close();
			}
		};
	}*/
	
	@RequestMapping(method = RequestMethod.GET, value = "/3/{nomeVideo:.+}/{nomeArquivo:.+}")
	public void streamVideo(@PathVariable String nomeVideo, @PathVariable String nomeArquivo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		File fileVideo = videoService.getVideo(nomeVideo, nomeArquivo);
		
        MultipartFileSender.fromFile(fileVideo)
                .with(request)
                .with(response)
            .serveResource();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	Set<String> list() {
		return videoService.getVideos().keySet();
	}

}
