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
	String item_type = request.getParameter("item_type");
	String description_heading_id = request.getParameter("description_heading_id");
	xcaping.DescriptionHeading description_heading = new xcaping.DescriptionHeading(item_type);
	
	String error = "index.jsp?content=error&error=";
	String follow = "";
	
	description_heading.heading = request.getParameter("heading");
	description_heading.order = request.getParameter("order");
	
	if (description_heading.modify(description_heading_id)) {
		follow = description_heading.returnURL;
	} else {
		follow = error+description_heading.error;
	}
	String redirectURL = follow;
    response.sendRedirect(redirectURL);
%>
			
<%@ include file="author_footer.jsp"%>
