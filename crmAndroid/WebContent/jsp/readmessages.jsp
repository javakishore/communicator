<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath()%>/resources/template/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/template/font-awesome/css/font-awesome.css" rel="stylesheet">

<!-- Data Tables -->
<link href="<%=request.getContextPath()%>/resources/template/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/template/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/template/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/template/css/animate.css" rel="stylesheet">

<link type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Like Message</title>
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
<style>
    body.DTTT_Print {
        background: #fff;

    }
    .DTTT_Print #page-wrapper {
        margin: 0;
        background:#fff;
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
					width="140" alt="PruBSN" src="resources/images/PruBSN_logo.png">
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
				<li><a class="back-btn" href="back">Back</a></li>
			</ul>
		</div> -->
		<h2 class="title" style="color:white">Read Message List</h2>
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
								<li><a id="anchorfontcolor3" style="color:white;"  href="/crmCmsAdmin/messagesStatus">Messages Status</a></li>
								<li><a id="anchorfontcolor4" style="color:white;" class="active" href="/crmCmsAdmin/readmessages">Read Messages</a></li>
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
			
				<div class="row border-bottom">
      <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
          <form role="search" class="navbar-form-custom" action="search_results.html">
           
          </form>
        </div>
       
      </nav>
    </div>
    
    
  
				
	<div class="ibox-content" >
      <div class="table-responsive" >
      <c:choose>
	<c:when test="${!empty messageList}">
        <table  class="table table-striped table-bordered table-hover dataTables-example">

          <thead>
            <tr>
											<th>Message ID</th>
											<th>Category</th>
											<th>Message</th>
											<th>Username</th>
											<th>Date and Time</th>
										</tr>
										</thead>
										<tbody>
										<c:forEach items="${messageList}" var="message" varStatus="i">
											<tr class="gradeA">
												<td>${message.msgID}</td>
												<td>${message.categoryName}</td>
												<td>${message.msgTitle}</td>
												<td>${message.userName}</td>
												<td>${message.timestamp}</td>

											</tr>
										</c:forEach>
										 </tbody>
									</table>
								</c:when>
								<c:otherwise>
<!-- 								No data to show. -->
								</c:otherwise>
							</c:choose>
           
           
            
          </tbody>
	
       
     
			</div>

		</div> 
		
	</div>
</div>
</div>

<script src="<%=request.getContextPath()%>/resources/template/js/jquery-2.1.1.js"></script> 
<script src="<%=request.getContextPath()%>/resources/template/js/bootstrap.min.js"></script> 
<script src="<%=request.getContextPath()%>/resources/template/js/plugins/metisMenu/jquery.metisMenu.js"></script> 
<script src="<%=request.getContextPath()%>/resources/template/js/plugins/slimscroll/jquery.slimscroll.min.js"></script> 
<script src="<%=request.getContextPath()%>/resources/template/js/plugins/jeditable/jquery.jeditable.js"></script> 

<!-- Data Tables --> 
<script src="<%=request.getContextPath()%>/resources/template/js/plugins/dataTables/jquery.dataTables.js"></script> 
<script src="<%=request.getContextPath()%>/resources/template/js/plugins/dataTables/dataTables.bootstrap.js"></script> 
<script src="<%=request.getContextPath()%>/resources/template/js/plugins/dataTables/dataTables.responsive.js"></script> 
<script src="<%=request.getContextPath()%>/resources/template/js/plugins/dataTables/dataTables.tableTools.min.js"></script> 

<!-- Custom and plugin javascript --> 
<script src="<%=request.getContextPath()%>/resources/template/js/inspinia.js"></script> 
<script src="<%=request.getContextPath()%>/resources/template/js/plugins/pace/pace.min.js"></script> 

<!-- Page-Level Scripts --> 

<script>
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


</body>
</html>