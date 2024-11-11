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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main/mainStyle.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script>
	    function checkId() {
	        var id = $('#id').val(); // id 값 저장
	        $.ajax({
	            url: './idCheck.do', // 요청 URL
	            type: 'post', // POST 방식
	            data: { id: id }, // 전송할 데이터
	            dataType: 'json', // 응답 데이터 형식
	            success: function(response) { // 서버 응답 처리
	                console.log("서버 응답:", response); // 전체 응답 로그 확인
	                if (response.cnt === 0) { // cnt가 0이면 사용 가능한 아이디
	                    $('.id_ok').css("display", "inline-block");
	                    $('.id_already').css("display", "none");
	                } else { // cnt가 0이 아니면 이미 존재하는 아이디
	                    $('.id_already').css("display", "inline-block");
	                    $('.id_ok').css("display", "none");
	                    alert("아이디를 다시 입력해주세요");
	                    $('#id').val('');
	                }
	            },
	            error: function() {
	                alert("에러입니다");
	            }
	        });
	    }
	    function checkMovie(id){
	    	console.log(id);
	    	
	    	var id = id;
	    	$.ajax({
	    		url: './movieCheck.do',
	    		type: 'post',
	    		data: {id:id},
	    		dataType: 'json',
	    		success: function(response) {
	    			console.log("서버 응답:", response); 
	                if (response.cnt === 0) { 
	                    movieSelect(id);
	                } else { 
	                    localMovieSelect(id);
	                }
	    		},
	    		error: function() {
	                alert("에러입니다");
	            }
	    	});
	    }
    </script>
    <script type="text/javascript">
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
	    function sug_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reco-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	    function sug_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reco-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
	    function rec_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.movie-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	    function rec_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.movie-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
	    function up_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.coming-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	    function up_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.coming-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
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
		function seriesDetail(id) {
			console.log(id);
			document.listForm.collectionId.value = id;
			document.listForm.action = "<c:url value='/seriesDetail.do'/>";
			document.listForm.submit();
		}

    </script>
    <script type="text/javascript">
        let currentSlide = 0;

        function showSlide(index) {
        	console.log(index)
            const slides = document.querySelectorAll('.slide');
            const totalSlides = slides.length;
            if (index >= totalSlides) {
                currentSlide = 0;
            } else if (index < 0) {
                currentSlide = totalSlides - 1;
            } else {
                currentSlide = index;
            }
            const sliderContainer = document.querySelector('.slider-container');
            sliderContainer.style.transform = 'translateX(-'+(currentSlide * 100)+'%)';
        }

        function nextSlide() {
            showSlide(currentSlide + 1);
        }

        function prevSlide() {
            showSlide(currentSlide - 1);
        }

        setInterval(nextSlide, 5000);

        document.addEventListener('DOMContentLoaded', () => {
            showSlide(currentSlide);
        });
    </script>
</head>
<body>
    <div class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Platform Logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="/main.do">홈</a></li>
                <li><a href="/movieList.do">영화</a></li>
                <li><a href="/seriesList.do">시리즈</a></li>
                <li><a href="/search.do">검색</a></li>
                <c:if test="${not empty username }"><li><a href="/mypage.do">마이페이지</a></li></c:if>
                <c:if test="${empty username }"><li><a href="/home.do">로그인</a></li></c:if>
                <c:if test="${not empty username }"><li><a href="/logout">로그아웃</a></li></c:if>
            </ul>
        </nav>
    </div>
    
    
    <form name="movieList" method="post">
	    <input type="hidden" name="id" value="">
	    <div class="movie-slider">
		        <div class="slider-container">
		            <c:if test="${not empty movieData}">
		                <c:forEach items="${movieData}" var="movie">
		                    <c:if test="${not empty movie.backdropPath}">
		                        <div class="slide" style="background-image: url('http://image.tmdb.org/t/p/w1280${movie.backdropPath}')" onclick="javascript:checkMovie(${movie.movieId})">
		                            <div class="slide-title">${movie.titleEn}</div>
		                        </div>
		                    </c:if>
		                </c:forEach>
		            </c:if>
		        </div>
		        <button class="slide-btn left" onclick="prevSlide()">◀</button>
		        <button class="slide-btn right" onclick="nextSlide()">▶</button>
		    </div>
	</form>
    
    <form action="" id="listForm" name="listForm" method="post">
    	<input type="hidden" name="id" value="">
    	<input type="hidden" name="collectionId" value="">
    
	     
	    
	    <div class="movie-section">
	        <h2>최근 등록 영화</h2>
	        <div class="movie-container">
	            <button type="button" class="scroll-btn" onclick="javascript:rec_scrollLeft(event)">◀</button>
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
	            <button type="button" class="scroll-btn" onclick="javascript:rec_scrollRight(event)">▶</button>
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
	                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:seriesDetail('${series.id }')" alt="${series.name}" class="movie-poster">
	                        </c:if>
	                        <c:if test="${not empty series.posterPath && series.id != 0}">
	                            <img src="http://image.tmdb.org/t/p/w780${series.posterPath}" onclick="javascript:seriesDetail('${series.id }')" alt="${series.name}" class="movie-poster">
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
	        <h2>개봉 예정</h2>
	        <div class="movie-container">
	            <button type="button" class="scroll-btn" onclick="javascript:up_scrollLeft(event)">◀</button>
	            <div class="coming-list">
	                <c:forEach items="${upComingData}" var="movie">
	                    <div class="movie-item">
	                        <c:if test="${empty movie.posterPath}">
	                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
	                        </c:if>
	                        <c:if test="${not empty movie.posterPath}">
	                            <img src="http://image.tmdb.org/t/p/w780${movie.posterPath}" onclick="checkMovie('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
	                        </c:if>
	                        <div class="movie-info">
	                            <p class="movie-name">${movie.titleEn}</p>
	                        </div>
	                    </div>
	                </c:forEach>
	            </div>
	            <button type="button" class="scroll-btn" onclick="javascript:up_scrollRight(event)">▶</button>
	        </div>
	    </div>
	    
	    <div class="movie-section">
	        <h2>추천 영화</h2>
	        <div class="movie-container">
	            <button type="button" class="scroll-btn" onclick="javascript:sug_scrollLeft(event)">◀</button>
	            <div class="reco-list">
	                <c:forEach items="${movieData}" var="movie">
	                    <div class="movie-item">
	                        <c:if test="${empty movie.posterPath}">
	                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
	                        </c:if>
	                        <c:if test="${not empty movie.posterPath}">
	                            <img src="http://image.tmdb.org/t/p/w780${movie.posterPath}" onclick="checkMovie('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
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

<%    
response.setHeader("Cache-Control","no-store");    
response.setHeader("Pragma","no-cache");    
response.setDateHeader("Expires",0);    
if (request.getProtocol().equals("HTTP/1.1"))  
        response.setHeader("Cache-Control", "no-cache");  
%>