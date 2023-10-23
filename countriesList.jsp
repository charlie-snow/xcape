<%
	xcaping.Lists list = new xcaping.Lists();
	xcaping.Country country = new xcaping.Country();
	java.util.Vector data = new java.util.Vector();
	data = list.getCountries();
	
	java.util.Enumeration e;
	e = data.elements() ;
	int i =  1;
%>


<table border="0" cellspacing="0" cellpadding="20" align="center">
<tr>
	<td>
	<%@ include file="80countries.jsp"%>
	</td>
	<td>
	<%@ include file="80countries.jsp"%>
	</td>
	<td valign="top">
	<%@ include file="80countries.jsp"%>
	</td>
</tr>
</table>