<%
	String contract_id = request.getParameter("contract_id");
	String room_id = request.getParameter("room_id");
	
	xcaping.Contract contract = new xcaping.Contract();
	contract.deactivateRoom(room_id, contract_id);
	String redirectURL = "roomsActivation.jsp?contract_id="+contract_id;
    response.sendRedirect(redirectURL);
%>