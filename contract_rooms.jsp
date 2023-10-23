<%@ include file="contract_data.jsp"%>
<table width="900" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>
<% 	if (finished || generated || !session.getAttribute("contract_edit").equals("true")) { %>
		<td align="center" valign="top">
		.:rooms:.<br>
		<iframe src="roomsView.jsp?contract_id=<%= contract_id %>" name="roomsView" id="roomsView" width="400" height="200" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" style="background-color:transparent;"></iframe>
		</td>
<% } else { %>
		<td align="center" valign="top">
		.:rooms:.<br>
		<iframe src="roomsActivation.jsp?contract_id=<%= contract_id %>" name="roomsActivation" id="roomsActivation" width="400" height="200" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" style="background-color:transparent;"></iframe>
		</td>
<% } %>
	</tr>
</table>
