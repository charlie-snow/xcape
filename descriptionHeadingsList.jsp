<%@ include file="author_init.jsp"%>

<%
	manager = false;
	properties_manager = true;
	contracts_manager = true;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
<%
	String item_type = request.getParameter("item_type");
	xcaping.Lists list = new xcaping.Lists();
	xcaping.DescriptionHeading description_heading = new xcaping.DescriptionHeading("0");
	java.util.Vector data = new java.util.Vector();
	data = list.getDescriptionHeadings(item_type);
	
	xcaping.Data names = new xcaping.Data();
	names.getNames(String.valueOf(Integer.parseInt(item_type)));
%>

<script language="JavaScript">
function confirmDelete(description_heading_id){
	if (confirm("are you sure you want to delete this heading?")) {
		window.location = "deleteDescriptionHeading.jsp?item_type=<%= item_type %>&description_heading_id="+description_heading_id;
	}
}
</script>

<table width="650" border="0" cellspacing="2" cellpadding="4" align="center">
<tr class="header">
	<td colspan="4" align="center">description headings for: <%= names.name %></td>
</tr>
<tr class="header">
	<td align="center">heading</td>
	<td align="center">position</td>
	<td></td>
	<td></td>
</tr>

<%
	java.util.Enumeration e;
	for (e = data.elements() ; e.hasMoreElements() ;) {
		description_heading = (xcaping.DescriptionHeading)e.nextElement();
%>

<tr>
	<td align="center"><%= description_heading.heading %></td>
	
	<% if (description_heading.id == "1") { %>
	<td align="center">1</td>
	<td></td>
	<td></td>
	<% } else { %>
	<td align="center">
	<a href="orderDescriptionHeading.jsp?item_type=<%= item_type %>&description_heading_id=<%= description_heading.id %>&up=1">+</a>
	<%= description_heading.order %>
		<% if (description_heading.order != "2") { %>
	<a href="orderDescriptionHeading.jsp?item_type=<%= item_type %>&description_heading_id=<%= description_heading.id %>&up=0">-</a>
		<% } %>
	</td>
	<td align="center"><a href="index.jsp?content=formEditDescriptionHeading&item_type=<%= item_type %>&description_heading_id=<%= description_heading.id %>">edit</a></td>
	<td align="center"><a href="#" onClick="confirmDelete(<%= description_heading.id %>)">delete</a></td>
	<% } %>	
</tr>

<%}%>

<form action="insertDescriptionHeading.jsp" method="post" name="insertdescriptionheading" id="insertdescriptionheading">

<input type="hidden" name="item_type" id="item_type" value="<%= item_type %>">

<tr>
	<td align="center"><input type="text" name="heading" id="heading" size="70"></td>
	<td colspan="2" align="center"><input type="submit" name="submit" id="submit" value="add"></td>
</tr>

</form>
</table>

<%@ include file="author_footer.jsp"%>