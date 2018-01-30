
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link
	href="<%=request.getContextPath()%>/resources/template/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/resources/template/font-awesome/css/font-awesome.css"
	rel="stylesheet">

<!-- Data Tables -->
<link
	href="<%=request.getContextPath()%>/resources/template/css/plugins/dataTables/dataTables.bootstrap.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/resources/template/css/plugins/dataTables/dataTables.responsive.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/resources/template/css/plugins/dataTables/dataTables.tableTools.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/resources/template/css/animate.css"
	rel="stylesheet">

<link type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Messages</title>
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
function deleteMyMessage(deleteId,messageName)
{
	
 var r = confirm("Are you sure want to delete the message  "+messageName+" !");
 if (r == true)
 {
  location="/crmCmsAdmin/deletemessages/"+deleteId;
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
				</div>
				<div class="clear_both">&nbsp;</div>
			</div>
			<div class="clear_both">&nbsp;</div>
		</div>
	</div>

	<div class="title-bar"  style="    background-color:#E70028;margin: 0px;">
		<!-- <div class="tools-box">
			<ul class="tool-list">
				<li><a class="back-btn" href="/crm/back">Back</a></li>
			</ul>
		</div> -->
		<h2 class="title" style="color:white">Login Details</h2>
	</div>
<!-- 	<div class="clear_both">&nbsp;</div> -->

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2" style="background-color:#00AA9A">
				<jsp:include page="leftMenu.jsp"></jsp:include>
			</div>

			<div class="col-md-10">

				<div class="row border-bottom">
					<nav class="navbar navbar-static-top" role="navigation"
						style="margin-bottom: 0">
					<div class="navbar-header">
						<form role="search" class="navbar-form-custom"
							action="search_results.html"></form>
					</div>

					</nav>
				</div>



<div class="ibox-content">
					<div class="table-responsive">
						<c:choose>
							<c:when test="${!empty messageList}">
								<table
									class="table table-striped table-bordered table-hover dataTables-example">

									<thead>
										<tr>
											<th>USER ID</th>
											<th>USER_NAME</th>
											<th>STATUS</th>
											<th>LOGIN DATE/TIME</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${messageList}" var="messageStatus">
											<tr class="gradeA">
												<td>${messageStatus.userid}</td>
												<td>${messageStatus.username}</td>
												<td>${messageStatus.status}</td>
												<td>${messageStatus.logindate}</td>
												
											</tr>
										</c:forEach>
									</tbody>

								</table>
							</c:when>
							<c:when test="${empty messageList}">
							<p>No Records found</p>
							</c:when>
						</c:choose>

					</div>

				</div>

			</div>

			<script
				src="<%=request.getContextPath()%>/resources/template/js/jquery-2.1.1.js"></script>
			<script
				src="<%=request.getContextPath()%>/resources/template/js/bootstrap.min.js"></script>
			<script
				src="<%=request.getContextPath()%>/resources/template/js/plugins/metisMenu/jquery.metisMenu.js"></script>
			<script
				src="<%=request.getContextPath()%>/resources/template/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
			<script
				src="<%=request.getContextPath()%>/resources/template/js/plugins/jeditable/jquery.jeditable.js"></script>

			<!-- Data Tables -->
			<script
				src="<%=request.getContextPath()%>/resources/template/js/plugins/dataTables/jquery.dataTables.js"></script>
			<script
				src="<%=request.getContextPath()%>/resources/template/js/plugins/dataTables/dataTables.bootstrap.js"></script>
			<script
				src="<%=request.getContextPath()%>/resources/template/js/plugins/dataTables/dataTables.responsive.js"></script>
			<script
				src="<%=request.getContextPath()%>/resources/template/js/plugins/dataTables/dataTables.tableTools.min.js"></script>

			<!-- Custom and plugin javascript -->
			<script
				src="<%=request.getContextPath()%>/resources/template/js/inspinia.js"></script>
			<script
				src="<%=request.getContextPath()%>/resources/template/js/plugins/pace/pace.min.js"></script>

			<!-- Page-Level Scripts -->

			<script>
			
			function deleteMyMessage(deleteId,messageName)
			{
				
			 var r = confirm("Are you sure want to delete the message  "+messageName+" !");
			 if (r == true)
			 {
			  location="/crmCmsAdmin/deletemessages/"+deleteId;
			 }
			 else
			 {
			   //do nothing
			 }
			 
			}
        $(document).ready(function() {
            $('.dataTables-example').dataTable({
                responsive: true,
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "<%=request.getContextPath()%>/resources/template/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                }
            });

            /* Init DataTables */
            var oTable = $('#editable').dataTable();

            /* Apply the jEditable handlers to the table */
            oTable.$('td').editable( '../example_ajax.php', {
                "callback": function( sValue, y ) {
                    var aPos = oTable.fnGetPosition( this );
                    oTable.fnUpdate( sValue, aPos[0], aPos[1] );
                },
                "submitdata": function ( value, settings ) {
                    return {
                        "row_id": this.parentNode.getAttribute('id'),
                        "column": oTable.fnGetPosition( this )[2]
                    };
                },

                "width": "90%",
                "height": "100%"
            } );


        });

        function fnClickAddRow() {
            $('#editable').dataTable().fnAddData( [
                "Custom row",
                "New row",
                "New row",
                "New row",
                "New row" ] );

        }
		
		
    </script>
			<script type="text/javascript">
	 $("#btnExport").click(function (e) {
    window.open('data:application/vnd.ms-excel,' + $('#dvData').html());
    e.preventDefault();
});
    $(document).ready(function() {
    $('#example').DataTable( {
        dom: 'Bfrtip',
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ]
    } );
} );
    </script>
			<style>
body.DTTT_Print {
	background: #fff;
}

.DTTT_Print #page-wrapper {
	margin: 0;
	background: #fff;
}

button.DTTT_button, div.DTTT_button, a.DTTT_button {
	border: 1px solid #e7eaec;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

button.DTTT_button:hover, div.DTTT_button:hover, a.DTTT_button:hover {
	border: 1px solid #d2d2d2;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

.dataTables_filter label {
	margin-right: 5px;
}
</style>
</body>
</html>