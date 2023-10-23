<table border="0" cellspacing="0" cellpadding="0">
<tr>
	<% if (session.getAttribute("type").equals("administrator")) { %>
	<td><a href="index.jsp?content=item&item_id=615&item_type=5">ejemplo benilux</a></td>
	<% } %>
	
	<% if (session.getAttribute("activate_photos").equals("true")) { %>
	<td>
	<img src="images/separator.gif" width="5" height="16" alt="" border="0">
	</td>
	<td>
	<a href="index.jsp?content=photos_to_activate">
	<img src="images/activate.gif" alt="" width="124" height="15" border="0">
	</a>
	</td>	
	<% } %>
	
	<% if (session.getAttribute("users").equals("true")) { %>
	<td>
	<img src="images/separator.gif" width="5" height="16" alt="" border="0">
	</td>
	<td>
	<a href="index.jsp?content=usersList">
	<img src="images/users.gif" alt="" width="59" height="16" border="0">
	</a>
	</td>	
	<% } %>
	
	<% if (session.getAttribute("lpp").equals("true")) { %>
	<td>
	<img src="images/separator.gif" width="5" height="16" alt="" border="0">
	</td>
	<td>
	<a href="javascript:abrir('lpp.html', 350, 220)">
	<img src="images/lpp.gif" alt="" width="38" height="16" border="0">
	</a>
	</td>
	<% } %>
	
	<% if (session.getAttribute("suppliers").equals("true")) { %>
	<td>
	<img src="images/separator.gif" width="5" height="16" alt="" border="0">
	</td>
	<td>
	<a href="index.jsp?content=suppliersList">
	<img src="images/suppliers.gif" alt="" width="73" height="16" border="0">
	</a>
	</td>
	<% } %>
	
	<% if (session.getAttribute("galas").equals("true")) { %>
	<td>
	<img src="images/separator.gif" width="5" height="16" alt="" border="0">
	</td>
	<td>
	<a href="index.jsp?content=galasList">
	<img src="images/galas.gif" alt="" width="54" height="16" border="0">
	</a>
	</td>
	<% } %>
</tr>
</table>
