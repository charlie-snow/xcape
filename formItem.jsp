<%@ include file="author_init.jsp"%>

<%
	manager = true;
	properties_manager = true;
	contracts_manager = true;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<% 
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	String modify = request.getParameter("modify");
	String list = "";
	
	xcaping.Item item = new xcaping.Item("0");
	if (modify.equals("1")) { 
		list = request.getParameter("list");
		item = new xcaping.Item(item_type);
		item.get(item_id);
	}
	
	xcaping.Rating rating = new xcaping.Rating();
	java.util.Vector data = new java.util.Vector();
	boolean is_property = (!modify.equals("1") && item_type.equals("4")) || (modify.equals("1") && item_type.equals("5"));
	boolean is_country = (modify.equals("1") && item_type.equals("1"));
	boolean is_region = (!modify.equals("1") && item_type.equals("1")) || (modify.equals("1") && item_type.equals("2"));
	boolean is_area = (!modify.equals("1") && item_type.equals("2")) || (modify.equals("1") && item_type.equals("3"));
	if (is_property) {
		xcaping.Lists rating_list = new xcaping.Lists();
		data = rating_list.getRatings();
	}
%>

<form action="manageItem.jsp" method="post" name="manageItem" id="manageItem">

<input type="hidden" name="item_id" id="item_id" value="<%= item_id %>">
<input type="hidden" name="item_type" id="item_type" value="<%= item_type %>">
<input type="hidden" name="modify" id="modify" value="<%= modify %>">

<% if (modify.equals("1")) { %>
<input type="hidden" name="list" id="list" value="<%= list %>">
<% } %>

<table width="350" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td align="center">name: <input type="text" name="name" id="name" value="<% if (modify.equals("1")) { out.print(item.name); } %>"></td>
<% if (is_property) { %>
	<td>rating:
	<select name="rating" id="rating" size="1">
		<%
		java.util.Enumeration e;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			rating = (xcaping.Rating)e.nextElement();
		%>
		<option value="<%= rating.id %>"<% if (item.rating.equals(rating.id)) { out.print(" selected"); } %>><%= rating.name %></option>
		<% } %>
	</select>
	</td>
</tr>

