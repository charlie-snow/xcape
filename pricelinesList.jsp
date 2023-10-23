<%
	xcaping.Lists list = new xcaping.Lists();
	java.util.Vector data_pricelines = new java.util.Vector();
	boolean history;
	if (request.getParameter("history") != null) {
		history = request.getParameter("history").equals("1");
	} else {
		history = false;
	}
	data_pricelines = list.getPricelines(item_id, contract_id, history);
	xcaping.Priceline priceline = new xcaping.Priceline();	
	xcaping.RoomSupplement roomSupplement = new xcaping.RoomSupplement();	
	java.util.Enumeration roomsE;
	
	java.util.Vector data_rooms = new java.util.Vector();
	if (is_contract) {
		data_rooms = list.getRoomsContract(contract_id, true);
	} else {
		data_rooms = list.getRoomsProperty(item_id);
	}
%>

<table border="0" cellspacing="0" cellpadding="2" bordercolor="#CCCCCC">
<TR align="center">
	<td></td>
	<TD colspan="2" class="h2blue">	General	</TD>
	<TD class="h2blue">	%	</TD>
	<TD colspan="6" class="h2blue">	Board Basis	</TD>
	<TD colspan="5" class="h2blue">	Discounts	</TD>
	<TD class="h2blue">	Active	</TD>
	<TD class="h2blue">	Arrival	</TD>
	<td rowspan="2" class="p4orange" valign="bottom">	S<br>l<BR>P	</td>

	<td rowspan="2" class="p4orange" valign="bottom">	M<BR>S	</td>
	<td rowspan="2" class="p4orange" valign="bottom">	C<BR>h<BR>A	</td>

	<td rowspan="2" class="p4orange" valign="bottom">	N/<BR>C	</td>
	<td rowspan="2" class="p4orange" valign="bottom">	S<BR>p<BR>p	</td>

	<td rowspan="2" class="p4orange" valign="bottom">	G<BR>ID	</td>
	<td rowspan="2" class="p4orange" valign="bottom">	Supplier	</td>
	<td rowspan="2" class="h3orange" valign="bottom">	$	</td>
	<% for (roomsE = data_rooms.elements() ; roomsE.hasMoreElements() ;) {
		room = (xcaping.Room)roomsE.nextElement(); %>
		<td colspan="2" valign="bottom"><%= room.name %></td>
		<td></td>
	<% } %>
	<td></td>
</TR>
<TR align="center" valign="bottom">
	<td></td>
	<TD class="h3orange">	From </TD>
	<TD class="h3orange">	To	</TD>
	<TD class="h3orange">	Profit	</TD>
	<TD class="h3orange">	SC	</TD>
	<TD class="h3orange">	RO	</TD>
	<TD class="h3orange">	BB	</TD>
	<TD class="h3orange">	HB	</TD>
	<TD class="h3orange">	FB	</TD>
	<TD class="h3orange">	AI	</TD>
	<TD class="h3orange">	1C	</TD>
	<TD class="h3orange">	2C	</TD>
	<TD class="h3orange">	3C	</TD>
	<TD class="h3orange">	3A	</TD>
	<TD class="h3orange">	4A	</TD>
	<TD class="hA3blue">active</TD>
	<TD class="hA3blue">arrival</TD>
	<% for (int i=0; i<data_rooms.size(); i++) { %>
		<td align="center" class="h3orange">p/u</td>
		<td align="center" class="h3orange">price</td>
		<td>|</td>
	<% } %>
	<td class="p4orange">editor</td>
