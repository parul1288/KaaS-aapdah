/**
 * 
 */

$(document).ready(function(){
	$('#displayTable').hide();

//	var $a = document.getElementById("crimeTypeImage");
//	var $img = $a.getElementsByTagName("img")[0];
//	var altValue = $img.alt;

//	$(".crimeTypeImage").click(function (){
//		var a = (this).id;
//		alert(a);
//		var img = a.children("img");
//		var altValue = img.alt;
//		alert(altValue);
//	});
	
//	$('.crimeTypeImage').click(function() {
//	    alert( this.id );
//	});

	var $a = document.getElementById("theftCrime");
	var $img = $a.getElementsByTagName("img")[0];
	var crimeType = $img.alt;
	
	$.ajax({
		type: 'GET',
		contentType: 'application/json',
		url:'RetrieveSqlDataServlet',
		data: 'crimeType=' +crimeType,
		dataType: 'json',
		success: function(jsonResponse){
			$("#retrieveSQLData tbody").find("tr:gt(0)").remove();
			var table = $('#retrieveSQLData');

			$.each(jsonResponse, function(key,value){
				var rowNew = $('<tr align="center" class="tableRows">' + 
						'<td id="address" name="address"> </td>'+	
						'<td id="agency" name="agency"> </td>'+
						'<td id="date" name="date"> </td>'+
						'<td id="time" name="time"> </td>'+
				'</tr>');

				rowNew.children().eq(0).text(value['address']);
				rowNew.children().eq(1).text(value['agency']);
				rowNew.children().eq(2).text(value['dateValue']);
				rowNew.children().eq(3).text(value['timeValue']);

				rowNew.appendTo(table);
			});

			$('#displayTable').show();			
		},
		error:function(){
			alert("No data for the selected crime type");	
		}
	});		
	

	$('#form1').submit(function(e){
		e.preventDefault();
		var crimeType = $('#crimeType').val();
		$.ajax({
			type: 'GET',
			contentType: 'application/json',
			url:'RetrieveSqlDataServlet',
			data: 'crimeType=' +crimeType,
			dataType: 'json',
			success: function(jsonResponse){
				$("#retrieveSQLData tbody").find("tr:gt(0)").remove();
				var table = $('#retrieveSQLData');

				$.each(jsonResponse, function(key,value){
					var rowNew = $('<tr align="center" class="tableRows">' + 
							'<td id="address" name="address"> </td>'+	
							'<td id="agency" name="agency"> </td>'+
							'<td id="date" name="date"> </td>'+
							'<td id="time" name="time"> </td>'+
					'</tr>');

					rowNew.children().eq(0).text(value['address']);
					rowNew.children().eq(1).text(value['agency']);
					rowNew.children().eq(2).text(value['dateValue']);
					rowNew.children().eq(3).text(value['timeValue']);

					rowNew.appendTo(table);
				});

				$('#displayTable').show();			
			},
			error:function(){
				alert("No data for the selected crime type");	
			}
		});		
	});

	$(function(){
		$("#crimeType").autocomplete({
			source: function(request, response){
				$.ajax({
					type: "GET",
					url: "AutoCompleteCrimeTypeServlet",
					dataType: "json",
					data: {
						"term": ''
					},
					success: function(data) {
						response(data);
					}
				});
			} 
		});
	});


	$(function() {
		$("#date").datepicker();
	});

	$(document).on('click', '#report', function(){
		new_dialog('Report');
		return false;
	});

	var new_dialog = function (action) {
		var dlg = $('#report_Form').clone();
		var crimeType = dlg.find("#crimeType"),
		date = dlg.find(("#date")),
		time = dlg.find(("#time")),
		address = dlg.find(("#address")),
		city = dlg.find(("#city")),
		description = dlg.find("#description");

		var config = {
				title: "Report Crime",
				autoOpen: true,
				height: 450,
				width: 450,
				modal: true,
				buttons:{
					"Save": function(){
						save_data();
					},
					"Cancel": function(){
						dlg.dialog("close");
					}
				},
				close: function () {
					dlg.remove();
				}
		};

		if(action === "Report"){
			dlg.dialog(config);
		}

		function save_data(){
			var crimeTypeServlet = crimeType.val();
			alert(crimeTypeServlet);
			var dateServlet = date.val();
			var timeServlet = time.val();
			var addressServlet = address.val();
			var cityServlet = city.val();
			var descriptionServlet = description.val();
			alert(addressServlet);

			$.ajax({
				type: "GET",
				url: "CrowdInputDataServlet",
				contentType:"application/json",
				dataType: "json",
				data: {
					"crimeType": crimeTypeServlet,
					"date": dateServlet,
					"time": timeServlet,
					"address": addressServlet,
					"city": cityServlet,
					"description": descriptionServlet
				},
				success: function(response){
					if(response.message == 'Reported')
						alert('Crime Reported');
					else{
						alert('This crime has not been reported. Please verify and submit the details of crime again');
					}
					dlg.dialog("close");

				},
				error: function(data) {
					alert("failure");

				}
			});
			return false;

		};
	};


	$(document).on('click', '#createAccount', function(){
		new_dialog1('Create Account');
		return false;
	});

	var new_dialog1 = function (action) {
		var dlg1 = $('#createNewAccount').clone();
		var firstName = dlg1.find(("#firstName")),
		lastName = dlg1.find(("#lastName")),
		city = dlg1.find(("#city")),
		state = dlg1.find(("#state")),
		email = dlg1.find(("#email")),
		password = dlg1.find(("#password"));

		var config = {
				title: "Create Account",
				autoOpen: true,
				height: 400,
				width: 400,
				modal: true,
				buttons:{
					//"Create an account" : save_data,
					"Join Aapdah": function(){
						save_data();
					},
					"Cancel": function(){
						dlg1.dialog("close");
					}
				},
				close: function () {
					dlg1.remove();
				}
		};

		if(action === "Create Account"){
			dlg1.dialog(config); 
		}

		function save_data(){
			var currFirstName = firstName.val();
			var currLastName = lastName.val();
			var currCity = city.val();
			var currState = state.val();
			var currEmail = email.val();
			var currPassword = password.val();
			alert(city.val());
			$.ajax({
				type:"GET",
				url: "UserProfileSaveServlet",
				contentType: "application/json",
				dataType: "json",
				data: {
					"firstName": currFirstName,
					"lastName": currLastName,
					"city": currCity,
					"state": currState,
					"email": currEmail,
					"password": currPassword
				},
				success: function(response){
					alert(response.message);
				},
				error: function(e){
					alert("Query not processed.");
				}

			});
			return false;
		}

	};

//	$(document).on('click', '#loginNav', function(){
//	$('#login').animate({width: 'show'},"slow");
//	});

	$(document).on('click', '#loginNav', function(){
		new_dialog2('Login');
		return false;
	});

	var new_dialog2 = function (action) {
		var dlg2 = $('#login').clone();
		var email = dlg2.find(("#email")),
		password = dlg2.find(("#password"));

		var config = {
				close: function () {
					dlg2.remove();
				}
		};

		if(action === "Login"){
			dlg2.dialog(config); 
		}
	};


});