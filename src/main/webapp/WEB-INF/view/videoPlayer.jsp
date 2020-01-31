<!DOCTYPE html>
<html lang="en">

<jsp:include page="include/head.jsp" />

<body>
	<div>
	
		<jsp:include page="include/nav.jsp" />
		
		<div class="jumbotron">
	
			<h4 class="text-truncate">${nome}</h4>
			
			<hr class="my-4">
			
	    	<video style="width: 100%"
			    id="player"
			    class="video-js"
			    controls
			    preload="auto"
			    width="640"
			    height="400"
			    data-setup='{ "playbackRates": [0.50, 1, 1.5, 2] }'>			    
	  			<source src="/api/video/3/${nome}/video.mp4" type='video/mp4' />
	  			<!-- <source src="/api/video/3/${nome}/video.webm" type='video/webm' /> -->
			    <p class="vjs-no-js">
			      Para ver este vídeo habilite o javascript ou atualize seu navegador.
			      <a href="https://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>
			    </p>
			  </video>
			
		</div>
		
		<jsp:include page="include/footer.jsp"></jsp:include>
		
	</div>
	
	<jsp:include page="include/js.jsp" />
	
</body>
</html>
