<%
	String stop_sale_id = request.getParameter("stop_sale_id");
	String item_id = request.getParameter("item_id");
	xcaping.StopSale stop_sale = new xcaping.StopSale();
	stop_sale.getURLs(item_id, "5");
	String follow = "";
	
	stop_sale.delete(stop_sale_id);
	follow = stop_sale.returnURL;
    response.sendRedirect(follow);
%>
