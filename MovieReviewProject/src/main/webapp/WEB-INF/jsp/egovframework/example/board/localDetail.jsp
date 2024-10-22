<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Netflix Clone - Content Detail</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main/detailStyle.css">
    <script src="${pageContext.request.contextPath}/js/detailScript.js"></script>
    
    <script type="text/javascript">
	    function scrollLeft(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.cast-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	
	    function scrollRight(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.cast-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
	    
	    function rec_scrollLeft(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.rec-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	
	    function rec_scrollRight(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.rec-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
	    
	    function moveToDetail(id){
	    	document.listForm.id.value = id;
	    	console.log(document.listForm.id.value);
	    	document.listForm.action = "<c:url value='/detail.do'/>";
	    	document.listForm.submit();
	    }
    </script>
</head>
<body>
    <div class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Platform Logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="/main.do">홈</a></li>
                <li><a href="#">영화</a></li>
                <li><a href="#">시리즈</a></li>
                <li><a href="/search.do">검색</a></li>
                <li><a href="#">설정</a></li>
            </ul>
        </nav>
    </div>
    
    <form action="" id="listForm" name="listForm" method="post">
    	<input type="hidden" name="id" value="">
	    <div class="content-detail" style="background-image: url('http://image.tmdb.org/t/p/w1280${selectMovie.backdropPath }');">
            <div class="overlay">
                <div class="info">
                    <img src="http://image.tmdb.org/t/p/w780${selectMovie.posterPath }" alt="Movie Poster" class="poster">
                    <div class="details">
                        <h1>${selectMovie.titleEn }</h1>
                        <h2><strong>${selectMovie.tagline }</strong></h2>
                        <p>${selectMovie.overview }</p>
                        <p><strong>개봉일: </strong>${selectMovie.releaseDate }</p>
                        <p><strong>장르: </strong>
						    <script type="text/javascript">
						        var genreString = '${selectMovie.genreDB}';
						        genreString = genreString.replace(/[\[\]']/g, '');
						
						        var genres = genreString.split(",").map(function(item) {
						            return item.trim();
						        });
						
						        document.write(genres.join(", "));
						    </script>
						</p>
                    </div>
                </div>
	            <button class="play-button">보러가기</button>
	        </div>
	    </div>
	    
	    <div class="cast-section">
		    <h2>출연 배우</h2>
		    
		        <div class="cast-container">
		            <button type="button" class="scroll-btn left" onclick="javascript:scrollLeft(event)">◀</button>
		            <div class="cast-list">
		                <c:forEach items="${actorData}" var="actor">
		                    <div class="cast-item">
		                        <c:if test="${empty actor.profile_path}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" alt="${actor.original_name}" class="actor-photo">
		                        </c:if>
		                        <c:if test="${not empty actor.profile_path}">
		                            <img src="http://image.tmdb.org/t/p/w500${actor.profile_path}" alt="${actor.original_name}" class="actor-photo">
		                        </c:if>
		                        <div class="actor-info">
		                            <p class="actor-name">${actor.name}</p>
		                            <p class="actor-role">${actor.character}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn right" onclick="javascript:scrollRight(event)">▶</button>
		        </div>
		</div>
		
		<div class="review-section">
			<h2> 리뷰 </h2>
		</div>
		
		<c:if test="${not empty collectionData.name }">
			<div class="collection-detail" style="background-image: url('http://image.tmdb.org/t/p/w1280${collectionData.backdropPath }');">
			    <div class="collection-overlay">
		            <div class="collection-info">
		            	<c:if test="${collectionData.posterPath == null}">
		            		<img src="${pageContext.request.contextPath}/images/profile.png" alt="${collectionData.name}" alt="Movie Poster" class="collection-poster">
		            	</c:if>
			            <c:if test="${not empty collectionData.posterPath}">
			                <img src="http://image.tmdb.org/t/p/w780${collectionData.posterPath}" alt="${collectionData.name}"alt="Movie Poster" class="collection-poster">
			            </c:if>
		                <div class="collection-details">
		                    <h1>${collectionData.name }</h1>
		                    <p>${collectionData.overview }</p>
		                </div>
		            </div>
		            <button class="register-button">시리즈 보러가기</button>
		        </div>
			</div>
		</c:if>
		
		<div class="rec-section">
		    <h2>추천 영화</h2>
		    
		        <div class="rec-container">
		            <button type="button" class="scroll-btn left" onclick="javascript:rec_scrollLeft(event)">◀</button>
		            <div class="rec-list">
		                <c:forEach items="${recommendData}" var="rec">
		                    <div class="cast-item">
		                        <c:if test="${empty rec.poster_path}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:moveToDetail('${rec.id }')" alt="${rec.title}" class="actor-photo">
		                        </c:if>
		                        <c:if test="${not empty rec.poster_path}">
		                            <img src="http://image.tmdb.org/t/p/w500${rec.poster_path}" onclick="javascript:moveToDetail('${rec.id }')" alt="${r.title}" class="actor-photo">
		                        </c:if>
		                        <div class="actor-info">
		                            <p class="actor-name">${rec.title}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn right" onclick="javascript:rec_scrollRight(event)">▶</button>
		        </div>
		    
		</div>
		
	</form>

</body>
</html>

