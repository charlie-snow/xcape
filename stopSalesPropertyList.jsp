<%
	String output;
	java.text.SimpleDateFormat formatter;
	java.util.Date today = new java.util.Date();
	formatter = new java.text.SimpleDateFormat("dd MM yy");
	output = formatter.format(today);

	java.util.Vector data_stop_sales = new java.util.Vector();
	xcaping.Lists list_stop_sales = new xcaping.Lists();
	
	data_stop_sales = list_stop_sales.getStopSalesProperty(property_id);
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
	<td></td>
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
	
	<td align="center"><a href="deleteStopSale.jsp?stop_sale_id=<%= stopSale.id %>&property_id=<%= property_id %>">delete</a></td>
	</tr>
<% } %>

<form action="insertStopSale.jsp" method="post" name="insertStopSale" id="insertStopSale">
<input type="hidden" name="edited_by" id="edited_by" value="<%= session.getAttribute("user") %>">
<input type="hidden" name="edit_date" id="edit_date" value="<%= output %>">

<input type="hidden" name="item_id" id="item_id" value="<%= property_id %>">
<tr>
	<td align="center">
	<input type="text" name="from_date" id="from_date" size="6">
	</td>
	<td align="center">
	<input type="text" name="to_date" id="to_date" size="6">
	</td>
	<td align="center">
	<select name="room" id="room" size="1">
		<option value="0" selected>-- all --</option>
		<%
		for (e = data_rooms.elements() ; e.hasMoreElements() ;) {
			room = (xcaping.Room)e.nextElement();
			if (room.active) {
%>
		<option value="<%= room.id %>"><%= room.name %></option>
		<% } } %>
	</select>
	</td>
	<td><input type="submit" name="submit" id="submit" value="ok"></td>
</form>

</tr>

</table>
