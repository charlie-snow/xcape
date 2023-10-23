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
	
	String error = "index.jsp?content=error&error=";
	String follow = "";
	
	if (property_photo_heading.delete(property_photo_heading_id)) {
		follow = property_photo_heading.returnURL;
	} else {
		follow = error+property_photo_heading.error;
	}
	String redirectURL = follow;
	
    response.sendRedirect(redirectURL);
%>

<%@ include file="author_footer.jsp"%>


