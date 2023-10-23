<%
	java.util.Vector data = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration enum_data;
	
	data = list.getDiscounts();
	xcaping.Discount discount = new xcaping.Discount();
%>
<head>
<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>

<table width="90%" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">name</td>
	<td></td>
	<td></td>
</tr>

<%
	for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
		discount = (xcaping.Discount)enum_data.nextElement();
%>

<tr>
	<td align="center"><%= discount.name %></td>
</tr>

<%}%>

</table>