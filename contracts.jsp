<% 
	String item_id = request.getParameter("item_id");
%>

<table border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td class="sub-title">Contracts</td>
</tr>
<tr>
	<td>
	<%@ include file="contractsList.jsp"%>
	</td>
</tr>
<tr>
	<td height="10"></td>
</tr>
<% if (session.getAttribute("contract_edit").equals("true")) { %>
<tr>
	<td class="sub-title">Insert New Contract</td>
</tr>
<tr>
	<td>
	<%@ include file="formInsertContract.jsp"%>
	</td>
</tr>
<% } %>
</table>