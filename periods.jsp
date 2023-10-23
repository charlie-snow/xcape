<%@ include file="contract_data.jsp"%>
<%@ include file="contract_data_extra.jsp"%>

<%	java.util.Enumeration e;
	
		// rooms
	
	java.util.Vector data_rooms = new java.util.Vector();
	xcaping.Lists list_rooms = new xcaping.Lists();
	
	data_rooms = list_rooms.getRoomsContract(contract.id, true);
	int rooms_count = data_rooms.size();
	xcaping.RoomPeriod roomPeriod = new xcaping.RoomPeriod();
	xcaping.Room room = new xcaping.Room();
	
		// discounts of the contract
	
	java.util.Vector data_discounts = new java.util.Vector();
	xcaping.Lists list_discounts = new xcaping.Lists();
	
	data_discounts = list_discounts.getDiscountsContract(contract_id);
	int discounts_count = data_discounts.size();
	xcaping.Discount discount = new xcaping.Discount();
	xcaping.DiscountPeriod discountPeriod = new xcaping.DiscountPeriod();
	
		// board bases of the contract
	
	java.util.Vector data_board_bases = new java.util.Vector();
	xcaping.Lists list_board_bases = new xcaping.Lists();
	
	data_board_bases = list_board_bases.getBoardBasesContract(contract_id);
	int board_bases_count = data_board_bases.size();
	xcaping.BoardBasis board_basis = new xcaping.BoardBasis();
	xcaping.BoardBasisPeriod board_basisPeriod = new xcaping.BoardBasisPeriod();
	
	String offer = "0";
	String text = "";
%>

<table border="0" cellspacing="0" cellpadding="0" align="center">
<%	 if (!finished && session.getAttribute("period_edit").equals("true")) {
		if (!generated) { %>
<tr>
	<td class="sub-title">
	Insert Period
	</td>
</tr>
<tr>
	<td>
	<%@ include file="formInsertPeriod.jsp"%>
	</td>
</tr>
<tr>
	<td></td>
</tr>		
<% } } %>

<tr>
	<td class="sub-title">Periods</td>
</tr>
<tr>
	<td>
	<%@ include file="periodsList.jsp"%>
	</td>
</tr>
</table>