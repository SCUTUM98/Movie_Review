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
    <title>영화 컬렉션</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detail/detailStyle.css">
</head>
<body>
    <header class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="/main.do">홈</a></li>
                <li><a href="#">영화</a></li>
                <li><a href="#">시리즈</a></li>
                <li><a href="/search.do">검색</a></li>
                <li><a href="#">설정</a></li>
            </ul>
        </nav>
    </header>
    
    <div class="container">
        <div class="left-panel">
            <img src="movie-poster.jpg" alt="영화 포스터" class="poster">
            <h2 class="series-title">시리즈 제목</h2>
        </div>
        <div class="right-panel">
            <h2>개요</h2>
            <p>여기에 영화 시리즈의 개요가 들어갑니다. 이곳에 스토리라인이나 주요 테마 등을 설명할 수 있습니다.</p>
            
            <h3>배우 정보</h3>
            <ul>
                <li>배우 1</li>
                <li>배우 2</li>
                <li>배우 3</li>
            </ul>
            
            <h3>시리즈에 포함된 영화들</h3>
            <div class="included-movies">
                <img src="included-movie1.jpg" alt="포스터1" class="included-poster">
                <img src="included-movie2.jpg" alt="포스터2" class="included-poster">
                <img src="included-movie3.jpg" alt="포스터3" class="included-poster">
            </div>
        </div>
    </div>
    <script src="script.js"></script>
</body>
</html>
