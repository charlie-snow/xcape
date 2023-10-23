<%@ include file="contract_data.jsp"%>

<%
	boolean has_child_discount = contract.hasChildDiscount(contract_id);
	boolean has_adult_discount = contract.hasAdultDiscount(contract_id);
%>

<table width="500" border="0" cellspacing="2" cellpadding="4" align="center">
	<%@ include file="contract_add_ons_list.jsp"%>
	
<% 	if (!finished && session.getAttribute("contract_edit").equals("true")) { %>
	<%@ include file="form_insert_add_on.jsp"%>
<% } %>

</table>