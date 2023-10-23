<% 
	manager = true;
	properties_manager = true;
	contracts_manager = true;
	contracts_operator = true;
%>

<%@ include file="author_header.jsp"%>

<%	
	xcaping.Contract contractB = new xcaping.Contract();
	xcaping.Item item = new xcaping.Item("0");
	
	String father = "";
	String bar = "";
%>
<%!
	public String getLink(xcaping.Item item2) {
		item2.getURLs(item2.id, item2.item_type);
		return("<a href='"+item2.itemURL+"'>"+item2.name+"</a>");
	}
%>

<table width="100%" border="0" cellspacing="0" cellpadding="2" bgcolor="#0000FF" class="browse">
<tr>
	<td bgcolor="#ACD2E4">
	<a href="index.jsp?content=item&item_type=0">
	-browse-&nbsp;&nbsp;
	</a>
	
<%
	if ((request.getParameter("contract_id") != null)) { 
			contractB = new xcaping.Contract();
			contractB.get(request.getParameter("contract_id"));
			bar = "<a href='index.jsp?content=contract&contract_id="+contractB.id+"'>"+contractB.name+"</a>";
			father = contractB.property;
			
			for (int i=5; i>0; i--) {
				item = new xcaping.Item(String.valueOf(i));
				item.get(father);
				father = item.father;
				bar = getLink(item)+" / "+bar;
			}
			out.print(bar);
	} else { 
		if (request.getParameter("item_type") != null) {
			String item_type = request.getParameter("item_type");
			String item_id = request.getParameter("item_id");
			father = item_id;
			for (int i=Integer.parseInt(item_type); i>0; i--) {
				item = new xcaping.Item(String.valueOf(i));
				item.get(father);
				father = item.father;
				bar = getLink(item)+" / "+bar;
			}
			out.print(bar);
		}
	}
%>
	</td>
	<td align="right">
	<form action="index.jsp?content=search" method="post">
	<input type="text" name="name" id="name">
	
	<select name="type" id="type" size="1">
		<option value="5">property</option>
		<option value="4">resort</option>
		<option value="3">area</option>
		<option value="2">region</option>
		<option value="1">country</option>
	</select>
	
	<input type="submit" name="search" id="search" value="search">
	</td>
</tr>
</form>
</table>

<%@ include file="author_footer.jsp"%>