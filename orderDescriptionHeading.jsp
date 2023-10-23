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
	String up = request.getParameter("up");
	xcaping.DescriptionHeading description_heading = new xcaping.DescriptionHeading(item_type);
	description_heading.order(description_heading_id, up);
	
    response.sendRedirect("index.jsp?content=descriptionHeadingsList&item_type="+item_type);
%>
			
<%@ include file="author_footer.jsp"%>


