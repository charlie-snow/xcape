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
	String item_type =  request.getParameter("item_type");
	
	String photo_id = request.getParameter("photo_id");
	
	xcaping.PhotoItem photo_item = new xcaping.PhotoItem();
	photo_item.setNames(item_type);
	photo_item.getURLs(item_id, item_type);
	
	if (photo_id.indexOf("G") != -1) {
		String[] array = new String[3];
		array = photo_id.split("G");
		photo_item.deleteGroup(array[1], item_id);
	} else {
		photo_item.delete(photo_id, item_id);
	}
    response.sendRedirect(photo_item.returnURL);
%>

<%@ include file="author_footer.jsp"%>

