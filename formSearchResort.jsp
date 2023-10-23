<% 
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	
	xcaping.Item item = new xcaping.Item(item_type);
	item.getURLs(item_id, item_type);
%>

<table width="350" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td align="center">
	search for a resort to move this property to:  
	<form action="<%= item.itemURL %>&subcontent=searchResort" method="post">
		<input type="text" name="name" id="name">
		<input type="hidden" name="type" id="type" value="4">
		<input type="hidden" name="item_id" id="item_id" value="<%= item_id %>">
		<input type="hidden" name="item_type" id="item_type" value="<%= item_type %>">
		<input type="submit" name="search" id="search" value="search">
	</form>
	</td>
</tr>
</table>