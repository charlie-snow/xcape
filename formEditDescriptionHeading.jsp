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
	String description_heading_id = request.getParameter("description_heading_id");
	String item_type = request.getParameter("item_type");
	xcaping.DescriptionHeading description_heading = new xcaping.DescriptionHeading(item_type);
	description_heading.get(description_heading_id);
%>

<form action="modifyDescriptionHeading.jsp" method="post" name="modifydescription_heading" id="modifydescription_heading">

<input type="hidden" name="description_heading_id" id="description_heading_id" value="<%= description_heading.id %>">
<input type="hidden" name="item_type" id="item_type" value="<%= item_type %>">
<input type="hidden" name="order" id="order" value="<%= description_heading.order %>">

<table width="350" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
	<td colspan="4" align="center">heading: <input type="text" name="heading" id="heading" value="<%= description_heading.heading %>"></td>
</tr>
<tr>
	<td colspan="2" align="center"><input type="submit" name="submit" id="submit" value="ok"></td>
</tr>
</table>

</form>
			
<%@ include file="author_footer.jsp"%>

