<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html lang="en">

<jsp:include page="include/head.jsp" />

<body>
	<div>
		
		<jsp:include page="include/nav.jsp" />
		
		<div class="jumbotron">
			<h4>Vídeos</h4>
			<div id="playlists" class="row">
				<c:forEach var="video" items="${videos}">
					<div class="col-sm-12 col-md-6 col-lg-4 col-xl-3">
					    <div class="card">
						  <div class="card-body">
						    <h5 class="card-title text-truncate">${video}</h5>
						    <p class="card-text"></p>
						    <a href="/video/${video}" class="btn btn-primary">Assistir</a>
						  </div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>

		<jsp:include page="include/footer.jsp"></jsp:include>

	</div>
	
	<jsp:include page="include/js.jsp"/>

</body>
</html>
