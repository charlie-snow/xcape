<%
	String name = request.getParameter("name");
	xcaping.BoardBasis board_basis = new xcaping.BoardBasis();
	board_basis.setData(name);
	board_basis.insert();
	String redirectURL = "board_basesList.jsp";
    response.sendRedirect(redirectURL);
%>