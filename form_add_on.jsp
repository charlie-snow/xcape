<table width="500" border="0" cellspacing="2" cellpadding="4" align="center">
<form action="insertAddOn.jsp" method="post" name="insertAddOn" id="insertAddOn">

<input type="hidden" name="contract_id" id="contract_id" value="<%= contract_id %>">
<tr>
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
	<td><input type="submit" name="submit" id="submit" value="add"></td>
</tr>
</table>
</form>