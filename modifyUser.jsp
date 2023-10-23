<%
	String user_id = request.getParameter("user_id");
	xcaping.User user = new xcaping.User();
	user.name = request.getParameter("name");
	user.password = request.getParameter("password");
	user.type = request.getParameter("type");
	user.modify(user_id);
	String redirectURL = "index.jsp?content=usersList";
    response.sendRedirect(redirectURL);
%>