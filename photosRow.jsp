<%
	int elements = 9;
%>

<table border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td colspan="<%= elements*2 %>"></td>
</tr>
<tr>
<%
	i = 1;
	while (e.hasMoreElements() && i<elements) {
		photo = (xcaping.Photo)e.nextElement();
		i++;
%>
	<td align="center">
	<!-- img src="<%= DBConnection.default_photos_url %>100/<%= photo.file %>" alt="" border="0">-->
	<%= photo.file %>
	</td>
	<% } %>
</tr>
</table>