<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Home | E-Shopper</title>
    <link href="./user/css/bootstrap.min.css" rel="stylesheet">
    <link href="./user/css/font-awesome.min.css" rel="stylesheet">
    <link href="./user/css/prettyPhoto.css" rel="stylesheet">
    <link href="./user/css/price-range.css" rel="stylesheet">
    <link href="./user/css/animate.css" rel="stylesheet">
	<link href="./user/css/main.css" rel="stylesheet">
	<link href="./user/css/responsive.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->       
    <link rel="shortcut icon" href="./user/images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="./user/images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="./user/images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="./user/images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="./user/images/ico/apple-touch-icon-57-precomposed.png">
	
	<script src="http://code.jquery.com/jquery-1.12.4.js"></script>
</head>

<body>

<c:import url="header.jsp"/>
<c:import url="slider.jsp"/>


	<section>
		<div class="container">
			<div class="row">

<!-- Sidebar menu -->
<c:import url="sidebar.jsp"/>
<!-- /Sidebar menu -->

	
				<!-- Main content -->
				<div class="col-sm-9 padding-right">
				
					<!-- Features Items -->
					<div class="features_items">
						<h2 class="title text-center">Features Items</h2>
						
						
						<c:forEach var="prod" items="${sessionScope.homeItems.lstFeatureProd }" varStatus="loop">
						<div class="col-sm-4">
							<div class="product-image-wrapper">
								<div class="single-products">
										<div class="productinfo text-center">
											<img src="./user/images/home/product1.jpg" alt="" />
											<h2><fmt:formatNumber type="number">${prod.price }</fmt:formatNumber></h2>
											<p>${prod.name }</p>
											<c:choose>
												<c:when test="${sessionScope.cartStatus[loop.index]=='out' }">
													<a href="#" class="btn btn-default add-to-cart ${prod.id }p" id="${prod.id }"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</c:when>
												<c:otherwise>
													<a href="#" class="btn btn-default add-to-cart ${prod.id }p" id="${prod.id }">Added to cart</a>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="product-overlay">
											<div class="overlay-content">
												<h2><fmt:formatNumber type="number">${prod.price }</fmt:formatNumber></h2>
												<p>${prod.name }</p>
												<c:choose>
													<c:when test="${sessionScope.cartStatus[loop.index]=='out' }">
														<a href="#" class="btn btn-default add-to-cart ${prod.id }" id="${prod.id }"><i class="fa fa-shopping-cart"></i>Add to cart</a>
													</c:when>
													<c:otherwise>
														<a href="#" class="btn btn-default add-to-cart ${prod.id }" id="${prod.id }"><i class="fa fa-shopping-cart"></i>Remove from cart</a>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
								</div>
								<div class="choose">
									<ul class="nav nav-pills nav-justified">
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
										<li><a href="./ProductDetailsController?id=${prod.id }"><i class="fa fa-plus-square"></i>See Details</a></li>
									</ul>
								</div>
							</div>
						</div>
						</c:forEach>
						
						
					</div>
					<!-- /Features Items -->
					
					
					<!-- Category Tab -->
<%-- 					<div class="category-tab">
						
						<!-- Tab title -->
						<div class="col-sm-12">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#${sessionScope.lstCats[0] }" data-toggle="tab">${sessionScope.homeItems.lstCat[0] }</a></li>
								<c:forEach var="cat" items="${sessionScope.homeItems.lstCat }" begin="1">
									<li><a href="#${cat.name}" data-toggle="tab">${cat.name }</a></li>
								</c:forEach>
							</ul>
						</div>
						<!-- /Tab title -->
						
						<div class="tab-content">
							<div class="tab-pane fade active in" id="${sessionScope.homeItems.lstCat[0] }" >
								<c:forEach var="prod" items="${sessionScope.homeItems.productByCat.${sessionScope.homeItems.lstCat[0] } }">
								<div class="col-sm-3">
									<div class="product-image-wrapper">
										<div class="single-products">
											<div class="productinfo text-center">
												<img src="./user/images/home/gallery1.jpg" alt="" />
												<h2><fmt:formatNumber type="number">${prod.price }</fmt:formatNumber></h2>
												<p>${prod.name }</p>
												<a href="#" class="btn btn-default add-to-cart ${prod.id }" id="${prod.id }"><i class="fa fa-shopping-cart"></i>Add to cart</a>
											</div>
											
										</div>
									</div>
								</div>
								</c:forEach>
							</div>
							
							<c:forEach var="cat" items="${sessionScope.homeItems.lstCat }" begin="1" varStatus="loop">
							<div class="tab-pane fade" id="${cat.name }" >
							
								<c:forEach var="prod" items="${sessionScope.homeItems.productByCat.SONY }">
								<div class="col-sm-3">
									<div class="product-image-wrapper">
										<div class="single-products">
											<div class="productinfo text-center">
												<img src="./user/images/home/gallery1.jpg" alt="" />
												<h2><fmt:formatNumber type="number">${prod.price }</fmt:formatNumber></h2>
												<p>${prod.name }</p>
												<a href="#" class="btn btn-default add-to-cart ${prod.id }" id="${prod.id }"><i class="fa fa-shopping-cart"></i>Add to cart</a>
											</div>
										</div>
									</div>
								</div>
								</c:forEach>
								
							</div>
							</c:forEach>
							
						</div>
					</div> --%>
					<!-- /Category Tab -->
					
					
					<!-- Recommended Items -->
					<div class="recommended_items">
						<h2 class="title text-center">recommended items</h2>
						
						<div id="recommended-item-carousel" class="carousel slide" data-ride="carousel">
							<div class="carousel-inner">
								<div class="item active">	
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="./user/images/home/recommend1.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="./user/images/home/recommend2.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="./user/images/home/recommend3.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
								</div>
								<div class="item">	
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="./user/images/home/recommend1.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="./user/images/home/recommend2.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="./user/images/home/recommend3.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
								</div>
							</div>
							 <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
								<i class="fa fa-angle-left"></i>
							  </a>
							  <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
								<i class="fa fa-angle-right"></i>
							  </a>			
						</div>
					</div>
					<!-- /Recommended Items -->
					
				</div>
				<!-- /Main Content -->
				
			</div>
		</div>
	</section>

<c:import url="footer.jsp"/>

	

	<script src="./user/js/cartStatus.js"></script>
	
    <script src="./user/js/jquery.js"></script>
	<script src="./user/js/bootstrap.min.js"></script> <!-- Slidebar -->
	<script src="./user/js/jquery.scrollUp.min.js"></script> <!-- Scroll to top page -->
	<script src="./user/js/price-range.js"></script> <!-- Price range area -->
    <script src="./user/js/jquery.prettyPhoto.js"></script>
    <script src="./user/js/main.js"></script>
</body>
</html>