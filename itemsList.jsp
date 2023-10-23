<%
	String item_id = request.getParameter("item_id");
	String item_type = request.getParameter("item_type");
	boolean countries = item_type.equals("0");
	
	xcaping.Lists list = new xcaping.Lists();
	xcaping.Item item = new xcaping.Item("0");
	java.util.Vector data = new java.util.Vector();
	data = list.getItems(item_id, item_type);
	java.util.Enumeration e;
	e = data.elements();
	
	int item_type_son = Integer.parseInt(item_type)+1;
	int items_column;
	if (data.size() > 13) {
		items_column = (data.size()/2);
	} else {
		items_column = data.size();
	}
	int j = 0;
	
	boolean property = (item_type.equals("4"));
	boolean active = true;
	boolean featured_resort = true;
	boolean featured_area = true;
%>
	
<table width="800" border="0" cellspacing="0" cellpadding="10" align="center">
<tr>

	<% if (!countries) { 
		if (session.getAttribute("edit_item").equals("true")) {
			item.getURLs(item_id, item_type); %>
	<tr>
		<td colspan="3" align="center"><a href="<%= item.insertURL %>">insert new</a></td>
	</tr>
	<%
		}
	 } else { %>
	<tr>
		<td colspan="3" align="center"><a href="index.jsp?content=item&item_id=1&item_type=1"> españa </a></td>
	</tr>
		
	<% } %>
	
	<% while (e.hasMoreElements()) { %>
	
	<td valign="top">

		<table width="450" border="0" cellspacing="2" cellpadding="4" align="center">
		<tr class="header">
			<td colspan="<% if (property) { out.print("6"); } else { out.print("4"); }%>" align="center">
			
			<%
			int item_number = Integer.parseInt(item_type);
			switch (item_number) {
				case 0: 
						out.print ("countries");
						break;
				case 1: 
						out.print ("regions");
						break;
				case 2: 
						out.print ("areas");
						break;
				case 3: 
						out.print ("resorts");
						break;
				case 4: 
						out.print ("properties");
						break;
				}
			%>
			
			</td>
				<% if (property && session.getAttribute("feature_property").equals("true")) { %>
				<td colspan="2" align="center">featured</td>
				<% } %>
		</tr>
		<tr class="header">
			<% if (countries) { %>
				<td>name</td>
			<% } else { 
				item.getURLs(item.id, String.valueOf(item_type_son)); %>
				<% if (property) { %>
					<td></td>
					<td>name</td>
					<td>rating</td>
					<td>id</td>
					<td></td>
					<td></td>
					<% if (session.getAttribute("feature_property").equals("true")) { %>
						<td>resort</td>
						<td>area</td>
					<% } %>
				<% } else { %>
					<td>name</td>
					<td>id</td>
					<td></td>
					<td></td>
				<% } %>
			<% } %>
		</tr>
		
		
		<%
			if (data.isEmpty()) {
		%>
		<tr>
			<td align="center">empty</td>
		</tr>
		<%
			} else {
				j = 0;
				while (e.hasMoreElements() && j<=items_column) {
					item = (xcaping.Item)e.nextElement();
					j++;
					if (property) {
						active = item.active.equals("1");
						featured_resort = item.featured_resort.equals("1");
						featured_area = item.featured_area.equals("1");
					}
		%>
		
		<tr>
			<% if (property) { 
				if (session.getAttribute("activate_property").equals("true")) {
					if (active) { %>
						<td>
						<a href="switchActiveProperty.jsp?property_id=<%= item.id %>&father_id=<%= item_id %>&value=0">
						<img src="images/active.gif" width="10" height="10" alt="" border="0"></a>
						</td>
					<% } else { %>
						<td>
						<a href="switchActiveProperty.jsp?property_id=<%= item.id %>&father_id=<%= item_id %>&value=1">
						<img src="images/inactive.gif" width="10" height="10" alt="" border="0"></a>
						</td>
					<% } 
				} else {
					if (active) { %>
						<td>
						<img src="images/active.gif" width="10" height="10" alt="" border="0">
						</td>
					<% } else { %>
						<td>
						<img src="images/inactive.gif" width="10" height="10" alt="" border="0">
						</td>
					<% } 
				}
			} %>				
			<td align="left"><a href="<%= item.itemURL %>"><%= item.name %></a></td>
			<% if (!countries) { 
				item.getURLs(item.id, String.valueOf(item_type_son)); %>
				<% if (property) { 
					xcaping.Rating rating = new xcaping.Rating();
					rating.get(item.rating);
				%>
				<td align="left"><%= rating.symble %></td>
				<% } %>
			<td><%= item.id %></td>
			<% if (session.getAttribute("edit_item").equals("true")) { %>
				<td><a href="<%= item.editURL %>&list=1">edit</a></td>
				<td><a href="<%= item.deleteURL %>">delete</a></td>
			<% } else { %>
				<td></td>
				<td></td>
			<% } %>
				<% if (property && session.getAttribute("feature_property").equals("true")) { %>
					<td align="center"><a href="switchFeaturedProperty.jsp?property_id=<%= item.id %>&father_id=<%= item_id %>&father_type=<%= item_type %>&resort=1
					<% if (featured_resort) { %>&featured=0"><img src="images/tick.gif" width="13" height="15" alt="" border="0">
					<% } else { %>&featured=1"><img src="images/cross.gif" width="15" height="15" alt="" border="0"><% } %>
					</a></td>
					<td align="center"><a href="switchFeaturedProperty.jsp?property_id=<%= item.id %>&father_id=<%= item_id %>&father_type=<%= item_type %>&resort=0
					<% if (featured_area) { %>&featured=0"><img src="images/tick.gif" width="13" height="15" alt="" border="0">
					<% } else { %>&featured=1"><img src="images/cross.gif" width="15" height="15" alt="" border="0"><% } %>
					</a></td>
				<% } %>
					
			<% } %>
		</tr>
		

<%		}
	}%>

		</table>

	</td>

	<% } %>

</tr>
</table>