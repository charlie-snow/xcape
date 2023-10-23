<%
	String contract_id = request.getParameter("contract_id");
	
	java.util.Vector data = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration enum_data;
	
	data = list.getRoomsContract(contract_id, false);	
	xcaping.Room room = new xcaping.Room();
%>
<head>
<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>
	
	<table border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
		<td align="center">name</td>
	</tr>
	
	<%
		String tr_class = "";
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			room = (xcaping.Room)enum_data.nextElement();
			if (room.active) {
				tr_class = "light";
			} else {
				tr_class = "inactive_room";
			}
	%>
	
	<tr class="<%= tr_class %>">
		<td align="center"><%= room.name %></td>
		</td>
	</tr>
	
	<%}%>
	
	</table>