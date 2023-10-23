<%
	String contract_id = request.getParameter("contract_id");
	xcaping.AddOn add_on = new xcaping.AddOn();
	add_on.getURLs(contract_id);
	String follow = "";
	
	String output;
	java.text.SimpleDateFormat formatter;
	java.util.Date today = new java.util.Date();
	formatter = new java.text.SimpleDateFormat("dd MM yy");
	output = formatter.format(today);
	
	add_on.edit_date = output;
	add_on.edited_by = session.getAttribute("user").toString();
	
	add_on.id = request.getParameter("add_on_id");
	add_on.contract = contract_id;
	add_on.supplement = request.getParameter("supplement");
	add_on.name = request.getParameter("name");
	add_on.from_date = request.getParameter("from_date");
	add_on.to_date = request.getParameter("to_date");
	add_on.price = request.getParameter("price");
	add_on.child_discount = request.getParameter("child_discount");
	add_on.child_percentage = request.getParameter("discount_percentage1");
	add_on.adult_discount = request.getParameter("adult_discount");
	add_on.adult_percentage = request.getParameter("discount_percentage2");
	
	if (add_on.insert()) {
		follow = add_on.returnURL;
	} else {
		follow = add_on.errorURL+add_on.error;
	}
	String redirectURL = follow;
    response.sendRedirect(redirectURL);
%>