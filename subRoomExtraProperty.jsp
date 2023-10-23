<%
	String room_id = request.getParameter("room_id");
	String item_id = request.getParameter("item_id");
	xcaping.Property property = new xcaping.Property();
	property.subRoomExtra(room_id, item_id);
	String redirectURL = "index.jsp?content=item&item_id="+item_id+"&item_type=5&subcontent=rooming";
    response.sendRedirect(redirectURL);
%>
