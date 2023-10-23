<%
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	
	String name = request.getParameter("name");
	String type = "4";
	
	xcaping.Lists list = new xcaping.Lists();
	java.util.Vector data = new java.util.Vector();
	xcaping.Item resort = new xcaping.Item("0");
	
	data = list.searchItems(name, type);
%>

<table width="550" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">resorts</td>
	<td align="center">id</td>
</tr>

<%
	if (data.isEmpty()) {
%>
<tr>
	<td colspan="3" align="center">there are no resorts that match your criteria</td>
</tr>
<%
	} else {
		java.util.Enumeration e;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			resort = (xcaping.Item)e.nextElement();
%>

<tr>
	<td align="left"><a href="moveProperty.jsp?item_id=<%= item_id %>&item_type=<%= item_type %>&resort_id=<%= resort.id %>"><%= resort.name %></a></td>
	<td><%= resort.id %></td>
</tr>

<%		}
	}%>

</table>
