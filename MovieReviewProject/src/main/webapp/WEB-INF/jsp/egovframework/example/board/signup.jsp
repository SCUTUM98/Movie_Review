<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>회원가입</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>

    <script>
        function validateForm() {
            var id = document.getElementById("id").value;
            var email = document.getElementById("email").value;
            var password = document.getElementById("pass").value;
            var rePassword = document.getElementById("re-password").value;

            if (id === "") {
                alert("아이디를 입력하세요.");
                return false;
            }

            if (email === "") {
                alert("이메일을 입력하세요.");
                return false;
            }

            // 비밀번호 규칙 확인
            var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/; // 대소문자 + 숫자 + 특수기호 + 최소 8자
            if (!passwordPattern.test(password)) {
                alert("비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.");
                return false;
            }

            // 비밀번호와 비밀번호 확인란 일치 확인
            if (password !== rePassword) {
                alert("비밀번호와 비밀번호 확인란이 일치하지 않습니다.");
                return false;
            }

            return true; // 모든 검증 통과 시 true 반환
        }
    </script>
    <script>
	    function checkId() {
	        var id = $('#id').val(); // id 값 저장
	        $.ajax({
	            url: './idCheck.do', // 요청 URL
	            type: 'post', // POST 방식
	            data: { id: id }, // 전송할 데이터
	            dataType: 'json', // 응답 데이터 형식
	            success: function(response) { // 서버 응답 처리
	                console.log("서버 응답:", response); // 전체 응답 로그 확인
	                if (response.cnt === 0) { // cnt가 0이면 사용 가능한 아이디
	                    $('.id_ok').css("display", "inline-block");
	                    $('.id_already').css("display", "none");
	                } else { // cnt가 0이 아니면 이미 존재하는 아이디
	                    $('.id_already').css("display", "inline-block");
	                    $('.id_ok').css("display", "none");
	                    alert("아이디를 다시 입력해주세요");
	                    $('#id').val('');
	                }
	            },
	            error: function() {
	                alert("에러입니다");
	            }
	        });
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
	</script>

	
	<style>
		.id_ok{
		color:#008000;
		display: none;
		}
		
		.id_already{
		color:#6A82FB; 
		display: none;
		}
		
		.email_ok{
		color:#008000;
		display: none;
		}
		
		.email_already{
		color:#6A82FB; 
		display: none;
		}
	</style>
</head>
<body>
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <!-- Start Sign In Form -->
            <form action="insertMember.do" role="form" class="fh5co-form animate-box" data-animate-effect="fadeIn" method="post" onsubmit="return validateForm();">
                <h2>Sign Up</h2>
                <div class="form-group">
                    <label for="id" class="sr-only">ID</label>
                    <input type="text" class="form-control" id="id" name="id" placeholder="ID" autocomplete="off" onchange="checkId()" required>
                    <span class="id_ok">사용 가능한 아이디입니다.</span>
					<span class="id_already">누군가 이 아이디를 사용하고 있어요.</span>
                </div>
                <div class="form-group">
                    <label for="name" class="sr-only">Name</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="이름" autocomplete="off" required>
                </div>
                <div class="form-group">
                    <label for="email" class="sr-only">Email</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="이메일" autocomplete="off" onchange="checkEmail()" required>
                    <span class="email_ok">사용 가능한 이메일입니다.</span>
					<span class="email_already">누군가 이 이메일을 사용하고 있어요.</span>
                </div>
                <div class="form-group">
                    <label for="password" class="sr-only">Password</label>
                    <input type="password" class="form-control" id="pass" name="pass" placeholder="비밀번호" autocomplete="off" required>
                </div>
                <div class="form-group">
                    <label for="re-password" class="sr-only">Re-type Password</label>
                    <input type="password" class="form-control" id="re-password" placeholder="비밀번호 확인" autocomplete="off" required>
                </div>
                <div class="form-group">
                    <p>회원이신가요? <a href="/home.do">로그인</a></p>
                </div>
                <div class="form-group">
                    <input type="submit" value="Sign Up" class="btn btn-primary">
                </div>
            </form>
            <!-- END Sign In Form -->
        </div>
    </div>
    <div class="row" style="padding-top: 60px; clear: both;">
        <div class="col-md-12 text-center"><p><small>&copy; 𝓕𝓸𝓻 𝓶𝔶 𝓸𝔀𝓷 𝓗𝓪𝓹𝓹𝓲𝓷𝓮𝓼𝓼 </small></p></div>
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
