<%
	xcaping.Lists list = new xcaping.Lists();
	xcaping.Contract contract = new xcaping.Contract();
	java.util.Vector data = new java.util.Vector();
	data = list.getContracts(item_id);
	
	xcaping.Supplier supplier = new xcaping.Supplier();
	
	xcaping.User user = new xcaping.User();
%>

<script language="JavaScript">
function confirmDelete(contract_id, item_id){
	if (confirm("WARNING: if you delete this contract, all the information (periods, prices, galas,...) will be lost ")) {
		window.location = "deleteContract.jsp?contract_id="+contract_id+"&item_id="+item_id;
	}
}
</script>

<table width="850" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">CONTRACT</td>
	<td align="center">FROM</td>
	<td align="center">TO</td>
	<td align="center">SUPPLIER</td>
	<td align="center">edited by</td>
	<td></td>
	<td></td>
</tr>

<%
	java.util.Enumeration e;
	String tr_class = "";
	for (e = data.elements() ; e.hasMoreElements() ;) {
		contract = (xcaping.Contract)e.nextElement();
		
		supplier.get(contract.supplier);
		boolean generated = contract.state.equals("active") || contract.state.equals("inactive");
		boolean finished = contract.state.equals("finished");
		boolean active = contract.state.equals("active");
		if (generated) {
			if (active) {
				tr_class="active";
			} else {
				tr_class="inactive";
			}
		} else {
			if (finished) {
				tr_class="finished";
			} else {
				tr_class="not_generated";
			}
		}
%>

<tr class="<%= tr_class %>">
	
	<td align="center">
	<% if (session.getAttribute("contract_edit").equals("true")) { %>
		<a href="index.jsp?content=contract&contract_id=<%= contract.id %>" class="blue"><%= contract.name %></a>
	<% } else { %>
		<%= contract.name %>
	<% } %>
	</td>
	<td align="center"><%= contract.from_date %></td>
	<td align="center" title="<%= contract.id %>"><%= contract.to_date %></td>
	<td align="center"><%= supplier.name %></td>
	<td align="center"><%= contract.edited_by %></td>
	<td align="center">
	
	<% if (generated && !finished && session.getAttribute("activate_contract").equals("true")) { %>
		<a href="switchActiveContract.jsp?contract_id=<%= contract.id %>&item_id=<%= item_id %>
		<% if (active) { %>&value=inactive">de<% } else { %>&value=active"><% } %>
		activate</a>
	<% } %>
	
	</td>	
	<td align="center">
	
	<% if (session.getAttribute("type").equals("administrator")) { %>
		<a href="#" onClick="confirmDelete(<%= contract.id %>, <%= contract.property %>)">DELETE</a>
	<% } else { %>
		<% if (!contract.state.equals("finished") && (session.getAttribute("delete_contract").equals("true"))) { %>
			<a href="#" onClick="confirmDelete(<%= contract.id %>, <%= contract.property %>)">DELETE</a>
		<% } %>
	<% } %>
	</td>
</tr>

<%}%>

</table>             