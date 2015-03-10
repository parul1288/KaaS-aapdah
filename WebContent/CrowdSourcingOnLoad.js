/**
 * 
 */

$(document).ready(function(){

	var todaysDate = new Date();
	var dd = todaysDate.getDate();

	var mm = todaysDate.getMonth()+1; 
	var yy = todaysDate.getFullYear();

	var crimeType;
	var address;
	var city;
	var time;
	var date;
	var description;
	var statement;
	var trendingData = [];

	if(dd<10) {
		dd='0'+dd;
	} 

	if(mm<10) {
		mm='0'+mm;
	} 

	todaysDate = mm+'/'+dd+'/'+yy;
	//alert(todaysDate);

	$.ajax({
		type: 'GET',
		contentType: 'application/json',
		dataType: 'json',
		url:'CrowdsourcingDataRetrievalServlet',
		//data: 'currentDate=' +todaysDate,
		success: function(jsonResponse){

			$.each(jsonResponse, function(key, value){
				crimeType = value['crimeType'];
				address = value['address'];
				city = value['city'];
				date = value['dateValue'];
				time = value['timeValue'];
				description = value['description'];

				statement = crimeType + " occurred at " + time + " "+ date + " on " +address +", " + city +". Details: " +description;
				trendingData.push(statement);
			});

			for(var i = 0; i< trendingData.length ; i++){
				alert(trendingData[i]);
			}
		}

	});
});