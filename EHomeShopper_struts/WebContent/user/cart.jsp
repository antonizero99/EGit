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
    <title>Cart | E-Shopper</title>
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

	<section id="cart_items">
		<div class="container">
			<div class="breadcrumbs">
				<ol class="breadcrumb">
				  <li><a href="./HomeController">Home</a></li>
				  <li class="active">Shopping Cart</li>
				</ol>
			</div>
			<div class="table-responsive cart_info">
				<table class="table table-condensed">
					<thead>
						<tr class="cart_menu">
							<td class="image">Item</td>
							<td class="description"></td>
							<td class="price">Price</td>
							<td class="quantity">Quantity</td>
							<td class="total">Total</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
					
					<c:forEach var="item" items="${sessionScope.lstCart }">
						<tr>
							<td class="cart_product">
								<a href=""><img src="images/cart/one.png" alt=""></a>
							</td>
							<td class="cart_description">
								<h4><a href="">${item.name }</a></h4>
								<p>Web ID: 1089772</p>
							</td>
							<td class="cart_price">
								<p><fmt:formatNumber type="number">${item.price }</fmt:formatNumber></p>
							</td>
							<td class="cart_quantity">
								<div class="cart_quantity_button">
									<a class="cart_quantity_up control_btt" id=${item.pID } href=""> + </a>
									<input class="cart_quantity_input" type="text" name="quantity" value="${item.qty }" autocomplete="off" size="2">
									<a class="cart_quantity_down control_btt" id=${item.pID } href=""> - </a>
								</div>
							</td>
							<td class="cart_total">
								<p class="cart_total_price"><fmt:formatNumber type="number">${item.price }</fmt:formatNumber></p>
							</td>
							<td class="cart_delete">
								<a class="cart_quantity_delete control_btt" id=${item.pID } href=""><i class="fa fa-times"></i></a>
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
				</table>
			</div>
		</div>
	</section> <!--/#cart_items-->

	<section id="do_action">
		<div class="container">
			<div class="heading">
				<h3>What would you like to do next?</h3>
				<p>Choose if you have a discount code or reward points you want to use or would like to estimate your delivery cost.</p>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="chose_area">
						<ul class="user_option">
							<li>
								<input type="checkbox">
								<label>Use Coupon Code</label>
							</li>
							<li>
								<input type="checkbox">
								<label>Use Gift Voucher</label>
							</li>
							<li>
								<input type="checkbox">
								<label>Estimate Shipping & Taxes</label>
							</li>
						</ul>
						<ul class="user_info">
							<li class="single_field">
								<label>Country:</label>
								<select>
									<option>United States</option>
									<option>Bangladesh</option>
									<option>UK</option>
									<option>India</option>
									<option>Pakistan</option>
									<option>Ucrane</option>
									<option>Canada</option>
									<option>Dubai</option>
								</select>
								
							</li>
							<li class="single_field">
								<label>Region / State:</label>
								<select>
									<option>Select</option>
									<option>Dhaka</option>
									<option>London</option>
									<option>Dillih</option>
									<option>Lahore</option>
									<option>Alaska</option>
									<option>Canada</option>
									<option>Dubai</option>
								</select>
							
							</li>
							<li class="single_field zip-field">
								<label>Zip Code:</label>
								<input type="text">
							</li>
						</ul>
						<a class="btn btn-default update" href="">Get Quotes</a>
						<a class="btn btn-default check_out" href="">Continue</a>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="total_area">
						<ul>
							<li>Cart Sub Total <span>$59</span></li>
							<li>Eco Tax <span>$2</span></li>
							<li>Shipping Cost <span>Free</span></li>
							<li>Total <span>$61</span></li>
						</ul>
							<a class="btn btn-default update" href="">Update</a>
							<a class="btn btn-default check_out" href="">Check Out</a>
					</div>
				</div>
			</div>
		</div>
	</section><!--/#do_action-->

<c:import url="footer.jsp"/>
	
	<script>
		$(".container").on("click", ".control_btt", function(event) {
			event.preventDefault();
			
			cmd = $(this).html();
			pid = $(this).attr("id");
			
			$.ajax({
				method: "get",
				url: "./CartDetailController",
				data: {
					cmd: cmd,
					pid: pid
				},
				
				success: function(response) {
					
				},
				
				error: function() {
					alert("error!!!")
				}
			})
		})
	</script>

    <script src="./user/js/jquery.js"></script>
	<script src="./user/js/bootstrap.min.js"></script>
	<script src="./user/js/jquery.scrollUp.min.js"></script>
    <script src="./user/js/jquery.prettyPhoto.js"></script>
    <script src="./user/js/main.js"></script>
</body>
</html>