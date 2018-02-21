<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
</head>
<body>

	<div class="addCategoryform">
		
		<div class="header">
			<div class="header-wrap">
				<div class="logo pull-left">
					<a href="javascript:void(0);" title="PruBSN">
						<img height="45" width="140" alt="PruBSN" src="resources/images/PruBSN_logo.png">
					</a>
				</div>
				<div class="right-block pull-right">
					<h3 class="user-intro pull-left">Welcome,${user.userName}!</h3>
					<div class="logout-box pull-left"><a class="btn btn-logout" href="/crmCmsAdmin/logout.htm">Logout</a></div>
					<div class="clear_both">&nbsp;</div>
				</div>
				<div class="clear_both">&nbsp;</div>
			</div>
		</div>
		
		<div class="title-bar">
			<!-- <div class="tools-box">
            	<ul class="tool-list">
                	<li><a class="back-btn" href="/crm/back">Back</a></li>
                </ul>
            </div> -->
        	<h2 class="title">Add Category</h2>
        </div>
		<div class="clear_both">&nbsp;</div>
		
		
		<div class="col-md-12">
			<form:form class="form-horizontal" action="addcategory" method="post"
				commandName="category">

				<div class="form-group">
					<label class="control-label col-sm-2">Category Name:</label>
					<div class="col-sm-6">
						<form:input path="categoryName" cssClass="form-control" />
						<div class="col-sm-12 ErrorMessageContent">
							<form:errors path="categoryName"
								cssClass="alert alert-danger ErrorMessage" />
						</div>
					</div>
					<div class="col-sm-4">
						<button type="submit" value="Add Category" class="btn">Add
							Category</button>

					</div>
					<div class="clear"></div>

				</div>
				<div class="form-group">
					<c:if test="${iscategoryAdded}">
						<div class="alert alert-success SuccessMessage col-md-5">
							<span>Category Added Successfully</span>
						</div>
					</c:if>
				</div>


			</form:form>
		</div>
	</div>
	<div class="clear"></div>
	<div class="col-sm-10 addedCategorys">
		<h3>Category List ${callToCat}</h3>
		<div class="CategoryTable">
			<c:if test="${!empty categoryList}">
				<table border="0" class="table table-striped table-bordered">
					<tr>
						<th width="80">Category ID</th>
						<th width="120">Category Name</th>
					</tr>
					<c:forEach items="${categoryList}" var="category">
						<tr>
							<td>${category.categoryId}</td>
							<td>${category.categoryName}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
	</div>
</body>
</html>