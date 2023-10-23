<script language="JavaScript1.2">

function abs() {
	alert ('All: allocation, cupo -- R: release');
}

function changePU(room_id) {
	document.getElementById('room_unit_changed'+room_id).value = '1';
	if (document.getElementById('room_unit'+room_id).value == '1') {
		document.getElementById('room_unit'+room_id).value = '0';
		document.getElementById('pu_'+room_id).innerHTML = '<strong>P</strong>';
	} else {
		document.getElementById('room_unit'+room_id).value = '1';
		document.getElementById('pu_'+room_id).innerHTML = '<strong>U</strong>';
	}
}

function changeSSpercentage() {
	if (document.getElementById('short_stay_percentage').value = '1') {
		document.getElementById('short_stay_percentage').value = '0';
		document.getElementById('ss').innerHTML = '<strong><%= currency.symbol %></strong>';
		SSpercentage = false;
	} else {
		document.getElementById('short_stay_percentage').value = '1';
		document.getElementById('ss').innerHTML = '<strong>%</strong>';
		SSpercentage = true;
	}
}

function changeDiscPercentage(discount_id) {
	if (document.getElementById('discount_percentage'+discount_id).value == '1') {
		document.getElementById('discount_percentage'+discount_id).value = '0';
		document.getElementById('disc_'+discount_id).innerHTML = '<strong><%= currency.symbol %></strong>';
	} else {
		document.getElementById('discount_percentage'+discount_id).value = '1';
		document.getElementById('disc_'+discount_id).innerHTML = '<strong>%</strong>';
	}
}
</script>

<form action="insertPeriod.jsp" method="post" name="insertPeriod" id="insertPeriod">
<input type="hidden" name="contract_id" id="contract_id" value="<%= contract_id %>">
<input type="hidden" name="period_id" id="period_id" value="<%= period_id %>">
<input type="hidden" name="edited_by" id="edited_by" value="<%= session.getAttribute("user") %>">
<input type="hidden" name="edit_date" id="edit_date" value="<%= output %>">
<input type="hidden" name="offer" id="offer" value="1">
<input type="hidden" name="last_complementary" id="last_complementary" value="<%= last_complementary %>">

<table width="900" border="0" cellspacing="0" cellpadding="5" align="center">
<tr>
	<% if (last_complementary) { %>
		<td>From <%= complementary.from_date %> <input type="hidden" name="from_date" id="from_date" value="<%= complementary.from_date %>"></td>
		<td>To <%= complementary.to_date %> <input type="hidden" name="to_date" id="to_date" value="<%= complementary.to_date %>"></td>
		<td>Minimum Stay
			<%= complementary.minimum_stay %>
			<input type="hidden" name="minimum_stay" id="minimum_stay" value="<%= complementary.minimum_stay %>">
		</td>
	<% } else { %>
		<td>From <input type="text" name="from_date" id="from_date" size="8" value="<%= period.from_date %>"></td>
		<td>To <input type="text" name="to_date" id="to_date" size="8" value="<%= period.to_date %>"></td>
		<td>Minimum Stay
			<input type="text" name="minimum_stay" id="minimum_stay" value="<%= period.minimum_stay %>">
		</td>
	<% }%>
</tr>
</table>

<table width="850" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<% if (board_bases_count != 0) {%>
	<td colspan="<%= board_bases_count %>" align="center" class="highlight">
	Board Bases</td>
	<% } if (discounts_count != 0) {%>
	<td colspan="<%= discounts_count %>" align="center" class="highlight">
	Discounts&nbsp;&nbsp;<img src="images/minus.gif" width="15" height="15" alt="" border="0"></td>
	<% } if (rooms_count != 0) {%>
	<td colspan="<%= (rooms_count*4)+1 %>" align="center" class="highlight">
	Supplements&nbsp;&nbsp;<img src="images/plus.gif" width="15" height="15" alt="" border="0"></td>
	<% } %>
</tr>
<tr>
	<% if (board_bases_count != 0) {%>
	<td colspan="<%= board_bases_count %>"></td>
	<% } if (discounts_count != 0) {%>
	<td colspan="<%= discounts_count %>"></td>
	<% } if (rooms_count != 0) {
			for (e = data_rooms.elements() ; e.hasMoreElements() ;) {
					room = (xcaping.Room)e.nextElement();
					out.print("<td align='center' colspan='4'>"+room.name+"</td>");
				}
	 } %>
		
	<td></td>