<tr>
	<td colspan="2">
	<table width="600" align="center">
	<tr>
		<td width="50" align="center">luxury</td>
		<td width="50" align="center">business</td>
		<td width="50" align="center">design</td>
		<td width="50" align="center">budget</td>
		<td width="50" align="center">student</td>
		<td width="50" align="center">spa</td>
		<td width="50" align="center">golf</td>
		<td width="50" align="center">with character</td>
		<td width="50" align="center">rustic country</td>
		<td width="50" align="center">mid range</td>
		<td width="50" align="center">club</td>
		<td width="50" align="center">family</td>
	</tr>
	<% if (modify.equals("1")) { %>
	
		<tr>
			<td align='center'>
			<input type="checkbox" name="luxury" id="luxury" value="1"
			<% if (item.luxury.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="business" id="business" value="1"
			<% if (item.business.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="design" id="design" value="1"
			<% if (item.design.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="budget" id="budget" value="1"
			<% if (item.budget.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="student" id="student" value="1"
			<% if (item.student.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="spa" id="spa" value="1"
			<% if (item.spa.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="golf" id="golf" value="1"
			<% if (item.golf.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="character" id="character" value="1"
			<% if (item.character.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="rustic" id="rustic" value="1"
			<% if (item.rustic.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="mid" id="mid" value="1"
			<% if (item.mid.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="club" id="club" value="1"
			<% if (item.club.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
			<td align='center'>
			<input type="checkbox" name="family" id="family" value="1"
			<% if (item.family.equals("1")) { %>
				 checked
			<% } %>
			>
			</td>
		</tr>
	
	
	<% } else { %>
	
		<tr>
			<td align='center'><input type="checkbox" name="luxury" id="luxury" value="1"></td>
			<td align='center'><input type="checkbox" name="business" id="business" value="1"></td>
			<td align='center'><input type="checkbox" name="design" id="design" value="1"></td>
			<td align='center'><input type="checkbox" name="budget" id="budget" value="1"></td>
			<td align='center'><input type="checkbox" name="student" id="student" value="1"></td>
			<td align='center'><input type="checkbox" name="spa" id="spa" value="1"></td>
			<td align='center'><input type="checkbox" name="golf" id="golf" value="1"></td>
			<td align='center'><input type="checkbox" name="character" id="character" value="1"></td>
			<td align='center'><input type="checkbox" name="rustic" id="rustic" value="1"></td>
			<td align='center'><input type="checkbox" name="mid" id="mid" value="1"></td>
			<td align='center'><input type="checkbox" name="club" id="club" value="1"></td>
			<td align='center'><input type="checkbox" name="family" id="family" value="1"></td>
		</tr>
	
	<% } %>
	</table>
	</td>
</tr>


<tr>
	<td colspan="2">
	<table width="600" align="center">
	<tr>
		<td width="50" align="center">address</td>
		<td width="50" align="center">city</td>
		<td width="50" align="center">post code</td>
		<td width="50" align="center">telephone</td>
	</tr>
	<% if (modify.equals("1")) { %>
	
		<tr>
			<td align='center'>
			<input type="text" name="address" id="address" value="<%= item.address %>">
			</td>
			<td align='center'>
			<input type="text" name="city" id="city" value="<%= item.city %>">
			</td>
			<td align='center'>
			<input type="text" name="post_code" id="post_code" value="<%= item.post_code %>">
			</td>
			<td align='center'>
			<input type="text" name="telephone" id="telephone" value="<%= item.telephone %>">
			</td>
		</tr>
	
	
	<% } else { %>
	
		<tr>
			<td align='center'><input type="text" name="address" id="address"></td>
			<td align='center'><input type="text" name="city" id="city"></td>
			<td align='center'><input type="text" name="post_code" id="post_code"></td>	
			<td align='center'><input type="text" name="telephone" id="telephone"></td>
		</tr>
	
	<% } %>
	
	<tr>
		<td width="50" align="center">fax</td>
		<td width="50" align="center">email</td>
		<td width="50" align="center">web</td>
		<td width="50" align="center">contact</td>
	</tr>
	<% if (modify.equals("1")) { %>
	
		<tr>
			<td align='center'>
			<input type="text" name="fax" id="fax" value="<%= item.fax %>">
			</td>
			<td align='center'>
			<input type="text" name="email" id="email" value="<%= item.email %>">
			</td>
			<td align='center'>
			<input type="text" name="web" id="web" value="<%= item.web %>">
			</td>
			<td align='center'>
			<input type="text" name="contact" id="contact" value="<%= item.contact %>">
			</td>		
		</tr>
	
	
	<% } else { %>
	
		<tr>
			<td align='center'><input type="text" name="fax" id="fax"></td>
			<td align='center'><input type="text" name="email" id="email"></td>
			<td align='center'><input type="text" name="web" id="web"></td>
			<td align='center'><input type="text" name="contact" id="contact"></td>
		</tr>
	
	<% } %>
	
	
	</table>
	</td>
</tr>

<% } 
  if (is_country || is_region || is_area) { %>

<tr>
	<td colspan="2">
	__ descriptions info __________________________________________________________
	<br><br>
	<table width="600" align="center">
	<% if (modify.equals("1")) { %>
	<tr>
		<td width="50" align="center">from</td>
		<td align='center'>
			<select name="desc_from" id="desc_from" size="1">
				<option value="0"<% if (item.desc_from.equals("0")) { out.print(" selected"); } %>>xcape</option>
				<option value="1"<% if (item.desc_from.equals("1")) { out.print(" selected"); } %>>columbus</option>
			</select>
		</td>
		<td width="50" align="center">table</td>
		<td align='center'>
			<select name="desc_table" id="desc_table" size="1">
				<option value="table1"<% if (item.desc_table.equals("table1")) { out.print(" selected"); } %>>table1</option>
				<option value="table2"<% if (item.desc_table.equals("table2")) { out.print(" selected"); } %>>table2</option>
			</select>
		</td>
		<td width="50" align="center">code</td>
		<td align='center'><input type="text" name="desc_code" id="desc_code" value="<%= item.desc_code %>"></td>
	</tr>
	<% } else { %>
	<tr>
		<td width="50" align="center">from</td>
		<td align='center'>
			<select name="rating" id="rating" size="1">
				<option value="0">xcape</option>
				<option value="1">columbus</option>
			</select>
		</td>
		<td width="50" align="center">table</td>
		<td align='center'><input type="text" name="desc_table" id="desc_table"></td>
		<td width="50" align="center">code</td>
		<td align='center'><input type="text" name="desc_code" id="desc_code"></td>
	</tr>
	<% } %>	
	</table>
	</td>
</tr>

<% } %>

<tr>
	<td colspan="2" align="center"><input type="submit" name="submit" id="submit" value="ok"></td>
</tr>
</table>

</form>
			
<%@ include file="author_footer.jsp"%>

