<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>검색결과</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script defer src="https://use.fontawesome.com/releases/v5.1.0/js/all.js" integrity="sha384-3LK/3kTpDE/Pkp8gTNp2gR/2gOiwQ6QaO7Td0zV76UFJVhqLl4Vl3KL1We6q6wR9" crossorigin="anonymous"></script>

  <script src="main.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search/main.css">
  
  <script type="text/javascript">
		function movieSelect(id) {
			console.log(id);
	    	document.listForm.id.value = id;
	       	document.listForm.action = "<c:url value='/detail.do'/>";
	       	document.listForm.submit();
	       	}
		function localMovieSelect(id) {
			console.log(id);
			document.searchForm.id.value = id;
			document.searchForm.action = "<c:url value='/localDetail.do'/>";
			document.searchForm.submit();
		}
		function actorSelect(id) {
			console.log(id);
			document.actorForm.actorId.value = id;
			document.actorForm.action = "<c:url value='/actorDetail.do'/>";
			document.actorForm.submit();
		}
  </script>
</head>
<body>
  	<div class="header">
    <!-- HEADER -->
	    <header>
	        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Platform Logo" class="logo">
	        <nav class="main-nav">
		        <a href="/main.do">홈</a>
	            <a href="#">영화</a>
	            <a href="#">시리즈</a>
	            <a href="/search.do">검색</a>
	            <a href="#">설정</a>
	        </nav>     
	    </header>
	    <!-- END OF HEADER -->
	</div>

    <div class="wrapper">
    
    <!-- MAIN CONTAINER -->
    <section class="main-container" >
      <div class="location" id="home">
          <h1 id="result">Search Result</h1>
          <form:form commandName="movieVO" name="searchForm" method="post">
          	  <input type="hidden" name="id" value="">
	          <div class="box">
		      	<c:forEach items="${searchList }" var="search" varStatus="status">
		      		<a href="javascript:localMovieSelect('${search.movieId}')"><img src="http://image.tmdb.org/t/p/w500${search.posterPath }" alt="${search.titleKr }" class="movie-poster"></a>
		      	</c:forEach>            
		      </div>
		  </form:form>
      </div>

      <h1 id="unregistered">Unregistered Movie</h1>
	  <form:form commandName="movieVO" name="tmdbForm" method="post">
		  <input type="hidden" name="id" value="">
		  <div class="box">
			 <c:forEach items="${resultData }" var="result" varStatus="status">
			 	<a href="javascript:movieSelect('${result.movieId}')"><img src="http://image.tmdb.org/t/p/w500${result.posterPath }" alt="${result.titleKr }" class="movie-poster"></a>
			 </c:forEach>
		  </div>    
	  </form:form>
	  
	  <h1 id="unregistered">Series</h1>
	  <form:form commandName="collectionVO" name="collectionForm" method="post">
		  <input type="hidden" name="id" value="">
		  <div class="box">
			 <c:forEach items="${resultData }" var="result" varStatus="status">
			 	<a href="javascript:movieSelect('${result.movieId}')"><img src="http://image.tmdb.org/t/p/w500${result.posterPath }" alt="${result.titleKr }" class="movie-poster"></a>
			 </c:forEach>
		  </div>    
	  </form:form>
	  
	  <h1 id="unregistered">Actor</h1>
	  <form:form commandName="actorVO" name="actorForm" method="post">
		  <input type="hidden" name="actorId" value="">
		  <div class="box">
			 <c:forEach items="${actorList }" var="actor" varStatus="status">
			 	<a href="javascript:actorSelect('${actor.actorId}')"><img src="http://image.tmdb.org/t/p/w500${actor.profilePath }" alt="${actor.actName }" class="movie-poster"></a>
			 </c:forEach>
		  </div>    
	  </form:form>
		  
      <h1 id="trendingMovie">Trending Now</h1>
      <form:form commandName="movieVO" name="listForm" method="post">
      	  <input type="hidden" name="id" value="">
	      <div class="box">
	      	<c:forEach items="${suggestData }" var="trending" varStatus="status">
	      		<a href="javascript:movieSelect('${trending.movieId}')"><img src="http://image.tmdb.org/t/p/w500${trending.posterPath }" alt="${trending.titleKr }" class="movie-poster"></a>
	      	</c:forEach>            
	      </div>
	  </form:form>

    <!-- LINKS -->
    <section class="link">
      <div class="logos">
        <a href="#"><i class="fab fa-facebook-square fa-2x logo"></i></a>
        <a href="#"><i class="fab fa-instagram fa-2x logo"></i></a>
        <a href="#"><i class="fab fa-twitter fa-2x logo"></i></a>
        <a href="#"><i class="fab fa-youtube fa-2x logo"></i></a>
      </div>
      <div class="sub-links">
        <ul>
          <li><a href="#">Audio and Subtitles</a></li>
          <li><a href="#">Audio Description</a></li>
          <li><a href="#">Help Center</a></li>
          <li><a href="#">Gift Cards</a></li>
          <li><a href="#">Media Center</a></li>
          <li><a href="#">Investor Relations</a></li>
          <li><a href="#">Jobs</a></li>
          <li><a href="#">Terms of Use</a></li>
          <li><a href="#">Privacy</a></li>
          <li><a href="#">Legal Notices</a></li>
          <li><a href="#">Corporate Information</a></li>
          <li><a href="#">Contact Us</a></li>
        </ul>
      </div>
    </section>
    <!-- END OF LINKS -->

    <!-- FOOTER -->
    <footer>
      <p>&copy 1997-2018 Netflix, Inc.</p>
      <p>Carlos Avila &copy 2018</p>
    </footer>
  </div>
</body>
</html>