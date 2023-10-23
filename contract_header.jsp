<%@ include file="contract_data_extra.jsp"%>

<table width="970" border="0" cellspacing="0" cellpadding="3" align="left">
<tr class="<%= tr_class_contract %>">
	<td width="10"></td>
	<td class="title">Contract <%= contract.name %> 
	---- <a href="<%= contract.editURL %>" class="blue">edit</a></td>
	<td>From <%= contract.from_date %>
	&nbsp;&nbsp;
	To <%= contract.to_date %>
	&nbsp;&nbsp;
	Supplier <%= supplier.name %>
	&nbsp;&nbsp;
	</td>
	<td width="200" class="title"><%= property.name %></td>
</tr>


<tr>
	<td width="10"></td>
	<td colspan="2">	
	<a href="<%= contract.active_roomsURL %>" class="blue">active rooms</a> | 
	<a href="<%= contract.configurationURL %>" class="blue">configuration</a> | 
	<a href="<%= contract.add_onsURL %>" class="blue">add-ons</a> |
	<a href="<%= contract.periodsURL %>" class="blue">periods</a> | 
	<!-- <a href="<%= contract.allocationsURL %>" class="blue">allocations</a> | -->
	
	<% if (session.getAttribute("av_pr").equals("true")) { %>
		<a href="<%= contract.availabilitiesURL %>" class="blue">availabilities</a> | 
		<a href="<%= contract.pricelinesURL %>" class="blue">pricing</a>
	<% } %>
	
	</td>
	<td>
	<% if (!finished && session.getAttribute("generate").equals("true")) { 
		 if (generated) { %>
		 <a href="unGenerate.jsp?contract_id=<%= contract_id %>" class="blue">UN-GENERATE</a>
		<% } else { %>
			<a href=generate.jsp?contract_id=<%= contract_id %>&item_id=<%= item_id %>"  class="blue">generate</a>
		<% } 
	 } %>
	</td>
</tr>
<tr>
	<td colspan="4"><img src="images/line.gif" width="960" height="3" alt="" border="0"></td>
</tr>
</table>