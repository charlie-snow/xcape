<%
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	boolean is_property = item_type.equals("5");
	String view = request.getParameter("view");
	boolean view_only = item_type.equals("1");
	
	String output;
	java.text.SimpleDateFormat formatter;
	java.util.Date today = new java.util.Date();
	formatter = new java.text.SimpleDateFormat("dd MM yy");
	output = formatter.format(today);
	
	// property: stop sales list objects declaration
	java.util.Vector data_stop_sales = new java.util.Vector();
	xcaping.Lists list_stop_sales = new xcaping.Lists();
	xcaping.StopSale stopSale;
	
	// property: rooms
	java.util.Vector data_rooms = new java.util.Vector();
	xcaping.Lists list_rooms = new xcaping.Lists();
	data_rooms = list_rooms.getRoomsProperty(item_id);
	int rooms_count = data_rooms.size();
	xcaping.Room room = new xcaping.Room();
	java.util.Enumeration e; 
	
	if (is_property) {
		data_stop_sales = list_stop_sales.getStopSalesProperty(item_id);
	}
	
	xcaping.Item item = new xcaping.Item(item_type);
	item.get(item_id);
%>

<% if (is_property) { %>

	<table width="550" border="0" cellspacing="2" cellpadding="4" align="center">
		<tr class="header">
		<td colspan="4" align="center"><strong>.:stop sales:.</strong></td>
	</tr>
	<tr>
		<td align="center">from date</td>
		<td align="center">to date</td>
		<td align="center">room</td>
		<td align="center">by</td>
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
		<td align="center"><%= stopSale.edited_by %></td>
		<% if (view_only) { %>
			<td></td>
		<% } else { %>
			<td align="center"><a href="deleteStopSale.jsp?stop_sale_id=<%= stopSale.id %>&item_id=<%= item_id %>&item_type=<%= item_type %>">delete</a></td>
		<% } %>
		</tr>
	<% } %>
	</table>
	
<% } else { %>
	<script language="JavaScript">
	function confirmST(){
		if (confirm("WARNING: are you absolutely sure you want to do a stop sale for <%= item.name %>, from "+document.getElementById('insertStopSale').from_date.value+" to "+document.getElementById('insertStopSale').to_date.value+" ?")) {
			document.getElementById('insertStopSale').submit();
		}
	}
	</script>
<% } %>

<table width="550" border="0" cellspacing="2" cellpadding="4" align="center">
<form action="insertStopSale.jsp" method="post" name="insertStopSale" id="insertStopSale">
<input type="hidden" name="edited_by" id="edited_by" value="<%= session.getAttribute("user") %>">
<input type="hidden" name="edit_date" id="edit_date" value="<%= output %>">

<input type="hidden" name="item_id" id="item_id" value="<%= item_id %>">
<input type="hidden" name="item_type" id="item_type" value="<%= item_type %>">
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
			room = (xcaping.Room)e.nextElement();%>
		<option value="<%= room.id %>"><%= room.name %></option>
		<% } %>
	</select>
	</td>
	<td>
	<% if (is_property) { %>
		<input type="submit" name="submit" id="submit" value="ok">
	<% } else { %>
		<a href="#" onClick="confirmST()">ok</a>
	<% } %>
	</td>
</form>
</tr>
</table>