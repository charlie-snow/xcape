<%
	String user_id = request.getParameter("user_id");
	xcaping.User user = new xcaping.User();
	user.delete(user_id);
	String redirectURL = "index.jsp?content=usersList";
    response.sendRedirect(redirectURL);
%>
