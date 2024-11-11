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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
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
	    function warningAlert(msg){
	    	alert(msg);
	    	document.reviewForm.action = "<c:url value='redirect:/localDetail.do'/>";
	    	document.reviewForm.submit();
	    }
	    function reviewSubmit(){
	    	console.log("review submit clicked");
	    	document.reviewForm.action = "<c:url value='/addActorReview.do'/>";
	    	document.reviewForm.submit();
	    }
	    function checkMovie(id){
	    	console.log(id);
	    	
	    	var id = id;
	    	$.ajax({
	    		url: './movieCheck.do',
	    		type: 'post',
	    		data: {id:id},
	    		dataType: 'json',
	    		success: function(response) {
	    			console.log("서버 응답:", response); 
	                if (response.cnt === 0) { 
	                    movieSelect(id);
	                } else { 
	                    localMovieSelect(id);
	                }
	    		},
	    		error: function() {
	                alert("에러입니다");
	            }
	    	});
	    }
	    function movieSelect(id) {
			console.log(id);
	    	document.movieForm.id.value = id;
	       	document.movieForm.action = "<c:url value='/detail.do'/>";
	       	document.movieForm.submit();
	       	}
		function localMovieSelect(id) {
			console.log(id);
			document.movieForm.id.value = id;
			document.movieForm.action = "<c:url value='/localDetail.do'/>";
			document.movieForm.submit();
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
                <c:if test="${not empty username }"><li><a href="/mypage.do">마이페이지</a></li></c:if>
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
            <div class="movie-section">
	        	<h2>출연작</h2>
	        	<form:form name="movieForm" method="post">
		          <input type="hidden" name="id" value="">
		        <div class="movie-container">
		            <button type="button" class="scroll-btn" onclick="javascript:sscrollLeft(event)">◀</button>
		            
		            <div class="movie-list">
		            	
		                <c:forEach items="${creditData}" var="credit">
		                    <div class="movie-item">
		                        <c:if test="${empty credit.poster_path}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="checkMovie('${credit.id }')" alt="${credit.title}" class="movie-poster">
		                        </c:if>
		                        <c:if test="${not empty credit.poster_path}">
		                            <img src="http://image.tmdb.org/t/p/w780${credit.poster_path}" onclick="checkMovie('${credit.id }')" alt="${credit.title}" class="movie-poster">
		                        </c:if>
		                        <div class="movie-info">
		                            <p class="movie-name">${credit.title}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		                
		            </div>
		            
		            <button type="button" class="scroll-btn" onclick="javascript:scrollRight(event)">▶</button>
		        </div>
		        </form:form>
	    	</div>
	    	
	    	<div class="review-section">
		    <h2>응원</h2>
		    
		    <div class="review-input">
		        <form:form action="addActorReview.do" name="reviewForm" role="form" method="post">
		            <div class="input-group">
		            	<input type="hidden" name="actorId" value="${actorData.actorId }">
		            	<input type="hidden" name="userId" value="${username }">
		            	<c:if test="${not empty username }">
			                <textarea name="detail" placeholder="응원 글을 작성해주세요." maxlength="1000" class="review-textarea"></textarea>
			                <div class="btn-rating">
			                	
				                <button type="submit" onclick="javascript:reviewSubmit()" class="review-button">등록</button>
			                </div>
			            </c:if>
			            <c:if test="${empty username }">
			                <textarea name="detail" placeholder="로그인 후 댓글을 작성할 수 있습니다." maxlength="1000" class="review-textarea" readonly></textarea>
			                <div class="btn-rating">
				                <button type="submit" onclick="javascript:warningAlert('로그인 후 댓글을 작성할 수 있습니다.')" class="review-button">등록</button>
			                </div>
			            </c:if>
		            </div>
		        </form:form>
		    </div>
		
		    <div class="review-list">
			    <c:forEach items="${reviews}" var="review">
			        <div class="review-item">
			            <div class="review-header">
			                <span class="review-author">${review.userId}</span>
			                <span class="review-date">${review.submitTime}</span>
			            </div>
			            <div class="review-content">
			                <p>${review.detail}</p>
			            </div>
			        </div>
			    </c:forEach>
			</div>
	    	
	    	<div class="news-section">
			    <h2>NEWS</h2>
			    <div class="table-horizontal hover">
			        <table>
			            <colgroup>
			                <col style="width:10px;"> <!-- 번호 열 -->
			                <col style="width:300px;"> <!-- 제목 열 -->
			            </colgroup>
			            <thead>
			                <tr>
			                    <th scope="col" style="text-align: center;">번호</th>
			                    <th scope="col" style="text-align: center;">제목</th>
			                </tr>
			            </thead>
			            <tbody>
			                <c:if test="${empty newsData }">
			                    <tr>
			                        <td colspan="2" style="text-align: center;">관련 뉴스가 없습니다.</td>
			                    </tr>
			                </c:if>
			                <c:forEach items="${newsData }" var="news" varStatus="status">
			                    <tr>
			                        <td style="text-align: center;">${status.index + 1}</td>
			                        <td><a href="${news.link}" target="_blank">${news.title }</a></td>
			                    </tr>
			                </c:forEach>
			            </tbody>
			        </table>
			    </div>
			</div>

        </div>
    </div>
</body>

<!-- <footer>
	<div class="row" style="padding-top: 60px; clear: both;">
		<div class="col-md-12 text-center"><p><small>&copy; 𝓕𝓸𝓻 𝓶𝔂 𝓸𝔀𝓷 𝓗𝓪𝓹𝓹𝓲𝓷𝓮𝓼𝓼</small></p></div>
	</div>
</footer> -->
</html>

<%    
response.setHeader("Cache-Control","no-store");    
response.setHeader("Pragma","no-cache");    
response.setDateHeader("Expires",0);    
if (request.getProtocol().equals("HTTP/1.1"))  
        response.setHeader("Cache-Control", "no-cache");  
%>
