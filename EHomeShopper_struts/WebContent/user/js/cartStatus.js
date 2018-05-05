	$(".container").on("click", ".add-to-cart", function(event){
		event.preventDefault();
		
		var pid = $(this).attr('id');
		var cmd = $(this).html();
		$.ajax({
			method: 'get',
			url: './CartController',
			data: {
				pid: pid,
				cmd: cmd},	
			
			success: function(response) {
				$("."+pid+"p").html(response.btn1);
				$("."+pid).html("<i class='fa fa-shopping-cart'></i>" + response.btn2);
			},
			error: function() {
				alert('error!!!');
			}
		});
	})