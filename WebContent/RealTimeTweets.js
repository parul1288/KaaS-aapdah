/**
 * 
 */

$(document).ready(function(){
	//$('#tweeting').hide();
	$.ajax({
		type: 'GET',
		contentType: 'application/json',
		dataType: 'json',
		url:'RetrieveRealTimeTweetsServlet',
		success: function(jsonResponse){
			var table = $('#tweetingTable');
			$.each(jsonResponse, function(key, value){
				//alert(value);
				//$('.marquee').marquee();
				var rowNew = $('<tr align="center" class="tableRows">' + 
						'<td id="tweets" name="tweets" > </td>'+	
				'</tr>');
				rowNew.children().eq(0).text("--" +value);
				rowNew.appendTo(table);
			});
			$('#tweeting').show();
		},
		error:function(){
			alert("No data for the selected crime type");	
		}
			//$('#testDiv').text('sdsdsdsd');
	});


});
