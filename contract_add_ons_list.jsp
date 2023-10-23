<%
	java.util.Vector data = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration enum_data;
	
	data = list.getAddOnsContract(contract_id);
	xcaping.AddOn add_on = new xcaping.AddOn();
	
	xcaping.Supplement supplement = new xcaping.Supplement();
	
	xcaping.Currency currency = new xcaping.Currency();
	currency.get(contract.currency);
%>

<tr class="header">
	<td align="center">supplement</td>
	<td align="center">name</td>
	<td align="center">from date</td>
	<td align="center">to date</td>
	<td align="center">price</td>
	<% if (has_child_discount) { %><td align="center">child_discount</td><% } %>
	<% if (has_adult_discount) { %><td align="center">adult_discount</td><% } %>
	<td></td>
</tr>

<%
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			add_on = (xcaping.AddOn)enum_data.nextElement();
			add_on.getURLs(contract_id);
			supplement = new xcaping.Supplement();
			supplement.get(add_on.supplement);
%>

<tr>
	<td align="center"><%= supplement.description %></td>
	<td align="center"><%= add_on.name %></td>
	<td align="center"><%= add_on.from_date %></td>
	<td align="center"><%= add_on.to_date %></td>
	<td align="center"><%= add_on.price %></td>
	<% if (has_child_discount) { %><td align="center"><%= add_on.child_discount %>
	<% if (add_on.child_percentage.equals("1")) { 
			 	out.print("%");
			 } else { 
				out.print(currency.symbol);
			 } %>
	</td><% } %>
	<% if (has_adult_discount) { %><td align="center"><%= add_on.adult_discount %>
	<% if (add_on.adult_percentage.equals("1")) { 
			 	out.print("%");
			 } else { 
				out.print(currency.symbol);
			 } %>
	</td><% } %>
	<td><a href="<%= add_on.deleteURL %>">delete</a></td>
</tr>

<% } %>