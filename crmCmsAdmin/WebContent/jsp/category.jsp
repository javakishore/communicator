<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Category</title>
</head>
<body>
	<form method="GET" action="addmessages">
		Category : <select name ="category">
						<option value="cat1">Cat1</option>
						<option value="cat2">Cat2</option>
						<option value="cat3">Cat3</option>
					</select></br>
		Message	 :	<input type="text" name="category"/></br>
		<input type="Submit" value="Sumbit"/>	
	</form>	
</body>
</html>