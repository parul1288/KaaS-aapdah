/**
 * 
 */

$(document).on('click', '#tologin', function(){
	$('#login').animate({width: 'show'},"slow");
    $('#register').hide();
});

$(document).on('click', '#toregister', function(){
	$('#register').animate({width: 'show'},"slow");
    $('#login').hide();
});