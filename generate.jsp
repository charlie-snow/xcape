<%
	String contract_id = request.getParameter("contract_id");
	String item_id = request.getParameter("item_id");
	xcaping.Contract contract = new xcaping.Contract();
	contract.get(contract_id);
	String message = "";
	boolean ok = contract.generate(contract_id);
	
		if (ok) {
			contract.setGenerated(contract_id);
			message = "insert ok";
			if (!session.getAttribute("generate_sql_view").equals("true")) {
				// if the sql report isn't to be shown, sendRedirect exits the page
				response.sendRedirect("index.jsp?content=contract&contract_id="+contract_id);
			}
		} else {
			contract.unGenerate(contract_id);
			message = contract.error;
		}
		
		java.util.Enumeration e;
		xcaping.Availability availability = new xcaping.Availability();
		xcaping.Priceline priceline = new xcaping.Priceline();
%>

<div align="center"><%= message %></div>

<br><br>

<div align="center"><a href="index.jsp?content=contract&contract_id=<%= contract_id %>">return to contract</a></div>

<br><br>

<%@ include file="sqlAvailability.jsp"%>

<br><br>

<%@ include file="sqlPricing.jsp"%>
