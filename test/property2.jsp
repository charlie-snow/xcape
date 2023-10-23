<br>
property header
<%@ include file="propertyHeader.jsp"%>
<br>
<% 
	out.print(property_id);
%>
<%
String subcontent = request.getParameter("subcontent");
String subarchive;
if (subcontent != null) {
	subarchive=subcontent+".jsp";
} else {
	subarchive="welcome.jsp";
}
%>

<jsp:include page="<%= subarchive %>" flush="true">

      <jsp:param name="property_id" value="<%= property_id %>" /> 

</jsp:include>