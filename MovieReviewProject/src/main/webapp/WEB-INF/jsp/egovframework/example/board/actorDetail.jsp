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
    <title>${actorData.actName } 배우 정보</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detail/detailStyle.css">
    
    <script type="text/javascript">
	    function sscrollLeft(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.movie-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	
	    function scrollRight(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.movie-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
    </script>
</head>
<body>
    <header class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="/main.do">홈</a></li>
                <li><a href="#">영화</a></li>
                <li><a href="#">시리즈</a></li>
                <li><a href="/search.do">검색</a></li>
                <li><a href="#">설정</a></li>
            </ul>
        </nav>
    </header>
    
    <div class="container">
        <div class="left-panel">
            <c:if test="${empty actorData.profilePath}">
            	<img src="${pageContext.request.contextPath}/images/profile.png" alt="${actorData.actName}" class="poster">
	        </c:if>
	        <c:if test="${not empty actorData.profilePath}">
	        	<img src="http://image.tmdb.org/t/p/w780${actorData.profilePath}"alt="${actorData.actName}" class="poster">
	        </c:if>
            <h2 class="series-title">${actorData.actName }</h2>
            
            <c:if test="${not empty actorData.deathday}">
            	<h3>🎉  ${actorData.birthday }</h3>
            	<h3>🎗  ${actorData.deathday }</h3>
            	<h3>🎬 Retired</h3>
            	<h3>🏡  ${actorData.homeplace }</h3>
            </c:if>
            <c:if test="${empty actorData.deathday}">
            	<h3>🎉  ${actorData.birthday }</h3>
            	<h3>🎬 ${actorData.status }</h3>
            	<h3>🏡  ${actorData.homeplace }</h3>
            </c:if>
            
        </div>
        
        <div class="right-panel">
        	<div class="overview-section">
            <h2>개요</h2>
            <p>${snsData.facebook}</p></div>
            
            <div class="movie-section">
	        	<h2>출현작</h2>
		        <div class="movie-container">
		            <button type="button" class="scroll-btn" onclick="javascript:sscrollLeft(event)">◀</button>
		            <div class="movie-list">
		                <c:forEach items="${creditData}" var="credit">
		                    <div class="movie-item">
		                        <c:if test="${empty credit.poster_path}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:movieSelect('${credit.id }')" alt="${credit.title}" class="movie-poster">
		                        </c:if>
		                        <c:if test="${not empty credit.poster_path}">
		                            <img src="http://image.tmdb.org/t/p/w780${credit.poster_path}" onclick="javascript:movieSelect('${credit.id }')" alt="${credit.title}" class="movie-poster">
		                        </c:if>
		                        <div class="movie-info">
		                            <p class="movie-name">${credit.title}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn" onclick="javascript:scrollRight(event)">▶</button>
		        </div>
	    	</div>
        </div>
    </div>
    <script src="script.js"></script>
</body>
</html>
