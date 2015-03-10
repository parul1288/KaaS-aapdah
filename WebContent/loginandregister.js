/**
 * 
 */

$(document).ready(function(){

	$('#login').submit(function(e){
		e.preventDefault();
		var email = $('#email').val();
		var password = $('#password').val();

		$.ajax({
			type:"GET",
			url: "ValidateLoginServlet",
			contentType: "application/json",
			dataType: "json",
			data: {
				"email": email,
				"password": password
			},
			success: function(response){
				alert("You are logged in");
				location.href = "http://localhost:8080/Aapdah/homepage.html";
			},
			error: function(e){
				alert("Query not processed.");
			}

		});
	});

	$('#register').submit(function(e){
		e.preventDefault();
		var firstName = $('#firstName').val();
		var lastName = $('#lastName').val();
		var city = $('#city').val();
		var state = $('#state').val();
		var email = $('#emailregister').val();
		var password = $('#passwordregister').val();

		$.ajax({
			type:"GET",
			url: "UserProfileSaveServlet",
			contentType: "application/json",
			dataType: "json",
			data: {
				"firstName": firstName,
				"lastName": lastName,
				"city": city,
				"state": state,
				"email": email,
				"password": password
			},
			success: function(response){
				alert("Your "+response.message);
				location.href = "http://localhost:8080/Aapdah/homepage.html";
			},
			error: function(e){
				alert("Query not processed.");
			}

		});
	});

});