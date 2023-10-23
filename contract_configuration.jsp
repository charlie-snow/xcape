<%@ include file="contract_data.jsp"%>


<table width="900" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td colspan="2" align="center" class="sub-title">Contract configuration</td>
</tr>
<tr>
<% 	if (finished || generated || !session.getAttribute("contract_edit").equals("true")) { %>
	<td align="center" valign="top">
	.:board_bases:.<br>
	<iframe src="board_basesSelected.jsp?contract_id=<%= contract_id %>" name="board_basesSelection" id="board_basesSelection" width="400" height="200" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" style="background-color:transparent;"></iframe>
	</td>
	<td align="center" valign="top">
	.:discounts:.<br>
	<iframe src="discountsSelected.jsp?contract_id=<%= contract_id %>" name="discountsSelection" id="discountsSelection" width="400" height="200" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" style="background-color:transparent;"></iframe>
	</td>
<% } else { %>
	<td align="center" valign="top">
	.:board_bases:.<br>
	<iframe src="board_basesSelection.jsp?contract_id=<%= contract_id %>" name="board_basesSelection" id="board_basesSelection" width="400" height="200" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" style="background-color:transparent;"></iframe>
	</td>
	<td align="center" valign="top">
	.:discounts:.<br>
	<iframe src="discountsSelection.jsp?contract_id=<%= contract_id %>" name="discountsSelection" id="discountsSelection" width="400" height="200" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" style="background-color:transparent;"></iframe>
	</td>
<% } %>	
</tr>
</table>


