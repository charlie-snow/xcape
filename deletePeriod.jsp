<%
	String period_id = request.getParameter("period_id");
	String contract_id = request.getParameter("contract_id");
	String complementary = request.getParameter("complementary");
	xcaping.Period period = new xcaping.Period();
	period.getURLs(contract_id);
	period.delete(period_id, complementary, true);
    response.sendRedirect(period.returnURL);
%>
