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
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function updateEmail(){
            console.log("Email Update Btn clicked");
            document.updateForm.action = "<c:url value='/updateEmail.do'/>";
            document.updateForm.submit(); // 수정: submit 메서드 호출
        }
        
        function updatePass(){
            console.log("Password Update Btn clicked");
            if (validatePassword()) { // 비밀번호 유효성 검사
                document.updateForm.action = "<c:url value='/updatePass.do'/>";
                document.updateForm.submit(); // 수정: submit 메서드 호출
            }
        }
        
        function checkEmail() {
            var email = $('#email').val(); // id 값 저장
            $.ajax({
                url: './emailCheck.do', // 요청 URL
                type: 'post', // POST 방식
                data: { email: email }, // 전송할 데이터
                dataType: 'json', // 응답 데이터 형식
                success: function(response) { // 서버 응답 처리
                    console.log("서버 응답:", response); // 전체 응답 로그 확인
                    if (response.cnt === 0) { // cnt가 0이면 사용 가능한 아이디
                        $('.email_ok').css("display", "inline-block");
                        $('.email_already').css("display", "none");
                    } else { // cnt가 0이 아니면 이미 존재하는 아이디
                        $('.email_already').css("display", "inline-block");
                        $('.email_ok').css("display", "none");
                        alert("이메일을 다시 입력해주세요");
                        $('#email').val('');
                    }
                },
                error: function() {
                    alert("에러입니다");
                }
            });
        }
        
        function deleteAcc() {
            var id = $('#id').val();
            if (confirm("계정을 삭제하시겠습니까?\n탈퇴이후 삭제된 데이터는 복구되지 않습니다.")){
            	$.ajax({
                    url: './deleteAcc.do',
                    type: 'post',
                    data: { id: id },
                    dataType: 'json',
                    success: function(response) {
                        console.log("서버 응답:", response);
                        if (response.result === 0) {
                        	alert("서버 통신에 에러가 발생했습니다.\n잠시후 다시 시도해 주세요.");
                        } else {
                        	alert("계정이 삭제되었습니다.\n감사합니다.");
                        	self.location.href="/logout";
                        }
                    },
                    error: function() {
                        alert("서버 통신에 에러가 발생했습니다.");
                    }
                });
            }
            
        }

        function validatePassword() {
            var password = $('#pass').val();
            var confirmPassword = $('#confirmPassword').val();
            var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/; // 최소 8자, 대문자, 소문자, 숫자, 특수문자 포함 확인

            if (!password || !confirmPassword) {
                alert("비밀번호를 입력해주세요.");
                return false;
            }
            if(!confirmPassword) {
            	alert("비밀번호를 확인해주세요.");
            	return false;
            }

            if (!passwordPattern.test(password)) {
                alert("비밀번호는 대문자, 소문자, 숫자 및 특수문자를 포함해야 하며 최소 8자 이상이어야 합니다.");
                return false;
            }

            if (password !== confirmPassword) {
                alert("비밀번호가 일치하지 않습니다.");
                return false;
            }

            return true;
        }
    </script>
    <style>
        .email_ok {
            color: #008000;
            display: none;
            width: auto;
        }
        
        .email_already {
            color: #6A82FB; 
            display: none;
            width: auto;
        }
    </style>
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
                    <h1>회원정보 수정</h1>
                    <form action="" name="updateForm" method="post">
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
                            <input type="email" id="email" name="email" value="${userInfo.email }" onchange="checkEmail()">
                            <span class="email_ok">사용 가능한 이메일입니다.</span>
                            <span class="email_already">누군가 이 이메일을 사용하고 있어요.</span>
                            <button type="button" onclick="updateEmail()">이메일 수정</button> <!-- type="submit" -> type="button"으로 수정 -->
                        </div>
                        <div class="input-group">
                            <label for="password">비밀번호</label>
                            <input type="password" id="pass" name="pass" value="">
                        </div>
                        <div class="input-group">
                            <label for="confirmPassword">비밀번호 확인</label>
                            <input type="password" id="confirmPassword" name="confirmPassword">
                        </div>
                        <button type="button" onclick="updatePass()">비밀번호 변경</button>
                        <button type="button" onclick="deleteAcc()">회원 탈퇴</button>
                    </form>
                    <button onclick="location.href='/mypage.do'" class="back-button">마이페이지 돌아가기</button> <!-- 수정: redirect 기능 변경 -->
                </div>
            </div>
        </div>
    </div>
</body>
</html>
