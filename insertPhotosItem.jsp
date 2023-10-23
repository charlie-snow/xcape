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
	String item_type = request.getParameter("item_type");
	String name = request.getParameter("name");
	xcaping.PhotoItem photo_item = new xcaping.PhotoItem();
	photo_item.setNames(item_type);
	photo_item.getURLs(item_id, item_type);
	photo_item.insertPhotos(name, item_id, item_type);
	String redirectURL = photo_item.returnURL;
    response.sendRedirect(redirectURL);
%>
			
<%@ include file="author_footer.jsp"%>

