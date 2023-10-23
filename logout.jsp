<%
	
	session.setAttribute( "type", "0" );
	session.setAttribute( "user", "alien" );
	session.setAttribute( "user_id", "0" );

	String redirectURL = "index.html";
    response.sendRedirect(redirectURL);
%>
