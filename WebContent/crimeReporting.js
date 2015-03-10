/**
 * 
 */

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