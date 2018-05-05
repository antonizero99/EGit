<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header id="header">
	<!--Header Top-->
	<div class="header_top">
		<div class="container">
			<div class="row">
				<div class="col-sm-6">
					<div class="contactinfo">
						<ul class="nav nav-pills">
							<li><a href="#"><i class="fa fa-phone"></i> +2 95 01 88 821</a></li>
							<li><a href="#"><i class="fa fa-envelope"></i> info@domain.com</a></li>
						</ul>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="social-icons pull-right">
						<ul class="nav navbar-nav">
							<li><a href="#"><i class="fa fa-facebook"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-linkedin"></i></a></li>
							<li><a href="#"><i class="fa fa-dribbble"></i></a></li>
							<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--/Header Top-->
	
	<!--Header Middle-->
	<div class="header-middle">
		<div class="container">
			<div class="row">
				<div class="col-sm-4">
					<div class="logo pull-left">
						<a href="./HomeController"><img src="./user/images/home/logo.png" alt="" /></a>
					</div>
					<div class="btn-group pull-right">
						<div class="btn-group">
							<button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
								USA
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="#">Canada</a></li>
								<li><a href="#">UK</a></li>
							</ul>
						</div>
						
						<div class="btn-group">
							<button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
								DOLLAR
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="#">Canadian Dollar</a></li>
								<li><a href="#">Pound</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-sm-8">
					<div class="shop-menu pull-right">
						<ul class="nav navbar-nav">
							<li><a href="#"><i class="fa fa-user"></i> Account</a></li>
							<li><a href="#"><i class="fa fa-star"></i> Wishlist</a></li>
							<li><a href="./user/checkout.jsp"><i class="fa fa-crosshairs"></i> Checkout</a></li>
							<li><a href="./CartController?action=viewcart"><i class="fa fa-shopping-cart"></i> Cart</a></li>
							<li><a href="./user/login.jsp"><i class="fa fa-lock"></i> Login</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--/Header Middle-->

	<!--Header Bottom-->
	<div class="header-bottom">
		<div class="container">
			<div class="row">
				<div class="col-sm-9">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
					</div>
					<div class="mainmenu pull-left">
						<ul class="nav navbar-nav collapse navbar-collapse">
							<li><a href="index.jsp" class="active">Home</a></li>
							<li class="dropdown"><a href="./ShopController">Products<i class="fa fa-angle-down"></i></a>
                                   <ul role="menu" class="sub-menu">
                                   <c:forEach var="cat" items="${sessionScope.homeItems.lstCat }">
                                   <li><a href="#">${cat.name }</a></li>
                                   </c:forEach>
									<!-- <li><a href="product-details.jsp">Product Details</a></li> 
									<li><a href="checkout.jsp">Checkout</a></li> 
									<li><a href="cart.jsp">Cart</a></li> 
									<li><a href="login.jsp">Login</a></li>  -->
                                   </ul>
                               </li> 
							<li class="dropdown"><a href="#">Blog<i class="fa fa-angle-down"></i></a>
                                   <ul role="menu" class="sub-menu">
                                       <li><a href="blog.jsp">Blog List</a></li>
									<li><a href="blog-single.jsp">Blog Single</a></li>
                                   </ul>
                               </li> 
							<li><a href="contact-us.jsp">Contact</a></li>
						</ul>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="search_box pull-right">
						<input type="text" placeholder="Search"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--/Header Bottom-->
</header>