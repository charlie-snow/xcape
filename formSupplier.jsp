<%@ include file="author_init.jsp"%>

<%
	manager = true;
	properties_manager = false;
	contracts_manager = true;
	contracts_operator = true;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<% 
	String supplier_id = "";
	String modify = request.getParameter("modify");
	
	xcaping.Supplier supplier = new xcaping.Supplier();
	if (modify.equals("1")) { 
		supplier_id = request.getParameter("supplier_id");
		supplier.get(supplier_id);
	}
%>

<form action="manageSupplier.jsp" method="post" name="manageSupplier" id="manageSupplier">

<input type="hidden" name="supplier_id" id="supplier_id" value="<%= supplier_id %>">
<input type="hidden" name="modify" id="modify" value="<%= modify %>">

<table border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">name</td>
	<td align="center">HF adult</td>
	<td align="center">HF child</td>
	<td align="center">contact name</td>
	<td align="center">contact title</td>
	<td align="center">address1</td>
	<td align="center">address2</td>
	<td align="center">city</td>
</tr>
<tr>
	<td align="center">
	<input type="text" name="name" id="name" value="<% if (modify.equals("1")) { out.print(supplier.name); } %>"></td>
	<td align="center">
	<input type="text" name="handling_fee_adult" id="handling_fee_adult" value="<% if (modify.equals("1")) { out.print(supplier.handling_fee_adult); } else { out.print("0.0"); } %>" size="4"></td>
	<td align="center">
	<input type="text" name="handling_fee_child" id="handling_fee_child" value="<% if (modify.equals("1")) { out.print(supplier.handling_fee_child); } else { out.print("0.0"); } %>" size="4"></td>
	<td align="center">
	<input type="text" name="contact_name" id="contact_name" value="<% if (modify.equals("1")) { out.print(supplier.contact_name); } %>"></td>
	<td align="center">
	<input type="text" name="contact_title" id="contact_title" value="<% if (modify.equals("1")) { out.print(supplier.contact_title); } %>"></td>
	<td align="center">
	<input type="text" name="address1" id="address1" value="<% if (modify.equals("1")) { out.print(supplier.address1); } %>"></td>
	<td align="center">
	<input type="text" name="address2" id="address2" value="<% if (modify.equals("1")) { out.print(supplier.address2); } %>"></td>
	<td align="center">
	<input type="text" name="city" id="city" value="<% if (modify.equals("1")) { out.print(supplier.city); } %>"></td>
</tr>
</table>
<br><br>
<table border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">county</td>
	<td align="center">post code</td>
	<td align="center">country</td>
	<td align="center">tel</td>
	<td align="center">tel ext</td>
</tr>
<tr>
	<td align="center">
	<input type="text" name="county" id="county" value="<% if (modify.equals("1")) { out.print(supplier.county); } %>"></td>
	<td align="center">
	<input type="text" name="post_code" id="post_code" value="<% if (modify.equals("1")) { out.print(supplier.post_code); } %>"></td>
	<td align="center">
	<input type="text" name="country" id="country" value="<% if (modify.equals("1")) { out.print(supplier.country); } %>"></td>
	<td align="center">
	<input type="text" name="tel" id="tel" value="<% if (modify.equals("1")) { out.print(supplier.tel); } %>"></td>
	<td align="center">
	<input type="text" name="tel_ext" id="tel_ext" value="<% if (modify.equals("1")) { out.print(supplier.tel_ext); } %>"></td>
</tr>
</table>
<br><br>
<table border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">fax</td>
	<td align="center">email</td>
	<td align="center">www</td>
	<td align="center">customerService</td>
</tr>
<tr>
	<td align="center">
	<input type="text" name="fax" id="fax" value="<% if (modify.equals("1")) { out.print(supplier.fax); } %>"></td>
	<td align="center">
	<input type="text" name="email" id="email" value="<% if (modify.equals("1")) { out.print(supplier.email); } %>"></td>
	<td align="center">
	<input type="text" name="www" id="www" value="<% if (modify.equals("1")) { out.print(supplier.www); } %>"></td>
	<td align="center">
	<input type="text" name="customer_service" id="customer_service" value="<% if (modify.equals("1")) { out.print(supplier.customer_service); } %>"></td>
</tr>
</table>

<div align="center"><input type="submit" name="submit" id="submit" value="ok"></div>

</form>
			
<%@ include file="author_footer.jsp"%>

