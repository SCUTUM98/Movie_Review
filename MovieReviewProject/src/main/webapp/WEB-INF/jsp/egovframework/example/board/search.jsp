<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Netflix</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search/style.css">
    <link rel="stylesheet" href="mediaquery.css">
    <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
    <script src="https://kit.fontawesome.com/bc3a1796c2.js" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="https://image.flaticon.com/icons/png/512/870/870910.ico" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.css" />
</head>
<body>

    <div class="navbar">
        <li class="logo"><img src="${pageContext.request.contextPath}/images/logo.png"></li>
        <li class="buttons">Sign In</li>
    </div>
    <div class="main">
        <div class="area">
            <h1>
                Unlimited movies, TV <br>shows, and more.
            </h1>
            <h3>Watch anywhere. Cancel anytime.</h3>

            <form action="result.do" method="post">
                <div class="search">
                    <input type="text" name="searchKeyword" class="box" placeholder="Search for a movie" required> 
                    <button type="submit" class="try">Search
                    	<i class="fas fa-chevron-right"></i>
                    </button>
                </div>
            </form>
            <h4>Ready to watch? Enter your email to create or access your account</h4>
        </div>
    </div>

    <div class="end">
        <h2>Netflix US</h2>
        <h2></h2>
    </div>
</body>
</html>
