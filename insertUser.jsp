<%
	xcaping.User user = new xcaping.User();
	user.name = request.getParameter("name");
	user.password = request.getParameter("password");
	user.type = request.getParameter("type");
	
	user.insert();
	String redirectURL = "index.jsp?content=usersList";
    response.sendRedirect(redirectURL);
%>
