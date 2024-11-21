<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="movreview.service.LogChartVO" %>
<%@ page import="java.util.List" %>
    
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Film Report 관리자 페이지</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/admin/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	    <script type="text/javascript">
		      google.charts.load('current', {'packages':['corechart']});
		      google.charts.setOnLoadCallback(drawChart);
		
		      function drawChart() {
		        var data = google.visualization.arrayToDataTable([
		          ['DATE', '로그인', '좋아요', '조회', '댓글', '등록'],
		          <%
		          	List<LogChartVO> activityCnt = (List<LogChartVO>) request.getAttribute("activityCnt");
		          	for(LogChartVO entry:activityCnt) {
		          		int login = entry.getLogIn();
		          		int like = entry.getLike();
		          		int movie = entry.getMovie();
		          		int review = entry.getReview();
		          		int register = entry.getRegister();
		          %>
		          	['<%= entry.getReportDate() %>', <%= login %>, <%= like %>, <%= movie %>, <%= review %>, <%= register %>],
		          <%
		          	}
		          %>
		        ]);
		
		        var options = {
		          hAxis: {title: 'DATE',  titleTextStyle: {color: '#333'}},
		          vAxis: {minValue: 0}
		        };
		
		        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
		        chart.draw(data, options);
		      }
	    </script>
	    <script type="text/javascript">
		      google.charts.load('current', {'packages':['corechart']});
		      google.charts.setOnLoadCallback(drawChart2);
		
		      function drawChart2() {
		        var data = google.visualization.arrayToDataTable([
		          ['DATE', '영화', '시리즈', '배우'],
		          <%
		          	List<LogChartVO> loadCnt = (List<LogChartVO>) request.getAttribute("loadCnt");
		          	for(LogChartVO entry:loadCnt) {
		          		int movie = entry.getMovie();
		          		int series = entry.getSeries();
		          		int actor = entry.getActor();
		          %>
		          	['<%= entry.getReportDate() %>', <%= movie %>, <%= series %>, <%= actor %>],
		          <%
		          	}
		          %>
		        ]);
		
		        var options = {
		          hAxis: {title: 'DATE',  titleTextStyle: {color: '#333'}},
		          vAxis: {minValue: 0}
		        };
		
		        var chart = new google.visualization.AreaChart(document.getElementById('chart_load'));
		        chart.draw(data, options);
		      }
	    </script>
        <script>
        
	        $(document).ready(function() {
	            $('#datatablesSimple').on('click', 'tbody tr', function() {
	            	var id = $(this).find('td').eq(0).text();
	            	moveToDetail(id);
	            })
	        } );
	        
	        function moveToDetail(id) {
	            var form = document.forms['memberForm'];
	            let popOption = "width=900px, height=1280px, top=300px, left=300px, scrollbars=yes"
	           	window.open('/adminLogPop.do?logId=' + id, 'pop', popOption);
	        }
        </script>
        <script>
	        function deleteAcc(id) {
	            console.log(id);
	            if (confirm("계정을 삭제하시겠습니까?\n탈퇴이후 삭제된 데이터는 복구되지 않습니다.")){
	            	$.ajax({
	                    url: './deleteAcc.do',
	                    type: 'post',
	                    data: { id: id },
	                    dataType: 'json',
	                    success: function(response) {
	                        console.log("서버 응답:", response);
	                        if (response.result === 0) {
	                        	alert("서버 통신에 에러가 발생했습니다.\n잠시후 다시 시도해 주세요.");
	                        } else {
	                        	alert("계정이 삭제되었습니다.");
	                        	self.location.href="/adminAccTable.do";
	                        }
	                    },
	                    error: function() {
	                        alert("서버 통신에 에러가 발생했습니다.");
	                    }
	                });
	            }
	            
	        }
	        function grantAdmin(id) {
	            console.log(id);
	            if (confirm("관리자 권한을 부여하시겠습니까?")){
	            	$.ajax({
	                    url: './adminGrant.do',
	                    type: 'post',
	                    data: { id: id },
	                    dataType: 'json',
	                    success: function(response) {
	                        console.log("서버 응답:", response);
	                        if (response.result === 0) {
	                        	alert("서버 통신에 에러가 발생했습니다.\n잠시후 다시 시도해 주세요.");
	                        } else {
	                        	alert("관리자 권한이 부여되었습니다.");
	                        	location.reload(true);
	                        }
	                    },
	                    error: function() {
	                        alert("서버 통신에 에러가 발생했습니다.");
	                    }
	                });
	            }  
	        }
	        function rebokeAdmin(id) {
	            console.log(id);
	            if (confirm("관리자 권한을 회수하시겠습니까?")){
	            	$.ajax({
	                    url: './adminRevoke.do',
	                    type: 'post',
	                    data: { id: id },
	                    dataType: 'json',
	                    success: function(response) {
	                        console.log("서버 응답:", response);
	                        if (response.result === 0) {
	                        	alert("서버 통신에 에러가 발생했습니다.\n잠시후 다시 시도해 주세요.");
	                        } else {
	                        	alert("관리자 권한이 회수되었습니다.");
	                        	location.reload(true);
	                        }
	                    },
	                    error: function() {
	                        alert("서버 통신에 에러가 발생했습니다.");
	                    }
	                });
	            }  
	        }
        </script>
    </head>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <!-- Navbar Brand-->
            <a class="navbar-brand ps-3" href="/adminMain.do">Film Report</a>
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <!-- Navbar Search-->
            <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
                <div class="input-group">
                    <input class="form-control" type="text" placeholder="Search for..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
                    <button class="btn btn-primary" id="btnNavbarSearch" type="button"><i class="fas fa-search"></i></button>
                </div>
            </form>
            <!-- Navbar-->
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#!">설정</a></li>
                        <li><a class="dropdown-item" href="#!">Activity Log</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="/logout">로그아웃</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Core</div>
                            <a class="nav-link" href="index.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Dashboard
                            </a>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#logLayouts" aria-expanded="false" aria-controls="logLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-list"></i></div>
                                System Log
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="logLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="/adminLogGuide.do">로그 가이드</a>
                                    <a class="nav-link" href="/adminAccList.do">시스템 로그</a>
                                </nav>
                            </div>
                            <div class="sb-sidenav-menu-heading">Contents</div>
                            <a class="nav-link" href="index.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-film"></i></div>
                                Movie
                            </a>
                            <a class="nav-link" href="index.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-object-group"></i></div>
                                Series
                            </a>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Comments
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="layout-static.html">Movie</a>
                                    <a class="nav-link" href="layout-sidenav-light.html">Series</a>
                                    <a class="nav-link" href="layout-sidenav-light.html">Actor</a>
                                </nav>
                            </div>
                            <div class="sb-sidenav-menu-heading">Accounts</div>
                            <a class="nav-link" href="charts.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                                Charts
                            </a>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#accountsLayouts" aria-expanded="false" aria-controls="accountsLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Accounts
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="accountsLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="/adminAccTable.do">계정 현황</a>
                                    <a class="nav-link" href="/adminAccList.do">관리자 현황</a>
                                </nav>
                            </div>
                            
                            <div class="sb-sidenav-menu-heading">Move To</div>
                            <a class="nav-link" href="/main.do">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                               	메인페이지
                            </a>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        Start Bootstrap
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">${userData.id } (${userData.name })</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">회원 상세 정보</li>
                        </ol>
                        
                        <div class="row">
                        	<div class="user-info">
                        		<div class="input-group">
		                            <label for="name">이름: ${userData.name } </label>
		                        </div>
		                        <div class="input-group">
		                        	<label for="levels">등급: ${userData.levels }</label> 
		                        </div>
		                        <div class="input-group">
		                            <label for="email">이메일: ${userData.email }</label>
		                        </div>
		                        <div class="input-group">
		                        	<c:if test="${userData.mailAuth == 0 }">
		                        		<label for="status">계정 상태: 로그인 불가</label>
		                        	</c:if>
		                        	<c:if test="${userData.mailAuth == 1 }">
		                        		<label for="status">계정 상태: 로그인 가능</label>
		                        	</c:if>
		                            
		                        </div>
                        	</div>
                            <div class="col-xl-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-area me-1"></i>
                                        ${userData.id } 님의 활동 근황
                                    </div>
                                    <div id="chart_div" style="width: 100%; height: 300px;"></div>
                                </div>
                            </div>
                            <div class="col-xl-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-bar me-1"></i>
                                        	조회 현황
                                    </div>
                                    <div id="chart_load" style="width: 100%; height: 300px;"></div>
                                </div>
                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>사용자 로그 현황
                            </div>
                            <div class="card-body">
                            <form:form name="memberForm" method="post">
                            	<input type="hidden" name="logId" value="">
                                <table id="datatablesSimple">
                                    <thead>
                                        <tr>
                                            <th>로그번호</th>
                                            <th>사용자 ID</th>
                                            <th>로그 타입</th>
                                            <th>로그 내용</th>
                                            <th>발생시간</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>로그번호</th>
                                            <th>사용자 ID</th>
                                            <th>로그 타입</th>
                                            <th>로그 내용</th>
                                            <th>발생시간</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                        <c:forEach items="${logList }" var="log" varStatus="status">
			                                    <tr>
				                                    <td>${log.logId }</td>
				                                    <td>${log.userId }</td>
				                                    <td>${log.logType }</td>
				                                    <td>${log.logDetail }</td>
				                                    <td>${log.reportTime }</td>
			                                    </tr>
		                                    </c:forEach>
                                    </tbody>
                                </table>
                                </form:form>
                            </div>
                        </div>
                        <div class="button-section">
                        	<a>
                        		<button type="button" class="detail-button" onclick="deleteAcc('${userData.id}')">회원 삭제</button>
                        		<c:if test="${userData.levels == 'ROLE_USER' }">
                        			<button type="button" class="detail-button" onclick="grantAdmin('${userData.id}')">관리자 설정</button>
                        		</c:if>
                        		<c:if test="${userData.levels == 'ROLE_ADMIN' }">
                        			<button type="button" class="detail-button" onclick="rebokeAdmin('${userData.id}')">관리자 해제</button>
                        		</c:if>
                        	</a>
                        </div>
                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">&copy; 𝓕𝓸𝓻 𝓶𝔂 𝓸𝔀𝓷 𝓗𝓪𝓹𝓹𝓲𝓷𝓮𝓼𝓼</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/assets/demo/chart-area-demo.js"></script>
        <script src="${pageContext.request.contextPath}/assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/datatables-simple-demo.js"></script>
    </body>
</html>
