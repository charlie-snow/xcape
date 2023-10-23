<%
	String period_id = request.getParameter("period_id");
	String contract_id = request.getParameter("contract_id");
	
	String complementary_id = request.getParameter("complementary_id");
	String first_complementary = request.getParameter("first_complementary");
	
	boolean last_complementary = false;
	boolean is_first_complementary = false;
	if (first_complementary != null) {
		if (first_complementary.equals("0")) {
			last_complementary = true;
		} else {
			is_first_complementary = true;
		}
	}
	
	String output;
	java.text.SimpleDateFormat formatter;
	java.util.Date today = new java.util.Date();
	formatter = new java.text.SimpleDateFormat("dd MM yy");
	output = formatter.format(today);
	
	xcaping.Period period = new xcaping.Period();
	period.get(period_id);
	boolean offer = period.offer.equals("1");
	
	// get the prices
	xcaping.Prices prices = new xcaping.Prices();
	prices.setPeriod(period.id, period.contract);
	prices.getPrices();
	
	boolean complementary = false;
	xcaping.Period comp_period = new xcaping.Period();
	xcaping.Prices comp_prices = new xcaping.Prices();
	if ((complementary_id != null) && (!complementary_id.equals("0"))) {
		complementary = true;
		comp_period.get(complementary_id);
		comp_period.compDays();
		comp_prices.setPeriod(comp_period.id, comp_period.contract);
		comp_prices.getPrices();
	}
		// rooms
	
	java.util.Vector data_rooms = new java.util.Vector();
	xcaping.Lists list_rooms = new xcaping.Lists();
	
	data_rooms = list_rooms.getRoomsContract(contract_id, true);
	int rooms_count = data_rooms.size();
	xcaping.RoomPeriod roomPeriod = new xcaping.RoomPeriod();
	xcaping.RoomPeriod comp_roomPeriod = new xcaping.RoomPeriod();
	xcaping.Room room = new xcaping.Room();
	
		// discounts of the contract
	
	java.util.Vector data_discounts = new java.util.Vector();
	xcaping.Lists list_discounts = new xcaping.Lists();
	
	data_discounts = list_discounts.getDiscountsContract(contract_id);
	int discounts_count = data_discounts.size();
	xcaping.Discount discount = new xcaping.Discount();
	xcaping.DiscountPeriod discountPeriod = new xcaping.DiscountPeriod();
	
		// board bases of the contract
	
	java.util.Vector data_board_bases = new java.util.Vector();
	xcaping.Lists list_board_bases = new xcaping.Lists();
	
	data_board_bases = list_board_bases.getBoardBasesContract(contract_id);
	int board_bases_count = data_board_bases.size();
	xcaping.BoardBasis board_basis = new xcaping.BoardBasis();
	xcaping.BoardBasisPeriod board_basisPeriod = new xcaping.BoardBasisPeriod();
	
	xcaping.Contract contract = new xcaping.Contract();
	contract.get(contract_id);
	xcaping.Currency currency = new xcaping.Currency();
	currency.get(contract.currency);
	
	xcaping.Lists list_agent_groups = new xcaping.Lists();
	java.util.Vector data_agent_groups = new java.util.Vector();
	java.util.Enumeration enum_data_agent_groups;
	data_agent_groups = list_agent_groups.getAgents();
	xcaping.AgentGroup agent_group = new xcaping.AgentGroup();
	
	java.util.Enumeration e;
	java.util.Enumeration e2;
	java.util.Enumeration e3;
	
	String id = "";
	String price = "";
	String percentage = "";
%>

<script>

function next_period() {
	document.getElementById("next").value="1";
}

