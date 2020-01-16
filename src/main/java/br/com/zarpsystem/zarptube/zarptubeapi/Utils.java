package br.com.zarpsystem.zarptube.zarptubeapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalTime;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	@Value("${zarptube.videos.upload.dir}")
	public String dirVideosUpload;
	
	@Value("${zarptube.videos.dir}")
	public String dirVideos;
	
	@Value("${zarptube.ffmpeg.dir}")
	public String ffmpegDir;

	public void lerEscrever(final InputStream is, OutputStream os) throws IOException {
		byte[] data = new byte[2048];
		int read = 0;
		while ((read = is.read(data)) > 0) {
			os.write(data, 0, read);
		}
		os.flush();
	}

	public String removerCaracEspeciais(String str) {
		return str.replaceAll("[^a-zA-Z0-9\\s+\\.]", "");
	}

	/*public static void main(String[] args) throws IOException {
		Utils utils = new Utils();
		utils.ffmpegDir = "D:/techcoffe/ffmpeg-20200113-7225479-win64-static/bin";
		utils.dirVideosUpload = "D:/techcoffe/videosapi";
		utils.dirVideosRecort = "D:/techcoffe/videosapi/recortes";
		utils.processarArquivo("fish3.mp4", 5);
	}*/

	public LocalTime retornarDuracaoVideo(Runtime rt, String nomeArquivo) throws IOException {
		Process procTotalSeg = rt.exec(String.format(
				"%s/ffprobe.exe -sexagesimal -v error -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 \"%s/%s\"",
				ffmpegDir, dirVideosUpload, nomeArquivo));
		Scanner sTotal = new Scanner(procTotalSeg.getInputStream());
		String strTotal = sTotal.next();
		LocalTime time = LocalTime.parse("0" + strTotal);
		System.out.println(strTotal);
		System.out.println(time);
		sTotal.close();
		return time;
	}

	/*String converterSegParaStringTime(long timeSeconds) {
		// Convert number of seconds into hours:mins:seconds string
		long hours = timeSeconds / 3600;
		long mins = (timeSeconds % 3600) / 60;
		long secs = timeSeconds % 60;
		String timeString = String.format("%02d:%02d:%02d", hours, mins, secs);
		return timeString;
	}*/

}
