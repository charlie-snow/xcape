<%
	String board_basis_id = request.getParameter("board_basis_id");
	String name = request.getParameter("name");
	xcaping.BoardBasis board_basis = new xcaping.BoardBasis();
	board_basis.setData(name);
	board_basis.modify(board_basis_id);
	String redirectURL = "board_basesList.jsp";
    response.sendRedirect(redirectURL);
%>