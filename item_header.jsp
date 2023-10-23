<%
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	boolean is_property = item_type.equals("5");
	boolean countries = item_type.equals("0");
	
	if (!countries) { // if we're on the countries list, the header doesn't appear
		xcaping.Item item = new xcaping.Item(item_type);
		item.get(item_id);
		
		if (is_property) { %>
			<%@ include file="item_header_property.jsp" %>
		<%} else { %>
			<%@ include file="item_header_item.jsp" %>
		<% }
	}
%>