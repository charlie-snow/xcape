<%@ include file="author_header.jsp"%>

<script>
function abrir (pagina, w, h) {
	nueva = window.open(pagina, "ppl", 'top=200,left=300,title=ppl, menubar=no,width='+w+',height='+h)
}
</script>
<table width="100%" height="20" border="0" cellspacing="0" cellpadding="0">
<tr align="right" class="header">
	<td>
	<img src="images/user.gif" width="36" height="15" alt="" border="0">
	&nbsp;
	</td>
	<td>
	&nbsp;
	<% if (session.getAttribute("user") != null) { %>
	<%= session.getAttribute("user") %>
	<% } else { %>guest<% } %>
	&nbsp;
	</td>
	<td>&nbsp;[<a href="logout.jsp">logout</a>]</td>
	<td></td>
	<td width="65%" align="right">
	<%@ include file="menu.jsp"%>
	</td>
	<td>
	<img src="images/separator.gif" width="5" height="16" alt="" border="0">
	</td>
	<td>
	<a href="index.jsp">
	<img src="images/main_page.gif" alt="" width="140" height="16" border="0">
	</a>
	</td>
</tr>
<tr>
	<tr><img src="images/line.gif" width="980" height="3" alt="" border="0"></tr>
</tr>
</table>

<%@ include file="author_footer.jsp"%>