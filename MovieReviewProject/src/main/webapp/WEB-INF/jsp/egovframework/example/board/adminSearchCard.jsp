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
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        
	    <script type="text/javascript">
		      google.charts.load("current", {packages:["corechart"]});
		      google.charts.setOnLoadCallback(drawChart);
		      function drawChart() {
		        var data = google.visualization.arrayToDataTable([
		          ['Used', 'Count'],
		          ['검색 페이지', ${chartData.log09}],
		          ['장르 선택', ${chartData.log29}]
		        ]);
		
		        var options = {
		          is3D: true,
		        };
		
		        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
		        chart.draw(data, options);
		      }
	    </script>
	    <script type="text/javascript">
		      google.charts.load("current", {packages:["corechart"]});
		      google.charts.setOnLoadCallback(drawChart2);
		      function drawChart2() {
		        var data = google.visualization.arrayToDataTable([
		          ['Used', 'Count'],
		          ['SF', ${genreData.sf}],
		          ['TV 영화', ${genreData.tv}],
		          ['가족', ${genreData.family}],
		          ['공포', ${genreData.horror}],
		          ['다큐멘터리', ${genreData.docu}],
		          ['로맨스', ${genreData.romance}],
		          ['모험', ${genreData.adventure}],
		          ['미스터리', ${genreData.mystery}],
		          ['범죄', ${genreData.crime}],
		          ['서부', ${genreData.western}],
		          ['스릴러', ${genreData.thriller}],
		          ['애니메이션', ${genreData.ani}],
		          ['액션', ${genreData.action}],
		          ['역사', ${genreData.history}],
		          ['음악', ${genreData.music}],
		          ['전쟁', ${genreData.war}],
		          ['코미디', ${genreData.comedy}],
		          ['판타지', ${genreData.fantasy}]
		        ]);
		
		        var options = {
		          is3D: true,
		        };
		
		        var chart = new google.visualization.PieChart(document.getElementById('genrechart_3d'));
		        chart.draw(data, options);
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
                                    <a class="nav-link" href="/adminLogList.do">시스템 로그</a>
                                </nav>
                            </div>
                            <div class="sb-sidenav-menu-heading">Contents</div>
                            <a class="nav-link" href="/adminMovie.do">
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
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Film Report 검색 현황</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Dashboard > Search Card</li>
                        </ol>
                        <div class="row">
                            
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-warning text-white mb-4">
                                    <div class="card-body">Detail Card</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="/adminDetailCard.do">View Details</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-success text-white mb-4">
                                    <div class="card-body">Review Card</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="/adminReviewCard.do">View Details</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-danger text-white mb-4">
                                    <div class="card-body">Insert Card</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="/adminInsertCard.do">View Details</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-area me-1"></i>
                                        Film Report 검색 현황
                                    </div>
                                    <div id="piechart_3d" style="width: auto; height: 500px;"></div>
                                </div>
                            </div>
                            <div class="col-xl-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-bar me-1"></i>
                                        	장르 검색 현황
                                    </div>
                                    <div id="genrechart_3d" style="width: auto; height: 500px;"></div>
                                </div>
                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                Film Report 검색 현황
                            </div>
                            <div class="card-body">
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
                                        <c:forEach items="${log }" var="log" varStatus="status">
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
                            </div>
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
