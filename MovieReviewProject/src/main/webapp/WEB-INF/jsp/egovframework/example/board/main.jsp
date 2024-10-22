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
    <title>영화 리뷰 사이트</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main/mainStyle.css">
    
    <script type="text/javascript">
	    function sug_scrollLeft(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.movie-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	
	    function sug_scrollRight(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.movie-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
    
	    let currentIndex = 0;
	
	    function scrollLeft(event) {
	        console.log("Left button clicked");
	        const slides = document.querySelectorAll('.slide');
	        currentIndex = (currentIndex === 0) ? slides.length - 1 : currentIndex - 1;
	        updateSlider();
	    }
	
	    function scrollRight(event) {
	        console.log("Right button clicked");
	        const slides = document.querySelectorAll('.slide');
	        currentIndex = (currentIndex === slides.length - 1) ? 0 : currentIndex + 1;
	        updateSlider();
	    }
	
	    function updateSlider() {
	        const sliderContainer = document.querySelector('.slider-container');
	        const offset = -currentIndex * 100; // 슬라이드 이동 거리
	        sliderContainer.style.transform = `translateX(${offset}%)`;
	    }

    </script>
</head>
<body>
    <div class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Platform Logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="#">홈</a></li>
                <li><a href="#">영화</a></li>
                <li><a href="#">시리즈</a></li>
                <li><a href="#">검색</a></li>
                <li><a href="#">설정</a></li>
            </ul>
        </nav>
    </div>
    
    <div class="movie-slider">
        <div class="slider-container">
        	<c:if test="${not empty movieData}">
        		<c:forEach items="${movieData}" var="movie">
        			<c:if test="${not empty movie.backdropPath }">
        				<div class="slide" style="background-image: url('http://image.tmdb.org/t/p/w500${movie.backdropPath}')">
            				<div class="slide-title">${movie.titleEn}</div>
        				</div>
        			</c:if>
        		</c:forEach>
        	</c:if>
        </div>
        <button class="scroll-btn" onclick="javascript:scrollLeft(event)">◀</button>
        <button class="scroll-btn" onclick="javascript:scrollRight(event)">▶</button>
    </div>
    
    <div class="movie-section">
        <h2>추천 영화</h2>
        <div class="movie-container">
            <button type="button" class="scroll-btn" onclick="javascript:sug_scrollLeft(event)">◀</button>
            <div class="movie-list">
                <c:forEach items="${movieData}" var="movie">
                    <div class="movie-item">
                        <c:if test="${empty movie.posterPath}">
                            <img src="${pageContext.request.contextPath}/images/profile.png" alt="${movie.titleEn}" class="movie-poster">
                        </c:if>
                        <c:if test="${not empty movie.posterPath}">
                            <img src="http://image.tmdb.org/t/p/w500${movie.posterPath}" alt="${movie.titleEn}" class="movie-poster">
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
</body>
