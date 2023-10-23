<%@ include file="author_init.jsp"%>

<%
	manager = false;
	properties_manager = true;
	contracts_manager = false;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<% 
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	String modify = request.getParameter("modify");
	String description_id = request.getParameter("description_id");
	
	xcaping.Description description = new xcaping.Description("0", "0");
	if (modify.equals("1")) { 
		description = new xcaping.Description(item_type, item_id);
		description.get(description_id);
	}
	
	xcaping.DescriptionHeading description_heading = new xcaping.DescriptionHeading("0");
	java.util.Vector data = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	data = list.getDescriptionHeadings(item_type);
%>

<form action="manageDescription.jsp" method="post" name="managedescription" id="managedescription">

<input type="hidden" name="item_id" id="item_id" value="<%= item_id %>">
<input type="hidden" name="item_type" id="item_type" value="<%= item_type %>">
<input type="hidden" name="modify" id="modify" value="<%= modify %>">
<input type="hidden" name="description_id" id="description_id" value="<%= description_id %>">

<table width="350" border="0" cellspacing="0" cellpadding="3" align="center">
<tr align="center">
	<td>heading: &nbsp;&nbsp;&nbsp;&nbsp;
	<select name="heading" id="heading" size="1">
		<%
		java.util.Enumeration e;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			description_heading = (xcaping.DescriptionHeading)e.nextElement();
		%>
		<option value="<%= description_heading.id %>"
		<% if (modify.equals("1")) {
				if (description.heading.equals(description_heading.id)) { out.print(" selected"); } 
			}
		%>><%= description_heading.heading %></option>
		<% } %>
	</select>
	</td>
</tr>
<tr>
	<td align="center">description: <textarea cols="80" rows="4" name="description" id="description"><% if (modify.equals("1")) { out.print(description.description); } %></textarea></td>
</tr>
<tr>
	<td align="center"><input type="submit" name="submit" id="submit" value="ok"></td>
</tr>
</table>

</form>
			
<%@ include file="author_footer.jsp"%>

