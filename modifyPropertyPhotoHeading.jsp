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
	xcaping.PropertyPhotoHeading property_photo_heading = new xcaping.PropertyPhotoHeading();
	String next = "index.jsp?content=propertyPhotoHeadingsList";
	String error = "index.jsp?content=error&error="+property_photo_heading.error;
	String follow = "";
	
	String property_photo_heading_id = request.getParameter("property_photo_heading_id");
	property_photo_heading.heading = request.getParameter("heading");
	
	if (property_photo_heading.modify(property_photo_heading_id)) {
		follow = next;
	} else {
		follow = error;
	}
	
	String redirectURL = follow;
    response.sendRedirect(redirectURL);
%>	

<%@ include file="author_footer.jsp"%>

