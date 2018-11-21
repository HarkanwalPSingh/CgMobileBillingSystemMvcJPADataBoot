<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Postpaid Account Details</title>
</head>
<body bgcolor="#E6E6FA">
	<div align="left">
		<h5 align="center">
			<font color="brown" size="4"> Details of Postpaid Accounts for
				Customer ID:
				<table border="1" align="center">
					<tr>
						<th>CustomerID</th>
						<th>MobileNo</th>
						<th>Plan</th>
					</tr>
					<c:forEach items="${postpaidAccounts}" var="postpaidAccount">
						<tr>
							<td>${postpaidAccount.customer.customerID}</td>
							<td>${postpaidAccount.mobileNo}</td>
							<td>${postpaidAccount.plan.planID}
								${postpaidAccount.plan.planName}</td>
						</tr>
					</c:forEach>
				</table>
			</font>
		</h5>
		<p>
			<a href="/mobilebilling">Return To Home Page</a>
	</div>
</body>
</html>