<%
	String gala_id = request.getParameter("gala_id");
	xcaping.Gala gala = new xcaping.Gala();
	gala.get(gala_id);
%>

<br>
<form action="modifyGala.jsp" method="post" name="modifyGala" id="modifyGala">
<input type="hidden" name="gala_id" id="gala_id" value="<%= gala_id %>">
gala name: <input type="text" name="name" id="name" value="<%= gala.name %>">
obligatory: <input type="checkbox" name="obligatory" id="obligatory" value="1"
<% if (gala.obligatory.equals("1")) { %>
		 checked
	<% } %>>
<br>
from date: <input type="text" name="from_date" id="from_date" value="<%= gala.from_date %>">
to date: <input type="text" name="to_date" id="to_date" value="<%= gala.to_date %>">
<input type="submit" name="submit" id="submit" value="ok">
</form>