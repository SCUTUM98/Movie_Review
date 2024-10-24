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
    <title>Film Report</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main/detailStyle.css">
    
    <script type="text/javascript">
	    function act_scrollLeft(event) {
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
	    	console.log(document.listForm.movieId.value);
	    	document.listForm.action = "<c:url value='/detail.do'/>";
	    	document.listForm.submit();
	    }
	    
	    function moveToLocalDetail(id){
	    	document.listForm.id.value = id;
	    	console.log(document.listForm.id.value);
	    	document.listForm.action = "<c:url value='/localDetail.do'/>";
	    	document.listForm.submit();
	    }
	    
	    function movieSubmit(id){
	    	document.listForm.id.value = id;
	    	console.log(document.listForm.actorIdList.value)
	    	document.listForm.action = "<c:url value='/addMovie.do'/>";
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
    
    <!-- 모든 데이터를 포함하는 폼 -->
    <form action="addMovie.do" id="listForm" name="listForm" method="post">
    	<input type="hidden" name="id" value="">
        <input type="hidden" name="movieId" value="${detailData.movieId }">
        <input type="hidden" name="titleKr" value="${detailData.titleKr }">
        <input type="hidden" name="titleEn" value="${detailData.titleEn }">
        <input type="hidden" name="genreDB" value="${detailData.genre }">
        <input type="hidden" name="releaseDate" value="${detailData.releaseDate }">
        <input type="hidden" name="overview" value="${detailData.overview }">
        <input type="hidden" name="backdropPath" value="${detailData.backdropPath }">
        <input type="hidden" name="posterPath" value="${detailData.posterPath }">
        <input type="hidden" name="collectionId" value="${detailData.collectionId }">
        <input type="hidden" name="status" value="${detailData.status }">
        <input type="hidden" name="tagline" value="${detailData.tagline }">
        <input type="hidden" name="seriesId" value="${collectionData.id}">
        <input type="hidden" name="seriesName" value="${collectionData.name}">
        <input type="hidden" name="seriesDropPath" value="${collectionData.backdropPath}">
        <input type="hidden" name="seriesPosterPath" value="${collectionData.posterPath}">
        <input type="hidden" name="seriesOverview" value="${overviewData.overview}">
        <c:forEach items="${actorData}" var="actor">
		    <input type="hidden" name="actorIdList" value="${actor.id}"/>
		    <input type="hidden" name="actNameList" value="${actor.name}"/>
		    <input type="hidden" name="actProfilePathList" value="${actor.profile_path}"/>
		</c:forEach>

       
        <div class="content-detail" style="background-image: url('http://image.tmdb.org/t/p/w1280${detailData.backdropPath }');">
            <div class="overlay">
                <div class="info">
                	<c:if test="${empty detailData.posterPath }">
                		<img src="${pageContext.request.contextPath}/images/profile.png" alt="Movie Poster" class="poster">
                	</c:if>
                	<c:if test="${not empty detailData.posterPath }">
                		<img src="http://image.tmdb.org/t/p/w780${detailData.posterPath }" alt="Movie Poster" class="poster">
                	</c:if>
                    
                    <div class="details">
                        <h1>${detailData.titleEn }</h1>
                        <h2><strong>${detailData.tagline }</strong></h2>
                        <p>${detailData.overview }</p>
                        <p><strong>개봉일: </strong>${detailData.releaseDate }</p>
                        <p><strong>장르: </strong>
                            <c:forEach items="${detailData.genre}" var="genre" varStatus="status">
                                ${genre}<c:if test="${!status.last}">, </c:if>
                            </c:forEach>
                        </p>
                    </div>
                </div>
                <button type="button" id="submitBtn" class="register-button" onclick="javascript:movieSubmit('${detailData.movieId}')">영화 등록하기</button>
            </div>
        </div>
        
        <c:if test="${not empty videoData.key }">
		    <div class="video-section">
		        <h2>예고편</h2>
		        <div class="video-container">
		            <div class="video-item">
		                <iframe width="720" height="405" src="https://www.youtube.com/embed/${videoData.key }" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
		            </div>
		            <div class="background-image" style="background-image: url('http://image.tmdb.org/t/p/w1280${detailData.backdropPath}');"></div>
		        </div>
		    </div>
		</c:if>
       
        <div class="cast-section">
            <h2>출연 배우</h2>
            <div class="cast-container">
                <button type="button" class="scroll-btn left" onclick="javascript:act_scrollLeft(event)">◀</button>
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
        
        <c:if test="${not empty collectionData.name}">
            <div class="collection-detail" style="background-image: url('http://image.tmdb.org/t/p/w1280${detailData.backdropPath }');">
                <div class="collection-overlay">
                    <div class="collection-info">
                        <c:if test="${collectionData.posterPath == null || empty collectionData.posterPath}">
                            <img src="${pageContext.request.contextPath}/images/profile.png" alt="${collectionData.name}" class="collection-poster">
                        </c:if>
                        <c:if test="${not empty collectionData.posterPath}">
                            <img src="http://image.tmdb.org/t/p/w780${collectionData.posterPath}" alt="${collectionData.name}" class="collection-poster">
                        </c:if>
                        <div class="collection-details">
                            <h1>${collectionData.name }</h1>
                            <p>${overviewData.overview }</p>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        
        <div class="rec-section">
            <h2>추천 영화</h2>
            <div class="rec-container">
                <button type="button" class="scroll-btn left" onclick="javascript:rec_scrollLeft(event)">◀</button>
                <div class="rec-list">
                	<c:forEach items="${notUniqueData}" var="dupRec">
		                <div class="cast-item">
		                    <c:if test="${empty dupRec.poster_path}">
		                        <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:moveToLocalDetail('${dupRec.id}')" alt="${dupRec.title}" class="actor-photo">
		                    </c:if>
		                    <c:if test="${not empty dupRec.poster_path}">
		                        <img src="http://image.tmdb.org/t/p/w780${dupRec.poster_path}" onclick="javascript:moveToLocalDetail('${dupRec.id}')" alt="${dupRec.title}" class="actor-photo">
		                    </c:if>
		                    <div class="actor-info">
		                        <p class="actor-name">${dupRec.title}</p>
		                    </div>
		                </div>
		            </c:forEach>
                    <c:forEach items="${recommendData}" var="rec" >
                        <div class="cast-item">
                            <c:if test="${empty rec.poster_path}">
                                <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:moveToDetail('${rec.id }')" alt="${rec.title}" class="actor-photo">
                            </c:if>
                            <c:if test="${not empty rec.poster_path}">
                                <img src="http://image.tmdb.org/t/p/w780${rec.poster_path}" onclick="javascript:moveToDetail('${rec.id }')" alt="${rec.title}" class="actor-photo">
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

    <script type="text/javascript">
        function submitForm(action) {
            document.getElementById('actionType').value = action;
            document.mainForm.submit();
        }
    </script>
</body>

</html>

