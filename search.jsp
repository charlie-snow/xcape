<%
	String name = request.getParameter("name");
	String type = request.getParameter("type");
	xcaping.Lists list = new xcaping.Lists();
	java.util.Vector data = new java.util.Vector();
	xcaping.Property property = new xcaping.Property();
	xcaping.Item item = new xcaping.Item("0");
	boolean is_property = type.equals("5");
	xcaping.Data names = new xcaping.Data();
	
	if (is_property) {
		data = list.searchProperties(name);
	} else {
		data = list.searchItems(name, type);
		names.getNames(type);
	}
	String follow = "";
%>

<% 
//  -------------- if there is only one result, go directly to the item: it doesn't work the redirection
//if (data.size() == 1) { //if there is only one result, go directly to the item %>

<%	/*if (is_property) {
		java.util.Enumeration e;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			property = (xcaping.Property)e.nextElement();
			item.getURLs(property.id, "5");
			follow = item.itemURL;
		} 
	} else { 
		java.util.Enumeration e;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			item = (xcaping.Item)e.nextElement();
			item.getURLs(item.id, item.item_type);
			follow = item.itemURL;
		}
	}
	response.sendRedirect(follow);*/
%>

<% //} else /*if there is more than one result*/{ %>

<% if (is_property) { %>

<table width="550" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">property</td>
	<td align="center">rating</td>
	<td align="center">id</td>
	<td align="center">resort</td>
	<td align="center">area</td>
</tr>

<%
	if (data.isEmpty()) {
%>
<tr>
	<td colspan="3" align="center">there are no properties that match your criteria</td>
</tr>
<%
	} else {
		java.util.Enumeration e;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			property = (xcaping.Property)e.nextElement();
%>

<tr>
	<td align="left"><a href="index.jsp?content=item&item_id=<%= property.id %>&item_type=5"><%= property.name %></a></td>
	<td><%= property.rating %></td>
	<td><%= property.id %></td>
	<td><a href="index.jsp?content=item&item_id=<%= property.resort_id %>&item_type=4"><%= property.resort_name %></a></td>
	<td><a href="index.jsp?content=item&item_id=<%= property.area_id %>&item_type=3"><%= property.area_name %></a></td>
</tr>

<%		}
	}%>

</table>

<%	} else { %>

<table width="550" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center"><%= names.name %></td>
	<td align="center">id</td>
</tr>

<%
	if (data.isEmpty()) {
%>
<tr>
	<td colspan="3" align="center">there is no <%= names.name %> that match your criteria</td>
</tr>
<%
	} else {
		java.util.Enumeration e;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			item = (xcaping.Item)e.nextElement();
			item.getURLs(item.id, item.item_type);
%>

<tr>
	<td align="left"><a href="<%= item.itemURL %>"><%= item.name %></a></td>
	<td><%= item.id %></td>
</tr>

<%		}
	} %>

<% //} %>

</table>

<% } %>
