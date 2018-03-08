<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

<title>Add Message ${userDetails}</title>
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/bootstrap-datetimepicker.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=request.getContextPath()%>/resources/css/main.css"
	rel="stylesheet" />
	 <link href="<%=request.getContextPath()%>/resources/chat/assets/bootstrap-3.3.5/css/bootstrap.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/resources/chat/assets/css/clientWebPage.css" rel="stylesheet"/>
<style>
			  .add{
			   visibility: hidden;
			  }
			  .body{
    height: 100%;
}
.navbar { 
	border-radius:0; 
}
.footer1 {
    position: fixed;
    bottom: 0%;
    width: 100%;
}
 </style>
 <style>
.body{
    height: 100%;
}
.navbar { 
	border-radius:0; 
}
.footer1 {
    position: fixed;
    bottom: 0%;
    width: 100%;
}
</style>
	<script src="<%=request.getContextPath()%>/resources/chat/assets/bootstrap-3.3.5/jquery-2.1.4.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/chat/assets/bootstrap-3.3.5/js/bootstrap.js" type="text/javascript"></script>		 
			 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/bootstrap-datetimepicker.js"></script>
<%String region=""; %>


<script>

/* function filterList(){ 
    var channelId = $("#channelId").val();
    alert(channelId);
    debugger;
     $.ajax({
        type:"POST",
        url: "http://localhost:8083/crmCmsAdmin/filterCategory",
        data: {"channelId " : channelId },
        success: function(data){
            var slctSubcat = $("#categoryId"), option= "";
            slctSubcat.empty();

            for(var sb =0; sb<data.length; sb++){
                option = option + "<option value='" + data[sb].categoryId + "'>" +data[sb].categoryName + "</option>";
            }
            slctSubcat.append(option);
        },
        error:function(){
           alert("error");
        }
   }); 

} */

function changeSelect(){
	//alert("test");
	 document.getElementById('allpamb').style.display='none';
	 document.getElementById('allpbtb').style.display='none';
	 document.getElementById('allbanca').style.display='none';
	 document.getElementById('zone').style.display='block';
	 
	//  allpamb.style.visibility  = "hidden";
	 //allpbtb.style.visibility  = "hidden";
	zone.style.visibility  = "visible";
}

function loadCategoryfilter() {
	
	var e = document.getElementById("channelId");
	var id = e.options[e.selectedIndex].value;
	
	//alert(" id "+id );
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
		 	    if (xhttp.readyState == 4 && xhttp.status == 200) {
	     //alert(" SUCESSS XXXXXXXXXX 11 ");
	  //alert("response text 4 "+xhttp.responseText);
	      var serverdata = xhttp.responseText;
		  var selectelement=document.getElementById("categoryId");
		  var str_array = serverdata.split(':');
		  var str_array2=[];
		 
		 
		  var len=0;
		  var j=0;
		  //alert(" selct length "+selectelement.length);
		  var selectlength = selectelement.length;
		  for (var i = 0; i < selectlength; ++i) {
			  //alert("select optionremove  "+selectelement.options[i].text);
	          //sselectelement.remove(i);
	          selectelement.options[i].text="";
	          selectelement.options[i].value="";
	           };

		  for(var i = 0; i < str_array.length; i++) {
		     // Trim the excess whitespace.
		     str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		     // Add additional code here, such as:
		     //alert(str_array[i]);
		     if(str_array[i].length>0)
		    	 {
		    	 len = len +1;
		    	 str_array2[j]=str_array[i];
		    	 j++;
		    	 }
		     //selectelement.options[i].value = str_array[i]+"xxxxx";
		  }
		  for(var i = 0; i < str_array2.length; i++) {
			    //alert("GOT VALUE aaaaaa  "+str_array2[i]);
			    
			  }
		  selectelement.size = str_array2.length;
		  //alert("selectelement "+selectelement)
		  for (var i = 0; i < str_array2.length; ++i) {
				  selectelement.options[i].text="";
				  selectelement.options[i].value="";
					
	        };
	        var str_array3=[];
		  for (var i = 0; i < str_array2.length; ++i) {
			//alert("str_array2 [i] " +str_array2[i])
			  str_array3 = str_array2[i].split('_');
			  var selVal = str_array2[i] ;
			   //alert("str_array3[0]  "+str_array3[0]);
			   //alert("str_array3[1]  "+str_array3[1]);
			   
			          selectelement.options[i].value = str_array3[0];
			          selectelement.options[i].text = selVal.substr(selVal.indexOf("_")+1);
					  
			 };
	    
		     //alert(" SUCESSS XXXXXXXXXX 22 ");
		 	
	    }
	  }
	  xhttp.open("GET", "allcategoryfilter/"+id, true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  xhttp.send("id="+id);
	  

	/* if(strUser == 'PAMB'){
		 document.getElementById('allpamb').style.display='block';
		 document.getElementById('allpbtb').style.display='none';
		 document.getElementById('zone').style.display='none';
    }else if(strUser == 'PBTB'){
    	document.getElementById('allpamb').style.display='none';
		 document.getElementById('allpbtb').style.display='block';
		 document.getElementById('zone').style.display='none';
        }
    	
	else
	{
		document.getElementById('allpamb').style.display='none';
		 document.getElementById('allpbtb').style.display='none';
		 document.getElementById('zone').style.display='block';	}
 */
 }



