<%
	String contract_id = request.getParameter("contract_id");
	
	java.util.Vector data = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration enum_data;
	
	data = list.getDiscountsContract(contract_id);
	xcaping.Discount discount = new xcaping.Discount();
%>

<head>
<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>

<table width="400" border="0" cellspacing="0" cellpadding="2" align="center">
<tr>
	<td align="center" valign="top">
	property discounts
	<table border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
		<td align="center">name</td>
		<td></td>
	</tr>
	
	<%
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			discount = (xcaping.Discount)enum_data.nextElement();
	%>
	
	<tr>
		<td align="center"><%= discount.name %></td>
		<td align="center"><a href="subDiscount.jsp?discount_id=<%= discount.id %>&contract_id=<%= contract_id %>">del</a></td>
	</tr>
	
	<%}%>
	
	</table>
	
	</td>
	
	<td align="center">
	
	system discounts
	<table border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
		<td align="center">name</td>
		<td></td>
	</tr>
	
	<%
		data = list.getDiscounts();	
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			discount = (xcaping.Discount)enum_data.nextElement();
	%>
	
	<tr>
		<td align="center"><%= discount.name %></td>
		<td align="center"><a href="addDiscount.jsp?discount_id=<%= discount.id %>&contract_id=<%= contract_id %>">add</a></td>
	</tr>
	
	<%}%>
	
	</table>
	
	</td>
</tr>
</table>


