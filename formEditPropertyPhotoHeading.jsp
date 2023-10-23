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
	String property_photo_heading_id = request.getParameter("property_photo_heading_id");
	xcaping.PropertyPhotoHeading property_photo_heading = new xcaping.PropertyPhotoHeading();
	property_photo_heading.get(property_photo_heading_id);
%>

<br>
<form action="modifyPropertyPhotoHeading.jsp" method="post" name="modifypropertyphotoheading" id="modifypropertyphotoheading">
<input type="hidden" name="property_photo_heading_id" id="property_photo_heading_id" value="<%= property_photo_heading_id %>">
heading: <input type="text" name="heading" id="heading" value="<%= property_photo_heading.heading %>">
<input type="submit" name="submit" id="submit" value="ok">
</form>
			
<%@ include file="author_footer.jsp"%>

