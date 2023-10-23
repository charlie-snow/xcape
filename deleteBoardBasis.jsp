<%
	String board_basis_id = request.getParameter("board_basis_id");
	xcaping.BoardBasis board_basis = new xcaping.BoardBasis();
	board_basis.delete(board_basis_id);
	String redirectURL = "board_basesList.jsp";
    response.sendRedirect(redirectURL);
%>
