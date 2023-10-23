<%
	xcaping.Contract contract = new xcaping.Contract();
	contract.name = request.getParameter("name");
	contract.property = request.getParameter("property");
	contract.supplier = request.getParameter("supplier");
	contract.from_date = request.getParameter("from_date");
	contract.to_date = request.getParameter("to_date");

	contract.getURLs("0", contract.property);
	String error = "index.jsp?content=error&error=";
	String follow = "";

	if (request.getParameter("arrival_monday") != null) {
		contract.arrival_monday = request.getParameter("arrival_monday");
	} else {
		contract.arrival_monday = "0";
	}
	if (request.getParameter("arrival_tuesday") != null) {
		contract.arrival_tuesday = request.getParameter("arrival_tuesday");
	} else {
		contract.arrival_tuesday = "0";
	}
	if (request.getParameter("arrival_wednesday") != null) {
		contract.arrival_wednesday = request.getParameter("arrival_wednesday");
	} else {
		contract.arrival_wednesday = "0";
	}
	if (request.getParameter("arrival_thursday") != null) {
		contract.arrival_thursday = request.getParameter("arrival_thursday");
	} else {
		contract.arrival_thursday = "0";
	}
	if (request.getParameter("arrival_friday") != null) {
		contract.arrival_friday = request.getParameter("arrival_friday");
	} else {
		contract.arrival_friday = "0";
	}
	if (request.getParameter("arrival_saturday") != null) {
		contract.arrival_saturday = request.getParameter("arrival_saturday");
	} else {
		contract.arrival_saturday = "0";
	}
	if (request.getParameter("arrival_sunday") != null) {
		contract.arrival_sunday = request.getParameter("arrival_sunday");
	} else {
		contract.arrival_sunday = "0";
	}
	contract.markup = request.getParameter("markup");
	if (request.getParameter("free_sale") != null) {
		contract.free_sale = request.getParameter("free_sale");
	} else {
		contract.free_sale = "0";
	}
	contract.cot_suplement = request.getParameter("cot_suplement");
	contract.infant_age = request.getParameter("infant_age");
	contract.child_age = request.getParameter("child_age");
	contract.text = request.getParameter("text");
	contract.currency = request.getParameter("currency");
	contract.sales_period = request.getParameter("sales_period");
	if (request.getParameter("commissionable") != null) {
		contract.commissionable = request.getParameter("commissionable");
	} else {
		contract.commissionable = "0";
	}
	contract.edited_by = request.getParameter("edited_by");
	contract.agen6t_group = request.getParameter("agent_group");
	
	if (contract.insert()) {
		follow = contract.propertyURL;
	} else {
		follow = error+contract.error;
	}
	
	String redirectURL = follow;
    response.sendRedirect(redirectURL);
%>