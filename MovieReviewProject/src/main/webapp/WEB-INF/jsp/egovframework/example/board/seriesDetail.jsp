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
    <title>Film Report ${collectionList.name }</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detail/detailStyle.css">
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
                <c:if test="${empty username }"><li><a href="/home.do">로그인</a></li></c:if>
                <c:if test="${not empty username }"><li><a href="/logout">로그아웃</a></li></c:if>
            </ul>
        </nav>
    </header>
    
    <div class="container">
        <div class="left-panel">
            <c:if test="${empty collectionList.posterPath && collectionList.id != 0}">
            	<img src="${pageContext.request.contextPath}/images/profile.png" alt="${collectionList.name}" class="poster">
	        </c:if>
	        <c:if test="${not empty collectionList.posterPath && collectionList.id != 0}">
	        	<img src="http://image.tmdb.org/t/p/w780${collectionList.posterPath}"alt="${collectionList.name}" class="poster">
	        </c:if>
            <h2 class="series-title">${collectionList.name }</h2>
        </div>
        
        <div class="right-panel">
        	<div class="overview-section">
            <h2>개요</h2>
            <p>${collectionList.overview }</p></div>
            
            <div class="movie-section">
	        	<h2>Series</h2>
		        <div class="movie-container">
		            <button type="button" class="scroll-btn" onclick="javascript:sug_scrollLeft(event)">◀</button>
		            <div class="movie-list">
		                <c:forEach items="${movieList}" var="movie">
		                    <div class="movie-item">
		                        <c:if test="${empty movie.posterPath}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:movieSelect('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
		                        </c:if>
		                        <c:if test="${not empty movie.posterPath}">
		                            <img src="http://image.tmdb.org/t/p/w780${movie.posterPath}" onclick="javascript:movieSelect('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
		                        </c:if>
		                        <div class="movie-info">
		                            <p class="movie-name">${movie.titleEn}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn" onclick="javascript:sug_scrollRight(event)">▶</button>
		        </div>
	    	</div>
        </div>
    </div>
    <script src="script.js"></script>
</body>
</html>

<%    
response.setHeader("Cache-Control","no-store");    
response.setHeader("Pragma","no-cache");    
response.setDateHeader("Expires",0);    
if (request.getProtocol().equals("HTTP/1.1"))  
        response.setHeader("Cache-Control", "no-cache");  
%>
