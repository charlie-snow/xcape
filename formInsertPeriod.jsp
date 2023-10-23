<%
	String complementary_id = request.getParameter("complementary_id");
	String first_complementary = request.getParameter("first_complementary");
	
	boolean last_complementary = false;
	if (first_complementary != null) {
		last_complementary = first_complementary.equals("0");
	}
	
	String output;
	java.text.SimpleDateFormat formatter;
	java.util.Date today = new java.util.Date();
	formatter = new java.text.SimpleDateFormat("dd MM yy");
	output = formatter.format(today);
	
	boolean complementary = false;
	xcaping.Period period = new xcaping.Period();
	xcaping.Prices prices = new xcaping.Prices();
	if ((complementary_id == null) || (complementary_id.equals("0"))) {
		period = contract.getInsertPeriod();
		if (period.insertType == "complementary") {
			complementary = true;
			prices.setPeriod(period.id, contract.id);
			prices.getPrices();
		}
	} else {
		period.insertType = "complementary";
		complementary = true;
		period.get(complementary_id);
		period.compDays();
		prices.setPeriod(complementary_id, contract.id);
		prices.getPrices();
	}
	
	xcaping.Lists list_agent_groups = new xcaping.Lists();
	java.util.Vector data_agent_groups = new java.util.Vector();
	java.util.Enumeration enum_data_agent_groups;
	data_agent_groups = list_agent_groups.getAgents();
	xcaping.AgentGroup agent_group = new xcaping.AgentGroup();
	
	java.util.Enumeration e3;
	java.util.Enumeration enum_board_bases;
	java.util.Enumeration enum_discounts;
	java.util.Enumeration enum_rooms;
	java.util.Enumeration enum_rooms_prices;
	
	String id = "";
	String price = "";
	String percentage = "";
