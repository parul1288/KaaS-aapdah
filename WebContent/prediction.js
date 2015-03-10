/**
 * 
 */

$(document).ready(
		function() {

			$('#predictor').hide();

			$('#predict').submit(
					function(e) {
						e.preventDefault();
						var city = $('#city').val();

						$.ajax({
							type : 'GET',
							contentType : 'application/json',
							url : 'PredictorServlet',
							data : 'city=' + city,
							dataType : 'json',

							success : function(jsonResponse) {
								$("#predictTable tbody").find("tr:gt(0)")
										.remove();
								// var table = $('#predictTable');
								var html = '<table>';

								$.each(jsonResponse, function(key, value) {
									// var rowNew = $('<tr align="center" ">' +
									// '<td id="crimeType" name="crimeType">
									// </td>'+
									// '<td id="prediction" name="prediction">
									// </td>'+
									// '</tr>');
									html += '<tr><td>' + key + '</td>' + '<td>'
											+ value + "%" + '</td></tr>';

									// rowNew.children().eq(0).text(value['crimeType']);
									// rowNew.children().eq(1).text(value['prediction']);
									//
									// rowNew.appendTo(table);
								});
								html += '</table>';
								$('#predictor').append(html);
								$('#predictor').show();

							},
							error : function() {
								alert("Some error");
							}
						});
					});

		});