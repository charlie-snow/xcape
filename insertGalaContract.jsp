<%
	String contract_id = request.getParameter("contract_id");
	xcaping.Gala gala = new xcaping.Gala();
	gala.getURLs(contract_id);
	String follow = "";
	
	gala.contract =	contract_id;
	gala.id = request.getParameter("gala_id");
	gala.price = request.getParameter("price");
	gala.child_discount = request.getParameter("child_discount");
	gala.adult_discount = request.getParameter("adult_discount");
	if (request.getParameter("obligatory") != null) {
		gala.obligatory = request.getParameter("obligatory");
	} else {
		gala.obligatory = "0";
	}
	
	xcaping.Contract contract = new xcaping.Contract();
	if (contract.addGala(gala)) {
		follow = gala.returnContractURL;
	} else {
		follow = gala.errorContractURL+gala.error;
	}
    response.sendRedirect(follow);
%>