<%@ include file="contract_data.jsp"%>
<%@ include file="contract_data_extra.jsp"%>

<table border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td class="sub-title">Contract info</td>
</tr>
<tr>
	<td>
	<% if (!session.getAttribute("contract_edit").equals("true") || finished) { %>
		<%@ include file="contract_view.jsp"%>
	<% } else { %>
		<%@ include file="formEditContract.jsp"%>
	<% } %>
	</td>
</tr>
</table>