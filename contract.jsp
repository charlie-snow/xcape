<%@ include file="author_init.jsp"%>

<%
	manager = true;
	properties_manager = false;
	contracts_manager = true;
	contracts_operator = true;
%>

<%@ include file="author_header.jsp"%>

<%@ include file="contract_data.jsp"%>
<%
String subcontent = request.getParameter("subcontent");
String subarchive;
if (subcontent != null) {
	subarchive=subcontent+".jsp";
} else {
	if (generated) {
		subarchive="periods.jsp";
	} else {
		subarchive="contract_rooms.jsp";
	}
}
%>

<table border="0" cellspacing="0" cellpadding="0">
<tr>
	<td><%@ include file="contract_header.jsp"%></td>
</tr>
<tr>
	<td><jsp:include page="<%= subarchive %>" flush="true"/></td>
</tr>
</table>


<%@ include file="author_footer.jsp"%>