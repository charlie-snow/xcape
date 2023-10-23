<%@ include file="author_init.jsp"%>

<%
	manager = true;
	properties_manager = true;
	contracts_manager = true;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->

<%
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	xcaping.Item item = new xcaping.Item(item_type);
	
	String follow = "";
	
	if (item.delete(item_id, item_type)) {
		follow = item.itemURL;
	} else {
		follow = item.errorURL+item.error;
	}
    response.sendRedirect(follow);
%>

<%@ include file="author_footer.jsp"%>


