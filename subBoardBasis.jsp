<%
	String contract_id = request.getParameter("contract_id");
	String board_basis_id = request.getParameter("board_basis_id");
	xcaping.Contract contract = new xcaping.Contract();
	contract.subBoardBasis(board_basis_id, contract_id);
	String redirectURL = "board_basesSelection.jsp?contract_id="+contract_id;
    response.sendRedirect(redirectURL);
%>