%>

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
	if (document.getElementById('short_stay_percentage').value == '1') {
		document.getElementById('short_stay_percentage').value = '0';
		document.getElementById('ss').innerHTML = '<strong><%= currency.symbol %></strong>';
	} else {
		document.getElementById('short_stay_percentage').value = '1';
		document.getElementById('ss').innerHTML = '<strong>%</strong>';
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
<input type="hidden" name="edited_by" id="edited_by" value="<%= session.getAttribute("user") %>">
<input type="hidden" name="edit_date" id="edit_date" value="<%= output %>">
<input type="hidden" name="offer" id="offer" value="0">
<input type="hidden" name="last_complementary" id="last_complementary" value="<%= last_complementary %>">
<input type="hidden" name="air_conditioned" id="air_conditioned" value="0">

<table width="900" border="0" cellspacing="0" cellpadding="5" align="center">
<tr>

	<% if (period.insertType == "new") { %>
	<td>From <%= period.from_date %></td>
	<input type="hidden" name="from_date" id="from_date" value="<%= period.from_date %>">
	<td>To <input type="text" name="to_date" id="to_date" size="8" value=""></td>
	<% } %>
	
	<% if (period.insertType == "next") { %>
	<td>From <input type="text" name="from_date" id="from_date" size="8" value="<%= period.from_date %>"></td>
	<td>To <input type="text" name="to_date" id="to_date" size="8" value=""></td>
	<% } %>
	
	<% if (complementary) { %>
	<td>From <%= period.from_date %></td>
	<input type="hidden" name="from_date" id="from_date" value="<%= period.from_date %>">
	<td>To <%= period.to_date %></td>
	<input type="hidden" name="to_date" id="to_date" value="<%= period.to_date %>">
	<% } %>
	<td>agent group
	<select name="agent_group" id="agent_group" size="1">
		<%
		for (enum_data_agent_groups = data_agent_groups.elements(); enum_data_agent_groups.hasMoreElements();) {
			agent_group = (xcaping.AgentGroup)enum_data_agent_groups.nextElement();
		%>
		<option value="<%= agent_group.group_id %>"
		<%  if (Integer.parseInt(contract.agent_group) == Integer.parseInt(agent_group.group_id)) { %>selected<% } %>>
		<%= agent_group.group_name %></option>
		<% } %>
	</select>
	</td>
	<td>Minimum Stay
	<% if (complementary) { %>
		<%= period.minimum_stay %>
		<input type="hidden" name="minimum_stay" id="minimum_stay" value="<%= period.minimum_stay %>">
	<% } else { %>
		<input type="text" name="minimum_stay" id="minimum_stay" size="4" value="">
	<% } %>
	</td>
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

<% 
	enum_board_bases = null;
	enum_discounts = null;
	enum_rooms = data_rooms.elements();
	enum_rooms_prices = null;
	if (complementary) {
		enum_board_bases = prices.board_bases.elements();
		enum_discounts = prices.discounts.elements();
		enum_rooms_prices = prices.rooms.elements();
		
		String active_days = "";
		if (Integer.parseInt(period.active_monday) == 1) { active_days+="M"; }
		if (Integer.parseInt(period.active_tuesday) == 1) { active_days+=", Tu"; }
		if (Integer.parseInt(period.active_wednesday) == 1) { active_days+=", W"; }
		if (Integer.parseInt(period.active_thursday) == 1) { active_days+=", Th"; }
		if (Integer.parseInt(period.active_friday) == 1) { active_days+=", F"; }
		if (Integer.parseInt(period.active_saturday) == 1) { active_days+=", Sa"; }
		if (Integer.parseInt(period.active_sunday) == 1) { active_days+=", Su"; }
	} else {
		enum_board_bases = data_board_bases.elements();
		enum_discounts = data_discounts.elements();
	}
 %>

<% 
	// board bases ____________________________________________________________________
	for (e = enum_board_bases ; e.hasMoreElements() ;) {
		if (complementary) { 
			board_basisPeriod = (xcaping.BoardBasisPeriod)e.nextElement();
			id = board_basisPeriod.board_basis_id;
			price = board_basisPeriod.price;
		} else { 
			board_basis = (xcaping.BoardBasis)e.nextElement();
			id = board_basis.id;
		}
	%>
	<td align="center">
	<input type="text" name="board_basis_price<%= id %>" id="board_basis_price<%= id %>" 
	<% if (complementary) { %> value="<%= price %>"<% } %> size="2">
	</td>
<% } %>

<% 
	// discounts ____________________________________________________________________
	for (e = enum_discounts ; e.hasMoreElements() ;) {
		if (complementary) { 
			discountPeriod = (xcaping.DiscountPeriod)e.nextElement();
			id = discountPeriod.discount_id;
			price = discountPeriod.amount;
			percentage = discountPeriod.percentage;
		} else { 
			discount = (xcaping.Discount)e.nextElement();
			id = discount.id;
		}
	%>
	<td align="center">
		<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><input type="text" name="discount_amount<%= id %>" id="discount_amount<%= id %>"
			<% if (complementary) { %> value="<%= price %>"<% } %> size="2">
			</td>
			<td align="right">
			<a href="#" onClick="changeDiscPercentage(<%= id %>)">
			<spam name="disc_<%= id %>" id="disc_<%= id %>">
			<strong>
			<% 
				if (complementary) {
					if (percentage.equals("1")) { out.print("%"); } else { out.print(currency.symbol); }
				} else { 
					 out.print("%");
				} 
			%>
			</strong></spam></a>
			<input type="hidden" name="discount_percentage<%= id %>" 
			id="discount_percentage<%= id %>" 
			value="<% if (complementary) { out.print(percentage); } else { out.print("1"); } %>">
			</td>
		</tr>
		</table>
	</td>
<% } %>

<%
	// rooms ____________________________________________________________________
	e3 = null;
	if (complementary) { e3 = enum_rooms_prices; }
	for (e = enum_rooms; e.hasMoreElements();) {
		room = (xcaping.Room)e.nextElement();
		id = room.id;
		if (complementary) { 
			roomPeriod = (xcaping.RoomPeriod)e3.nextElement();
			price = roomPeriod.price;
			
		}
	%>
	
	<td align="center">
		<input type="text" name="room_price<%= id %>" id="room_price<%= id %>"
		<% if (complementary) { %> value="<%= price %>"<% } %> size="2">
	</td>
	<td align="center">
	<a href="#" onClick="changePU(<%= id %>)">
	<spam name="pu_<%= id %>" id="pu_<%= id %>"><strong>P</strong></spam>
	</a>
	<input type="hidden" name="room_unit<%= id %>" id="room_unit<%= id %>"
	 value="<% if (complementary) { out.print(roomPeriod.unit); } else { out.print("0"); } %>">
	<input type="hidden" name="room_unit_changed<%= id %>" id="room_unit_changed<%= id %>" value="0">
	</td>
		<% if (complementary) { %>
			<td align="center">
			<%= roomPeriod.allotment %>
			<input type="hidden" name="room_allotment<%= id %>" id="room_allotment<%= id %>" value="<%= roomPeriod.allotment %>">
			</td>
			<td align="center"><%= roomPeriod.release %>
			<input type="hidden" name="room_release<%= id %>" id="room_release<%= id %>" value="<%= roomPeriod.release %>">
			</td>
		<% } else { %>
			<td align="center">
			<input type="text" name="room_allotment<%= id %>" id="room_allotment<%= id %>" size="2">
			</td>
			<td align="center">
			<input type="text" name="room_release<%= id %>" id="room_release<%= id %>" size="2">
			</td>
		<% } %>
<% } %>

<td align="center">
		<table border="0" cellspacing="0" cellpadding="0">
			<td><input type="text" name="short_stay_suplement" id="short_stay_suplement" size="2"
			<% if (complementary) { %> value="<%= period.short_stay_suplement %>"<% } %>"></td>
			<td align="right">
			<a href="#" onClick="changeSSpercentage()">
			<spam name="ss" id="ss"><strong>%</strong></spam></a>
			<input type="hidden" name="short_stay_percentage" id="short_stay_percentage" value="1">
			<% if (complementary) {
				if (period.short_stay_percentage.equals("0")) { %>
				 <script> changeSSpercentage(); </script>
			<% } } %>
			</td>
		</table>
	</td>	
</table>

<table width="900" border="0" cellspacing="0" cellpadding="5" align="center">
<tr>
	<td align="center">
	Active Days 
		
	<%if (period.insertType == "complementary") { 
		String active_days = "";
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

	<input type="hidden" name="agent_group" id="agent_group" value="<%= period.agent_group %>">
	
	<% } else { %>
	M<input type="checkbox" name="active_monday" id="active_monday" value="1" checked>
	Tu<input type="checkbox" name="active_tuesday" id="active_tuesday" value="1" checked>
	W<input type="checkbox" name="active_wednesday" id="active_wednesday" value="1" checked>
	Th<input type="checkbox" name="active_thursday" id="active_thursday" value="1" checked>
	F<input type="checkbox" name="active_friday" id="active_friday" value="1" checked>
	Sa<input type="checkbox" name="active_saturday" id="active_saturday" value="1" checked>
	Su<input type="checkbox" name="active_sunday" id="active_sunday" value="1" checked>
	<% } %>
		
	</td>
	<td align="center">
	Arrival Days 
	
	<% if (complementary) { %>
		M<input type="checkbox" name="arrival_monday" id="arrival_monday" value="1" 
		<% if (period.arrival_monday.equals("1")) { %>
			 checked
		<% } %>>
		Tu<input type="checkbox" name="arrival_tuesday" id="arrival_tuesday" value="1"
		<% if (period.arrival_tuesday.equals("1")) { %>
			 checked
		<% } %>>
		W<input type="checkbox" name="arrival_wednesday" id="arrival_wednesday" value="1"
		<% if (period.arrival_wednesday.equals("1")) { %>
			 checked
		<% } %>>
		Th<input type="checkbox" name="arrival_thursday" id="arrival_thursday" value="1"
		<% if (period.arrival_thursday.equals("1")) { %>
			 checked
		<% } %>>
		F<input type="checkbox" name="arrival_friday" id="arrival_friday" value="1"
		<% if (period.arrival_friday.equals("1")) { %>
			 checked
		<% } %>>
		Sa<input type="checkbox" name="arrival_saturday" id="arrival_saturday" value="1"
		<% if (period.arrival_saturday.equals("1")) { %>
			 checked
		<% } %>>
		Su<input type="checkbox" name="arrival_sunday" id="arrival_sunday" value="1"
		<% if (period.arrival_sunday.equals("1")) { %>
			 checked
		<% } %>>
	<% } else { %>
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
	<% } %>
	
	
	</td>
	<td align="right"><a href="#" onClick="abs()">help</a></td>
	<td align="right">
	<input type="submit" name="submit" id="submit" value="insert">
	</td>
</tr>
</table>

</form>

<script> document.getElementById('to_date').focus(); </script>