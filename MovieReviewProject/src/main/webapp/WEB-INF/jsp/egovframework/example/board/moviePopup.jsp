<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>Movie Detail</title>
	<link href="${pageContext.request.contextPath}/css/admin/popup.css" rel="stylesheet" />
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<header class="header">
	        <img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" class="logo">
    </header>
    
    <h2>상세정보</h2>
    <div class="movie-container">
    	<div class="upper-panel">
    		<c:if test="${not empty movieDetail.posterPath }">
    			<img src="http://image.tmdb.org/t/p/w780${movieDetail.posterPath}" alt="${movieDetail.titleEn }" class="poster">
    		</c:if>
    		<c:if test="${empty movieDetail.posterPath }">
    			<img src="${pageContext.request.contextPath}/images/profile.png" alt="${movieDetail.titleEn }" class="poster">
   			</c:if>
    		<c:if test="${not empty seriesDetail.posterPath }">
    			<img src="http://image.tmdb.org/t/p/w780${seriesDetail.posterPath}" alt="${seriesDetail.name }" class="poster">
    		</c:if>
    		<c:if test="${empty seriesDetail.posterPath }">
    			<img src="${pageContext.request.contextPath}/images/profile.png" alt="${seriesDetail.name }" class="poster">
   			</c:if>
   		</div>
   		<div class="down-panel">
   			<table id="movieTable">
   				<tbody>
   					<tr>
   						<th scope="row" colspan="1">영화 ID</th>
   						<td colspan="1">${movieDetail.movieId }</td>
   						<th scope="row" colspan="1">장르</th>
   						<td colspan="1">${movieDetail.genreDB }</td>
   						<th scope="row" colspan="1">개봉일</th>
   						<td colspan="1">${movieDetail.releaseDate }</td>
   					</tr>
   					<tr>
   						<th scope="row" colspan="2">한글 제목</th>
   						<td colspan="4">${movieDetail.titleEn }</td>
   					</tr>
   					<tr>
   						<th scope="row" colspan="2">원문 제목</th>
   						<td colspan="4">${movieDetail.titleKr }</td>
   					</tr>
   					<tr>
   						<th scope="row" colspan="2">Tagline</th>
   						<td colspan="4">${movieDetail.tagline }</td>
   					</tr>
   					<tr>
   						<th scope="row">상태</th>
   						<td>${movieDetail.status }</td>
   						<th scope="row">시리즈 ID</th>
   						<td>${movieDetail.collectionId }</td>
   						<th scope="row">등록일</th>
   						<td>${movieDetail.submitDate }</td>
   					</tr>
   					<tr>
   						<th scope="row">포스터 주소</th>
   						<td colspan="2" onClick="location.href='http://image.tmdb.org/t/p/w780${movieDetail.posterPath}'">${movieDetail.posterPath}</td>
   						<th scope="row">배경 주소</th>
   						<td colspan="2" onClick="location.href='http://image.tmdb.org/t/p/w1280${movieDetail.backdropPath}'">${movieDetail.backdropPath}</td>
   					</tr>
   					<tr>
   						<th scope="row">개요</th>
   						<td colspan="5">${movieDetail.overview }</td>
   					</tr>
   					
   				</tbody>
   			
    		</table>
   		</div>
    	
    </div>
    <div class="btn-section">
    	<button type="button" class="close-button" onClick="window.close()">확인</button>
    </div>
</body>
</html>