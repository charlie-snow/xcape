<%
	String gala_id = request.getParameter("gala_id");
	xcaping.Gala gala = new xcaping.Gala();
	gala.getURLs("");
	
	String follow = "";
	
	if (gala.delete(gala_id)) {
		follow = gala.returnURL;
	} else {
		follow =gala.errorURL+gala.error;
	}
    response.sendRedirect(follow);
%>
