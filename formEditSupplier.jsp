<%
	String supplier_id = request.getParameter("supplier_id");
	xcaping.Supplier supplier = new xcaping.Supplier();
	supplier.get(supplier_id);
%>

<%@ include file="supplierHeader.jsp"%>

<form action="modifySupplier.jsp" method="post" name="modifySupplier" id="modifySupplier">
<input type="hidden" name="supplier_id" id="supplier_id" value="<%= supplier_id %>">

<table width="350" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td colspan="4" align="center">supplier name: <input type="text" name="name" id="name" value="<%= supplier.name %>"></td>
</tr>
</table>

<input type="submit" name="submit" id="submit" value="ok">
</form>