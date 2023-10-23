<%
	xcaping.Lists list_suppliers = new xcaping.Lists();
	java.util.Vector data_suppliers = new java.util.Vector();
	java.util.Enumeration enum_data_suppliers;
	data_suppliers = list_suppliers.getSuppliers();
	
	xcaping.Lists list_markups = new xcaping.Lists();
	java.util.Vector data_markups = new java.util.Vector();
	java.util.Enumeration enum_data_markups;
	data_markups = list_markups.getMarkups();
	
	xcaping.Lists list_currencies = new xcaping.Lists();
	java.util.Vector data_currencies = new java.util.Vector();
	java.util.Enumeration enum_data_currencies;
	data_currencies = list_currencies.getCurrencies();
	
	xcaping.Lists list_agent_groups = new xcaping.Lists();
	java.util.Vector data_agent_groups = new java.util.Vector();
	java.util.Enumeration enum_data_agent_groups;
	data_agent_groups = list_agent_groups.getAgents();
	xcaping.AgentGroup agent_group = new xcaping.AgentGroup();
	
	String checked = "";
%>

<form action="manageContract.jsp" method="post" name="modifyContract" id="modifyContract">
<input type="hidden" name="modify" id="modify" value="1">

<input type="hidden" name="contract_id" id="contract_id" value="<%= contract_id %>">
<input type="hidden" name="property" id="property" value="<%= contract.property %>">
<input type="hidden" name="edited_by" id="edited_by" value="<%= session.getAttribute("user") %>">
<table width="750" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td>CONTRACT: <input type="text" name="name" id="name" value="<%= contract.name %>" size="20"></td>
	<td>supplier:
	<select name="supplier" id="supplier" size="1">
		<%
		for (enum_data_suppliers = data_suppliers.elements() ; enum_data_suppliers.hasMoreElements() ;) {
			supplier = (xcaping.Supplier)enum_data_suppliers.nextElement();
		%>
		<option value="<%= supplier.id %>" <%  if (Integer.parseInt(contract.supplier) == Integer.parseInt(supplier.id)) { %>selected<% } %>><%= supplier.name %></option>
		<% } %>
	</select>
	</td>
	<td>from day:
	<% if (not_generated) { %>
	<input type="text" name="from_date" id="from_date" value="<%= contract.from_date %>" size="10">
	<% } else { %>
	<%= contract.from_date %>
	<input type="hidden" name="from_date" id="from_date" value="<%= contract.from_date %>">
	<% } %>
	</td>
	<td>to day:
	<% if (not_generated) { %>
	<input type="text" name="to_date" id="to_date" value="<%= contract.to_date %>" size="10">
	<% } else { %>
	<%= contract.to_date %>
	<input type="hidden" name="to_date" id="to_date" value="<%= contract.to_date %>">
	<% } %>
	</td>
</tr>
<tr>
	<td colspan="4" align="center">
		
	markup: 36%<input type="hidden" name="markup" id="markup" value="1">
	
	free sale: <input type="checkbox" name="free_sale" id="tactive_uesday" value="1"
	<% if (contract.free_sale.equals("1")) { %>
		 checked
	<% } %>
	>
	
	</td>
</tr>
<tr>
	<td colspan="4" align="center">
	
	cot suplement: <input type="text" name="cot_suplement" id="cot_suplement" value="<%= contract.cot_suplement %>"> 
	infant age: <input type="text" name="infant_age" id="infant_age" value="<%= contract.infant_age %>"> 
	child age: <input type="text" name="child_age" id="child_age" value="<%= contract.child_age %>"> 
	
	</td>
</tr>
<tr>
	<td colspan="4" align="center">
	
	currency: <select name="currency" id="currency" size="1">
		<%
		for (enum_data_currencies = data_currencies.elements(); enum_data_currencies.hasMoreElements();) {
			currency = (xcaping.Currency)enum_data_currencies.nextElement();
		%>
		<option value="<%= currency.id %>"<%  if (Integer.parseInt(contract.currency) == Integer.parseInt(currency.id)) { %>selected<% } %>
		><%= currency.name %></option>
		<% } %>
	</select>
	sales period: <input type="text" name="sales_period" id="sales_period" value="<%= contract.sales_period %>" size="3">
	commissionable: <input type="checkbox" name="commissionable" id="commissionable" value="1"
	<% if (contract.commissionable.equals("1")) { %>checked<% } %>>
	agent group: 
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
</tr>
<tr>
	<td colspan="4" align="center">
	
	<table width="400" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td align="center">
		arrival days:
		</td>
		<td>
		M<input type="checkbox" name="arrival_monday" id="arrival_monday" value="1"
		<% checked = (Integer.parseInt(contract.arrival_monday) == 1) ? "checked" : ""; %>
		<%=checked%>>
		Tu<input type="checkbox" name="arrival_tuesday" id="tarrival_uesday" value="1"
		<% checked = (Integer.parseInt(contract.arrival_tuesday) == 1) ? "checked" : ""; %>
		<%=checked%>>
		W<input type="checkbox" name="arrival_wednesday" id="arrival_wednesday" value="1"
		<% checked = (Integer.parseInt(contract.arrival_wednesday) == 1) ? "checked" : ""; %>
		<%=checked%>>
		Th<input type="checkbox" name="arrival_thursday" id="arrival_thursday" value="1"
		<% checked = (Integer.parseInt(contract.arrival_thursday) == 1) ? "checked" : ""; %>
		<%=checked%>>
		F<input type="checkbox" name="arrival_friday" id="arrival_friday" value="1"
		<% checked = (Integer.parseInt(contract.arrival_friday) == 1) ? "checked" : ""; %>
		<%=checked%>>
		Sa<input type="checkbox" name="arrival_saturday" id="arrival_saturday" value="1"
		<% checked = (Integer.parseInt(contract.arrival_saturday) == 1) ? "checked" : ""; %>
		<%=checked%>>
		Su<input type="checkbox" name="arrival_sunday" id="arrival_sunday" value="1"
		<% checked = (Integer.parseInt(contract.arrival_sunday) == 1) ? "checked" : ""; %>
		<%=checked%>>
		</td>
	</tr>
	</table>
	
	</td>
</tr>
<tr>
	<td colspan="4" align="center">
	texto: <input type="text" name="text" id="text" value="<%= contract.text %>" size="80">
	</td>
</tr>
</table>
<div align="center"><input type="submit" name="submit" id="submit" value="contract ok"></div>

</form>