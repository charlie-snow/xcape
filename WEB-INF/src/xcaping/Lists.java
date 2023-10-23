package xcaping;

import java.io.File;
import java.sql.*;
import java.util.Vector;
import xcaping.Data;

/**
 * <div align="center"><strong>lists generator</strong></div>
 * <p>description: this class has methods to get the list of any element on the system (items, users, board bases,...)
 * <br>- it also provides methods to search (obtaining a list too) some elements (items, photos)<br><br>
 * - the methods with no parameters return system lists</p>
 * @author carlos nieves lameiro
 * @version 1.2
 */
public class Lists extends DBConnection{

	private ResultSet result;
	
	private String SQL;
	private String cadena;
	public boolean more;
	private Object objeto;
		
	// ------ methods that take the info for each item directly from the database (without using object.get())
			
	/** obtains the list of different rooms the property has, selecting them from the extra rooms list
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @param property_id
	 * @return a vector of Room objects, one for each different room
	 */
	public Vector getRoomsProperty(String property_id) {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select  distinct RoomNameTBL.roomNameID, " +
			"RoomNameTBL.roomName " +
			"from RoomNameTBL " +
			"join ExtraBedsTBL " +
			"on RoomNameTBL.roomNameID=ExtraBedsTBL.roomNameID " +
			"join PropBedsTBL " +
			"on ExtraBedsTBL.extraBedsID = PropBedsTBL.extraBedsID " +
			"where PropBedsTBL.propID="+property_id;
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Room room = new Room();
      		while (more) {
      			room = new Room();
      			room.id = result.getString("roomNameID");
    			room.name = result.getString("roomName");
      			data.add(room);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}

	/** obtains the extra rooms list (different rooms plus the "extra" varations ("with extra bed")) of a property
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @param property_id
	 * @return vector of Room objects, one for each extra room
	 */
	public Vector getRoomsExtraProperty(String property_id) {
		connect();
		Vector data = new Vector();
		try {
			SQL = "SELECT ExtraBedsTBL.extraBedsID, propBedsID, extraBedsDesc " +
			"FROM ExtraBedsTBL, propBedsTBL " +
			"where propBedsTBL.extraBedsID = ExtraBedsTBL.extraBedsID " +
			"and propBedsTBL.propID = '"+property_id+"' " +
			"order by ExtraBedsTBL.extraBedsDesc";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Room room = new Room();
      		while (more) {
      			room = new Room();
      			room.id = result.getString("propBedsID");
    			room.name = result.getString("extraBedsDesc");
      			data.add(room);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}	
	
	/** obtains the system's extra rooms list
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @return vector of Room objects
	 */
	public Vector getRoomsExtra() {
		connect();
		Vector data = new Vector();
		try {
			SQL = "SELECT * FROM ExtraBedsTBL order by extraBedsDesc";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Room room = new Room();
      		while (more) {
      			room = new Room();
      			room.id = result.getString("extraBedsID");
    			room.name = result.getString("extraBedsDesc");
      			data.add(room);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}	
	
	/** obtains the system's board bases list
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @return vector of BoarBasis objects
	 */
	public Vector getBoardBases() {

		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[board_basis] order by (board_basis_id)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		BoardBasis board_basis = new BoardBasis();
      		while (more) {
      			board_basis = new BoardBasis();
      			board_basis.id = result.getString("board_basis_id");
    			board_basis.name = result.getString("name_board_basis");
      			data.add(board_basis);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** obtains the system's discounts list
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @return vector of Discount objects
	 */
	public Vector getDiscounts() {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[discount] order by (discount_id)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		Discount discount = new Discount();
      		while (more) {
      			discount = new Discount();
      			discount.id = result.getString("discount_id");
    			discount.name = result.getString("name_discount");
      			data.add(discount);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return(data);
	}
		
	/** lists the selected contract's board bases
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @param contract_id
	 * @return vector of BoardBasis objects
	 */
	public Vector getBoardBasesContract(String contract_id) {
		connect();
		Vector data = new Vector();
		ResultSet result2;
		Statement sentencia2;
		try {			
			SQL = "select board_basis_bb_con from "+this.tables_modifyer+"[board_basis_contract] " +
					"where contract_bb_con='"+contract_id+"' order by (board_basis_bb_con)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		BoardBasis board_basis = new BoardBasis();
      		while (more) {
      			board_basis = new BoardBasis();
      			
      			SQL = "select * from "+this.tables_modifyer+"[board_basis] " +
					"where board_basis_id='"+result.getString("board_basis_bb_con")+"'";
      			sentencia2 = conexion.createStatement();
      			result2 = sentencia2.executeQuery(SQL);
      			result2.next();
      			
      			board_basis.id = result2.getString("board_basis_id");
    			board_basis.name = result2.getString("name_board_basis");
      			data.add(board_basis);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the selected contract's discounts
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @param contract_id
	 * @return vector of Discount objects
	 */
	public Vector getDiscountsContract(String contract_id) {
		connect();
		Vector data = new Vector();
		ResultSet result2;
		Statement sentencia2;
		try {			
			SQL = "select discount_disc_con from "+this.tables_modifyer+"[discount_contract] " +
					"where contract_disc_con='"+contract_id+"' order by (discount_disc_con)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		Discount discount = new Discount();
      		while (more) {
      			discount = new Discount();
      			
      			SQL = "select * from "+this.tables_modifyer+"[discount] " +
					"where discount_id='"+result.getString("discount_disc_con")+"'";
      			sentencia2 = conexion.createStatement();
      			result2 = sentencia2.executeQuery(SQL);
      			result2.next();
      			
      			discount.id = result2.getString("discount_id");
    			discount.name = result2.getString("name_discount");
      			data.add(discount);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the system's property ratings
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @return vector of Rating objects
	 */
	public Vector getRatings() {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[PropertyRatingTBL] order by (ratingID)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Rating rating = new Rating();
      		while (more) {
      			rating = new Rating();
      			rating.id = result.getString("ratingID");
    			rating.name = result.getString("rating");
          		rating.symble = result.getString("symble");
      			data.add(rating);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the system's currencies list
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @return vector of Currency objects
	 */
	public Vector getCurrencies() {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[CurrencyExchangeTBL] order by (exchangeID)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Currency currency = new Currency();
      		while (more) {
      			currency = new Currency();
      			currency.id = result.getString("exchangeID");
    			currency.name = result.getString("currency");
          		currency.symbol = result.getString("symble");
          		//currency.exchange = result.getString("exchange");
      			data.add(currency);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the system's markups
	 * <br><br>- this method takes the info for each item directly from the database, using a SQL sentence
	 * @return vector of Markup objects
	 */
	public Vector getMarkups() {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[markup] order by (markup_id)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Markup markup = new Markup();
      		while (more) {
      			markup = new Markup();
      			markup.id = result.getString("markup_id");
    			markup.name = result.getString("name_markup");
          		markup.markup = result.getString("markup");
      			data.add(markup);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	// --------------------------------------------------------------------------------
	
	// -------------------- methods that take the info for each item using object.get()
	
	/** obtains the item's sub-items list
	 * <br><br>- this method takes the info for each item obtaining first the id's list, and getting the objects
	 * using the Item.get(id) method
	 * @param item_id the item's id
	 * @param item_type the type (country, region, area or resort)
	 * @return vector of Item objects
	 */
	public Vector getItems(String item_id, String item_type) {
		connect();
		Data names = new Data();
		int item_sons = Integer.parseInt(item_type)+1;
		String item_type_temp = String.valueOf(item_sons);
		names.getNames(item_type_temp);
		Vector data = new Vector();
		try {
			SQL = "select * from "+names.table_name;
			if (!item_type_temp.equals("1")) {
				SQL = SQL + " where "+names.father_name+"ID='"+item_id+"'";
			}
			SQL = SQL + " order by ("+names.name+"Name)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Item item = new Item(item_type_temp);
      		while (more) {
      			item = new Item(item_type_temp);
      			item.get(result.getString(names.name+"ID"));
      			data.add(item);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the property's contract list
	 * <br><br>- this method takes the info for each contract obtaining first the id's list, and getting the objects
	 * using the Contract.get(id) method
	 * @param property_id
	 * @return vector of Contract objects
	 */
	public Vector getContracts(String property_id) {
		
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[contract] where property_contract='"+property_id+"' order by from_date_contract";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		Contract contract = new Contract();
      		while (more) {
      			contract = new Contract();
      			contract.get(result.getString("contract_id"));
      			data.add(contract);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the contract's periods list
	 * <br><br>- this method takes the info for each period obtaining first the id's list, and getting the objects
	 * using the Period.get(id) method
	 * @param contract_id
	 * @return vector of Period objects
	 */
	public Vector getPeriods(String contract_id) {

		connect();
		Vector data = new Vector();
		try {			
			SQL = "select * from "+this.tables_modifyer+"[period] where contract_period='"
				+contract_id+"' order by from_date_period, to_date_period, offer_period";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		Period period = new Period();
      		boolean next_complementary = true;
      		while (more) {
      			period = new Period();
      			period.get(result.getString("period_id"));
      			if (!next_complementary) {
      				period.firstComplementary = false;
      				next_complementary = true;
      			} else {
      				if (period.hasComplementary) {
      					period.firstComplementary = true;
          				next_complementary = false;
          			}
      			}
      			data.add(period);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** gets the system's galas list. when getting the galas list that can be attached to a contract 
	 * (contract_id <> "0"), only the galas with dates inside the contracts period are listed
	 * <br><br>- this method takes the info for each gala obtaining first the id's list, and getting the objects
	 * using the Gala.get(id) method
	 * @param contract_id - "0" to get all the system's galas list / - >"0" to get the galas that can be 
	 * attached to this contract 
	 * @return vector of Galas objects
	 */
	public Vector getGalas(String contract_id) {
		connect();
		Vector data = new Vector();
		Validation validation = new Validation();
		try {
			SQL = "select * from "+this.tables_modifyer+"[gala] order by (gala_id)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		Gala gala = new Gala();
      		while (more) {
      			gala = new Gala();
          		gala.get(result.getString("gala_id"));
          		if (!contract_id.equals("0")) {
          			if (validation.inContract(gala, contract_id)) {
              			data.add(gala);
              		}
          		} else {
          			data.add(gala);
          		}
          		more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
		
	/** lists the contract's galas list (with prices)
	 * @param contract_id
	 * @return vector of Gala objects
	 */
	/*public Vector getGalasContract(String contract_id) {
		
		connect();
		Vector data = new Vector();
		Gala gala = new Gala();
		try {			
			SQL = "select * from "+this.tables_modifyer+"[gala_contract] " +
			"where contract_gala_contract='"+contract_id+"' order by (gala_gala_contract)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		while (more) {
      			gala = new Gala();
      			gala.get(result.getString("gala_gala_contract"));
      			gala.id = result.getString("gala_gala_contract");
      			gala.price = result.getString("price_gala_contract");
      			gala.child_discount = result.getString("child_discount");
      			gala.adult_discount = result.getString("adult_discount");
      			gala.obligatory = result.getString("obligatory_gala_contract");
      			data.add(gala);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}*/
	
	/** lists the contract's add-ons list
	 * @param contract_id
	 * @return vector of AddOn objects
	 */
	public Vector getAddOnsContract(String contract_id) {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select add_on_id from "+this.tables_modifyer+"[add_on_contract] " +
			"where contract_add_on='"+contract_id+"' order by (add_on_id)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	AddOn add_on = new AddOn();
      		while (more) {
      			add_on = new AddOn();
          		add_on.get(result.getString("add_on_id"));
      			data.add(add_on);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** obtains the contract's stop sales list
	 * <br><br>- this method takes the info for each stop sale obtaining first the id's list, and getting the objects
	 * using the StopSale.get(id) method
	 * @param contract_id
	 * @return vector of StopSale objects
	 */
	public Vector getStopSalesProperty(String property_id) {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[stop_sale] " +
			"where property_stop_sale='"+property_id+"' order by (stop_sale_id)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	StopSale stop_sale = new StopSale();
      		while (more) {
      			stop_sale = new StopSale();
          		stop_sale.get(result.getString("stop_sale_id"));
      			data.add(stop_sale);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the system's user list
	 * <br><br>- this method takes the info for each user obtaining first the id's list, and getting the objects
	 * using the User.get(id) method
	 * @return vector of User objects
	 */
	public Vector getUsers() {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[user] order by (user_id)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	User user = new User();
      		while (more) {
      			user = new User();
          		user.get(result.getString("user_id"));
      			data.add(user);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the system's suppliers list
	 * <br><br>- this method takes the info for each supplier obtaining first the id's list, and getting the objects
	 * using the Supplier.get(id) method
	 * @return vector of User objects
	 */
	public Vector getSuppliers() {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from SuppliersTBL order by (suppliersName)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Supplier supplier = new Supplier();
      		while (more) {
      			supplier = new Supplier();
        		supplier.get(result.getString("supplierID"));
      			data.add(supplier);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** obtain the list of rooms for the period with their price
	 * @param period_id
	 * @return vector of RoomPeriod objects
	 */
	public Vector getRoomsPeriod(String period_id) {
		connect();
		Vector data = new Vector();
		ResultSet result2;
		Statement sentencia2;
		try {			
			SQL = "select * from "+this.tables_modifyer+"[room_period] " +
			"where period_rp='"+period_id+"' order by (room_rp)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		RoomPeriod roomPeriod = new RoomPeriod();
      		while (more) {
      			roomPeriod = new RoomPeriod();    			
    			roomPeriod.get(result.getString("period_rp"), result.getString("room_rp"));
      			data.add(roomPeriod);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** obtains the contract's allocations list
	 * <br><br>- this method takes the info for each allocation obtaining first the id's list, and getting the objects
	 * using the Allocation.get(id) method
	 * @param contract_id
	 * @return vector of Allocation objects
	 */
	public Vector getAllocations(String contract_id) {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[allocation] " +
			"where contract_allocation='"+contract_id+"' order by (allocation_id)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Allocation allocation = new Allocation();
      		while (more) {
      			allocation = new Allocation();
          		allocation.get(result.getString("allocation_id"));
      			data.add(allocation);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
//	 -----------------------------------------------------------------------
	
	/** list the availabilities on the old system for the property, between the start and the end of the contract
	 * @param property_id
	 * @param contract_id
	 * @return vector of Availability objects
	 */
	public Vector getAvailabilities(String property_id, String contract_id) {
		connect();
		Vector data = new Vector();
		try {
			Contract contract = new Contract();
			String from_date = "";
			String to_date = "";
			
			if (!contract_id.equals("0")) {
				contract.get(contract_id);
				
				Validation validation = new Validation();
				from_date = validation.getSQLDate(contract.from_date);
				to_date = validation.getSQLDate(contract.to_date);
			}
			
			if (contract_id.equals("0")) {
				SQL = "select * from AvailPropertiesTBL " +
				"where propID="+property_id;
			} else {
				SQL = this.SQLprefix+"select * from AvailPropertiesTBL " +
				"where propID="+contract.property+" and " +
				"fromDate between '"+from_date+"' AND '"+to_date+"' " +
				"AND fromDate!='"+to_date+"'";
			}
			
			SQL = SQL + " order by freeSale desc, fromDate, toDate, roomNameID";
			
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	Availability availability = new Availability();
          	more = result.next();
          	
          	Formatter formatter = new Formatter();
        	
      		while (more) {
      			availability = new Availability();
      			
      			availability.idColumn = result.getString("idColumn");
      			availability.fromDate =formatter.formatDate(result.getString("fromDate"));
				availability.toDate =formatter.formatDate(result.getString("toDate"));
				availability.roomNameID = result.getString("roomNameID");
				availability.allocation = result.getString("allocation");
				availability.freeSale = result.getString("freeSale");
				availability.releasePeriod = result.getString("releasePeriod");
				availability.minimumStay = result.getString("minimumStay");
				availability.propID = result.getString("propID");
				availability.supplier = result.getString("supplier");
				availability.active = result.getString("active");
				availability.stopStart = result.getString("stopStart");
				availability.editDate =formatter.formatDate(result.getString("editDate"));
				availability.editBy = result.getString("editBy");
				
      			data.add(availability);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
		
	/** list the pricelines on the old system for the property, between the start and the end of the contract
	 * @param property_id
	 * @param contract_id
	 * @return vector of Priceline objects
	 */
	public Vector getPricelines(String property_id, String contract_id, boolean history) {
		connect();
		Vector data = new Vector();
		String table = "";
		if (history) {
			table = "HistoryPriceTBL";
		} else {
			table = "PriceTBL";
		}
		try {			
			Contract contract = new Contract();
			String from_date = "";
			String to_date = "";
			
			if (!contract_id.equals("0")) {
				contract.get(contract_id);
				
				Validation validation = new Validation();
				from_date = validation.getSQLDate(contract.from_date);
				to_date = validation.getSQLDate(contract.to_date);
			}
			
			if (contract_id.equals("0")) {
				SQL = "select * from "+table+
				" where propID="+property_id+" ";
			} else {
				SQL = this.SQLprefix+"select * from "+table+
				" where propID="+contract.property+" and " +
				"fromDate between '"+from_date+"' AND '"+to_date+"' ";
			}
			SQL = SQL+"order by priceType, supplement, fromDate, toDate, pricePeriodID";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	Priceline priceline = new Priceline();
          	more = result.next();
        	
        	Formatter formatter = new Formatter();
        	
      		while (more) {
      			priceline = new Priceline();
      			
      			priceline.pricePeriodID = result.getString("pricePeriodID");
      			priceline.fromDate =formatter.formatDate(result.getString("fromDate"));
      			priceline.toDate =formatter.formatDate(result.getString("toDate"));
      			priceline.propID = result.getString("propID");
      			priceline.introDate =formatter.formatDate(result.getString("introDate"));
      			priceline.supplierID = result.getString("supplierID");
      			priceline.agentGroup = result.getString("agentGroup");
      			priceline.exchangeID = result.getString("exchangeID");
      			priceline.markup = formatter.formatPercentage(result.getString("markup"));
      			priceline.priceType = result.getString("priceType");
      			
      			priceline.selfCatering = formatter.formatCurrency(result.getString("selfCatering"));
      			priceline.roomOnly = formatter.formatCurrency(result.getString("roomOnly"));
      			priceline.bedBreakfast = formatter.formatCurrency(result.getString("bedBreakfast"));
      			priceline.halfBoard = formatter.formatCurrency(result.getString("halfBoard"));
      			priceline.fullBoard = formatter.formatCurrency(result.getString("fullBoard"));
      			priceline.allInclusive = formatter.formatCurrency(result.getString("allInclusive"));
      			
      			priceline.percentage1Child = result.getString("percentage1Child");
      			priceline.percentage2Child = result.getString("percentage2Child");
      			priceline.percentage3Child = result.getString("percentage3Child");
      			priceline.percentage3Adult = result.getString("percentage3Adult");
      			priceline.percentage4Adult = result.getString("percentage4Adult");
      			
      			if (priceline.percentage1Child.equals("1")) {
      				priceline.disc1stChild = formatter.formatPercentage(result.getString("disc1stChild"));
      			} else {
      				priceline.disc1stChild = formatter.formatCurrency(result.getString("disc1stChild"));
      			}
      			if (priceline.percentage2Child.equals("1")) {
      				priceline.disc2ndChild = formatter.formatPercentage(result.getString("disc2ndChild"));
      			} else {
      				priceline.disc2ndChild = formatter.formatCurrency(result.getString("disc2ndChild"));
      			}
      			if (priceline.percentage3Child.equals("1")) {
      				priceline.disc3rdChild = formatter.formatPercentage(result.getString("disc3rdChild"));
      			} else {
      				priceline.disc3rdChild = formatter.formatCurrency(result.getString("disc3rdChild"));
      			}
      			if (priceline.percentage3Adult.equals("1")) {
      				priceline.disc3rdAdult = formatter.formatPercentage(result.getString("disc3rdAdult"));
      			} else {
      				priceline.disc3rdAdult = formatter.formatCurrency(result.getString("disc3rdAdult"));
      			}
      			if (priceline.percentage4Adult.equals("1")) {
      				priceline.disc4thAdult = formatter.formatPercentage(result.getString("disc4thAdult"));
      			} else {
      				priceline.disc4thAdult = formatter.formatCurrency(result.getString("disc4thAdult"));
      			}      			
      			
      			priceline.activeMonday = result.getString("activeMonday");
      			priceline.activeTuesday = result.getString("activeTuesday");
      			priceline.activeWednesday = result.getString("activeWednesday");
      			priceline.activeThursday = result.getString("activeThursday");
      			priceline.activeFriday = result.getString("activeFriday");
      			priceline.activeSaturday = result.getString("activeSaturday");
      			priceline.activeSunday = result.getString("activeSunday");
      			
      			priceline.minStayTrig = result.getString("minStayTrig");
      			priceline.active = result.getString("active");
      			priceline.commissionable = result.getString("commissionable");
      			priceline.supplement = result.getString("supplement");
      			priceline.arrivalMonday = result.getString("arrivalMonday");
      			priceline.arrivalTuesday = result.getString("arrivalTuesday");
      			priceline.arrivalWednesday = result.getString("arrivalWednesday");
      			priceline.arrivalThursday = result.getString("arrivalThursday");
      			priceline.arrivalFriday = result.getString("arrivalFriday");
      			priceline.arrivalSaturday = result.getString("arrivalSaturday");
      			priceline.arrivalSunday = result.getString("arrivalSunday");
      			
      			priceline.salesPeriod = result.getString("salePeriod");
      			priceline.childAge = result.getString("childAge");
      			priceline.editBy = result.getString("editBy");
      			priceline.refNumber = result.getString("refNumber");
      			priceline.contractGuarantee = result.getString("contractGuarantee");
      			priceline.infantAge = result.getString("infantAge");
      			
    			priceline.rooms = getRoomSupplements(priceline.pricePeriodID);
      			
      			data.add(priceline);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	private Vector getRoomSupplements(String period_id) {
		connect();
		Vector rooms = new Vector();
		
		try {
			SQL = "SELECT * FROM "+this.tables_modifyer+"[RoomSupplementsTBL] " +
				"where pricePeriodID="+period_id+" order by RoomID";
          	Statement sentencia = conexion.createStatement();
      		ResultSet result = sentencia.executeQuery(SQL);
      		more = result.next();
          	
      		Formatter formatter = new Formatter();
      		Room room = new Room();
        	RoomSupplement room_supplement = new RoomSupplement();
      		while (more) {
      			room_supplement = new RoomSupplement();
      			room_supplement.RoomID = result.getString("RoomID");
      			room.get(room_supplement.RoomID);
      			room_supplement.name = room.name;
      			room_supplement.RoomPrice = formatter.formatCurrency(result.getString("RoomPrice"));
      			room_supplement.pricePerUnitPerPerson = result.getString("pricePerUnitPerPerson");
				
      			rooms.add(room_supplement);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
		disconnect();
		return (rooms);
	}
	
	/** obtains the list of active properties that match the text string entered on the search box; 
	 * matching the property name or the property id 
	 * @param name the text string to search for
	 * @return vector of Property objects, with the property basic data, plus the area and resort
	 */
	public Vector searchProperties(String name) {
		connect();
		Vector data = new Vector();
		boolean is_integer = true;
		try {
			int integer = Integer.parseInt(name);
      	} catch (Exception ex) {
      		is_integer = false;
      	}
		
		try { 
			SQL = "SELECT dbo.Property.propertyID, dbo.Property.propertyName, " +
					"dbo.PropertyRatingTBL.symble, dbo.Resort.resortID, dbo.Resort.resortName, " +
					"dbo.Area.areaID, dbo.Area.areaName " +
					"FROM dbo.Property " +
					"INNER JOIN dbo.Resort ON dbo.Property.resortID = dbo.Resort.resortID " +
					"INNER JOIN dbo.Area ON dbo.Resort.areaID = dbo.Area.areaID " +
					"INNER JOIN dbo.PropertyRatingTBL ON dbo.Property.ratingID = dbo.PropertyRatingTBL.ratingID " +
					"WHERE (dbo.Property.active = 1) ";
			if (is_integer) {
				SQL = SQL + "and (dbo.Property.propertyID="+name+")";
			} else {
				SQL = SQL + "and (dbo.Property.propertyName LIKE '%"+name+"%')";
			}
			Statement sentencia = conexion.createStatement();
      		ResultSet result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Property property = new Property();
      		while (more) {
      			property = new Property();
      			property.id = result.getString("propertyID");
    			property.name = result.getString("propertyName");
    			property.rating = result.getString("symble");
    			property.resort_id = result.getString("resortID");
    			property.resort_name = result.getString("resortName");
    			property.area_id = result.getString("areaID");
    			property.area_name = result.getString("areaName");
      			data.add(property);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** obtains the list of items that match the text string entered on the search box; 
	 * matching the item name or the item id 
	 * @param name the text string to search for
	 * @param item_type
	 * @return vector of Item objects with the item's basic data
	 */
	public Vector searchItems(String name, String item_type) {
		connect();
		Vector data = new Vector();
		Data names = new Data();
		names.getNames(item_type);
		boolean is_number = false;
		int i = 0;
		try	{
		  i = Integer.parseInt(name);
		  is_number = true;
		}
		catch(NumberFormatException e) {
		}
		
		try {			
			SQL = "select "+names.name+"ID, "+names.name+"Name from "+names.name+" ";
			if (is_number) {
				SQL = SQL+"where ("+names.name+"ID ="+name+")";
			} else {
				SQL = SQL+"where ("+names.name+"Name LIKE '%"+name+"%')";
			}
			Statement sentencia = conexion.createStatement();
      		ResultSet result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	Item item = new Item(item_type);
      		while (more) {
      			item = new Item(item_type);
      			item.id = result.getString(names.name+"ID");
      			item.name = result.getString(names.name+"Name");    			
      			data.add(item);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the contract's property rooms, and if they are active or inactive for this contract
	 * @param contract_id
	 * @return vector of Room objects, and if they are active or inactive
	 */
	public Vector getRoomsContract(String contract_id, boolean active_rooms) {
		connect();
		Contract contract = new Contract();
		contract.get(contract_id);
		Vector data = new Vector();
		try {
			SQL = "select  distinct RoomNameTBL.roomNameID, " +
			"RoomNameTBL.roomName " +
			"from RoomNameTBL " +
			"join ExtraBedsTBL " +
			"on RoomNameTBL.roomNameID=ExtraBedsTBL.roomNameID " +
			"join PropBedsTBL " +
			"on ExtraBedsTBL.extraBedsID = PropBedsTBL.extraBedsID " +
			"where PropBedsTBL.propID="+contract.property;
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	        	
        	Room room = new Room();
        	boolean deactivated;
      		while (more) {
      			room = new Room();
      			room.id = result.getString("roomNameID");
    			room.name = result.getString("roomName");
    			deactivated = contract.isDeactivatedRoomContract(room.id, contract_id);
    			if (deactivated) {
    				room.active = false;
    			} else {
    				room.active = true;
    			}
    			
    			if (active_rooms) {
    				if (!deactivated) {
    					data.add(room);
        			} 
    			} else {
    				data.add(room);
    			}    			
      			
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lis all the system user types
	 * @return vector of UserType objects
	 */
	public Vector getUserTypes() {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from "+this.tables_modifyer+"[user_type] order by (user_type_id)";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
        	UserType userType = new UserType();
      		while (more) {
      			userType = new UserType();
      			userType.id = result.getString("user_type_id");
    			userType.name = result.getString("name_user_type");
      			data.add(userType);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** returns the list of photo files that are on the "activate" folder, on the photos path
	 * @return vector of Photo objects
	 */
	public Vector getPhotosToActivate() {

		File dir = new File(this.default_photos_path+"activate");
		//File dir = new File("/");
		String[] children = dir.list();
		Vector data = new Vector();
		Photo photo = new Photo();
		for (int i=0; i<children.length; i++) {
			photo = new Photo();
			photo.file = children[i];
			data.add(photo);
		}
      	return (data);
	}
	
	/** returns the item's photos list and data
	 * @param item_id
	 * @param item_type
	 * @return vector of Photo objects
	 */
	public Vector getPhotos(String item_id, String item_type) {

		connect();
		Vector data = new Vector();
		PhotoItem photo_item = new PhotoItem();
		Data names = new Data();
		names.getNames(item_type);
		try {
			SQL = "select PhotosTBL.PhotosID, ";
			if (item_type.equals("5")) { SQL = SQL + "PhotoHeading, "; }
			SQL = SQL + "PhotoDescription, PhotoOrder, PhotosFiles " +
			"from "+names.name+names.photos_table_name+" inner join " +
			"PhotosTBL on "+names.name+"ID="+item_id+" " +
			"and PhotosTBL.PhotosID="+names.name+names.photos_table_name+".PhotosID " +
			"order by PhotosFiles";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
      		while (more) {
      			photo_item = new PhotoItem();
      			photo_item.photo = result.getString("PhotosID");
				photo_item.item = item_id;
				if (item_type.equals("5")) { 
					photo_item.heading = result.getString("PhotoHeading");
				}
				photo_item.description = result.getString("PhotoDescription");
				photo_item.order = result.getString("PhotoOrder");
        		photo_item.file = result.getString("PhotosFiles");
      			data.add(photo_item);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** returns all the photos from the system - deprecated
	 * @return vector of Photo objects
	 */
	public Vector getPhotos() { // 

		connect();
		Vector data = new Vector();
		String name = "";
		Photo photo = new Photo();
		try {
			SQL = "select * from PhotosTBL order by PhotosFiles";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
      		while (more) {
      			photo = new Photo();
        		photo.get(result.getString("PhotosID"));
      			data.add(photo);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** searches photos on the system that match the name, or belong to the group identified by the name, so this
	 * photos can be added to the item 
	 * @param name it can be a filename (xx-xx.jpg) or a groupname (xxx)
	 * @return a Vector of Photo objects
	 */
	public Vector searchPhotos(String name) {
		connect();
		Vector data = new Vector();
		Photo photo = new Photo();
		boolean one_photo = false;
		
		if (name.indexOf("-") == -1) {
			one_photo = false;
		} else {
			one_photo = true;
		}
		try {
			SQL = "select * from PhotosTBL where ";
			if (one_photo) {
				SQL = SQL + "PhotosFiles like '"+name+".%'";
			} else {
				SQL = SQL + "(PhotosFiles like '"+name+"-%')";
			}
			SQL = SQL + " order by PhotosFiles";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
      		while (more) {
      			photo = new Photo();
        		photo.get(result.getString("PhotosID"));
      			data.add(photo);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** searches the photos from the group that are attached to the item, for deleting all the group
	 * @param group the group name
	 * @param item_id
	 * @param item_type
	 * @return a Vector of Photo objects
	 */
	public Vector searchPhotos(String group, String item_id, String item_type) {
		connect();
		Data names = new Data();
		names.getNames(item_type);
		Vector data = new Vector();
		PhotoItem photo_item = new PhotoItem();
		Photo photo = new Photo();
		
		try {
			SQL = "select * from PhotosTBL "+
			"inner join "+names.name+"PhotoSelectTBL " +
			"on PhotosTBL.PhotosID="+names.name+"PhotoSelectTBL.PhotosID " +
			"where ("+names.name+"ID="+item_id+") and (PhotosFiles like '"+group+"-%') order by PhotosFiles";
			
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
      		while (more) {
      			photo_item = new PhotoItem();
      			photo_item.photo = result.getString("PhotosID");
				photo_item.item = item_id;
				if (item_type.equals("5")) { 
					photo_item.heading = result.getString("PhotoHeading");
				}
				photo_item.description = result.getString("PhotoDescription");
				photo_item.order = result.getString("PhotoOrder");
      			photo = new Photo();
        		photo.get(photo_item.photo);
        		photo_item.file = photo.file;
      			data.add(photo_item);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** lists the system's property photo headings
	 * @return vector of PropertyPhotoHeading objects
	 */
	public Vector getPropertyPhotoHeadings() {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from PropertyPhotoHeadingTBL order by (PhotoHeading)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		PropertyPhotoHeading property_photo_heading = new PropertyPhotoHeading();
      		while (more) {
      			property_photo_heading  = new PropertyPhotoHeading();
      			property_photo_heading.id = result.getString("PhotoHeadingID");
      			property_photo_heading.heading = result.getString("PhotoHeading");
      			data.add(property_photo_heading);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}

	/** lists the system's description headings for the selected item type (country, region, area or resort)
	 * @param item_type
	 * @return vector of DescriptionHeading objects
	 */
	public Vector getDescriptionHeadings(String item_type) {
		connect();
		Data names = new Data();
		names.getNames(item_type);
		Vector data = new Vector();
		try {
			SQL = "select * from "+names.name+"HeadingTBL order by ("+names.name+"OrderBy)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		DescriptionHeading description_heading = new DescriptionHeading("0");
      		while (more) {
      			description_heading  = new DescriptionHeading("0");
      			description_heading.id = result.getString(names.name+"HeadingID");
      			description_heading.heading = result.getString(names.name+"Heading");
      			description_heading.order = result.getString(names.name+"OrderBy");
      			data.add(description_heading);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	/** obtains the list and data of the descriptions existing for this item
	 * @param item_id
	 * @param item_type
	 * @return vector of Description objects
	 */
	public Vector getDescriptions(String item_id, String item_type) {
		connect();
		Vector data = new Vector();
		Description description = new Description("0", "0");
		Data names = new Data();
		names.getNames(item_type);
		try {
			SQL="select "+names.name+"DescriptionID, "+names.name+names.descriptions_table_name+"."+names.name+"HeadingID, "
			+names.name+"Description, "+names.name+"Activate " +
			"from "+names.name+names.descriptions_table_name+" inner join "+names.name+names.headings_table_name
			+" on "+names.name+"TBLid="+item_id+" " +
			"and "+names.name+names.headings_table_name+"."+names.name+"HeadingID="+names.name+names.descriptions_table_name+"."+names.name+"HeadingID " +
			"order by "+names.name+"OrderBy";
			
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
      		while (more) {
      			description = new Description("0", "0");
      			description.id = result.getString(names.name+"DescriptionID");
      			description.heading = result.getString(names.name+"HeadingID");
      			description.description = result.getString(names.name+"Description");
      			description.active = result.getString(names.name+"Activate");
      			data.add(description);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      		int i = 1;
      	}
      	disconnect();
      	return (data);
	}
	
	/** obtains the list of agent groups on the system
	 * @return vector of AgentGroup objects
	 */
	public Vector getAgents() {
		connect();
		Vector data = new Vector();
		AgentGroup agent_group = new AgentGroup();
		try {
			SQL="SELECT * FROM [dbo].[AgentGroupTBL]";
			
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
      		while (more) {
      			agent_group = new AgentGroup();
      			agent_group.get(result.getString("groupID"));
      			data.add(agent_group);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	//-----------------------------------------------------------------------------------
	
	public Vector getAvailabilities(String property_id) {
		connect();
		Vector data = new Vector();
		try {
			SQL = "select * from AvailPropertiesTBL " +
			"where propID="+property_id+" order by freeSale desc, fromDate, toDate, roomNameID";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
          	Formatter formatter = new Formatter();
        	Availability availability = new Availability();
      		while (more) {
      			availability = new Availability();
      			
      			availability.idColumn = result.getString("idColumn");
      			availability.fromDate =formatter.formatDate(result.getString("fromDate"));
    			availability.toDate =formatter.formatDate(result.getString("toDate"));
				availability.roomNameID = result.getString("roomNameID");
				availability.allocation = result.getString("allocation");
				availability.freeSale = result.getString("freeSale");
				availability.releasePeriod = result.getString("releasePeriod");
				availability.minimumStay = result.getString("minimumStay");
				availability.propID = result.getString("propID");
				availability.supplier = result.getString("supplier");
				availability.active = result.getString("active");
				availability.stopStart = result.getString("stopStart");
				availability.editDate = result.getString("editDate");
				availability.editDate = availability.editDate.substring(0, 10);
				availability.editBy = result.getString("editBy");
				
      			data.add(availability);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	return (data);
	}
	
	public Vector getPricelines(String property_id, boolean history) {
		connect();
		Vector data = new Vector();
		String table = "";
		if (history) {
			table = "HistoryPriceTBL";
		} else {
			table = "PriceTBL";
		}
		try {
			SQL = "select * from "+table+
			" where propID="+property_id+" order by priceType, supplement, fromDate, toDate, pricePeriodID";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	more = result.next();
          	
          	Formatter formatter = new Formatter();
        	Priceline priceline = new Priceline();
      		while (more) {
      			priceline = new Priceline();
      			
      			priceline.pricePeriodID = result.getString("pricePeriodID");
      			priceline.fromDate =formatter.formatDate(result.getString("fromDate"));
      			priceline.toDate =formatter.formatDate(result.getString("toDate"));
      			priceline.propID = result.getString("propID");
      			priceline.introDate =formatter.formatDate(result.getString("introDate"));
      			priceline.supplierID = result.getString("supplierID");
      			priceline.agentGroup = result.getString("agentGroup");
      			priceline.exchangeID = result.getString("exchangeID");
      			priceline.markup = formatter.formatPercentage(result.getString("markup"));
      			priceline.priceType = result.getString("priceType");
      			
      			priceline.selfCatering = formatter.formatCurrency(result.getString("selfCatering"));
      			priceline.roomOnly = formatter.formatCurrency(result.getString("roomOnly"));
      			priceline.bedBreakfast = formatter.formatCurrency(result.getString("bedBreakfast"));
      			priceline.halfBoard = formatter.formatCurrency(result.getString("halfBoard"));
      			priceline.fullBoard = formatter.formatCurrency(result.getString("fullBoard"));
      			priceline.allInclusive = formatter.formatCurrency(result.getString("allInclusive"));
      			
      			priceline.percentage1Child = result.getString("percentage1Child");
      			priceline.percentage2Child = result.getString("percentage2Child");
      			priceline.percentage3Child = result.getString("percentage3Child");
      			priceline.percentage3Adult = result.getString("percentage3Adult");
      			priceline.percentage4Adult = result.getString("percentage4Adult");
      			
      			if (priceline.percentage1Child.equals("1")) {
      				priceline.disc1stChild = formatter.formatPercentage(result.getString("disc1stChild"));
      			} else {
      				priceline.disc1stChild = formatter.formatCurrency(result.getString("disc1stChild"));
      			}
      			if (priceline.percentage2Child.equals("1")) {
      				priceline.disc2ndChild = formatter.formatPercentage(result.getString("disc2ndChild"));
      			} else {
      				priceline.disc2ndChild = formatter.formatCurrency(result.getString("disc2ndChild"));
      			}
      			if (priceline.percentage3Child.equals("1")) {
      				priceline.disc3rdChild = formatter.formatPercentage(result.getString("disc3rdChild"));
      			} else {
      				priceline.disc3rdChild = formatter.formatCurrency(result.getString("disc3rdChild"));
      			}
      			if (priceline.percentage3Adult.equals("1")) {
      				priceline.disc3rdAdult = formatter.formatPercentage(result.getString("disc3rdAdult"));
      			} else {
      				priceline.disc3rdAdult = formatter.formatCurrency(result.getString("disc3rdAdult"));
      			}
      			if (priceline.percentage4Adult.equals("1")) {
      				priceline.disc4thAdult = formatter.formatPercentage(result.getString("disc4thAdult"));
      			} else {
      				priceline.disc4thAdult = formatter.formatCurrency(result.getString("disc4thAdult"));
      			}
      			
      			priceline.activeMonday = result.getString("activeMonday");
      			priceline.activeTuesday = result.getString("activeTuesday");
      			priceline.activeWednesday = result.getString("activeWednesday");
      			priceline.activeThursday = result.getString("activeThursday");
      			priceline.activeFriday = result.getString("activeFriday");
      			priceline.activeSaturday = result.getString("activeSaturday");
      			priceline.activeSunday = result.getString("activeSunday");
      			
      			priceline.minStayTrig = result.getString("minStayTrig");
      			priceline.active = result.getString("active");
      			priceline.commissionable = result.getString("commissionable");
      			priceline.supplement = result.getString("supplement");
      			priceline.arrivalMonday = result.getString("arrivalMonday");
      			priceline.arrivalTuesday = result.getString("arrivalTuesday");
      			priceline.arrivalWednesday = result.getString("arrivalWednesday");
      			priceline.arrivalThursday = result.getString("arrivalThursday");
      			priceline.arrivalFriday = result.getString("arrivalFriday");
      			priceline.arrivalSaturday = result.getString("arrivalSaturday");
      			priceline.arrivalSunday = result.getString("arrivalSunday");
      			
      			priceline.salesPeriod = result.getString("salePeriod");
      			priceline.childAge = result.getString("childAge");
      			priceline.editBy = result.getString("editBy");
      			priceline.refNumber = result.getString("refNumber");
      			priceline.contractGuarantee = result.getString("contractGuarantee");
      			priceline.infantAge = result.getString("infantAge");
      			
      			priceline.rooms = getRoomSupplements(priceline.pricePeriodID);
      			
      			data.add(priceline);
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	return (data);
	}
	
	public Vector getPropertiesItem(String item_id, String item_type) {
		connect();
		Vector items = new Vector();
		try {			
			int item_type_int = Integer.parseInt(item_type);
			//this.id = item_id;
			
			Item item = new Item(item_type);
			item.get(item_id);
			Item sub_item = new Item(item_type);
			
			java.util.Enumeration e;
			items.add(item);
			
			Vector items_temp = new Vector();
			Vector sub_items = new Vector();
			//Lists list = new Lists();
			java.util.Enumeration sub_e;
			
			for (int i=item_type_int; i<5; i++) {
				e = items.elements();
				items_temp = new java.util.Vector();
				while (e.hasMoreElements()) {
					item = (Item)e.nextElement();
					sub_items = this.getItems(item.id, String.valueOf(i));
					sub_e = sub_items.elements();
					while (sub_e.hasMoreElements()) {
						sub_item = (Item)sub_e.nextElement();
						items_temp.add(sub_item);
					}
				}
				items = items_temp;
			}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (items);
	}
	
	public Vector getRoomsItem (String item_id, String item_type) {
		Vector rooms = new Vector();
		Vector rooms_temp = new Vector();
		Room room = new Room();
		java.util.Enumeration data_rooms;
		Vector properties = new Vector();
		Item property = new Item("5");
		java.util.Enumeration data_properties;
		properties = this.getPropertiesItem(item_id, item_type);
		data_properties = properties.elements();
		while (data_properties.hasMoreElements()) {
			property = (Item)data_properties.nextElement();
			rooms_temp = this.getRoomsProperty(property.id);
			data_rooms = rooms_temp.elements();
			while (data_rooms.hasMoreElements()) {
				room = (Room)data_rooms.nextElement();
				rooms.add(room);
			}			
		}
		return (rooms);
	}
	
	public Vector getSupplements() {
		connect();
		Vector data = new Vector();
		Validation validation = new Validation();
		try {
			SQL = "select SupplementID from "+this.tables_modifyer+"[SupplementsTBL] order by (suplDescription)";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		more = result.next();
      		
      		Supplement supplement = new Supplement();
      		while (more) {
      			supplement = new Supplement();
          		supplement.get(result.getString("SupplementID"));
          		if (!supplement.id.equals("40") && !supplement.id.equals("51")) {
          			data.add(supplement);
          		}
          		more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (data);
	}
	
	public Vector getOffers(String contract_id) {
		Vector data = new Vector();
		Vector periods = new Vector();
		Period period = new Period();
		java.util.Enumeration e;
		periods = this.getPeriods(contract_id);
		for (e=periods.elements(); e.hasMoreElements();) {
			period = (Period)e.nextElement();
			if (period.offer.equals("1") && (!period.hasComplementary || (period.hasComplementary && period.firstComplementary))) {
				data.add(period);
			}
		}
		return (data);
	}
	
}