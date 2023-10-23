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
	
	xcaping.Item item = new xcaping.Item(item_type);
	item.getURLs(item_id, item_type);
	
	xcaping.DBConnection DBConnection = new xcaping.DBConnection();
	java.util.Vector descriptions = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration e;
	xcaping.Description description = new xcaping.Description("0", "0");
	descriptions = list.getDescriptions(item_id, item_type);
	e = descriptions.elements();
	String preview = "";
	xcaping.DescriptionHeading description_heading = new xcaping.DescriptionHeading(item_type);
%>

<script language="JavaScript">
function confirmDelete(item_id, item_type, desc_id){
	if (confirm("WARNING: are you sure you want to delete this description?")) {
		window.location = "deleteDescription.jsp?item_id="+item_id+"&item_type="+item_type+"&description_id="+desc_id;
	}
}
</script>

<table width="800" border="0" cellspacing="2" cellpadding="3" align="center">

	<% if (session.getAttribute("descriptions_edit").equals("true")) { %>
	<tr>
		<td colspan="5" align="center">
		<a href="<%= item.itemURL %>&subcontent=formDescription&modify=0">insert new</a></td>
	</tr>
	<% } %>
	<tr class="header">
		<td width="120" align="center">heading</td>
		<td width="600" align="center">description</td>
		<td width="10" align="center">act</td>
		<td width="30" align="center">edit</td>
		<td width="30" align="center">del</td>
	</tr>
	<% while (e.hasMoreElements()) { 
		description = (xcaping.Description)e.nextElement();
		description_heading.get(description.heading);
		if (description.description.length() > 80) { 
			preview	= description.description.substring(0, 80)+"...";
		} else {
			preview	= description.description;
		}
	%>
	<tr>
		<td><%= description_heading.heading %></td>
		<td><%= preview %></td>
		<td><%= description.active %></td>
		<% if (session.getAttribute("descriptions_edit").equals("true")) { %>
			<td>
			<a href="<%= item.itemURL %>&subcontent=formDescription&description_id=<%= description.id %>&modify=1">edit</a></td>
			<td>
			<a href="#" onClick="confirmDelete(<%= item_id %>, <%= item_type %>, <%= description.id %>)">delete</a>
			</td>
		<% } else { %>
			<td></td>
			<td></td>
		<% } %>
		
	</tr>
	<% } %>
</table>

<%@ include file="author_footer.jsp"%>