</tr>
<tr>
	
	<% 
		for (e = data_board_bases.elements() ; e.hasMoreElements() ;) {
			board_basis = (xcaping.BoardBasis)e.nextElement();
			out.print("<td align='center'>"+board_basis.name+"</td>");
		}
	%>
	
	<% 
		for (e = data_discounts.elements() ; e.hasMoreElements() ;) {
			discount = (xcaping.Discount)e.nextElement();
			out.print("<td align='center'>"+discount.name+"</td>");
		}
	%>
	
	<% for (int i=0; i<rooms_count; i++) {
		out.print("<td align='center'>"+currency.symbol+"</td>");
		out.print("<td align='center'>P/U</td>");
		out.print("<td align='center'>All.</td>");
		out.print("<td align='center'>R.</td>");
	} %>
	
	<td align="center">short stay</td>
</tr>

<tr>

	<% 
	for (e = data_board_bases.elements() ; e.hasMoreElements() ;) {
		board_basis = (xcaping.BoardBasis)e.nextElement();
	%>
	<td align="center">
	<input type="text" name="board_basis_price<%= board_basis.id %>" id="board_basis_price<%= board_basis.id %>" size="2">
	<input type="hidden" name="board_basis_percentage<%= board_basis.id %>" id="board_basis_percentage<%= board_basis.id %>" value="0">
	</td>
	<% } %>
		
	<% 
	for (e = data_discounts.elements() ; e.hasMoreElements() ;) {
		discount = (xcaping.Discount)e.nextElement();
	%>
	<td align="center">
		<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><input type="text" name="discount_amount<%= discount.id %>" id="discount_amount<%= discount.id %>" size="2"></td>
			<td align="right">
			<a href="#" onClick="changeDiscPercentage(<%= discount.id %>)">
			<spam name="disc_<%= discount.id %>" id="disc_<%= discount.id %>"><strong>%</strong></spam></a>
			<input type="hidden" name="discount_percentage<%= discount.id %>" id="discount_percentage<%= discount.id %>" value="1">
		</tr>
		</table>
	</td>
	<% } %>

	<% 
	for (e = data_rooms.elements() ; e.hasMoreElements() ;) {
		room = (xcaping.Room)e.nextElement(); %>
	<td align="center">
		<input type="text" name="room_price<%= room.id %>" id="room_price<%= room.id %>" size="2">
	</td>
	<td align="center">
	<a href="#" onClick="changePU(<%= room.id %>)">
	<spam name="pu_<%= room.id %>" id="pu_<%= room.id %>"><strong>P</strong></spam>
	</a>
	<input type="hidden" name="room_unit<%= room.id %>" id="room_unit<%= room.id %>" value="0">
	<input type="hidden" name="room_unit_changed<%= room.id %>" id="room_unit_changed<%= room.id %>" value="0">
	</td>
	<td align="center">
	<input type="text" name="room_allotment<%= room.id %>" id="room_allotment<%= room.id %>" size="2">
	</td>
	<td align="center">
	<input type="text" name="room_release<%= room.id %>" id="room_release<%= room.id %>" size="2">
	</td>
	<% } %>
	
	<td align="center">
		<table border="0" cellspacing="0" cellpadding="0">
		<tr>
		
		<% if (last_complementary) { %>
			<td><input type="text" name="short_stay_suplement" id="short_stay_suplement" size="2" value="<%= complementary.short_stay_suplement %>"></td>
			<td align="right">
			<a href="#" onClick="changeSSpercentage()">
			<spam name="ss" id="ss"><strong>%</strong></spam></a>
			<input type="hidden" name="short_stay_percentage" id="short_stay_percentage" value="1">
			<% if (complementary.short_stay_percentage.equals("0")) { %>
				 <script> changeSSpercentage(); </script>
			<% } %>
			</td>
		<% } else { %>
			<td><input type="text" name="short_stay_suplement" id="short_stay_suplement" size="2" value="<%= period.short_stay_suplement %>"></td>
			<td align="right">
			<a href="#" onClick="changeSSpercentage()">
			<spam name="ss" id="ss"><strong>%</strong></spam></a>
			<input type="hidden" name="short_stay_percentage" id="short_stay_percentage" value="1">
			<% if (period.short_stay_percentage.equals("0")) { %>
				 <script> changeSSpercentage(); </script>
			<% } %>
			</td>
		<% }%>
		</tr>
		</table>
	</td>
	<td align="center">
	</td>
