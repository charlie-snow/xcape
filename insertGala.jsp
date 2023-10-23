<%
	xcaping.Gala gala = new xcaping.Gala();
	gala.getURLs("");
	String follow = "";
	
	gala.name = request.getParameter("name");
	if (request.getParameter("obligatory") != null) {
		gala.obligatory = "1";
	} else {
		gala.obligatory = "0";
	}
	gala.from_date = request.getParameter("from_date");
	gala.to_date = request.getParameter("to_date");
	
	if (gala.insert()) {
		follow = gala.returnURL;
	} else {
		follow = gala.errorURL+gala.error;
	}
    response.sendRedirect(follow);
%>