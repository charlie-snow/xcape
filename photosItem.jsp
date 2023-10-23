<%@ include file="author_init.jsp"%>

<%
	manager = true;
	properties_manager = true;
	contracts_manager = false;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<% 
	String item_id ="";
	String item_type = "";
	item_id = request.getParameter("item_id");
	item_type = request.getParameter("item_type");
	
	xcaping.DBConnection DBConnection = new xcaping.DBConnection();
	java.util.Vector photos = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration e;
	xcaping.PhotoItem photo_item = new xcaping.PhotoItem();
	photos = list.getPhotos(item_id, item_type);
	e = photos.elements();
	int i = 1;
	String group = "";
	String real_group = "";
%>

<% if (session.getAttribute("photos_edit").equals("true")) { %>
<table width="800" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td>
	<table border="0" cellspacing="2" cellpadding="3" align="center">
	<tr class="header">
		<td width="400" rowspan="2" align="center" class="sub-title">item Photos</td>
		<td>Add Photos</td>
	</tr>
	<tr>
		<td><%@ include file="formSearchPhotos.jsp"%></td>
	</tr>
	</table>
	</td>
</tr>
<tr>
	<td>
	<table width="800" border="0" cellspacing="2" cellpadding="3" align="center">
	<tr class="header">
		<td align="center">photo</td>
		<td></td>
		<td align="center">del</td>
	</tr>
	<% while (e.hasMoreElements()) { 
		photo_item = (xcaping.PhotoItem)e.nextElement();
		real_group = photo_item.getGroup(photo_item.file);
	%>
	<tr>
		<td width="140" align="center" valign="top">
		<%= photo_item.file %>
		</td>
		<td></td>
		<td width="100">
		<a href="deletePhotoItem.jsp?item_id=<%= item_id %>&item_type=<%= item_type %>&photo_id=<%= photo_item.photo %>">del</a>
		<%	if (!real_group.equals(group)) {
				group = real_group; %>
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="deletePhotoItem.jsp?item_id=<%= item_id %>&item_type=<%= item_type %>&photo_id=G<%= group %>">del group</a>
		<% } %>
		</td>
	</tr>
	<% } %>
	</table>
	</td>
</table>
<% } %>

<%@ include file="formEditPhotosItem.jsp"%>

			
<%@ include file="author_footer.jsp"%>

