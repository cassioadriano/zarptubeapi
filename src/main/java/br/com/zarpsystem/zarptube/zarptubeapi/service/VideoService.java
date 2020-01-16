package br.com.zarpsystem.zarptube.zarptubeapi.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.zarpsystem.zarptube.zarptubeapi.Utils;

@Service
public class VideoService {

	Logger log = LoggerFactory.getLogger(VideoService.class);
	
	@Autowired
	Utils utils;
	
	private ConcurrentHashMap<String, File> videos = new ConcurrentHashMap<String, File>();
	
	@PostConstruct
	public void init() {
		this.updateFiles();
	}
	
	public void updateFiles() {
		File dir = new File(utils.dirVideos);
		videos.putAll(Arrays.asList(dir.listFiles()).stream()
				.collect(Collectors.toMap((f) -> {
					return ((File) f).getName();					
				}, (f) -> (File) f)));
	}
			
	public File getVideo(String nomeVideo, String nomeArquivo) {
		return new File(String.format("%s/%s/%s", utils.dirVideos, nomeVideo, nomeArquivo));
	}
		
	public ConcurrentHashMap<String, File> getVideos() {
		return videos;
	}

	public void upload(MultipartFile file) throws IOException, InterruptedException {
		String nomeArquivo = utils.removerCaracEspeciais(file.getOriginalFilename());
		if (videos.containsKey(nomeArquivo)) {
			throw new RuntimeException("Video já existe! Renomeie o video e tente novamente!");
		}
		OutputStream os = new FileOutputStream(new File(utils.dirVideosUpload, nomeArquivo));
		utils.lerEscrever(file.getInputStream(), os);
		os.close();
		
		processarArquivo(nomeArquivo, 5);
		
		updateFiles();
	}
	
	public void processarArquivo(String nomeArquivo, long tamanhoRecorte) throws IOException, InterruptedException {
		String dirNovoVideo = utils.dirVideos + "/" + nomeArquivo;
		File diretorioNovo = new File(dirNovoVideo);
		if (!diretorioNovo.exists()){
			diretorioNovo.mkdir();
			updateFiles();
	    }
		/*
		Runtime rt = Runtime.getRuntime();
		rt.exec(String.format("%s/ffmpeg.exe -i \"%s/%s\" "
				+ " -c:v libx264 -pix_fmt yuv420p -profile:v baseline -preset slower -crf 23 -vf \"scale=trunc(in_w/2)*2:trunc(in_h/2)*2\" -movflags +faststart "
				+ " \"%s/video.mp4\"\n",
				utils.ffmpegDir, utils.dirVideosUpload, nomeArquivo, dirNovoVideo));
		
		rt.exec(String.format("%s/ffmpeg.exe -i \"%s/%s\" "
				+ " -codec:a libvorbis -codec:v libvpx-vp9 -pix_fmt yuv420p -b:v 2M -crf 5 "
				+ " \"%s/video.webm\"\n",
				utils.ffmpegDir, utils.dirVideosUpload, nomeArquivo, dirNovoVideo));
		*/
		compilarMp4(nomeArquivo, dirNovoVideo);
		
		updateFiles();
		
		compilarWebm(nomeArquivo, dirNovoVideo);

	}

	private void compilarWebm(String nomeArquivo, String dirNovoVideo) throws IOException, InterruptedException {
		log.info("Compilando webm");
		Process processDuration = new ProcessBuilder("\"" + utils.ffmpegDir + "/" + "ffmpeg.exe" + "\"",
				"-i", "\"" + utils.dirVideosUpload + "/" + nomeArquivo + "\"",
				"-codec:a", "libvorbis",
				"-codec:v", "libvpx-vp9",
				//"-pix_fmt", "yuv420p",
				"-b:v", "1M",
				"-b:a", "128k",
				"-crf", "28",/*18-28, 0-51 (maior pior)*/
				"-preset", "ultrafast",//velocidade ultrafast, superfast, veryfast, faster, fast, medium (the default), slow, slower, veryslow
				dirNovoVideo + "/video.webm").redirectErrorStream(true).start();
		StringBuilder strBuild = new StringBuilder("webm: ");
		try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(processDuration.getInputStream(), Charset.defaultCharset()));) {
		    String line;
		    while ((line = processOutputReader.readLine()) != null) {
		        strBuild.append(line + System.lineSeparator());
		    }
		    processDuration.waitFor();
		}
		log.info(strBuild.toString().trim());
	}
	
	private void compilarMp4(String nomeArquivo, String dirNovoVideo) throws IOException, InterruptedException {
		log.info("Compilando mp4");
		Process processDuration = new ProcessBuilder("\"" + utils.ffmpegDir + "/" + "ffmpeg.exe" + "\"",
				"-i", "\"" + utils.dirVideosUpload + "/" + nomeArquivo + "\"",
				"-c:v", "libx264",
				"-pix_fmt", "yuv420p",
				"-profile:v", "baseline",
				"-crf", "28",/*18-28, 0-51 (maior pior)*/
				"-preset", "ultrafast",//velocidade ultrafast, superfast, veryfast, faster, fast, medium (the default), slow, slower, veryslow
				"-vf", "\"scale=trunc(in_w/2)*2:trunc(in_h/2)*2\"",
				"-movflags", "+faststart",
				dirNovoVideo + "/video.mp4").redirectErrorStream(true).start();
		StringBuilder strBuild = new StringBuilder("mp4: ");
		try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(processDuration.getInputStream(), Charset.defaultCharset()));) {
		    String line;
		    while ((line = processOutputReader.readLine()) != null) {
		        strBuild.append(line + System.lineSeparator());
		    }
		    processDuration.waitFor();
		}
		log.info(strBuild.toString().trim());
	}

}
