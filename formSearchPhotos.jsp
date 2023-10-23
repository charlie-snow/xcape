<%
	manager = false;
	properties_manager = true;
	contracts_manager = false;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<form action="insertPhotosItem.jsp" method="post" name="searchPhotos" id="searchPhotos">
<input type="hidden" name="item_id" id="item_id" value="<%= item_id %>">
<input type="hidden" name="item_type" id="item_type" value="<%= item_type %>">

<input type="text" name="name" id="name" value="">

<input type="submit" name="submit" id="submit" value="ok">
</form>
			
<%@ include file="author_footer.jsp"%>

