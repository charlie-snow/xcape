<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>preview - availability and pricing</title>
	<link href="Style1.css" rel="stylesheet" type="text/css">
</head>

<body>

<% 
	String contract_id = request.getParameter("contract_id");
	xcaping.Converter converter = new xcaping.Converter();
	converter.contract_id = contract_id;
	xcaping.Supplier supplier = new xcaping.Supplier();
	java.util.Enumeration e;
	
	converter.getAvailabilities(contract_id, false);
	xcaping.Availability availability = new xcaping.Availability();
	
	xcaping.RoomSupplement roomSupplement = new xcaping.RoomSupplement();
	java.util.Enumeration roomsE;
	xcaping.Currency currency = new xcaping.Currency();
	
	converter.getPricing(contract_id, false);
	xcaping.Priceline priceline = new xcaping.Priceline();
 %>


<div align="center">.: Availability :.</div>

<%@ include file="availability.jsp"%>

<br>
-
<br>
<div align="center">.: Pricing :.</div>

<%@ include file="pricing.jsp"%>

<br><br>

<%@ include file="sqlAvailability.jsp"%>

<br><br>

<%@ include file="sqlPricing.jsp"%>

</body>
</html>
