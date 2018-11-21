<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Generate Bill</title>
</head>
<body bgcolor="#E6E6FA">
	<div align="center">
		<form action="generateMonthlyMobileBill" method="post">
			<table border=1>
				<tr>
					<td>Enter Customer ID</td>
					<td><input type="text" name="customerID" size="25" /></td>
				</tr>
				<tr>
					<td>Enter Mobile Number:</td>
					<td><input type="text" name="mobileNo" size="25" /></td>
				</tr>
				<tr>
					<td>Enter Bill Month:</td>
					<td><input list="billMonth" name="billMonth"> <datalist id="billMonth">
						<option value="January">
						<option value="February">
						<option value="March">
						<option value="April">
						<option value="May">
						<option value="June">
						<option value="July">
						<option value="August">
						<option value="September">
						<option value="October">
						<option value="November">
						<option value="December">
						</datalist></td>
				</tr>
				<tr>
					<td>Enter Number of Local SMS</td>
					<td><input type="text" name="noOfLocalSMS" size="25" /></td>
				</tr>

				<tr>
					<td>Enter Number of STD SMS</td>
					<td><input type="text" name="noOfStdSMS" size="25" /></td>
				</tr>
				<tr>
					<td>Enter Number of Local Calls</td>
					<td><input type="text" name="noOfLocalCalls" size="25" /></td>
				</tr>
				<tr>
					<td>Enter Number of STD Calls</td>
					<td><input type="text" name="noOfStdCalls" size="25" /></td>
				</tr>
				<tr>
					<td>Enter Number of Internet Data Usage</td>
					<td><input type="text" name="internetDataUsageUnits" size="25" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="Submit"
						name="submit" value="Submit"></td>
				</tr>
			</table>
			<br> ${error}
		</form>
	</div>
</body>
</html>