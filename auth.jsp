<%
	String name = request.getParameter("name");
	String password = request.getParameter("password");
	String type = "0";
	xcaping.Auth auth = new xcaping.Auth();
	auth.name = name;
	auth.password = password;
	
	String error = "error_logging.jsp?error=";
	String follow = "";	
	
	if (auth.getUser()) {
		if (auth.user.type.equals("0")) {
			session.setAttribute( "type", "alien" );
			session.setAttribute( "user", "not logged" );
			session.setAttribute( "user_id", "0" );
			follow = error+auth.error;
		} else {
			xcaping.UserType userType = new xcaping.UserType();
			userType.get(auth.user.type);
			session.setAttribute( "type", userType.name );
			session.setAttribute( "user", auth.user.name );
			session.setAttribute( "user_id", auth.user.id );
			follow = auth.next;
		}
	} else {
		follow = error+auth.error;
	}
	
	String redirectURL = follow;
    response.sendRedirect(redirectURL);
%>
