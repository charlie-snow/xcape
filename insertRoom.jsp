<%
	String name = request.getParameter("name");
	String name = request.getParameter("item_id");
	xcaping.Room room = new xcaping.Room();
	room.setData(name);
	room.insert();
	String redirectURL = "index.jsp?content=item&item_id="+item_id+"&item_type=5&subcontent=rooming";
    response.sendRedirect(redirectURL);
%>