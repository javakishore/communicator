<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Message ${userDetails}</title>
<link type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet" />
<link type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap-datetimepicker.css" rel="stylesheet" />
<link type="text/css" href="<%=request.getContextPath()%>/resources/css/main.css" rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/bootstrap-datetimepicker.js"></script>

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
		} /* else if (document.addmessage.headlineName1.value == "") {
			alert("Please enter Headline Name");
			document.addmessage.headlineName1.focus();
			return false;
		} else if (document.addmessage.messageName1.value == "") {
			alert("Please enter Message");
			document.addmessage.messageName1.focus();
			return false;
		} */ else if (document.addmessage.expdate.value == "") {
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
<body onLoad="bodyload();">
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
		<h2 class="title" style="color:white">Edit Message ${testMessage}</h2>
	</div>
	<div class="clear_both">&nbsp;</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2" style="background-color:#00AA9A">
				<jsp:include page="leftMenu.jsp"></jsp:include>
			</div>
			<div class="col-md-10">
				<div class="addCategoryform">
				<br/>	<form:form name="addmessage" action="editmessage" method="post" commandName="message" enctype="multipart/form-data" cssClass="form-horizontal" onSubmit="return validateForm()">
						<input type="hidden" name="messageId" class="form-control" value="${messageEng.msgID}" />
						<input type="hidden" name="selectedZoneIds" id="selectedZoneIds" class="form-control" value="${zoneIds}"/>
						<input type="hidden" name="selectedCHANNEL_IDs" id="selectedCHANNEL_IDs" class="form-control" value="${CHANNEL_IDs}"/>
						<div class="form-group">
							<label class="control-label col-sm-2">Zone :</label>
							<div class="col-sm-5">
								<select name="zone" id="zone" class="form-control" multiple="multiple">
									<c:forEach items="${zoneList}" var="zone">
										<option value="${zone.zoneId}"> ${zone.zoneName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Channel :</label>
							<div class="col-sm-5">
								<select name="channel" id="channel" class="form-control" multiple="multiple">
									<c:forEach items="${channelList}" var="channel">
										<option value="${channel.channelId}"> ${channel.channelName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2"> Category :</label>
							<div class="col-sm-5">
								<select id="categoryId" class="form-control" name="category.categoryId">
									<c:forEach items="${categoryList}" var="category">
										<c:choose>
											<c:when test="${category.categoryId == categoryId}">
												<option value="${category.categoryId}" selected> ${category.categoryName}</option>
											</c:when>
											<c:otherwise>
												<option value="${category.categoryId}"> ${category.categoryName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
								<label class="control-label col-sm-2">Reference No:</label>
								<div class="col-sm-5">
									<input type="text" name="referenceNo" class="form-control" value="${referenceNo }" />
								</div>
							</div>
						<div class="form-group">
							<label class="control-label col-sm-2">English :</label>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Headline:</label>
							<div class="col-sm-5">
								<input type="text" name="headlineName" class="form-control" value="${messageEng.msgTitle}" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Message :</label>
							<div class="col-sm-5">
								<textarea name="messageName" class="form-control" rows="5">${messageEng.msgData}</textarea>
							</div>
						</div>
						<div class="form-group" id="div1">
							<label class="control-label col-sm-2">Image upload :${encodedImgDataVal}</label>
							<div class="col-sm-5">
								<input type="file" name="file_image" id="img1" value="" onchange="return ValidateFileUpload()" accept='image/*' />
								<!-- ${messageEng.msgLink2} -->
								<!-- <img id="blah1" class="add" src="" alt="Image" height="100" /> -->
								<img src="data:image/jpg;base64,${filePath}" />
								<%-- <img id="blah1" src="/EditImage/${imgfilename}" alt="Image" height="100" /> --%>
							</div>
						</div>
						<div class="form-group" id="show_div" style="display: none;">
							<label class="control-label col-sm-2">Video URL :</label>
							<div class="col-sm-5">
								<input type="text" name="videoURL1" id="vid1" value=">${messageEng.msgLink2}" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Document upload:</label>
							<div class="col-sm-5">
								<input type="file" name="file" value="${messageEng.msgLink}" />
								<label >${isattached}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Bahasa :</label>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Headline:</label>
							<div class="col-sm-5">
								<input type="text" name="headlineName1" class="form-control" value="${messageBha.msgTitle}" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Message:</label>
							<div class="col-sm-5">
								<textarea name="messageName1" class="form-control" rows="5">${messageBha.msgData}</textarea>
							</div>
						</div>
						<div class="form-group" id="div2">
							<label class="control-label col-sm-2">Image upload :</label>
							<div class="col-sm-5">
								<input type="file" name="file2_image" id="img3" value="${messageBha.msgLink2}" />
							</div>
						</div>
						<div class="form-group" id="show_div2" style="display: none;">
							<label class="control-label col-sm-2">Video URL :</label>
							<div class="col-sm-5">
								<input type="text" name="videoURL2" id="vid1" value="${messageBha.msgLink2}" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Document upload:</label>
							<div class="col-sm-5">
								<input type="file" name="file2" value="${messageBha.msgLink}" />
								<label >${isattached1}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Effective Date & Time :</label>
							<div class="col-sm-5">
								<input type="text" name="effdate" value="" placeholder="DD-MM-YYYY" class="form_datetime form-control" id="effdate" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">ExpiryDate & Time :</label>
							<div class="col-sm-5">
								<input type="text" name="expdate" value="" placeholder="DD-MM-YYYY" class="form_datetime form-control" id="expdate" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10 btn-block">
								<input type="submit" value="Broadcast" name="submit" class="btn btn-logout" />
							</div>
						</div>
						<div class="clear"></div>
						<c:if test="${ismessageAdded}">
							<div class="alert alert-success SuccessMessage col-md-5">
								<span>Message Added Successfully</span>
							</div>
						</c:if>
					</form:form>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
/* Update multiple selection elements starts : Prashant Shinde */	
$(document).ready(function() {
	var selectedZones = document.getElementById("selectedZoneIds").value;
	var selectedChannels = document.getElementById("selectedCHANNEL_IDs").value;
	if(selectedZones != ""){
		fetchZoneSelections(selectedZones);	
	}
	if(selectedChannels != ""){
		fetchChannelSelections(selectedChannels);	
	}
	if ($('#categoryId').val() === '6') {
		//alert("Load");
		$("#show_div").show();
		$("#show_div2").show();
		$("#doc_div").hide();
		$("#div1").hide();
		$("#div2").hide();
	} else {
		$("#show_div").hide();
		$("#show_div2").hide();
		$("#div1").show();
		$("#div2").show();
	}
	$('#categoryId').change(function() {
		if ($(this).val() === '6') {
			//Alert("Load cat");
			$("#show_div").show();
			$("#show_div2").show();
			$("#doc_div").hide();
			$("#div1").hide();
			$("#div2").hide();
		} else {
			$("#show_div").hide();
			$("#show_div2").hide();
			$("#div1").show();
			$("#div2").show();
		}
	});
});	

function bodyload(){
	
		
		if ($('#categoryId').val() === '6') {
			//alert("Load");
			$("#show_div").show();
			$("#show_div2").show();
			$("#doc_div").hide();
			$("#div1").hide();
			$("#div2").hide();
		} else {
			$("#show_div").hide();
			$("#show_div2").hide();
			$("#div1").show();
			$("#div2").show();
		}
	
	
}
/*Checks & updates the selected zones multiple select*/
function fetchZoneSelections(zoneSelections){
	var totalSelections = zoneSelections.split(",");
	for(var j=0; j< totalSelections.length;j++){
    	var optionVal = eval(totalSelections[j]);
    	$("#zone option[value='" + optionVal + "']").attr("selected", "selected");	
    }
}
/*Checks & updates the selected channels in multiple select*/
function fetchChannelSelections(channelSelections){
	var totalSelections = channelSelections.split(",");
	for(var j=0; j< totalSelections.length;j++){
    	var optionVal = eval(totalSelections[j]);
    	$("#channel option[value='" + optionVal + "']").attr("selected", "selected");	
    }
}
/*NOT USED CURRENTLY*/
function fetchZoneSelections_withEqualityCheck(zoneSelections){
	var totalSelections = zoneSelections.split(",");
	var zoneSelect = document.getElementById("zone");
	for(var i = 0; i < zoneSelect.length; i++){
		var iteratedOptionVal = zoneSelect[i].value;
		for(var j=0; j< totalSelections.length; j++){
      		if(totalSelections[j] == iteratedOptionVal){
      			$("#zone option[value='" + iteratedOptionVal + "']").attr("selected", "selected");
      		}
      	}
	}
}
/* Update multiple selection elements ends : Prashant Shinde*/

$(function() {
	setTimeout(function(){ 
		$(".form_datetime").datetimepicker({
			format : 'dd-mm-yyyy hh:ii:ss',
			autoclose : true,
			showMeridian : true,
		});
		}, 1000);
		$('#expdate').val("${messageEng.expiryDate}");
		$('#effdate').val("${messageEng.effectiveDate}");
		$('#categoryId').change(function() {
			if ($(this).val() === '6') {
				$("#show_div").show();
				$("#show_div2").show();
				$("#doc_div").hide();
				$("#div1").hide();
				$("#div2").hide();
			} else {
				$("#show_div").hide();
				$("#show_div2").hide();
				$("#div1").show();
				$("#div2").show();
			}
		});
	});

	function readURL(input, id) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$(id).attr('src', e.target.result);
				$(id).removeClass('add');
			}

			reader.readAsDataURL(input.files[0]);
		}
	}

	$("#img1").change(function() {
		$("#blah1").removeClass('add');
		readURL(this, "#blah1");
	});

	$("#img3").change(function() {
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
			var Extension = FileUploadPath.substring( FileUploadPath.lastIndexOf('.') + 1).toLowerCase();
			//The file uploaded is an image
			if (Extension == "gif" || Extension == "png" || Extension == "bmp" || Extension == "jpeg" || Extension == "jpg") {
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