<% 	
	if (session.getAttribute("user") == null) { %>
		<%@ include file="access_denied.html"%>
<% } else { %>
<% 
	logged = !(session.getAttribute("type").equals("0"));
	authorised = false;
	if (administrator) { 
		authorised = authorised || session.getAttribute("type").equals("administrator");
	}
	if (manager) { 
		authorised = authorised || session.getAttribute("type").equals("manager");
	}
	if (properties_manager) { 
		authorised = authorised || session.getAttribute("type").equals("properties_manager");
	}
	if (contracts_manager) { 
		authorised = authorised || session.getAttribute("type").equals("contracts_manager");
	}
	if (contracts_operator) { 
		authorised = authorised || session.getAttribute("type").equals("contracts_operator");
	}
	if (sales) { 
		authorised = authorised || session.getAttribute("type").equals("sales");
	}
	authorised = logged && authorised;
	if (!authorised) { %>
		<%@ include file="access_denied.html"%>
<% 	} else { %>