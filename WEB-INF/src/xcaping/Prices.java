package xcaping;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Prices extends DBConnection{
	
	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert prices";
	
	private String period_id = "";
	private String contract_id = "";
	public Vector board_bases = new Vector();
	public Vector discounts = new Vector();
	public Vector rooms = new Vector();
	
	public Enumeration e;
	
	public void setPeriod(String period_id, String contract_id) {
		this.period_id = period_id;
		this.contract_id = contract_id;
	}
	
	public boolean validate(boolean offer, Period complementary) {
		Validation validation = new Validation();
		boolean valid = validation.validate(this, offer, complementary);
		if (valid == false) {
			this.error = validation.message;
		}
		return(valid);
	}
	
	public void getPrices() {
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		data = list.getBoardBasesContract(this.contract_id);
		BoardBasis board_basis = new BoardBasis();
		BoardBasisPeriod board_basisPeriod = new BoardBasisPeriod();
		
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			board_basis = (xcaping.BoardBasis)enum_data.nextElement();
			board_basisPeriod = new BoardBasisPeriod();
			board_basisPeriod.get(this.period_id, board_basis.id);
			board_bases.add(board_basisPeriod);
		}
		
		data = list.getDiscountsContract(this.contract_id);
		Discount discount = new Discount();
		DiscountPeriod discountPeriod = new DiscountPeriod();
		
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			discount = (xcaping.Discount)enum_data.nextElement();
			discountPeriod = new DiscountPeriod();
			discountPeriod.get(this.period_id, discount.id);
			discounts.add(discountPeriod);
		}
		
		data = list.getRoomsContract(contract_id, true);
		Room room = new Room();
		RoomPeriod roomPeriod = new RoomPeriod();
		
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			room = (xcaping.Room)enum_data.nextElement();
			
			roomPeriod = new RoomPeriod();
			roomPeriod.get(this.period_id, room.id);
			roomPeriod.name = room.name;
			rooms.add(roomPeriod);
		}
	}
	
	public void insert(boolean offer) {

		// old system: if the contract is generated, modify the prices on the pricelines, and the availability
		Contract contract = new Contract();
		contract.get(this.contract_id);
		if (contract.state.equals("active") || contract.state.equals("inactive")) {
			this.modifyPricelines(offer);
		}		
		
		// xcaping
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		data = list.getBoardBasesContract(this.contract_id);
		BoardBasis board_basis = new BoardBasis();
		BoardBasisPeriod board_basisPeriod = new BoardBasisPeriod();
		
		e = this.board_bases.elements();
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			board_basis = (xcaping.BoardBasis)enum_data.nextElement();
			board_basisPeriod =(xcaping.BoardBasisPeriod)e.nextElement();
			board_basisPeriod.period_id = this.period_id;
			board_basisPeriod.set();
		}
		
		data = list.getDiscountsContract(contract_id);
		Discount discount = new Discount();
		DiscountPeriod discountPeriod = new DiscountPeriod();
		
		e = this.discounts.elements();
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			discount = (xcaping.Discount)enum_data.nextElement();
			discountPeriod =(xcaping.DiscountPeriod)e.nextElement();
			discountPeriod.period_id = this.period_id;
			discountPeriod.set();		
		}
		
		data = list.getRoomsContract(contract_id, true);
		Room room = new Room();
		RoomPeriod roomPeriod = new RoomPeriod();
		e = this.rooms.elements();
		
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			room = (xcaping.Room)enum_data.nextElement();
			
			roomPeriod =(xcaping.RoomPeriod)e.nextElement();
			roomPeriod.period_id = this.period_id;
			roomPeriod.set();
		}
	}
	
	public void modifyPricelines(boolean offer) { // old system
	connect();
	try {
		Period period = new Period();
		period.get(this.period_id);
		Contract contract = new Contract();
		contract.get(period.contract);
		
		Validation validation = new Validation();
		String from_date = validation.getSQLDate(period.from_date);
		String to_date = validation.getSQLDate(period.to_date);
		
		SQL = this.SQLprefix+"select pricePeriodID " +
		"from "+this.tables_modifyer+"[PriceTBL] " +
		"where fromDate='"+from_date+"' AND toDate='"+to_date+"' " +
		"AND propID='"+contract.property+"' and supplement=0 AND "+
		"[activeMonday]='"+period.active_monday+"' AND "+
		"[activeTuesday]='"+period.active_tuesday+"' AND "+
		"[activeWednesday]='"+period.active_wednesday+"' AND "+
		"[activeThursday]='"+period.active_thursday+"' AND "+
		"[activeFriday]='"+period.active_friday+"' AND "+
		"[activeSaturday]='"+period.active_saturday+"' AND "+
		"[activeSunday]='"+period.active_sunday+"'";
		
		if (offer) {
			SQL = SQL + " and priceType=3";
		}
		
      	Statement sentencia = conexion.createStatement();
  		result = sentencia.executeQuery(SQL);
  		result.next();
		
  		String pricePeriodID = result.getString("pricePeriodID");
  		
		String name = "";
		String name2 = "";
		
		SQL = this.SQLprefix+"update "+this.tables_modifyer+"[PriceTBL] set ";
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		BoardBasisPeriod board_basisPeriod = new BoardBasisPeriod();
		
		for (enum_data = this.board_bases.elements() ; enum_data.hasMoreElements() ;) {
			board_basisPeriod =(xcaping.BoardBasisPeriod)enum_data.nextElement();
			switch (Integer.parseInt(board_basisPeriod.board_basis_id)) {
				case 1:  name = "selfCatering"; break;
				case 2:  name = "roomOnly"; break;
				case 3:  name = "bedBreakfast"; break;
				case 4:  name = "halfBoard"; break;
				case 5:  name = "fullBoard"; break;
				case 6:  name = "allInclusive"; break;
			}
			SQL = SQL+name+"='"+board_basisPeriod.price+"'";
			if (enum_data.hasMoreElements()) {
				SQL = SQL+", ";
			} else  {
				SQL = SQL+" ";
			}
		}
		
		DiscountPeriod discountPeriod = new DiscountPeriod();
		
		for (enum_data = this.discounts.elements() ; enum_data.hasMoreElements() ;) {
			discountPeriod =(xcaping.DiscountPeriod)enum_data.nextElement();
			switch (Integer.parseInt(discountPeriod.discount_id)) {
				case 1:  name = "disc1stChild"; name2 = "percentage1Child"; break;
				case 2:  name = "disc2ndChild"; name2 = "percentage2Child"; break;
				case 3:  name = "disc3rdChild"; name2 = "percentage3Child"; break;
				case 4:  name = "disc3rdAdult"; name2 = "percentage3Adult"; break;
				case 5:  name = "disc4thAdult"; name2 = "percentage4Adult"; break;
			}
			SQL = SQL+", "+name+"='"+discountPeriod.amount+"', "+name2+"='"+discountPeriod.percentage+"'";	
		}
			
		data = list.getRoomsContract(contract_id, true);
		Room room = new Room();
		RoomPeriod roomPeriod = new RoomPeriod();
		e = this.rooms.elements();
		
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			room = (xcaping.Room)enum_data.nextElement();
			
			roomPeriod =(xcaping.RoomPeriod)e.nextElement();
			String SQL2 = this.SQLprefix+"update "+this.tables_modifyer+"[RoomSupplementsTBL] " +
			"SET [RoomPrice]="+roomPeriod.price+", [pricePerUnitPerPerson]='"+roomPeriod.unit+"' " +
			"WHERE [pricePeriodID]="+pricePeriodID+" and [RoomID]="+room.id;
			sentencia = conexion.createStatement();
			sentencia.execute(SQL2);
			
			// modify the allotment and release from the availabilities
			String SQL3 = this.SQLprefix+"select idColumn " +
			"from "+this.tables_modifyer+"[AvailPropertiesTBL] " +
			"where fromDate='"+from_date+"' AND toDate='"+to_date+"' " +
			"AND propID='"+contract.property+"' and roomNameID='"+room.id+"'";
	      	sentencia = conexion.createStatement();
	  		result = sentencia.executeQuery(SQL3);
	  		result.next();
			
	  		String idColumn = result.getString("idColumn");
	  		
	  		Converter converter = new Converter();
	  		converter.modifyAvailability(idColumn, roomPeriod);			
		}
		
		SQL = SQL+"where pricePeriodID='"+pricePeriodID+"'";
		sentencia = conexion.createStatement();
  		sentencia.execute(SQL);
	} catch (Exception ex) {
	}
		disconnect();
	}	
}
