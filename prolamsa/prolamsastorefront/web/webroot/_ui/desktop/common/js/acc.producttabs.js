ACC.producttabs = {

	bindAll: function()
	{
		this.bindProdTabs();
	},

	bindProdTabs: function()
	{
	
		if(typeof PRODUCTTABSARRAY == 'undefined' || PRODUCTTABSARRAY.length == 0) {
		}
		else {
			for (var index = PRODUCTTABSARRAY.length - 1; index >=0; index--) {
				$('#tab_01').after('<li><h2><a href="#tab-'+ PRODUCTTABSARRAY[index].componentUid+'" class="' + PRODUCTTABSARRAY[index].titleClass + '">'+ PRODUCTTABSARRAY[index].componentTitle+'</a></h2></li>');
				$('#prod_tabs').append('<div class="'+ PRODUCTTABSARRAY[index].contentClass +'" id="tab-'+ PRODUCTTABSARRAY[index].componentUid+'">'+ PRODUCTTABSARRAY[index].componentContent+'</div>'); 

			}
		}
	
		function showReviewsAction() {
			$.get(getPageOfReviewsUrl, function(result){
				$('#reviews').html(result);
				$('#show_all_reviews_top_action, #show_all_reviews_bottom_action').click(function(e){
					e.preventDefault();
					$.get(getAllReviewsUrl, function(result){
						$('#reviews').html(result);
						$('#show_all_reviews_top_action, #show_all_reviews_bottom_action').hide();
						$('#write_review_action').click(function(e){
							e.preventDefault();
							$('#reviews').hide();
							$('#write_reviews').show();
						});
					});
				});
				$('#write_review_action').click(function(e){
					e.preventDefault();
					$('#reviews').hide();
					$('#write_reviews').show();
					$('#reviewForm input[name=headline]').focus();
				});
			});
		}
	
	
		if(typeof showReviewForm == 'undefined') {
		}
		else {
			$('#read_reviews_action').click(function(e){
				e.preventDefault();
				showReviewsAction();
				$('#reviews').show();
				$('#write_reviews').hide();
			});
	
			$(".tab_03").click(function() {
				showReviewsAction();
			});
	
			$('#write_review_action').click(function(e){
				e.preventDefault();
				$('#reviews').hide();
				$('#write_reviews').show();
				$('#reviewForm input[name=headline]').focus();
			});
			if(showReviewForm) {
				$('#reviews').hide();
				$('#write_reviews').show();
			}
		}
		
		function selectTabs() {
			$( "#prod_tabs" ).tabs({ selected: 0 });		
		}
			

	}
	
};

$(document).ready(function() {
	ACC.producttabs.bindAll();
});

