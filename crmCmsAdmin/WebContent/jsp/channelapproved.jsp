
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pending Message</title>
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/main.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
<style>
.error {
	color: #ff0000;
	font-style: italic;
	position: relative;
	top: -11px;
}

.setMargin {
	margin-top: -18px;
}
</style>

<script>
$(document).ready(function(){
	
	 $("#login").mouseenter(function(){
		  
		 $("#login").css("background-color","#E70028");
		 $("#login").css("color","white");
	 });
	 $("#login").mouseleave(function(){
		  
		 $("#login").css("background-color","white");
		 $("#login").css("color","#444");
		 
	 });
	 
	 
	 $("#anchorfontcolor1").mouseenter(function(){
		  
		 $("#anchorfontcolor1").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor1").mouseleave(function(){
		   
		 $("#anchorfontcolor1").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor2").mouseenter(function(){
		  
		 $("#anchorfontcolor2").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor2").mouseleave(function(){
		   
		 $("#anchorfontcolor2").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor3").mouseenter(function(){
		  
		 $("#anchorfontcolor3").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor3").mouseleave(function(){
		   
		 $("#anchorfontcolor3").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor4").mouseenter(function(){
		  
		 $("#anchorfontcolor4").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor4").mouseleave(function(){
		   
		 $("#anchorfontcolor4").css("color","white");
		 
	 });
	 
	 $("#anchorfontcolor5").mouseenter(function(){
		  
		 $("#anchorfontcolor5").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor5").mouseleave(function(){
		   
		 $("#anchorfontcolor5").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor6").mouseenter(function(){
		  
		 $("#anchorfontcolor6").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor6").mouseleave(function(){
		   
		 $("#anchorfontcolor6").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor7").mouseenter(function(){
		  
		 $("#anchorfontcolor7").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor7").mouseleave(function(){
		   
		 $("#anchorfontcolor7").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor8").mouseenter(function(){
		  
		 $("#anchorfontcolor8").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor8").mouseleave(function(){
		   
		 $("#anchorfontcolor8").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor9").mouseenter(function(){
		  
		 $("#anchorfontcolor9").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor9").mouseleave(function(){
		   
		 $("#anchorfontcolor9").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor10").mouseenter(function(){
		  
		 $("#anchorfontcolor10").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor10").mouseleave(function(){
		   
		 $("#anchorfontcolor10").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor11").mouseenter(function(){
		  
		 $("#anchorfontcolor11").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor11").mouseleave(function(){
		   
		 $("#anchorfontcolor11").css("color","white");
		 
	 });
	 
	 
	 $("#anchorfontcolor12").mouseenter(function(){
		  
		 $("#anchorfontcolor12").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor12").mouseleave(function(){
		   
		 $("#anchorfontcolor12").css("color","white");
		 
	 });
	 
	 $("#anchorfontcolor13").mouseenter(function(){
		  
		 $("#anchorfontcolor13").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor13").mouseleave(function(){
		   
		 $("#anchorfontcolor13").css("color","white");
		 
	 });
	 
	 $("#anchorfontcolor14").mouseenter(function(){
		  
		 $("#anchorfontcolor14").css("color","#E70028");
	 });
	 
	 $("#anchorfontcolor14").mouseleave(function(){
		   
		 $("#anchorfontcolor14").css("color","white");
		 
	 });
  });
</script>

</head>
<body>

	<div class="header">
		<div class="header-wrap" style="padding: 0px 0px;">
			<div class="logo pull-left">
				<a href="javascript:void(0);" title="PruBSN"> 
				<img height="100%" width="100%" alt="PruBSN" src="resources/images/PruBSN_logo.png" style="padding-top:1%;">
				</a>
			</div>
			<div class="right-block pull-right" style="padding: 10px 15px;">
				<h3 class="user-intro pull-left">Welcome,${user.userName}!</h3>
				<div class="logout-box pull-left">
					<a id="login" style="color:#3c3434;background-color:white;border-radius:5px;border-color:#E70028" class="btn btn-logout anchor3" href="/crmCmsAdmin/logout.htm">Logout</a></div>				</div>
				</div>
				<div class="clear_both">&nbsp;</div>
			</div>
			<div class="clear_both">&nbsp;</div>
		</div>
	</div>

	<div class="title-bar" style="    background-color:#E70028;margin: 0px;">
		<!-- <div class="tools-box">
			<ul class="tool-list">
				<li><a class="back-btn" href="/crm/back">Back</a></li>
			</ul>
		</div> -->
		<h2 class="title" style="color:white">Messages</h2>
	</div>
	<div class="clear_both">&nbsp;</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2" style="background-color:#00AA9A">
				<jsp:include page="leftMenu.jsp"></jsp:include>
			</div>

			<div class="col-md-10">Channel approved Successfully</div>

		</div>
	</div>



</body>
</html>