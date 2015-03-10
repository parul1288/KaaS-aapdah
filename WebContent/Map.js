/**
 * 
 */

$(document).ready(function(){

	google.maps.event.addDomListener(window, 'load', initialize);
	var map = null;
	var latlng = null ;
	var infowindow = new google.maps.InfoWindow();

	function initialize(position) {
		if(navigator.geolocation){
			navigator.geolocation.getCurrentPosition(function (position) {
				latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

				alert(latlng);
				var myOptions = new google.maps.Map(document.getElementById('map'), {
					mapTypeId: google.maps.MapTypeId.ROADMAP,
					center: latlng,
					zoom: 15,
				});

				var map = new google.maps.Map(document.getElementById('map'), myOptions);

				var marker = new google.maps.Marker({
					position: latlng, 
					map: map, 
					title:"You are here! (at least within a "+position.coords.accuracy+" meter radius)"
				});

//				google.maps.event.addListener(marker, 'click', function() {
//					infowindow.setContent("You are here");
//					infowindow.open(map, marker);
//				});
			});
		}
	}

	var addresses = [];
	$('#formMaps').submit(function(e){
		e.preventDefault();
		//var city = document.getElementById("city").value;
		var city = $('#city').val();


		$.ajax({
			type: 'GET',
			url:'MapAddressRetrieveServlet',
			data: 'city=' +city,
			dataType: 'json',
			contentType: 'application/json',
			success: function(response){

				$.each(response, function( index, value ) {
					addresses.push(value);
				});
				//alert(addresses.length);
				for (var x = 0; x < addresses.length; x++) {
					var count = 0;
					$.getJSON('http://maps.googleapis.com/maps/api/geocode/json?address='+addresses[x] + ", " + city+ ', CA'+'&sensor=false', null, function (data)
							{
						
						var p = data.results[0].geometry.location;
						//alert(p);
						var latlng = new google.maps.LatLng(p.lat, p.lng);
						createMarker(latlng, data.results[0].formatted_address);
							});
				}
			},
			error: function(err){
				console.log(err);
			}
		});
	});


//	function geoCodingMethod(address){
//		geocoder.geocode( { 'address': address}, function(results, status) {
//			if (status === google.maps.GeocoderStatus.OK) {
//				map.setCenter(results[0].geometry.location);
//
//				var add = results[0].geometry.location;
//				createMarker(add);
//			} 
//			else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {    
//				//setTimeout(100);
//				geoCodingMethod(address);			
//			}
//			else {
//				alert('Geocode was not successful for the following reason: ' + status);
//			}
//		});
//	}


	function createMarker(latlon, add) {
//		clearMarkers();
		var marker = new google.maps.Marker({
			position: latlon,
			center:latlon,
			map: map,
			zoom: 12,
			animation: google.maps.Animation.DROP
		});
		google.maps.event.addListener(marker, 'mouseover', function() {
			infowindow.setContent(add);
			infowindow.open(map, marker);
		});
	}

//	function clearMarkers() {
//		setAllMap(null);
//	}
	
});