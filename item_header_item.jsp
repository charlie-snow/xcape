<table width="900" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td width="100"></td>
	<td class="title">
	<%= item.name %> - code: <%= item.id %> 
	</td>
	<td></td>
</tr>
<tr>
	<td></td>
	<td>
	
	<a href="<%= item.browseURL %>" class="blue">sub-items</a> | 
	<% if (session.getAttribute("photos").equals("true")) { %>
	<a href="<%= item.photosURL %>" class="blue">photos</a> | <% } %>
	<% if (session.getAttribute("descriptions").equals("true")) { %>
	<a href="<%= item.descriptionsURL %>" class="blue">descriptions</a>	| <% } %>
	<% if (session.getAttribute("edit_item").equals("true")) { %>
	<a href="<%= item.editURL %>&list=0" class="blue">edit</a> | <% } %>
	<a href="<%= item.brochureURL %>" target="_blank" class="blue">brochure</a> | 
	<% if (session.getAttribute("total_stop_sale").equals("true")) { %>
	<a href="<%= item.itemURL %>&subcontent=stop_sale_item" class="blue">stop sale</a><% } %>
	
	</td>
	<td></td>
</tr>
</table>
<br>
<img src="images/line.gif" width="960" height="3" alt="" border="0">
<br>