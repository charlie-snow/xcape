<%
	xcaping.Lists list = new xcaping.Lists();
	java.util.Vector data = new java.util.Vector();
	data = list.getAvailabilities(item_id, contract_id);
	xcaping.Availability availability = new xcaping.Availability();
%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td colspan="3"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="3">
        <tr align="center">
			<td></td>
          <td class="h3orange">From</td>
          <td class="h3orange">To</td>
          <td class="h3orange">Free Sale</td>
          <td class="h3orange">Allocation</td>
          <td class="h3orange">Release</td>
          <td class="h3orange">M. Stay</td>
          <td class="h3orange">Unit Type</td>

          <td class="h3orange">Supplier</td>
          <td class="h3orange">Date</td>
          <td class="h3orange">Edit By</td>
		  <!-- <td></td> -->
        </tr>
        
	<%
		if (data.isEmpty()) {
	%>
		<tr>
			<td colspan="11" align="center">there are no availabilities</td>
		</tr>
	<%
	} else {
		String tr_class = "";
		boolean light = true;
		boolean active = false;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			availability = (xcaping.Availability)e.nextElement();
			supplier.get(availability.supplier);
			room.get(availability.roomNameID);
			boolean stop_sale = availability.minimumStay.equals("0");
			active = availability.active.equals("1");
			if (stop_sale) {
				tr_class="stop_sale_print";
			} else {
				if (light) {
					tr_class="light_print";
				} else {
					tr_class="dark_print";
				}
			}
			if (light) {
				light=false;
			} else {
				light=true;
			}
	%>

	<tr class="<%= tr_class %>">
          
	        <td>
			<% if (active) { %>
				ON
			<% } else { %>
				OFF
			<% } %>
			</td>	
			<td align="center"><%= availability.fromDate %></td>
			<td align="center" title="<%= availability.idColumn %>"><%= availability.toDate %></td>
			<td align="center"><%= availability.freeSale %></td>
			<td align="center"><%= availability.allocation %></td>
			<td align="center"><%= availability.releasePeriod %></td>
			<td align="center"><%= availability.minimumStay %></td>
			<td align="center"><%= room.name %></td>
			
			<td align="center"><%= supplier.name %></td>
			
			<td align="center"><%= availability.editDate %></td>
			<td align="center"><%= availability.editBy %></td>
        </tr>
		
		<% } } %>
        
      </table>
    </td>
  </tr>
</table>