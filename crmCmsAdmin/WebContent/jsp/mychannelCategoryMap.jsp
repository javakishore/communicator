<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Map Channel Category</title>
<link type="text/css" href="<%=request.getContextPath() %>/resources/css/main.css" rel="stylesheet"/>
<link type="text/css" href="<%=request.getContextPath() %>/resources/css/bootstrap.min.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.11.3.min.js"></script>
<style>
.error {
    color: #ff0000;
    font-style: italic;
    position:relative;
    top:-11px;    
}
.setMargin{
margin-top: -18px;
}
</style>
<script type="text/javascript">

function validateForm() {
	if (document.savechannelcategory.channelName.value == "") {
		alert("Please enter Channel Name");
		document.savechannelcategory.channelName.focus();
		return false;
	} 
}

function deleteMapping(deleteId,channelName,categoryname)
{
	
 var r = confirm("Are you sure want to unassign the "+channelName+" and "+categoryname+" !");
 if (r == true)
 {
  location="/crmCmsAdmin/deletechannelcategory/"+deleteId;
 }
 else
 {
   //do nothing
 }
 
}

</script>

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
					<div class="clear_both">&nbsp;</div>
				</div>
				<div class="clear_both">&nbsp;</div>
			</div>
		</div>
		
		<div class="title-bar" style="    background-color:#E70028;margin: 0px;">
        	<h2 class="title" style="color:white">Assign The Channel To Category</h2>
        </div>
		<div class="clear_both">&nbsp;</div>
		
	<div class="container-fluid">
		<div class="row">
		   <div class="col-md-2" style="background-color:#00AA9A">
				<jsp:include page="leftMenu.jsp"></jsp:include>
			</div>
			<div class="col-md-10">
				<div class="addCategoryform">
				<div class="">
				<br/> <form:form name="savechannelcategory" action="savechannelcategory" method="post" commandName="channelcategory" cssClass="form-horizontal" onSubmit="return validateForm()">
				<div class="form-group">
					<label class="control-label col-sm-2">Select Channel :</label>
					<div class="col-sm-3">
						<form:select path="" cssClass="form-control" id="channelId" name="channelId" multiple="multiple" >
							 <c:if test="${!empty channelList}">
								<c:forEach items="${channelList}" var="channel">
									<form:option value="${channel.channelId}">${channel.channelName}</form:option>
								</c:forEach>
							</c:if>
						</form:select>  
					</div>
					<label class="control-label col-sm-2">Select Category :</label>
					<div class="col-sm-3">
									<form:select path="" cssClass="form-control" id="categoryId" name="categoryId" multiple="multiple">
										<c:if test="${!empty categoryList}">
											<c:forEach items="${categoryList}" var="category">
												<form:option value="${category.categoryId}">${category.categoryName}</form:option>
											</c:forEach>
										</c:if>
									</form:select>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group" style="text-align: center;">
								<div class="col-sm-offset-2 col-sm-12 btn-block">
									<input type="submit" value="Assign" name="submit" class="btn btn-logout" style="float:left;margin-left:25%;">
								</div>
							</div>
				<div class="form-group">
					<c:if test="${isAdded}">
						<div class="alert alert-success SuccessMessage col-md-3">
							<span>Channel successfully assigned to category</span>
						</div>
					</c:if>
					<c:if test="${isDuplicate}">
						<div class="col-sm-3 alert alert-danger ErrorMessage">
							<span>Channel already assigned to category</span>
						</div>
					</c:if>
				</div>
			  </form:form>
			  </div>
			  
			  <div class="col-md-10">

				<h3>Channel Category List ${callToCat}</h3>
				<div class="ibox-content">
					<c:if test="${!empty channelList}">
				 	<table border="0" class="table table-striped table-bordered">
						<tr>
						<th width="25" align="center">Map ID</th>
						<th width="25" align="center">Channel Name</th>
						<th width="25" align="center">Category Name</th>
						<th width="25" align="center">Action</th>
						</tr>
						<c:forEach items="${channelCategoryList}" var="channelCategory">
						<tr>
							<td>${channelCategory.map_Id}</td>
							<td>${channelCategory.channelName}</td>
							<td>${channelCategory.categoryName}</td>
							<td align="center">
								<a href="javascript:deleteMapping('${channelCategory.map_Id}','${channelCategory.channelName}','${channelCategory.categoryName}');">UnAssign</a>
							</td>
							</tr>
					 	</c:forEach>
					</table>
					</c:if>
				</div>
			</div>
		</div>
	   </div>	
	 </div>
	</div>
	
</body>
</html>