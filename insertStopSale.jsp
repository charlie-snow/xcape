<%
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	xcaping.Item item = new xcaping.Item(item_type);
	xcaping.StopSale stop_sale = new xcaping.StopSale();
	stop_sale.getURLs(item_id, item_type);
	
	String follow = "";
	
	stop_sale.from_date = request.getParameter("from_date");
	stop_sale.to_date = request.getParameter("to_date");
	stop_sale.room = request.getParameter("room");
	stop_sale.edited_by = request.getParameter("edited_by");
	stop_sale.edit_date = request.getParameter("edit_date");
	
	if (item.stopSales (item_id, item_type, stop_sale)) {
		follow = stop_sale.returnURL;;
	} else {
		follow = stop_sale.errorURL+item.error;
	}
	
    response.sendRedirect(follow);
%>