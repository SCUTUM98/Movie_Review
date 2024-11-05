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
    <title>${actorData.actName } 배우 정보</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detail/detailStyle.css">
    
    <script type="text/javascript">
	    function sscrollLeft(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.movie-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	
	    function scrollRight(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.movie-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
    </script>
</head>
<body>
    <header class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="/main.do">홈</a></li>
                <li><a href="/movieList.do">영화</a></li>
                <li><a href="/seriesList.do">시리즈</a></li>
                <li><a href="/search.do">검색</a></li>
                <c:if test="${not empty username }"><li><a href="#">마이페이지</a></li></c:if>
                <c:if test="${empty username }"><li><a href="/home.do">로그인</a></li></c:if>
                <c:if test="${not empty username }"><li><a href="/logout">로그아웃</a></li></c:if>
            </ul>
        </nav>
    </header>
    
    <div class="container">
        <div class="left-panel">
		    <c:if test="${empty actorData.profilePath}">
		        <img src="${pageContext.request.contextPath}/images/profile.png" alt="${actorData.actName}" class="poster">
		    </c:if>
		    <c:if test="${not empty actorData.profilePath}">
		        <img src="http://image.tmdb.org/t/p/w780${actorData.profilePath}" alt="${actorData.actName}" class="poster">
		    </c:if>
		    <h2 class="series-title">${actorData.actName }</h2>
		
		    <c:if test="${not empty actorData.deathday}">
		        <h3>🎉  ${actorData.birthday }</h3>
		        <h3>🎗  ${actorData.deathday }</h3>
		        <h3>🎬 Retired</h3>
		        <h3>🏡  ${actorData.homeplace }</h3>
		    </c:if>
		    <c:if test="${empty actorData.deathday}">
		        <c:if test="${not empty actorData.birthday }"><h3>🎉  ${actorData.birthday }</h3></c:if>
		        <c:if test="${not empty actorData.status }"><h3>🎬 ${actorData.status }</h3></c:if>
		        <c:if test="${not empty actorData.homeplace }">
		        	<h3>🏡  ${actorData.homeplace }</h3>
		        	<iframe width="320" height="240" style="border:0" loading="lazy" allowfullscreen referrerpolicy="no-referrer-when-downgrade"
		            src="https://www.google.com/maps/embed/v1/place?key=${googleAPI }&q=${actorData.homeplace}">
					</iframe>
		        </c:if>
		    </c:if>
		
		    <div class="sns-section">
		        <c:if test="${not empty snsData.facebook || not empty snsData.instagram || not empty snsData.twitter || not empty snsData.tiktok || not empty snsData.youtube}">
		            <h2 class="series.title">SNS</h2>
		            <c:if test="${not empty snsData.facebook}">
		                <div class="sns-item">
		                    <img src="${pageContext.request.contextPath}/images/facebook.png" alt="Facebook" class="sns-logo">
		                    <a href="https://facebook.com/${snsData.facebook}" target="_blank">${snsData.facebook}</a>
		                </div>
		            </c:if>
		            <c:if test="${not empty snsData.instagram}">
		                <div class="sns-item">
		                    <img src="${pageContext.request.contextPath}/images/instagram.png" alt="Instagram" class="sns-logo">
		                    <a href="https://instagram.com/${snsData.instagram}" target="_blank">${snsData.instagram}</a>
		                </div>
		            </c:if>
		            <c:if test="${not empty snsData.twitter}">
		                <div class="sns-item">
		                    <img src="${pageContext.request.contextPath}/images/x.png" alt="Twitter" class="sns-logo">
		                    <a href="https://twitter.com/${snsData.twitter}" target="_blank">${snsData.twitter}</a>
		                </div>
		            </c:if>
		            <c:if test="${not empty snsData.tiktok}">
		                <div class="sns-item">
		                    <img src="${pageContext.request.contextPath}/images/tiktok.png" alt="TikTok" class="sns-logo">
		                    <a href="https://tiktok.com/@${snsData.tiktok}" target="_blank">${snsData.tiktok}</a>
		                </div>
		            </c:if>
		            <c:if test="${not empty snsData.youtube}">
		                <div class="sns-item">
		                    <img src="${pageContext.request.contextPath}/images/youtube.png" alt="YouTube" class="sns-logo">
		                    <a href="https://youtube.com/c/${snsData.youtube}" target="_blank">${snsData.youtube}</a>
		                </div>
		            </c:if>
		        </c:if>
		    </div>
		</div>

        
        <div class="right-panel">
        	<div class="overview-section">
            <h2>개요</h2>
            <p></p>
            </div>
            
            <div class="movie-section">
	        	<h2>출연작</h2>
		        <div class="movie-container">
		            <button type="button" class="scroll-btn" onclick="javascript:sscrollLeft(event)">◀</button>
		            <div class="movie-list">
		                <c:forEach items="${creditData}" var="credit">
		                    <div class="movie-item">
		                        <c:if test="${empty credit.poster_path}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:movieSelect('${credit.id }')" alt="${credit.title}" class="movie-poster">
		                        </c:if>
		                        <c:if test="${not empty credit.poster_path}">
		                            <img src="http://image.tmdb.org/t/p/w780${credit.poster_path}" onclick="javascript:movieSelect('${credit.id }')" alt="${credit.title}" class="movie-poster">
		                        </c:if>
		                        <div class="movie-info">
		                            <p class="movie-name">${credit.title}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn" onclick="javascript:scrollRight(event)">▶</button>
		        </div>
	    	</div>
	    	
	    	<div class="news-section">
			    <h2>NEWS</h2>
			    <div class="table-horizontal hover">
			        <table>
			            <colgroup>
			                <col style="width:10px;"> <!-- 번호 열 -->
			                <col style="width:300px;"> <!-- 제목 열 -->
			                <col style="width:100px;"> <!-- 게시일 열 -->
			            </colgroup>
			            <thead>
			                <tr>
			                    <th scope="col">번호</th>
			                    <th scope="col">제목</th>
			                    <th scope="col">게시일</th>
			                </tr>
			            </thead>
			            <tbody>
			                <c:if test="${empty newsData }">
			                    <tr>
			                        <td colspan="3" style="text-align: center;">관련 뉴스가 없습니다.</td>
			                    </tr>
			                </c:if>
			                <c:forEach items="${newsData }" var="news" varStatus="status">
			                    <tr>
			                        <td>${status.index + 1}</td>
			                        <td><a href="${news.link}" target="_blank">${news.title }</a></td>
			                        <td><c:out value="${news.pubDate }"/></td>
			                    </tr>
			                </c:forEach>
			            </tbody>
			        </table>
			    </div>
			</div>

        </div>
    </div>
    <script src="script.js"></script>
</body>

<footer>
	<div class="row" style="padding-top: 60px; clear: both;">
		<div class="col-md-12 text-center"><p><small>&copy; 𝓕𝓸𝓻 𝓶𝔂 𝓸𝔀𝓷 𝓗𝓪𝓹𝓹𝓲𝓷𝓮𝓼𝓼</small></p></div>
	</div>
</footer>
</html>

<%    
response.setHeader("Cache-Control","no-store");    
response.setHeader("Pragma","no-cache");    
response.setDateHeader("Expires",0);    
if (request.getProtocol().equals("HTTP/1.1"))  
        response.setHeader("Cache-Control", "no-cache");  
%>
