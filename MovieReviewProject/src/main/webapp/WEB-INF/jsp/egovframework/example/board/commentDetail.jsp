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
    <title>영화</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage/detailPage.css">
    <script type="text/javascript">
	    function localMovieSelect(id) {
			console.log(id);
			document.listForm.id.value = id;
			document.listForm.action = "<c:url value='/localDetail.do'/>";
			document.listForm.submit();
		}
	    function filterReview(value) {
	    	console.log(value);
	        document.getElementById("filterForm").selectedValue.value = value;
	        document.getElementById("filterForm").submit();
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
                <c:if test="${not empty username }">
                    <li><a href="/mypage.do">마이페이지</a></li>
                </c:if>
                <c:if test="${empty username }">
                    <li><a href="/home.do">로그인</a></li>
                </c:if>
                <c:if test="${not empty username }">
                    <li><a href="/logout">로그아웃</a></li>
                </c:if>
            </ul>
        </nav>
    </header>

    <div class="container">
        <div class="right-panel">
            <div class="review-section">
	            <form id="filterForm" action="/commentCategory.do" method="post">
	            	<input type="hidden" name="selectedValue" value="">
					<h2>Reviews</h2>
					<div class="review-list">
					    <c:if test="${empty param.selectedValue}">
						    <select class="selectBox-section" onchange="filterReview(this.value)">
							    <option value="movie" selected>영화</option>
							    <option value="series">시리즈</option>
							    <option value="actor">배우</option>
							</select>
					        <c:forEach items="${reviewList}" var="review">
					            <div class="review-item">
					                <img src="http://image.tmdb.org/t/p/w780${review.posterPath}" alt="Movie Poster" class="review-poster">
					                <div class="review-content">
					                    <div class="review-header">
					                        <span class="review-author">${review.titleEn}</span>
					                        <span class="review-date">${review.submitTime}</span>
					                    </div>
					                    <p>${review.detail}</p>
					                    <div class="review-rating">
					                        <c:if test="${review.rate == '1'}"><span>⭐</span></c:if>
					                        <c:if test="${review.rate == '2'}"><span>⭐⭐</span></c:if>
					                        <c:if test="${review.rate == '3'}"><span>⭐⭐⭐</span></c:if>
					                        <c:if test="${review.rate == '4'}"><span>⭐⭐⭐⭐</span></c:if>
					                        <c:if test="${review.rate == '5'}"><span>⭐⭐⭐⭐⭐</span></c:if>
					                    </div>
					                    <div class="deleteBtn-section">
					                        <form:form action="deleteComment.do" name="deleteForm" method="post">
					                            <input type="hidden" name="id" value="${review.reviewId}">
					                            <button type="button" name="deleteBtn" onclick="">삭제</button>
					                        </form:form>
					                    </div>
					                </div>
					            </div>
					        </c:forEach>
					    </c:if>
					    <c:if test="${param.selectedValue == 'movie'}">
						    <select class="selectBox-section" onchange="filterReview(this.value)">
							    <option value="movie" selected>영화</option>
							    <option value="series">시리즈</option>
							    <option value="actor">배우</option>
							</select>
					        <c:forEach items="${reviewList}" var="review">
					            <div class="review-item">
					                <img src="http://image.tmdb.org/t/p/w780${review.posterPath}" alt="Movie Poster" class="review-poster">
					                <div class="review-content">
					                    <div class="review-header">
					                        <span class="review-author">${review.titleEn}</span>
					                        <span class="review-date">${review.submitTime}</span>
					                    </div>
					                    <p>${review.detail}</p>
					                    <div class="review-rating">
					                        <c:if test="${review.rate == '1'}"><span>⭐</span></c:if>
					                        <c:if test="${review.rate == '2'}"><span>⭐⭐</span></c:if>
					                        <c:if test="${review.rate == '3'}"><span>⭐⭐⭐</span></c:if>
					                        <c:if test="${review.rate == '4'}"><span>⭐⭐⭐⭐</span></c:if>
					                        <c:if test="${review.rate == '5'}"><span>⭐⭐⭐⭐⭐</span></c:if>
					                    </div>
					                    <div class="deleteBtn-section">
					                        <form:form action="deleteComment.do" name="deleteForm" method="post">
					                            <input type="hidden" name="id" value="${review.reviewId}">
					                            <button type="button" name="deleteBtn" onclick="">삭제</button>
					                        </form:form>
					                    </div>
					                </div>
					            </div>
					        </c:forEach>
					    </c:if>
					
					    <c:if test="${param.selectedValue == 'series'}">
						    <select class="selectBox-section" onchange="filterReview(this.value)">
							    <option value="movie">영화</option>
							    <option value="series" selected>시리즈</option>
							    <option value="actor">배우</option>
							</select>
					        <c:forEach items="${seriesList}" var="review">
					            <div class="review-item">
					                <img src="http://image.tmdb.org/t/p/w780${review.posterPath}" alt="Series Poster" class="review-poster">
					                <div class="review-content">
					                    <div class="review-header">
					                        <span class="review-author">${review.cname}</span>
					                        <span class="review-date">${review.submitTime}</span>
					                    </div>
					                    <p>${review.detail}</p>
					                    <div class="review-rating">
					                        <c:if test="${review.rate == '1'}"><span>⭐</span></c:if>
					                        <c:if test="${review.rate == '2'}"><span>⭐⭐</span></c:if>
					                        <c:if test="${review.rate == '3'}"><span>⭐⭐⭐</span></c:if>
					                        <c:if test="${review.rate == '4'}"><span>⭐⭐⭐⭐</span></c:if>
					                        <c:if test="${review.rate == '5'}"><span>⭐⭐⭐⭐⭐</span></c:if>
					                    </div>
					                    <div class="deleteBtn-section">
					                        <form:form action="deleteComment.do" name="deleteForm" method="post">
					                            <input type="hidden" name="id" value="${review.reviewId}">
					                            <button type="button" name="deleteBtn" onclick="">삭제</button>
					                        </form:form>
					                    </div>
					                </div>
					            </div>
					        </c:forEach>
					    </c:if>
					
					    <c:if test="${param.selectedValue == 'actor'}">
					    	<select class="selectBox-section" onchange="filterReview(this.value)">
							    <option value="movie">영화</option>
							    <option value="series">시리즈</option>
							    <option value="actor" selected>배우</option>
							</select>
					        <!-- 배우 리뷰 리스트 출력 -->
					    </c:if>
					</div>

	           </form>
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