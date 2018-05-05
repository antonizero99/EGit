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
    <title>Shop | E-Shopper</title>
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
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
    <script src="http://code.jquery.com/jquery-1.12.4.js"></script>
</head><!--/head-->

<body>
	<c:import url="header.jsp"/>
	
	<section id="advertisement">
		<div class="container">
			<img src="images/shop/advertisement.jpg" alt="" />
		</div>
	</section>
	
	<section>
		<div class="container">
			<div class="row">
				
				<c:import url="sidebar.jsp"/>
				
				<div class="col-sm-9 padding-right">
					<div class="features_items">
						<h2 class="title text-center">PRODUCTS</h2>
						
						<c:forEach var="prod" items="${sessionScope.lstShow }" varStatus="loop">
						<div class="col-sm-4">
							<div class="product-image-wrapper">
								<div class="single-products">
									<div class="productinfo text-center">
										<img src="./user/images/shop/product12.jpg" alt="" />
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
										<li><a href=""><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
										<li><a href=""><i class="fa fa-plus-square"></i>Add to compare</a></li>
									</ul>
								</div>
							</div>
						</div>
						</c:forEach>
						
						<ul class="pagination">
							<li class="active"><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">&raquo;</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<c:import url="footer.jsp"/>

	<script src="./user/js/cartStatus.js"></script>
	
    <script src="./user/js/jquery.js"></script>
	<script src="./user/js/price-range.js"></script>
    <script src="./user/js/jquery.scrollUp.min.js"></script>
	<script src="./user/js/bootstrap.min.js"></script>
    <script src="./user/js/jquery.prettyPhoto.js"></script>
    <script src="./user/js/main.js"></script>
</body>
</html>