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
    
    <script type="text/javascript">
    	function updateEmail(){
    		console.log("Email Update Btn clicked");
    		document.updateForm.action = "<c:url value='/updateEmail.do'/>";
    		documnet.updateForm.submit;
    	}
    	function updatePass(){
    		console.log("Password Update Btn clicked");
    		document.updateForm.action = "<c:url value='/updatePass.do'/>";
    		document.updateForm.submit;
    	}
    </script>
</head>
<body>
    <div class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Platform Logo" class="logo">
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
    </div>

    <div class="content-detail">
        <div class="overlay">
            <div class="info">
                <div class="details">
                    <h1>회원정보 수정</h1>
                    <form action="/updateUserInfo" name="updateForm" method="post">
                        <div class="input-group">
                            <label for="name">이름</label>
                            <input type="text" id="name" name="name" value="${userInfo.name }" readonly>
                        </div>
                        <div class="input-group">
                            <label for="username">아이디</label>
                            <input type="text" id="id" name="id" value="${userInfo.id }" readonly>
                        </div>
                        <div class="input-group">
                            <label for="email">이메일</label>
                            <input type="email" id="email" name="email" value="${userInfo.email }">
                            <button type="submit" onclick="javascript:updateEmail()">이메일 수정</button>
                        </div>
                        <div class="input-group">
                            <label for="password">비밀번호</label>
                            <input type="password" id="pass" name="pass" value="">
                        </div>
                        <div class="input-group">
                            <label for="confirmPassword">비밀번호 확인</label>
                            <input type="password" id="confirmPassword" name="confirmPassword">
                        </div>
                        <button type="submit" onclick="javascript:updatePass()">비밀번호 변경</button>
                    </form>
                    <button onclick="redirect:/mypage.do" class="back-button">마이페이지 돌아가기</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
