/**
 * 
 */
$(document).ready(function(){

	$(".link-image").click(function(){
		//var crimeType = $(this).children('img').attr('alt');
		var crimeType = $(this).attr('alt');
		alert(crimeType);

		$.ajax({
			type: 'GET',
			contentType: 'application/json',
			url:'RetrieveSqlDataServlet',
			data: 'crimeType=' +crimeType,
			dataType: 'json',
			success: function(){
				
				alert("Done");		
			}
		});		
	});
});