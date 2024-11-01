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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list/listPage.css">
    <script type="text/javascript">
    	function genreSelect(id){
    		console.log("genre btn clicked");
    		console.log(id);
    		document.buttonForm.id.value = id;
    		document.buttonForm.action = "<c:url value='/genre.do'/>";
    		document.buttonForm.submit();
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
            <div class="left-panel">
            	<form:form action="genre.do" name="buttonForm" method="post">
            		<input type="hidden" name="id" value=""/>
            		<c:forEach items="${genreList }" var="genre">
            			<button type="button" id="submitBtn" class="menu-button" onclick="javascript:genreSelect('${genre.id}')">${genre.name }</button>
            		</c:forEach>
            	</form:form>
            </div>

            <div class="right-panel">
            	<c:if test="${title == 'no' }"><h2 class="mypage-title">전체</h2></c:if>
                <c:if test="${title != 'no' }"><h2 class="mypage-title">${title }</h2></c:if>
                <form:form action="localDetail.do" name="listForm" method="post">
                	<input type="hidden" name="id" value=""/>
                	<div class="box">
                		<c:forEach items="${movieList }" var="movie">
                			<div class="movie-item">
                				<c:if test="${empty movie.posterPath }">
				                        <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:localMovieSelect('${movie.movieId}')" alt="${movie.titleKr }" class="movie-poster">
				                        <p class="movie-title">${movie.titleEn }</p>
				                </c:if>
				                <c:if test="${not empty movie.posterPath }">
				                        <img src="http://image.tmdb.org/t/p/w780${movie.posterPath}" onclick="javascript:localMovieSelect('${movie.movieId}')" alt="${movie.titleKr }" class="movie-poster">
				                        <p class="movie-title">${movie.titleEn }</p>
				                </c:if>
                			</div>
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
