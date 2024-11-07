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
    <title>Film Report: ${selectMovie.titleEn }</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main/detailStyle.css">
    
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
	    	console.log(document.listForm.id.value);
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
	    	document.listForm.actorId.value = id;
	    	console.log(document.listForm.actorId.value);
	    	document.listForm.action = "<c:url value='/actorDetail.do'/>";
	    	document.listForm.submit();
	    }
	    
	    function moveToSeries(id){
	    	document.listForm.collectionId.value = id;
	    	console.log(document.listForm.collectionId.value);
	    	document.listForm.action = "<c:url value='/seriesDetail.do'/>";
	    	document.listForm.submit();
	    }
	    function moveToUpdate(id){
	    	document.listForm.movieId.value = id;
	    	console.log(document.listForm.movieId.value);
	    	document.listForm.action = "<c:url value='/movieUpdate.do'/>";
	    	document.listForm.submit();
	    }
	    
	    function moveToProvider(provider, name){
	    	if (provider == 'Google Play Movies'){
	    		url = 'https://play.google.com/store/search?q=' + encodeURIComponent(name) + '&c=movies&hl=en&gl=KR';
	            console.log(url);
	            window.open(url);
	    		
	    	}
	    	if (provider == 'Disney Plus'){
	    		url = 'https://www.disneyplus.com/ko-kr';
	            console.log(url);
	            window.open(url);
	    	}
	    	if (provider == 'Netflix'){
	    		url = 'https://www.netflix.com/kr';
	            console.log(url);
	            window.open(url);
	    	}
	    	if (provider == 'Netflix basic with Ads'){
	    		url = 'https://www.netflix.com/kr';
	            console.log(url);
	            window.open(url);
	    	}
	    	if (provider == 'wavve'){
	    		url = 'https://www.wavve.com/search?searchWord=' + encodeURIComponent(name);
	            console.log(url);
	            window.open(url);
	    	}
	    	if (provider == 'Watcha'){
	    		url = 'https://watcha.com/search?query=' + encodeURIComponent(name) + '&domain=all';
	    		console.log(url);
	            window.open(url);
	    	}
	    	if (provider == 'Amazon Prime Video'){
	    		url = 'https://www.primevideo.com/-/ko/offers/nonprimehomepage/ref=dv_web_force_root?language=ko';
	    		console.log(url);
	    		window.open(url);
	    	}
	    	
	    }
	    
	    function reviewSubmit(){
	    	console.log("review submit clicked");
	    	document.reviewForm.action = "<c:url value='/addReview.do'/>";
	    	document.reviewForm.submit();
	    }
	    
	    function disliked(id){
	    	document.listForm.movieId.value = id;
	    	console.log(document.listForm.movieId.value);
	    	document.listForm.action = "<c:url value='/deleteLike.do'/>";
	    	document.listForm.submit();
	    }
	    
	    function liked(id){
	    	document.listForm.movieId.value = id;
	    	console.log(document.listForm.movieId.value);
	    	document.listForm.action = "<c:url value='/addLike.do'/>";
	    	document.listForm.submit();
	    }
	    function warningAlert(msg){
	    	alert(msg);
	    	document.reviewForm.action = "<c:url value='redirect:/localDetail.do'/>";
	    	document.reviewForm.submit();
	    }
	    
    </script>
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
                <c:if test="${not empty username }"><li><a href="/mypage.do">마이페이지</a></li></c:if>
                <c:if test="${empty username }"><li><a href="/home.do">로그인</a></li></c:if>
                <c:if test="${not empty username }"><li><a href="/logout">로그아웃</a></li></c:if>
            </ul>
        </nav>
    </div>
    
    <form action="" id="listForm" name="listForm" method="post">
    	<input type="hidden" name="id" value="">
    	<input type="hidden" name="movieId" value="">
    	<input type="hidden" name="collectionId" value="">
	    <div class="content-detail" style="background-image: url('http://image.tmdb.org/t/p/w1280${selectMovie.backdropPath }');">
            <div class="overlay">
                <div class="info">
				    <c:if test="${empty selectMovie.posterPath }">
				        <img src="${pageContext.request.contextPath}/images/profile.png" alt="Movie Poster" class="poster">
				    </c:if>
				    <c:if test="${not empty selectMovie.posterPath }">
				        <img src="http://image.tmdb.org/t/p/w780${selectMovie.posterPath }" alt="Movie Poster" class="poster">
				    </c:if>
				    <div class="details">
					    <h1>${selectMovie.titleEn }</h1>
					    <h2><strong>${selectMovie.tagline }</strong></h2>
					    <p>${selectMovie.overview }</p>
					    <p><strong>개봉일: </strong>${selectMovie.releaseDate }</p>
					    <p><strong>장르: </strong>
					        <script type="text/javascript">
					            var genreString = '${selectMovie.genreDB}';
					            genreString = genreString.replace(/[\[\]']/g, '');
					            var genres = genreString.split(",").map(function(item) {
					                return item.trim();
					            });
					            document.write(genres.join(", "));
					        </script>
					    </p>
					    
					    <div class="providers">
					        <div class="provider-category">
					            <c:if test="${not empty buyList}">
					            	<h2>구매</h2>
					            </c:if>
					            <c:forEach items="${buyList}" var="buyList">
					                <c:if test="${not empty buyList.provider_id}">
					                    <c:if test="${buyList.provider_name != 'Naver Store' }">
					                        <img src="http://image.tmdb.org/t/p/w780${buyList.logo_path}" onclick="javascript:moveToProvider('${buyList.provider_name}', '${selectMovie.titleEn }')" alt="${buyList.provider_name}" class="provider-icon">
					                    </c:if>
					                </c:if>
					            </c:forEach>
					        </div>
					        <div class="provider-category">
					            <c:if test="${not empty rentList }"><h2>대여</h2></c:if>
					            <c:forEach items="${rentList}" var="rentList">
					                <c:if test="${not empty rentList.provider_id}">
					                    <c:if test="${rentList.provider_name != 'Naver Store' }">
					                        <img src="http://image.tmdb.org/t/p/w780${rentList.logo_path}" onclick="javascript:moveToProvider('${rentList.provider_name}', '${selectMovie.titleEn }')" alt="${rentList.provider_name}" class="provider-icon">
					                    </c:if>
					                </c:if>
					            </c:forEach>
					        </div>
					        <div class="provider-category">
					            <c:if test="${not empty streamList }"><h2>스트리밍</h2></c:if>
					            <c:forEach items="${streamList}" var="streamList">
					                <c:if test="${not empty streamList.provider_id}">
					                    <c:if test="${streamList.provider_name != 'Naver Store' }">
					                        <img src="http://image.tmdb.org/t/p/w780${streamList.logo_path}" onclick="javascript:moveToProvider('${streamList.provider_name}', '${selectMovie.titleEn }')" alt="${streamList.provider_name}" class="provider-icon">
					                    </c:if>
					                </c:if>
					            </c:forEach>
					        </div>
					        
					        <c:if test="${not empty username }">
								<c:if test="${liked == 1 }">
									<div class="favorite-section">
									    <img id="favorite-icon" src="${pageContext.request.contextPath}/images/liked.png" alt="Favorite" class="favorite-icon" onclick="javascript:disliked('${selectMovie.movieId}')">
									</div>
								</c:if>
								<c:if test="${liked == 0 }">
									<div class="favorite-section">
									    <img id="favorite-icon" src="${pageContext.request.contextPath}/images/unliked.png" alt="Favorite" class="favorite-icon" onclick="javascript:liked('${selectMovie.movieId}')">
									</div>
								</c:if>
							</c:if>
					    </div>
					</div>
					<button type="submit" class="register-button" onclick="javascript:moveToUpdate('${selectMovie.movieId}')">영화 업데이트</button>
					
				</div>
			</div>
	    </div>
	    
	    <c:if test="${not empty videoData.key }">
		    <div class="video-section">
		        <h2>예고편</h2>
		        <div class="video-container">
		            <div class="video-item">
		                <iframe width="720" height="405" src="https://www.youtube.com/embed/${videoData.key }" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
		            </div>
		            <div class="background-image" style="background-image: url('http://image.tmdb.org/t/p/w1280${selectMovie.backdropPath}');"></div>
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
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:moveToActorDetail('${actor.id}')" alt="${actor.original_name}" class="actor-photo">
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
		</form>
		
		<div class="review-section">
		    <h2>리뷰</h2>
		    
		    <div class="review-input">
		        <form:form action="addReview.do" name="reviewForm" role="form" method="post">
		            <div class="input-group">
		            	<input type="hidden" name="movieId" value="${selectMovie.movieId }">
		            	<input type="hidden" name="userId" value="${username }">
		            	<c:if test="${not empty username }">
			                <textarea name="detail" placeholder="리뷰를 작성하세요..." maxlength="1000" class="review-textarea"></textarea>
			                <div class="btn-rating">
			                	<div class="rating-input">
				                    <input type="number" name="rate" min="1" max="5" placeholder="평점" class="rating-field"> / 5
				                </div>
				                <button type="submit" onclick="javascript:reviewSubmit()" class="review-button">등록</button>
			                </div>
			            </c:if>
			            <c:if test="${empty username }">
			                <textarea name="detail" placeholder="로그인 후 댓글을 작성할 수 있습니다." maxlength="1000" class="review-textarea" readonly></textarea>
			                <div class="btn-rating">
			                	<div class="rating-input">
				                    <input type="number" name="rate" min="1" max="5" placeholder="평점" class="rating-field" readonly> / 5
				                </div>
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
			            <div class="review-rating">
			                <c:if test="${review.rate == '1' }"><span>⭐</span></c:if>
		                    <c:if test="${review.rate == '2' }"><span>⭐⭐</span></c:if>
		                    <c:if test="${review.rate == '3' }"><span>⭐⭐⭐</span></c:if>
		                    <c:if test="${review.rate == '4' }"><span>⭐⭐⭐⭐</span></c:if>
		                    <c:if test="${review.rate == '5' }"><span>⭐⭐⭐⭐⭐</span></c:if>
			            </div>
			        </div>
			    </c:forEach>
			</div>

		
		    <div class="pagination">
		        <c:if test="${currentPage > 1}">
		            <a href="reviewList.do?page=${currentPage - 1}" class="page-link">◀ 이전</a>
		        </c:if>
		        <span>Page ${currentPage} of ${totalPages}</span>
		        <c:if test="${currentPage < totalPages}">
		            <a href="reviewList.do?page=${currentPage + 1}" class="page-link">다음 ▶</a>
		        </c:if>
		    </div>
		</div>

		<c:if test="${not empty collectionData.name }">
			<div class="collection-detail" style="background-image: url('http://image.tmdb.org/t/p/w1280${collectionData.backdropPath }');">
			    <div class="collection-overlay">
		            <div class="collection-info">
		            	<c:if test="${collectionData.posterPath == null}">
		            		<img src="${pageContext.request.contextPath}/images/profile.png" alt="${collectionData.name}" alt="Movie Poster" class="collection-poster">
		            	</c:if>
			            <c:if test="${not empty collectionData.posterPath}">
			                <img src="http://image.tmdb.org/t/p/w780${collectionData.posterPath}" alt="${collectionData.name}"alt="Movie Poster" class="collection-poster">
			            </c:if>
		                <div class="collection-details">
		                    <h1>${collectionData.name }</h1>
		                    <p>${collectionData.overview }</p>
		                </div>
		            </div>
		            <button class="register-button" onclick="javascript:moveToSeries('${collectionData.id}')">시리즈 보러가기</button>
		        </div>
			</div>
		</c:if>
		
		<div class="rec-section">
		    <h2>추천 영화</h2>
		    
		        <div class="rec-container">
		            <button type="button" class="scroll-btn left" onclick="javascript:rec_scrollLeft(event)">◀</button>
		            <div class="rec-list">
			            <c:forEach items="${notUniqueData}" var="dupRec">
		                <div class="cast-item">
		                    <c:if test="${empty dupRec.poster_path}">
		                        <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:moveToLocalDetail('${dupRec.id}')" alt="${dupRec.title}" class="actor-photo">
		                    </c:if>
		                    <c:if test="${not empty dupRec.poster_path}">
		                        <img src="http://image.tmdb.org/t/p/w780${dupRec.poster_path}" onclick="javascript:moveToLocalDetail('${dupRec.id}')" alt="${dupRec.title}" class="actor-photo">
		                    </c:if>
		                    <div class="actor-info">
		                        <p class="actor-name">${dupRec.title}</p>
		                    </div>
		                </div>
		            </c:forEach>
		                <c:forEach items="${recommendData}" var="rec">
		                    <div class="cast-item">
		                        <c:if test="${empty rec.poster_path}">
		                            <img src="${pageContext.request.contextPath}/images/profile.png" onclick="javascript:moveToDetail('${rec.id }')" alt="${rec.title}" class="actor-photo">
		                        </c:if>
		                        <c:if test="${not empty rec.poster_path}">
		                            <img src="http://image.tmdb.org/t/p/w500${rec.poster_path}" onclick="javascript:moveToDetail('${rec.id }')" alt="${r.title}" class="actor-photo">
		                        </c:if>
		                        <div class="actor-info">
		                            <p class="actor-name">${rec.title}</p>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
		            <button type="button" class="scroll-btn right" onclick="javascript:rec_scrollRight(event)">▶</button>
		        </div>
		    
		</div>
		
		<div class="update-section">
			<form:form action="movieUpdate.do" name="updateForm" method="post">
				<input type="hidden" name="movieId" value="">
				<button type="submit" class="update-Btn" onclick="javascript:moveToUpdate('${selectMovie.movieId}')">영화 업데이트</button>
			</form:form>
			
			
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
