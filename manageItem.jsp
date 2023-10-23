<%@ include file="author_init.jsp"%>

<%
	manager = true;
	properties_manager = true;
	contracts_manager = true;
	contracts_operator = false;
%>

<%@ include file="author_header.jsp"%>
			<!-- END OF AUTHORISATION, START OF THE SCRIPT -->
			
<%
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	String modify = request.getParameter("modify");
	String list = request.getParameter("list");
	xcaping.Item item = new xcaping.Item("0");
	boolean is_property = (!modify.equals("1") && item_type.equals("4")) || (modify.equals("1") && item_type.equals("5"));
	boolean is_country = (modify.equals("1") && item_type.equals("1"));
	boolean is_region = (!modify.equals("1") && item_type.equals("1")) || (modify.equals("1") && item_type.equals("2"));
	boolean is_area = (!modify.equals("1") && item_type.equals("2")) || (modify.equals("1") && item_type.equals("3"));
	String follow = "";
	
	String item_type_aux = "";
	if (modify.equals("1")) {
		item_type_aux = item_type;
	} else {
		item_type_aux = String.valueOf(Integer.parseInt(item_type)+1);
	}
	item = new xcaping.Item(item_type_aux);
	item.name = request.getParameter("name");
	if (is_property) {
		item.rating = request.getParameter("rating");
		item.address = request.getParameter("address");
		item.city = request.getParameter("city");
		item.post_code = request.getParameter("post_code");
		item.telephone = request.getParameter("telephone");
		item.fax = request.getParameter("fax");
		item.email = request.getParameter("email");
		item.web = request.getParameter("web");
		item.contact = request.getParameter("contact");
		item.active = "1";

		if (request.getParameter("featured_resort") != null) {
			item.featured_resort = request.getParameter("featured_resort");
		} else {
			item.featured_resort = "0";
		}
		if (request.getParameter("featured_area") != null) {
			item.featured_area = request.getParameter("featured_area");
		} else {
			item.featured_area = "0";
		}
		if (request.getParameter("luxury") != null) {
			item.luxury = request.getParameter("luxury");
		} else {
			item.luxury = "0";
		}
		if (request.getParameter("business") != null) {
			item.business = request.getParameter("business");
		} else {
			item.business = "0";
		}
		if (request.getParameter("design") != null) {
			item.design = request.getParameter("design");
		} else {
			item.design = "0";
		}
		if (request.getParameter("budget") != null) {
			item.budget = request.getParameter("budget");
		} else {
			item.budget = "0";
		}
		if (request.getParameter("student") != null) {
			item.student = request.getParameter("student");
		} else {
			item.student = "0";
		}
		if (request.getParameter("spa") != null) {
			item.spa = request.getParameter("spa");
		} else {
			item.spa = "0";
		}
		if (request.getParameter("golf") != null) {
			item.golf = request.getParameter("golf");
		} else {
			item.golf = "0";
		}
		if (request.getParameter("character") != null) {
			item.character = request.getParameter("character");
		} else {
			item.character = "0";
		}
		if (request.getParameter("rustic") != null) {
			item.rustic = request.getParameter("rustic");
		} else {
			item.rustic = "0";
		}
		if (request.getParameter("mid") != null) {
			item.mid = request.getParameter("mid");
		} else {
			item.mid = "0";
		}
		if (request.getParameter("club") != null) {
			item.club = request.getParameter("club");
		} else {
			item.club = "0";
		}
		if (request.getParameter("family") != null) {
			item.family = request.getParameter("family");
		} else {
			item.family = "0";
		}
	} 
  	if (is_country || is_region || is_area) {
		item.desc_from = request.getParameter("desc_from");
		item.desc_table = request.getParameter("desc_table");
		item.desc_code = request.getParameter("desc_code");
	}
	boolean ok = false;
	if (modify.equals("1")) {
		ok = item.modify(item_id, list);
	} else {
		ok = item.insert(item_id, item_type);
	}
	
	if (ok) {
		follow = item.itemURL;
	} else {
		follow = item.errorURL+item.error;
	}
    response.sendRedirect(follow);
%>
			
<%@ include file="author_footer.jsp"%>