</TR>
		
		
		
	<% 
	String tr_class = "";
	int section = 1;
	boolean change = true;
	boolean light = true;
	boolean active = false;
	for (e = data_pricelines.elements() ; e.hasMoreElements() ;) {
		priceline = (xcaping.Priceline)e.nextElement();
		currency.get(priceline.exchangeID);
		supplier.get(priceline.supplierID);
		active = priceline.active.equals("1");
		
		// getting active days text
		String active_days = "";
		if (Integer.parseInt(priceline.activeMonday) == 1 
			&& Integer.parseInt(priceline.activeTuesday) == 1 
			&& Integer.parseInt(priceline.activeWednesday) == 1 
			&& Integer.parseInt(priceline.activeThursday) == 1 
			&& Integer.parseInt(priceline.activeFriday) == 1 
			&& Integer.parseInt(priceline.activeSaturday) == 1 
			&& Integer.parseInt(priceline.activeSunday) == 1) {
			active_days = "all";
		} else {
			if (Integer.parseInt(priceline.activeMonday) == 1) { active_days+="M"; }
			if (Integer.parseInt(priceline.activeTuesday) == 1) { active_days+=",Tu"; }
			if (Integer.parseInt(priceline.activeWednesday) == 1) { active_days+=",W"; }
			if (Integer.parseInt(priceline.activeThursday) == 1) { active_days+=",Th"; }
			if (Integer.parseInt(priceline.activeFriday) == 1) { active_days+=",F"; }
			if (Integer.parseInt(priceline.activeSaturday) == 1) { active_days+=",Sa"; }
			if (Integer.parseInt(priceline.activeSunday) == 1) { active_days+=",Su"; }
		}
		
		// getting arrival days text
		String arrival_days = "";
		if (Integer.parseInt(priceline.arrivalMonday) == 1 
			&& Integer.parseInt(priceline.arrivalTuesday) == 1 
			&& Integer.parseInt(priceline.arrivalWednesday) == 1 
			&& Integer.parseInt(priceline.arrivalThursday) == 1 
			&& Integer.parseInt(priceline.arrivalFriday) == 1 
			&& Integer.parseInt(priceline.arrivalSaturday) == 1 
			&& Integer.parseInt(priceline.arrivalSunday) == 1) {
			arrival_days = "all";
		} else {
			if (Integer.parseInt(priceline.arrivalMonday) == 1) { arrival_days+="M"; }
			if (Integer.parseInt(priceline.arrivalTuesday) == 1) { arrival_days+=",Tu"; }
			if (Integer.parseInt(priceline.arrivalWednesday) == 1) { arrival_days+=",W"; }
			if (Integer.parseInt(priceline.arrivalThursday) == 1) { arrival_days+=",Th"; }
			if (Integer.parseInt(priceline.arrivalFriday) == 1) { arrival_days+=",F"; }
			if (Integer.parseInt(priceline.arrivalSaturday) == 1) { arrival_days+=",Sa"; }
			if (Integer.parseInt(priceline.arrivalSunday) == 1) { arrival_days+=",Su"; }
		}
		
		boolean offer = priceline.priceType.equals("3");
		boolean supplement = priceline.priceType.equals("2");
		if (offer) {
			tr_class="offer";
		} else {
			if (supplement) {
				tr_class="supplement";
			} else {
				if (light) {
					tr_class="light";
					light=false;
				} else {
					tr_class="dark_period";
					light=true;
				}
			}
		}
		
		// sections
		if (offer) {
			if (section != 6) {
				change = true;
			}
			section = 6;
		} else {
			if (supplement) {
				switch (Integer.parseInt(priceline.supplement)) {
					case 40: 
						if (section != 2) {
							change = true;
						}
						section = 2;
						break;
					case 48: 
						if (section != 3) {
							change = true;
						}
						section = 3;
						break;
					case 49: 
						if (section != 4) {
							change = true;
						}
						section = 4;
						break;
					case 51: 
						if (section != 5) {
							change = true;
						}
						section = 5;
						break;
				}
			} else {
				if (section != 1) {
					change = true;
				}
				section = 1;
			}
		}
	%>

<% if (change) { %>
<tr>
	<td colspan="<%= 26+(data_rooms.size()*3) %>">
	<%
	switch (section) {
		case 1: 
			out.print("net rates ____________________________________________________________________________________________________");
			break;
		case 2: 
			out.print("short stay supplement ____________________________________________________________________________________________________");
			break;
		case 3: 
			out.print("gala dinner ____________________________________________________________________________________________________");
			break;
		case 4: 
			out.print("add-on ____________________________________________________________________________________________________");
			break;
		case 5: 
			out.print("baby cot ____________________________________________________________________________________________________");
			break;
		case 6: 
			out.print("offers ____________________________________________________________________________________________________");
			break;
	}
	%>
	</td>
</tr>
<% change = false; } %>
<tr class="<%= tr_class %>">
	<td>
	<% if (active) { %>
		<img src="images/active.gif" width="10" height="10" alt="" border="0">
	<% } else { %>
		<img src="images/inactive.gif" width="10" height="10" alt="" border="0">
	<% } %>
	</td>	
	<TD class="p4black" height="20">	<%= priceline.fromDate %> </TD>
	<TD class="p4black" height="20">	<%= priceline.toDate %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.markup %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.selfCatering %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.roomOnly %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.bedBreakfast %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.halfBoard %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.fullBoard %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.allInclusive %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.disc1stChild %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.disc2ndChild %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.disc3rdChild %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.disc3rdAdult %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.disc4thAdult %>	</TD>
	
	<td align="center"><%= active_days %></td>
	
	<td align="center"><%= arrival_days %></td>

	<TD class="p4black" height="20">	<%= priceline.salesPeriod %> </TD>
	<TD class="p4black" height="20">	<%= priceline.minStayTrig %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.childAge %>	</TD>
	<TD class="tickRed" height="20">
	<% if (priceline.commissionable.equals("1")) { %>	i <% } else {%>	a <% } %>
	</TD>
	<TD class="p4black" height="20">	<%= priceline.supplement %>	</TD>
	<TD class="p4black" height="20">	<%= priceline.agentGroup %>		</TD>
	<td height="20" align="center" class="p4black">	<%= supplier.id %>	</td>
	<TD class="p4black" height="20">	<%= currency.symbol %>	</TD>
	<% for (roomsE = priceline.rooms.elements() ; roomsE.hasMoreElements() ;) {
			roomSupplement = (xcaping.RoomSupplement)roomsE.nextElement(); %>
			<td align="center" class="p4black">
			<% if (roomSupplement.pricePerUnitPerPerson.equals("1")) { 
			 	out.print("U");
			 } else { 
				out.print("P");
			 } %>
			 </td>
			<td align="center" class="p4black"><%= roomSupplement.RoomPrice %></td>
			<td>|</td>
	<% } %>
	<td><%= priceline.editBy %></td>
</TR>
		
	<% } %>

</table>
