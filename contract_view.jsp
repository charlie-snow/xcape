<%
	markup.get(contract.markup);
	currency.get(contract.currency);
	
	xcaping.AgentGroup agent_group = new xcaping.AgentGroup();
	agent_group.get(contract.agent_group);
%>

<table width="750" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td>CONTRACT: <%= contract.name %></td>
	<td>supplier: <%= supplier.name %></td>
	<td>from day: <%= contract.from_date %></td>
	<td>to day: <%= contract.to_date %></td>
</tr>
<tr>
	<td colspan="4" align="center">
		
	markup: <%= markup.markup %>
	
	free sale: 
	<% if (contract.free_sale.equals("1")) { %> yes <% } else { %> no <% } %>
	</td>
</tr>
<tr>
	<td colspan="4" align="center">
	cot suplement: <%= contract.cot_suplement %>
	infant age: <%= contract.infant_age %>
	child age: <%= contract.child_age %>	
	</td>
</tr>
<tr>
	<td colspan="4" align="center">
	
	currency: <%= currency.name %>
	sales period: <%= contract.sales_period %>
	commissionable: 
	<% if (contract.commissionable.equals("1")) { %> yes <% } else { %> no <% } %>
	agent group: <%= agent_group.group_name %>
	</td>
</tr>
<tr>
	<td colspan="4" align="center">
	
	<table width="400" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td align="center">
		arrival days:
		</td>
		<td>
		<% if (Integer.parseInt(contract.arrival_monday) == 1) { %> M <% } %>
		<% if (Integer.parseInt(contract.arrival_tuesday) == 1) { %> Tu <% } %>
		<% if (Integer.parseInt(contract.arrival_wednesday) == 1) { %> W <% } %>
		<% if (Integer.parseInt(contract.arrival_thursday) == 1) { %> Th <% } %>
		<% if (Integer.parseInt(contract.arrival_friday) == 1) { %> F <% } %>
		<% if (Integer.parseInt(contract.arrival_saturday) == 1) { %> Sa <% } %>
		<% if (Integer.parseInt(contract.arrival_sunday) == 1) { %> Su <% } %>
		</td>
	</tr>
	</table>
	
	</td>
</tr>
<tr>
	<td colspan="4" align="center">
	text: <%= contract.text %>
	</td>
</tr>
</table>