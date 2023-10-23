<%
	String contract_id = request.getParameter("contract_id");
	xcaping.Contract contract = new xcaping.Contract();
	contract.get(contract_id);
	xcaping.Currency currency = new xcaping.Currency();
	currency.get(contract.currency);
	String period_id = request.getParameter("period_id");
	String first_complementary = request.getParameter("first_complementary");
	String complementary_id = "";
	
	String output;
	java.text.SimpleDateFormat formatter;
	java.util.Date today = new java.util.Date();
	formatter = new java.text.SimpleDateFormat("dd MM yy");
	output = formatter.format(today);
	
	xcaping.Period period = new xcaping.Period();
	period.get(period_id);
	boolean last_complementary = first_complementary.equals("0");
	boolean needs_comp_offer = period.hasComplementary;
	xcaping.Period complementary = new xcaping.Period();
	if (last_complementary) {
		complementary_id = request.getParameter("complementary_id");
		complementary.get(complementary_id);
	}
	
	xcaping.Lists list_agent_groups = new xcaping.Lists();
	java.util.Vector data_agent_groups = new java.util.Vector();
	java.util.Enumeration enum_data_agent_groups;
	data_agent_groups = list_agent_groups.getAgents();
	xcaping.AgentGroup agent_group = new xcaping.AgentGroup();
	
	java.util.Enumeration e3;
	
	// ------------------------------ lists
	
	java.util.Enumeration e;
	
		// rooms
	
	java.util.Vector data_rooms = new java.util.Vector();
	xcaping.Lists list_rooms = new xcaping.Lists();
	
	data_rooms = list_rooms.getRoomsContract(contract.id, true);
	int rooms_count = data_rooms.size();
	xcaping.Room room = new xcaping.Room();
	
		// discounts of the contract
	
	java.util.Vector data_discounts = new java.util.Vector();
	xcaping.Lists list_discounts = new xcaping.Lists();
	
	data_discounts = list_discounts.getDiscountsContract(contract_id);
	int discounts_count = data_discounts.size();
	xcaping.Discount discount = new xcaping.Discount();
	
		// board bases of the contract
	
	java.util.Vector data_board_bases = new java.util.Vector();
	xcaping.Lists list_board_bases = new xcaping.Lists();
	
	data_board_bases = list_board_bases.getBoardBasesContract(contract_id);
	int board_bases_count = data_board_bases.size();
	xcaping.BoardBasis board_basis = new xcaping.BoardBasis();
	
	String active_days = "";
%>


<table border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td class="sub-title">
	Insert Offer
	</td>
</tr>
<tr>
	<td>
	<%@ include file="formInsertOffer.jsp"%>
	</td>
</tr>
</table>