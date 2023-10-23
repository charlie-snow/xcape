<%
	String add_on_id = request.getParameter("add_on_id");
	String contract_id = request.getParameter("contract_id");
	xcaping.AddOn add_on = new xcaping.AddOn();
	add_on.contract = contract_id;
	add_on.getURLs(contract_id);
	add_on.delete(add_on_id);
    response.sendRedirect(add_on.returnURL);
%>
