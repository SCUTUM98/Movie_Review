<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Film Report: TV</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main/mainStyle.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script>
	    function checkMovie(id){
	    	console.log(id);
	    	
	    	var id = id;
	    	$.ajax({
	    		url: './tvSeriesCheck.do',
	    		type: 'post',
	    		data: {id:id},
	    		dataType: 'json',
	    		success: function(response) {
	    			console.log("서버 응답:", response); 
	                if (response.cnt === 0) { 
	                    seriesSelect(id);
	                } else { 
	                    localSeriesSelect(id);
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
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	
	    function colScrollRight(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.collection-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function sug_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reco-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function sug_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reco-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function aniKR_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.aniKR-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function aniKR_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.aniKR-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function aniJP_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.aniJP-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function aniJP_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.aniJP-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function aniUS_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.aniUS-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function aniUS_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.aniUS-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function reKR_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reKR-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function reKR_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reKR-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function reJP_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reJP-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function reJP_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reJP-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function reUS_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reUS-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function reUS_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.reUS-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function drKR_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.drKR-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function drKR_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.drKR-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function drJP_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.drJP-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function drJP_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.drJP-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    function drUS_scrollLeft(event) {
	    	console.log("Left button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.drUS-list');
	        container.scrollBy({
	            left: -200,
	            behavior: 'smooth'
	        });
	    }
	    function drUS_scrollRight(event) {
	    	console.log("Right button clicked")
	    	event.preventDefault();
	        const container = document.querySelector('.drUS-list');
	        container.scrollBy({
	            left: 200,
	            behavior: 'smooth'
	        });
	    }
	    
	    
	    function seriesSelect(id) {
			console.log(id);
	    	document.listForm.id.value = id;
	       	document.listForm.action = "<c:url value='/detailTv.do'/>";
	       	document.listForm.submit();
	       	}
		function localseriesSelect(id) {
			console.log(id);
			document.listForm.id.value = id;
			document.listForm.action = "<c:url value='/localDetail.do'/>";
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
     <script>
        $(document).ready(function() {
            function updateBigPoster(section, list) {
                let currentIndex = 0;
                const posters = $(list).children('.movie-item');
                
                $(posters).hover(function() {
                    currentIndex = $(this).index();
                    updatePoster(section, currentIndex);
                }, function() {
                    updatePoster(section, currentIndex);
                });

                function updatePoster(section, index) {
                    const posterData = $(posters).eq(index);
                    const posterSrc = posterData.find('img').attr('src');
                    const posterName = posterData.find('.movie-name').text();
                    const overview = posterData.find('.movie-overview').val();
                    console.log(overview);
                    
                    $(section).find('.big-poster').attr('src', posterSrc);
                    $(section).find('.big-poster-title').text(posterName);
                    $(section).find('.big-poster-overview').text(overview);
                }

                updatePoster(section, currentIndex);
            }

            updateBigPoster('.big-poster-section-kr', '.aniKR-list');
            updateBigPoster('.big-poster-section-jp', '.aniJP-list');
            updateBigPoster('.big-poster-section-us', '.aniUS-list');
        });
    </script>
    <script>
        $(document).ready(function() {
            function updateReBigPoster(section, list) {
                let currentIndex = 0;
                const posters = $(list).children('.movie-item');
                
                $(posters).hover(function() {
                    currentIndex = $(this).index();
                    updatePoster(section, currentIndex);
                }, function() {
                    updatePoster(section, currentIndex);
                });

                function updatePoster(section, index) {
                    const posterData = $(posters).eq(index);
                    const posterSrc = posterData.find('img').attr('src');
                    const posterName = posterData.find('.movie-name').text();
                    const overview = posterData.find('.movie-overview').val();
                    console.log(overview);
                    
                    $(section).find('.big-poster').attr('src', posterSrc);
                    $(section).find('.big-poster-title').text(posterName);
                    $(section).find('.big-poster-overview').text(overview);
                }

                updatePoster(section, currentIndex);
            }

            updateReBigPoster('.big-reposter-section-kr', '.reKR-list');
            updateReBigPoster('.big-reposter-section-jp', '.reJP-list');
            updateReBigPoster('.big-reposter-section-us', '.reUS-list');
        });
    </script>
    <script>
        $(document).ready(function() {
            function updateReBigPoster(section, list) {
                let currentIndex = 0;
                const posters = $(list).children('.movie-item');
                
                $(posters).hover(function() {
                    currentIndex = $(this).index();
                    updatePoster(section, currentIndex);
                }, function() {
                    updatePoster(section, currentIndex);
                });

                function updatePoster(section, index) {
                    const posterData = $(posters).eq(index);
                    const posterSrc = posterData.find('img').attr('src');
                    const posterName = posterData.find('.movie-name').text();
                    const overview = posterData.find('.movie-overview').val();
                    console.log(overview);
                    
                    $(section).find('.big-poster').attr('src', posterSrc);
                    $(section).find('.big-poster-title').text(posterName);
                    $(section).find('.big-poster-overview').text(overview);
                }

                updatePoster(section, currentIndex);
            }

            updateReBigPoster('.big-dposter-section-kr', '.drKR-list');
            updateReBigPoster('.big-dposter-section-jp', '.drJP-list');
            updateReBigPoster('.big-dposter-section-us', '.drUS-list');
        });
    </script>
</head>
<body>
    <div class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Platform Logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="/main.do">홈</a></li>
                <li><a href="/main.do">영화</a></li>
                <li><a href="/seriesList.do">드라마</a></li>
                <li><a href="/seriesList.do">TV 프로그램</a></li>
                <li><a href="/seriesList.do">K-컬쳐</a></li>
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
		            <c:if test="${not empty trendingData}">
		                <c:forEach items="${trendingData}" var="trending">
		                    <c:if test="${not empty trending.backdropPath}">
		                        <div class="slide" style="background-image: url('http://image.tmdb.org/t/p/w1280${trending.backdropPath}')" onclick="">
		                            <div class="slide-title">${trending.name}</div>
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
	        <h2>추천 프로그램</h2>
	        <div class="movie-container">
	            <button type="button" class="scroll-btn" onclick="javascript:sug_scrollLeft(event)">◀</button>
	            <div class="reco-list">
	                <c:forEach items="${trendingData}" var="trending">
	                    <div class="movie-item">
	                        <c:if test="${empty trending.posterPath}">
	                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${trending.id})" alt="${trending.originalName}" class="movie-poster">
	                        </c:if>
	                        <c:if test="${not empty trending.posterPath}">
	                            <img src="http://image.tmdb.org/t/p/w780${trending.posterPath}" onclick="checkMovie(${trending.id})" alt="${trending.originalName}" class="movie-poster">
	                        </c:if>
	                        <div class="movie-info">
	                            <p class="movie-name">${trending.name}</p>
	                        </div>
	                    </div>
	                </c:forEach>
	            </div>
	            <button type="button" class="scroll-btn" onclick="javascript:sug_scrollRight(event)">▶</button>
	        </div>
	    </div>
	    
	    <div class="reality-section">
            <h2>예능</h2>
            <div class="kr-section">
                <h3>KOREA</h3>
                <div class="big-reposter-section-kr">
                    <img src="http://image.tmdb.org/t/p/w780${reKR[0].posterPath}" alt="Big Poster" class="big-poster kr-big-poster">
                    <h4 class="big-poster-title kr-big-poster-title">${reKR[0].name}</h4>
                    <h4 class="big-poster-overview">${reKR[0].overview}</h4>
                </div>
                <div class="small-poster-section">
                    <button type="button" class="scroll-btn" onclick="javascript:reKR_scrollLeft(event)">◀</button>
                    <div class="reKR-list">
                        <c:forEach items="${reKR}" var="kr">
                            <div class="movie-item">
                                <c:if test="${empty kr.posterPath}">
                                    <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${kr.id})" alt="${kr.originalName}" class="movie-poster">
                                </c:if>
                                <c:if test="${not empty kr.posterPath}">
                                    <img src="http://image.tmdb.org/t/p/w780${kr.posterPath}" onclick="checkMovie(${kr.id})" alt="${kr.originalName}" class="movie-poster">
                                </c:if>
                                <div class="movie-info">
                                    <p class="movie-name">${kr.name}</p>
                                    <input type="hidden" class="movie-overview" value="${kr.overview }">
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" class="scroll-btn" onclick="javascript:reKR_scrollRight(event)">▶</button>
                </div>
            </div>
            <div class="jp-section">
                <h3>JAPAN</h3>
                <div class="big-reposter-section-jp">
                    <img src="http://image.tmdb.org/t/p/w780${reJP[0].posterPath}" alt="Big Poster" class="big-poster jp-big-poster">
                    <h4 class="big-poster-title jp-big-poster-title">${reJP[0].name}</h4>
                    <h4 class="big-poster-overview">${reJP[0].overview}</h4>        
                </div>
                <div class="small-poster-section">
                    <button type="button" class="scroll-btn" onclick="javascript:reJP_scrollLeft(event)">◀</button>
                    <div class="reJP-list">
                        <c:forEach items="${reJP}" var="jp">
                            <div class="movie-item">
                                <c:if test="${empty jp.posterPath}">
                                    <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${jp.id})" alt="${jp.originalName}" class="movie-poster">
                                </c:if>
                                <c:if test="${not empty jp.posterPath}">
                                    <img src="http://image.tmdb.org/t/p/w780${jp.posterPath}" onclick="checkMovie(${jp.id})" alt="${jp.originalName}" class="movie-poster">
                                </c:if>
                                <div class="movie-info">
                                    <p class="movie-name">${jp.name}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" class="scroll-btn" onclick="javascript:reJP_scrollRight(event)">▶</button>
                </div>
            </div>
            <div class="us-section">
                <h3>US</h3>
                <div class="big-reposter-section-us">
                    <img src="http://image.tmdb.org/t/p/w780${reUS[0].posterPath}" alt="Big Poster" class="big-poster us-big-poster">
                    <h4 class="big-poster-title us-big-poster-title">${reUS[0].name}</h4>
                    <h4 class="big-poster-overview">${reUS[0].overview }</h4>
                </div>
                <div class="small-poster-section">
                    <button type="button" class="scroll-btn" onclick="javascript:reUS_scrollLeft(event)">◀</button>
                    <div class="reUS-list">
                        <c:forEach items="${reUS}" var="us">
                            <div class="movie-item">
                                <c:if test="${empty us.posterPath}">
                                    <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${us.id})" alt="${us.originalName}" class="movie-poster">
                                </c:if>
                                <c:if test="${not empty us.posterPath}">
                                    <img src="http://image.tmdb.org/t/p/w780${us.posterPath}" onclick="checkMovie(${us.id})" alt="${us.originalName}" class="movie-poster">
                                </c:if>
                                <div class="movie-info">
                                    <p class="movie-name">${us.name}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" class="scroll-btn" onclick="javascript:reUS_scrollRight(event)">▶</button>
                </div>
            </div>
        </div>
        
        <div class="drama-section">
		    <h2>드라마</h2>
		    <div class="kr-section">
		        <h3>KOREA</h3>
		        <div class="small-dposter-section">
		            <button type="button" class="scroll-btn" onclick="javascript:drKR_scrollLeft(event)">◀</button>
		            <div class="drKR-list">
		                <c:forEach items="${dramaKR}" var="kr">
		                    <div class="movie-item">
		                        <c:if test="${empty kr.posterPath}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${kr.id})" alt="${kr.originalName}" class="movie-poster">
		                        </c:if>
		                        <c:if test="${not empty kr.posterPath}">
		                            <img src="http://image.tmdb.org/t/p/w780${kr.posterPath}" onclick="checkMovie(${kr.id})" alt="${kr.originalName}" class="movie-poster">
		                        </c:if>
		                        <div class="movie-info">
		                            <p class="movie-name">${kr.name}</p>
		                            <input type="hidden" class="movie-overview" value="${kr.overview}">
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn" onclick="javascript:drKR_scrollRight(event)">▶</button>
		        </div>
		        <div class="big-dposter-section-kr">
		            <img src="http://image.tmdb.org/t/p/w780${dramaKR[0].posterPath}" alt="Big Poster" class="big-poster kr-big-poster">
		            <h4 class="big-poster-title kr-big-poster-title">${dramaKR[0].name}</h4>
		            <h4 class="big-poster-overview">${dramaKR[0].overview}</h4>
		        </div>
		    </div>
		    <div class="jp-section">
		        <h3>JAPAN</h3>
		        <div class="small-dposter-section">
		            <button type="button" class="scroll-btn" onclick="javascript:drJP_scrollLeft(event)">◀</button>
		            <div class="drJP-list">
		                <c:forEach items="${dramaJP}" var="jp">
		                    <div class="movie-item">
		                        <c:if test="${empty jp.posterPath}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${jp.id})" alt="${jp.originalName}" class="movie-poster">
		                        </c:if>
		                        <c:if test="${not empty jp.posterPath}">
		                            <img src="http://image.tmdb.org/t/p/w780${jp.posterPath}" onclick="checkMovie(${jp.id})" alt="${jp.originalName}" class="movie-poster">
		                        </c:if>
		                        <div class="movie-info">
		                            <p class="movie-name">${jp.name}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn" onclick="javascript:drJP_scrollRight(event)">▶</button>
		        </div>
		        <div class="big-dposter-section-jp">
		            <img src="http://image.tmdb.org/t/p/w780${dramaJP[0].posterPath}" alt="Big Poster" class="big-poster jp-big-poster">
		            <h4 class="big-poster-title jp-big-poster-title">${dramaJP[0].name}</h4>
		            <h4 class="big-poster-overview">${dramaJP[0].overview}</h4>
		        </div>
		    </div>
		    <div class="us-section">
		        <h3>US</h3>
		        <div class="small-dposter-section">
		            <button type="button" class="scroll-btn" onclick="javascript:drUS_scrollLeft(event)">◀</button>
		            <div class="drUS-list">
		                <c:forEach items="${dramaUS}" var="us">
		                    <div class="movie-item">
		                        <c:if test="${empty us.posterPath}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${us.id})" alt="${us.originalName}" class="movie-poster">
		                        </c:if>
		                        <c:if test="${not empty us.posterPath}">
		                            <img src="http://image.tmdb.org/t/p/w780${us.posterPath}" onclick="checkMovie(${us.id})" alt="${us.originalName}" class="movie-poster">
		                        </c:if>
		                        <div class="movie-info">
		                            <p class="movie-name">${us.name}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn" onclick="javascript:drUS_scrollRight(event)">▶</button>
		        </div>
		        <div class="big-dposter-section-us">
		            <img src="http://image.tmdb.org/t/p/w780${dramaUS[0].posterPath}" alt="Big Poster" class="big-poster us-big-poster">
		            <h4 class="big-poster-title us-big-poster-title">${dramaUS[0].name}</h4>
		            <h4 class="big-poster-overview">${dramaUS[0].overview}</h4>
		        </div>
		    </div>
		</div>
        
	    
	    <div class="ani-section">
            <h2>애니메이션</h2>
            <div class="kr-section">
                <h3>KOREA</h3>
                <div class="big-poster-section-kr">
                    <img src="http://image.tmdb.org/t/p/w780${aniKR[0].posterPath}" alt="Big Poster" class="big-poster kr-big-poster">
                    <h4 class="big-poster-title kr-big-poster-title">${aniKR[0].name}</h4>
                    <h4 class="big-poster-overview">${aniKR[0].overview}</h4>
                </div>
                <div class="small-poster-section">
                    <button type="button" class="scroll-btn" onclick="javascript:aniKR_scrollLeft(event)">◀</button>
                    <div class="aniKR-list">
                        <c:forEach items="${aniKR}" var="kr">
                            <div class="movie-item">
                                <c:if test="${empty kr.posterPath}">
                                    <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${kr.id})" alt="${kr.originalName}" class="movie-poster">
                                </c:if>
                                <c:if test="${not empty kr.posterPath}">
                                    <img src="http://image.tmdb.org/t/p/w780${kr.posterPath}" onclick="checkMovie(${kr.id})" alt="${kr.originalName}" class="movie-poster">
                                </c:if>
                                <div class="movie-info">
                                    <p class="movie-name">${kr.name}</p>
                                    <input type="hidden" class="movie-overview" value="${kr.overview }">
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" class="scroll-btn" onclick="javascript:aniKR_scrollRight(event)">▶</button>
                </div>
            </div>
            <div class="jp-section">
                <h3>JAPAN</h3>
                <div class="big-poster-section-jp">
                    <img src="http://image.tmdb.org/t/p/w780${aniJP[0].posterPath}" alt="Big Poster" class="big-poster jp-big-poster">
                    <h4 class="big-poster-title jp-big-poster-title">${aniJP[0].name}</h4>
                    <h4 class="big-poster-overview">${aniJP[0].overview}</h4>        
                </div>
                <div class="small-poster-section">
                    <button type="button" class="scroll-btn" onclick="javascript:aniJP_scrollLeft(event)">◀</button>
                    <div class="aniJP-list">
                        <c:forEach items="${aniJP}" var="jp">
                            <div class="movie-item">
                                <c:if test="${empty jp.posterPath}">
                                    <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${jp.id})" alt="${jp.originalName}" class="movie-poster">
                                </c:if>
                                <c:if test="${not empty jp.posterPath}">
                                    <img src="http://image.tmdb.org/t/p/w780${jp.posterPath}" onclick="checkMovie(${jp.id})" alt="${jp.originalName}" class="movie-poster">
                                </c:if>
                                <div class="movie-info">
                                    <p class="movie-name">${jp.name}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" class="scroll-btn" onclick="javascript:aniJP_scrollRight(event)">▶</button>
                </div>
            </div>
            <div class="us-section">
                <h3>US</h3>
                <div class="big-poster-section-us">
                    <img src="http://image.tmdb.org/t/p/w780${aniUS[0].posterPath}" alt="Big Poster" class="big-poster us-big-poster">
                    <h4 class="big-poster-title us-big-poster-title">${aniUS[0].name}</h4>
                    <h4 class="big-poster-overview">${aniUS[0].overview }</h4>
                </div>
                <div class="small-poster-section">
                    <button type="button" class="scroll-btn" onclick="javascript:aniUS_scrollLeft(event)">◀</button>
                    <div class="aniUS-list">
                        <c:forEach items="${aniUS}" var="us">
                            <div class="movie-item">
                                <c:if test="${empty us.posterPath}">
                                    <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie(${us.id})" alt="${us.originalName}" class="movie-poster">
                                </c:if>
                                <c:if test="${not empty us.posterPath}">
                                    <img src="http://image.tmdb.org/t/p/w780${us.posterPath}" onclick="checkMovie(${us.id})" alt="${us.originalName}" class="movie-poster">
                                </c:if>
                                <div class="movie-info">
                                    <p class="movie-name">${us.name}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" class="scroll-btn" onclick="javascript:aniUS_scrollRight(event)">▶</button>
                </div>
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