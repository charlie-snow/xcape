<%
	xcaping.Lists list_suppliers = new xcaping.Lists();
	java.util.Vector data_suppliers = new java.util.Vector();
	java.util.Enumeration enum_data_suppliers;
	data_suppliers = list_suppliers.getSuppliers();
	
	xcaping.Lists list_markups = new xcaping.Lists();
	java.util.Vector data_markups = new java.util.Vector();
	java.util.Enumeration enum_data_markups;
	data_markups = list_markups.getMarkups();
	xcaping.Markup markup = new xcaping.Markup();
	
	xcaping.Lists list_currencies = new xcaping.Lists();
	java.util.Vector data_currencies = new java.util.Vector();
	java.util.Enumeration enum_data_currencies;
	data_currencies = list_currencies.getCurrencies();
	xcaping.Currency currency = new xcaping.Currency();
	
	xcaping.Lists list_agent_groups = new xcaping.Lists();
	java.util.Vector data_agent_groups = new java.util.Vector();
	java.util.Enumeration enum_data_agent_groups;
	data_agent_groups = list_agent_groups.getAgents();
	xcaping.AgentGroup agent_group = new xcaping.AgentGroup();
%>

<form action="manageContract.jsp" method="post" name="insertContract" id="insertContract">
<input type="hidden" name="modify" id="modify" value="0">

<input type="hidden" name="property" id="property" value="<%= item_id %>">
<input type="hidden" name="edited_by" id="edited_by" value="<%= session.getAttribute("user") %>">
<table width="850" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td>Contract Name <input type="text" name="name" id="name" size="22"></td>
	<td>Supplier
	<select name="supplier" id="supplier" size="1">
		<%
		for (enum_data_suppliers = data_suppliers.elements() ; enum_data_suppliers.hasMoreElements() ;) {
			supplier = (xcaping.Supplier)enum_data_suppliers.nextElement();
		%>
		<option value="<%= supplier.id %>" ><%= supplier.name %></option>
		<% } %>
	</select>
	</td>
	<td>From Day <input type="text" name="from_date" id="from_date" size="10"></td>
	<td>To Day <input type="text" name="to_date" id="to_date" size="10"></td>
</tr>
</table>
<table width="600" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td align="center">Child Age <input type="text" name="child_age" id="child_age" size="3"></td>
	<td align="center">Infant Age <input type="text" name="infant_age" id="infant_age" size="3"> </td>
	<td colspan="2" align="center">Cot Suplement <input type="text" name="cot_suplement" id="cot_suplement" size="5"></td>
</tr>
<tr>
	<td rowspan="2" align="center" valign="middle">
	markup: 36%<input type="hidden" name="markup" id="markup" value="1">
	</td>
	<td valign="bottom">Currency</td>
	<td valign="bottom">Sales Period</td>
	<td valign="bottom">Agent Group</td>
</tr>
	<td>
	<select name="currency" id="currency" size="1">
		<%
		for (enum_data_currencies = data_currencies.elements(); enum_data_currencies.hasMoreElements();) {
			currency = (xcaping.Currency)enum_data_currencies.nextElement();
		%>
		<option value="<%= currency.id %>" ><%= currency.name %></option>
		<% } %>
	</select>
	</td>
	<td><input type="text" name="sales_period" id="sales_period" value="0" size="5"></td>
	<td>
	<select name="agent_group" id="agent_group" size="1">
		<%
		for (enum_data_agent_groups = data_agent_groups.elements(); enum_data_agent_groups.hasMoreElements();) {
			agent_group = (xcaping.AgentGroup)enum_data_agent_groups.nextElement();
		%>
		<option value="<%= agent_group.group_id %>" ><%= agent_group.group_name %></option>
		<% } %>
	</select>
	</td>
</tr>
<tr>
	<td colspan="5" align="center">
	Arrival Days 
	
	M<input type="checkbox" name="arrival_monday" id="arrival_monday" value="1" checked>
	Tu<input type="checkbox" name="arrival_tuesday" id="tarrival_uesday" value="1" checked>
	W<input type="checkbox" name="arrival_wednesday" id="arrival_wednesday" value="1" checked>
	Th<input type="checkbox" name="arrival_thursday" id="arrival_thursday" value="1" checked>
	F<input type="checkbox" name="arrival_friday" id="arrival_friday" value="1" checked>
	Sa<input type="checkbox" name="arrival_saturday" id="arrival_saturday" value="1" checked>
	Su<input type="checkbox" name="arrival_sunday" id="arrival_sunday" value="1" checked>
	&nbsp;&nbsp;&nbsp;
	Free Sale <input type="checkbox" name="free_sale" id="free_sale" value="1" checked>
	&nbsp;&nbsp;&nbsp;
	Commissionable <input type="checkbox" name="commissionable" id="commissionable" value="">
	</td>
</tr>
<tr>
	<td colspan="5" align="center">Comments 
	<textarea cols="100" rows="2" name="text" id="text"></textarea></td>
</tr>
<tr>
	<td colspan="5" align="center"><input type="submit" name="submit" id="submit" value="create"></td>
</tr>
</table>
</form>