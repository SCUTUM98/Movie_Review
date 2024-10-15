<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Movie Result</title>
</head>
<body>
    <h1>영화 검색 결과</h1>
    <c:if test="${not empty movieData}">
        <pre>${movieData}</pre> <!-- JSON 데이터 출력 -->
    </c:if>
    <c:if test="${empty movieData}">
        <pre>데이터 수신 오류</pre> <!-- JSON 데이터 출력 -->
    </c:if>
</body>
</html>
