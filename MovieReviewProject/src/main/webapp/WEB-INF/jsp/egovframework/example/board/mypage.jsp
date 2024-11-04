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
            document.listForm.action = "<c:url value='/identify.do'/>";
            document.listForm.submit();
        }
        
        function moveToCommentDetail(id) {
        	console.log(id);
        	document.commentForm.id.value = id;
        	document.commentForm.action = "<c:url value='/commentDetail.do'/>";
        	document.commentForm.submit();
        }
        
        function moveToFavoritesDetail(id) {
        	console.log(id);
        	document.likeForm.id.value = id;
        	document.likeForm.action = "<c:url value='/favoritesDetail.do'/>";
        	document.likeForm.submit();
        }

        function updateProfile() {
            document.getElementById("profileFile").click();
        }

        function uploadProfile() {
            const fileInput = document.getElementById('profileFile');
            const file = fileInput.files[0];
            const formData = new FormData();
            formData.append("id", "${username}"); // 사용자 ID 추가
            formData.append("file", file); // 업로드할 파일 추가

            fetch("<c:url value='/updateProfile.do'/>", {
                method: "POST",
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json(); // JSON으로 변환
            })
            .then(data => {
                if (data.success) {
                    alert("프로필 사진이 업데이트되었습니다.");
                    location.reload(); // 페이지 리로드
                } else {
                    alert("업데이트 실패: " + data.message);
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("업로드 중 오류가 발생했습니다.");
            });
        }
    </script>
</head>
<body>
    <c:if test="${not empty username }">
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

        <h2 class="mypage-title">마이페이지</h2>
        <div class="container">
            <div class="left-panel">
                <div class="profile-section">
                    <img src="${pageContext.request.contextPath}/images/profile.png" 
                         onclick="updateProfile()" alt="Profile Picture" class="profile-pic">
                    <input type="file" id="profileFile" style="display: none;" accept=".jpg, .jpeg, .png" onchange="uploadProfile()">
                    <p class="user-name">${username}</p>
                    <form:form action="updateInfo.do" name="listForm" role="form" method="post">
                        <input type="hidden" name="id" value="">
                        <button type="submit" onclick="updateInfo('${username}')" class="modify-button">회원정보 수정</button>
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
                                    <div class="deleteBtn-section">
                                    	<form:form action="deleteComment.do" name="deleteForm" method="post">
                                    		<input type="hidden" name="id" value="">
                                    		<button type="button" name="deleteBtn" onclick="">삭제</button>
                                    	</form:form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="detailBtn-section">
                    	<form:form action="commentDetail.do" name="commentForm" method="post">
                    		<input type="hidden" name="id" value="">
                    		<button type="button" class="detailBtn" name="commentDetailBtn" onclick="javascript:moveToCommentDetail('${username}')">>>더보기</button>
                    	</form:form>
                    </div>
                </div>

                <h2>Favorites</h2>
                <form commandName="movieVO" name="likeForm" method="post">
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
                    <div class="detailBtn-section">
                    	<button type="button" class="detailBtn" name="favoritesDetailBtn" onclick="javascript:moveToFavoritesDetail('${username}')">>>더보기</button>
	                </div>
                </form>
            </div>
        </div>
    </c:if>
    <c:if test="${empty username }">
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
        <h2>잘못된 접근</h2>
        <h2>로그인이 필요한 페이지 입니다.</h2>
    </c:if>
</body>
</html>
