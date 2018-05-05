<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-3">
	<div class="left-sidebar">
		<h2>Category</h2>
		<div class="panel-group category-products" id="accordian">
		
			<c:forEach var="cat" items="${sessionScope.homeItems.lstCat }" varStatus="loop">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordian" href="#${cat.name }sidebar">
							<span class="badge pull-right"><i class="fa fa-plus"></i></span>
							${cat.name }
						</a>
					</h4>
				</div>
				<div id="${cat.name }sidebar" class="panel-collapse collapse">
					<div class="panel-body">
						<ul>
							<c:forEach var="brand" items="${sessionScope.homeItems.lstBrand }">
							<li><a href="#">${brand.name }</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			</c:forEach>
			
			<!-- <div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title"><a href="#">Kids</a></h4>
				</div>
			</div> -->
			
		</div>
	
		<div class="brands_products">
			<h2>Brands</h2>
			<div class="brands-name">
				<ul class="nav nav-pills nav-stacked">
					<c:forEach var="brand" items="${sessionScope.homeItems.lstBrand }" varStatus="loop">
					<li><a href="#"> <span class="pull-right">(10)</span>${brand.name }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		
		<div class="price-range">
			<h2>Price Range</h2>
			<div class="well text-center">
				 <input type="text" class="span2" value="" data-slider-min="0" data-slider-max="600" data-slider-step="5" data-slider-value="[250,450]" id="sl2" ><br />
				 <b class="pull-left">$ 0</b> <b class="pull-right">$ 600</b>
			</div>
		</div>
		
		<div class="shipping text-center">
			<img src="./user/images/home/shipping.jpg" alt="" />
		</div>
	
	</div>
</div>