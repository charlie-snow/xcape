<%
	String contract_id = request.getParameter("contract_id");
	xcaping.Contract contract = new xcaping.Contract();
	contract.unGenerate(contract_id);
	String redirectURL = "index.jsp?content=contract&contract_id="+contract_id;
    response.sendRedirect(redirectURL);
%>