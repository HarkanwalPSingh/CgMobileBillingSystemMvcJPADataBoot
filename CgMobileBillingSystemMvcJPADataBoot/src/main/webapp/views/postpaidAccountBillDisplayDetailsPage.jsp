<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    	    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Bill Details Page</title>
</head>
<body bgcolor="#E6E6FA">
<div align="left">
		<h5 align="center">
			<font color="brown" size="4"> Details of bills:
				<table border="1" align="center">
					<tr>
						<th>Mobile No</th>
						<th>Bill ID</th>
						<th>Plan</th>
						<th>Service Tax</th>
						<th>VAT</th>
						<th>Total Bill Amount</th>
					</tr>
					<c:forEach items="${bills}" var="bill">
						<tr>
							<td>${bill.postPaidAccount.mobileNo}</td>
							<td>${bill.billID}</td>
							<td>${bill.billMonth}</td>
							<td>${bill.servicesTax}</td>
							<td>${bill.vat}</td>
							<td>${bill.totalBillAmount}</td>
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