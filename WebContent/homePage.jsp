<!DOCTYPE html>
<html>
<head>home page</head>
<body>
<br>
<a href="login.jsp">login page</a>
<br>
<a href="blood_test_form.jsp">test form</a>
<form action="UserRegistrationServlet" method="post">
<input type="hidden" name="command" value="Logout">
<input type="submit" value="logout">
</form>
    <form id="tracking" name="tracking" action="TrackingServlet" method="post">
    <input type="hidden" name="command" value="all_results">
	<input type="submit" value="all_results">
    </form>   
<br>
<br>
 calculate(${user})
 <%
 
 
 
 %>
 <script type="text/javascript"> function calculate (s){
	 
	 
 }</script>
</body>
</html>