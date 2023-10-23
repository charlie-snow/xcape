<%
	xcaping.Lists list = new xcaping.Lists();
	java.util.Vector data = new java.util.Vector();
	
	xcaping.Room room = new xcaping.Room();
	java.util.Enumeration e;
%>

<script language="JavaScript">
function confirmDelete(room_id, item_id){
	if (confirm("WARNING: if you delete this room, all the prices for all the periods on the contracts will be lost")) {
		window.location = "subRoomExtraProperty.jsp?room_id="+room_id+"&item_id="+item_id;
	}
}
</script>

<table border="0" cellspacing="0" cellpadding="10" align="center">
<tr>
	<td align="center" valign="top">
	property extra rooms
	<table width="350" border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
		<td align="center">name</td>
		<td></td>
	</tr>
		
	<%
	data = list.getRoomsExtraProperty(item_id);
	if (data.isEmpty()) {
%>
	<tr>
	<td align="center">there are no rooms in this property</td>
	</tr>
<%
	} else {
		for (e = data.elements() ; e.hasMoreElements() ;) {
			room = (xcaping.Room)e.nextElement();
%>	
	<tr>
		<td align="left" title="<%= room.id %>"><%= room.name %></td>
		<td><a href="#" onClick="confirmDelete(<%= room.id %>, <%= item_id %>)">del</a></td>
	</tr>
	<%	}
	}%>
	
	</table>
	
	<br><br>
	property rooms
	<table width="350" border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
		<td align="center">name</td>
	</tr>
		
	<%
	data = list.getRoomsProperty(item_id);
	if (data.isEmpty()) {
%>
	<tr>
	<td align="center">there are no rooms in this property</td>
	</tr>
<%
	} else {
		for (e = data.elements() ; e.hasMoreElements() ;) {
			room = (xcaping.Room)e.nextElement();
%>	
	<tr>
		<td align="left"><%= room.name %></td>
	</tr>
	<%	}
	}%>
	
	</table>	
	
	</td>
	<td align="center" valign="top">
	
	system rooms
	<table width="400" border="1" cellspacing="0" cellpadding="0" align="center">
		
	<form action="addRoomExtraProperty.jsp" method="post" name="addRoomExtraProperty" id="addRoomExtraProperty">
	<input type="hidden" name="item_id" id="item_id" value="<%= item_id %>">
	<tr>
		<td align="center">
<%
	data = list.getRoomsExtra();
	if (data.isEmpty()) {
		out.print("there are no rooms");
	} else { %>
	
	<select name="room_id" id="room_id" size="1">
		
<%
		for (e = data.elements() ; e.hasMoreElements() ;) {
			room = (xcaping.Room)e.nextElement();
%>	
	<option value="<%= room.id %>"><%= room.name %></option>

		<% } %>
	
	</select>&nbsp;&nbsp;
	<input type="submit" name="submit" id="submit" value="add">
	<% }%>
</form>
		</td>
	</tr>
	</table>
	
	</td>
</tr>
</table>
