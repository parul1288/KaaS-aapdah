/**
 * 
 */
$(document).ready(function(){
	var form = document.getElementById("crowdDataForm");
	$('#reportCrime').submit(function(e){
		e.preventDefault();
		var crimeType = $('#crimeType').val();
		var date = $('#date').val();
		var time = $('#time').val();
		var address = $('#address').val();
		var city = $('#city').val();
		var description = $('#description').val();

		$.ajax({
			type: "GET",
			url: "CrowdInputDataServlet",
			contentType:"application/json",
			dataType: "json",
			data: {
				"crimeType": crimeType,
				"date": date,
				"time": time,
				"address": address,
				"city": city,
				"description": description
			},
			success: function(response){
				if(response.message == 'Reported')
					alert('Crime Reported');
				else{
					alert('This crime has not been reported. Please verify and submit the details of crime again');
				}
				form.reset();

			},
			error: function(data) {
				alert("failure");

			}
		});

	});
});