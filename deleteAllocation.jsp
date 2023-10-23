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
	String allocation_id = request.getParameter("allocation_id");
	xcaping.Allocation allocation = new xcaping.Allocation();
	
	String follow = "";
	
	if (allocation.delete(allocation_id)) {
		follow = allocation.returnURL;
	} else {
		follow = allocation.errorURL+allocation.error;
	}
    response.sendRedirect(follow);
%>

<%@ include file="author_footer.jsp"%>


