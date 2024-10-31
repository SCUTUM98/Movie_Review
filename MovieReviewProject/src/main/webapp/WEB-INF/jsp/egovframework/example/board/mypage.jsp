<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>마이페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage/mypage.css">
<script type="text/javascript">
	function movieSelect(id) {
		console.log(id);
		document.likeForm.id.value = id;
	   	document.likeForm.action = "<c:url value='/localDetail.do'/>";
	   	document.likeForm.submit();
	   	}
	function updateInfo(id) {
		console.log(id);
		document.listForm.id.value = id;
		document.listForm.action = "<c:url value='/updateInfo.do'/>";
		document.listForm.submit;
	}
</script>

</head>
<body>
	<c:if test="${not empty username }">
		<header class="header">
			<img src="${pageContext.request.contextPath}/images/logo.png"
				alt="logo" class="logo">
			<nav class="navbar">
				<ul>
					<li><a href="/main.do">홈</a></li>
					<li><a href="#">영화</a></li>
					<li><a href="#">시리즈</a></li>
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

		<h2 class="mypage-title">마이페이지</h2>
		<div class="container">
			<div class="left-panel">
				<div class="profile-section">
					<img src="${pageContext.request.contextPath}/images/profile.png"
						alt="Profile Picture" class="profile-pic">
					<p class="user-name">${username}</p>
					<form:form action="updateInfo.do" name="listForm" role="form" method="post">
						<input type="hidden" name="id" value="">
						<button type="submit" onclick="javascript:updateInfo('${username}')" class="modify-button">회원정보 수정</button>
					</form:form>
				</div>
			</div>

			<div class="right-panel">
				<div class="review-section">
					<h2>Reviews</h2>
					<div class="review-list">
						<c:forEach items="${reviewList}" var="review">
							<div class="review-item">
								<img src="http://image.tmdb.org/t/p/w780${review.posterPath }"
									alt="Movie Poster" class="review-poster">
								<!-- 포스터 이미지 추가 -->
								<div class="review-content">
									<div class="review-header">
										<span class="review-author">${username}</span> <span
											class="review-date">${review.submitTime}</span>
									</div>
									<p>${review.detail}</p>
									<div class="review-rating">
										<c:if test="${review.rate == '1' }">
											<span>⭐</span>
										</c:if>
										<c:if test="${review.rate == '2' }">
											<span>⭐⭐</span>
										</c:if>
										<c:if test="${review.rate == '3' }">
											<span>⭐⭐⭐</span>
										</c:if>
										<c:if test="${review.rate == '4' }">
											<span>⭐⭐⭐⭐</span>
										</c:if>
										<c:if test="${review.rate == '5' }">
											<span>⭐⭐⭐⭐⭐</span>
										</c:if>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>

				<h2>Favorites</h2>
				<form:form commandName="movieVO" name="likeForm" method="post">
					<input type="hidden" name="id" value="">
					<div class="box">
						<c:forEach items="${likeList}" var="like">
							<c:if test="${empty like.posterPath}">
								<a href="javascript:movieSelect('${like.movieId }')"> <img
									src="${pageContext.request.contextPath}/images/profile.png"
									alt="${like.title}" class="movie-poster">
									<p class="movie-title">${like.title}</p>
								</a>
							</c:if>
							<c:if test="${not empty like.posterPath}">
								<a href="javascript:movieSelect('${like.movieId }')"> <img
									src="http://image.tmdb.org/t/p/w780${like.posterPath}"
									alt="${like.title}" class="movie-poster">
									<p class="movie-title">${like.title}</p>
								</a>
							</c:if>
						</c:forEach>
					</div>
				</form:form>
			</div>
		</div>

	</c:if>
	<c:if test="${empty username }">
		<h2>로그인 필요</h2>
	</c:if>

</body>
</html>
