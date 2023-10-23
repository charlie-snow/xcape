<%
	String property_id = request.getParameter("property_id");
	String resort = request.getParameter("resort");
	String featured = request.getParameter("featured");
	
	String father_id = request.getParameter("father_id");
	String father_type = request.getParameter("father_type");
	xcaping.Property property = new xcaping.Property();
	property.switchFeatured(property_id, resort, featured);
	String follow = "index.jsp?content=item&item_id="+father_id+"&item_type="+father_type;
    response.sendRedirect(follow);
%>
