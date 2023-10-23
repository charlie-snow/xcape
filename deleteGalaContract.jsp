<%
	String gala_id = request.getParameter("gala_id");
	String contract_id = request.getParameter("contract_id");
	xcaping.Contract contract = new xcaping.Contract();
	contract.get(contract_id);
	contract.subGala(gala_id);
	
	xcaping.Gala gala = new xcaping.Gala();
	gala.getURLs(contract_id);
	String follow = gala.returnContractURL;
    response.sendRedirect(follow);
%>