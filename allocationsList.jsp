<%
	java.util.Vector data_allocations = new java.util.Vector();
	xcaping.Lists list_allocations = new xcaping.Lists();
	
	data_allocations = list_allocations.getAllocations(contract_id);
	xcaping.Allocation allocation = new xcaping.Allocation();
%>

<table width="550" border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
	<td colspan="4" align="center"><strong>.:allocations:.</strong></td>
</tr>
<tr>
	<td align="center">from date</td>
	<td align="center">to date</td>
	<td align="center">room</td>
	<td align="center">allocation</td>
	<td></td>
	<td></td>
</tr>

<%
	for (e = data_allocations.elements() ; e.hasMoreElements() ;) {
		allocation = (xcaping.Allocation)e.nextElement();
		room.get(allocation.room);
%>

<tr>
	<td align="center"><%= allocation.from_date %></td>
	<td align="center"><%= allocation.to_date %></td>
	<td align="center"><%= room.name %></td>
	<td align="center"><%= allocation.allocation %></td>
	
	<td align="center"><a href="index.jsp?content=contract&subcontent=formEditAllocation&contract_id=<%= contract_id %>&allocation_id=<%= allocation.id %>">edit</a></td>
	<td align="center"><a href="deleteAllocation.jsp?allocation_id=<%= allocation.id %>&contract_id=<%= contract_id %>">delete</a></td>
	</tr>
<% } %>

<form action="manageAllocation.jsp" method="post" name="manageAllocation" id="manageAllocation">
<input type="hidden" name="edited_by" id="edited_by" value="<%= session.getAttribute("user") %>">
<input type="hidden" name="modify" id="modify" value="0">

<input type="hidden" name="contract_id" id="contract_id" value="<%= contract_id %>">
<tr>
	<td align="center">
	<input type="text" name="from_date" id="from_date" size="6">
	</td>
	<td align="center">
	<input type="text" name="to_date" id="to_date" size="6">
	</td>
	<td align="center">
	<select name="room" id="room" size="1">
		<%
		for (e = data_rooms.elements() ; e.hasMoreElements() ;) {
			room = (xcaping.Room)e.nextElement();%>
		<option value="<%= room.id %>"><%= room.name %></option>
		<% } %>
	</select>
	</td>
	<td align="center">
	<input type="text" name="allocation" id="allocation" size="6">
	</td>
	<td colspan="2" align="center"><input type="submit" name="submit" id="submit" value="ok"></td>
</form>

</tr>

</table>
