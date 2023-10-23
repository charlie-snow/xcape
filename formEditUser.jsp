<% 
	String user_id = request.getParameter("user_id");
	xcaping.User user = new xcaping.User();
	user.get(user_id);
	
	xcaping.Lists list = new xcaping.Lists();	
	xcaping.UserType type = new xcaping.UserType();
	
	java.util.Vector data_types = new java.util.Vector();
	
	java.util.Enumeration e_types;
	
	data_types = list.getUserTypes();
%>


<form action="modifyUser.jsp" method="post" id="modifyUser">

<input type="hidden" name="user_id" id="user_id" value="<%= user.id %>">

<% if (user.type.equals("1")) { %>

<input type="hidden" name="name" id="name" value="<%= user.name %>">
<input type="hidden" name="type" id="type" value="1">
<table width="350" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td align="center">
	new password for <%= user.name %>: <input type="text" name="password" id="password">
	</td>
</tr>
<tr>
	<td colspan="2" align="center"><input type="submit" name="submit" id="submit" value="ok"></td>
</tr>
</table>

<% } else { %>

<table width="350" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td colspan="4" align="center">
	name: <input type="text" name="name" id="name" value="<%= user.name %>">
	<br>
	password: <input type="text" name="password" id="password" value="<%= user.password %>">
	</td>
</tr>
<tr>
	<td>type:</td>
	<td>
	<select name="type" id="type" size="1">
		<%
		for (e_types = data_types.elements() ; e_types.hasMoreElements() ;) {
			type = (xcaping.UserType)e_types.nextElement();
		%>
		<option value="<%= type.id %>" <%  if (Integer.parseInt(user.type) == Integer.parseInt(type.id)) { %>selected<% } %>><%= type.name %></option>
		<% } %>
	</select>
	</td>
</tr>
<tr>
	<td colspan="2" align="center"><input type="submit" name="submit" id="submit" value="ok"></td>
</tr>
</table>

<% } %>



</form>