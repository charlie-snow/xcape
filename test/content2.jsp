<%
String content = request.getParameter("content");
String archive;
if (content != null) {
	archive=content+".jsp";
} else {
	archive="welcome.jsp";
}
%>

<jsp:include page="<%= archive %>" flush="true"/>