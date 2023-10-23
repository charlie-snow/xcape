<%@ include file="author_init.jsp"%>

<%
	manager = true;
	properties_manager = false;
	contracts_manager = true;
	contracts_operator = true;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<% 
	String supplier_id = "";
	String modify = request.getParameter("modify");
	
	xcaping.Supplier supplier = new xcaping.Supplier();
	supplier.getURLs();
	if (modify.equals("1")) { 
		supplier_id = request.getParameter("supplier_id");
	}
	
	String follow = "";
	
	supplier.name = request.getParameter("name");
	supplier.handling_fee_adult = request.getParameter("handling_fee_adult");
	supplier.handling_fee_child = request.getParameter("handling_fee_child");
	supplier.contact_name = request.getParameter("contact_name");
	supplier.contact_title = request.getParameter("contact_title");
	supplier.address1 = request.getParameter("address1");
	supplier.address2 = request.getParameter("address2");
	supplier.city = request.getParameter("city");
	supplier.county = request.getParameter("county");
	supplier.post_code = request.getParameter("post_code");
	supplier.country = request.getParameter("country");
	supplier.tel = request.getParameter("tel");
	supplier.tel_ext = request.getParameter("tel_ext");
	supplier.fax = request.getParameter("fax");
	supplier.email = request.getParameter("email");
	supplier.www = request.getParameter("www");
	supplier.customer_service = request.getParameter("customer_service");
	
	boolean ok = false;
	if (modify.equals("1")) {
		ok = supplier.modify(supplier_id);
	} else {
		ok = supplier.insert();
	}
	
	if (ok) {
		follow = supplier.returnURL;
	} else {
		follow = supplier.errorURL+supplier.error;
	}
    response.sendRedirect(follow);
%>
			
<%@ include file="author_footer.jsp"%>

