<%
	String supplier_id = request.getParameter("supplier_id");
	xcaping.Supplier supplier = new xcaping.Supplier();
	supplier.getURLs();
	
	String follow = "";
	
	if (supplier.delete(supplier_id)) {
		follow = supplier.returnURL;
	} else {
		follow =supplier.errorURL+supplier.error;
	}
    response.sendRedirect(follow);
%>
