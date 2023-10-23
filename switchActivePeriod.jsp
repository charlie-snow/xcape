<%
	String period_id = request.getParameter("period_id");
	String contract_id = request.getParameter("contract_id");
	String value = request.getParameter("value");
	String generated = request.getParameter("generated");
	String has_complementary = request.getParameter("has_complementary");
	
	xcaping.Period period = new xcaping.Period();
	period.setActive(period_id, value, generated.equals("true"), has_complementary.equals("true"));
	period.getURLs(contract_id);
	String follow = period.returnURL;
    response.sendRedirect(follow);
%>