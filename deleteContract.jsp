<%
	String contract_id = request.getParameter("contract_id");
	String item_id = request.getParameter("item_id");
	xcaping.Contract contract = new xcaping.Contract();
	contract.delete(contract_id);
	contract.getURLs ("0", item_id);
	
	String follow = contract.propertyURL;
    response.sendRedirect(follow);
%>