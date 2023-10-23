package xcaping;

import java.sql.*;
import java.util.Vector;

/** <div align="center"><strong>property's contract</strong></div>
 * <p>description: the contract, as one of the basic units of the system, has lots of important methods. 
 * most of the changes made here affect the periods and the generation / pricing and availability</p>
 * @author carlos nieves lameiro
 * @version 1.2
 */

public class Contract extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert contract";
		
	public String id;
	public String name;
	public String property;
	public String supplier;
	public String from_date;
	public String to_date;
	public String arrival_monday;
	public String arrival_tuesday;
	public String arrival_wednesday;
	public String arrival_thursday;
	public String arrival_friday;
	public String arrival_saturday;
	public String arrival_sunday;
	public String markup;
	public String free_sale;
	public String cot_suplement;
	public String infant_age;
	public String child_age;
	public String text;
	public String currency = "";
	public String sales_period = "";
	public String commissionable = "";
	public String state = "not_generated";
	public String edited_by = "0";
	public String offer = "0";
	public String agent_group = "0";
	
	// generating sql results
	public Vector availabilities = new Vector();
	public Vector pricing = new Vector();
	
	public String returnURL = "";
	public String propertyURL = "";
	public String periodsURL = "";
	public String offersURL = "";
	public String configurationURL = "";
	public String editURL = "";
	public String active_roomsURL = "";
	public String stop_salesURL = "";
	public String allocationsURL = "";
	public String availabilitiesURL = "";
	public String pricelinesURL = "";
	public String add_onsURL = "";
	
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
	
	public boolean insert() {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
	          	Statement sentencia = conexion.createStatement();
	          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[contract] " +
	          	"([name_contract], [property_contract], [supplier_contract], " +
	          	"[from_date_contract], [to_date_contract], [arrival_monday], " +
	          	"[arrival_tuesday], [arrival_wednesday], [arrival_thursday], " +
	          	"[arrival_friday], [arrival_saturday], [arrival_sunday], [markup], " +
	          	"[free_sale], [cot_suplement], [infant_age], [child_age], " +
	          	"[text_contract], [currency], [sales_period], [commissionable], " +
	          	"[state], [edited_by_contract], [offer], [agent_group_contract]) " +
	          	"values ('"+this.name+"','"+this.property
				+"','"+this.supplier+"','"+this.from_date
				+"','"+this.to_date+"','"+this.arrival_monday
				+"','"+this.arrival_tuesday+"','"+this.arrival_wednesday
				+"','"+this.arrival_thursday+"','"+this.arrival_friday
				+"','"+this.arrival_saturday+"','"+this.arrival_sunday
				+"','"+this.markup+"','"+this.free_sale
				+"','"+this.cot_suplement+"','"+this.infant_age
				+"','"+this.child_age+"','"+this.text
				+"','"+this.currency+"','"+this.sales_period
				+"', '"+this.commissionable+"', '"+this.state
				+"', '"+this.edited_by+"', '"+this.offer
				+"', '"+this.agent_group+"')";
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      		this.error = ex.toString();
	      		valid = false;
	      	}
		} else {
			valid = false;
		}
		disconnect();
		this.getURLs("0", this.property);
		return (valid);
	}
	
	
	/** obtains all the contract's information, and if it has finished (the "to" date is in the past), 
	 * it's state is changed to "finished"
	 * @param contract_id
	 */
	public void get(String contract_id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[contract] where contract_id='"+contract_id+"'";
          	Statement sentencia = conexion.createStatement();
       		result = sentencia.executeQuery(SQL);
      		result.next();
			this.id = result.getString("contract_id");
			this.name = result.getString("name_contract");
			this.property = result.getString("property_contract");
      		this.supplier = result.getString("supplier_contract");
      		this.from_date = result.getString("from_date_contract");
      		this.to_date = result.getString("to_date_contract");
      		this.arrival_monday = result.getString("arrival_monday");
      		this.arrival_tuesday = result.getString("arrival_tuesday");
      		this.arrival_wednesday = result.getString("arrival_wednesday");
      		this.arrival_thursday = result.getString("arrival_thursday");
      		this.arrival_friday = result.getString("arrival_friday");
      		this.arrival_saturday = result.getString("arrival_saturday");
      		this.arrival_sunday = result.getString("arrival_sunday");
      		this.markup = result.getString("markup");
      		this.free_sale = result.getString("free_sale");
      		this.cot_suplement = result.getString("cot_suplement");
      		this.infant_age = result.getString("infant_age");
      		this.child_age = result.getString("child_age");
      		this.text = result.getString("text_contract");
      		this.currency = result.getString("currency");
      		this.sales_period = result.getString("sales_period");
      		this.commissionable = result.getString("commissionable");
      		this.state = result.getString("state");      		
      		this.edited_by = result.getString("edited_by_contract");
      		this.offer = result.getString("offer");
      		this.agent_group = result.getString("agent_group_contract");
      		
      		if (this.hasFinished()) {
      			this.setFinished(this.id);
      		}
      		
      		this.format();
      	} catch (Exception ex) {
      		this.error = ex.toString();
      	}
      	this.getURLs(contract_id, this.property);
      	disconnect();
	}
	
	private boolean hasFinished() {
		boolean finished = false;
		
		// convert string dates to date objects
		java.util.Date contract_to_date;
		java.util.Date today = new java.util.Date();
		
		Validation validation = new Validation();
		String pattern="yyy-MM-dd";
		java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat(pattern);
		
		contract_to_date = sdf.parse(validation.getSQLDate(this.to_date), new java.text.ParsePosition(0));
		
		finished = today.after(contract_to_date);
		
		return (finished);
	}
	
	/** deletes the contract, and all the related info: on the old system it deletes all the availabilities and pricing
	 * on the contract's period, on xcaping deletes the galas, addons, board bases, discounts, desctivated rooms and periods
	 * @param contract_id
	 */
	public void delete(String contract_id) {
		this.get(contract_id);
		connect();
		try {
			//this.id = id;
			
			// deleting on the old system
			if (this.state.equals("active") || this.state.equals("inactive")) {
				Converter converter = new Converter();
				converter.deleteAvailabilities(this.property, this.from_date, this.to_date);
				converter.deletePricelines(this.id);
			}
			
			// deleting on xcaping
			//deleteGalas();
			deleteAddOns();
			deleteBoardBases();
			deleteDiscounts();
			deleteDeactivatedRooms();
			deletePeriods();
			SQL = "delete from "+this.tables_modifyer+"[contract] where contract_id='"+this.id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
      	this.getURLs(contract_id, this.property);
	}
	
	/*private void deleteGalas() {
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		try {
			Gala gala = new Gala();
			
			data = list.getGalasContract(this.id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				gala = (xcaping.Gala)enum_data.nextElement();
				this.subGala(gala.id);
			}
      	} catch (Exception ex) {
      	}
	}*/
	
	private void deleteAddOns() {
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		try {
			AddOn addOn = new AddOn();
			
			data = list.getAddOnsContract(this.id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				addOn = (xcaping.AddOn)enum_data.nextElement();
				this.subAddOn(addOn.id);
			}
      	} catch (Exception ex) {
      	}
	}
	
	private void deleteBoardBases() {
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		try {
			BoardBasis board_basis = new BoardBasis();
			
			data = list.getBoardBasesContract(this.id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				board_basis = (xcaping.BoardBasis)enum_data.nextElement();
				this.subBoardBasis(board_basis.id, this.id);
			}
      	} catch (Exception ex) {
      	}
	}
	
	private void deleteDiscounts() {
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		try {
			Discount discount = new Discount();
			
			data = list.getDiscountsContract(this.id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				discount = (xcaping.Discount)enum_data.nextElement();
				this.subDiscount(discount.id, this.id);
			}
      	} catch (Exception ex) {
      	}
	}
		
	private void deletePeriods() {
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		try {
			Period period = new Period();
			
			data = list.getPeriods(this.id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				period = (xcaping.Period)enum_data.nextElement();
				period.delete(period.id, "0", true);
			}
      	} catch (Exception ex) {
      	}
	}
	
	private void deleteDeactivatedRooms() {
		
		Statement sentencia;
		try {
			SQL = "delete from "+this.tables_modifyer+"[not_active_room_contract] where " +
				"contract_narc='"+this.id+"'";
          	sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	/** modifies the contract data<br>
	 * &nbsp;&nbsp;&nbsp; - old-system: if generated, modify the availabilities and pricelines involved
	 * @param contract_id
	 * @return valid if the data is valid and the operation correct
	 */
	public boolean modify(String contract_id) {
		boolean valid = true;
		this.id = contract_id;
		if (validate()) {
			try {
				// old-system: if generated, modify the availabilities and pricelines involved;
				Contract contract = new Contract();
				contract.get(contract_id);
				if (contract.state.equals("active") || contract.state.equals("inactive")) {
					this.id = contract.id;
					Converter converter = new Converter();
					converter.modifyAvailabilities(this);
					converter.modifyPricelines(this, contract.cot_suplement);
				}
				
				// xcaping: update data
				connect();
				SQL = this.SQLprefix+"update "+this.tables_modifyer+"[contract] set name_contract='"+this.name+"', " +
					"supplier_contract='"+this.supplier+"', " +
					"from_date_contract='"+this.from_date+"', " +
					"to_date_contract='"+this.to_date+"', " +
					"arrival_monday='"+this.arrival_monday+"',  " +
					"arrival_tuesday='"+this.arrival_tuesday+"',  " +
					"arrival_wednesday='"+this.arrival_wednesday+"',  " +
					"arrival_thursday='"+this.arrival_thursday+"',  " +
					"arrival_friday='"+this.arrival_friday+"',  " +
					"arrival_saturday='"+this.arrival_saturday+"',  " +
					"arrival_sunday='"+this.arrival_sunday+"',  " +
					"markup='"+this.markup+"',  " +
					"free_sale='"+this.free_sale+"',  " +
					"cot_suplement='"+this.cot_suplement+"',  " +
					"infant_age='"+this.infant_age+"',  " +
					"child_age='"+this.child_age+"',  " +
					"text_contract='"+this.text+"', " +
					"currency='"+this.currency+"',  " +
					"sales_period='"+this.sales_period+"', " +
					"commissionable='"+this.commissionable+"', " +
					"edited_by_contract='"+this.edited_by+"', " +
					"agent_group_contract='"+this.agent_group+"' " +
					"where contract_id='"+contract_id+"'";
	          	Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      		disconnect();
	      	} catch (Exception ex) {
	      		this.error = ex.toString();
	      		valid = false;
	      	}
		} else {
			valid = false;
		}
		this.getURLs(contract_id, this.property);
		return (valid);
	}
	
	private boolean existsGalaContract(String gala_id, String contract_id) {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+this.tables_modifyer+"[gala_contract] " +
				"where gala_gala_contract='"+gala_id+"' " +
				"and  contract_gala_contract='"+contract_id+"'";
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	/** validates the gala data, and if the gala doesn't exist for this contract, it's added
	 * @param gala
	 * @return valid if the data is valid
	 */
	public boolean addGala(Gala gala) {
		connect();
		boolean valid = true;
		if (gala.validateGalaContract()) {
			if (!this.existsGalaContract(gala.id, gala.contract)) {
				try {
					SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[gala_contract] " +
						"([contract_gala_contract], [gala_gala_contract], [price_gala_contract], " +
						"[obligatory_gala_contract], [child_discount], [adult_discount]) values ("
						+gala.contract+","+gala.id+",'"+gala.price+"', "+gala.obligatory+",'"+gala.child_discount+"', '"
						+gala.adult_discount+"')";
					Statement sentencia = conexion.createStatement();
					sentencia.execute(SQL);
				} catch (Exception ex) {
					valid = false;
		      	}
			}			
		} else {
			valid = false;
		}
		disconnect();
		return (valid);
	}
	
	public void subGala(String id) {
		connect();
		Statement sentencia;
		try {
			SQL = "delete from "+this.tables_modifyer+"[gala_contract] where " +
				"gala_gala_contract='"+id+"' and " +
				"contract_gala_contract='"+this.id+"'";
          	sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public Gala getGala(String id) {
		connect();
		Gala gala = new Gala();
		try {
			SQL = "select * from "+this.tables_modifyer+"[gala_contract] where gala_gala_contract='"+id
			+"' and contract_gala_contract='"+this.id+"'";
			Statement sentencia = conexion.createStatement();
			result = sentencia.executeQuery(SQL);
      		result.next();
      		gala.get(result.getString("gala_gala_contract"));
			gala.price = result.getString("price_gala_contract");
			gala.obligatory = result.getString("obligatory_gala_contract");
			gala.child_discount = result.getString("child_discount");
			gala.adult_discount = result.getString("adult_discount");
		} catch (Exception ex) {
      	}
		disconnect();
		return (gala);
	}
	
	public void subAddOn(String id) {
		Statement sentencia;
		try {
			SQL = "delete from "+this.tables_modifyer+"[add_on_contract] where " +
				"contract_add_on='"+this.id+"'";
          	sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	private boolean existsBoardBasisContract(String board_basis_id, String contract_id) {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+this.tables_modifyer+"[board_basis_contract] " +
			"where board_basis_bb_con='"+board_basis_id+"' " +
			"and  contract_bb_con='"+contract_id+"'";			
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	/** attach a system's board basis to this contract if it wasn't attached yet, 
	 * and fill the existing periods with empty data for this board basis
	 * @param board_basis_id
	 * @param contract_id
	 * @return
	 */
	public boolean addBoardBasis(String board_basis_id, String contract_id) {
		connect();
		boolean valid = true;
		if (!this.existsBoardBasisContract(board_basis_id, contract_id)) {
				try {
					SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[board_basis_contract] values ('"
						+contract_id+"','"+board_basis_id+"')";
					Statement sentencia = conexion.createStatement();
					sentencia.execute(SQL);
					
					// fill the existing periods with empty data for this board basis
					
					java.util.Vector data_periods = new java.util.Vector();
					Lists list_periods = new Lists();
					java.util.Enumeration enum_data_periods;
					Period period = new Period();
					
					data_periods = list_periods.getPeriods(contract_id);
					for (enum_data_periods = data_periods.elements() ; enum_data_periods.hasMoreElements() ;) {
						period = (xcaping.Period)enum_data_periods.nextElement();
						BoardBasisPeriod board_basisPeriod = new BoardBasisPeriod();
						board_basisPeriod.board_basis_id = board_basis_id;
						board_basisPeriod.period_id = period.id;
						board_basisPeriod.price = "0";
						board_basisPeriod.set();
					}
				} catch (Exception ex) {
					valid = false;
		      	}
		}
		
		return (valid);
	}
		
	/** de-attach the board basis from the contract, and delete all the prices for this board basis
	 * @param board_basis_id
	 * @param contract_id
	 */
	public void subBoardBasis(String board_basis_id, String contract_id) {
		connect();
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		try {
			Period period = new Period();
			
			data = list.getPeriods(contract_id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				period = (xcaping.Period)enum_data.nextElement();
				period.subBoardBasis(id);
			}
			SQL = "delete from "+this.tables_modifyer+"[board_basis_contract] where contract_bb_con='"
				+contract_id+"' " +"and board_basis_bb_con='"+board_basis_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	
	}
	
	
	private boolean existsDiscountContract(String discount_id, String contract_id) {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+this.tables_modifyer+"[discount_contract] " +
			"where discount_disc_con='"+discount_id+"' " +
			"and  contract_disc_con='"+contract_id+"'";			
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	/** attach a system's discount to this contract if it wasn't attached yet, 
	 * and fill the existing periods with empty data for this discount
	 * @param discount_id
	 * @param contract_id
	 * @return
	 */
	public boolean addDiscount(String discount_id, String contract_id) {
		connect();
		boolean valid = true;
		if (!this.existsDiscountContract(discount_id, contract_id)) {
				try {
					SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[discount_contract] values ('"
						+contract_id+"','"+discount_id+"')";
					Statement sentencia = conexion.createStatement();
					sentencia.execute(SQL);
					
					//	fill the existing periods with empty data for this discount
					
					java.util.Vector data_periods = new java.util.Vector();
					Lists list_periods = new Lists();
					java.util.Enumeration enum_data_periods;
					Period period = new Period();
					
					data_periods = list_periods.getPeriods(contract_id);
					for (enum_data_periods = data_periods.elements() ; enum_data_periods.hasMoreElements() ;) {
						period = (xcaping.Period)enum_data_periods.nextElement();
						DiscountPeriod discountPeriod = new DiscountPeriod();					
						discountPeriod.discount_id = discount_id;
						discountPeriod.period_id = period.id;
						discountPeriod.percentage = "0";
						discountPeriod.amount = "0";
						discountPeriod.set();			
					}
			
				} catch (Exception ex) {
					valid = false;
		      	}
		}
		
		return (valid);
	}
	
	public void subDiscount(String discount_id, String contract_id) {
		connect();
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		try {
			Period period = new Period();
			
			data = list.getPeriods(contract_id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				period = (xcaping.Period)enum_data.nextElement();
				period.subDiscount(discount_id);
			}
			SQL = "delete from "+this.tables_modifyer+"[discount_contract] where contract_disc_con='"+contract_id+"' " +
					"and discount_disc_con='"+discount_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	
	}
	
	/** searches for the last period of the contract
	 * @return a Period object with the last period
	 */
	public Period getLastPeriod() {
		Period period = new Period();
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		data = list.getPeriods(this.id);
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			period = (xcaping.Period)enum_data.nextElement();
		}
		
      	return (period);
	}
	
	/** searches for the period before the last one
	 * @return a Period object with this period
	 */
	public Period getBeforeLastPeriod() {
		Period period = new Period();
		Period period2 = new Period();
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		data = list.getPeriods(this.id);
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			period2 = (xcaping.Period)enum_data.nextElement();
			if (enum_data.hasMoreElements()) {
				period = period2;
			}
		}
		
      	return (period);
	}

	/** manages the generation of the contract (the generation itself is made by the Generator)<br>
	 * first makes the conversion with the Generator object, and takes the SQL conversion strings from it
	 * @param contract_id
	 * @return true if there were no errors on the conversion
	 */
	public boolean generate(String contract_id) {
		Converter converter = new Converter();
		boolean ok = (converter.getAvailabilities(contract_id, true)) 
		&& (converter.getPricing(contract_id, true));
		if (ok) {
			// restore the stop sale lines for the property stop sales
			Contract contract = new Contract();
			contract.get(contract_id);
			
			java.util.Enumeration e;
			Lists list = new Lists();
			java.util.Vector data = new java.util.Vector();
			data = list.getStopSalesProperty(contract.property);
			StopSale stop_sale = new StopSale();
					
			for (e = data.elements() ; e.hasMoreElements() ;) {
				stop_sale = (StopSale)e.nextElement();
				converter.deleteAvailabilitiesStopSale(stop_sale.id);
				converter.insertAvailabilitiesStopSale(stop_sale.id);
				//stop_sale.delete(stop_sale.id);
				//stop_sale.insert(contract.property);
			}
		}
		this.error = converter.error;
		this.availabilities = converter.availabilities;
		this.pricing = converter.pricing;
		return(ok);
	}
	
	/** changes the contract state to generated
	 * @param contract_id
	 */
	public void setGenerated(String contract_id) {
		connect();
		try {
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[contract] " +
			"set state='active' " +
			"where contract_id='"+contract_id+"'";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	/** to ungenerate, the pricelines and availabilities on the old system for the contract period are deleted, 
	 * and the contract state is changed to "not_generated"
	 * @param contract_id
	 */
	public void unGenerate(String contract_id) {
		this.get(contract_id);
		connect();
		try {
			// delete all pricelines and availabilities on the old system
			Converter converter = new Converter();
			converter.deleteAvailabilities(this.property, this.from_date, this.to_date);
			converter.deletePricelines(contract_id);
			
			// activate all the offer lines - deprecated
			/*Period period = new Period();
			java.util.Vector data = new java.util.Vector();
			Lists list = new Lists();
			java.util.Enumeration enum_data;
			
			data = list.getPeriods(this.id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				period = (xcaping.Period)enum_data.nextElement();
				if (period.offer.equals("1")) {
					boolean has_complementary = !period.getComplementaryId(period.id).equals("0");
					period.setActive(period.id, "1", false, has_complementary);
				}
			}*/
			
			// restore the stop sale lines for the property stop sales
			Contract contract = new Contract();
			contract.get(contract_id);
			
			Lists list = new Lists();
			java.util.Vector data = new java.util.Vector();
			java.util.Enumeration e;
			data = list.getStopSalesProperty(contract.property);
			StopSale stop_sale = new StopSale();
					
			for (e = data.elements() ; e.hasMoreElements() ;) {
				stop_sale = (StopSale)e.nextElement();
				stop_sale.delete(stop_sale.id);
				stop_sale.insert(contract.property);
			}
			
			// set the contract as "not_generated"
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[contract] " +
			"set state='not_generated' " +
			"where contract_id='"+contract_id+"'";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	private void setFinished(String contract_id) {
		connect();
		try {
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[contract] " +
			"set state='finished' " +
			"where contract_id='"+contract_id+"'";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(SQL);
			
			this.state = "finished";
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean hasPeriods() {
		boolean has = true;
		connect();
		try {			
			SQL = this.SQLprefix+"select * from "+this.tables_modifyer+"[period] where " +
			"contract_period='"+this.id+"'";
			
	      	Statement sentencia = conexion.createStatement();
	  		result = sentencia.executeQuery(SQL);
	  		has = result.next();		  			
		} catch (Exception ex) {
		}
		disconnect();
		return (has);
	}
	
	private boolean hasMoreThanOnePeriod() {
		
		boolean has = true;
		connect();
		try {			
			SQL = this.SQLprefix+"select * from "+this.tables_modifyer+"[period] where " +
			"contract_period='"+this.id+"'";
			
	      	Statement sentencia = conexion.createStatement();
	  		result = sentencia.executeQuery(SQL);
	  		has = (result.next() && result.next());		  			
		} catch (Exception ex) {
		}
		disconnect();
		return (has);
	}
	
	private boolean hasDaysLeft(Period period) {
		boolean has = false;
		
		has = !((period.active_monday.equals("1")) 
				&& (period.active_tuesday.equals("1")) 
				&& (period.active_wednesday.equals("1")) 
				&& (period.active_thursday.equals("1"))
				&& (period.active_friday.equals("1"))
				&& (period.active_saturday.equals("1"))
				&& (period.active_sunday.equals("1")));
		
		return (has);
	}
		
	private boolean areComplementary(Period period1, Period period2) {
		boolean are = false;
		are = (
			!(period1.active_monday.equals(period2.active_monday)) && 
			!(period1.active_tuesday.equals(period2.active_tuesday)) && 
			!(period1.active_wednesday.equals(period2.active_wednesday)) && 
			!(period1.active_thursday.equals(period2.active_thursday)) && 
			!(period1.active_friday.equals(period2.active_friday)) && 
			!(period1.active_saturday.equals(period2.active_saturday)) && 
			!(period1.active_sunday.equals(period2.active_sunday)));
		return (are);
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
	
	/** calculates the "from" date or, if needed, the complementary dates for the new period to be inserted:<br>
	 * - if there are no periods, the "from" date matches the "from" contract date<br>
	 * - if the last period has all the week days active, the "from" date matches the "to" date from the last period<br>
	 * - if it hasn't all the week days active, the new period is complementary: it has the same "from" and "to" dates
	 * and the active dates are the ones not active on the last period
	 * @return a Period object with the results
	 */
	public Period getInsertPeriod() {
		Period period = new Period();
		
		if (this.state.equals("not_generated")) {
			Period last_period = new Period();
			Period before_period = new Period();
			
			if (this.hasPeriods()) {
				last_period = this.getLastPeriod();
				if (this.hasDaysLeft(last_period)) {
					if (this.hasMoreThanOnePeriod()) {
						
						// checks if all the dates are "filled" with the last and the before last periods, in that
						// case, the insert period info will be clean (next)
						before_period = this.getBeforeLastPeriod();
						boolean dates_match = 
							((last_period.from_date.equals(before_period.from_date))
								&& (last_period.to_date.equals(before_period.to_date)));
						
						if ((dates_match) && 
								(this.areComplementary(last_period, before_period))) {
							period.insertType = "next";
						} else {
							period.insertType = "complementary";
						}
						
					} else {
						period.insertType = "complementary";
					}
				} else {
					period.insertType = "next";
				}
			} else {
				period.insertType = "new";
			}
			
			if (period.insertType == "new") {
				period.from_date = this.from_date;
			}
			
			if (period.insertType == "next") {
				period.from_date = last_period.to_date;
			}
			
			if (period.insertType == "complementary") {
				period.from_date = last_period.from_date;
				period.to_date = last_period.to_date;
				
				period.active_monday = not(last_period.active_monday);
				period.active_tuesday = not(last_period.active_tuesday);
				period.active_wednesday = not(last_period.active_wednesday);
				period.active_thursday = not(last_period.active_thursday);
				period.active_friday = not(last_period.active_friday);
				period.active_saturday = not(last_period.active_saturday);
				period.active_sunday = not(last_period.active_sunday);
				
				period.arrival_monday = last_period.arrival_monday;
				period.arrival_tuesday = last_period.arrival_tuesday;
				period.arrival_wednesday = last_period.arrival_wednesday;
				period.arrival_thursday = last_period.arrival_thursday;
				period.arrival_friday = last_period.arrival_friday;
				period.arrival_saturday = last_period.arrival_saturday;
				period.arrival_sunday = last_period.arrival_sunday;
				
				// last period info
				period.id = last_period.id;
				period.contract = this.id;
				period.minimum_stay = last_period.minimum_stay;
				
				period.short_stay_suplement = last_period.short_stay_suplement;
				period.short_stay_percentage = last_period.short_stay_percentage;
			}
		} else {
			period.insertType = "next";
			period.from_date = "";
		}
		
		return (period);
	}
	
	/** checks if this property room is deactivated for this contract
	 * @param room_id
	 * @param contract_id
	 * @return true if it's deactivated
	 */
	public boolean isDeactivatedRoomContract(String room_id, String contract_id) {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+this.tables_modifyer+"[not_active_room_contract] " +
			"where room_narc='"+room_id+"' " +
			"and  contract_narc='"+contract_id+"'";			
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	/** to deactivate a room, a new record is inserted on the "not_active_room_contract" table
	 * @param room_id
	 * @param contract_id
	 * @return true if there were no errors on the operation
	 */
	public boolean deactivateRoom(String room_id, String contract_id) {
		this.get(contract_id);
		connect();
		boolean valid = true;
		if (!this.isDeactivatedRoomContract(room_id, contract_id)) {
				try {
					// old system: if the contract is generated, insert an stop sale
					Contract contract = new Contract();
					contract.get(contract_id);
					if (contract.state.equals("active") || contract.state.equals("inactive")) {
						Room room = new Room();
						room.get(room_id);
						Converter converter = new Converter();
						converter.setContract(contract_id);
						converter.insertStopSale(room, true);
					}	
					
					// insert a mark as the room is deactivated
					SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[not_active_room_contract] values ('"
						+room_id+"','"+contract_id+"')";
					Statement sentencia = conexion.createStatement();
					sentencia.execute(SQL);
				} catch (Exception ex) {
					valid = false;
		      	}
		}
		return (valid);
	}
		
	/** deletes the record on the table "not_active_room_contract" that deactivates the room, 
	 * and if the prices for this room doesn't exist, sets a zero value for each period on the contract
	 * @param room_id
	 * @param contract_id
	 */
	public void activateRoom(String room_id, String contract_id) {
		this.get(contract_id);
		connect();
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		java.util.Enumeration e;
		Period period = new Period();
		
		try {			
			// if the prices for this room doesn't exist, set a zero value for each period on the contract
			
			data = list.getPeriods(contract_id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				period = (xcaping.Period)enum_data.nextElement();
				
				RoomPeriod roomPeriod = new RoomPeriod();
				roomPeriod.period_id = period.id;
				roomPeriod.room_id = room_id;
				roomPeriod.price = "0";
				roomPeriod.percentage = "0";
				roomPeriod.unit = "0";
				roomPeriod.allotment = "0";
				roomPeriod.release = "0";
				roomPeriod.create();
				
			}			
			
			// activate the room
			
			SQL = "delete from "+this.tables_modifyer+"[not_active_room_contract] " +
				"where room_narc='"+room_id+"' " +"and contract_narc='"+contract_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	/** changes the contract state to the value of the parameter "value"
	 * @param contract_id
	 * @param value
	 */
	public void switchActive(String contract_id, String value) {
		connect();
		try {
			SQL = "update "+this.tables_modifyer+"[contract] set state='"+value+"' "+
				"where contract_id='"+contract_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
      	this.getURLs(contract_id, this.property);
	}
	
	/** obtains the year from the contract's "from" date; this is needed to set the galas year
	 * @param id
	 * @return int value with the year
	 */
	public int getYear(String id) {
		int year = 0;
		connect();
		try {
			SQL = "select from_date_contract from "+this.tables_modifyer+"[contract] where contract_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
       		result = sentencia.executeQuery(SQL);
      		result.next();
			String from_date = result.getString("from_date_contract");
			
			String[] array = new String[3];
			String date = "";
			date = from_date.substring(0, 10);
			array = date.split("-");
			year = Integer.parseInt(array[0]);
				
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return year;
	}
	
	/** creates and inserts a bulk contract with the dates of this contract; this contract has a total stop sale:
	 * for all the rooms, from the start to the end of the contract 
	 * @return true if there were no errors on the operations
	 */
	public boolean insertContract_stopsale() {
		connect();
		boolean valid = true;
		this.name = "total stop sale "+this.from_date+" - "+this.to_date;
		this.arrival_monday = "1";
		this.arrival_tuesday = "1";
		this.arrival_wednesday = "1";
		this.arrival_thursday = "1";
		this.arrival_friday = "1";
		this.arrival_saturday = "1";
		this.arrival_sunday = "1";
		this.markup = "1";
		this.free_sale = "1";
		this.cot_suplement = "0";
		this.infant_age = "0";
		this.child_age = "0";
		this.text = "";
		this.currency = "4";
		this.sales_period = "0";
		this.commissionable = "0";
		this.state = "not_generated";
		this.offer = "0";
		
		if (validate()) {
			try {
	          	Statement sentencia = conexion.createStatement();
	          	
	          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[contract] values ('"
	          	+this.name+"','"+this.property
				+"','"+this.supplier+"','"+this.from_date
				+"','"+this.to_date+"','"+this.arrival_monday
				+"','"+this.arrival_tuesday+"','"+this.arrival_wednesday
				+"','"+this.arrival_thursday+"','"+this.arrival_friday
				+"','"+this.arrival_saturday+"','"+this.arrival_sunday
				+"','"+this.markup+"','"+this.free_sale
				+"','"+this.cot_suplement+"','"+this.infant_age
				+"','"+this.child_age+"','"+this.text
				+"','"+this.currency+"','"+this.sales_period
				+"', '"+this.commissionable+"', '"+this.state
				+"', '"+this.edited_by+"', '"+this.offer+"')";
	      		sentencia.execute(SQL);
	      		
	      		SQL = " SELECT @@identity AS contractID from "+this.tables_modifyer+"[contract]";
	          	
	      		ResultSet result = sentencia.executeQuery(SQL);
	      		result.next();
	      		this.id = result.getString("contractID");
	      	} catch (Exception ex) {
	      		this.error = ex.toString();
	      		valid = false;
	      	}
		} else {
			valid = false;
		}
		disconnect();
		// insert total stop sale
		String output;
		java.text.SimpleDateFormat formatter;
		java.util.Date today = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd MM yy");
		output = formatter.format(today);
		
		StopSale stop_sale = new StopSale();
		stop_sale.from_date = this.from_date;
		stop_sale.to_date = this.to_date;
		stop_sale.room = "0";
		stop_sale.edited_by = this.edited_by;
		stop_sale.edit_date = output;
		
		stop_sale.insert(this.id);
		
		this.getURLs("0", this.property);
		return (valid);
	}
	
	/** tells if there is child discount for this contract
	 * @param contract_id
	 * @return true if the contract has child discount
	 */
	public boolean hasChildDiscount(String contract_id) {
		boolean has = false;
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[discount_contract] " +
					"where contract_disc_con='"+contract_id+"' and discount_disc_con=1";
          	Statement sentencia = conexion.createStatement();
       		result = sentencia.executeQuery(SQL);
      		if (result.next()) { has = true; }
      	} catch (Exception ex) {
      		
      	}
      	disconnect();
      	return has;
	}
	
	/** tells if there is adult discount for this contract
	 * @param contract_id
	 * @return true if the contract has adult discount
	 */
	public boolean hasAdultDiscount(String contract_id) {
		boolean has = false;
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[discount_contract] " +
					"where contract_disc_con='"+contract_id+"' and discount_disc_con=4";
          	Statement sentencia = conexion.createStatement();
       		result = sentencia.executeQuery(SQL);
      		if (result.next()) { has = true; }
      	} catch (Exception ex) {
      		
      	}
      	disconnect();
      	return has;
	}
	
	/** obtains all the contract's information, and if it has finished (the "to" date is in the past), 
	 * it's state is changed to "finished"
	 * @param contract_id
	 */
	public void getProperty(String contract_id) {
		connect();
		try {
			SQL = "select property_contract from "+this.tables_modifyer+"[contract] " +
					"where contract_id='"+contract_id+"'";
          	Statement sentencia = conexion.createStatement();
       		result = sentencia.executeQuery(SQL);
      		result.next();
			this.property = result.getString("property_contract");
      	} catch (Exception ex) {
      		this.error = ex.toString();
      	}
      	this.getURLs(contract_id, this.property);
      	disconnect();
	}
	
	/** obtains the first period's minimum stay, to fill in the add-ons information (really needed???)
	 * @param contract_id
	 */
	public String getMinimumStay(String contract_id) {
		String ms = "";
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[period] where contract_period='"
				+contract_id+"'";
			Statement sentencia = conexion.createStatement();
			result = sentencia.executeQuery(SQL);
			if (result.next()) {
				ms = result.getString("minimum_stay");
			}
      	} catch (Exception ex) {
      		this.error = ex.toString();
      	}
      	disconnect();
      	return (ms);
	}
	
	/** obtains dynamically the URL's for the web links
	 * @param contract_id
	 * @param property_id
	 */
	public void getURLs (String contract_id, String property_id) {
		this.returnURL = "index.jsp?content=contract&contract_id="+contract_id;
		this.propertyURL = "index.jsp?content=item&item_id="+property_id+"&item_type=5";
		this.periodsURL = this.returnURL+"&subcontent=periods";
		this.offersURL = this.returnURL+"&subcontent=offers";
		this.configurationURL = this.returnURL+"&subcontent=contract_configuration";
		this.editURL = this.returnURL+"&subcontent=contract_edit";
		this.active_roomsURL = this.returnURL+"&subcontent=contract_rooms";
		this.stop_salesURL = this.returnURL+"&subcontent=stop_sales";
		this.allocationsURL = this.returnURL+"&subcontent=allocations";
		this.availabilitiesURL = this.returnURL+"&item_id="+property_id+"&subcontent=availabilities&history=0";
		this.pricelinesURL = this.returnURL+"&item_id="+property_id+"&subcontent=pricelines&history=0";
		this.add_onsURL = this.returnURL+"&subcontent=contract_add_ons";
		
		this.errorURL = this.returnURL+"&subcontent=error&error=";
	}
}
