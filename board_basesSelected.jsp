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
	
	property board_bases
	<table border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
		<td align="center">name</td>
	</tr>
	
	<%
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			board_basis = (xcaping.BoardBasis)enum_data.nextElement();
	%>
	
	<tr>
		<td align="center"><%= board_basis.name %></td>
	</tr>
	
	<%}%>
	
	</table>
	
	</td>

	<td align="center">
	
	system board_bases
	<table border="0" cellspacing="2" cellpadding="4" align="center">
	<tr class="header">
		<td align="center">name</td>
	</tr>
	
	<%
		data = list.getBoardBases();	
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			board_basis = (xcaping.BoardBasis)enum_data.nextElement();
	%>
	
	<tr>
		<td align="center"><%= board_basis.name %></td>
	</tr>
	
	<%}%>
	
	</table>
	
	</td>
</tr>
</table>


