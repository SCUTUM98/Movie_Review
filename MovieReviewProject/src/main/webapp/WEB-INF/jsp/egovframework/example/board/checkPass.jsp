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
    <title>회원정보 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage/updateMember.css">
    
    <script>
        window.onload = function() {
            var errorMessage = "${errorMessage}";
            if (errorMessage) {
                alert(errorMessage);
                errorMessage = null;
            }
        };
    </script>
    
    <script type="text/javascript">    
    	function passCheck(){
    		console.log("Password check Btn clicked");
    		document.updateForm.action = "<c:url value='/updateInfo.do'/>";
    		document.updateForm.submit();
    	}
    </script>
</head>
<body>
    <div class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Platform Logo" class="logo">
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
    </div>

    <div class="content-detail">
        <div class="overlay">
            <div class="info">
                <div class="details">
                    <h1>비밀번호 확인</h1>
                    <form action="/updateInfo.do" name="updateForm" method="post">
                    	<input type="hidden" name="id" value="${username}">
                        <div class="input-group">
                            <label for="name">비밀번호를 입력해주세요.</label>
                            <input type="password" id="name" name="pw" value="" required>
                        </div>
                        <button type="submit" onclick="javascript:passCheck()">확인</button>
                    </form>
                    <button onclick="redirect:/mypage.do" class="back-button">마이페이지 돌아가기</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
