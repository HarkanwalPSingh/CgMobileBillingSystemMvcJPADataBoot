<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<style>
.button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
}
</style>
</head>
<body bgcolor="#E6E6FA">
	<div align="center">
		<h1>
			<font color="red">Welcome To Mobile Billing</font>
		</h1>
	</div>
		<div align="center">
			<table>
				<tr>
					<td><a href="addCustomer"><button>Add New Customer</button></a></td>
					<td><a href="removeCustomer"><button>Remove Customer</button></a></td>
					<td><a href="customerDetails"><button>Customer Details</button></a></td>
					<td><a href="allCustomerDetails"><button>All Customer Details</button></a></td>
				</tr>
				</table><p><table>
				<tr>
					<td><a href="addPostpaidAccount"><button>Add PostPaid Account</button></a></td>
					<td><a href="postpaidAccountDetails"><button>PostPaid Account Details</button></a></td>
					<td><a href="allPostpaidAccountDetails"><button>All PostPaid Account Details</button></a></td>
					<td><a href="removePostpaidAccount"><button>Deactivate Customer PostPaid Account</button></a></td>
					<td><a href="changePlan"><button>Change PostPaid Account Plan</button></a></td>
				</tr>
				</table><p><table>
				<tr>
					<td><a href="generateBill"><button>Generate Monthly Bill</button></a></td>
					<td><a href="postpaidAccountBillDetails"><button>PostPaid Account All Bill Details</button></a></td>
					<td><a href="mobileBillDetails"><button>PostPaid Account Monthly Bill Details</button></a></td>
				</tr>
			</table>
		</div>
</body>
</html>