<%
	manager = true;
	properties_manager = true;
	contracts_manager = false;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<%
	boolean property = request.getParameter("item_type").equals("5");
	
	java.util.Vector photo_headings = new java.util.Vector();
	java.util.Enumeration e2;
	xcaping.PropertyPhotoHeading property_photo_heading = new xcaping.PropertyPhotoHeading();
	photo_headings = list.getPropertyPhotoHeadings();

%>

<form action="modifyPhotosItem.jsp" method="post" name="modifyPhotosItem" id="modifyPhotosItem">
<input type="hidden" name="item_id" id="item_id" value="<%= item_id %>">
<input type="hidden" name="item_type" id="item_type" value="<%= item_type %>">
<table width="800" border="0" cellspacing="3" cellpadding="2" align="center">
	<tr class="header">
		<td align="center">photo</td>
		<td align="center">description</td>
		<td align="center">order</td>
	</tr>
	<% e = photos.elements();
	while (e.hasMoreElements()) { 
		photo_item = (xcaping.PhotoItem)e.nextElement();
		e2 = photo_headings.elements();%>
	<tr>
		<td align="center" valign="top">
		<a href="javascript:abrir('<%= DBConnection.default_photos_url %>350/<%= photo_item.file %>', 350, 350)">
		<img src="<%= DBConnection.default_photos_url %>100/<%= photo_item.file %>" alt="" border="0">
	<br><%= photo_item.file %>
		</a>
		</td>
		<td align="center">
			<table>
			<tr>
				<% if (property) { out.print("<td align='center'>heading</td>"); } %>
				<td align="center">description</td>
			</tr>
			<tr>
				<% if (property) { %>
				<td>
				<% if (session.getAttribute("photos_edit").equals("true")) { %>
					<select name="photo_heading<%= photo_item.photo %>" id="photo_heading<%= photo_item.photo %>">
					<% while (e2.hasMoreElements()) { 
						property_photo_heading = (xcaping.PropertyPhotoHeading)e2.nextElement();
						out.print("<option value='"+property_photo_heading.id+"' ");
						if (property_photo_heading.id.equals(photo_item.heading)) { out.print("SELECTED "); }
						out.print(">"+property_photo_heading.heading+"</option>");
					}%>
					</select>
				<% } else { 
					out.print(photo_item.heading);
				 } %>
				</td>
				<% } %>
				<td>
				<% if (session.getAttribute("photos_edit").equals("true")) { %>
					<input type="text" name="photo_description<%= photo_item.photo %>" id="photo_description<%= photo_item.photo %>" value="<%= photo_item.description %>" size="70">
				<% } else { 
					out.print(photo_item.description);
				 } %>
				</td>
			</tr>
			</table>
		</td>
		<td align="center">
		<% if (session.getAttribute("photos_edit").equals("true")) { %>
			<select name="photo_order<%= photo_item.photo %>" id="photo_order<%= photo_item.photo %>">
				<option value="1"<% if (photo_item.order.equals("1")) {out.print (" SELECTED ");} %>>Album</option>
				<option value="2"<% if (photo_item.order.equals("2")) {out.print (" SELECTED ");} %>>Hide</option>
				<option value="3"<% if (photo_item.order.equals("3")) {out.print (" SELECTED ");} %>>1st</option>
				<option value="4"<% if (photo_item.order.equals("4")) {out.print (" SELECTED ");} %>>2nd</option>
				<option value="5"<% if (photo_item.order.equals("5")) {out.print (" SELECTED ");} %>>3rd</option>
			</select>
		<% } else { 
			out.print(photo_item.order);
		 } %>
		</td>
	</tr>
	<% } %>
	
	<% if (session.getAttribute("photos_edit").equals("true")) { %>
	<tr class="header">
		<td colspan="3" align="center"><input type="submit" name="submit" id="submit" value="save changes"></td>
	</tr>
	<% } %>
	
</table>
</form>
			
<%@ include file="author_footer.jsp"%>

