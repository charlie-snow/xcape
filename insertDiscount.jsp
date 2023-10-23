<%
	String name = request.getParameter("name");
	xcaping.Discount discount = new xcaping.Discount();
	discount.setData(name);
	discount.insert();
	String redirectURL = "discountsList.jsp";
    response.sendRedirect(redirectURL);
%>