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
    <title>Film Report 인증</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/verify/main.css">
    
</head>
<body>
    <header class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="로고" class="logo">
    </header>

    <div class="content-detail">
        <div class="overlay">
            <div class="auth-section">
                <h2>인증하기</h2>
                <form name="verify" action="verifyTest.do" method="post">
                    <input type="email" name="email" placeholder="이메일" class="input-field">
                    <input type="text" name="mailKey" placeholder="인증번호" class="input-field">
                    <button class="register-button">제출</button>
                </form>
            </div>
        </div>
    </div>
    <script>
        window.onload = function() {
            var errorMessage = "${errorMessage}";
            if (errorMessage) {
                alert(errorMessage);
                errorMessage = null;
            }
        };
    </script>
</body>
</html>
