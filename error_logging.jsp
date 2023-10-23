<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String error = request.getParameter("error");
%>
<html>
<head>
	<title>xcaping: error</title>
	<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>

<body>

<head>

</head>
<br><br><br><br><br><br><br><br><br>
	<table width="350" border="0" cellspacing="0" cellpadding="15" align="center">
	<tr class="header">
		<td align="center">
		<%= error %> <br><br>
		<a href="index.html">try again</a>
		</td>
	</tr>
	</table>

</body>
</html>
