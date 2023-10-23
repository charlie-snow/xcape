<%
	java.util.Vector data_periods = new java.util.Vector();
	xcaping.Lists list_periods = new xcaping.Lists();
	java.util.Enumeration enum_data_periods;
	
	data_periods = list_periods.getPeriods(contract_id);
	xcaping.Period period = new xcaping.Period();
	
	java.util.Enumeration e2;
	java.util.Enumeration e3;
	
	boolean active = true;
	
	if (data_periods.size() == 0) {
		out.print("<div align='center'>There are no periods for this Contract</div>");
	} else {
%>

<table width="950" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td></td>
	<td colspan="2" align="center">Dates</td>
	<% if (board_bases_count != 0) {%>
	<td colspan="<%= board_bases_count %>" align="center">
	Board Bases</td>
	<% } if (discounts_count != 0) {%>
	<td colspan="<%= discounts_count %>" align="center">
	Discounts&nbsp;&nbsp;<img src="images/minus.gif" width="15" height="15" alt="" border="0"></td>
	<% } if (rooms_count != 0) {%>
	<td colspan="<%= (rooms_count*4)+2 %>" align="center">
	Supplements&nbsp;&nbsp;<img src="images/plus.gif" width="15" height="15" alt="" border="0"></td>
	<% } %>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td></td>
	<td colspan="2"></td>
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
	<td></td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td></td>
	<td>From</td>
	<td>To</td>
	
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
	<td align="center">active days</td>
	<td align="center">arrival days</td>
	<td>MS</td>
	<td></td>
	<td></td>
	<td></td>
</tr>

<%
	String price = "";
	String tr_class = "";
	boolean light = false;
	boolean is_offer = false;
	for (e2 = data_periods.elements() ; e2.hasMoreElements() ;) {
		period = (xcaping.Period)e2.nextElement();
		is_offer = period.offer.equals("1");
		xcaping.Prices prices = new xcaping.Prices();
		prices.setPeriod(period.id, contract_id);
		prices.getPrices();
		period.getURLs(contract_id);
		active = period.active.equals("1");
		
		// getting active days text
		String active_days = "";
		if (Integer.parseInt(period.active_monday) == 1 
			&& Integer.parseInt(period.active_tuesday) == 1 
			&& Integer.parseInt(period.active_wednesday) == 1 
			&& Integer.parseInt(period.active_thursday) == 1 
			&& Integer.parseInt(period.active_friday) == 1 
			&& Integer.parseInt(period.active_saturday) == 1 
			&& Integer.parseInt(period.active_sunday) == 1) {
			active_days = "all";
		} else {
			if (Integer.parseInt(period.active_monday) == 1) { active_days+="M"; }
			if (Integer.parseInt(period.active_tuesday) == 1) { active_days+=",Tu"; }
			if (Integer.parseInt(period.active_wednesday) == 1) { active_days+=",W"; }
			if (Integer.parseInt(period.active_thursday) == 1) { active_days+=",Th"; }
			if (Integer.parseInt(period.active_friday) == 1) { active_days+=",F"; }
			if (Integer.parseInt(period.active_saturday) == 1) { active_days+=",Sa"; }
			if (Integer.parseInt(period.active_sunday) == 1) { active_days+=",Su"; }
		}
		
		// getting arrival days text
		arrival_days = "";
		if (Integer.parseInt(period.arrival_monday) == 1 
			&& Integer.parseInt(period.arrival_tuesday) == 1 
			&& Integer.parseInt(period.arrival_wednesday) == 1 
			&& Integer.parseInt(period.arrival_thursday) == 1 
			&& Integer.parseInt(period.arrival_friday) == 1 
			&& Integer.parseInt(period.arrival_saturday) == 1 
			&& Integer.parseInt(period.arrival_sunday) == 1) {
			arrival_days = "all";
		} else {
			if (Integer.parseInt(period.arrival_monday) == 1) { arrival_days+="M"; }
			if (Integer.parseInt(period.arrival_tuesday) == 1) { arrival_days+=",Tu"; }
			if (Integer.parseInt(period.arrival_wednesday) == 1) { arrival_days+=",W"; }
			if (Integer.parseInt(period.arrival_thursday) == 1) { arrival_days+=",Th"; }
			if (Integer.parseInt(period.arrival_friday) == 1) { arrival_days+=",F"; }
			if (Integer.parseInt(period.arrival_saturday) == 1) { arrival_days+=",Sa"; }
			if (Integer.parseInt(period.arrival_sunday) == 1) { arrival_days+=",Su"; }
		}
		//-------------------------
		
		if (is_offer) {
			tr_class="offer";
		} else {
			if (light) {
				tr_class="light";
				light=false;
			} else {
				tr_class="dark_period";
				light=true;
			}
		}
		
		// identify the features, depending on the contract state and the period
		boolean show_edit = false;
		boolean show_delete = false;
		boolean show_offer = false;
		boolean activate = false;
		if (generated) {
			if (!is_offer) {
				if (!period.hasComplementaryOffer) {
					if (!period.hasComplementary) {
						show_edit = true;
						show_delete = true;
						show_offer = true;
						activate = true;
					} else {
						if (period.firstComplementary) {
							show_edit = true;
							show_delete = true;
							show_offer = true;
							activate = true;
						}
					}
				}
			} else { // if it's offer
				if (!period.hasComplementary) {
					show_edit = true;
					show_delete = true;
					activate = true;
				} else {
					if (period.firstComplementary) {
						show_delete = true;
						activate = true;
					}
				}
			}
		} else { // if not generated
			if (!period.hasComplementary) {
				show_edit = true;
				show_delete = true;
				activate = true;
			} else {
				if (period.firstComplementary) {
					show_edit = true;
					show_delete = true;
					activate = true;
				}
			}
		}
		show_edit = show_edit && session.getAttribute("period_edit").equals("true");
		show_delete = show_delete && session.getAttribute("period_edit").equals("true");
		show_offer = show_offer && session.getAttribute("offers").equals("true");
%>
<tr class="<%= tr_class %>">
	<td>
	<% 
	if (activate) { 
		if (show_edit) {
			if (active) { %>
				<a href="switchActivePeriod.jsp?period_id=<%= period.id %>&contract_id=<%= contract_id %>&value=0&generated=<%= generated %>&has_complementary=<%= period.hasComplementary %>">
				<img src="images/active.gif" width="10" height="10" alt="" border="0"></a>
			<% } else { %>
				<a href="switchActivePeriod.jsp?period_id=<%= period.id %>&contract_id=<%= contract_id %>&value=1&generated=<%= generated %>&has_complementary=<%= period.hasComplementary %>">
				<img src="images/inactive.gif" width="10" height="10" alt="" border="0"></a>
			<% } 
		} else {
			if (active) { %>
				<img src="images/active.gif" width="10" height="10" alt="" border="0">
			<% } else { %>
				<img src="images/inactive.gif" width="10" height="10" alt="" border="0">
			<% } 
		}
		
	} %>
	</td>
	<td align="center">
		<table width="45" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr><td align="center"><%= period.from_date %></td></tr>
		</table>
	</td>
	<td align="center" title="<%= period.id %>">
		<table width="45" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr><td align="center"><%= period.to_date %></td></tr>
		</table>
	</td>
		
	<%  
	for (e = prices.board_bases.elements() ; e.hasMoreElements() ;) {
		board_basisPeriod = (xcaping.BoardBasisPeriod)e.nextElement();
	%>
	<td align="center">
	<%= board_basisPeriod.price %> 
	</td>
	<% } %>
		
	<% 
	for (e = prices.discounts.elements() ; e.hasMoreElements() ;) {
		discountPeriod = (xcaping.DiscountPeriod)e.nextElement();
	%>
	<td align="center">
		<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><%= discountPeriod.amount %></td>
			<td align="right">
			<% if (discountPeriod.percentage.equals("1")) { 
			 	out.print("%");
			 } else { 
				out.print(currency.symbol);
			 } %>
			</td>
		</tr>
		</table>
	</td>
	<% } %>

	<% 
	e3 = prices.rooms.elements();
	for (e = data_rooms.elements(); e.hasMoreElements();) {
		room = (xcaping.Room)e.nextElement();
		roomPeriod = (xcaping.RoomPeriod)e3.nextElement();
	%>
	<td align="center">
		<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><%= roomPeriod.price %></td>
			<td align="right">
			<% if (roomPeriod.percentage.equals("1")) {
				out.print("%");
			 } else { 
				out.print(currency.symbol);
			 } %>
			</td>
		</tr>
		</table>
	</td>
	<td align="center">
	<% if (roomPeriod.unit.equals("1")) { %>
		U
	<% } else { %>
		P
	<% } %>
	</td>
	<td align="center">
	<%= roomPeriod.allotment %> 
	</td>
	<td align="center">
	<%= roomPeriod.release %>
	</td>
	<% } %>	
	
	<td align="center">
		<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><%= period.short_stay_suplement %></td>
			<td align="right">
			<% if (period.short_stay_percentage.equals("1")) {
				 out.print("%");
			 } else { 
				out.print(currency.symbol);
			 } %>
			</td>
		</tr>
		</table>
	
	
	</td>
	<td align="center"><%= active_days %></td>
	<td align="center"><%= arrival_days %></td>
	<td align="center"><%= period.minimum_stay %></td>

<% if (show_edit) { %>
	<td align="center"><a href="<%= period.editURL %>
	<% if (period.firstComplementary) { %>
		&first_complementary=1
	<% } %>
	">edit</a></td>
<% } else { %> <td></td> <% } %>
<% if (show_delete) { %>
	<td align="center"><a href="<%= period.deleteURL %>&complementary=<%= period.hasComplementary %>">delete</a></td>
<% } else { %> <td></td> <% } %>
<% if (show_offer) { %>
	<td align="center"><a href="<%= period.offerURL %>&first_complementary=1">offer</a></td>
<% } else { %> <td></td> <% } %>

</tr>

<% } %>

</table>
<% } %>