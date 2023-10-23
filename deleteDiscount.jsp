<%
	String discount_id = request.getParameter("discount_id");
	xcaping.Discount discount = new xcaping.Discount();
	discount.delete(discount_id);
	String redirectURL = "discountsList.jsp";
    response.sendRedirect(redirectURL);
%>
