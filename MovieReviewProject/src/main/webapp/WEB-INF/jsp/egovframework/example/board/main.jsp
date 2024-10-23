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
	    
	    function colScrollLeft(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.collection-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	
	    function colScrollRight(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.collection-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
	
	    function updateSlider() {
	        const sliderContainer = document.querySelector('.slider-container');
	        const offset = -currentIndex * 100; // 슬라이드 이동 거리
	        sliderContainer.style.transform = `translateX(${offset}%)`;
	    }
	    
	    function movieSelect(id) {
			console.log(id);
	    	document.listForm.id.value = id;
	       	document.listForm.action = "<c:url value='/detail.do'/>";
	       	document.listForm.submit();
	       	}
		function localMovieSelect(id) {
			console.log(id);
			document.listForm.id.value = id;
			document.listForm.action = "<c:url value='/localDetail.do'/>";
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
    
    <form action="addMovie.do" id="listForm" name="listForm" method="post">
    	<input type="hidden" name="id" value="">
    
	    <div class="movie-slider">
	        <div class="slider-container">
	        	<c:if test="${not empty movieData}">
	        		<c:forEach items="${movieData}" var="movie">
	        			<c:if test="${not empty movie.backdropPath }">
	        				<div class="slide" style="background-image: url('http://image.tmdb.org/t/p/w1280${movie.backdropPath}')">
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
	        <h2> 최근 등록 영화</h2>
	        <div class="movie-container">
	            <button type="button" class="scroll-btn" onclick="javascript:sug_scrollLeft(event)">◀</button>
	            <div class="movie-list">
	                <c:forEach items="${recentlyAdded}" var="movie">
	                    <div class="movie-item">
	                        <c:if test="${empty movie.posterPath}">
	                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:localMovieSelect('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
	                        </c:if>
	                        <c:if test="${not empty movie.posterPath}">
	                            <img src="http://image.tmdb.org/t/p/w780${movie.posterPath}" onclick="javascript:localMovieSelect('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
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
	    
	    <div class="movie-section">
	        <h2> 최근 등록 시리즈</h2>
	        <div class="movie-container">
	            <button type="button" class="scroll-btn" onclick="javascript:colScrollLeft(event)">◀</button>
	            <div class="collection-list">
	                <c:forEach items="${recentlyCollected}" var="series">
	                    <div class="movie-item">
	                        <c:if test="${empty series.posterPath && series.id != 0}">
	                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:localMovieSelect('${series.id }')" alt="${series.name}" class="movie-poster">
	                        </c:if>
	                        <c:if test="${not empty series.posterPath && series.id != 0}">
	                            <img src="http://image.tmdb.org/t/p/w780${series.posterPath}" onclick="javascript:localMovieSelect('${series.id }')" alt="${series.name}" class="movie-poster">
	                        </c:if>
	                        
	                        <div class="movie-info">
	                            <p class="movie-name">${series.name}</p>
	                        </div>
	                    </div>
	                </c:forEach>
	            </div>
	            <button type="button" class="scroll-btn" onclick="javascript:colScrollRight(event)">▶</button>
	        </div>
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
	                            <img src="http://image.tmdb.org/t/p/w780${movie.posterPath}" alt="${movie.titleEn}" class="movie-poster">
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
	</form>
</body>
