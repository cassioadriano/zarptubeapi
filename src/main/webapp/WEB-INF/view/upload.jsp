<!DOCTYPE html>
<html lang="en">

<jsp:include page="include/head.jsp" />

<body>
	<div>
		<jsp:include page="include/nav.jsp" />

		<div class="jumbotron">
			<h4>Upload - MP4 até 100MB</h4>
			<form method="post" enctype="multipart/form-data" action="/upload">
				<input type="file" id="file" name="file" accept="video/mp4" /> <input
					type="submit" id="upload" value="Upload" />
			</form>
		</div>

		<jsp:include page="include/footer.jsp"></jsp:include>

	</div>
	
	<jsp:include page="include/js.jsp"/>
	
</body>
</html>
