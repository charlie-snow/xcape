<%
	xcaping.Lists list_supplements = new xcaping.Lists();
	java.util.Vector data_supplements = new java.util.Vector();
	java.util.Enumeration enum_data_supplements;
	data_supplements = list_supplements.getSupplements();
%>

<script language="JavaScript1.2">
function changeDiscPercentage(discount_id) {
	if (document.getElementById('discount_percentage'+discount_id).value == '1') {
		document.getElementById('discount_percentage'+discount_id).value = '0';
		document.getElementById('disc_'+discount_id).innerHTML = '<strong><%= currency.symbol %></strong>';
	} else {
		document.getElementById('discount_percentage'+discount_id).value = '1';
		document.getElementById('disc_'+discount_id).innerHTML = '<strong>%</strong>';
	}
}
</script>

<form action="insertAddOn.jsp" method="post" name="insertAddOn" id="insertAddOn">
<input type="hidden" name="contract_id" id="contract_id" value="<%= contract_id %>">
<tr>
	<td>
	<select name="supplement" id="supplement" size="1">
		<%
		for (enum_data_supplements = data_supplements.elements() ; enum_data_supplements.hasMoreElements() ;) {
			supplement = (xcaping.Supplement)enum_data_supplements.nextElement();
		%>
		<!-- <option value="<%= supplement.id %>"><%= supplement.description %></option> -->
		<% } %>
		<option value="49">add-on (per person)</option>
		<option value="51">gala dinner</option>
	</select>
	</td>
	<td align="center">
	<input type="text" name="name" id="name" size="10">
	</td>
	<td align="center">
	<input type="text" name="from_date" id="from_date" size="6">
	</td>
	<td align="center">
	<input type="text" name="to_date" id="to_date" size="6">
	</td>
	<td align="center">
	<input type="text" name="price" id="price" size="4">
	</td>
	<% if (has_child_discount) { %>
	<td align="center">
	<input type="text" name="child_discount" id="child_discount" size="4">
	<a href="#" onClick="changeDiscPercentage('1')">
	<spam name="disc_1" id="disc_1">
	<strong>%</strong>
	</spam></a>
	</td>
	<% } else { %>
	<input type="hidden" name="child_discount" id="child_discount" value="0"><% } %>
	<input type="hidden" name="discount_percentage1" id="discount_percentage1" value="1">
	<% if (has_adult_discount) { %>
	<td align="center"><input type="text" name="adult_discount" id="adult_discount" size="4">
	<a href="#" onClick="changeDiscPercentage('2')">
	<spam name="disc_2" id="disc_2">
	<strong>%</strong>
	</spam></a>
	</td>
	<% } else { %>
	<input type="hidden" name="adult_discount" id="adult_discount" value="0"><% } %>
	<input type="hidden" name="discount_percentage2" id="discount_percentage2" value="1">
	<td><input type="submit" name="submit" id="submit" value="add"></td>
</tr>
</form>