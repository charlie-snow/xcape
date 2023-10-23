<%
	xcaping.Lists list = new xcaping.Lists();	
	xcaping.User user = new xcaping.User();
	xcaping.UserType type = new xcaping.UserType();
	
	java.util.Vector data = new java.util.Vector();
	java.util.Vector data_types = new java.util.Vector();
	
	java.util.Enumeration e;
	java.util.Enumeration e_types;
	
	data = list.getUsers();
	data_types = list.getUserTypes();
%>
<head>
<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>

<script language="JavaScript">
function confirmDelete(user_id){
	if (confirm("WARNING: if you delete this user, all the contracts he/she edited will have no owner")) {
		window.location = "deleteUser.jsp?user_id="+user_id;
	}
}
</script>

<table width="650" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td align="center">name</td>
	<td align="center">type</td>
	<td></td>
	<td></td>
</tr>

<%
	for (e = data.elements() ; e.hasMoreElements() ;) {
		user = (xcaping.User)e.nextElement();
		type.get(user.type);
%>

<tr>
	<td align="center"><%= user.name %></td>
	<td align="center"><%= type.name %></td>
	<td align="center"><a href="index.jsp?content=formEditUser&user_id=<%= user.id %>">edit</a></td>
	<td align="center">
	<% if (!user.type.equals("1")) {%>
	<a href="#" onClick="confirmDelete(<%= user.id %>)">delete</a>
	<% } %>
	</td>
</tr>

<%}%>
</table>

<table width="650" border="0" cellspacing="2" cellpadding="4" align="center">
<form action="insertUser.jsp" method="post" name="insertUser" id="insertUser">

<tr>
	<td align="center">name: <input type="text" name="name" id="name"></td>
	<td align="center">password: <input type="text" name="password" id="password"></td>
	<td align="center">type: 
	<select name="type" id="type" size="1">
		<%
		for (e_types = data_types.elements() ; e_types.hasMoreElements() ;) {
			type = (xcaping.UserType)e_types.nextElement();
			if (!type.id.equals("1")) {
		%>
		<option value="<%= type.id %>" ><%= type.name %></option>
		<% } } %>
	</select>
	</td>
	<td colspan="2" align="center">
	<input type="submit" name="submit" id="submit" value="add"></td>
</tr>

</form>
</table>

