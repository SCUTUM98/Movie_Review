<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Film Report 아이디 찾기</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Template by FreeHTML5.co" />
	<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
	<meta name="author" content="FreeHTML5.co" />

	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
	<link rel="shortcut icon" href="favicon.ico">

	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}css/login/animate.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}css/login/style.css">

	<!-- Modernizr JS -->
	<script src="${pageContext.request.contextPath}/js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->
		
	<!-- jQuery -->
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<!-- Placeholder -->
	<script src="${pageContext.request.contextPath}/js/jquery.placeholder.min.js"></script>
	<!-- Waypoints -->
	<script src="${pageContext.request.contextPath}/js/jquery.waypoints.min.js"></script>
	<!-- Main JS -->
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
	<script>
		function findPass(){
			var id = $('#id').val();
			var email = $('#email').val();
			console.log(name);
			console.log(email);
			
			$.ajax({
				url: './findPassResult.do',
				type: 'post',
				data: {id:id, email:email},
				dataType: 'json',
				success: function(response) {
					console.log("서버 응답:", response);
					if (response.cnt === 0){
						alert("일치하는 정보가 없습니다.")
					} else {
						alert("임시 비밀번호가 등록된 이메일로 전송되었습니다.");
						window.location.href = '/home.do';
					}
				},
				error: function(xhr, status, error) {
		            console.error("AJAX 요청 실패:", status, error); // 에러 로그 추가
		            alert("서버 오류가 발생했습니다.");
		        }
			})
		}
	</script>

	</head>
	<body>

		<div class="container">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					

					<!-- Start Sign In Form -->
					<form action="#" class="fh5co-form animate-box" data-animate-effect="fadeIn">
						<h2>비밀번호 찾기</h2>
						<div class="form-group">
							<label for="name" class="sr-only">Id</label>
							<input class="form-control" id="id" placeholder="id" autocomplete="off" required>
						</div>
						<div class="form-group">
							<label for="email" class="sr-only">Email</label>
							<input type="email" class="form-control" id="email" placeholder="Email" autocomplete="off" required>
						</div>
						<div class="form-group">
							<p><a href="/home.do">로그인</a> or <a href="/registerMember.do">회원가입</a></p>
						</div>
						<div class="form-group">
							<p><a href="/findId.do">아이디를 잊으셨나요?</a></p>
						</div>
						<div class="form-group">
							<input type="button" value="이메일 인증하기" class="btn btn-primary" onclick="findPass()">
						</div>
					</form>
					<!-- END Sign In Form -->


				</div>
			</div>
			<div class="row" style="padding-top: 60px; clear: both;">
				<div class="col-md-12 text-center"><p><small>&copy;𝓕𝓸𝓻 𝓶𝔶 𝓸𝔀𝓷 𝓗𝓪𝓹𝓹𝓲𝓷𝓮𝓼𝓼</small></p></div>
			</div>
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