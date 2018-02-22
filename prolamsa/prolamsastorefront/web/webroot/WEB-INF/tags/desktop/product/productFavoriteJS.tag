<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script>
onDOMLoaded(bindingFavoriteProductElements);

function bindingFavoriteProductElements()
{
	$.each($(".favorite_container .favorite_star"), function(index, value) 
		{
			createRateStarFor(value);
		});
}

function createRateStarFor(element)
{
	var rateImagePath = "${commonResourcePath}/images/";
	
	$(element).raty(
			{ 
				number: 1,
			    starOff: rateImagePath + 'star-off.png',
			    starOn: rateImagePath + 'star-on.png',
			    score: function() {
			        return $(this).attr('data-score');
			      },
			    hints: resolveHintForScore($(element).attr('data-score')),
			    click: function(){
			    	clikOnStar($(this));
			    	return false;
			    }
			});
}

function resolveHintForScore(score)
{
	var message = "";
	
	if(score == "1")
	{
		message = "<spring:theme code="text.account.customerFavoriteProducts.remove" />";
	}
	else
	{
		message = "<spring:theme code="text.account.customerFavoriteProducts.add" />";
	}
	
	return [message, null, null, null, null];
}

function clikOnStar(star)
{
	var score = star.attr('data-score');
	var productCode = star.attr('data-productCode');
	
	if(score == "1")
	{
		// remove from favorites
		removeProductFromFavorites(productCode);
	}
	else
	{
		// add to favorites
		addToFavoriteProducts(productCode);
	}
}

function addToFavoriteProducts(productCode)
{
	jQuery.ajax
	({  
		url: "<c:url value="/favorite-products/manage/edit-product-list.json"/>",
		type: 'POST',
		dataType: 'json',
		data: {'productCode' : productCode, 'action': 'add'},
		beforeSend: function ()
		{
			mask(productCode);
		},
		success: function (data)
		{
			if(data.status == 0)
			{
				var element = $("#star_" + productCode);
				element.attr('data-score', "1");
				element.raty('set', 
					{
						score: function() {
					        return $(this).attr('data-score');
					      },
						hints: resolveHintForScore($(element).attr('data-score'))
					});
			}
		},
		error: function (xht, textStatus, ex)
		{
		},
		complete: function ()
		{
			unmask(productCode);
		}
	});
}

function removeProductFromFavorites(productCode, score)
{	
	jQuery.ajax
	({  
		url: "<c:url value="/favorite-products/manage/edit-product-list.json"/>",
		type: 'POST',
		dataType: 'json',
		data: {'productCode' : productCode, 'action': 'remove'},
		beforeSend: function ()
		{
			mask(productCode);
		},
		success: function (data)
		{
			if(data.status == 0)
			{
				var element = $("#star_" + productCode);
				element.attr('data-score', "0");
				element.raty('set', 
					{
						score: function() {
					        return $(this).attr('data-score');
					      },
						hints: resolveHintForScore($(element).attr('data-score'))
					});
			}
		},
		error: function (xht, textStatus, ex)
		{    
		},
		complete: function ()
		{
			unmask(productCode);
		}
	});
}

function mask(productCode)
{
	$("#star_" + productCode).hide();
	$("#loading_" + productCode).show();
}

function unmask(productCode)
{
	window.setTimeout(function() 
	{
		$("#loading_" + productCode).hide();
		$("#star_" + productCode).show();
	}, 1000);
	
}

</script>