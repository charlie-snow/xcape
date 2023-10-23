<%
	xcaping.Item property = new xcaping.Item(item_type);
	property = item;
	xcaping.Rating rating = new xcaping.Rating();
	rating.get(property.rating);
	String rrating = rating.name;
%>

<table width="960" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td width="20"></td>
	<td class="title">
	<%= item.name %> - <%= rrating %> - code: <%= item.id %> 
	</td>
	<td></td>
</tr>
<tr>
	<td></td>
	<td>
	<% if (session.getAttribute("contracts_property").equals("true")) { %>
	<a href="<%= item.contractsURL %>" class="blue">contracts</a> | <% } %>
	<% if (session.getAttribute("rooming_property").equals("true")) { %>
	<a href="<%= item.roomingURL %>" class="blue">rooming</a> | <% } %>
	<% if (session.getAttribute("photos").equals("true")) { %>
	<a href="<%= item.photosURL %>" class="blue">photos</a> | <% } %>
	<% if (session.getAttribute("descriptions").equals("true")) { %>
	<a href="<%= item.descriptionsURL %>" class="blue">descriptions</a>	| <% } %>
	<% if (session.getAttribute("edit_item").equals("true")) { %>
	<a href="<%= item.editURL %>&list=0" class="blue">edit</a> | <% } %>
	<a href="<%= item.brochureURL %>" target="_blank" class="blue">brochure</a> | 
	<% if (session.getAttribute("move_property").equals("true")) { %>
	<a href="<%= item.itemURL %>&subcontent=formSearchResort" class="blue">MOVE</a> | <% } %>
	<% if (session.getAttribute("av_pr").equals("true")) { %>
	<a href="<%= item.availabilitiesURL %>" class="blue">availabilities</a> | 
	<a href="<%= item.pricelinesURL %>" class="blue">pricing</a> | <% } %>
	<% if (session.getAttribute("total_stop_sale").equals("true")) { %>
	<a href="<%= item.itemURL %>&subcontent=stop_sale_item" class="blue">stop sale</a><% } %>
	<% if (session.getAttribute("history").equals("true")) { %>
	history: 
	<a href="<%= item.availabilitiesHistoryURL %>" class="blue">availabilities</a> | 
	<a href="<%= item.pricelinesHistoryURL %>" class="blue">pricing</a> | <% } %>
	</td>
	<td></td>
</tr>
</table>
<br>
<img src="images/line.gif" width="960" height="3" alt="" border="0">
<br>