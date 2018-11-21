<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="#E6E6FA">
	<div align="center">
		Monthly Bill Details<br>
		<form>
			<table border=1>
				<tr>
					<td>Enter Customer ID</td>
					<td><input type="text" name="customerID" size="25" /></td>
				</tr>
				<tr>
					<td>Enter Mobile Number</td>
					<td><input type="text" name="mobileNo" size="25" /></td>
				</tr>
				<tr>
					<td>Enter Month</td>
					<td><input type="text" name="billMonth" size="25" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="Submit"
						name="submit" value="Submit" formaction="getMobileBillDetails"></td>
				</tr>
			</table>
			<%-- 	<form action="/generatePdf" method="post">Click here to get Detailed bill in PDF format<br>
			<table border=1>
				<tr>
					<td>Enter Customer ID </td>
					<td><input type="text" name="customerID" size="25" /></td>
				</tr> 
				<tr>
					<td>Enter Mobile Number</td>
					<td><input type="text" name="mobileNo" size="25" /></td>
				</tr> 
				<tr>
					<td>Enter Month </td>
					<td><input type="text" name="billMonth" size="25" /></td>
				</tr> 
				<tr>
					<td colspan="2" align="center"><input type="Submit"
						name="submit" value="Submit"></td>
				</tr>
			</table></form> --%>
			<p>
				<br> ${error}
		</form>
	</div>
</body>
</html>