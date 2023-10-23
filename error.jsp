<%
	String error = request.getParameter("error");
%>

<head>
<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>

<table width="350" border="0" cellspacing="0" cellpadding="15" align="center">
<tr class="header">
	<td align="center"><%= error %><br><a href="#" onClick="history.back()">return</a></td>
</tr>
</table>
