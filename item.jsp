<%@ include file="item_header.jsp"%>

<%
String subcontent = request.getParameter("subcontent");
String subarchive;
if (subcontent != null) {
	subarchive=subcontent+".jsp";
} else {
	if (is_property) {
		if (session.getAttribute("type").equals("properties_manager")) {
			subarchive="photosItem.jsp";
		} else {
			subarchive="contracts.jsp";
		}
	} else {
		subarchive="itemsList.jsp";
	}
}
%>
<br>
<jsp:include page="<%= subarchive %>" flush="true"/>
