<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.Inquiry"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inquiry Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/inquiry.js"></script>
</head>
<body>

<div class="container"><div class="row"><div class="col-6">

		<h1>Inquiry Management</h1>

			<form id="formInquiry" name="formInquiry">
			 Inquiry Title:
			 			<input id="InquiryTitle" name="InquiryTitle" type="text" class="form-control form-control-sm">
			 <br> Inquiry Description:
			 			<input id="InquiryDesc" name="InquiryDesc" type="text" class="form-control form-control-sm">
			 <br> Contact Number :
						 <input id="ContactNum" name="ContactNum" type="text" class="form-control form-control-sm">
			 <br>
						 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
						 
						 <input type="hidden" id="hidInquiryIDSave" name="hidInquiryIDSave" value="">
			</form>
			
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<br>
<div id="divInquriesGrid">
		 <%
		 Inquiry itemObj = new Inquiry();
		 out.print(itemObj.readInquiries());
		 %>
		 
</div>
</div> </div> </div>
</body>
</html>