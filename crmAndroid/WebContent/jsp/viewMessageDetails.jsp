

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Messages ${userDetails}</title>
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/bootstrap-datetimepicker.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/main.css"
	rel="stylesheet" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/bootstrap-datetimepicker.js"></script>

<script>
	function validateForm() {
		if (document.addmessage.headlineName.value == "") {
			alert("Please enter Headline Name");
			document.addmessage.headlineName.focus();
			return false;
		} else if (document.addmessage.messageName.value == "") {
			alert("Please enter Message");
			document.addmessage.messageName.focus();
			return false;
		} else if (document.addmessage.headlineName1.value == "") {
			alert("Please enter Headline Name");
			document.addmessage.headlineName1.focus();
			return false;
		} else if (document.addmessage.messageName1.value == "") {
			alert("Please enter Message");
			document.addmessage.messageName1.focus();
			return false;
		} else if (document.addmessage.expdate.value == "") {
			alert("Please enter expiry date");
			document.addmessage.expdate.focus();
			return false;
		} else if (document.addmessage.effdate.value == "") {
			alert("Please enter effective date");
			document.addmessage.effdate.focus();
			return false;
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
				<a href="javascript:void(0);" title="PruBSN"> <img height="45"
					width="140" alt="PruBSN" src="../resources/images/PruBSN_logo.png">
				</a>
			</div>
			<div class="right-block pull-right">
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
				<li><a class="back-btn" href="/crm/mymessages">Back</a></li>
			</ul>
		</div> -->
		<h2 class="title" style="color:white">Edit Message ${testMessage}</h2>
	</div>
	<div class="clear_both">&nbsp;</div>


	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2" style="background-color:#00AA9A">
				<div class="container-wrap" style="height: 53em;">
					<c:choose>
						<c:when test="${user.userType=='admin'}">
							<ul class="main-nav" style="border:none;">
								<!-- <li><a href="/crm/addcategory">Add Category</a></li> -->
				
								<li><a id="anchorfontcolor1" style="color:white;" href="/crmCmsAdmin/addmessage">Add Message</a></li>
								<li><a id="anchorfontcolor2" style="color:white;" href="/crmCmsAdmin/mymessages">Message List</a></li>
								<li><a id="anchorfontcolor3" style="color:white;" href="/crmCmsAdmin/messagesStatus">Messages Status</a></li>
								<li><a id="anchorfontcolor4" style="color:white;" href="/crmCmsAdmin/readmessages">Read Messages</a></li>
								<li><a id="anchorfontcolor5" style="color:white;" href="/crmCmsAdmin/pendingmessages">Pending Messages</a></li>
								<li><a id="anchorfontcolor6" style="color:white;" href="/crmCmsAdmin/myCategory">Category List</a></li>
								<li><a id="anchorfontcolor7" style="color:white;" href="/crmCmsAdmin/myChannel">Channel List</a></li>
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
						<form:form name="addmessage" action="editmessage" method="post"
							commandName="message" enctype="multipart/form-data"
							cssClass="form-horizontal" onSubmit="return validateForm()">
							<%-- <div class="form-group">
								<label class="control-label col-sm-2">Zone :</label>
								<div class="col-sm-5">

									 <select name="zone" class="form-control" multiple="multiple" readonly="readonly">
										<option>All</option>
										<option>North </option>
										<option>east</option>
										<option>west</option>
										<option>south</option>
									</select>
									<form:select path="category.categoryId" cssClass="form-control"
										id="categoryId">
										<form:option value=""></form:option>
										<c:if test="${!empty categoryList}">
											<c:forEach items="${categoryList}" var="category">
												<form:option value="${category.categoryId}">${category.categoryName}</form:option>
											</c:forEach>
										</c:if>
									</form:select>
									<form:errors path="category.categoryId"
										cssClass="alert alert-danger ErrorMessage" />

								</div>
							</div> --%>
							<%-- <div class="form-group">
								<label class="control-label col-sm-2">Channel :</label>
								<div class="col-sm-5">

									 <select name="channel" class="form-control" multiple="multiple" readonly="readonly">
										<option>All</option>
										<option>PBTB agents</option>
										<option>PBTB leaders</option>
										<option>PAMB agents</option>
										<option>PAMB leaders</option>
									</select>
									<form:select path="category.categoryId" cssClass="form-control"
										id="categoryId">
										<form:option value=""></form:option>
										<c:if test="${!empty categoryList}">
											<c:forEach items="${categoryList}" var="category">
												<form:option value="${category.categoryId}">${category.categoryName}</form:option>
											</c:forEach>
										</c:if>
									</form:select>
									<form:errors path="category.categoryId"
										cssClass="alert alert-danger ErrorMessage" />

								</div>
							</div> --%>


							<div class="form-group">
								<label class="control-label col-sm-2">Category :</label>

								<div class="col-sm-5">




									<input style="background-color: #d3d3d3;" name="category"
										class="form-control" value="${categoryName}"
										readonly="readonly" />


									<%-- <select id="categoryId" class="form-control"
										name="category.categoryId">
										<option value="">Select category</option>
										<c:forEach items="${categoryList}" var="category">

											<c:choose>
												<c:when
													test="${category.categoryId == messageEng.category_Id}">
													<option value="${category.categoryId}" selected  readonly="readonly">
														${category.categoryName}</option>
												</c:when>
												<c:otherwise>
													<option value="${category.categoryId}" selected>
														${category.categoryName}</option>
												</c:otherwise>
											</c:choose>


										</c:forEach>
									</select> --%>



								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">English :</label>
							</div>
							<div class="form-group">

								<div class="col-sm-5">
									<input style="background-color: #d3d3d3;" type="hidden"
										name="messageId" class="form-control"
										value="${messageEng.msgID}" readonly="readonly" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Headline:</label>
								<div class="col-sm-5">
									<input style="background-color: #d3d3d3;" type="text"
										name="headlineName" class="form-control"
										value="${messageEng.msgTitle}" readonly="readonly" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Message :</label>
								<div class="col-sm-5">
									<textarea style="background-color: #d3d3d3;" name="messageName"
										class="form-control" rows="5" readonly="readonly">${messageEng.msgData}</textarea>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Image:</label>
								<%-- <div class="col-sm-5">
									<img src="${CMS_WD_URL}${messageEng.msgLink2}"/>
								</div>  --%>

								<div class="col-sm-5">
									<img width="200px" height="200px" class="img-responsive"
										src="data:image/png;base64,${messageEng.msgLink2}" alt="">
								</div>

							</div>
							<div class="form-group">
								<!-- <label class="control-label col-sm-2">Document:</label> -->
								<div class="col-sm-5">
									<%-- <a href="${CMS_WD_URL}${messageEng.msgLink}">${CMS_WD_URL}${messageEng.msgLink}</a> --%>

								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Bahasa :</label>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Headline:</label>
								<div class="col-sm-5">
									<input style="background-color: #d3d3d3;"
										text" name="headlineName1" class="form-control"
										value="${messageBha.msgTitle}" readonly="readonly" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Message:</label>
								<div class="col-sm-5">
									<textarea style="background-color: #d3d3d3;"
										messageName1" class="form-control" rows="5"
										readonly="readonly">${messageBha.msgData}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2">Image:</label>
								<div class="col-sm-5">

									<img width="200px" height="200px" class="img-responsive"
										src="data:image/png;base64,${messageBha.msgLink2}" alt="">

								</div>
							</div>
							<div class="form-group">
								<!-- <label class="control-label col-sm-2">Document upload:</label> -->
								<div class="col-sm-5">
									<%-- <a href="${CMS_WD_URL}${messageBha.msgLink}">
								${CMS_WD_URL}${messageBha.msgLink}</a> --%>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">ExpiryDate & Time
									:</label>
								<div class="col-sm-5">
									<input style="background-color: #d3d3d3;" text" name="expdate"
										value="${messageEng.expiryDate}" placeholder="DD-MM-YYYY"
										class="form_datetime form-control" readonly="readonly" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Effective Date &
									Time :</label>
								<div class="col-sm-5">
									<input style="background-color: #d3d3d3;" text" name="effdate"
										value="${messageEng.effectiveDate}" placeholder="DD-MM-YYYY"
										class="form_datetime form-control" readonly="readonly" />
								</div>
							</div>


							<div class="form-group">&nbsp;</div>
							<div class="clear"></div>
							<c:if test="${ismessageAdded}">
								<div class="alert alert-success SuccessMessage col-md-5">
									<span>Message Added Successfully</span>
								</div>
							</c:if>
						</form:form>
					</div>
					<div class="clear"></div>
				</div>

			</div>

		</div>
	</div>

	<script type="text/javascript">
	$(".form_datetime").datetimepicker({
		format: 'dd-mm-yyyy - HH:ii',
		autoclose: true,
		showMeridian: true,
	});
	
	
	
	
	
		function readURL(input, id) {
		  if (input.files && input.files[0]) {
		            var reader = new FileReader();
		            
		            reader.onload = function (e) {
		                $(id).attr('src', e.target.result);
		    $(id).removeClass('add');
		            }
		            
		            reader.readAsDataURL(input.files[0]);
		        }
		    }
		    
		    $("#img1").change(function(){
			  $("#blah1").removeClass('add');
		        readURL(this, "#blah1");
		    });
		    
		    $("#img3").change(function(){
				  $("#blah3").removeClass('add');
			        readURL(this, "#blah3");
			    });
		    
		    function ValidateFileUpload() {
		        var fuData = document.getElementById('img3');
		        var FileUploadPath = fuData.value;

		//To check if user upload any file
		        if (FileUploadPath == '') {
		          //  alert("Please upload an image");

		        } else {
		            var Extension = FileUploadPath.substring(
		                    FileUploadPath.lastIndexOf('.') + 1).toLowerCase();

		//The file uploaded is an image

		if (Extension == "gif" || Extension == "png" || Extension == "bmp"
		                    || Extension == "jpeg" || Extension == "jpg") {

		// To Display
		                if (fuData.files && fuData.files[0]) {
		                    var reader = new FileReader();

		                    reader.onload = function(e) {
		                        $('#blah3').attr('src', e.target.result);
		                    }

		                    reader.readAsDataURL(fuData.files[0]);
		                }

		            } 

		//The file upload is NOT an image
		else {
		               // alert("Photo only allows file types of GIF, PNG, JPG, JPEG and BMP. ");

		            }
		        }
		    }
	</script>

</body>
</html>