</tr>

</table>

<table width="900" border="0" cellspacing="0" cellpadding="5" align="center">
<tr>
	<td align="center">
	Active Days 
		
	<%
		active_days = "";
		if (Integer.parseInt(period.active_monday) == 1) { active_days+="M"; }
		if (Integer.parseInt(period.active_tuesday) == 1) { active_days+=", Tu"; }
		if (Integer.parseInt(period.active_wednesday) == 1) { active_days+=", W"; }
		if (Integer.parseInt(period.active_thursday) == 1) { active_days+=", Th"; }
		if (Integer.parseInt(period.active_friday) == 1) { active_days+=", F"; }
		if (Integer.parseInt(period.active_saturday) == 1) { active_days+=", Sa"; }
		if (Integer.parseInt(period.active_sunday) == 1) { active_days+=", Su"; }
	%>
	
	<%= active_days %>
	
	<input type="hidden" name="active_monday" id="active_monday" value="<%= period.active_monday %>">	
	<input type="hidden" name="active_tuesday" id="active_tuesday" value="<%= period.active_tuesday %>">
	<input type="hidden" name="active_wednesday" id="active_wednesday" value="<%= period.active_wednesday %>">
	<input type="hidden" name="active_thursday" id="active_thursday" value="<%= period.active_thursday %>">
	<input type="hidden" name="active_friday" id="active_friday" value="<%= period.active_friday %>">
	<input type="hidden" name="active_saturday" id="active_saturday" value="<%= period.active_saturday %>">
	<input type="hidden" name="active_sunday" id="active_sunday" value="<%= period.active_sunday %>">

	<select name="agent_group" id="agent_group" size="1">
	<%
	for (enum_data_agent_groups = data_agent_groups.elements(); enum_data_agent_groups.hasMoreElements();) {
		agent_group = (xcaping.AgentGroup)enum_data_agent_groups.nextElement();
	%>
		<option value="<%= agent_group.group_id %>"
		<%  if (Integer.parseInt(period.agent_group) == Integer.parseInt(agent_group.group_id)) { %>selected<% } %>>
		<%= agent_group.group_name %></option>
	<% } %>
	</select>
		
	</td>
	<td align="center">
	Arrival Days 
	
	M<input type="checkbox" name="arrival_monday" id="arrival_monday" value="1" 
	<% if (contract.arrival_monday.equals("1")) { %>
		 checked
	<% } %>>
	Tu<input type="checkbox" name="arrival_tuesday" id="arrival_tuesday" value="1"
	<% if (contract.arrival_tuesday.equals("1")) { %>
		 checked
	<% } %>>
	W<input type="checkbox" name="arrival_wednesday" id="arrival_wednesday" value="1"
	<% if (contract.arrival_wednesday.equals("1")) { %>
		 checked
	<% } %>>
	Th<input type="checkbox" name="arrival_thursday" id="arrival_thursday" value="1"
	<% if (contract.arrival_thursday.equals("1")) { %>
		 checked
	<% } %>>
	F<input type="checkbox" name="arrival_friday" id="arrival_friday" value="1"
	<% if (contract.arrival_friday.equals("1")) { %>
		 checked
	<% } %>>
	Sa<input type="checkbox" name="arrival_saturday" id="arrival_saturday" value="1"
	<% if (contract.arrival_saturday.equals("1")) { %>
		 checked
	<% } %>>
	Su<input type="checkbox" name="arrival_sunday" id="arrival_sunday" value="1"
	<% if (contract.arrival_sunday.equals("1")) { %>
		 checked
	<% } %>>
		
	</td>
	<td align="right"><a href="#" onClick="abs()">help</a></td>
	<td align="right">
	<input type="submit" name="submit" id="submit" value="insert">
	</td>
</tr>
</table>

</form>