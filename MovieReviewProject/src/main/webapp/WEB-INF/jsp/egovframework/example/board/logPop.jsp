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
	<link href="${pageContext.request.contextPath}/css/admin/popup.css" rel="stylesheet" />
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<header class="header">
	        <img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" class="logo">
    </header>
    
    <h2>상세정보</h2>
    <div class="container">
    	<c:if test="${logDescription.id == 4 || logDescription.id == 5 }">
    		<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td >${log.logId }</td>
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
    					<th scope="row" colspan="3">로그 설명</th>
    					<td >${logDescription.typeDescription }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="3">로그인(로그아웃) 일시</th>
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
    					<th scope="row" colspan="3">이동 페이지</th>
    					<td>${log.logDetail }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="3">로그 일시</th>
    					<td>${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    	</c:if>
    	<c:if test="${logDescription.id == 7 ||logDescription.id == 8 || logDescription.id == 22 || logDescription.id == 29 || logDescription.id == 32}">
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
    					<th scope="row" colspan="3">로그 설명</th>
    					<td>${logDescription.typeDescription }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="3">로그 일시</th>
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
    					<th scope="row" colspan="3">로그 설명</th>
    					<td>${logDescription.typeDescription }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="3">로그 일시</th>
    					<td>${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    	</c:if>
    	<c:if test="${logDescription.id == 10 || logDescription.id == 11 || logDescription.id == 12 || logDescription.id == 16 || logDescription.id == 30 || logDescription.id == 31 }">
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
    					<td colspan="2">${log.logId }</td>
    					<th scope="row" colspan="2">사용자 ID</th>
    					<td colspan="2">${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td colspan="2">${log.logType }</td>
    					<th scope="row" colspan="2">로그 설명</th>
    					<td colspan="2">${logDescription.typeDescription }</td>
    					
    				</tr>
    				<tr>
    					<th scope="row">영화 ID</th>
    					<td>${log.logDetail }</td>
    					<th scope="row">영화 이름</th>
    					<td>${movieDetail.titleEn }</td>
    					<th scope="row">시리즈 ID</th>
    					<td>${movieDetail.collectionId }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="3">로그 일시</th>
    					<td colspan="3">${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    		</div>
    	</c:if>
    	<c:if test="${logDescription.id == 13}">
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
    					<td colspan="2">${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td colspan="2">${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td colspan="2">${log.logType }</td>
    					<th scope="row" >로그 설명</th>
    					<td colspan="2">${logDescription.typeDescription }</td>
    					
    				</tr>
    				<tr>
    					<th scope="row">영화 ID</th>
    					<td>${log.logDetail }</td>
    					<th scope="row">영화 이름</th>
    					<td>${movieDetail.titleEn }</td>
    					<th scope="row">시리즈 ID</th>
						<td>${movieDetail.collectionId }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="2">댓글 내용</th>
    					<td colspan="4">${log.logDetail2 }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="2">로그 일시</th>
    					<td colspan="4">${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    		</div>
    	</c:if>
    	<c:if test="${logDescription.id == 14}">
    		<div class="left-panel">
    			<c:if test="${not empty seriesDetail.posterPath }">
    				<img src="http://image.tmdb.org/t/p/w780${seriesDetail.posterPath}" alt="${seriesDetail.name }" class="poster">
    				<p class="movie-title">${seriesDetail.name }</p>
    			</c:if>
    			<c:if test="${empty seriesDetail.posterPath }">
    				<img src="${pageContext.request.contextPath}/images/profile.png" alt="${seriesDetail.name }" class="poster">
    				<p class="movie-title">${seriesDetail.name }</p>
    			</c:if>
    		</div>
    		<div class="right-panel">
    			<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td colspan="2">${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td colspan="2">${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td colspan="2">${log.logType }</td>
    					<th scope="row">로그 설명</th>
    					<td colspan="2">${logDescription.typeDescription }</td>
    					
    				</tr>
    				<tr>
    					<th scope="row">시리즈 ID</th>
    					<td colspan="2">${log.logDetail }</td>
    					<th scope="row">시리즈 이름</th>
    					<td colspan="2">${seriesDetail.name }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="2">댓글 내용</th>
    					<td colspan="4">${log.logDetail2 }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="2">로그 일시</th>
    					<td colspan="4">${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    		</div>
    	</c:if>
    	<c:if test="${logDescription.id == 15}">
    		<div class="left-panel">
    			<c:if test="${not empty actorDetail.profilePath }">
    				<img src="http://image.tmdb.org/t/p/w780${actorDetail.profilePath}" alt="${actorDetail.actName }" class="poster">
    				<p class="movie-title">${seriesDetail.actName }</p>
    			</c:if>
    			<c:if test="${empty actorDetail.profilePath }">
    				<img src="${pageContext.request.contextPath}/images/profile.png" alt="${actorDetail.actName }" class="poster">
    				<p class="movie-title">${actorDetail.actName }</p>
    			</c:if>
    		</div>
    		<div class="right-panel">
    			<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td colspan="2">${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td colspan="2">${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td colspan="2">${log.logType }</td>
    					<th scope="row">로그 설명</th>
    					<td colspan="2">${logDescription.typeDescription }</td>
    					
    				</tr>
    				<tr>
    					<th scope="row">배우 ID</th>
    					<td colspan="2">${log.logDetail }</td>
    					<th scope="row">배우 이름</th>
    					<td colspan="2">${actorDetail.actName }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="2">댓글 내용</th>
    					<td colspan="4">${log.logDetail2 }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="2">로그 일시</th>
    					<td colspan="4">${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    		</div>
    	</c:if>
    	<c:if test="${logDescription.id == 17 }">
    		<div class="left-panel">
    			<c:if test="${not empty seriesDetail.posterPath }">
    				<img src="http://image.tmdb.org/t/p/w780${seriesDetail.posterPath}" alt="${seriesDetail.name }" class="poster">
    				<p class="movie-title">${seriesDetail.name }</p>
    			</c:if>
    			<c:if test="${empty seriesDetail.posterPath }">
    				<img src="${pageContext.request.contextPath}/images/profile.png" alt="${seriesDetail.name }" class="poster">
    				<p class="movie-title">${seriesDetail.name }</p>
    			</c:if>
    		</div>
    		<div class="right-panel">
    			<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td colspan="2">${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td colspan="2">${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td colspan="2">${log.logType }</td>
    					<th scope="row">로그 설명</th>
    					<td colspan="2">${logDescription.typeDescription }</td>
    					
    				</tr>
    				<tr>
    					<th scope="row">시리즈 ID</th>
    					<td colspan="2">${log.logDetail }</td>
    					<th scope="row">시리즈 이름</th>
    					<td colspan="2">${seriesDetail.name }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="2">로그 일시</th>
    					<td colspan="4">${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    		</div>
    	</c:if>
    	<c:if test="${logDescription.id == 19 || logDescription.id == 20 || logDescription.id == 21 }">
    		<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td colspan="2">${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td colspan="2">${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td colspan="2">${log.logType }</td>
    					<c:if test="${logDescription.id == 19 }"><th scope="row">영화 ID</th></c:if>
    					<c:if test="${logDescription.id == 20 }"><th scope="row">시리즈 ID</th></c:if>
    					<c:if test="${logDescription.id == 21 }"><th scope="row">배우 ID</th></c:if>
    					<td colspan="2">${log.logDetail }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 설명</th>
    					<td colspan="2">${logDescription.typeDescription }</td>
    				</tr>
    				<tr>
    					<th scope="row">삭제 일시</th>
    					<td colspan="2">${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    	</c:if>
    	<c:if test="${logDescription.id == 23 || logDescription.id == 24 || logDescription.id == 25 || logDescription.id == 28}">
    		<table id="logTable">
    			<tbody>
    				<tr>
    					<th scope="row">로그 ID</th>
    					<td colspan="2">${log.logId }</td>
    					<th scope="row">사용자 ID</th>
    					<td colspan="2">${log.userId }</td>
    				</tr>
    				<tr>
    					<th scope="row">로그 타입</th>
    					<td colspan="2">${log.logType }</td>
    					<c:if test="${logDescription.id == 23 || logDescription.id == 24 }"><th scope="row">인증코드</th></c:if>
    					<c:if test="${logDescription.id == 25 || logDescription.id == 28 }"><th scope="row">이메일</th></c:if>
    					<td colspan="2">${log.logDetail }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="2">로그 설명</th>
    					<td colspan="4">${logDescription.typeDescription }</td>
    				</tr>
    				<tr>
    					<th scope="row" colspan="2">삭제 일시</th>
    					<td colspan="4">${log.reportTime }</td>
    				</tr>
    			</tbody>
    		</table>
    	</c:if>
    </div>
    <div class="btn-section">
    	<button type="button" class="close-button" onClick="window.close()">확인</button>
    </div>
</body>
</html>