<head>
<link rel="STYLESHEET" type="text/css" href="Xtyle.css">
</head>

<span class="small">  
<% 
for (e = contract.pricing.elements() ; e.hasMoreElements() ;) {
	priceline = (xcaping.Priceline)e.nextElement();
%>

<%= priceline.SQL %>
	
<% } %>
</span>   
		

