<%@ include file="author_init.jsp"%>

<%
	manager = false;
	properties_manager = false;
	contracts_manager = true;
	contracts_operator = true;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<%
	String contract_id = request.getParameter("contract_id");
	String modify = request.getParameter("modify");
	String allocation_id = "";
	if (modify.equals("1")) {
		allocation_id = request.getParameter("allocation_id");
	}
	String follow = "";
	xcaping.Allocation allocation = new xcaping.Allocation();
	
	allocation.contract = contract_id;
	allocation.from_date = request.getParameter("from_date");
	allocation.to_date = request.getParameter("to_date");
	allocation.room = request.getParameter("room");
	allocation.allocation = request.getParameter("allocation");
	
	allocation.edited_by = request.getParameter("edited_by");
	
	boolean ok = false;
	if (modify.equals("1")) {
		ok = allocation.modify(allocation_id);
	} else {
		ok = allocation.insert(contract_id);
	}
	
	if (ok) {
		follow = allocation.returnURL;
	} else {
		follow = allocation.errorURL+allocation.error;
	}
    response.sendRedirect(follow);
%>
			
<%@ include file="author_footer.jsp"%>

