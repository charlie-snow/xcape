<%@ include file="author_init.jsp"%>

<%
	manager = false;
	properties_manager = true;
	contracts_manager = false;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<%
	xcaping.Lists list = new xcaping.Lists();
	xcaping.PropertyPhotoHeading property_photo_heading = new xcaping.PropertyPhotoHeading();
	java.util.Vector data = new java.util.Vector();
	data = list.getPropertyPhotoHeadings();
%>

<script language="JavaScript">
function confirmDelete(property_photo_heading_id){
	if (confirm("are you sure you want to delete this heading?")) {
		window.location = "deletePropertyPhotoHeading.jsp?property_photo_heading_id="+property_photo_heading_id;
	}
}
</script>

<table width="650" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">heading</td>
	<td></td>
	<td></td>
</tr>

<%
	java.util.Enumeration e;
	for (e = data.elements() ; e.hasMoreElements() ;) {
		property_photo_heading = (xcaping.PropertyPhotoHeading)e.nextElement();
%>

<tr>
	<td align="center"><%= property_photo_heading.heading %></td>
	<td align="center">
	<% if (property_photo_heading.id != "1" && property_photo_heading.id != "2") { %>
	<a href="index.jsp?content=formEditPropertyPhotoHeading&property_photo_heading_id=<%= property_photo_heading.id %>">edit</a>
	<% } %>
	</td>
	<td align="center">
	<% if (property_photo_heading.id != "1" && property_photo_heading.id != "2") { %>
	<a href="#" onClick="confirmDelete(<%= property_photo_heading.id %>)">delete</a>
	<% } %>
	</td>
</tr>

<%}%>

<form action="insertPropertyPhotoHeading.jsp" method="post" name="insertpropertyheading" id="insertpropertyheading">

<tr>
	<td align="center"><input type="text" name="heading" id="heading" size="70"></td>
	<td colspan="2" align="center"><input type="submit" name="submit" id="submit" value="add"></td>
</tr>

</form>
</table>


			
<%@ include file="author_footer.jsp"%>

