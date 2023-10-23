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
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	String modify = request.getParameter("modify");
	String description_id = request.getParameter("description_id");
	
	String follow = "";
	
	xcaping.Description description = new xcaping.Description(item_type, item_id);
	description.heading = request.getParameter("heading");
	description.description = request.getParameter("description");
	
	boolean ok = false;
	if (modify.equals("1")) {
		ok = description.modify(description_id);
	} else {
		ok = description.insert();
	}
	
	if (ok) {
		follow = description.returnURL;
	} else {
		follow = description.errorURL+description.error;
	}
    response.sendRedirect(follow);
%>


<%@ include file="author_footer.jsp"%>