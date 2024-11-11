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
    <script type="text/javascript">
	    function warningAlert(msg){
	    	alert(msg);
	    	document.reviewForm.action = "<c:url value='redirect:/localDetail.do'/>";
	    	document.reviewForm.submit();
	    }
	    function reviewSubmit(){
	    	console.log("review submit clicked");
	    	document.reviewForm.action = "<c:url value='/addSeriesReview.do'/>";
	    	document.reviewForm.submit();
	    }
	    function localMovieSelect(id) {
			console.log(id);
			document.sereisForm.id.value = id;
			document.sereisForm.action = "<c:url value='/localDetail.do'/>";
			document.sereisForm.submit();
		}
    </script>
</head>
<body>
    <header class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="/main.do">홈</a></li>
                <li><a href="/movieList.do">영화</a></li>
                <li><a href="/seriesList.do">시리즈</a></li>
                <li><a href="/search.do">검색</a></li>
                <c:if test="${not empty username }"><li><a href="#">마이페이지</a></li></c:if>
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
            
            <div class="review-section">
		    <h2>리뷰</h2>
		    
		    <div class="review-input">
		        <form:form action="addReview.do" name="reviewForm" role="form" method="post">
		            <div class="input-group">
		            	<input type="hidden" name="seriesId" value="${collectionList.id }">
		            	<input type="hidden" name="userId" value="${username }">
		            	<c:if test="${not empty username }">
			                <textarea name="detail" placeholder="리뷰를 작성하세요..." maxlength="1000" class="review-textarea"></textarea>
			                <div class="btn-rating">
			                	<div class="rating-input">
				                    <input type="number" name="rate" min="1" max="5" placeholder="평점" class="rating-field"> / 5
				                </div>
				                <button type="submit" onclick="javascript:reviewSubmit()" class="review-button">등록</button>
			                </div>
			            </c:if>
			            <c:if test="${empty username }">
			                <textarea name="detail" placeholder="로그인 후 댓글을 작성할 수 있습니다." maxlength="1000" class="review-textarea" readonly></textarea>
			                <div class="btn-rating">
			                	<div class="rating-input">
				                    <input type="number" name="rate" min="1" max="5" placeholder="평점" class="rating-field" readonly> / 5
				                </div>
				                <button type="submit" onclick="javascript:warningAlert('로그인 후 댓글을 작성할 수 있습니다.')" class="review-button">등록</button>
			                </div>
			            </c:if>
		            </div>
		        </form:form>
		    </div>
		
		    <div class="review-list">
			    <c:forEach items="${reviews}" var="review">
			        <div class="review-item">
			            <div class="review-header">
			                <span class="review-author">${review.userId}</span>
			                <span class="review-date">${review.submitTime}</span>
			            </div>
			            <div class="review-content">
			                <p>${review.detail}</p>
			            </div>
			            <div class="review-rating">
			                <c:if test="${review.rate == '1' }"><span>⭐</span></c:if>
		                    <c:if test="${review.rate == '2' }"><span>⭐⭐</span></c:if>
		                    <c:if test="${review.rate == '3' }"><span>⭐⭐⭐</span></c:if>
		                    <c:if test="${review.rate == '4' }"><span>⭐⭐⭐⭐</span></c:if>
		                    <c:if test="${review.rate == '5' }"><span>⭐⭐⭐⭐⭐</span></c:if>
			            </div>
			        </div>
			    </c:forEach>
			</div>
            
            <div class="movie-section">
	        	<h2>Series</h2>
	        	<form:form name="sereisForm" method="post">
	        	<input type="hidden" name="id" value="">
		        <div class="movie-container">
		            <button type="button" class="scroll-btn" onclick="javascript:sug_scrollLeft(event)">◀</button>
		            <div class="movie-list">
		                <c:forEach items="${movieList}" var="movie">
		                    <div class="movie-item">
		                        <c:if test="${empty movie.posterPath}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="localMovieSelect('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
		                        </c:if>
		                        <c:if test="${not empty movie.posterPath}">
		                            <img src="http://image.tmdb.org/t/p/w780${movie.posterPath}" onclick="localMovieSelect('${movie.movieId }')" alt="${movie.titleEn}" class="movie-poster">
		                        </c:if>
		                        <div class="movie-info">
		                            <p class="movie-name">${movie.titleEn}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn" onclick="javascript:sug_scrollRight(event)">▶</button>
		        </div>
		        </form:form>
	    	</div>
        </div>
    </div>
</div>
</body>
</html>

<%    
response.setHeader("Cache-Control","no-store");    
response.setHeader("Pragma","no-cache");    
response.setDateHeader("Expires",0);    
if (request.getProtocol().equals("HTTP/1.1"))  
        response.setHeader("Cache-Control", "no-cache");  
%>
