<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>availability</title>
	<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
	<link rel="STYLESHEET" type="text/css" href="links.css">
	<link rel="stylesheet" href="Style1.css" type="text/css">
</head>

<body>

<% 
	String item_id = request.getParameter("item_id");
	String contract_id = "";
	if (request.getParameter("contract_id") != null) {
		contract_id = request.getParameter("contract_id");
	} else {
		contract_id = "0";
	}
	
	xcaping.Item item = new xcaping.Item("5");
	item.get(item_id);
	xcaping.Rating rating = new xcaping.Rating();
	rating.get(item.rating);
	
	xcaping.Contract contract = new xcaping.Contract();
	xcaping.Supplier supplier = new xcaping.Supplier();
	boolean is_contract = !contract_id.equals("0");
	if (is_contract) {
		contract.get(contract_id);
		supplier.get(contract.supplier);
	}
	
	xcaping.Room room = new xcaping.Room();
	java.util.Enumeration e; 
%>

<table border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td align="center"><strong>Property:</strong> <%= item.name %> - <%= rating.name %> - code: <%= item.id %> </td>
</tr>
<% if (is_contract) { %>
<tr>
	<td align="center"><strong>Contract:</strong> <%= contract.name %> - From <%= contract.from_date %>
	&nbsp;&nbsp;To <%= contract.to_date %>&nbsp;&nbsp;Supplier <%= supplier.name %> </td>
</tr>
<% } %>
<tr>
	<td class="sub-title">Availabilities</td>
</tr>
<tr>
	<td>
	<%@ include file="availabilitiesPrintList.jsp"%>
	</td>
</tr>
</table>

</body>
</html>