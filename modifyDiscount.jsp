<%
	String discount_id = request.getParameter("discount_id");
	String name = request.getParameter("name");
	xcaping.Discount discount = new xcaping.Discount();
	discount.setData(name);
	discount.modify(discount_id);
	String redirectURL = "discountsList.jsp";
    response.sendRedirect(redirectURL);
%>