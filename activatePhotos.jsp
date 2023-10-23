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
	xcaping.Photo photo = new xcaping.Photo();
	String next = "index.jsp?content=photos_to_activate";
	String error = "index.jsp?content=error&error="+photo.error;
	String follow = "";
	
	if (photo.activatePhotos()) {
		follow = next;
	} else {
		follow = error;
	}
	String redirectURL = follow;
    response.sendRedirect(redirectURL);
%>

<%@ include file="author_footer.jsp"%>

