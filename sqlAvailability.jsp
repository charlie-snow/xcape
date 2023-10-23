<head>
<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>

<span class="small">  
<% 
for (e = contract.availabilities.elements() ; e.hasMoreElements() ;) {
	availability = (xcaping.Availability)e.nextElement();
%>
    	<%= availability.SQL %>

<% } %>
</span>   