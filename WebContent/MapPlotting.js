/**
 * 
 */

$(document).ready(function(){
	google.maps.event.addDomListener(window, 'load', initialize);
	var map = null;
	var latlng = null ;
	var lat = null;
	var lng = null;
	var addresses = [];
//	var latlngAddresses = [];
//	var formattedAddresses = [];
	var infowindow = new google.maps.InfoWindow();
	var globalZoom = 13;

	function initialize() {
		if(navigator.geolocation){
			navigator.geolocation.getCurrentPosition(function (position) {
				lat = position.coords.latitude;
				lng = position.coords.longitude;
				latlng = new google.maps.LatLng(lat, lng);
//				latlng = new google.maps.LatLng(lat,lng);

				//alert(latlng);
				var myOptions = new google.maps.Map(document.getElementById('map'), {
					mapTypeId: google.maps.MapTypeId.ROADMAP,
					center: latlng,
					zoom: globalZoom,
				});

				map = new google.maps.Map(document.getElementById('map'), myOptions);

				var marker = new google.maps.Marker({
					map: map,
					position: latlng,
					title:"You are here!"
				});

				getAddress(lat, lng);
			});

		}
	}

	function getAddress(latitude,longitude){
		var request = new XMLHttpRequest();

		var method = 'GET';
		var url = 'http://maps.googleapis.com/maps/api/geocode/json?latlng='+latitude+','+longitude+'&sensor=true';
		var async = true;

		request.open(method, url, async);
		request.onreadystatechange = function(){
			if(request.readyState == 4 && request.status == 200){
				var data = JSON.parse(request.responseText);
				var address = data.results[0];
				var addressString = address.formatted_address;
				var str = addressString.split(",");
				var city = str[1].trim();
				alert(city);
				if(city == "San José State University"){
					city = "San Jose State University";
				}

				alert(city);
				alert(city);

				findMarkersByCity(city);
				//findMarkersByCity("sunnyvale");

			}
		};
		request.send();
	}

	function findMarkersByCity(city){
		//alert(city);

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
				drop(city);
//				for (var x = 0; x < addresses.length; x++) {

//				$.getJSON('http://maps.googleapis.com/maps/api/geocode/json?address='+addresses[x] + ", " + city+ ', CA'+'&sensor=false', null, function (data)
//				{

//				var p = data.results[0].geometry.location;

//				var latlngAddr = new google.maps.LatLng(p.lat, p.lng);
////				alert(latlngAddr);
//				createMarker(latlngAddr, data.results[0].formatted_address);
//				});
//				}
			},
			error: function(err){
				console.log(err);
			}
		});

	}

	function drop(city){
		//alert(addresses.length);
		for (var x = 0; x < addresses.length; x++) {
			//alert(addresses[x]);


			$.getJSON('http://maps.googleapis.com/maps/api/geocode/json?address='+addresses[x] + ", " + city+ ', CA'+'&sensor=false', null, function (data)
					{

				var p = data.results[0].geometry.location;

				var latlngAddr = new google.maps.LatLng(p.lat, p.lng);
				//latlngAddresses.push(latlngAddr);
				//formattedAddresses.push(data.results[0].formatted_address);

				setTimeout(function(){
					createMarker(latlngAddr, data.results[0].formatted_address);
				}, x * 200);

					});
		}
	}

	function createMarker(latlon, add) {

		var marker = new google.maps.Marker({
			map: map,
			position: latlon,
			center:latlon,
			zoom:globalZoom,
			animation: google.maps.Animation.DROP
		});


		google.maps.event.addListener(marker, 'mouseover', function() {
			infowindow.setContent(add);
			infowindow.open(map, marker);
		});

	}


});