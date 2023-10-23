<%
	String contract_id = request.getParameter("contract_id");
	String discount_id = request.getParameter("discount_id");
	xcaping.Contract contract = new xcaping.Contract();
	contract.subDiscount(discount_id, contract_id);
	String redirectURL = "discountsSelection.jsp?contract_id="+contract_id;
    response.sendRedirect(redirectURL);
%>