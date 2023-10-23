<%@ include file="contract_data.jsp"%>

<table border="0" cellspacing="0" cellpadding="0" align="center">
<% 
	// identify what url are we in
	
	String url = "index.jsp%3fcontent%3dcontractStopSales%26contract_id%3d"+contract_id;

	// rooms
	
	java.util.Vector data_rooms = new java.util.Vector();
	xcaping.Lists list_rooms = new xcaping.Lists();
	
	data_rooms = list_rooms.getRoomsContract(contract.id, true);
	int rooms_count = data_rooms.size();
	xcaping.RoomPeriod roomPeriod = new xcaping.RoomPeriod();
	xcaping.Room room = new xcaping.Room();
	
	java.util.Enumeration e; 
%>

<tr>
	<td>
		<table border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
				<%@ include file="allocationsList.jsp"%>
			</td>
		</tr>
		
		<% if (session.getAttribute("av_pr").equals("true")) { %>
		<tr>
			<td class="sub-title">Availabilities</td>
		</tr>
		<tr>
			<td>
			<%@ include file="availabilitiesList.jsp"%>
			</td>
		</tr>
		<% } %>
		
		</table>
	</td>
</tr>
</table>