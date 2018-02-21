<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Category</title>
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
	if (document.savechannel.channelName.value == "") {
		alert("Please enter Channel Name");
		document.savechannel.channelName.focus();
		return false;
	} 
}

function deleteMyChannel(deleteId,channelName)
{
	
 var r = confirm("Are you sure want to delete the channel  "+channelName+" !");
 if (r == true)
 {
  location="/crmCmsAdmin/deletechannel/"+deleteId;
 }
 else
 {
   //do nothing
 }
 
}

function updateMyChannel()
{
 var channelId = $("#channelId").val();
 var channelName = $("#channelName").val();	
 
 var r = confirm("Are you sure want to update the channel  "+channelName+" !");
 if (r == true)
 {
  location="/crmCmsAdmin/updatechannel/"+channelId+"/"+channelName;
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
			<div class="header-wrap">
				<div class="logo pull-left">
					<a href="javascript:void(0);" title="PruBSN">
						<img height="45" width="140" alt="PruBSN" src="resources/images/PruBSN_logo.png">
					</a>
				</div>
				<div class="right-block pull-right">
					<h3 class="user-intro pull-left">Welcome,${user.userName}!</h3>
					<div class="logout-box pull-left"><a id="login" style="color:#3c3434;background-color:white;border-radius:5px;border-color:#E70028" class="btn btn-logout anchor3" href="/crmCmsAdmin/logout.htm">Logout</a></div>
					<div class="clear_both">&nbsp;</div>
				</div>
				<div class="clear_both">&nbsp;</div>
			</div>
		</div>
		
		<div class="title-bar" style="    background-color:#E70028;margin: 0px;">
        	<h2 class="title" style="color:white">Add Channel</h2>
        </div>
		<div class="clear_both">&nbsp;</div>
		
	<div class="container-fluid">
		<div class="row">
		   <div class="col-md-2" style="background-color:#00AA9A">
				<div class="container-wrap" style="height: 53em;">
					<c:choose>
						<c:when test="${user.userType=='admin'}">
							<ul class="main-nav"  style="border:none;">
								<!-- <li><a href="/crm/addchannel">Add Category</a></li> -->
								<li><a id="anchorfontcolor1" style="color:white;" href="/crmCmsAdmin/addmessage">Add Message</a></li>
									<li><a id="anchorfontcolor2" style="color:white;" href="/crmCmsAdmin/mymessages">Message List</a></li>
								
								<li><a id="anchorfontcolor3" style="color:white;" href="/crmCmsAdmin/messagesStatus">Messages Status</a></li>
								<li><a id="anchorfontcolor4" style="color:white;" href="/crmCmsAdmin/readmessages">Read Messages</a></li>
								<li><a id="anchorfontcolor5" style="color:white;" href="/crmCmsAdmin/pendingmessages">Pending Messages</a></li>
								<li><a id="anchorfontcolor6" style="color:white;" href="/crmCmsAdmin/myCategory">Category List</a></li>
								<li><a id="anchorfontcolor7" style="color:white;" class="active" href="/crmCmsAdmin/myChannel">Channel List</a></li>
								<li><a id="anchorfontcolor8" style="color:white;" href="/crmCmsAdmin/pendingcategory">Pending Category</a></li>
								<li><a id="anchorfontcolor9" style="color:white;" href="/crmCmsAdmin/pendingchannel">Pending Channel</a></li>
								<li><a id="anchorfontcolor10" style="color:white;" href="/crmCmsAdmin/channelCategoryMapping">Channel Category Mapping</a></li>
								<li><a id="anchorfontcolor11" style="color:white;" href="/crmCmsAdmin/likemessage">Like</a></li>
								<li><a id="anchorfontcolor12" style="color:white;" href="/crmCmsAdmin/favouritemessage">Favourite</a></li>
								<li><a id="anchorfontcolor13" style="color:white;" href="/crmCmsAdmin/viewrating">Rate App</a></li>
								<li><a id="anchorfontcolor14" style="color:white;" href="/crmCmsAdmin/loginDetails">Log In Details</a></li>
								
							</ul>
						</c:when>
						<c:otherwise>
							<ul class="main-nav">
								<li><a href="/crmCmsAdmin/addmessage">Add Message</a></li>
								<li><a href="/crmCmsAdmin/mymessages">My Messages</a></li>

							</ul>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-md-10">
				<div class="addCategoryform">
				<div class="">
				<br/><form:form name="savechannel" action="savechannel" method="post" commandName="channel" cssClass="form-horizontal" onSubmit="return validateForm()">
				<div class="form-group">
					<label class="control-label col-sm-2">Channel Name:</label>
					<div class="col-sm-6">
						<input type="hidden" name="channelId" id = "channelId" value="${strChannelId}" />
						<input type="hidden" name="userName" id = "userName" value="${user.userName}" />
						<c:if test="${isView}">
						<input type="text" class="form-control" name="channelName" id = "channelName" value="${strChannelName}" style="background-color: #d3d3d3;" readonly="readonly"/>
						</c:if>
						<c:if test="${!isView}">
						<input type="text" class="form-control" name="channelName" id = "channelName" value="${strChannelName}" />
						</c:if>
					</div>
					<div class="col-sm-4">
						<c:if test="${empty isView}">
						   <button type="submit" value="Add Channel" class="btn">Add Channel</button>
						</c:if>
						<c:if test="${not empty strChannelId and !isView}">
						   <button type="button" value="Add Channel" class="btn" onclick="javascript:updateMyChannel();">Update Channel</button>
						</c:if>
						
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<c:if test="${ischannelAdded}">
						<div class="alert alert-success SuccessMessage col-md-2">
							<span>Channel Added Successfully</span>
						</div>
					</c:if>
					<c:if test="${isDuplicate}">
						<div class="col-sm-3 alert alert-danger ErrorMessage">
							<span>Channel already exist</span>
						</div>
					</c:if>
				</div>
				<div class="form-group">
					<c:if test="${displayMsg}">
						<div class="alert alert-success SuccessMessage col-md-2">
							<span>Channel data Displayed</span>
						</div>
					</c:if>
				</div>
			  </form:form>
			  </div>
			  
			  <div class="col-md-10">

				<h3>Channel List ${callToCat}</h3>
				<div class="ibox-content">
					<c:if test="${!empty channelList}">
				 	<table border="0" class="table table-striped table-bordered">
						<tr>
						<th width="25" align="center">Channel ID</th>
						<th width="25" align="center">Channel Name</th>
						<th width="25" align="center">Status</th>
						<th width="25" align="center">Action</th>
						</tr>
						<c:forEach items="${channelList}" var="channel">
						<tr>
							<td>${channel.channelId}</td>
							<td>${channel.channelName}</td>
							<td>${channel.isapproved}</td>
							<td align="center">
								<a href="/crmCmsAdmin/viewchannel/${channel.channelId}">View</a> / 
								<a href="/crmCmsAdmin/editchannel/${channel.channelId}">Edit</a> / 
								<a href="javascript:deleteMyChannel('${channel.channelId}','${channel.channelName}')">Delete</a>
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