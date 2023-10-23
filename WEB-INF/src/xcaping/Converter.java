package xcaping;

import java.sql.*;
import java.util.*;

/** <div align="center"><strong>contract's information converter</strong></div>
 * <p>description: converts the information of the contracts 
 * stored on the xcaping's database structure 
 * to the old structure --> so it's ready to be accessed by 
 * the other applications of the system (web page, bookings system,...)</p>
 * @author carlos nieves lameiro
 * @version 1.2
 */

public class Converter extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;

	public String error = "error generating the pricing " +
		"and availability, please verify the contract " +
		"data and try again";
	
	private Priceline priceline;
	private Prices prices;
	private Contract contract;
	private Property property;	
	private RoomSupplement roomSupplement;
	
	private String net_rates = "1";
	private String supplement = "2";
		private String sea_view = "10";
		private String short_stay = "40";
		private String baby_cot = "51";
		private String gala_dinner = "48";
		private String add_on = "49";
		//private String air_conditioned = "???";
	private String offer = "3";
	
	public String contract_id = "";
	public Vector availabilities = new Vector();
	public Vector pricing = new Vector();
	public Vector stopSales = new Vector();
	
	public String period_id = ""; // id of the complementary (active days) offer
	private Markup markup = new Markup();
	private Validation validation = new Validation();
	private String today = "";
	boolean valid = true;
	
	public Converter() {
		super();
		java.text.SimpleDateFormat formatter;
		java.util.Date date = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd MM yy");
		this.today = formatter.format(date);
		
		this.today = validation.getSQLDate(this.today);
	}	
	
	private boolean hasValue(String value) {
		return ((!value.equals(null)) && (!value.equals("0.00")) && (!value.equals("0") && (!value.equals(""))));
	}
	
	private boolean isNotNull(String value) {
		return (!value.equals(null));
	}
	
	/** post edit: before inserting add ons, and baby cot, the contract variable must contain data
	 * @param contract_id
	 */
	public void setContract(String contract_id) {
		Contract contract = new Contract();
		contract.get(contract_id);
		this.contract = contract;
		
		Markup markup = new Markup();
		markup.get(contract.markup);
		this.markup = markup;
	}
	
	/**
	 * gets (calculates) availability lines from xcaping's contracts structure
	 * , and generates the sql scripts to insert them.<br>
	 * if insert is "true", the sql scripts are executed, and the availability lines, inserted
	 * @param contract_id the contract id to take the data from
	 * @param insert if "true", the availability lines are iserted
	 * @return true if there are no errors on the conversion, false in other case
	 */
	
	public boolean getAvailabilities(String contract_id, boolean insert) {
		
		valid = true;
		java.util.Enumeration e;
		java.util.Enumeration e2;
		Contract contract = new Contract();
		contract.get(contract_id);
		this.contract = contract;
		try {
			Lists list = new Lists();
			java.util.Vector data_periods = new java.util.Vector();
			data_periods = list.getPeriods(contract_id);
			Availability availability = new Availability();
			
			if (data_periods.size() != 0) {
				
				//Prices prices = new Prices();
				Period period = new Period();
				User user = new User();
				RoomPeriod roomPeriod = new RoomPeriod();
				
				java.util.Vector data_rooms = new java.util.Vector();
				data_rooms = list.getRoomsContract(contract_id, false);
				Room room = new Room();
						
				for (e = data_rooms.elements() ; e.hasMoreElements() ;) {
					room = (xcaping.Room)e.nextElement();
					
					if (room.active) {
						data_periods.firstElement();
						
						String release = "";
						
						for (e2 = data_periods.elements() ; e2.hasMoreElements() ;) {
							period = (xcaping.Period)e2.nextElement();
							
							if (!period.firstComplementary || period.offer.equals("1")) {
								roomPeriod = new RoomPeriod();
								roomPeriod.get(period.id, room.id);
								
								availability = new Availability();
								availability.idColumn = "";
								availability.fromDate = period.from_date;
								availability.toDate = period.to_date;
								availability.allocation = roomPeriod.allotment;
								availability.freeSale = this.contract.free_sale;
								availability.releasePeriod = roomPeriod.release;
								if (!hasValue(period.short_stay_suplement)) {
									availability.minimumStay = period.minimum_stay;
								} else {
									availability.minimumStay = "1";					
								}
								
								availability.roomNameID = roomPeriod.room_id;
								availability.propID = this.contract.property;
								availability.supplier = this.contract.supplier;
								availability.active = period.active;
								availability.stopStart = "1";
								availability.editDate = period.edit_date;
								availability.editBy = period.edited_by;
								
								availability.roomName = room.name;
								
								release = roomPeriod.release;
								if (period.offer.equals("1")) {
									this.deleteAvailability(this.contract.property, period.from_date, period.to_date, room.id, false);
								}
								this.processAvailability(availability, insert);
							}
						}
					} else { // if inactive: stop sale for all the contract
						this.insertStopSale(room, insert);					
					}
				}
			}			
		} catch (Exception ex) {
			valid = false;
		}
		
		return (valid);
	}
	
	public void insertStopSale(Room room, boolean insert) {
		Availability availability = new Availability();
		availability.fromDate = contract.from_date;
		availability.toDate = contract.to_date;
		availability.roomNameID = room.id;
		
		availability.allocation = "0";
		availability.freeSale = "0";
		availability.releasePeriod = "0";
		availability.minimumStay = "0";
		availability.propID = contract.property;
		availability.supplier = contract.supplier;
		availability.active = "1";
		availability.stopStart = "1";
		availability.editDate = this.today;
		availability.editBy = contract.edited_by;
		
		availability.roomName = room.name;
		
		this.processAvailability(availability, insert);
	}
	
	/**
	 * gets and inserts the availability lines for a property stop sale.<br>
	 * if it's a total stopsale (all the rooms), inserts an availability line for each room
	 * @param property_id the property the stop sale belongs to
	 * @param stop_sale_id the stop sale to work with
	 * @return true if there are no errors on the conversion, false in other case
	 */
	
	public boolean insertAvailabilitiesStopSale(String stop_sale_id) {
		
		valid = true;
		try {
			StopSale stopSale = new StopSale();
			stopSale.get(stop_sale_id);
			stopSale.validate();
			Availability availability = new Availability();
			
			Lists list = new Lists();
			java.util.Vector data = new java.util.Vector();
			
			java.util.Enumeration enum_data_rooms;
			Room room = new Room();
			
			if (stopSale.room.equals("0")) {
				
				java.util.Vector data_rooms = new java.util.Vector();
				data_rooms = list.getRoomsProperty(stopSale.property);
				room = new Room();
				
				for (enum_data_rooms = data_rooms.elements() ; enum_data_rooms.hasMoreElements() ;) {
					room = (xcaping.Room)enum_data_rooms.nextElement();
					
					this.insertStopSalesContracts(stopSale, room.id);
				}
				
			} else {
				
				this.insertStopSalesContracts(stopSale, stopSale.room);
			}
		} catch (Exception ex) {
			valid = false;
		}
		
		return (valid);	
	}
	
	private boolean insertStopSalesContracts (StopSale stop_sale, String room_id) {
		boolean valid = true;
		
		Lists list = new Lists();
		Contract contract = new Contract();
		java.util.Vector data = new java.util.Vector();
		data = list.getContracts(stop_sale.property);
		
		Availability availability = new Availability();
		
		Room room = new Room();
		room.get(stop_sale.room);
		
		// convert string dates to date objects
		java.util.Date stop_sale_from_date;
		java.util.Date stop_sale_to_date;
		java.util.Date contract_from_date;
		java.util.Date contract_to_date;
		
		String pattern="yyy-MM-dd";
		java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat(pattern);
		
		stop_sale_from_date = sdf.parse(validation.getSQLDate(stop_sale.from_date), new java.text.ParsePosition(0));
		stop_sale_to_date = sdf.parse(validation.getSQLDate(stop_sale.to_date), new java.text.ParsePosition(0));
		// ---------------convert string dates to date objects
		
		java.util.Enumeration e;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			contract = (Contract)e.nextElement();
			
			contract_from_date = sdf.parse(validation.getSQLDate(contract.from_date), new java.text.ParsePosition(0));
			contract_to_date = sdf.parse(validation.getSQLDate(contract.to_date), new java.text.ParsePosition(0));
			
			availability = new Availability();
			availability.roomNameID = room_id;
			if (stop_sale_to_date.after(contract_from_date)) {
				if (!stop_sale_from_date.after(contract_to_date)) {
					if (contract_from_date.equals(stop_sale_from_date) || contract_from_date.after(stop_sale_from_date)) {
						availability.fromDate = validation.getSQLDate(contract.from_date);
					} else {
						availability.fromDate = stop_sale.from_date;
					}
					if (stop_sale_to_date.equals(contract_to_date) || stop_sale_to_date.after(contract_to_date)) {
						availability.toDate = validation.getSQLDate(contract.to_date);
					} else {
						availability.toDate = stop_sale.to_date;
					}
					
					availability.allocation = "0";
					availability.freeSale = "0";
					availability.releasePeriod = "0";
					availability.minimumStay = "0";
					availability.propID = stop_sale.property;
					availability.supplier = contract.supplier;
					availability.active = "1";
					availability.stopStart = "1";
					availability.editDate = stop_sale.edit_date;
					availability.editBy = stop_sale.edited_by;
					
					availability.roomName = room.name;
					
					insertAvailability(availability);
				}
			}
		}		
		return (valid);
	}
	
	/**
	 * deletes the availabilities lines for a property stop sale.<br>
	 * if it's a total stopsale (all the rooms), deletes an availability line for each room
	 * @param property_id the property the stop sale belongs to
	 * @param stop_sale_id the stop sale to work with
	 * @return true if there are no errors on the conversion, false in other case
	 */
	
	public boolean deleteAvailabilitiesStopSale(String stop_sale_id) {
		
		valid = true;
		try {
			StopSale stopSale = new StopSale();
			stopSale.get(stop_sale_id);
			stopSale.validate();
			Availability availability = new Availability();
			
			Lists list = new Lists();
			java.util.Vector data = new java.util.Vector();
			
			java.util.Enumeration enum_data_rooms;
			Room room = new Room();
			
			if (stopSale.room.equals("0")) {
				
				java.util.Vector data_rooms = new java.util.Vector();
				data_rooms = list.getRoomsProperty(stopSale.property);
				room = new Room();
				
				for (enum_data_rooms = data_rooms.elements() ; enum_data_rooms.hasMoreElements() ;) {
					room = (xcaping.Room)enum_data_rooms.nextElement();
					
					this.deleteStopSalesContracts(stopSale, room.id);
				}
				
			} else {
				
				this.deleteStopSalesContracts(stopSale, stopSale.room);
			}
		} catch (Exception ex) {
			valid = false;
		}
		
		return (valid);	
	}
	
	private boolean deleteStopSalesContracts (StopSale stop_sale, String room_id) {
		boolean valid = true;
		
		Lists list = new Lists();
		Contract contract = new Contract();
		java.util.Vector data = new java.util.Vector();
		data = list.getContracts(stop_sale.property);
		
		// convert string dates to date objects
		java.util.Date stop_sale_from_date;
		java.util.Date stop_sale_to_date;
		java.util.Date contract_from_date;
		java.util.Date contract_to_date;
		
		String pattern="yyy-MM-dd";
		java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat(pattern);
		
		stop_sale_from_date = sdf.parse(validation.getSQLDate(stop_sale.from_date), new java.text.ParsePosition(0));
		stop_sale_to_date = sdf.parse(validation.getSQLDate(stop_sale.to_date), new java.text.ParsePosition(0));
		// ---------------convert string dates to date objects
		
		String from_date = "";
		String to_date = "";
		
		java.util.Enumeration e;
		for (e = data.elements() ; e.hasMoreElements() ;) {
			contract = (Contract)e.nextElement();
			
			contract_from_date = sdf.parse(validation.getSQLDate(contract.from_date), new java.text.ParsePosition(0));
			contract_to_date = sdf.parse(validation.getSQLDate(contract.to_date), new java.text.ParsePosition(0));
			
			if (stop_sale_to_date.after(contract_from_date)) {
				if (!stop_sale_from_date.after(contract_to_date)) {
					if (contract_from_date.equals(stop_sale_from_date) || contract_from_date.after(stop_sale_from_date)) {
						from_date = validation.getSQLDate(contract.from_date);
					} else {
						from_date = stop_sale.from_date;
					}
					if (stop_sale_to_date.equals(contract_to_date) || stop_sale_to_date.after(contract_to_date)) {
						to_date = validation.getSQLDate(contract.to_date);
					} else {
						to_date = stop_sale.to_date;
					}
					
					this.deleteAvailability(stop_sale.property, from_date, to_date, room_id, true);
				}
			}
		}		
		return (valid);
	}
	
	/**
	 * directly inserts the availability lines from the information from a period
	 * @param period the period to work with
	 * @return true if there are no errors on the conversion, false in other case
	 */
	
	public boolean insertAvailabilities(Period period) {
		
		valid = true;
		java.util.Enumeration e;
		java.util.Enumeration e2;
		try {
			Lists list = new Lists();
			Availability availability = new Availability();
			Contract contract = new Contract();
			
			contract.get(period.contract);

			User user = new User();
			RoomPeriod roomPeriod = new RoomPeriod();
			
			java.util.Vector data_rooms = new java.util.Vector();
			data_rooms = list.getRoomsContract(contract.id, true);
			Room room = new Room();
					
			for (e = data_rooms.elements() ; e.hasMoreElements() ;) {
				room = (xcaping.Room)e.nextElement();
				
				String release = "";
				roomPeriod = new RoomPeriod();
				roomPeriod.get(period.id, room.id);
				
				availability = new Availability();
				availability.idColumn = "";
				availability.fromDate = period.from_date;
				availability.toDate = period.to_date;
				availability.allocation = roomPeriod.allotment;
				availability.freeSale = contract.free_sale;
				availability.releasePeriod = roomPeriod.release;
				if (!hasValue(period.short_stay_suplement)) {
					availability.minimumStay = period.minimum_stay;
				} else {
					availability.minimumStay = "1";					
				}
				
				availability.roomNameID = roomPeriod.room_id;
				availability.propID = contract.property;
				availability.supplier = contract.supplier;
				availability.active = period.active;
				availability.stopStart = "1";
				availability.editDate = period.edit_date;
				availability.editBy = period.edited_by;
				
				availability.roomName = room.name;
				
				release = roomPeriod.release;
				this.processAvailability(availability, true);				
			}			
			
		} catch (Exception ex) {
			valid = false;
		}
		
		return (valid);
	}
	
	/**
	 * gets (calculates) price lines from xcaping's contracts structure:<br>
	 * - from the periods:
	 * <ol type="a">
	 * 	<li>net rates
	 * 	<li>short stay supplement
	 * 	<li>offers: re-inserts them one by one
	 * </ol>
	 * - from the contract and property information:
	 * <ol type="a">
	 * 	<li>baby cot supplement
	 * 	<li>add-ons
	 * </ol>
	 * , and generates the sql scripts to insert them.<br>
	 * if insert is "true", the sql scripts are executed, and the price lines, inserted
	 * @param contract_id the contract id to take the data from
	 * @param insert if "true", the price lines are iserted
	 * @return true if there are no errors on the conversion, false in other case
	 */
	public boolean getPricing(String contract_id, boolean insert) {
		
	valid = true;
	try {
		// variables definition
		Lists list = new Lists();
		Period period;
		
		// common variables for supplements
		String edit_by = "";
		String edit_date = "";
		String minStay = "";
		
		xcaping.BoardBasisPeriod board_basisPeriod = new xcaping.BoardBasisPeriod();
		xcaping.DiscountPeriod discountPeriod = new xcaping.DiscountPeriod();
		xcaping.RoomPeriod roomPeriod = new xcaping.RoomPeriod();
		
		// data gathering: contract and property
		contract = new Contract();
		contract.get(contract_id);
		/*property = new Property();
		property.get(contract.property);*/
		markup.get(contract.markup);
		
		java.util.Vector data = new java.util.Vector();
		java.util.Enumeration enum_data;
		
		data = list.getPeriods(contract_id);
		
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			period = (xcaping.Period)enum_data.nextElement();
			
			// from ---------------- net rates
			
			priceline = new Priceline();
			
			if (!period.offer.equals("1")) {
				
				priceline.priceType = net_rates;
				priceline.supplement = "0";
				
				// data filling: common data to all lines (contract and property)
				this.fillCommonData();
					// period arrival days prevail over the contract ones
					priceline.arrivalMonday = period.arrival_monday;
					priceline.arrivalTuesday = period.arrival_tuesday;
					priceline.arrivalWednesday = period.arrival_wednesday;
					priceline.arrivalThursday = period.arrival_thursday;
					priceline.arrivalFriday = period.arrival_friday;
					priceline.arrivalSaturday = period.arrival_saturday;
					priceline.arrivalSunday = period.arrival_sunday;
					
				// fill data from the period
				priceline.fromDate = period.from_date;
				priceline.toDate = period.to_date;
				priceline.introDate = period.edit_date;
				priceline.activeMonday = period.active_monday;
				priceline.activeTuesday = period.active_tuesday;
				priceline.activeWednesday = period.active_wednesday;
				priceline.activeThursday = period.active_thursday;
				priceline.activeFriday = period.active_friday;
				priceline.activeSaturday = period.active_saturday;
				priceline.activeSunday = period.active_sunday;
				priceline.minStayTrig = period.minimum_stay;
				priceline.active = period.active;
				priceline.editBy = period.edited_by;
				priceline.agentGroup = period.agent_group;
				
				// data fill for the supplement lines
				edit_by = period.edited_by;
				edit_date = period.edit_date;
				minStay = period.minimum_stay;
				
				this.fillPrices_Discounts_Rooms(period.id, contract.id, "0.00", "0.00");
				
				if (!this.processPriceline(priceline, insert)) {
					valid = false;
				}
				
				// to --------------- net rates
				
				// from ------------- short_stay
				
				if (hasValue(period.short_stay_suplement)) {
					
					priceline = new Priceline();
					priceline.priceType = this.supplement;
					priceline.supplement = short_stay;
								
					this.fillCommonData();
					
					// fill data from the period
					priceline.fromDate = period.from_date;
					priceline.toDate = period.to_date;
					priceline.introDate = period.edit_date;
					priceline.activeMonday = period.active_monday;
					priceline.activeTuesday = period.active_tuesday;
					priceline.activeWednesday = period.active_wednesday;
					priceline.activeThursday = period.active_thursday;
					priceline.activeFriday = period.active_friday;
					priceline.activeSaturday = period.active_saturday;
					priceline.activeSunday = period.active_sunday;
					priceline.minStayTrig = period.minimum_stay;
					priceline.active = period.active;
					priceline.editBy = period.edited_by;
					priceline.agentGroup = period.agent_group;
					
					this.fillPrices_Discounts_Rooms(period.id, contract.id, period.short_stay_suplement, period.short_stay_percentage);
					
					if (!this.processPriceline(priceline, insert)) {
						valid = false;
					}
				}
				
			}
			// to --------------- short_stay
		}
		
		
		// baby cot
		
		if (hasValue(contract.cot_suplement)) {
			if (!this.insertBabyCot(insert)) {
				valid = false;
			}
		}
		
		// add ons
		
		java.util.Vector data_add_ons = new java.util.Vector();
		Lists list_add_ons = new Lists();
		
		AddOn add_on = new AddOn();
		data_add_ons = list_add_ons.getAddOnsContract(contract_id);
		
		for (enum_data = data_add_ons.elements() ; enum_data.hasMoreElements() ;) {
			add_on = (xcaping.AddOn)enum_data.nextElement();
			
			if (!this.insertAddOn(add_on, insert)) {
				valid = false;
			}
		}
		
		// offers
		
		java.util.Vector data_offers = new java.util.Vector();
		Lists list_offers = new Lists();
		
		Period offer = new Period();
		data_offers = list_offers.getOffers(contract_id);
		
		for (enum_data = data_offers.elements() ; enum_data.hasMoreElements() ;) {
			offer = (Period)enum_data.nextElement();
			this.activateOffer(offer, offer.hasComplementary);
		}
		
	} catch (Exception ex) {
		valid = false;
		this.error = ex.toString();
	}
	
	return (valid);	
		
	}
	
	// ------------------------- filling methods to help with the get & insert --------------------
	
	private void fillCommonData() {
		priceline.propID = contract.property;
		priceline.supplierID = contract.supplier;
		priceline.exchangeID = contract.currency;
		priceline.markup = markup.markup;
		priceline.commissionable = contract.commissionable;
		priceline.arrivalMonday = contract.arrival_monday;
		priceline.arrivalTuesday = contract.arrival_tuesday;
		priceline.arrivalWednesday = contract.arrival_wednesday;
		priceline.arrivalThursday = contract.arrival_thursday;
		priceline.arrivalFriday = contract.arrival_friday;
		priceline.arrivalSaturday = contract.arrival_saturday;
		priceline.arrivalSunday = contract.arrival_sunday;
		priceline.salesPeriod = contract.sales_period;
		priceline.childAge = contract.child_age;
		priceline.refNumber = contract.property + " - " + contract.name;
		priceline.contractGuarantee = "1";
		priceline.infantAge = contract.infant_age;
	}
	
	// ---------------- new
	private void fillPrices_Discounts_Rooms(String period_id, String contract_id, String supplement, String supplement_percentage) {
		
		boolean has_supplement = hasValue(supplement);
		boolean is_supplement_percentage = supplement_percentage.equals("1");
		double modifyer;
		double price;
		String stringPrice = "";		
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		data = list.getBoardBasesContract(contract_id);
		BoardBasis board_basis = new BoardBasis();
		BoardBasisPeriod board_basisPeriod = new BoardBasisPeriod();
		
		priceline.selfCatering = "0";
		priceline.roomOnly = "0";
		priceline.bedBreakfast = "0";
		priceline.halfBoard = "0";
		priceline.fullBoard = "0";
		priceline.allInclusive = "0";
		
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			board_basis = (xcaping.BoardBasis)enum_data.nextElement();

			if (has_supplement) {
				if (is_supplement_percentage) {
					board_basisPeriod = new BoardBasisPeriod();
					board_basisPeriod.get(period_id, board_basis.id);
					price = Double.parseDouble(board_basisPeriod.price) * (Double.parseDouble(supplement)/100);
					price = Math.round(price*100) / 100.00; 
				} else {
					price = Double.parseDouble(supplement);
				}
			} else {
				board_basisPeriod = new BoardBasisPeriod();
				board_basisPeriod.get(period_id, board_basis.id);
				price = Double.parseDouble(board_basisPeriod.price);
			}
			stringPrice = Double.toString(price);
			switch (Integer.parseInt(board_basis.id)) {
	        	case 1:  priceline.selfCatering = stringPrice; break;
	        	case 2:  priceline.roomOnly = stringPrice; break;
	            case 3:  priceline.bedBreakfast = stringPrice; break;
	            case 4:  priceline.halfBoard = stringPrice; break;
	            case 5:  priceline.fullBoard = stringPrice; break;
	            case 6:  priceline.allInclusive = stringPrice; break;
	        }
		}
		
		data = list.getDiscountsContract(contract_id);
		Discount discount = new Discount();
		DiscountPeriod discountPeriod = new DiscountPeriod();
		
		priceline.disc1stChild = "0";
		priceline.disc2ndChild = "0";
		priceline.disc3rdChild = "0";
		priceline.disc3rdAdult = "0";
		priceline.disc4thAdult = "0";
		priceline.percentage1Child = "0";
		priceline.percentage2Child = "0";
		priceline.percentage3Child = "0";
		priceline.percentage3Adult = "0";
		priceline.percentage4Adult = "0";
		
		if (!has_supplement) {
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				discount = (xcaping.Discount)enum_data.nextElement();
				discountPeriod = new DiscountPeriod();
				discountPeriod.get(period_id, discount.id);

				switch (Integer.parseInt(discountPeriod.discount_id)) {
	        	case 1:  {
	        		priceline.disc1stChild = discountPeriod.amount;
	        		priceline.percentage1Child = discountPeriod.percentage;
	        		break;
	        	}
	        	case 2:  {
	        		priceline.disc2ndChild = discountPeriod.amount;
	        		priceline.percentage2Child = discountPeriod.percentage;
	        		break;
	        	}
	        	case 3:  {
	        		priceline.disc3rdChild = discountPeriod.amount;
	        		priceline.percentage3Child = discountPeriod.percentage;
	        		break;
	        	}
	        	case 4:  {
	        		priceline.disc3rdAdult = discountPeriod.amount;
	        		priceline.percentage3Adult = discountPeriod.percentage;
	        		break;
	        	}
	        	case 5:  {
	        		priceline.disc4thAdult = discountPeriod.amount;
	        		priceline.percentage4Adult = discountPeriod.percentage;
	        		break;
	        	}
				}
			}
		}
		
		data = list.getRoomsContract(contract_id, true);
		Room room = new Room();
		RoomPeriod roomPeriod = new RoomPeriod();
		String unit = "";
		
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			room = (xcaping.Room)enum_data.nextElement();
			roomSupplement = new RoomSupplement();
			roomSupplement.RoomID = room.id;
			roomSupplement.name = room.name;
			
			roomPeriod = new RoomPeriod();
			roomPeriod.get(period_id, room.id);
			
			if (has_supplement) {
				if (is_supplement_percentage) {
					price = Double.parseDouble(roomPeriod.price) * (Double.parseDouble(supplement)/100);
					price = Math.round(price*100) / 100.00;
					unit = roomPeriod.unit;
				} else {
					price = 0;
					unit = "0";
				}
			} else {				
				price = Double.parseDouble(roomPeriod.price);
				unit = roomPeriod.unit;
			}
			stringPrice = Double.toString(price);
			
			roomSupplement.RoomPrice = stringPrice;
			roomSupplement.pricePerUnitPerPerson = unit;
			
			priceline.rooms.add(roomSupplement);
		}
	}
	
	/* deprecated ------------------------------------------------------
	private void fillSupplement(String value) {
		
		priceline.selfCatering = "0";
		priceline.roomOnly = "0";
		priceline.bedBreakfast = "0";
		priceline.halfBoard = "0";
		priceline.fullBoard = "0";
		priceline.allInclusive = "0";

		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		BoardBasis board_basis = new BoardBasis();
		data = list.getBoardBasesContract(contract.id);
		
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			board_basis = (xcaping.BoardBasis)enum_data.nextElement();
			switch (Integer.parseInt(board_basis.id)) {
	    		case 1:  priceline.selfCatering = value; break;
	    		case 2:  priceline.roomOnly = value; break;
	    		case 3:  priceline.bedBreakfast = value; break;
	    		case 4:  priceline.halfBoard = value; break;
	    		case 5:  priceline.fullBoard = value; break;
	    		case 6:  priceline.allInclusive = value; break;
			}
		}
		fillDiscounts("0.00", "0.00");
	}*/
	
	private void fillDiscounts(String child_discount, String percentage_child, String adult_discount, String percentage_adult) { // used by add-ons conversion
		
		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		Discount discount = new Discount();
		if (hasValue(child_discount) || hasValue(adult_discount)) {
			if (!hasValue(child_discount)) { child_discount = "0"; }
			if (!hasValue(adult_discount)) { adult_discount = "0"; }
			data = list.getDiscountsContract(contract.id);
			for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
				discount = (xcaping.Discount)enum_data.nextElement();
				switch (Integer.parseInt(discount.id)) {
	    		case 1:  {
	    			priceline.disc1stChild = child_discount;
	    			priceline.percentage1Child = percentage_child;
	    			break;
	    		}
	    		case 2:  {
	    			priceline.disc2ndChild = child_discount;
	    			priceline.percentage2Child = percentage_child;
	    			break;
	    		}
	    		case 3:  {
	    			priceline.disc3rdChild = child_discount;
	    			priceline.percentage3Child = percentage_child;
	    			break;
	    		}
	    		case 4:  {
	    			priceline.disc3rdAdult = adult_discount;
	    			priceline.percentage3Adult = percentage_adult;
	    			break;
	    		}
	    		case 5:  {
	    			priceline.disc4thAdult = adult_discount;
	    			priceline.percentage4Adult = percentage_adult;
	    			break;
	    		}
			}
			}
		}
	}
	
	// ------------------------- inserting availabilities/pricelines ---------------------------
	
	private void processAvailability(Availability availability, boolean insert) {
		availabilities.add(availability);
		validation.validate(availability);
		
		//sqlAvailability(availability);
		if (insert) {
			this.insertAvailability(availability);
		}
	}

	/**takes the information from the object "availability", 
	 * and generates a record on the availability table
	 * @param availability the object availability, with all the info
	 */
	public void insertAvailability(Availability availability) {
		connect();
		try {
          	Statement sentencia = conexion.createStatement();     	
          	SQL = this.SQLprefix+"insert INTO "+this.tables_modifyer+"[AvailPropertiesTBL](" +
          	"[fromDate], [toDate], [allocation], [freeSale], " +
          	"[releasePeriod], [minimumStay], [roomNameID], " +
          	"[propID], [supplier], [active], [stopStart], " +
          	"[editDate], [editBy])" +
          	"VALUES( '"+availability.fromDate
			+"', '"+availability.toDate
			+"', '"+availability.allocation
			+"', '"+availability.freeSale
			+"', '"+availability.releasePeriod
			+"', '"+availability.minimumStay
			+"', '"+availability.roomNameID
			+"', '"+availability.propID
			+"', '"+availability.supplier
			+"', '"+availability.active
			+"', '"+availability.stopStart
			+"', '"+availability.editDate
			+"', '"+availability.editBy+"')";
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	private boolean processPriceline(Priceline priceline, boolean insert) {
		
		boolean valid = true;
		pricing.add(priceline);
		validation.validate(priceline);
		
		if (insert) {
			if (!this.insertPriceline(priceline)) {
				valid = false;
			}
		}
		this.sqlPriceline(priceline);
		return (valid);
	}
	
	/**takes the information from the object "priceline", 
	 * and generates a record on the price table<br>
	 * gets the identity value with @@identity (MSSQL)
	 * @param priceline the object priceline, with all the info
	 */	
	public boolean insertPriceline(Priceline priceline) {
		boolean valid = true;
		connect();
		try {
			ResultSet result;
			Statement sentencia = conexion.createStatement();
			
      		SQL = this.SQLprefix+"INSERT INTO [dbo].[PriceTBL]" +
      		"([fromDate], [toDate], [propID], [introDate], [supplierID], " +
      		"[agentGroup], [exchangeID], [markup], [priceType], [selfCatering], [roomOnly], " +
      		"[bedBreakfast], [halfBoard], [fullBoard], [allInclusive], [disc1stChild], " +
      		"[disc2ndChild], [disc3rdChild], [disc3rdAdult], [disc4thAdult], [" +
      		"percentage1Child], [percentage2Child], [percentage3Child], [percentage3Adult], " +
      		"[percentage4Adult], [activeMonday], [activeTuesday], [activeWednesday], " +
      		"[activeThursday], [activeFriday], [activeSaturday], [activeSunday], [minStayTrig], " +
      		"[active], [commissionable], [supplement], [arrivalMonday], [arrivalTuesday], " +
      		"[arrivalWednesday], [arrivalThursday], [arrivalFriday], [arrivalSaturday], " +
      		"[arrivalSunday], [salePeriod], [childAge], [editBy], [refNumber], [contractGuarantee], [infantAge]) " +
      		"VALUES('"+priceline.fromDate+"', '"+priceline.toDate+"', "+priceline.propID
			+", '"+priceline.introDate+"', "+priceline.supplierID+", "+priceline.agentGroup+", "+priceline.exchangeID
			+", "+priceline.markup+", "+priceline.priceType+", "
			+priceline.selfCatering+", "+priceline.roomOnly+", "+priceline.bedBreakfast+", "+priceline.halfBoard
			+", "+priceline.fullBoard+", "+priceline.allInclusive
			+", "+priceline.disc1stChild+", "+priceline.disc2ndChild+", "+priceline.disc3rdChild+", "
			+priceline.disc3rdAdult+", "+priceline.disc4thAdult+", "
			+priceline.percentage1Child+", "+priceline.percentage2Child+", "+priceline.percentage3Child
			+", "+priceline.percentage3Adult+", "+priceline.percentage4Adult
			+", "+priceline.activeMonday+", "+priceline.activeTuesday+", "+priceline.activeWednesday
			+", "+priceline.activeThursday+", "+priceline.activeFriday+", "+priceline.activeSaturday
			+", "+priceline.activeSunday
			+", "+priceline.minStayTrig+", "+priceline.active+", "+priceline.commissionable+", "+priceline.supplement
			+", "+priceline.arrivalMonday+", "+priceline.arrivalTuesday+", "+priceline.arrivalWednesday
			+", "+priceline.arrivalThursday+", "+priceline.arrivalFriday+", "+priceline.arrivalSaturday
			+", "+priceline.arrivalSunday
			+", "+priceline.salesPeriod+", "+priceline.childAge+", '"+priceline.editBy+"', '"+priceline.refNumber
			+"', "+priceline.contractGuarantee+", "+priceline.infantAge+")";
      		//SQL = SQL+" SELECT max(@@identity) AS pricePeriodID from [dbo].[PriceTBL]";
      		sentencia.executeUpdate(SQL);
      		SQL = "SELECT @@identity";
      		result = sentencia.executeQuery(SQL);
			result.next();
			priceline.pricePeriodID = String.valueOf(result.getInt(1));
      		
      		java.util.Enumeration e;
          	RoomSupplement room_supplement = new RoomSupplement();
    		
    		for (e = priceline.rooms.elements() ; e.hasMoreElements() ;) {
    			room_supplement = (xcaping.RoomSupplement)e.nextElement();
    			SQL = this.SQLprefix+"insert INTO "+this.tables_modifyer+"[RoomSupplementsTBL]" +
    			"([pricePeriodID], [RoomID], [RoomPrice], [pricePerUnitPerPerson]) " +
    			"VALUES ( '"+priceline.pricePeriodID+"', '"+
				room_supplement.RoomID+"', '"+
				room_supplement.RoomPrice+"', '"+
				room_supplement.pricePerUnitPerPerson+"')";
    			sentencia.execute(SQL);
    		}
    		
      	} catch (Exception ex) {
      		valid = false;
      		this.error = this.error+" - "+ex.toString();
      	}
      	disconnect();
      	return (valid);
	}	
	
	// ------------------------- sql chains generation (for checking) ---------------------------
	
	/** generates the sql chain to insert the availability, and appends it as a String on the variable SQL
	 * of the availability object. the final chain allows to check the insertions on generating contracts
	 * @param availability the availability object with all the info
	 */
	public void sqlAvailability(Availability availability) {
		try {    	
          	SQL = this.SQLprefix+"insert INTO "+this.tables_modifyer+"[AvailPropertiesTBL](" +
          	"[fromDate], [toDate], [allocation], [freeSale], " +
          	"[releasePeriod], [minimumStay], [roomNameID], " +
          	"[propID], [supplier], [active], [stopStart], " +
          	"[editDate], [editBy])" +
          	"VALUES( '"+availability.fromDate
			+"', '"+availability.toDate
			+"', '"+availability.allocation
			+"', '"+availability.freeSale
			+"', '"+availability.releasePeriod
			+"', '"+availability.minimumStay
			+"', '"+availability.roomNameID
			+"', '"+availability.propID
			+"', '"+availability.supplier
			+"', '"+availability.active
			+"', '"+availability.stopStart
			+"', '"+availability.editDate
			+"', '"+availability.editBy+"')";
          	availability.SQL = availability.SQL + "<br><br>" + SQL;
      	} catch (Exception ex) {
      	}
	}
	
	/** generates the sql chain to insert the priceline, and appends it as a String on the variable SQL
	 * of the priceline object. the final chain allows to check the insertions on generating contracts
	 * @param priceline the priceline object with all the info
	 */
	public void sqlPriceline(Priceline priceline) {
		try {   	
          	SQL = this.SQLprefix+"insert INTO "+this.tables_modifyer+"[PriceTBL]" +
          	"([fromDate], [toDate], [propID], [introDate], " +
          	"[supplierID], [agentGroup], [exchangeID], [markup], " +
          	"[priceType], [selfCatering], [roomOnly], " +
          	"[bedBreakfast], [halfBoard], [fullBoard], " +
          	"[allInclusive], [disc1stChild], [disc2ndChild], " +
          	"[disc3rdChild], [disc3rdAdult], [disc4thAdult], " +
          	"[percentage1Child], [percentage2Child], " +
          	"[percentage3Child], [percentage3Adult], " +
          	"[percentage4Adult], [activeMonday], " +
          	"[activeTuesday], [activeWednesday], " +
          	"[activeThursday], [activeFriday], " +
          	"[activeSaturday], [activeSunday], " +
          	"[minStayTrig], [active], [commissionable], " +
          	"[supplement], [arrivalMonday], [arrivalTuesday], " +
          	"[arrivalWednesday], [arrivalThursday], " +
          	"[arrivalFriday], [arrivalSaturday], " +
          	"[arrivalSunday], [salePeriod], [childAge], " +
          	"[editBy], [refNumber], [contractGuarantee], " +
          	"[infantAge])" +
          	"VALUES('"+priceline.fromDate+"', '"+priceline.toDate
			+"', '"+priceline.propID+"', '"+priceline.introDate
			+"', '"+priceline.supplierID+"', '"+priceline.agentGroup
			+"', '"+priceline.exchangeID+"', '"+markup.markup
			+"', '"+priceline.priceType+"', '"+priceline.selfCatering
			+"', '"+priceline.roomOnly+"', '"+priceline.bedBreakfast
			+"', '"+priceline.halfBoard+"', '"+priceline.fullBoard
			+"', '"+priceline.allInclusive
			+"', '"+priceline.disc1stChild+"', '"+priceline.disc2ndChild
			+"', '"+priceline.disc3rdChild+"', '"+priceline.disc3rdAdult
			+"', '"+priceline.disc4thAdult+"', '"+priceline.percentage1Child
			+"', '"+priceline.percentage2Child+"', '"+priceline.percentage3Child
			+"', '"+priceline.percentage3Adult+"', '"+priceline.percentage4Adult
			+"', '"+priceline.activeMonday+"', '"+priceline.activeTuesday
			+"', '"+priceline.activeWednesday+"', '"+priceline.activeThursday
			+"', '"+priceline.activeFriday+"', '"+priceline.activeSaturday
			+"', '"+priceline.activeSunday+"', '"+priceline.minStayTrig
			+"', '"+priceline.active+"', '"+priceline.commissionable
			+"', '"+priceline.supplement+"', '"+priceline.arrivalMonday
			+"', '"+priceline.arrivalTuesday+"', '"+priceline.arrivalWednesday
			+"', '"+priceline.arrivalThursday+"', '"+priceline.arrivalFriday
			+"', '"+priceline.arrivalSaturday+"', '"+priceline.arrivalSunday
			+"', '"+priceline.salesPeriod+"', '"+priceline.childAge
			+"', '"+priceline.editBy+"', '"+priceline.refNumber
			+"', '"+priceline.contractGuarantee
			+"', '"+priceline.infantAge+"')";
          	
          	priceline.SQL = priceline.SQL +  "<br><br>" + SQL;
          	
          	java.util.Enumeration e;
          	RoomSupplement room_supplement = new RoomSupplement();
    		
    		for (e = priceline.rooms.elements() ; e.hasMoreElements() ;) {
    			room_supplement = (xcaping.RoomSupplement)e.nextElement();
    			SQL = this.SQLprefix+"insert INTO "+this.tables_modifyer+"[RoomSupplementsTBL]" +
    			"([pricePeriodID], [RoomID], [RoomPrice], [pricePerUnitPerPerson]) " +
    			"VALUES ( '"+priceline.pricePeriodID+"', '"+
				room_supplement.RoomID+"', '"+
				room_supplement.RoomPrice+"', '"+
				room_supplement.pricePerUnitPerPerson+"')";
    			
    			priceline.SQL = priceline.SQL +  "<br>" + SQL;
    		}
          	
      	} catch (Exception ex) {
      	}
	}
	
	// ------------------------- POST - EDIT --------------------------------------------
	// ----------------------------------------------------------------------------------
	
	
	// ------------------------- manual insertions --------------------------------------------
	
	/**generated contracs edit: inserts a priceline for the cot supplement for this contract
	 * @param contract the object contract, with all the info
	 */
	public void insertCotSupplement(Contract contract) {
		try {
			priceline = new Priceline();
			priceline.priceType = this.supplement;
			priceline.supplement = this.baby_cot;
			
			// data filling: common data to all lines (contract and property)
			this.contract = contract;
			priceline.agentGroup = contract.agent_group;
			this.fillCommonData();
			
			// supplement: the whole contract
			priceline.fromDate = contract.from_date;
			priceline.toDate = contract.to_date;

			// default values
			priceline.activeMonday = "1";
			priceline.activeTuesday = "1";
			priceline.activeWednesday = "1";
			priceline.activeThursday = "1";
			priceline.activeFriday = "1";
			priceline.activeSaturday = "1";
			priceline.activeSunday = "1";
			priceline.active = "1";
			
			// data from net rates
			priceline.introDate = this.today;
			priceline.minStayTrig = "0"; // ------------ puede ser 0?
			priceline.editBy = contract.edited_by;
			
			this.fillPrices_Discounts_Rooms("0", contract.id, contract.cot_suplement, "0");
			
			this.processPriceline(priceline, true);
      	} catch (Exception ex) {
      	}
	}
	
	/**generated contracs edit: inserts a priceline for the short stay supplement for this period
	 * @param period the object period, with all the info
	 */
	public boolean insertShortStay(Period period) {
		
		boolean valid = true;
		priceline = new Priceline();
		priceline.priceType = this.supplement;
		priceline.supplement = short_stay;
		
		priceline.agentGroup = period.agent_group;
		
		contract = new Contract(); 		// this can be moved to a function "setContract(id)"
		contract.get(period.contract);	// this can be moved to a function "setContract(id)"
		
		markup = new Markup();
		markup.get(contract.markup);
		
		this.fillCommonData();
		
		// fill data from the period
		priceline.fromDate = period.from_date;
		priceline.toDate = period.to_date;
		priceline.introDate = period.edit_date;
		priceline.activeMonday = period.active_monday;
		priceline.activeTuesday = period.active_tuesday;
		priceline.activeWednesday = period.active_wednesday;
		priceline.activeThursday = period.active_thursday;
		priceline.activeFriday = period.active_friday;
		priceline.activeSaturday = period.active_saturday;
		priceline.activeSunday = period.active_sunday;
		priceline.minStayTrig = period.minimum_stay;
		priceline.active = period.active;
		priceline.editBy = period.edited_by;
		
		this.fillPrices_Discounts_Rooms(period.id, contract.id, period.short_stay_suplement, period.short_stay_percentage);
		
		if (!this.processPriceline(priceline, true)) { valid = false; }
		return valid;
	}
	
	/* used by period ------------------------------------------- */
	public boolean insertPriceline (Period period, Prices prices, boolean offer) {
		
		boolean valid = true;
		this.prices = prices;
		priceline = new Priceline();
		
		if (offer) {
			priceline.priceType = this.offer;
		} else {
			priceline.priceType = this.net_rates;
		}
		priceline.supplement = "0";
		
		
		// data filling: common data to all lines (contract and property)
		Contract contract = new Contract();
		contract.get(period.contract);
		this.contract = contract;
		
		Markup markup = new Markup();
		markup.get(contract.markup);
		this.markup = markup;
		
		priceline.agentGroup = period.agent_group;
		this.fillCommonData();			
		
		// fill data from the period
		priceline.fromDate = period.from_date;
		priceline.toDate = period.to_date;
		priceline.introDate = period.edit_date;
		priceline.activeMonday = period.active_monday;
		priceline.activeTuesday = period.active_tuesday;
		priceline.activeWednesday = period.active_wednesday;
		priceline.activeThursday = period.active_thursday;
		priceline.activeFriday = period.active_friday;
		priceline.activeSaturday = period.active_saturday;
		priceline.activeSunday = period.active_sunday;
		priceline.arrivalMonday = period.arrival_monday;
		priceline.arrivalTuesday = period.arrival_tuesday;
		priceline.arrivalWednesday = period.arrival_wednesday;
		priceline.arrivalThursday = period.arrival_thursday;
		priceline.arrivalFriday = period.arrival_friday;
		priceline.arrivalSaturday = period.arrival_saturday;
		priceline.arrivalSunday = period.arrival_sunday;
		priceline.minStayTrig = period.minimum_stay;
		priceline.active = period.active;
		priceline.editBy = period.edited_by;
				
		//fillPrices("0.00"); ----------------------------------------------------------------
		
		double modifyer;
		double price;
		String stringPrice = "";
		Enumeration e;
		BoardBasisPeriod board_basisPeriod = new BoardBasisPeriod();
		DiscountPeriod discountPeriod = new DiscountPeriod();
		priceline.selfCatering = "0";
		priceline.roomOnly = "0";
		priceline.bedBreakfast = "0";
		priceline.halfBoard = "0";
		priceline.fullBoard = "0";
		priceline.allInclusive = "0";
		for (e = prices.board_bases.elements() ; e.hasMoreElements() ;) {
			board_basisPeriod = (xcaping.BoardBasisPeriod)e.nextElement();
			switch (Integer.parseInt(board_basisPeriod.board_basis_id)) {
	        	case 1:  priceline.selfCatering = board_basisPeriod.price; break;
	        	case 2:  priceline.roomOnly = board_basisPeriod.price; break;
	            case 3:  priceline.bedBreakfast = board_basisPeriod.price; break;
	            case 4:  priceline.halfBoard = board_basisPeriod.price; break;
	            case 5:  priceline.fullBoard = board_basisPeriod.price; break;
	            case 6:  priceline.allInclusive = board_basisPeriod.price; break;
	        }
		}
		priceline.disc1stChild = "0";
		priceline.disc2ndChild = "0";
		priceline.disc3rdChild = "0";
		priceline.disc3rdAdult = "0";
		priceline.disc4thAdult = "0";
		priceline.percentage1Child = "0";
		priceline.percentage2Child = "0";
		priceline.percentage3Child = "0";
		priceline.percentage3Adult = "0";
		priceline.percentage4Adult = "0";
		for (e = prices.discounts.elements() ; e.hasMoreElements() ;) {
			discountPeriod = (xcaping.DiscountPeriod)e.nextElement();
			switch (Integer.parseInt(discountPeriod.discount_id)) {
	        	case 1:  {
	        		priceline.disc1stChild = discountPeriod.amount;
	        		priceline.percentage1Child = discountPeriod.percentage;
	        		break;
	        	}
	        	case 2:  {
	        		priceline.disc2ndChild = discountPeriod.amount;
	        		priceline.percentage2Child = discountPeriod.percentage;
	        		break;
	        	}
	        	case 3:  {
	        		priceline.disc3rdChild = discountPeriod.amount;
	        		priceline.percentage3Child = discountPeriod.percentage;
	        		break;
	        	}
	        	case 4:  {
	        		priceline.disc3rdAdult = discountPeriod.amount;
	        		priceline.percentage3Adult = discountPeriod.percentage;
	        		break;
	        	}
	        	case 5:  {
	        		priceline.disc4thAdult = discountPeriod.amount;
	        		priceline.percentage4Adult = discountPeriod.percentage;
	        		break;
	        	}
	        }
		}
		
		// ------------------------------------------------------------------------------------
		
		//this.fillRooms(prices);
		
		RoomPeriod roomPeriod = new RoomPeriod();
		for (e = prices.rooms.elements() ; e.hasMoreElements() ;) {
			roomPeriod = (RoomPeriod)e.nextElement();
			roomSupplement = new RoomSupplement();
			roomSupplement.RoomID = roomPeriod.room_id;
			roomSupplement.RoomPrice = roomPeriod.price;
			roomSupplement.pricePerUnitPerPerson = roomPeriod.unit;
			
			roomSupplement.name = roomPeriod.name;
			priceline.rooms.add(roomSupplement);
		}
		
		// --------------------
		if (!this.processPriceline(priceline, true)) { valid = false; }
		return (valid);
	}
	
	// ------------------------- deleting generated lines --------------------------------------------

	/** deletes the pricelines with dates that match the dates, 
	 * for this property, including stop sales and cot supplements.<br>
	 * if short_stay is active, it only deletes it<br>
	 * if cot_supplement is active, it only deletes it<br>
	 * - used by Period (so public)
	 * 	use deletePricelines instead???
	 */
	/*public void deletePriceline(String propID, String fromDate, String toDate, boolean short_stay, boolean cot_supplement, boolean offer) {
		
		connect();
		try {
			String pricePeriodID = "";
			
			Validation validation = new Validation();
			fromDate = validation.getSQLDate(fromDate);
			toDate = validation.getSQLDate(toDate);
			
			SQL = this.SQLprefix+"select pricePeriodID from "+this.tables_modifyer+"[PriceTBL] " +
					"where propID='"+propID+"' and " +
					" fromDate='"+fromDate+"' and toDate='"+toDate+"'";
			if (cot_supplement){
				SQL = SQL + " and supplement="+this.baby_cot;
			}
			if (short_stay) {
				SQL = SQL + " and supplement="+this.short_stay;
			}
			if (offer){
				SQL = SQL + " and priceType="+this.offer;
			}
							
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	boolean more = result.next();
          	
      		while (more) {
      			pricePeriodID = result.getString("pricePeriodID");
      			
      			// delete the room supplements for the priceline
      			SQL = "delete from "+this.tables_modifyer+"[RoomSupplementsTBL] where pricePeriodID='"+pricePeriodID+"'";
      			sentencia = conexion.createStatement();
      			sentencia.execute(SQL);
      			
      			// delete the priceline itself
      			SQL = "delete from "+this.tables_modifyer+"[PriceTBL] where pricePeriodID='"+pricePeriodID+"'";
      			sentencia = conexion.createStatement();
      			sentencia.execute(SQL);
      			
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
	}*/
	
	/** deletes all the pricelines between this dates, for this property, including stop sales and cot supplements.<br>
	 * if short_stay is active, it only deletes them<br>
	 * if cot_supplement is active, it only deletes them<br>
	 * - used by Period (so public)
	 */
	/*public void deletePricelines(String propID, String fromDate, String toDate, boolean short_stay, boolean cot_supplement, boolean offer) {
		
		connect();
		try {
			String pricePeriodID = "";
			
			Validation validation = new Validation();
			fromDate = validation.getSQLDate(fromDate);
			toDate = validation.getSQLDate(toDate);
			
			SQL = this.SQLprefix+"select pricePeriodID from "+this.tables_modifyer+"[PriceTBL] " +
			"where propID='"+propID+"' " +
			"and ((fromDate between '"+fromDate+" 00:00:00:000' and '"+toDate+" 23:00:00:000') " +
			"and (toDate between '"+fromDate+" 00:00:00:000' and '"+toDate+" 23:00:00:000')) ";
			if (cot_supplement){
				SQL = SQL + " and supplement="+this.baby_cot;
			}
			if (short_stay) {
				SQL = SQL + " and supplement="+this.short_stay;
			}
			if (offer){
				SQL = SQL + " and priceType="+this.offer;
			}
							
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	boolean more = result.next();
          	
      		while (more) {
      			pricePeriodID = result.getString("pricePeriodID");
      			
      			// delete the room supplements for the priceline
      			SQL = "delete from "+this.tables_modifyer+"[RoomSupplementsTBL] where pricePeriodID='"+pricePeriodID+"'";
      			sentencia = conexion.createStatement();
      			sentencia.execute(SQL);
      			
      			// delete the priceline itself
      			SQL = "delete from "+this.tables_modifyer+"[PriceTBL] where pricePeriodID='"+pricePeriodID+"'";
      			sentencia = conexion.createStatement();
      			sentencia.execute(SQL);
      			
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
	}*/

	/** pricelines deletion: deletes a priceline that matches these dates, or all the pricelines between the dates.
	 * deletes only the lines that match the type 
	 * ("net_rates", "baby_cot", "short_stay", "add_on, "gala_dinner", "offer").
	 */
	public void deletePricelines(String propID, String fromDate, String toDate, String type, boolean one_line) {
		
		connect();
		try {
			String pricePeriodID = "";
			
			Validation validation = new Validation();
			fromDate = validation.getSQLDate(fromDate);
			toDate = validation.getSQLDate(toDate);
			
			SQL = this.SQLprefix+"select pricePeriodID from "+this.tables_modifyer+"[PriceTBL] " +
			"where propID='"+propID+"' ";
			
			if (one_line) {
				SQL = SQL + "and fromDate='"+fromDate+"' and toDate='"+toDate+"'";
			} else {
				SQL = SQL + "and ((fromDate between '"+fromDate+" 00:00:00:000' and '"+toDate+" 23:00:00:000') " +
				"and (toDate between '"+fromDate+" 00:00:00:000' and '"+toDate+" 23:00:00:000')) ";
			}			
	
			if (type.equals("net_rates")) {
				SQL = SQL + " and priceType="+this.net_rates;
			}
			// supplements
			if (type.equals("baby_cot") || type.equals(this.baby_cot)) {
				SQL = SQL + " and supplement="+this.baby_cot;
			}
			if (type.equals("short_stay") || type.equals(this.short_stay)) {
				SQL = SQL + " and supplement="+this.short_stay;
			}
			if (type.equals("add_on") || type.equals(this.add_on)) {
				SQL = SQL + " and supplement="+this.add_on;
			}
			if (type.equals("gala_dinner") || type.equals(this.gala_dinner)) {
				SQL = SQL + " and supplement="+this.gala_dinner;
			}
			if (type.equals("sea_view") || type.equals(this.sea_view)) {
				SQL = SQL + " and supplement="+this.sea_view;
			}
			// -------------------------------
			if (type.equals("offer")) {
				SQL = SQL + " and priceType="+this.offer;
			}
							
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	boolean more = result.next();
          	
      		while (more) {
      			pricePeriodID = result.getString("pricePeriodID");
      			
      			// delete the room supplements for the priceline
      			SQL = "delete from "+this.tables_modifyer+"[RoomSupplementsTBL] where pricePeriodID='"+pricePeriodID+"'";
      			sentencia = conexion.createStatement();
      			sentencia.execute(SQL);
      			
      			// delete the priceline itself
      			SQL = "delete from "+this.tables_modifyer+"[PriceTBL] where pricePeriodID='"+pricePeriodID+"'";
      			sentencia = conexion.createStatement();
      			sentencia.execute(SQL);
      			
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	/** deletes a single availability line (even if there is more than one that matches the criteria), 
	 * also stop sales
	 */
	public void deleteAvailability(String propID, String fromDate, String toDate, String room_id,  boolean stop_sale) {
		connect();
		try {
			
			Validation validation = new Validation();
			fromDate = validation.getSQLDate(fromDate);
			toDate = validation.getSQLDate(toDate);
			
			Statement sentencia = conexion.createStatement();
			if (stop_sale) {
				SQL = this.SQLprefix+"select MAX(idColumn) as 'max' " +
				"from "+this.tables_modifyer+"[AvailPropertiesTBL] where propID='"+propID+"' " +
				"and fromDate='"+fromDate+"' and toDate='"+toDate+"' " +
				"and roomNameID='"+room_id+"' and minimumStay='0'";
				result = sentencia.executeQuery(SQL);
				if (result.next()) {
					SQL = "delete from "+this.tables_modifyer+"[AvailPropertiesTBL] " +
					"where idColumn="+result.getString("max");
				}
			} else {
				SQL = this.SQLprefix+"delete from "+this.tables_modifyer+"[AvailPropertiesTBL] " +
	      		"where propID='"+propID+"' and " +
				" fromDate='"+fromDate+"' and toDate='"+toDate+"'";
				
	      		if (!room_id.equals("0")) {
	      			SQL = SQL + " and roomNameID='"+room_id+"'";
	      		}
			}
      		sentencia.execute(SQL);
      		
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	/**- used by Contract (so public)
	 */
	/*public void deleteAvailabilities(String contract_id) {
		connect();
		try {
			String pricePeriodID = "";
			
			contract = new Contract();
			contract.get(contract_id);
			
			Validation validation = new Validation();
			String from_date = validation.getSQLDate(contract.from_date);
			String to_date = validation.getSQLDate(contract.to_date);
			
			property = new Property();
			property.get(contract.property);
			
			SQL = this.SQLprefix+"delete from "+this.tables_modifyer+"[AvailPropertiesTBL]  " +
				"where fromDate between '"+from_date+"' " +
				"AND '"+to_date+"' AND propID='"+contract.property+"'";
          	Statement sentencia = conexion.createStatement();
          	sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}*/
	
	/** deletes all availabilities between these dates for this property
	 */
	public void deleteAvailabilities(String propID, String fromDate, String toDate) {
		connect();
		try {
			
			Validation validation = new Validation();
			fromDate = validation.getSQLDate(fromDate);
			toDate = validation.getSQLDate(toDate);
			
			SQL = this.SQLprefix+"delete from "+this.tables_modifyer+"[AvailPropertiesTBL] " +
			"where propID = "+propID+" and ((fromDate between '"+fromDate+" 00:00:00:000' and '"+toDate+" 23:00:00:000') " +
			"and (toDate between '"+fromDate+" 00:00:00:000' and '"+toDate+" 23:00:00:000'))";
			//SQL = SQL +	" and minimumStay<>'0'";
      		
      		Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      		
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	/**- used by Contract (so public)
	 */
	public void deletePricelines(String contract_id) {
		connect();
		try {
			String pricePeriodID = "";
			
			contract = new Contract();
			contract.get(contract_id);
			
			Validation validation = new Validation();
			String from_date = validation.getSQLDate(contract.from_date);
			String to_date = validation.getSQLDate(contract.to_date);
			
			/*property = new Property();
			property.get(contract.property);*/
			
			SQL = this.SQLprefix+"select * from "+this.tables_modifyer+"[PriceTBL]  " +
					"where fromDate between '"+from_date+"' " +
					"AND '"+to_date+"' AND propID='"+contract.property+"'";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	boolean more = result.next();
          	
      		while (more) {
      			pricePeriodID = result.getString("pricePeriodID");
      			
      			// delete the room supplements for the priceline
      			SQL = "delete from "+this.tables_modifyer+"[RoomSupplementsTBL] where pricePeriodID='"+pricePeriodID+"'";
      			sentencia = conexion.createStatement();
      			sentencia.execute(SQL);
      			
      			// delete the priceline itself
      			SQL = "delete from "+this.tables_modifyer+"[PriceTBL] where pricePeriodID='"+pricePeriodID+"'";
      			sentencia = conexion.createStatement();
      			sentencia.execute(SQL);
      			
      			more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	// ------------------------- modifying generated lines --------------------------------------------
	
	/**- used by Contract (so public)
	 */
	public void modifyAvailabilities(Contract contract) {
		connect();
		try {
						
			SQL = this.SQLprefix+"UPDATE "+this.tables_modifyer+"[AvailPropertiesTBL] " +
  			"SET [freeSale]='"+contract.free_sale+"', [supplier]='"+contract.supplier+"', " +
  			"[editDate]='"+this.today+"', [editBy]='"+contract.edited_by+"' " +
  			"where fromDate between '"+contract.from_date+"' " +
			"AND '"+contract.to_date+"' AND propID='"+contract.property+"'";
      		
      		Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      		
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	/**- used by Prices (so public)
	 */
	public void modifyAvailability(String idColumn, RoomPeriod roomPeriod) {
		connect();
		try {		
			Period period = new Period();
			period.get(roomPeriod.period_id);
						
			SQL = this.SQLprefix+"UPDATE "+this.tables_modifyer+"[AvailPropertiesTBL] " +
  			"SET [allocation]='"+roomPeriod.allotment+"' ,[releasePeriod]='"+roomPeriod.release+"' " +
  			"where idColumn='"+idColumn+"'";
      		
      		Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      		
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	/** post-generation values editting requires to alter the availability values - used by Period (so public)
	 * @param period the Period object with the new values
	 * @param old_from_date the original period dates (to make the select)
	 * @param old_to_date
	 */
	public void modifyAvailability(Period period, String old_from_date, String old_to_date) {
		connect();
		try {
			Validation validation = new Validation();
			String from_date = validation.getSQLDate(period.from_date);
			String to_date = validation.getSQLDate(period.to_date);
			old_from_date = validation.getSQLDate(old_from_date);
			old_to_date = validation.getSQLDate(old_to_date);
			
			Contract contract = new Contract();
			contract.getProperty(period.contract);
			
			String minimum_stay = "";
			if (!hasValue(period.short_stay_suplement)) {
				minimum_stay = period.minimum_stay;
			} else {
				minimum_stay = "1";					
			}
			
			SQL = this.SQLprefix+"UPDATE "+this.tables_modifyer+"[AvailPropertiesTBL] " +
  			"SET [minimumStay]='"+minimum_stay+"', " +
  			"[fromDate]='"+from_date+"', " +
  			"[toDate]='"+to_date+"', " +
  			"[active]='"+period.active+"', " +
  			"[editBy]='"+period.edited_by+"', " +
  			"[editDate]='"+period.edit_date+"' " +
  			"where fromDate='"+old_from_date+"' AND toDate='"+old_to_date+"' " +
			"AND propID='"+contract.property+"'";
      		
      		Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      		
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	/**- used by Contract (so public)
	 */
	public void modifyPricelines(Contract contract, String old_cot_supplement) {
		connect();
		try {			
			this.markup.get(contract.markup);
						
			SQL = this.SQLprefix+"UPDATE "+this.tables_modifyer+"[PriceTBL] " +
  			"SET [introDate]='"+this.today+"', [supplierID]='"+contract.supplier+"', " +
  			"[exchangeID]='"+contract.currency+"', [markup]='"+this.markup.markup+"', " +
  			"[commissionable]='"+contract.commissionable+"', " +
  			"[arrivalMonday]='"+contract.arrival_monday+"', " +
  			"[arrivalTuesday]='"+contract.arrival_tuesday+"', " +
  			"[arrivalWednesday]='"+contract.arrival_wednesday+"', " +
  			"[arrivalThursday]='"+contract.arrival_thursday+"', " +
  			"[arrivalFriday]='"+contract.arrival_friday+"', " +
  			"[arrivalSaturday]='"+contract.arrival_saturday+"', " +
  			"[arrivalSunday]='"+contract.arrival_sunday+"', " +
  			"[salePeriod]='"+contract.sales_period+"', " +
  			"[childAge]='"+contract.child_age+"', " +
  			"[editBy]='"+contract.edited_by+"', " +
  			"[infantAge]='"+contract.infant_age+"' " +
  			"where fromDate between '"+contract.from_date+"' " +
			"AND '"+contract.to_date+"' AND propID='"+contract.property+"'";
			
      		Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      		
      		if (!hasValue(contract.cot_suplement) && hasValue(old_cot_supplement)) {
      			this.deletePricelines(contract.property, contract.from_date, contract.to_date, "baby_cot", false);
      			//this.deletePriceline(contract.property, contract.from_date, contract.to_date, false, true, false);
      		}
      		if (hasValue(contract.cot_suplement)) {
      			if (hasValue(old_cot_supplement)) {
      				this.deletePricelines(contract.property, contract.from_date, contract.to_date, "baby_cot", false);
      				//this.deletePriceline(contract.property, contract.from_date, contract.to_date, false, true, false);
      			}
      			this.contract = contract;
      			this.insertBabyCot(true);
      		}
      		
      	} catch (Exception ex) {
      		int i = 1;
      	}
      	disconnect();
	}
	
	/**- used by Period (so public)
	 */
	public void modifyPricelines(Period period, Period old_period, boolean del_short_stay) {
		connect();
		try {
			Validation validation = new Validation();
			String from_date = validation.getSQLDate(period.from_date);
			String to_date = validation.getSQLDate(period.to_date);
			old_period.from_date = validation.getSQLDate(old_period.from_date);
			old_period.to_date = validation.getSQLDate(old_period.to_date);
			
			Contract contract = new Contract();
			contract.get(period.contract);
			
			SQL = this.SQLprefix+"UPDATE "+this.tables_modifyer+"[PriceTBL] " +
  			"SET [introDate]='"+this.today+"', [minStayTrig]='"+period.minimum_stay+"', " +
  			"[fromDate]='"+from_date+"', " +
  			"[toDate]='"+to_date+"', " +
  			"[agentGroup]='"+period.agent_group+"', " +
  			"[activeMonday]='"+period.active_monday+"', " +
  			"[activeTuesday]='"+period.active_tuesday+"', " +
  			"[activeWednesday]='"+period.active_wednesday+"', " +
  			"[activeThursday]='"+period.active_thursday+"', " +
  			"[activeFriday]='"+period.active_friday+"', " +
  			"[activeSaturday]='"+period.active_saturday+"', " +
  			"[activeSunday]='"+period.active_sunday+"', " +
  			"[arrivalMonday]='"+period.arrival_monday+"', " +
  			"[arrivalTuesday]='"+period.arrival_tuesday+"', " +
  			"[arrivalWednesday]='"+period.arrival_wednesday+"', " +
  			"[arrivalThursday]='"+period.arrival_thursday+"', " +
  			"[arrivalFriday]='"+period.arrival_friday+"', " +
  			"[arrivalSaturday]='"+period.arrival_saturday+"', " +
  			"[arrivalSunday]='"+period.arrival_sunday+"', " +
  			"[editBy]='"+period.edited_by+"', " +
  			"[active]='"+period.active+"' " +
  			"where fromDate='"+old_period.from_date+"' " +
			"AND toDate='"+old_period.to_date+"' AND propID='"+contract.property+"' AND "+
			"[activeMonday]='"+old_period.active_monday+"' AND "+
  			"[activeTuesday]='"+old_period.active_tuesday+"' AND "+
  			"[activeWednesday]='"+old_period.active_wednesday+"' AND "+
  			"[activeThursday]='"+old_period.active_thursday+"' AND "+
  			"[activeFriday]='"+old_period.active_friday+"' AND "+
  			"[activeSaturday]='"+old_period.active_saturday+"' AND "+
  			"[activeSunday]='"+old_period.active_sunday+"'";
			
			if (period.offer.equals("1")) {
				SQL = SQL + " and priceType="+this.offer;
			}
			
      		Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      		
      		if (!hasValue(period.short_stay_suplement) && hasValue(old_period.short_stay_suplement)) {
      			if (del_short_stay) { this.deletePricelines(contract.property, period.from_date, period.to_date, "short_stay", false); }
      		}
      		if (hasValue(period.short_stay_suplement)) {
      			if (hasValue(old_period.short_stay_suplement)) {
      				if (del_short_stay) { this.deletePricelines(contract.property, period.from_date, period.to_date, "short_stay", false); }
      			}
      			this.contract = contract;
      			Markup markup = new Markup();
      			markup.get(contract.markup);
      			this.markup = markup;
      			this.insertShortStay(period);
      		}
      		
      	} catch (Exception ex) {
      		int i = 1;
      	}
      	disconnect();
	}
	
	// ------------------------------- OFFERS --------------
		
	/** inserts the price and availability lines for this offer, using the data on the parameters
	 *  - it first deletes the availability for this period
	 * @param period_id this is the period in which the offer dates are located
	 * @param offer_period the offer's dates and other info
	 * @param offer_prices the offer's prices
	 */

	public boolean insertBrokenOfferShortStays(String period_id, Period offer_period, String complementary_id) {
		
		boolean valid = true;

		boolean has_complementary = !complementary_id.equals("0");
		
		Period period = new Period();
		Period complementary = new Period();
		period.get(period_id);
		if (has_complementary) {
			complementary.get(complementary_id);
		}
		String period_from = period.from_date;
		String period_to = period.to_date;
		
		// convert string dates to date objects
		java.util.Date offer_from_date;
		java.util.Date offer_to_date;
		java.util.Date period_to_date;
		java.util.Date period_from_date;
		
		String pattern="yyy-MM-dd";
		java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat(pattern);
		
		offer_from_date = sdf.parse(validation.getSQLDate(offer_period.from_date), new java.text.ParsePosition(0));
		offer_to_date = sdf.parse(validation.getSQLDate(offer_period.to_date), new java.text.ParsePosition(0));
		period_from_date = sdf.parse(validation.getSQLDate(period.from_date), new java.text.ParsePosition(0));
		period_to_date = sdf.parse(validation.getSQLDate(period.to_date), new java.text.ParsePosition(0));
		// ---------------convert string dates to date objects
		
		// delete availabilities
		contract = new Contract(); 		// this can be moved to a function "setContract(id)"
		contract.get(period.contract);	// this can be moved to a function "setContract(id)"
		this.deleteAvailabilities(contract.property, period.from_date, period.to_date);
		
		markup.get(contract.markup);
		
		// deleting the existing short_stay supplements existing for this period
		this.deletePricelines(contract.property, period.from_date, period.to_date, "short_stay", false);
		//this.deletePricelines(contract.property, period.from_date, period.to_date, true, false, false);
		
		// insert, if needed, broken-side availabilities
		if (offer_from_date.after(period_from_date)) { // if the offer.from doesn't match the period.from, a small period must be inserted on the hole 
			period.from_date = period_from;
			period.to_date = offer_period.from_date;
			
			if (!this.insertAvailabilities(period)) { valid = false; }
			if (hasValue(period.short_stay_suplement)) {
				if (!this.insertShortStay(period)) { valid = false; }
				if (has_complementary) {
					complementary.from_date = period.from_date;
					complementary.to_date = period.to_date;
					if (!this.insertShortStay(complementary)) { valid = false; }
				}
			}
		}
		
		if (period_to_date.after(offer_to_date)) { // if the offer.to doesn't match the period.to, a small period must be inserted on the hole
			period.from_date = offer_period.to_date;
			period.to_date = period_to;

			if (!this.insertAvailabilities(period)) { valid = false; }
			if (hasValue(period.short_stay_suplement)) {
				if (!this.insertShortStay(period)) { valid = false; }
				if (has_complementary) {
					complementary.from_date = period.from_date;
					complementary.to_date = period.to_date;
					if (!this.insertShortStay(complementary)) { valid = false; }
				}
			}
		}		
		return (valid);
	}
	
	public void deactivateOffer (Period offer, boolean has_complementary) {
		// delete offer lines
		Contract contract = new Contract();
		contract.get(offer.contract);
		this.contract = contract;
		Period period = new Period();
		period = period.getPeriod_Offer(offer.id, offer.from_date, offer.contract);
		this.deletePricelines(contract.property, period.from_date, period.to_date, "offer", false);
		//this.deletePricelines(contract.property, offer.from_date, offer.to_date, false, false, true);
		
		// delete short stay lines
		this.deletePricelines(contract.property, period.from_date, period.to_date, "short_stay", false);
		//this.deletePricelines(contract.property, period.from_date, period.to_date, true, false, false);
		
		// insert short stay from the period (if)
		if (hasValue(period.short_stay_suplement)) {
			Markup markup = new Markup();
			markup.get(contract.markup);
			this.markup = markup;
			this.insertShortStay(period);
			if (has_complementary) {
				Period complementary = new Period();
				complementary = period.getComplementary("0", period.id);
				if (!this.insertShortStay(complementary)) { valid = false; }
			}
  		}		
		// delete availability
		// insert period availability
		this.deleteAvailabilities(contract.property, period.from_date, period.to_date);
		if (!this.insertAvailabilities(period)) { valid = false; }
		//this.modifyAvailability(period, period.from_date, period.to_date);
	}
	
	public void activateOffer (Period offer, boolean has_complementary) {
		// insert offer lines
		
		// insert short stay from the offer
		// delete availability
		// insert offer availability
		Period period = new Period();
		period = period.getPeriod_Offer(offer.id, offer.from_date, offer.contract);
		Prices prices = new Prices();
		prices.setPeriod(offer.id, offer.contract);
		prices.getPrices();
		Contract contract = new Contract();
		contract.getProperty(offer.contract);
		String complementary_id = offer.getComplementaryId("1");
		// delete short stay period lines
		this.deletePricelines(contract.property, period.from_date, period.to_date, "short_stay", false);
		//this.deletePricelines(contract.property, period.from_date, period.to_date, true, false, false);
		this.insertBrokenOfferShortStays(period.id, offer, complementary_id);
		if (hasValue(offer.short_stay_suplement)) {
			if (!this.insertShortStay(offer)) { valid = false; }
			if (has_complementary) {
				Period complementary_offer = new Period();
				complementary_offer = offer.getComplementary("1", offer.id);
				if (!this.insertShortStay(complementary_offer)) { valid = false; }
			}
		}
		if (!this.insertPriceline(offer, prices, true)) { valid = false; }
		if (has_complementary) {
			Period complementary_offer = new Period();
			complementary_offer.get(complementary_id);
			Prices complementary_prices = new Prices();
			complementary_prices.setPeriod(complementary_offer.id, complementary_offer.contract);
			complementary_prices.getPrices();
			if (!this.insertPriceline(complementary_offer, complementary_prices, true)) { valid = false; }
		}
		//this.deleteAvailabilities(contract.property, period.from_date, period.to_date);
		if (!this.insertAvailabilities(offer)) { valid = false; }
	}
	
	/** sets active value for the period pricelines
	 * @param period
	 */
	public void setActivePricelines(Period period) {
		connect();
		try {
			Contract contract = new Contract();
			contract.getProperty(period.contract);
			Validation validation = new Validation();
			
			SQL = this.SQLprefix+"UPDATE "+this.tables_modifyer+"[PriceTBL] " +
  			"SET [active]='"+period.active+"' " +
  			"where fromDate='"+validation.getSQLDate(period.from_date)+"' " +
			"AND toDate='"+validation.getSQLDate(period.to_date)+"' " +
			"AND propID='"+contract.property+"'";
			
			if (period.offer.equals("1")) {
				SQL = SQL + " and priceType="+this.offer;
			} else {
				SQL = SQL + " and (priceType="+this.net_rates+" " +
				"or (priceType="+this.supplement+" and supplement="+this.short_stay+"))";
			}
      		Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      		int i = 1;
      	}
      	disconnect();
	}
	
	/** sets active value for the period availability lines
	 * @param period
	 */
	public void setActiveAvailability(Period period) {
		connect();
		try {
			Contract contract = new Contract();
			contract.getProperty(period.contract);
			Validation validation = new Validation();
			
			SQL = this.SQLprefix+"UPDATE "+this.tables_modifyer+"[AvailPropertiesTBL] " +
  			"SET [active]='"+period.active+"' " +
  			"where fromDate='"+validation.getSQLDate(period.from_date)+"' " +
			"AND toDate='"+validation.getSQLDate(period.to_date)+"' " +
			"AND propID='"+contract.property+"' and freeSale<>0";

      		Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      		int i = 1;
      	}
      	disconnect();
	}
	
	public boolean insertAddOn(AddOn add_on, boolean insert) {
		
		boolean valid = true;
		priceline = new Priceline();
		priceline.priceType = this.supplement;
		
		priceline.supplement = add_on.supplement;
		
		priceline.agentGroup = contract.agent_group;
		this.fillCommonData();
		// rectification of arrival days: all active for the supplements
		priceline.arrivalMonday = "1";
		priceline.arrivalTuesday = "1";
		priceline.arrivalWednesday = "1";
		priceline.arrivalThursday = "1";
		priceline.arrivalFriday = "1";
		priceline.arrivalSaturday = "1";
		priceline.arrivalSunday = "1";
		
		// supplement: the whole contract
		priceline.fromDate = add_on.from_date;
		priceline.toDate = add_on.to_date;

		// default values
		priceline.activeMonday = "1";
		priceline.activeTuesday = "1";
		priceline.activeWednesday = "1";
		priceline.activeThursday = "1";
		priceline.activeFriday = "1";
		priceline.activeSaturday = "1";
		priceline.activeSunday = "1";
		priceline.active = "1";
		
		// data from net rates
		priceline.introDate = add_on.edit_date;
		priceline.minStayTrig = contract.getMinimumStay(contract.id);
		priceline.editBy = add_on.edited_by;
		
		this.fillPrices_Discounts_Rooms("0", contract.id, add_on.price, "0");
		this.fillDiscounts(add_on.child_discount, add_on.child_percentage, add_on.adult_discount, add_on.adult_percentage);
			
		if (!this.processPriceline(priceline, insert)) {
			valid = false;
		}
		return (valid);
	}
	
	public boolean insertBabyCot(boolean insert) {
		
		boolean valid = true;
		priceline = new Priceline();
		priceline.priceType = this.supplement;
		priceline.supplement = this.baby_cot;
		
		priceline.agentGroup = contract.agent_group;
		
		// data filling: common data to all lines (contract and property)
		this.fillCommonData();
		// rectification of arrival days: all active for the supplements
		priceline.arrivalMonday = "1";
		priceline.arrivalTuesday = "1";
		priceline.arrivalWednesday = "1";
		priceline.arrivalThursday = "1";
		priceline.arrivalFriday = "1";
		priceline.arrivalSaturday = "1";
		priceline.arrivalSunday = "1";
		
		// supplement: the whole contract
		priceline.fromDate = contract.from_date;
		priceline.toDate = contract.to_date;

		// default values
		priceline.activeMonday = "1";
		priceline.activeTuesday = "1";
		priceline.activeWednesday = "1";
		priceline.activeThursday = "1";
		priceline.activeFriday = "1";
		priceline.activeSaturday = "1";
		priceline.activeSunday = "1";
		priceline.active = "1";
		
		priceline.introDate = this.today;
		priceline.minStayTrig = contract.getMinimumStay(contract.id);
		priceline.editBy = contract.edited_by;
		
		this.fillPrices_Discounts_Rooms("0", contract.id, contract.cot_suplement, "0");
		
		if (!this.processPriceline(priceline, insert)) {
			valid = false;
		}
		return (valid);
	}
	
	/** deletes all availabilities and price room supplements for this property, for this room (when a room is deleted)
	 */
	public void deleteRoomLines(String property_id, String property_room_id) {
		connect();
		try {
			Statement sentencia = conexion.createStatement();
			SQL = "delete from "+this.tables_modifyer+"[AvailPropertiesTBL] " +
			"where propID = "+property_id+" and roomNameID='"+property_room_id+"'";
      		sentencia.execute(SQL);
      		
      		SQL = "select roomPriceID from "+this.tables_modifyer+"[RoomSupplementsTBL] " +
      			"join "+this.tables_modifyer+"[PriceTBL] " +
      			"on RoomSupplementsTBL.pricePeriodID = PriceTBL.pricePeriodID " +
      			"where propID='"+property_id+"' and RoomID='"+property_room_id+"'";
      		result = sentencia.executeQuery(SQL);
      		
          	boolean more = result.next();
          	Statement sentencia2 = conexion.createStatement();
      		while (more) {
      			SQL = "delete from "+this.tables_modifyer+"[RoomSupplementsTBL] " +
    			"where roomPriceID = '"+result.getString("roomPriceID")+"'";
          		sentencia2.execute(SQL);
          		more = result.next();
      		}
      	} catch (Exception ex) {
      		int i = 1;
      	}
      	disconnect();
	}
	
}