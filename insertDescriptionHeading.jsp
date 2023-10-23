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
	xcaping.DescriptionHeading description_heading = new xcaping.DescriptionHeading(item_type);
	
	String follow = "";
	
	description_heading.heading = request.getParameter("heading");
	
	if (description_heading.insert()) {
		follow = description_heading.returnURL;
	} else {
		follow = description_heading.errorURL+description_heading.error;
	}
    response.sendRedirect(follow);
%>
			
<%@ include file="author_footer.jsp"%>

