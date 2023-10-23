<%
	String contract_id = request.getParameter("contract_id");
	String item_id = request.getParameter("item_id");
	String value = request.getParameter("value");
	xcaping.Contract contract = new xcaping.Contract();
	contract.switchActive(contract_id, value);
	xcaping.Item item = new xcaping.Item("5");
	item.getURLs(item_id, "5");
	String redirectURL = item.itemURL;
    response.sendRedirect(redirectURL);
%>