function send() {
	document.getElementById("modifyPeriod").submit();
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

<form action="modifyPeriod.jsp" method="post" name="modifyPeriod" id="modifyPeriod">
<input type="hidden" name="contract_id" id="contract_id" value="<%= period.contract %>">
<input type="hidden" name="period_id" id="period_id" value="<%= period_id %>">
<input type="hidden" name="edited_by" id="edited_by" value="<%= session.getAttribute("user") %>">
<input type="hidden" name="edit_date" id="edit_date" value="<%= output %>">
<input type="hidden" name="offer" id="offer" value="<%= period.offer %>">
<input type="hidden" name="active" id="active" value="<%= period.active %>">
<input type="hidden" name="last_complementary" id="last_complementary" value="<%= last_complementary %>">

<table width="900" border="0" cellspacing="0" cellpadding="5" align="center">
<tr>
	<% if (period.offer.equals("1")) { %>
		<td>From <%= period.from_date %></td>
		<input type="hidden" name="from_date" id="from_date" value="<%= period.from_date %>">
		<td>To <%= period.to_date %></td>
		<input type="hidden" name="to_date" id="to_date" value="<%= period.to_date %>">
	<% } else { %>
		<% if (complementary) { %>
			<td>From <%= comp_period.from_date %></td>
			<input type="hidden" name="from_date" id="from_date" value="<%= comp_period.from_date %>">
			<td>To <%= comp_period.to_date %></td>
			<input type="hidden" name="to_date" id="to_date" value="<%= comp_period.to_date %>">
		<% } else { %>
			<% if (is_first_complementary) { %>
				<td>From <%= period.from_date %></td>
				<input type="hidden" name="from_date" id="from_date" value="<%= period.from_date %>">
				<td>To <%= period.to_date %></td>
				<input type="hidden" name="to_date" id="to_date" value="<%= period.to_date %>">
			<% } else { %>
				<td align="center">
				From: <input type="text" name="from_date" id="from_date" value="<%= period.from_date %>" size="10">
				</td>
				<td>
				To: <input type="text" name="to_date" id="to_date" value="<%= period.to_date %>" size="10">
				</td>
			<% } %>
		<% } %>
	<% } %>
	<td>
	agent group: 
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
	edited by: <%= period.edited_by %>
	</td>
	<td align="center">
	Minimum stay 
	<% if (complementary) { %>
		<%= comp_period.minimum_stay %>
		<input type="hidden" name="minimum_stay" id="minimum_stay" value="<%= comp_period.minimum_stay %>">
	<% } else { %>
		<input type="text" name="minimum_stay" id="minimum_stay" size="4" value="<%= period.minimum_stay %>">
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
	<td colspan="<%= (rooms_count*4)+2 %>" align="center" class="highlight">
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
		
	<td colspan="2"></td>
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
	for (e = prices.board_bases.elements() ; e.hasMoreElements() ;) {
		board_basisPeriod = (xcaping.BoardBasisPeriod)e.nextElement();
	%>
	<td align="center">
	<input type="text" name="board_basis_price<%= board_basisPeriod.board_basis_id %>" id="board_basis_price<%= board_basisPeriod.board_basis_id %>" value="<%= board_basisPeriod.price %>" size="2">
	</td>
	<% } %>
		
	<% 
	for (e = prices.discounts.elements() ; e.hasMoreElements() ;) {
		discountPeriod = (xcaping.DiscountPeriod)e.nextElement();
		id = discountPeriod.discount_id;
		price = discountPeriod.amount;
		percentage = discountPeriod.percentage;
	%>
	<td align="center">
	
		<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><input type="text" name="discount_amount<%= id %>" id="discount_amount<%= id %>" value="<%= price %>" size="2">
			</td>
			<td align="right">
			<a href="#" onClick="changeDiscPercentage(<%= id %>)">
			<spam name="disc_<%= id %>" id="disc_<%= id %>">
			<strong>
			<% if (percentage.equals("1")) { out.print("%"); } else { out.print(currency.symbol); }%>
			</strong></spam></a>
			<input type="hidden" name="discount_percentage<%= id %>" 
			id="discount_percentage<%= id %>" value="<%= percentage %>">
			</td>
		</tr>
		</table>
	
	</td>
	<% } %>

	<%
	e2 = prices.rooms.elements();
	e3 = null;
	if (complementary) { e3 = comp_prices.rooms.elements();	}
	for (e = data_rooms.elements(); e.hasMoreElements();) {
		room = (xcaping.Room)e.nextElement();
		roomPeriod = (xcaping.RoomPeriod)e2.nextElement();
		if (complementary) { comp_roomPeriod = (xcaping.RoomPeriod)e3.nextElement(); }
		id = room.id;
		price = roomPeriod.price;
	%>
	
	<td align="center">
	
		<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><input type="text" name="room_price<%= room.id %>" id="room_price<%= room.id %>" 
			value="<%= roomPeriod.price %>" size="2"></td>
		</tr>
		</table>
		
	</td>
	<td align="center">
	<a href="#" onClick="changePU(<%= id %>)">
	<spam name="pu_<%= id %>" id="pu_<%= id %>"><strong>P</strong></spam>
	</a>
	<input type="hidden" name="room_unit<%= id %>" id="room_unit<%= id %>" value="<%= roomPeriod.unit %>">
	<input type="hidden" name="room_unit_changed<%= id %>" id="room_unit_changed<%= id %>" value="0">
	<% if (roomPeriod.unit.equals("1")) { %>
		 <script> changePU(<%= room.id %>); </script>
	<% } %>
	</td>
		<% if (complementary) { %>
			<td align="center">
			<%= comp_roomPeriod.allotment %>
			<input type="hidden" name="room_allotment<%= room.id %>" id="room_allotment<%= room.id %>" value="<%= comp_roomPeriod.allotment %>">
			</td>
			<td align="center">
			<%= comp_roomPeriod.release %>
			<input type="hidden" name="room_release<%= room.id %>" id="room_release<%= room.id %>" value="<%= comp_roomPeriod.release %>">
			</td>
		<% } else { %>
			<td align="center">
			<input type="text" name="room_allotment<%= room.id %>" id="room_allotment<%= room.id %>" value="<%= roomPeriod.allotment %>" size="2">
			</td>
			<td align="center">
			<input type="text" name="room_release<%= room.id %>" id="room_release<%= room.id %>" value="<%= roomPeriod.release %>" size="2">
			</td>
		<% } %>
	<% }%>
	
	<td align="center">
	<table border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><input type="text" name="short_stay_suplement" id="short_stay_suplement" size="2" value="<%= period.short_stay_suplement %>"></td>
		<td align="right">
		<a href="#" onClick="changeSSpercentage()">
		<spam name="ss" id="ss"><strong>%</strong></spam></a>
		<input type="hidden" name="short_stay_percentage" id="short_stay_percentage" value="1">
		<% if (complementary) {
			if (period.short_stay_percentage.equals("0")) { %>
			<script> changeSSpercentage(); </script>
		<% } } %>
		</td>
	</tr>
	</table>
	</td>
</tr>

</table>

<table width="900" border="0" cellspacing="0" cellpadding="5" align="center">
<tr>
	<td align="center">
	Active Days 
	
	<% if (!offer) { 	
		if  (complementary) { 
			String active_days = "";
			if (Integer.parseInt(comp_period.active_monday) == 1) { active_days+="M"; }
			if (Integer.parseInt(comp_period.active_tuesday) == 1) { active_days+=", Tu"; }
			if (Integer.parseInt(comp_period.active_wednesday) == 1) { active_days+=", W"; }
			if (Integer.parseInt(comp_period.active_thursday) == 1) { active_days+=", Th"; }
			if (Integer.parseInt(comp_period.active_friday) == 1) { active_days+=", F"; }
			if (Integer.parseInt(comp_period.active_saturday) == 1) { active_days+=", Sa"; }
			if (Integer.parseInt(comp_period.active_sunday) == 1) { active_days+=", Su"; }
		%>
		
		<%= active_days %>
		
		<input type="hidden" name="active_monday" id="active_monday" value="<%= comp_period.active_monday %>">	
		<input type="hidden" name="active_tuesday" id="active_tuesday" value="<%= comp_period.active_tuesday %>">
		<input type="hidden" name="active_wednesday" id="active_wednesday" value="<%= comp_period.active_wednesday %>">
		<input type="hidden" name="active_thursday" id="active_thursday" value="<%= comp_period.active_thursday %>">
		<input type="hidden" name="active_friday" id="active_friday" value="<%= comp_period.active_friday %>">
		<input type="hidden" name="active_saturday" id="active_saturday" value="<%= comp_period.active_saturday %>">
		<input type="hidden" name="active_sunday" id="active_sunday" value="<%= comp_period.active_sunday %>">
	
		
		<% } else { %>
		M<input type="checkbox" name="active_monday" id="active_monday" value="1" 
		<% if (period.active_monday.equals("1")) { %>
			 checked
		<% } %>>
		Tu<input type="checkbox" name="active_tuesday" id="active_tuesday" value="1"
		<% if (period.active_tuesday.equals("1")) { %>
			 checked
		<% } %>>
		W<input type="checkbox" name="active_wednesday" id="active_wednesday" value="1"
		<% if (period.active_wednesday.equals("1")) { %>
			 checked
		<% } %>>
		Th<input type="checkbox" name="active_thursday" id="active_thursday" value="1"
		<% if (period.active_thursday.equals("1")) { %>
			 checked
		<% } %>>
		F<input type="checkbox" name="active_friday" id="active_friday" value="1"
		<% if (period.active_friday.equals("1")) { %>
			 checked
		<% } %>>
		Sa<input type="checkbox" name="active_saturday" id="active_saturday" value="1"
		<% if (period.active_saturday.equals("1")) { %>
			 checked
		<% } %>>
		Su<input type="checkbox" name="active_sunday" id="active_sunday" value="1"
		<% if (period.active_sunday.equals("1")) { %>
			 checked
		<% } %>>
		<% } } else { %>
			all
			<input type="hidden" name="active_monday" id="active_monday" value="1">	
			<input type="hidden" name="active_tuesday" id="active_tuesday" value="1">
			<input type="hidden" name="active_wednesday" id="active_wednesday" value="1">
			<input type="hidden" name="active_thursday" id="active_thursday" value="1">
			<input type="hidden" name="active_friday" id="active_friday" value="1">
			<input type="hidden" name="active_saturday" id="active_saturday" value="1">
			<input type="hidden" name="active_sunday" id="active_sunday" value="1">
		<% } %>
		
	</td>
<td align="center">
	Arrival Days 
	
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
	</td>
	<td align="right">
	<input type="hidden" name="next" id="next" value="0">
	
	<a href="#" class="blue" onClick="send()">ok</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" class="blue" onClick="next_period(); send();">ok and next...</a>
	</td>
</tr>
</table>

</form>

<script language="JavaScript1.2">
document.getElementById('to_date').focus();

document.onkeydown = enterSubmit;

function enterSubmit(event) {	
	if (document.all) {
		if(window.event.keyCode == 13) {
			next_period(); // pressing enter goes to next period
			send();
		}
	}
	if (document.getElementById) {
		if(event.which == 13) {
			next_period(); // pressing enter goes to next period
			send();
		}
	}
}
</script>