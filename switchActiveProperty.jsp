<%
	String property_id = request.getParameter("property_id");
	String value = request.getParameter("value");
	
	String father_id = request.getParameter("father_id");
	xcaping.Property property = new xcaping.Property();
	property.setActive(property_id, value);
	xcaping.Item item = new xcaping.Item("4");
	item.getURLs(father_id, "4");
	String redirectURL = item.itemURL;
    response.sendRedirect(redirectURL);
%>
