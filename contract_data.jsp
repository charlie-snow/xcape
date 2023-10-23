<%
	String contract_id = request.getParameter("contract_id");
	xcaping.Contract contract = new xcaping.Contract();
	contract.get(contract_id);
	
	boolean generated = contract.state.equals("active") || contract.state.equals("inactive");
	boolean finished = contract.state.equals("finished");
	boolean not_generated = contract.state.equals("not_generated");
	//boolean active = contract.state.equals("active");
	
	String tr_class_contract = "";
	if (generated) {
		tr_class_contract="active";
	} else {
		if (finished) {
			tr_class_contract="finished";
		} else {
			tr_class_contract="not_generated";
		}
	}
%>