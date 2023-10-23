<%
	xcaping.Lists list = new xcaping.Lists();
	xcaping.Supplier supplier = new xcaping.Supplier();
	java.util.Vector data = new java.util.Vector();
	data = list.getSuppliers();
%>

<script language="JavaScript">
function confirmDelete(supplier_id){
	if (confirm("are you sure you want to delete this supplier?")) {
		window.location = "deleteSupplier.jsp?supplier_id="+supplier_id;
	}
}
</script>

<div align="center"><a href="index.jsp?content=formSupplier&modify=0">insert new</a></div>

<table width="650" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">name</td>
	<td align="center">city</td>
	<td align="center">tel</td>
	<td align="center">email</td>
	<td></td>
	<td></td>
</tr>

<%
	java.util.Enumeration e;
	for (e = data.elements() ; e.hasMoreElements() ;) {
		supplier = (xcaping.Supplier)e.nextElement();
%>

<tr>
	<td align="center"><%= supplier.name %></td>
	<td align="center"><%= supplier.city %></td>
	<td align="center"><%= supplier.tel %></td>
	<td align="center"><%= supplier.email %></td>
	<td align="center"><a href="index.jsp?content=formSupplier&supplier_id=<%= supplier.id %>&modify=1">edit</a></td>
	<td align="center"><a href="#" onClick="confirmDelete(<%= supplier.id %>)">delete</a></td>
</tr>

<%}%>
</table>

