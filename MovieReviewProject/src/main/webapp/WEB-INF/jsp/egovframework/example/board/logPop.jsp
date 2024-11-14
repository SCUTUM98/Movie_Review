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
	<title>Log Detail</title>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<header class="header">
	        <img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" class="logo">
    </header>
    <div class="container">
    	<c:if test="${logDescription.id == 4 || logDescription.id == 5 }">
    		<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td>${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td>${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td>${log.logType }</td>
    					<th scope="row">로그 상세</th>
    					<td>${log.logDetail }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 설명</th>
    					<td>${logDescription.typeDescription }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그인(로그아웃) 일시</th>
    					<td>${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    	</c:if>
    	<c:if test="${logDescription.id == 6 }">
    		<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td>${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td>${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td>${log.logType }</td>
    					<th scope="row">로그 설명</th>
    					<td>${logDescription.typeDescription }</td>
    				</tr>
    				<tr>
    					<th scope="row">이동 페이지</th>
    					<td>${log.logDetail }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 일시</th>
    					<td>${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    	</c:if>
    	<c:if test="${logDescription.id == 7 ||logDescription.id == 8 }">
    		<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td>${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td>${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td>${log.logType }</td>
    					<th scope="row">로그 상세</th>
    					<td>${log.logDetail }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 설명</th>
    					<td>${logDescription.typeDescription }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 일시</th>
    					<td>${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    	</c:if>
    	<c:if test="${logDescription.id == 9 }">
    		<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td>${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td>${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td>${log.logType }</td>
    					<th scope="row">검색어</th>
    					<td>${log.logDetail }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 설명</th>
    					<td>${logDescription.typeDescription }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 일시</th>
    					<td>${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    	</c:if>
    	<c:if test="${logDescription.id == 10 || logDescription.id == 11 }">
    		<div class="left-panel">
    			<c:if test="${not empty movieDetail.posterPath }">
    				<img src="http://image.tmdb.org/t/p/w780${movieDetail.posterPath}" alt="${movieDetail.titleEn }" class="poster">
    				<p class="movie-title">${movieDetail.titleEn }</p>
    			</c:if>
    			<c:if test="${empty movieDetail.posterPath }">
    				<img src="${pageContext.request.contextPath}/images/profile.png" alt="${movieDetail.titleEn }" class="poster">
    				<p class="movie-title">${movieDetail.titleEn }</p>
    			</c:if>
    		</div>
    		<div class="right-panel">
    			<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td>${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td>${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td>${log.logType }</td>
    					<th scope="row">로그 설명</th>
    					<td>${logDescription.typeDescription }</td>
    					
    				</tr>
    				<tr>
    					<th scope="row">영화 ID</th>
    					<td>${log.logDetail }</td>
    					<th scope="row">영화 이름</th>
    					<td>${movieDetail.titleEn }</td>
    					<c:if test="${not empty movieDetail.collectionId }">
    						<th scope="row">시리즈 ID</th>
    						<td>${movieDetail.collectionId }</td>
    					</c:if>
    				</tr>
    				<tr>
    					<th scope="row">로그 일시</th>
    					<td>${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    		</div>
    	</c:if>
    </div>
</body>
</html>