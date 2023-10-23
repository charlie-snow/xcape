<%
	String contract_id = request.getParameter("contract_id");
	
	java.util.Vector data = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration enum_data;
	
	data = list.getBoardBasesContract(contract_id);	
	xcaping.BoardBasis board_basis = new xcaping.BoardBasis();
%>
<head>
<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>

<table width="400" border="0" cellspacing="0" cellpadding="2" align="center">
<tr>
	<td align="center" valign="top">
	
	contract board_bases
	<table border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
		<td align="center">name</td>
		<td></td>
	</tr>
	
	<%
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			board_basis = (xcaping.BoardBasis)enum_data.nextElement();
	%>
	
	<tr>
		<td align="center"><%= board_basis.name %></td>
		<td align="center"><a href="subBoardBasis.jsp?board_basis_id=<%= board_basis.id %>&contract_id=<%= contract_id %>">del</a></td>
	</tr>
	
	<%}%>
	
	</table>
	
	</td>

	<td align="center">
	
	system board_bases
	<table border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
		<td align="center">name</td>
		<td></td>
	</tr>
	
	<%
		data = list.getBoardBases();	
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			board_basis = (xcaping.BoardBasis)enum_data.nextElement();
	%>
	
	<tr>
		<td align="center"><%= board_basis.name %></td>
		<td align="center"><a href="addBoardBasis.jsp?board_basis_id=<%= board_basis.id %>&contract_id=<%= contract_id %>">add</a></td>
	</tr>
	
	<%}%>
	
	</table>
	
	</td>
</tr>
</table>


