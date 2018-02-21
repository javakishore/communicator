<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CRM-Login</title>
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/main.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
 
</head>

<body class="login">
	<div class="login-block">
		<div class="addProjectform">
			<div class="logo">
				<a href="javascript:void(0);" title="PruBSN">
					<img alt="PruBSN" src="resources/images/PruBSN_logo.png">
				</a>
			</div>
			<div class="clear"></div>
			<div class="login-body">
				<form:form action="login" method="post" commandName="login"
					cssClass="form-horizontal" id="frm">
					<c:if test="${!empty invalidUser}">
						<div class="form-group">
							<div
								class="col-sm-offset-10 col-sm-4 alert alert-danger ErrorMessage" style="width:300px">
								<span style="width:300px">${invalidUser}</span>
							</div>
						</div>
					</c:if>
					
					<div class="form-group" id="msg" style="display:none">
							<div
								class="col-sm-offset-10 col-sm-4 alert alert-danger ErrorMessage" style="width:300px">
								<span style="width:300px">Enter credentials </span>
							</div>
						</div>
						
					<div class="form-group">
						
						<div class="form-item">
							<form:input path="userName" cssClass="form-control" placeholder="Username" />
							<div class="col-sm-10">
								<form:errors path="userName"
									cssClass="alert alert-danger ErrorMessage" />
							</div>
						</div>
					</div>
					<div class="form-group">
						
						<div class="form-item">
							<form:password path="password" cssClass="form-control" placeholder="Password" />
							<div class="col-sm-10">
								<form:errors path="password"
									cssClass="alert alert-danger ErrorMessage"  />
							</div>
						</div>
					</div>
					
					<!-- <div class="form-group lang-box">
						<span>Language</span> 
						<select name="category">
							<option value="english">English</option>
							<option value="bhasa">Bhasa</option>
						</select>
					</div> -->
	
					<div class="form-group">
						<div class="col-sm-12 btn-box">
							<button type="button" onclick="valdateLogin()" class="btn btn-lg btn-block btn-login">Login</button>
						</div>
					</div>
				
				<%--	<div class="form-group register">
						<div class="col-sm-12">
							<a href="/crm/register">Register Here</a>
						</div>
					</div>
							
				 	<div class="form-group">
						<label class="control-label col-sm-6" for="language">Language:</label>
						<!-- <label class="control-label col-sm-4">Language :</label> -->
						<div class="col-sm-5">
							<form:select path="language" id="language" cssClass="form-control">
								<form:option value="">Select Language </form:option>
								<form:option value="english">English</form:option>
								<form:option value="bhasa">Bhasa</form:option>
							</form:select>
	
						</div>
					</div> --%>
					<%--  <div class="form-group">
					 <label class="control-label col-sm-6" for="Language">Language:</label>
						<!-- <label class="control-label col-sm-4">Language :</label> -->
						<div class="col-sm-5">
							<form:select id="language" cssClass="form-control">
								<form:option value="">Select Language </form:option>
								<form:option value="english">English</form:option>
								<form:option value="bhasa">Bhasa</form:option>
							</form:select>
	
						</div>
					</div>  --%>
	
	
				</form:form>
			</div>
		</div>
	</div>
	
	
</body>
</html>