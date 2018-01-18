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
 .add{
			   visibility: hidden;
			  }
			  .body{
    height: 100%;
}
.error {
    color: #ff0000;
    font-style: italic;
    position:relative;
    top:-11px;    
}
.setMargin{
margin-top: -18px;
}
#login{background-color:white}
</style>
<script type="text/javascript">

function validateForm() {
	if (document.savecategory.categoryName.value == "") {
		alert("Please enter Category Name");
		document.savecategory.categoryName.focus();
		return false;
	} 
	if (document.savecategory.categoryNameBahasa.value == "") {
		alert("Please enter Category Name Bahasa");
		document.savecategory.categoryNameBahasa.focus();
		return false;
	} 
}

function deleteMyCategory(deleteId,categoryName)
{
	
 var r = confirm("Are you sure want to delete the category  "+categoryName+" !");
 if (r == true)
 {
  location="/crmCmsAdmin/deletecategory/"+deleteId;
 }
 else
 {
   //do nothing
 }
 
}

function updateMyCategory()
{
 var categoryId = $("#categoryId").val();
 var categoryName = $("#categoryName").val();
 var category_name_bhasa = $("#category_name_bhasa").val();
 
 var r = confirm("Are you sure want to update the category  "+categoryName+" !");
 if (r == true)
 {
  location="/crmCmsAdmin/updatecategory/"+categoryId+"/"+categoryName+"/"+category_name_bhasa;
 }
 else
 {
   //do nothing
 }
 
}

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
	    
		$('#img1').change(function () {
            var val = $(this).val().toLowerCase();
            var regex = new RegExp("(.*?)\.(jpg|jpeg|gif|png|bmp)$");
             if(!(regex.test(val))) {
            $(this).val('');
            alert('Please select correct file format');
            } });	    
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
					<div class="logout-box pull-left"><a id="login" style="color:#3c3434;border-radius:5px;border-color:#E70028;" class="btn btn-logout anchor3" href="/crmCmsAdmin/logout.htm">Logout</a></div>
					<div class="clear_both">&nbsp;</div>
				</div>
				<div class="clear_both">&nbsp;</div>
			</div>
		</div>
		
		<div class="title-bar" style="    background-color:#E70028;margin: 0px;">
        	<h2 class="title" style="color:white">Add Category</h2>
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
				<br/><form:form name="savecategory" action="savecategory" method="post" commandName="category" enctype="multipart/form-data"	cssClass="form-horizontal"  onSubmit="return validateForm()">
				<div class="form-group">
					<label class="control-label col-sm-2">Category Name:</label>
					<div class="col-sm-6">
						<input type="hidden" name="categoryId" id = "categoryId" value="${strCategoryId}" />
						<input type="hidden" name="userName" id = "userName" value="${user.userName}" />
						<c:if test="${isView}">
						<input type="text" class="form-control" name="categoryName" id = "categoryName" value="${strCategoryName}" style="background-color: #d3d3d3;"  readonly="readonly"/>
						</c:if>
						<c:if test="${!isView}">
						<input type="text" class="form-control" name="categoryName" id = "categoryName" value="${strCategoryName}" />
						</c:if>
					</div>
				</div>	
					<div class="form-group">
						<label class="control-label col-sm-2">Category Name Bahasa:</label>
						<div class="col-sm-6">
						<c:if test="${isView}">
						<input type="text" class="form-control" name="category_name_bhasa" id = "category_name_bhasa" value="${strCategory_name_bhasa}" style="background-color: #d3d3d3;"  readonly="readonly"/>
						</c:if>
						<c:if test="${!isView}">
						<input type="text" class="form-control" name="category_name_bhasa" id = "category_name_bhasa" value="${strCategory_name_bhasa}" />
						</c:if>
					   </div>
					</div>
					<div class="form-group">
						
								<label class="control-label col-sm-2">Image upload :</label>
								<div class="col-sm-5">
									<input type="file" name="file_image" id="img1" value="file_image"  accept='image/*' />
									<img id="blah1" class="add" src="" alt="Image" height="100" />
								</div>
						
					</div>
					<div class="form-group">
						<div class="col-sm-4">
						<c:if test="${empty isView}">
						   <button type="submit" value="Add Category" class="btn" style="float:right;margin-right:30%;" >Add Category</button>
						</c:if>
						<c:if test="${not empty strCategoryId and !isView}">
						   <button type="button" value="Add Category" class="btn" onclick="javascript:updateMyCategory();">Update Category</button>
						</c:if>
						
						</div>		
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<c:if test="${iscategoryAdded}">
						<div class="alert alert-success SuccessMessage col-md-2">
							<span>Category Added Successfully</span>
						</div>
					</c:if>
					<c:if test="${isDuplicate}">
						<div class="col-sm-3 alert alert-danger ErrorMessage">
							<span>Category already exist</span>
						</div>
					</c:if>
				</div>
				<div class="form-group">
					<c:if test="${displayMsg}">
						<div class="alert alert-success SuccessMessage col-md-2">
							<span>Category data Displayed</span>
						</div>
					</c:if>
				</div>
			  </form:form>
			  </div>
			  
			  <div class="col-md-10">

				<h3>Category List ${callToCat}</h3>
				<div class="ibox-content">
					<c:if test="${!empty categoryList}">
				 	<table border="0" class="table table-striped table-bordered">
						<tr>
						<th width="25" align="center">Category ID</th>
						<th width="25" align="center">Category Name</th>
						<th width="25" align="center">Category Bahasa Name</th>
						<th width="25" align="center">Status</th>
						<th width="25" align="center">Action</th>
						</tr>
						<c:forEach items="${categoryList}" var="category">
						<tr>
							<td>${category.categoryId}</td>
							<td>${category.categoryName}</td>
							<td>${category.category_name_bhasa}</td>
							<td>${category.isapproved}</td>
							<td align="center">
								<a href="/crmCmsAdmin/viewcategory/${category.categoryId}">View</a> / 
								<a href="/crmCmsAdmin/editcategory/${category.categoryId}">Edit</a> / 
								<a href="javascript:deleteMyCategory('${category.categoryId}','${category.categoryName}')">Delete</a>
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