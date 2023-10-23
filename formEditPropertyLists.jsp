<%
	String item_id = request.getParameter("item_id");
%>


<br>

<table width="100%" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td width="50%" align="center" valign="top">
	.:rooms:.<br>
	<iframe src="roomsPropertySelection.jsp?item_id=<%= item_id %>" name="roomsPropertySelection" id="roomsPropertySelection" width="80%" marginwidth="0" marginheight="0" scrolling="yes" frameborder="0" style="background-color:transparent;"></iframe>
	</td>
</tr>

</table>