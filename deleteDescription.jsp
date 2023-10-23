<%@ include file="author_init.jsp"%>

<%
	manager = false;
	properties_manager = true;
	contracts_manager = true;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->

<%
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	String description_id = request.getParameter("description_id");
	xcaping.Description description = new xcaping.Description(item_type, item_id);
	
	description.delete(description_id);
	String redirectURL = description.returnURL;
    response.sendRedirect(redirectURL);
%>

<%@ include file="author_footer.jsp"%>


