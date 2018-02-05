

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
				<li><a class="back-btn" href="/crm/mymessages">Back</a></li>
			</ul>
		</div> -->
		<h2 class="title" style="color:white">View Message ${testMessage}</h2>
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
						<form:form name="addmessage" action="editmessage" method="post"
							commandName="message" enctype="multipart/form-data"
							cssClass="form-horizontal" onSubmit="return validateForm()">
						<div class="form-group">
							<label class="control-label col-sm-2">Region :</label>
							<div class="col-sm-5">
								<select name="zone" id="zone" class="form-control" multiple="multiple" name="category"
										class="form-control" readonly="readonly" style="background-color: #d3d3d3;">
									<c:forEach items="${zoneList}" var="zone">
										<option value="${zone}" selected="selected"> ${zone}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Channel :</label>
							<div class="col-sm-5">
								<select name="zone" id="zone" class="form-control" name="category"
										class="form-control" readonly="readonly" style="background-color: #d3d3d3;">
									<c:forEach items="${channelList}" var="channel">
										<option value="${channel}" selected="selected"> ${channel}</option>
									</c:forEach>
								</select>
							</div>
						</div>
							<div class="form-group">
								<label class="control-label col-sm-2">Category :</label>
								<div class="col-sm-5">
									<input style="background-color: #d3d3d3;" name="category" style="background-color: #d3d3d3;"
										class="form-control" value="${categoryName}"
										readonly="readonly" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2">Reference No:</label>
								<div class="col-sm-5">
									<input type="text" value="${referenceNo}" class="form-control" style="background-color: #d3d3d3;"
										readonly="readonly" class="form-control" />
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