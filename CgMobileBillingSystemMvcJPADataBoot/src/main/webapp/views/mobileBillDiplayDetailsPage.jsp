<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Monthly Mobile Bill</title>
</head>
<body bgcolor="#E6E6FA">>
<div align="left">
		<h5 align="center">
			<font color="brown" size="4"> Bill details for month ${bill.billMonth} for Mobile Number ${bill.postPaidAccount.mobileNo}<br>
				<table border="1" align="center">
					<tr>
						<th>Bill ID</th>
						<th>Services Tax</th>
						<th>VAT</th>
						<th>Total Bill Amount</th>
					</tr>
						<tr>
							<td>${bill.billID}</td>
							<td>${bill.servicesTax}</td>
							<td>${bill.vat}</td>
							<td>${bill.totalBillAmount}</td>
						</tr>
				</table>
			</font>
		</h5>
		<p>
			<a href="/mobilebilling">Return To Home Page</a>
	</div>

</body>
</html>