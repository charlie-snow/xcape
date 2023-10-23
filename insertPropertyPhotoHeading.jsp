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
	
	String follow = "";
	
	property_photo_heading.heading = request.getParameter("heading");
	
	if (property_photo_heading.insert()) {
		follow = property_photo_heading.returnURL;
	} else {
		follow = property_photo_heading.errorURL+property_photo_heading.error;
	}
    response.sendRedirect(follow);
%>
			
<%@ include file="author_footer.jsp"%>

