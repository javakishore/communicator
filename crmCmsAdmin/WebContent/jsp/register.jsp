<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register User</title>
<link type="text/css" href="<%=request.getContextPath() %>/resources/css/main.css" rel="stylesheet"/>
<link type="text/css" href="<%=request.getContextPath() %>/resources/css/bootstrap.min.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.11.3.min.js"></script>

</head>
<body>
		<div class="col-md-8 addProjectform">
		<div class="col-md-10">
			<h2>Please Register</h2>
		</div>
		<div class="clear"></div>
		<div class="col-md-10">
        <form:form action="register" method="post" commandName="register" cssClass="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-4">User Name:</label>
				<div class="col-sm-5">
					<form:input path="userName" cssClass="form-control" />
					<form:errors path="userName" cssClass="alert alert-danger ErrorMessage" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-4">Password:</label>
				<div class="col-sm-5">
					<form:password path="password" cssClass="form-control" />
					<form:errors path="password" cssClass="alert alert-danger ErrorMessage" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-4">Confirm Password:</label>
				<div class="col-sm-5">
					<form:password path="confirmPassword" cssClass="form-control" />
					<form:errors path="confirmPassword" cssClass="alert alert-danger ErrorMessage" />
				</div>
			</div>
		  <div class="form-group">
				<label class="control-label col-sm-4">User Type:</label>
				<div class="col-sm-5">
					<form:select path="userType" items="${userTypeList}"
						cssClass="form-control" />
					<form:errors path="userType"> </form:errors>
				</div>
			</div>  
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-10">
					<input type="submit" value="Register" class="btn" />
				</div>
			</div>
		</form:form>
		</div>
    </div>
</body>
</html>