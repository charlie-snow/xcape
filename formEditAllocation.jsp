<%
	String allocation_id = request.getParameter("allocation_id");
	String contract_id = request.getParameter("contract_id");
	xcaping.Allocation allocation = new xcaping.Allocation();
	allocation.get(allocation_id);
	
	// rooms
	
	java.util.Vector data_rooms = new java.util.Vector();
	xcaping.Lists list_rooms = new xcaping.Lists();
	
	data_rooms = list_rooms.getRoomsContract(contract_id, true);
	int rooms_count = data_rooms.size();
	xcaping.RoomPeriod roomPeriod = new xcaping.RoomPeriod();
	xcaping.Room room = new xcaping.Room();
	
	java.util.Enumeration e; 
%>

<form action="manageAllocation.jsp" method="post" name="manageAllocation" id="manageAllocation">
<input type="hidden" name="edited_by" id="edited_by" value="<%= session.getAttribute("user") %>">
<input type="hidden" name="modify" id="modify" value="1">
<input type="hidden" name="contract_id" id="contract_id" value="<%= allocation.contract %>">
<input type="hidden" name="allocation_id" id="allocation_id" value="<%= allocation_id %>">

<table border="0" align="center">
<tr>
	<td align="center">
	<input type="text" name="from_date" id="from_date" value="<%= allocation.from_date %>" size="6">
	</td>
	<td align="center">
	<input type="text" name="to_date" id="to_date" value="<%= allocation.to_date %>" size="6">
	</td>
	<td align="center">
	<select name="room" id="room" size="1">
		<%
		for (e = data_rooms.elements() ; e.hasMoreElements() ;) {
			room = (xcaping.Room)e.nextElement(); %>
		<option 
		<%  if (Integer.parseInt(allocation.room) == Integer.parseInt(room.id)) { %>selected<% } %>
		 value="<%= room.id %>"><%= room.name %></option>
		<% } %>
	</select>
	</td>
	<td align="center">
	<input type="text" name="allocation" id="allocation" value="<%= allocation.allocation %>" size="6">
	</td>
	<td><input type="submit" name="submit" id="submit" value="ok"></td>
</tr>
</form>
</table>
