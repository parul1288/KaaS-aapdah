<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">

<title>Aapdah Home</title>

<style type="text/css">
#report_Form {
	display: none;
}

#createNewAccount {
	display: none;
}
</style>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />

<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script type="text/javascript" src="RetrieveSqlData.js"></script>

<script type="text/javascript" src="RealTimeTweets.js"></script>
</head>
<body style="background-color: #424242;">
	<div id="body">
		<div id="header_cc"
			style="margin: 0 auto; padding: 10px 0 40px 0; clear: both; font-family: Verdana, Helvetica, sans-serif; max-width: 100% 16px; height: 75px; background: #000000; width: 1250px">
			<table cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td>
							<table id="masterhead" cellpadding="0" border="0" align="center">
								<tbody>
									<tr>

										<td width="3100px" valign="middle" align="left">
											<h2
												style="font-family: Verdana, Helvetica, sans-serif; color: #A4A4A4; margin: 10px; padding: 10px 0 0 0; text-transform: uppercase; color: white; font-size: 35px; font-weight: bold; line-height: 0.3cm; letter-spacing: 0.3cm;"
												align="left">
												Aapdah <br>
											</h2>
											<p
												style="font-family: Verdana, Helvetica, sans-serif; padding: 10px 0 0 0; font-size: 20px; font-style: italic; line-height: 0.3cm; letter-spacing: 0.1cm; color: #A4A4A4"
												align="left">A Disaster Data Handling Application</p>
										</td>
										<td><a href=""
											style="color: white; font-family: Verdana, Helvetica, sans-serif; font-size: 20px;"
											id="createAccount" name="createAccount">Create Account</a></td>
										<td><img
											src="http://www.clker.com/cliparts/o/q/H/f/A/e/caution-logo.svg"
											align="right" width=100 height=100></td>
									</tr>
								</tbody>
							</table>
						</td>

					</tr>
				</tbody>
			</table>
		</div>
		<br> <br> <br>
		<div id="selection">
			<table cellpadding="0" cellspacing="25">
				<tbody>
					<tr>
						<td><img
							src="http://stopcrime365.com/uploaded_images/stop-crime-clipart-720270.jpg"
							width=100 height=100 align="left"></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>
							<form name="form1" id="form1">
								<p
									style="font-family: Verdana, Helvetica, sans-serif; font-size: 20px; color: white">
									Select Crime Type: <input type="" name="crimeType"
										id="crimeType" align="middle" /> <input type="submit"
										value="Submit" id="submitButton" name="submitButton"
										align="middle" />
								</p>
							</form>
						</td>
					</tr>
					<tr>
						<td><img
							src="http://www.mikesowden.org/feveredmutterings/wp-content/uploads/2009/08/RobberyNotAllowed.jpg"
							width=100 height=100 align="left"></td>

						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><a href=""
							style="color: white; font-family: Verdana, Helvetica, sans-serif; font-size: 20px;"
							id="report" name="report">Report</a>
					</tr>
					<tr>
						<td><img
							src="http://www.openmarket.org/wp-content/uploads/2012/08/ban-fists-update-75.png"
							width=100 height=100 align="left"></td>
					</tr>
				</tbody>
			</table>
		</div>

		<br> <br>

		<div id="displayTable" align="center">
			<table id=retrieveSQLData align="center" cellpadding="1"
				cellspacing="1" border="1" width="1000" style="color: white;">
				<thead>
					<tr align="center">
						<th>Address</th>
						<th>Agency</th>
						<th>Date</th>
						<th>Time</th>
					</tr>

				</thead>

				<tbody id="rows" align="center">
				</tbody>
			</table>
		</div>

		<div id="report_Form" align="center" title="Report">
			<form id="crowdDataForm" style="">
				<fieldset>
					<label for="crimeType">Crime Type: </label> 
					<select id="crimeType" name="crimeType"	class="basic" style="width: 250px">
						<option value="-1">Select Crime Type</option>
						<option value="Assault">Assault</option>
						<option value="Assault With Deadly Weapon">Assault With Deadly Weapon</option>
						<option value="Breaking & Entering">Breaking & Entering</option>
						<option value="Homicide">Homicide</option>
						<option value="Sexual Offense">Sexual Offense</option>
						<option value="Robbery">Robbery</option>
						<option value="Theft">Theft</option>
						<option value="Theft from Vehicle">Theft from Vehicle</option>
						<option value="Theft of Vehicle">Theft of Vehicle</option>
						<option value="Vehicle Recovery">Vehicle Recovery</option>
					</select> &nbsp;
					<label for="date">Date of crime: </label> 
					<input type="text" name="date" id="date" value="" /> 
					<label for="time">Time of crime: </label> 
					<input type="text" name="time" id="time" value="" /> 
					<label for="address">Address: </label> 
					<input type="text" name="address" id="address" value=""> 
					<label for="city">City: </label> 
					<select id="city" name="city"	class="basic" style="width: 250px">
						<option value="-1">Select City</option>
						<option value="San Jose">San Jose</option>
						<option value="Santa Clara">Santa Clara</option>
						<option value="Menlo Park">Menlo Park</option>
						<option value="San Francisco">San Francisco</option>
						<option value="Sunnyvale">Sunnyvale</option>
						<option value="Palo Alto">Palo Alto</option>
						<option value="San Mateo">San Mateo</option>
						<option value="Mountain View">Mountain View</option>
						<option value="Los Altos">Los Altos</option>
						<option value="Los Angeles">Los Angeles</option>
						<option value="Burlingame">Burlingame</option>
						<option value="Alexander City">Alexander City</option>
					</select> &nbsp;
					<label for="description">Description: </label> 
					<input type="text" name="description" id="description" value="" />
				</fieldset>
			</form>
		</div>

		<!-- Used to create a new account for the user -->
		<div id="createNewAccount" align="center" title="Create Account">
			<form id="createAccountForm">
				<fieldset>
					<label for="firstName" style="width: 100px">First Name: </label> 
					<input id="firstName" type="text" name="firstName" title="First Name [required]"
						required="required" style="width: 250px"> &nbsp;
					<label for="lastName" style="width: 100px">Last Name: </label> 
					<input id="lastName" type="text" name="lastName" title="Last Name [required]" 
						required="required" style="width: 250px"> &nbsp;
					<label for="city" style="width: 100px">City: </label> 
					<select id="city" name="city"	class="basic" style="width: 250px">
						<option value="-1">Select City</option>
						<option value="San Jose">San Jose</option>
						<option value="Santa Clara">Santa Clara</option>
						<option value="Menlo Park">Menlo Park</option>
						<option value="San Francisco">San Francisco</option>
						<option value="Sunnyvale">Sunnyvale</option>
						<option value=""></option>
						<option value=""></option>
					</select> &nbsp;
					<label for="state" style="width: 100px">State: </label> 
					<input type="text" id="state" name="state" class="basic" value="CA" 
						style="width: 250px"/> &nbsp;
					<label for="email" style="width: 100px">Email Id: </label> 
					<input id="email" type="email" name="email" title="Email [required]" 
						required="required" style="width: 250px"> &nbsp; 
					<label for="password" style="width: 100px">Password: </label> 
					<input id="password" type="password" name="password" 
						title="Password (At least 5 characters) [required]"
						autocomplete="off" required="required" style="width: 250px"> &nbsp;
					<label for="passwordConfirm" style="width: 100px">Confirm Password: </label> 
					<input id="passwordConfirm" type="password" name="passwordConfirm"
						title="Confirm Password [required]" autocomplete="off" required="required"
						style="width: 250px"> &nbsp;

				</fieldset>
			</form>

		</div>

	</div>
</body>
</html>