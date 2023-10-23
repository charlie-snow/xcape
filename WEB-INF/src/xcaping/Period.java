package xcaping;

import java.sql.*;
import java.util.Enumeration;

public class Period extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert period";
	
	public String id;
	public String active;
	public String contract;
	public String from_date;
	public String to_date;
	public String minimum_stay;
	public String active_monday;
	public String active_tuesday;
	public String active_wednesday;
	public String active_thursday;
	public String active_friday;
	public String active_saturday;
	public String active_sunday;
	public String arrival_monday;
	public String arrival_tuesday;
	public String arrival_wednesday;
	public String arrival_thursday;
	public String arrival_friday;
	public String arrival_saturday;
	public String arrival_sunday;
	public String short_stay_suplement;
	public String short_stay_percentage;
	public String edited_by;
	public String edit_date;
	public String air_conditioned = "";
	public String offer = "0";
	public String agent_group = "0";
	
	///private Vector[] prices = new Vector[4];
	
	public String insertType;
	public boolean hasComplementaryOffer;
	public boolean hasComplementary;
	public boolean firstComplementary;
	public String complementary_id;
	
	public String returnURL = "";
	public String next_periodURL = "";
	public String editURL = "";
	public String deleteURL = "";
	public String offerURL = "";
	
	public String errorURL = "";
	
	public boolean validate() {
		Validation validation = new Validation();
		boolean valid = validation.validate(this);
		if (valid == false) {
			this.error = validation.message;
		}
		return(valid);
	}
	
	private void format() {
		Formatter formatter = new Formatter();
		formatter.format(this);
	}
	
	public boolean insert(Prices prices, boolean last_complementary) {
		boolean valid = true;

      	// getting the complementary
		Period complementary = new Period();
		complementary = this.getComplementary(this.offer, "0");
		
		this.hasComplementary = !(this.active_monday.equals("1") 
		&& this.active_tuesday.equals("1") 
		&& this.active_wednesday.equals("1") && this.active_thursday.equals("1") 
		&& this.active_friday.equals("1") && this.active_saturday.equals("1") 
		&& this.active_sunday.equals("1"));
		
		if (last_complementary) {
			this.firstComplementary = false;
			this.hasComplementary = false;
		}
		
		try {
			connect();
			Statement sentencia = conexion.createStatement();
          	
          	SQL = "select max(period_id) as max from "+this.tables_modifyer+"[period]";
      		ResultSet result = sentencia.executeQuery(SQL);
      		result.next();
      		String string_max = result.getString("max");
      		int max;
      		if (string_max == null) {
      			max = 0;
      		} else {
      			max = Integer.parseInt(string_max);
      		}      		
      		max++;
          	this.id = Integer.toString(max);
    		
    		if (prices.validate(this.offer.equals("1"), complementary)) {
    			if (validate()) {
    				SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[period] " +
    				"([period_id], [active_period], [contract_period], [from_date_period], [to_date_period], " +
    				"[minimum_stay], [active_monday], [active_tuesday], [active_wednesday], " +
    				"[active_thursday], [active_friday], [active_saturday], [active_sunday], " +
    				"[short_stay_suplement], [short_stay_percentage], [edited_by_period], [edit_date], " +
    				"[air_conditioned], [offer_period], " +
    				"[arrival_monday], [arrival_tuesday], [arrival_wednesday], [arrival_thursday], " +
    				"[arrival_friday], [arrival_saturday], [arrival_sunday], [agent_group_period])  " +
    				"values ('"+this.id+
    	          	"','"+this.active+"','"+this.contract
    				+"','"+this.from_date+"','"+this.to_date
    				+"',"+this.minimum_stay
    				+",'"+this.active_monday
    				+"','"+this.active_tuesday+"','"+this.active_wednesday
    				+"','"+this.active_thursday+"','"+this.active_friday
    				+"','"+this.active_saturday+"','"+this.active_sunday
    				+"',"+this.short_stay_suplement+","+this.short_stay_percentage
    				+", '"+this.edited_by+"', '"+this.edit_date
    				+"', '"+this.air_conditioned+"', '"+this.offer
    	          	+"','"+this.arrival_monday
    				+"','"+this.arrival_tuesday+"','"+this.arrival_wednesday
    				+"','"+this.arrival_thursday+"','"+this.arrival_friday
    				+"','"+this.arrival_saturday+"','"+this.arrival_sunday
    				+"','"+this.agent_group+"')";
    	      		sentencia.execute(SQL);
    	      		
    	      		prices.setPeriod(this.id, this.contract);
    				prices.insert(false);			
    			} else {
    				valid = false;
    			}
    			
    		} else {
    			valid = false;
    			this.error = prices.error;
    		}
    		disconnect();
		} catch (Exception ex) {
      		this.error = ex.toString();
      		valid = false;
      	}
		return(valid);
	}
	
	public boolean insertOffer(Prices offer_prices, String period_id, boolean last_complementary) {
		boolean valid = true;
		
		Validation validation = new Validation();
		if (validation.validate(this)) {
			// checking if the period has complementary (active days)
			Period period = new Period();
			period.get(period_id);
			String complementary_id = "0";
			complementary_id = period.getComplementaryId("0");
			
			//-------------------------
			
	      	Converter converter = new Converter();
	      	if (!converter.insertBrokenOfferShortStays(period_id, this, complementary_id)) { valid = false; }

			// insertion of the offer, mixing the missing data with the period
	      	Prices prices = new Prices();
			prices.setPeriod(period.id, period.contract);
			prices.getPrices();
			Period new_period = new Period();
			prices = this.mergeData(prices, offer_prices);
			new_period = this.mergeData(period, this);
			
			new_period.offer = "1";
			new_period.from_date = this.from_date;
			new_period.to_date = this.to_date;
			if (!new_period.insert(prices, last_complementary)) { valid = false; } // an offer period inserted manually
			//this.period_id = new_period.id;
			if (hasValue(new_period.short_stay_suplement)) {
				if (!converter.insertShortStay(new_period)) { valid = false; }
				if (last_complementary) {
					Period complementary_offer = new Period();
					complementary_offer = this.getComplementary("1", new_period.id);
					if (!converter.insertShortStay(complementary_offer)) { valid = false; }
				}
			}
			
			if (!converter.insertPriceline(new_period, prices, true)) { valid = false; }
			if (!converter.insertAvailabilities(new_period)) { valid = false; }
	      	
	      	// returning info for complementary (active days) offers
	      	if (period.hasComplementary) {
	      		this.hasComplementary = true;
				this.id = complementary_id;
				this.complementary_id = new_period.id;
			}
		} else {
			valid = false;
			this.error = validation.message;
		}
				
		return(valid);
	}
	
	private boolean hasValue(String value) {
		return ((!value.equals(null)) && (!value.equals("0.00")) && (!value.equals("0") && (!value.equals(""))));
	}
	
	private Prices mergeData(Prices prices, Prices prices2) {
		
		Enumeration e;
		Enumeration e2;
		
		BoardBasisPeriod board_basisPeriod = new BoardBasisPeriod();
		DiscountPeriod discountPeriod = new DiscountPeriod();
		RoomPeriod roomPeriod = new RoomPeriod();
		
		BoardBasisPeriod board_basisPeriod_offer = new BoardBasisPeriod();
		DiscountPeriod discountPeriod_offer = new DiscountPeriod();
		RoomPeriod roomPeriod_offer = new RoomPeriod();
		
		e2 = prices2.board_bases.elements();
		for (e = prices.board_bases.elements() ; e.hasMoreElements() ;) {
			board_basisPeriod = (xcaping.BoardBasisPeriod)e.nextElement();
			board_basisPeriod_offer = (xcaping.BoardBasisPeriod)e2.nextElement();
			
			if (this.hasValue(board_basisPeriod_offer.price)) {
				board_basisPeriod.price = board_basisPeriod_offer.price;
			}
		}
		
		e2 = prices2.discounts.elements();
		for (e = prices.discounts.elements() ; e.hasMoreElements() ;) {
			discountPeriod = (xcaping.DiscountPeriod)e.nextElement();
			discountPeriod_offer = (xcaping.DiscountPeriod)e2.nextElement();
			
			if (this.hasValue(discountPeriod_offer.amount)) {
				discountPeriod.amount = discountPeriod_offer.amount;
				discountPeriod.percentage = discountPeriod_offer.percentage;
			}
		}
		
		e2 = prices2.rooms.elements();
		for (e = prices.rooms.elements() ; e.hasMoreElements() ;) {
			roomPeriod = (RoomPeriod)e.nextElement();
			roomPeriod_offer = (RoomPeriod)e2.nextElement();
			
			if (this.hasValue(roomPeriod_offer.price)) {
				roomPeriod.price = roomPeriod_offer.price;
			}
			if (!roomPeriod_offer.unit.equals("2")) {
				roomPeriod.unit = roomPeriod_offer.unit;
			}
			if (this.hasValue(roomPeriod_offer.allotment)) {
				roomPeriod.allotment = roomPeriod_offer.allotment;
			}
			if (this.hasValue(roomPeriod_offer.release)) {
				roomPeriod.release = roomPeriod_offer.release;
			}
		}
		
		return (prices);
	}
	
	
	private Period mergeData(Period period, Period period2) {
		
		Period merged = new Period();
		merged = period;
		if (this.hasValue(period2.minimum_stay)) {
			merged.minimum_stay = period2.minimum_stay;
		}
		if (this.hasValue(period2.short_stay_suplement)) {
			merged.short_stay_suplement = period2.short_stay_suplement;
		} else {
			merged.short_stay_suplement = "0";
		}
		if (this.hasValue(period2.short_stay_percentage)) {
			merged.short_stay_percentage = period2.short_stay_percentage;
		} else {
			merged.short_stay_percentage = "0";
		}
		if (this.hasValue(period2.agent_group)) {
			merged.agent_group = period2.agent_group;
		}
		merged.arrival_monday = period2.arrival_monday;
		merged.arrival_tuesday = period2.arrival_tuesday;
		merged.arrival_wednesday = period2.arrival_wednesday;
		merged.arrival_thursday = period2.arrival_thursday;
		merged.arrival_friday = period2.arrival_friday;
		merged.arrival_saturday = period2.arrival_saturday;
		merged.arrival_sunday = period2.arrival_sunday;
		return (merged);
	}
		
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[period] where period_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("period_id");
      		this.active = result.getString("active_period");
			this.contract = result.getString("contract_period");
      		
			this.from_date = result.getString("from_date_period");
			this.to_date = result.getString("to_date_period");
			
      		this.minimum_stay = result.getString("minimum_stay");
      		this.active_monday = result.getString("active_monday");
      		this.active_tuesday = result.getString("active_tuesday");
      		this.active_wednesday = result.getString("active_wednesday");
      		this.active_thursday = result.getString("active_thursday");
      		this.active_friday = result.getString("active_friday");
      		this.active_saturday = result.getString("active_saturday");
      		this.active_sunday = result.getString("active_sunday");
      		this.arrival_monday = result.getString("arrival_monday");
      		this.arrival_tuesday = result.getString("arrival_tuesday");
      		this.arrival_wednesday = result.getString("arrival_wednesday");
      		this.arrival_thursday = result.getString("arrival_thursday");
      		this.arrival_friday = result.getString("arrival_friday");
      		this.arrival_saturday = result.getString("arrival_saturday");
      		this.arrival_sunday = result.getString("arrival_sunday");
      		this.short_stay_suplement = result.getString("short_stay_suplement");
      		this.short_stay_percentage = result.getString("short_stay_percentage");
      		this.edited_by = result.getString("edited_by_period");
      		this.edit_date = result.getString("edit_date");
      		
      		this.air_conditioned = result.getString("air_conditioned");
      		this.offer = result.getString("offer_period");
      		this.agent_group = result.getString("agent_group_period");
      		
      		this.format();
      		
      		// extras:	- checks if a normal period has a complementary offer or period
      		Validation validation = new Validation();
  			String SQL= this.SQLprefix+"select * from "+this.tables_modifyer+"[period] where " +
			"from_date_period='"+validation.getSQLDate(this.from_date)+"' and " +
			"to_date_period='"+validation.getSQLDate(this.to_date)+"' and " +
			"contract_period='"+this.contract+"' and offer_period='"+this.offer+"' " +
			"and period_id<>'"+this.id+"'";
	      	sentencia = conexion.createStatement();
	  		result = sentencia.executeQuery(SQL);
  			this.hasComplementary = result.next();
      		
      		if (!this.offer.equals("1")) {
      			
      			SQL = this.SQLprefix+"select * from "+this.tables_modifyer+"[period] where " +
      			"contract_period='"+this.contract+"' " +
      			"and ((from_date_period between '"+validation.getSQLDate(this.from_date)+" 00:00:00:000' " +
      				"and '"+validation.getSQLDate(this.to_date)+" 23:00:00:000') " +
      			"and (to_date_period between '"+validation.getSQLDate(this.from_date)+" 00:00:00:000' " +
      				"and '"+validation.getSQLDate(this.to_date)+" 23:00:00:000')) " +
      			"and offer_period='1'";
		      	sentencia = conexion.createStatement();
		  		result = sentencia.executeQuery(SQL);
      			this.hasComplementaryOffer = result.next();
      		} else {
      			this.hasComplementaryOffer = false;
      		}
      		
      	} catch (Exception ex) {
      		this.error = ex.toString();
      	}
      	disconnect();
	}
	
	public void delete(String period_id, String has_complementary, boolean delete) {
		this.get(period_id);
		try {
			// if generated, delete the pricelines and availabilities from the old system
			boolean offer = this.offer.equals("1");
			Contract contract = new Contract();
			Converter converter = new Converter();
			contract.get(this.contract);
			if (contract.state.equals("active") || contract.state.equals("inactive")) {
				String type = "";
				if (offer) {
					type = "offer";
				} else {
					type = "net_rates";
				}
				converter.deletePricelines(contract.property, this.from_date, this.to_date, type, false);
				//converter.deletePriceline(contract.property, this.from_date, this.to_date, false, false, offer);
				if (offer) {
					// insert availability from the complementary period
					
					Period period = new Period();
					period = this.getPeriod_Offer(period_id, this.from_date, this.contract);
					Prices prices = new Prices();
					prices.setPeriod(period.id, period.contract);
					prices.getPrices();
					converter.deletePricelines(contract.property, period.from_date, period.to_date, "short_stay", false);
					//converter.deletePricelines(contract.property, period.from_date, period.to_date, true, false, false);
					converter.deleteAvailabilities(contract.property, period.from_date, period.to_date);
					if (!period.id.equals("none")){
						converter.insertAvailabilities(period);
					}
					//period.modify(prices);
					if (hasValue(period.short_stay_suplement)) {
						converter.insertShortStay(period);
						if (has_complementary.equals("true")) {
							Period complementary = new Period();
							complementary = period.getComplementary("0", period.id);
							converter.insertShortStay(complementary);
						}
			  		}		
				} else {
					converter.deleteAvailability(contract.property, this.from_date, this.to_date, "0", false);
				}
			}
			
			// delete from xcaping
			
			if (delete) {
				connect();
				Statement sentencia = conexion.createStatement();
				if (has_complementary.equals("true")) {
	      			
	      			// getting the complementary id
	      			Validation validation = new Validation();
	      			SQL = this.SQLprefix+"select * from "+this.tables_modifyer+"[period] where " +
					"from_date_period='"+validation.getSQLDate(this.from_date)+"' and " +
					"to_date_period='"+validation.getSQLDate(this.to_date)+"' and " +
					"contract_period='"+this.contract+"' and offer_period='"+this.offer+"' " +
					"and period_id<>'"+this.id+"'";
			  		result = sentencia.executeQuery(SQL);
	      			boolean hasComplementary = result.next();
	      			if (hasComplementary) { // deleting the complementary
	      				String id = result.getString("period_id");
	      				deleteBBasesPrices(id);
	      				deleteDiscountsPrices(id);
	      				deleteRoomsPrices(id);
	      				connect();
	      				SQL = "delete from "+this.tables_modifyer+"[period] where period_id='"+id+"'";
	      	          	sentencia = conexion.createStatement();
	      	      		sentencia.execute(SQL);
	      			}
				}
				
				deleteBBasesPrices(period_id);
				deleteDiscountsPrices(period_id);
				deleteRoomsPrices(period_id);
				SQL = "delete from "+this.tables_modifyer+"[period] where period_id='"+period_id+"'";
	          	sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      		disconnect();
			}			
      	} catch (Exception ex) {
      	}
	}
	
	private void deleteBBasesPrices(String period_id) {
		try {			
			SQL = "delete from "+this.tables_modifyer+"[board_basis_period] where " +
				"period_bbp='"+period_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	private void deleteRoomsPrices(String period_id) {
		try {			
			SQL = "delete from "+this.tables_modifyer+"[room_period] where " +
				"period_rp='"+period_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	private void deleteDiscountsPrices(String period_id) {
		try {			
			SQL = "delete from "+this.tables_modifyer+"[discount_period] where " +
				"period_dp='"+period_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	public boolean modify(Prices prices, boolean last_complementary) {
		boolean valid = true;
		
		// getting the complementary
		Period complementary = new Period();
		complementary = this.getComplementary(this.offer, this.id);
		this.hasComplementary = !complementary.id.equals("0");
		this.complementary_id = complementary.id;
		if (last_complementary) {
			this.firstComplementary = false;
			this.hasComplementary = false;
		}
		Period period = new Period();
		period.get(this.id);
		if (prices.validate(this.offer.equals("1"), complementary)) {
			if (validate()) {
				connect();
				try {					
					this.air_conditioned = "0";
					
					prices.setPeriod(this.id, this.contract);
    				prices.insert(this.offer.equals("1"));
					
					// xcaping
					SQL = this.SQLprefix+"update "+this.tables_modifyer+"[period] " +
						"set active_period='"+this.active+"', " +
						"contract_period='"+this.contract+"', "+
						"from_date_period='"+this.from_date+"', " +
						"to_date_period='"+this.to_date+"', " +
						"minimum_stay="+this.minimum_stay+", " +
						"active_monday='"+this.active_monday+"',  " +
						"active_tuesday='"+this.active_tuesday+"',  " +
						"active_wednesday='"+this.active_wednesday+"',  " +
						"active_thursday='"+this.active_thursday+"',  " +
						"active_friday='"+this.active_friday+"',  " +
						"active_saturday='"+this.active_saturday+"',  " +
						"active_sunday='"+this.active_sunday+"',  " +
						"short_stay_suplement="+this.short_stay_suplement+",  " +
						"short_stay_percentage='"+this.short_stay_percentage+"', " +
						"edited_by_period='"+this.edited_by+"', " +
						"edit_date='"+this.edit_date+"', " +
						"air_conditioned='"+this.air_conditioned+"', " +
						"offer_period='"+this.offer+"', " +
						"arrival_monday='"+this.arrival_monday+"',  " +
						"arrival_tuesday='"+this.arrival_tuesday+"',  " +
						"arrival_wednesday='"+this.arrival_wednesday+"',  " +
						"arrival_thursday='"+this.arrival_thursday+"',  " +
						"arrival_friday='"+this.arrival_friday+"',  " +
						"arrival_saturday='"+this.arrival_saturday+"',  " +
						"arrival_sunday='"+this.arrival_sunday+"', " +
						"agent_group_period='"+this.agent_group+"'  " +
						"where period_id='"+this.id+"'";
		          	Statement sentencia = conexion.createStatement();
		      		sentencia.execute(SQL);
    				
    				// old-system: if generated and the offer is active, 
					// modify the availabilities and pricelines involved;
					Contract contract = new Contract();
					contract.get(this.contract);
					if (contract.state.equals("active") || contract.state.equals("inactive")) {
						boolean offer = period.offer.equals("1");
						if (!offer || period.active.equals("1")) {
							Converter converter = new Converter();
							converter.modifyPricelines(this, period, !last_complementary);
							converter.modifyAvailability(this, period.from_date, period.to_date);
						}				
					}
    				
		      	} catch (Exception ex) {
		      		this.error = ex.toString();
		      		valid = false;
		      	}
			} else {
				valid = false;
			}
		} else {
			valid = false;
			this.error = prices.error;
		}
		
		disconnect();
		return (valid);
	}
	
	public void subBoardBasis(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[board_basis_period] where period_bbp='"
				+this.id+"' " +"and board_basis_bbp='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void subDiscount(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[discount_period] " +
				"where period_dp='"+this.id+"' " +
				"and discount_dp='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void subRoom(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[room_period] where period_rp='"
				+this.id+"' " +"and room_rp='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void addNewRooms(String property_id) {
		Lists list = new Lists();
		Room room = new Room();
		RoomPeriod roomPeriod = new RoomPeriod();
		try {
			
			java.util.Vector data = new java.util.Vector();
			data = list.getRoomsProperty(property_id);
			
			java.util.Enumeration enum_data;
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				room = (xcaping.Room)enum_data.nextElement();
				roomPeriod = new RoomPeriod();
				roomPeriod.period_id = this.id;
				roomPeriod.room_id = room.id;
				if (!roomPeriod.exists()) {
					roomPeriod.price = "0";
					roomPeriod.percentage = "0";
					roomPeriod.allotment = "0";
					roomPeriod.unit = "0";
					roomPeriod.release = "0";
					roomPeriod.insert();
				}
			}
		} catch (Exception ex) {
			
		}
	}
	
	public void subNewRooms(String property_id) {
		connect();
		Lists list = new Lists();
		Room room = new Room();
		try {
			
			java.util.Vector data = new java.util.Vector();
			data = list.getRoomsProperty(property_id);
			
			SQL = "delete from "+this.tables_modifyer+"[room_period] where period_rp='"+this.id+"'";
			
			java.util.Enumeration enum_data;
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				room = (xcaping.Room)enum_data.nextElement();
				SQL = SQL+" and room_rp<>'"+room.id+"'";
			}
			Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
		} catch (Exception ex) {
		}
		disconnect();
	}
	
	/**
	 * returns the complementary period. if it doesn't exist, the returned period has the id "none".<br>
	 * if it's an offer, the complementary is the net rates period 
	 * @param period_id
	 * @return
	 */
	
	public Period getPeriod_Offer(String offer_id, String from_date, String contract) {
		
		connect();
		Validation validation = new Validation();
		Period offer_period = new Period();
		offer_period.id = "none";
		try {
			
			SQL = this.SQLprefix+"select * from [period] " +
			"where ('"+validation.getSQLDate(from_date)+" 23:00:00:000' " +
			"between from_date_period and to_date_period) " +
			"and contract_period='"+contract+"' and offer_period='0'";
			
	      	Statement sentencia = conexion.createStatement();
	  		result = sentencia.executeQuery(SQL);
	  		if (result.next()) {
	  			offer_period.id = result.getString("period_id");
	      		offer_period.active = result.getString("active_period");
				offer_period.contract = result.getString("contract_period");
	      		
				offer_period.from_date = result.getString("from_date_period");
				offer_period.to_date = result.getString("to_date_period");
				
	      		offer_period.minimum_stay = result.getString("minimum_stay");
	      		offer_period.active_monday = result.getString("active_monday");
	      		offer_period.active_tuesday = result.getString("active_tuesday");
	      		offer_period.active_wednesday = result.getString("active_wednesday");
	      		offer_period.active_thursday = result.getString("active_thursday");
	      		offer_period.active_friday = result.getString("active_friday");
	      		offer_period.active_saturday = result.getString("active_saturday");
	      		offer_period.active_sunday = result.getString("active_sunday");
	      		offer_period.short_stay_suplement = result.getString("short_stay_suplement");
	      		offer_period.short_stay_percentage = result.getString("short_stay_percentage");
	      		offer_period.edited_by = result.getString("edited_by_period");
	      		offer_period.edit_date = result.getString("edit_date");
	      		
	      		offer_period.air_conditioned = result.getString("air_conditioned");
	      		offer_period.offer = result.getString("offer_period");
	      		
	      		offer_period.agent_group = result.getString("agent_group_period");
	  		}		  			
		} catch (Exception ex) {
			this.error = ex.toString();
		}
		disconnect();
		offer_period.format();
		return (offer_period);
	}
	
	/** changes the period state to the value of the parameter "value"
	 * @param period_id
	 * @param value
	 */
	public void setActive(String period_id, String value, boolean generated, boolean has_complementary) {
		try {
			connect();
			SQL = "update "+this.tables_modifyer+"[period] set active_period='"+value+"' "+
			"where period_id='"+period_id+"'";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(SQL);
			disconnect();
			
			this.id = period_id;
			Converter converter = new Converter();
			// if offer: restore/delete the below period without deleting the offer
			boolean activate = value.equals("1");
			this.get(period_id);
			if (generated) {
				if (this.offer.equals("1")) {
					if (activate) {
						converter.activateOffer(this, has_complementary);
					} else {
						converter.deactivateOffer(this, has_complementary);
					}
				} else {
					converter.setActivePricelines(this);
					converter.setActiveAvailability(this);
				}
			}   		
      	} catch (Exception ex) {
      	}
	}

	public void compDays() {
		
		this.active_monday = not(this.active_monday);
		this.active_tuesday = not(this.active_tuesday);
		this.active_wednesday = not(this.active_wednesday);
		this.active_thursday = not(this.active_thursday);
		this.active_friday = not(this.active_friday);
		this.active_saturday = not(this.active_saturday);
		this.active_sunday = not(this.active_sunday);
	}
	
	private String not(String value) {
		String inverse = "";
		
		if (value.equals("1")) {
			inverse = "0";
		} else {
			inverse = "1";
		}
		
		return(inverse);
	}
	
	public Period getComplementary(String offer, String period_id) {
		Period complementary = new Period();
		complementary.id = "0";
		connect();
		try {
			Validation validation = new Validation();
			String SQL = this.SQLprefix+"select * from "+this.tables_modifyer+"[period] where " +
			"from_date_period='"+validation.getSQLDate(this.from_date)+"' and " +
			"to_date_period='"+validation.getSQLDate(this.to_date)+"' and " +
			"contract_period='"+this.contract+"' " +
			"and offer_period='"+offer+"'";
			if (!period_id.equals("0")) { SQL = SQL + " and period_id<>'"+period_id+"'"; }
			Statement sentencia = conexion.createStatement();
			result = sentencia.executeQuery(SQL);
			boolean hasComplementary = result.next();
			if (hasComplementary) {
				complementary.get(result.getString("period_id"));
				complementary.hasComplementary = true;
			}
	  	} catch (Exception ex) {
	  		this.error = ex.toString();
	  	}
	  	disconnect();
	  	return (complementary);
	}
	
	public String getIdNextPeriod(String period_id, String contract_id) {
		String id = "0";
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		data = list.getPeriods(contract_id);
		Period period = new Period();
		
		enum_data = data.elements();
		period = (Period)enum_data.nextElement();
		
		while (enum_data.hasMoreElements() && !period.id.equals(period_id)) {
			period = (Period)enum_data.nextElement();
		}
		if (enum_data.hasMoreElements()) {
			period = (Period)enum_data.nextElement();
			id = period.id;
		}
		
		return (id);
	}
	
	public String getComplementaryId(String offer) {
		String complementary_id = "0";
		connect();
		try {
			Validation validation = new Validation();
			
			SQL = this.SQLprefix+"select * from "+this.tables_modifyer+"[period] " +
			"where contract_period='"+this.contract+"' " +
			"and (('"+validation.getSQLDate(this.from_date)+" 23:00:00:000' " +
					"between from_date_period and to_date_period) " +
			"and ('"+validation.getSQLDate(this.to_date)+" 00:00:00:000' " +
					"between from_date_period and to_date_period)) " +
			"and offer_period='"+offer+"' and period_id<>'"+this.id+"'";
			
			Statement sentencia = conexion.createStatement();
			result = sentencia.executeQuery(SQL);
			if (result.next()) {
				complementary_id = result.getString("period_id");
				this.hasComplementary = true;
			}
      	} catch (Exception ex) {
      		this.error = ex.toString();
      	}
      	disconnect();		
		return (complementary_id);
	}
	
	public void getURLs (String contract_id) {
		this.returnURL = "index.jsp?content=contract&contract_id="+contract_id+"&subcontent=periods";
		this.next_periodURL = "index.jsp?content=contract&contract_id="+contract_id+"&subcontent=formEditPeriod";
		this.editURL = "index.jsp?content=contract&contract_id="+contract_id+"&subcontent=formEditPeriod&period_id="+this.id;
		this.deleteURL = "deletePeriod.jsp?period_id="+this.id+"&contract_id="+contract_id;
		this.offerURL = "index.jsp?content=contract&contract_id="+contract_id+"&subcontent=offer&period_id="+this.id;
		
		this.errorURL = "index.jsp?content=contract&contract_id="+contract_id+"&subcontent=error&error=";
	}
	
}
