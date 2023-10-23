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

	xcaping.PhotoItem photo_item = new xcaping.PhotoItem();

	// list the item photos -----------------

	java.util.Vector data = new java.util.Vector();
	xcaping.Lists list = new xcaping.Lists();
	java.util.Enumeration e;
	
	String follow = "";
	
	data = list.getPhotos(item_id, item_type);
	
	boolean valid = true;
	for (e = data.elements(); e.hasMoreElements();) {
		photo_item = (xcaping.PhotoItem)e.nextElement();
		photo_item.setNames(item_type);
		if (item_type.equals("5")) { photo_item.heading = request.getParameter("photo_heading"+photo_item.photo); }
		photo_item.description = request.getParameter("photo_description"+photo_item.photo);
		photo_item.order = request.getParameter("photo_order"+photo_item.photo);
		if (!photo_item.modify(item_type)) {
			valid = false;
		}
	}
	photo_item.getURLs(item_id, item_type);
	
	if (valid) {
		follow = photo_item.returnURL;
	} else {
		follow = photo_item.errorURL+photo_item.error;
	}
    response.sendRedirect(follow);
%>
			
<%@ include file="author_footer.jsp"%>

