<% 
	String item_id = request.getParameter("item_id");
	String contract_id = "";
	if (request.getParameter("contract_id") != null) {
		contract_id = request.getParameter("contract_id");
	} else {
		contract_id = "0";
	}
	
	xcaping.Room room = new xcaping.Room();
	java.util.Enumeration e; 
%>

<table border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td class="sub-title">Availabilities    ------------------------------------  
	<a href="availabilitiesPrint.jsp?item_id=<%= item_id %>
	&contract_id=<%= contract_id %>&history=0" target="_blank" class="blue">print report</a>
	</td>
</tr>
<tr>
	<td>
	<%@ include file="availabilitiesList.jsp"%>
	</td>
</tr>
</table>