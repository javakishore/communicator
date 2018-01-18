<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-wrap" style="height: 104em;">
					<c:choose>
						<c:when test="${user.userType=='admin'}">
							<ul class="mainmenu" style="border:none;">
								<!-- <li><a href="/crm/addcategory">Add Category</a></li> -->
								<li><span>Messages</span>
									<ul class="submenu">
										<li><a id="anchorfontcolor1" style="color:white;" class="active" href="/crmCmsAdmin/addmessage">Add Message</a></li>
										<li><a id="anchorfontcolor2" style="color:white;"  href="/crmCmsAdmin/mymessages">Message List</a></li>
										<li><a id="anchorfontcolor5" style="color:white;"  href="/crmCmsAdmin/pendingmessages">Pending Messages</a></li>
									</ul>
								</li>
								<li><span>Channel & Category</span>
									<ul class="submenu">
										<li><a id="anchorfontcolor10" style="color:white;"  href="/crmCmsAdmin/channelCategoryMapping">Channel Category Mapping</a></li>
										<li><a id="anchorfontcolor6" style="color:white;"   href="/crmCmsAdmin/myCategory">Category List</a></li>
										<li><a id="anchorfontcolor8" style="color:white;"  href="/crmCmsAdmin/pendingcategory">Pending Category</a></li>
										<li><a id="anchorfontcolor7" style="color:white;"  href="/crmCmsAdmin/myChannel">Channel List</a></li>
										<li><a id="anchorfontcolor9" style="color:white;"  href="/crmCmsAdmin/pendingchannel">Pending Channel</a></li>
									</ul>
								</li>
								
								<li><span>Reports</span>
									 <ul class="submenu">
										<li><a id="anchorfontcolor3" style="color:white;"  href="/crmCmsAdmin/messagesStatus">Messages Status</a></li>
										<li><a id="anchorfontcolor4" style="color:white;"  href="/crmCmsAdmin/readmessages">Read Messages</a></li>
										<li><a id="anchorfontcolor11" style="color:white;"  href="/crmCmsAdmin/likemessage">Like</a></li>
										<li><a id="anchorfontcolor12" style="color:white;"  href="/crmCmsAdmin/favouritemessage">Favourite</a></li>
										<li><a id="anchorfontcolor13" style="color:white;"  href="/crmCmsAdmin/viewrating">Rate App</a></li>
										<li><a id="anchorfontcolor14" style="color:white;"  href="/crmCmsAdmin/loginDetails">Log In Details</a></li>
									</ul>
								</li>
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