function loadDoc() {
	
	var e = document.getElementById("region1");
	var strUser = e.options[e.selectedIndex].value;
	
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (xhttp.readyState == 4 && xhttp.status == 200) {
	      //document.getElementById("region1").innerHTML = xhttp.responseText;
	  //    alert("document.getElementById"+strUser);
	    }
	  }
	  xhttp.open("GET", "allregion/"+strUser, true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  xhttp.send("id="+strUser);
	  

	if(strUser == 'PAMB'){
		 document.getElementById('allpamb').style.display='block';
		 document.getElementById('allpbtb').style.display='none';
		 document.getElementById('zone').style.display='none';
    }else if(strUser == 'PBTB'){
    	 document.getElementById('allpamb').style.display='none';
		 document.getElementById('allpbtb').style.display='block';
		 document.getElementById('zone').style.display='none';
        }else if(strUser == 'BANCA'){
        	document.getElementById('banca').style.display='none';
   			  }
    	
	else
	{
		document.getElementById('allpamb').style.display='none';
		 document.getElementById('allpbtb').style.display='none';
		 document.getElementById('zone').style.display='block';	}
	}

	function validateForm() {
		
		if(document.addmessage.channelId.value == 1){
			alert('Please select the channel ');
			document.addmessage.channelId.focus();
			return false;
		}
		if(document.addmessage.categoryId.value == '' || document.addmessage.categoryId.value == null){
			alert('Please select the category ');
			document.addmessage.categoryId.focus();
			return false;
		}
		
		if (document.addmessage.headlineName.value == "") {
			alert("Please enter Headline for English");
			document.addmessage.headlineName.focus();
			return false;
		} else if (document.addmessage.messageName.value == "") {
			alert("Please enter Message for English");
			document.addmessage.messageName.focus();
			return false;
		}  else if (document.addmessage.headlineName1.value == "") {
			alert("Please enter Headline Name for Bahasa");
			document.addmessage.headlineName1.focus();
			return false;
		} else if (document.addmessage.messageName1.value == "") {
			alert("Please enter Message for Bahasa");
			document.addmessage.messageName1.focus();
			return false;
		} 
		else if (document.addmessage.effdate.value == "") {
			alert("Please enter effective date");
			document.addmessage.effdate.focus();
			return false;
		}
		else if (document.addmessage.expdate.value == "") {
			alert("Please enter expiry date");
			document.addmessage.expdate.focus();
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
<body onload="changeSelect()">

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
					<div class="logout-box pull-left"><a id="login" style="color:#3c3434;background-color:white;border-radius:5px;border-color:#E70028" class="btn btn-logout anchor3" href="/crmCmsAdmin/logout.htm">Logout</a></div>
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
		<h2 class="title" style="color:white">Add Message  </h2>
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
						<br/><form:form name="addmessage" action="addmessage" method="post"
							commandName="message" enctype="multipart/form-data"
							cssClass="form-horizontal" onSubmit="return validateForm()">

					
							<div class="form-group">
							<label class="control-label col-sm-2"></label>
							<div class="col-sm-5">
							<select  class="form-control" name="region1" id="region1" onchange="loadDoc()">
							  <option id=""  selected>Open this select menu</option>
							  <option value="PAMB">All PAMB</option>
							  <option value="PBTB">All PBTB</option>
							</select>


</div>
</div>
							  <div class="form-group">
								<label class="control-label col-sm-2">Region :</label>
								<div id="test" class="col-sm-5">
<%-- 								<c:forEach items="${messageList}" var="message"> --%>
<%-- 								<c:choose> --%>

<%-- <c:if test="${messageList  == 'PAMB'}"> --%>

<%-- </c:if> --%>
<%-- </c:choose> --%>
<%-- </c:forEach> --%>

                                         
						<c:if test="${user.userName=='admin'}">
							<select id ="allpamb" name="allpamb" class="form-control" multiple="multiple">
										<option value="1">CENTRAL 1-DA</option>
										<option value="2">CENTRAL 2-DA </option>
										<option value="3">EAST COAST-DA</option>
										<!-- <option value="4">BANCATAKAFUL-DA</option>-->
										<option value="5">NORTH-DA</option>
										<option value="6">BRANCH OFFICE(DIRECT)-DA</option>
										<option value="7">SOUTH-DA </option>
										<option value="8">SABAH/SARAWAK-DA </option>
										<!-- <option selected value="9">BANCATAKAFUL-PAMB</option>-->
										<!-- <option value="10">BANCATAKAFUL-TFE </option>-->
										<option selected value="11">CENTRAL 1-PAMB</option>
										<option selected value="12">CENTRAL 2-PAMB</option>
										<option selected value="13">EAST COAST-PAMB</option>
										<option selected value="14">NORTH-PAMB</option>
										<option selected value="15">SOUTH-PAMB</option>
										<option selected value="16">SABAH/SARAWAK-PAMB</option>
										<option selected value="17">BRANCH OFFICE(DIRECT)-PAMB</option>
										<option selected value="18">PERAK-PAMB</option>
									</select>
									
									<select id ="zone" name="zone" class="form-control" multiple="multiple">
										<option value="1">CENTRAL 1-DA</option>
										<option value="2">CENTRAL 2-DA </option>
										<option value="3">EAST COAST-DA</option>
										<!-- <option value="4">BANCATAKAFUL-DA</option>-->
										<option value="5">NORTH-DA</option>
										<option value="6">BRANCH OFFICE(DIRECT)-DA</option>
										<option value="7">SOUTH-DA </option>
										<option value="8">SABAH/SARAWAK-DA </option>
										<!-- <option value="9">BANCATAKAFUL-PAMB</option>-->
										<!-- <option value="10">BANCATAKAFUL-TFE </option>-->
										<option value="11">CENTRAL 1-PAMB</option>
										<option value="12">CENTRAL 2-PAMB</option>
										<option value="13">EAST COAST-PAMB</option>
										<option value="14">NORTH-PAMB</option>
										<option value="15">SOUTH-PAMB</option>
										<option value="16">SABAH/SARAWAK-PAMB</option>
										<option value="17">BRANCH OFFICE(DIRECT)-PAMB</option>
										<option value="18">PERAK-PAMB</option>
									</select> 
									 
									 <select id ="allpbtb" name="allpbtb" class="form-control" multiple="multiple">
										<option selected value="1">CENTRAL 1-DA</option>
										<option selected value="2">CENTRAL 2-DA </option>
										<option selected value="3">EAST COAST-DA</option>
										<!-- <option selected value="4">BANCATAKAFUL-DA</option>-->
										<option selected value="5">NORTH-DA</option>
										<option selected value="6">BRANCH OFFICE(DIRECT)-DA</option>
										<option selected value="7">SOUTH-DA </option>
										<option selected value="8">SABAH/SARAWAK-DA </option>
										<!-- <option value="9">BANCATAKAFUL-PAMB</option>-->
										<!-- <option value="10">BANCATAKAFUL-TFE </option>-->
										<option value="11">CENTRAL 1-PAMB</option>
										<option value="12">CENTRAL 2-PAMB</option>
										<option value="13">EAST COAST-PAMB</option>
										<option value="14">NORTH-PAMB</option>
										<option value="15">SOUTH-PAMB</option>
										<option value="16">SABAH/SARAWAK-PAMB</option>
										<option value="17">BRANCH OFFICE(DIRECT)-PAMB</option>
										<option value="18">PERAK-PAMB</option>
									</select> 
							
						</c:if >
						<c:if test="${ user.userName=='superadmin'}">
							<select id ="allpamb" name="allpamb" class="form-control" multiple="multiple">
										<option value="1">CENTRAL 1-DA</option>
										<option value="2">CENTRAL 2-DA </option>
										<option value="3">EAST COAST-DA</option>
										<option value="4">BANCATAKAFUL-DA</option>
										<option value="5">NORTH-DA</option>
										<option value="6">BRANCH OFFICE(DIRECT)-DA</option>
										<option value="7">SOUTH-DA </option>
										<option value="8">SABAH/SARAWAK-DA </option>
										<option selected value="9">BANCATAKAFUL-PAMB</option>
										<option value="10">BANCATAKAFUL-TFE </option>
										<option selected value="11">CENTRAL 1-PAMB</option>
										<option selected value="12">CENTRAL 2-PAMB</option>
										<option selected value="13">EAST COAST-PAMB</option>
										<option selected value="14">NORTH-PAMB</option>
										<option selected value="15">SOUTH-PAMB</option>
										<option selected value="16">SABAH/SARAWAK-PAMB</option>
										<option selected value="17">BRANCH OFFICE(DIRECT)-PAMB</option>
										<option selected value="18">PERAK-PAMB</option>
									</select>
									
									<select id ="zone" name="zone" class="form-control" multiple="multiple">
										<option value="1">CENTRAL 1-DA</option>
										<option value="2">CENTRAL 2-DA </option>
										<option value="3">EAST COAST-DA</option>
										<option value="4">BANCATAKAFUL-DA</option>
										<option value="5">NORTH-DA</option>
										<option value="6">BRANCH OFFICE(DIRECT)-DA</option>
										<option value="7">SOUTH-DA </option>
										<option value="8">SABAH/SARAWAK-DA </option>
										<option value="9">BANCATAKAFUL-PAMB</option>
										<option value="10">BANCATAKAFUL-TFE </option>
										<option value="11">CENTRAL 1-PAMB</option>
										<option value="12">CENTRAL 2-PAMB</option>
										<option value="13">EAST COAST-PAMB</option>
										<option value="14">NORTH-PAMB</option>
										<option value="15">SOUTH-PAMB</option>
										<option value="16">SABAH/SARAWAK-PAMB</option>
										<option value="17">BRANCH OFFICE(DIRECT)-PAMB</option>
										<option value="18">PERAK-PAMB</option>
									</select> 
									 
									 <select id ="allpbtb" name="allpbtb" class="form-control" multiple="multiple">
										<option selected value="1">CENTRAL 1-DA</option>
										<option selected value="2">CENTRAL 2-DA </option>
										<option selected value="3">EAST COAST-DA</option>
										<option selected value="4">BANCATAKAFUL-DA</option>
										<option selected value="5">NORTH-DA</option>
										<option selected value="6">BRANCH OFFICE(DIRECT)-DA</option>
										<option selected value="7">SOUTH-DA </option>
										<option selected value="8">SABAH/SARAWAK-DA </option>
										<option value="9">BANCATAKAFUL-PAMB</option>
										<option value="10">BANCATAKAFUL-TFE </option>
										<option value="11">CENTRAL 1-PAMB</option>
										<option value="12">CENTRAL 2-PAMB</option>
										<option value="13">EAST COAST-PAMB</option>
										<option value="14">NORTH-PAMB</option>
										<option value="15">SOUTH-PAMB</option>
										<option value="16">SABAH/SARAWAK-PAMB</option>
										<option value="17">BRANCH OFFICE(DIRECT)-PAMB</option>
										<option value="18">PERAK-PAMB</option>
									</select> 
							
						</c:if >
						<c:if test="${user.userName=='banca'}">
							<select id="allpbtb" name="allpbtb" class="form-control"
								multiple="multiple">
								<option value="1">BANCATAKAFUL-DA</option>
								<option value="2">BANCATAKAFUL-PAMB</option>
								<option value="3">BANCATAKAFUL-TFE</option>
							</select>
						</c:if>

					  </div>
					</div>  		  
							<div class="form-group">
								<label class="control-label col-sm-2">Channel :</label>
								<div class="col-sm-5">

								<form:select path="" cssClass="form-control" id="channelId" name="channelId" onchange="loadCategoryfilter();">
								<option value="1">---Select---</option>
									 <c:if test="${!empty channelList}">
										<c:forEach items="${channelList}" var="channel">
											<form:option value="${channel.channelId}">${channel.channelName}</form:option>
										</c:forEach>
									</c:if>
								</form:select>
									
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2">Category :</label>
								<div class="col-sm-5">
									<form:select  path="category.categoryId" cssClass="form-control" id="categoryId" name="categoryId">
										<c:if test="${!empty categoryList}">
											<c:forEach items="${categoryList}" var="category">
												<form:option value="${category.categoryId}">${category.categoryName}</form:option>
											</c:forEach>
										</c:if>
									</form:select>
									
									<%-- <form:select class="form-control" id="categoryId" path="">
    									<form:option value="-" label="--Select category--"/>
									</form:select> --%>
									<form:errors path="category.categoryId"	cssClass="alert alert-danger ErrorMessage" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2">Reference No:</label>
								<div class="col-sm-5">
									<input type="text" name="referenceNo" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">English : </label>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Headline:</label>
								<div class="col-sm-5">
									<input type="text" name="headlineName" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Message :</label>
								<div class="col-sm-5">
									<textarea name="messageName" class="form-control" rows="5"></textarea>


								</div>
							</div>

							<div class="form-group" id="div1">
								<label class="control-label col-sm-2">Image upload :</label>
								<div class="col-sm-5">
									<input type="file" name="file_image" id="img1" value="file_image"  accept='image/*' />
									<img id="blah1" class="add" src="" alt="Image" height="100" />
								</div>
							</div>
							<div class="form-group" id="show_div" style="display:none;">
								<label class="control-label col-sm-2">Video URL :</label>
								<div class="col-sm-5">
									<input type="text" name="videoURL1" id="vid1"  />
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-2">Document upload:</label>
								<div class="col-sm-5">
									<input type="file" name="file"  id="img2" value="file" />
									<!--  <img id="blah2" class="add" src="" alt="Image" height="100" /> -->
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Bahasa :</label>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Headline:</label>
								<div class="col-sm-5">
									<input type="text" name="headlineName1" class="form-control" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-2">Message:</label>
								<div class="col-sm-5">
									<textarea name="messageName1" class="form-control" rows="5"></textarea>
								</div>
							</div>
							<div class="form-group" id="div2">
								<label class="control-label col-sm-2">Image upload :</label>
								<div class="col-sm-5">
									<input type="file" name="file2_image"  id="img3"  accept='image/*'/>
									<img id="blah3" class="add" src="" alt="Image" height="100" />
								</div>
							</div>
							<div class="form-group" id="show_div2" style="display:none;">
								<label class="control-label col-sm-2">Video URL :</label>
								<div class="col-sm-5">
									<input type="text" name="videoURL2" id="vid1"  />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2">Document upload:</label>
								<div class="col-sm-5">
									<input type="file" name="file2"  id="img4" value="file2" />
									<!-- <img id="blah4" class="add" src="" alt="Image" height="100" /> -->
								</div>
							</div>
                            
                            <div class="form-group">
								<label class="control-label col-sm-2">Effective Date &
									Time :</label>
								<div class="col-sm-5">
									<input type="text" name="effdate" id="effdate" value="" placeholder="DD-MM-YYYY" class="form_datetime form-control" readonly="readonly"/>
								</div>
							</div>
                            
							<div class="form-group">
								<label class="control-label col-sm-2">Expiry Date & Time
									:</label>
								<div class="col-sm-5">
									<div class="input-append">
										<input type="text" name="expdate" id="expdate" value="" placeholder="DD-MM-YYYY" class="form_datetime form-control" readonly="readonly"/>
									</div>
								</div>
							</div>

							

							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10 btn-block">
									<input type="submit" value="Broadcast" name="submit"
										class="btn btn-logout">
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
					<div class="clear"></div>
					<div class="col-sm-10 addedCategory" style="display: none">
						<h3>Message List</h3>
						<div class="CategoryTable">
							<table id="messageTable"
								class="table table-striped table-bordered">
								<tr>
									<th width="80">Message ID</th>
									<th width="120">Category</th>
									<th width="120">Message</th>
								</tr>
								<c:choose>
									<c:when test="${!empty messageList}">
										<c:forEach items="${messageList}" var="message">
											<tr>
												<td>${message.messageId}</td>
												<td>${message.category.categoryName}</td>
												<td>${message.messageName}</td>
											</tr>
										</c:forEach>

									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="3">No records found for selected category</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
					</div>
				</div>
				<div style="height:250px;">&nbsp;</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		/*$(".form_datetime").datetimepicker({
			format: 'dd-mm-yyyy - hh:ii ',
			autoclose: true,
			showMeridian: true,
		});*/
		
var dateToday = new Date();
		
		/*$("#expdate").datetimepicker({
			format: 'dd-mm-yyyy - hh:ii ',
			autoclose: true,
			showMeridian: true,
		});*/
		
		$("#effdate").datetimepicker({
			format: 'dd-mm-yyyy - hh:ii ',
			autoclose: true,
			showMeridian: true,
			startDate: dateToday,
			
		}).on('changeDate', function(ev){
			$('#expdate').datetimepicker('setStartDate', new Date(ev.date.valueOf()));
			
		});
		
		$("#expdate").datetimepicker({
			format: 'dd-mm-yyyy - hh:ii ',
			autoclose: true,
			showMeridian: true,
			startDate: dateToday,
			
		})
		
		$('#img1').change(function () {
                       var val = $(this).val().toLowerCase();
                       var regex = new RegExp("(.*?)\.(jpg|jpeg|gif|png|bmp)$");
                        if(!(regex.test(val))) {
                       $(this).val('');
                       alert('Please select correct file format');
                       } }); 
		$('#img3').change(function () {
            var val = $(this).val().toLowerCase();
            var regex = new RegExp("(.*?)\.(jpg|jpeg|gif|png|bmp)$");
             if(!(regex.test(val))) {
            $(this).val('');
            alert('Please select correct file format');
            } }); 
		
		$('#img2').change(function () {
				var val = $(this).val().toLowerCase();
				var regex = new RegExp("(.*?)\.(docx|doc|pdf|xml|bmp|ppt|xls|xlsx)$");
				 if(!(regex.test(val))) {
				$(this).val('');
				alert('Please select correct file format');
			} }); 
			
			$('#img4').change(function () {
				var val = $(this).val().toLowerCase();
				var regex = new RegExp("(.*?)\.(docx|doc|pdf|xml|bmp|ppt|xls|xlsx)$");
				 if(!(regex.test(val))) {
				$(this).val('');
				alert('Please select correct file format');
			} }); 
			
		
		
		$(function() {
		    $('#categoryId').change(function(){
		        if ($(this).val() === '6') 
		        {
		        	$("#show_div").show();
		        	$("#show_div2").show();
		        	$("#div1").hide();
		        	$("#div2").hide();
		        }
		        else {
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
			           // alert("Please upload an image");

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
	<%--Sudd Added  --%>
<!-- 	<div id="clientChatDiv"> -->
<!-- 	<div id="loginDiv" class="floatingChat collapse"> -->
<!-- 		<iframe id="chatFrame"  name="chatFrame" src=""></iframe> -->
<!-- 	</div> -->
<!-- 	<div class="chatBalloonDiv"> -->
<%-- 		<img data-target="#loginDiv" data-toggle="collapse" src="<%=request.getContextPath()%>/resources/chat/assets/img/chatBalloon2.png" class="imgClass"/> --%>
<!-- 	</div> -->
<!-- </div> -->

<script src="<%=request.getContextPath()%>/resources/chat/assets/bootstrap-3.3.5/jquery-2.1.4.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/chat/assets/bootstrap-3.3.5/js/bootstrap.js" type="text/javascript"></script>
	<%--Sudd Added --%>
</body>
<script>
onloadCustomerChatPage();
	function onloadCustomerChatPage(){
		var chatSrc = "http://cloudchat-xangerschat.rhcloud.com/springchat/"+"customerLogin";
		$("#chatFrame").attr("src", chatSrc);
	}
</script>

</html>
