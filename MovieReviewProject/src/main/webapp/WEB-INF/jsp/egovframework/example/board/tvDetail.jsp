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
    <title>Film Report: ${detailData.name }</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main/detailStyle.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script type="text/javascript">
	    function act_scrollLeft(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.cast-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	
	    function scrollRight(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.cast-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
	    
	    function rec_scrollLeft(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.rec-list');
	        container.scrollBy({
	            left: -150,
	            behavior: 'smooth'
	        });
	    }
	
	    function rec_scrollRight(event) {
	    	event.preventDefault();
	        const container = document.querySelector('.rec-list');
	        container.scrollBy({
	            left: 150,
	            behavior: 'smooth'
	        });
	    }
	    
	    function moveToDetail(id){
	    	document.listForm.id.value = id;
	    	console.log(document.listForm.movieId.value);
	    	document.listForm.action = "<c:url value='/detail.do'/>";
	    	document.listForm.submit();
	    }
	    
	    function moveToLocalDetail(id){
	    	document.listForm.id.value = id;
	    	console.log(document.listForm.id.value);
	    	document.listForm.action = "<c:url value='/localDetail.do'/>";
	    	document.listForm.submit();
	    }
	    
	    function moveToActorDetail(id){
	    	alert('프로그램 등록 후 이용해 주세요.');
	    }
	    
	    function seriesSubmit(id){
	    	document.listForm.programId.value = id;
	    	document.listForm.action = "<c:url value='/addTvSeries.do'/>";
	    	document.listForm.submit();
	    }
	    
	    function needLogin(){
	    	alert('로그인 후 사용가능합니다.');
	    }
    </script>
    <script type="text/javascript">
	    function toggleSeasons() {
	        const allSeasons = document.querySelectorAll('.season-item');
	        const showMoreBtn = document.getElementById('showMoreBtn');
	        console.log("btn clicked"); 
	        
	        let allVisible = true;
	        allSeasons.forEach((season, index) => {
	        	console.log(season.style.display);
	            if (season.style.display === 'none' || season.style.display === '') {
	            	season.style.display = 'flex';
	                showMoreBtn.innerText = 'HIDE';
	            } else {
	            	season.style.display = 'none';
	                showMoreBtn.innerText = 'MORE';
	            }
	        });
	        
	        console.log(allVisible);
	    }
	</script>

    
</head>
<body>
    <div class="header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Platform Logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="/main.do">홈</a></li>
                <li><a href="/main.do">영화</a></li>
                <li><a href="/seriesList.do">드라마</a></li>
                <li><a href="/seriesList.do">TV 프로그램</a></li>
                <li><a href="/seriesList.do">K-컬쳐</a></li>
                <li><a href="/search.do">검색</a></li>
                <c:if test="${not empty username }"><li><a href="/mypage.do">마이페이지</a></li></c:if>
                <c:if test="${empty username }"><li><a href="/home.do">로그인</a></li></c:if>
                <c:if test="${not empty username }"><li><a href="/logout">로그아웃</a></li></c:if>
            </ul>
        </nav>
    </div>
    
    <form action="addMovie.do" id="listForm" name="listForm" method="post">
    	<input type="hidden" name="programId" value="${detailData.id }">
        <input type="hidden" name="programName" value="${detailData.name }">
        <input type="hidden" name="programOriginalName" value="${detailData.originalName }">
        <input type="hidden" name="programOverview" value="${detailData.overview }">
        <input type="hidden" name="programBackdropPath" value="${detailData.backdropPath }">
        <input type="hidden" name="programPosterPath" value="${detailData.posterPath }">
        <input type="hidden" name="programAdult" value="${detailData.adult }">
        <input type="hidden" name="programGenre" value="${detailData.genre }">
        <input type="hidden" name="programFirstAirDate" value="${detailData.firstAirDate }">
        <input type="hidden" name="programOriginCountry" value="${detailData.originCountry }">
        <c:forEach items="${seasonList}" var="season">
		    <input type="hidden" name="seasonId" value="${season.id }">
	        <input type="hidden" name="seriesId" value="${detailData.id }">
	        <input type="hidden" name="seasonName" value="${season.name}">
	        <input type="hidden" name="seasonAirDate" value="${season.air_date}">
	        <input type="hidden" name="seasonEpisodeCount" value="${season.episode_count}">
	        <input type="hidden" name="seasonPosterPath" value="${season.poster_path}">
		</c:forEach>
       
        <div class="content-detail" style="background-image: url('http://image.tmdb.org/t/p/w1280${detailData.backdropPath }');">
            <div class="overlay">
                <div class="info">
                	<c:if test="${empty detailData.posterPath }">
                		<img src="${pageContext.request.contextPath}/images/profile.png" alt="Movie Poster" class="poster">
                	</c:if>
                	<c:if test="${not empty detailData.posterPath }">
                		<img src="http://image.tmdb.org/t/p/w780${detailData.posterPath }" alt="Movie Poster" class="poster">
                	</c:if>
                    
                    <div class="details">
                        <h1>${detailData.name }</h1>
                        <h2><strong>  </strong></h2>
                        <p>${detailData.overview }</p>
                        <p><strong>방영 시작일: </strong>${detailData.firstAirDate }</p>
                        <p><strong>장르: </strong>
                            <c:forEach items="${detailData.genre}" var="genre" varStatus="status">
                                ${genre}<c:if test="${!status.last}">, </c:if>
                            </c:forEach>
                        </p>
                    </div>
                </div>
                <c:if test="${not empty username }">
                	<button type="button" id="submitBtn" class="register-button" onclick="javascript:seriesSubmit('${detailData.id}')">시리즈 등록하기</button>
                </c:if>
                <c:if test="${empty username }">
                	<button type="button" id="submitBtn" class="register-button" onclick="javascript:needLogin()">시리즈 등록하기</button>
                </c:if>
            </div>
        </div>
        
        <c:if test="${not empty videoData.key }">
		    <div class="video-section">
		        <h2>예고편</h2>
		        <div class="video-container">
		            <div class="video-item">
		                <iframe width="720" height="405" src="https://www.youtube.com/embed/${videoData.key }" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
		            </div>
		            <div class="background-image" style="background-image: url('http://image.tmdb.org/t/p/w1280${detailData.backdropPath}');"></div>
		        </div>
		    </div>
		</c:if>
       
        <div class="cast-section">
            <h2>출연 배우</h2>
            <div class="cast-container">
                <button type="button" class="scroll-btn left" onclick="javascript:act_scrollLeft(event)">◀</button>
                <div class="cast-list">
                	<input type="hidden" name="actorId" value="">
                    <c:forEach items="${actorData}" var="actor">
                        <div class="cast-item">
                            <c:if test="${empty actor.profile_path}">
                                <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:moveToActorDetail('${actor.id}')"  alt="${actor.original_name}" class="actor-photo">
                            </c:if>
                            <c:if test="${not empty actor.profile_path}">
                                <img src="http://image.tmdb.org/t/p/w500${actor.profile_path}" onclick="javascript:moveToActorDetail('${actor.id}')" alt="${actor.original_name}" class="actor-photo">
                            </c:if>
                            <div class="actor-info">
                                <p class="actor-name">${actor.name}</p>
                                <p class="actor-role">${actor.character}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <button type="button" class="scroll-btn right" onclick="javascript:scrollRight(event)">▶</button>
            </div>
        </div>
        <div class="season-section">
		    <h2>Season</h2>
		    <div class="season-list">
		        <c:forEach items="${seasonList }" var="season" varStatus="status">
		            <div class="season-item" id="season-item-${status.index}" style="${status.index < 3 ? '' : 'display: none !important;'}">
		                <div class="season-poster-section">
		                    <c:if test="${empty season.poster_path}">
		                        <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:needLogin()" alt="${season.name}" class="season-poster">
		                    </c:if>
		                    <c:if test="${not empty season.poster_path}">
		                        <img src="http://image.tmdb.org/t/p/w780${season.poster_path}" onclick="javascript:needLogin()" alt="${season.name}" class="season-poster">
		                    </c:if>
		                </div>
		                <div class="season-title-section">
		                    <p class="season-title">${season.name }</p>
		                    <p class="season-overview">${season.overview }</p>
		                    <c:if test="${not empty season.air_date}">
		                        <p class="season-air-time">방영 시작: ${season.air_date }</p>
		                    </c:if>
		                </div>
		                <div class="season-button-section">
		                    <button type="button" id="seasonDetail-btn" class="season-button" onclick="javascript:needLogin()">시즌 상세 보기</button>
		                </div>
		            </div>
		        </c:forEach>
		    </div>
		    
		    <c:if test="${seasonList.size() > 3}">
		        <div id="more-seasons" class="hidden-seasons" style="display: none;">
		            <c:forEach items="${seasonList}" var="season" varStatus="status">
		                <c:if test="${status.index >= 3}">
		                    <div class="hidden-item">
		                        <div class="season-poster-section">
		                            <c:if test="${empty season.poster_path}">
		                                <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:needLogin()" alt="${season.name}" class="season-poster">
		                            </c:if>
		                            <c:if test="${not empty season.poster_path}">
		                                <img src="http://image.tmdb.org/t/p/w780${season.poster_path}" onclick="javascript:needLogin()" alt="${season.name}" class="season-poster">
		                            </c:if>
		                        </div>
		                        <div class="season-title-section">
		                            <p class="season-title">${season.name }</p>
		                            <p class="season-overview">${season.overview }</p>
		                            <c:if test="${not empty season.air_date}">
		                                <p class="season-air-time">방영 시작: ${season.air_date }</p>
		                            </c:if>
		                        </div>
		                        <div class="season-button-section">
		                            <button type="button" id="seasonDetail-btn" class="season-button" onclick="javascript:needLogin()">시즌 상세 보기</button>
		                        </div>
		                    </div>
		                </c:if>
		            </c:forEach>
		        </div>
		        <button type="button" id="showMoreBtn" class="show-more-button" onclick="toggleSeasons()">MORE</button>
		    </c:if>
		</div>


        <div class="rec-section">
            <h2>추천 시리즈</h2>
            <div class="rec-container">
                <button type="button" class="scroll-btn left" onclick="javascript:rec_scrollLeft(event)">◀</button>
                <div class="rec-list">
                    <c:forEach items="${recommendData}" var="rec" >
                        <div class="cast-item">
                            <c:if test="${empty rec.poster_path}">
                                <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:moveToDetail('${rec.id }')" alt="${rec.name}" class="actor-photo">
                            </c:if>
                            <c:if test="${not empty rec.poster_path}">
                                <img src="http://image.tmdb.org/t/p/w780${rec.poster_path}" onclick="javascript:moveToDetail('${rec.id }')" alt="${rec.name}" class="actor-photo">
                            </c:if>
                            <div class="actor-info">
                                <p class="actor-name">${rec.name}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <button type="button" class="scroll-btn right" onclick="javascript:rec_scrollRight(event)">▶</button>
            </div>
        </div>
    </form>

    <script type="text/javascript">
        function submitForm(action) {
            document.getElementById('actionType').value = action;
            document.mainForm.submit();
        }
    </script>
</body>

</html>

<%    
response.setHeader("Cache-Control","no-store");    
response.setHeader("Pragma","no-cache");    
response.setDateHeader("Expires",0);    
if (request.getProtocol().equals("HTTP/1.1"))  
        response.setHeader("Cache-Control", "no-cache");  
%>

