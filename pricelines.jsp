<% 
	String item_id = request.getParameter("item_id");
	String contract_id = "";
	if (request.getParameter("contract_id") != null) {
		contract_id = request.getParameter("contract_id");
	} else {
		contract_id = "0";
	}
	boolean is_contract = !contract_id.equals("0");
	xcaping.Supplier supplier = new xcaping.Supplier();
	xcaping.Room room = new xcaping.Room();
	xcaping.Currency currency = new xcaping.Currency();
	java.util.Enumeration e; 
%>

<table border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td class="sub-title">Pricelines    ------------------------------------  
	<a href="pricelinesPrint.jsp?item_id=<%= item_id %>
	&contract_id=<%= contract_id %>&history=0" target="_blank" class="blue">print report</a></td>
</tr>
<tr>
	<td>
	<%@ include file="pricelinesList.jsp"%>
	</td>
</tr>
</table>