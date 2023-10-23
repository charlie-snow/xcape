<%
	java.util.Vector data = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration enum_data;
	
	data = list.getBoardBases();
	xcaping.BoardBasis board_basis = new xcaping.BoardBasis();
%>
<head>
<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>

<table width="90%" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">name</td>
	<td></td>
	<td></td>
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