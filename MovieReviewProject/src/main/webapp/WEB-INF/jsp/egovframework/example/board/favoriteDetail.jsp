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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list/seriesList.css">
    <script type="text/javascript">
	    function seriesDetail(id) {
			console.log(id);
			document.listForm.collectionId.value = id;
			document.listForm.action = "<c:url value='/seriesDetail.do'/>";
			document.listForm.submit();
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
            <h2 class="mypage-title">즐겨찾기</h2>
            <form:form action="localDetail.do" name="listForm" method="post">
                <input type="hidden" name="collectionId" value=""/>
                <div class="box">
                    <c:forEach items="${movieList }" var="movie">
                    	<c:if test="${movie.movieId != 0 }">
	                        <div class="movie-item">
	                            <c:if test="${empty movie.posterPath }">
	                            	<img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:localMovieSelect('${movie.movieId}')" alt="${movie.title }" class="movie-poster">
	                            	<p class="movie-title">${movie.title }</p>
				                </c:if>
				                <c:if test="${not empty movie.posterPath }">
				                	<img src="http://image.tmdb.org/t/p/w780${movie.posterPath}" onclick="javascript:localMovieSelect('${movie.movieId}')" alt="${movie.title }" class="movie-poster">
				                    <p class="movie-title">${movie.title }</p>
				                </c:if>
	                        </div>
	                    </c:if>
                    </c:forEach>
                </div>
            </form:form>
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