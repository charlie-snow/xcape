<%
	String output;
	java.text.SimpleDateFormat formatter;
	java.util.Date today = new java.util.Date();
	formatter = new java.text.SimpleDateFormat("dd MM yy");
	output = formatter.format(today);

	java.util.Vector data_stop_sales = new java.util.Vector();
	xcaping.Lists list_stop_sales = new xcaping.Lists();
	
	data_stop_sales = list_stop_sales.getStopSalesContract(contract_id);
	xcaping.StopSale stopSale = new xcaping.StopSale();
%>

<table width="550" border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
	<td colspan="4" align="center"><strong>.:stop sales:.</strong></td>
</tr>
<tr>
	<td align="center">from date</td>
	<td align="center">to date</td>
	<td align="center">room</td>
</tr>

<%
	for (e = data_stop_sales.elements() ; e.hasMoreElements() ;) {
		stopSale = (xcaping.StopSale)e.nextElement();
		room = new xcaping.Room();
		if (stopSale.room.equals("0")) {
			room.name = "-- all --";
		} else {
			room.get(stopSale.room);
		}
%>

<tr>
	<td align="center"><%= stopSale.from_date %></td>
	<td align="center"><%= stopSale.to_date %></td>
	<td align="center"><%= room.name %></td>
</tr>
<% } %>

</table>
