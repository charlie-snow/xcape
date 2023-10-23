<%@ include file="author_init.jsp"%>

<%
	manager = true;
	properties_manager = true;
	contracts_manager = false;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<%
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	String resort_id = request.getParameter("resort_id");
	xcaping.Item item = new xcaping.Item(item_type);
	item.get(item_id);
	
	String error = "index.jsp?content=error&error=";
	String follow = "";
	
	item.father = resort_id;

	if (item.move(item_id, resort_id)) {
		follow = item.itemURL;
	} else {
		follow = error+item.error;
	}	
	String redirectURL = follow;
    response.sendRedirect(redirectURL);
%>
			
<%@ include file="author_footer.jsp"%>

