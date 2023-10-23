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
	xcaping.DBConnection DBConnection = new xcaping.DBConnection();
	java.util.Vector photos = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration e;
	xcaping.Photo photo = new xcaping.Photo();
	
	photos = list.getPhotosToActivate();
	e = photos.elements();
	int i = 1;
%>
<table border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td align="center" class="sub-title">
	Photos to activate &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="activatePhotos.jsp">·activate photos·</a>	
	</td>
</tr>
<tr>
	<td>
	<table border="0" cellspacing="0" cellpadding="3" align="center">
	<% while (e.hasMoreElements()) { %>
	<tr>
		<td align="center" valign="top">
		<%@ include file="photosRow.jsp"%>
		</td>
	</tr>
	<% } %>
	</table>
	</td>
</tr>
</table>
			
<%@ include file="author_footer.jsp"%>

