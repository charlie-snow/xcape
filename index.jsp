<%@ include file="author_init.jsp"%>

<% 
	manager = true;
	properties_manager = true;
	contracts_manager = true;
	contracts_operator = true;
	sales = true;
%>

<%@ include file="author_header.jsp"%>

<%@ include file="user_rights.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>xcaping</title>
	<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
	<link rel="STYLESHEET" type="text/css" href="links.css">
</head>

<body bgcolor="#000000">

<table width="980" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td><%@ include file="header.jsp" %></td>
</tr>
<tr>
	<td valign="top" bgcolor="#FFFFFF">
	<img src="pixel.gif" alt="" width="1" height="430" border="0" align="left">
	<br>
	<%@ include file="content.jsp" %>
	<br>
	</td>
</tr>
<tr>
	<td height="3"><%@ include file="footer.jsp" %></td>
</tr>
</table>

</body>
</html>

<%@ include file="author_footer.jsp"%>
