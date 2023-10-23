package xcaping;

import java.util.Enumeration;

/** <div align="center"><strong>classe's validation center</strong></div>
 * <p>description: to make any data validation, this class is invoked, the correct method is used, 
 * and the result is a boolean value</p>
 * @author carlos nieves lameiro
 * @version 1.2
 */

public class Validation {
	
	private String cadena;
	boolean valid = true;
	boolean finished = false;
	String message="error";
	
	private String valid_chars_login = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.";
	private String valid_chars_text = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_?.,;:@ ™∫·ÈÌÛ˙¡…Õ”⁄Ò—ø; ()/";
	
	/** from a date string in the formats: dd-mm-yy, d m y, composes a SQL date string (yyyy-mm-dd) 
	 * @param date date string (dd-mm-yy, d m y)
	 * @return SQL date string (yyyy-mm-dd)
	 */
	public String getSQLDate(String date) {
		
		cadena = date;
		this.validDate();	
		return cadena;
	}
	
	private boolean hasValue(String value) {
		return (!value.equals(null) && (!value.equals("")));
	}
	
	private boolean isNull(String value) {
		return (value.equals(null));
	}
	
	private boolean isZero(String value) {
		return ((!value.equals("0.00")) && (!value.equals("0")));
	}
	
	private void validInteger(int max) {
		if (!hasValue(cadena)) {
			cadena = "0";
		} else {
			int number = Integer.parseInt(cadena);
			if ((number < 0) || (number > max)) {
				valid = false;
			}
		}
	}
	
	private void validPrice(double max) {
		
		Double number;
		if (!hasValue(cadena)) {
			valid = false;
		} else {
			cadena = cadena.replace(',', '.');
			try {
				number = new Double(cadena);
				if ((number.doubleValue() <= 0) || (number.doubleValue() > max)) {
					valid = false;
				}
			} catch (Exception ex){
		        valid = false;
		    }
		}		
	}
	
	private void validComplementary(String complementary) {
		
		Double number;
		cadena = cadena.replace(',', '.');
		number = new Double(cadena);
		if (number.doubleValue() == Double.parseDouble(complementary)) {
			this.cadena = String.valueOf(number.doubleValue() + 0.01);				
		}		
	}
	
	private void validPriceOrNullToZero(double max) {
		
		Double number;
		if (!hasValue(cadena)) {
			cadena = "0";
		} else {
			try {
				number = new Double(cadena);
				if ((number.doubleValue() < 0) || (number.doubleValue() > max)) {
					valid = false;
				}
			} catch (Exception ex){
		        valid = false;
		    }
		}		
		
	}
	
	private void validPriceOrNull(double max) {
		
		Double number;
		if (hasValue(cadena)) {
			try {
				number = new Double(cadena);
				if ((number.doubleValue() <= 0) || (number.doubleValue() > max)) {
					valid = false;
				}
			} catch (Exception ex){
		        valid = false;
		    }
		}		
		
	}
	
	private void validPercentage(int max) {
		
		Double number;
		int percentage;
		if (!hasValue(cadena)) {
			cadena = "0";
		} else {
			try {
				number = new Double(cadena);
				percentage = number.intValue();
				cadena = String.valueOf(percentage);
				if ((percentage < 0) || (percentage > max)) {
					valid = false;
				}
			} catch (Exception ex){
		        valid = false;
		    }
		}		
		
	}
	
	private void validPercentageOrNull(int max) {
		
		Double number;
		int percentage;
		if (hasValue(cadena)) {
			try {
				number = new Double(cadena);
				percentage = number.intValue();
				cadena = String.valueOf(percentage);
				if ((percentage < 0) || (percentage > max)) {
					valid = false;
				}
			} catch (Exception ex){
		        valid = false;
		    }
		}		
		
	}
	
	private boolean valid(String string, String valid_chars){

		boolean valid = true;
		for (int i=0; i < string.length(); i++) {
			if (valid_chars.indexOf(string.charAt(i)) == -1) {
				valid = false;
			}
		}
		return valid;
	}
	
	private void validString(int type) {
		
		if (!hasValue(cadena)) {
			cadena = "";
		} else {
			String valid_chars = "";
			switch (type) {
				case 1: 
						valid_chars = this.valid_chars_login;
						break;
				case 2: 
					valid_chars = this.valid_chars_text;
						break;
			}
			if (!valid(cadena, valid_chars)) {
				valid = false;
			}
		}
	}
	
	private void validBoolean() {
		
		if (!hasValue(cadena)) {
			cadena = "0";
		} 
		
	}
	
	/** 
	 *  gets a date on any of this formats: "yyyy-mm-dd : ..." (SQLserver), "dd-mm-yy" or "d m y", 
	 * checks if it's valid, and formats it to the sql format ("yyyy-mm-dd") 
	 */
	private void validDate() {
		
		String[] array = new String[3];
		int day = 32;
		int month = 13;
		int year = 5000;
		try {
			if (cadena.indexOf(":") != -1) {
				cadena = cadena.substring(0, 10);
			}
			if (cadena.indexOf("-") != -1) {
				array = cadena.split("-");
				year = Integer.parseInt(array[0]);
				month = Integer.parseInt(array[1]);
				day = Integer.parseInt(array[2]);
			} else {
				if (cadena.indexOf("/") != -1) {
					array = cadena.split("/");
					day = Integer.parseInt(array[0]);
					month = Integer.parseInt(array[1]);
					year = Integer.parseInt(array[2]) + 2000;
				} else {
					if (cadena.indexOf(" ") != -1) {
						array = cadena.split(" ");
						day = Integer.parseInt(array[0]);
						month = Integer.parseInt(array[1]);
						year = Integer.parseInt(array[2]) + 2000;
					}
				}				
			}
			if ((day < 32) && (month < 13) && (year < 4000)) {
				cadena = year+"-"+month+"-"+day;
			} else {
				valid = false;
			}
		} catch (Exception ex) {
			valid = false;
		}		
		
	}
	
	private boolean areConsecutive(String from, String to) {
		
		java.util.Date from_date;
		java.util.Date to_date;
		
		String pattern="yyy-MM-dd";
		java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat(pattern);
		
		from_date = sdf.parse(from, new java.text.ParsePosition(0));
		to_date = sdf.parse(to, new java.text.ParsePosition(0));
		
		return (to_date.after(from_date));
	}
	
	private boolean areConsecutiveOrEqual(String from, String to) {
		
		java.util.Date from_date;
		java.util.Date to_date;
		
		String pattern="yyy-MM-dd";
		java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat(pattern);
		
		from_date = sdf.parse(from, new java.text.ParsePosition(0));
		to_date = sdf.parse(to, new java.text.ParsePosition(0));
		
		return (to_date.after(from_date) || to_date.equals(from_date));
	}
	
	private boolean between(String from, String between, String to, boolean equals) {
		
		java.util.Date from_date;
		java.util.Date to_date;
		java.util.Date between_date;
		
		from = this.getSQLDate(from);
		between = this.getSQLDate(between);
		to = this.getSQLDate(to);
		
		String pattern="yyy-MM-dd";
		java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat(pattern);
		
		from_date = sdf.parse(from, new java.text.ParsePosition(0));
		to_date = sdf.parse(to, new java.text.ParsePosition(0));
		between_date = sdf.parse(between, new java.text.ParsePosition(0));
		boolean are_between = false;
		if (equals) {
			are_between = ((between_date.equals(from_date) || between_date.after(from_date)) 
			&& (to_date.equals(between_date) || to_date.after(between_date)));
		} else {
			are_between = (between_date.after(from_date) && to_date.after(between_date));
		}
		return (are_between);
	}
	
	private Period validDates(Period period) {
		
		
		this.cadena = period.from_date;
		this.validDate();
		period.from_date = this.cadena;
		
		this.cadena = period.to_date;
		this.validDate();
		period.to_date = this.cadena;
		
		this.cadena = period.edit_date;
		this.validDate();
		period.edit_date = this.cadena;
		
		if (valid) {
			
			Contract contract = new Contract();
			contract.get(period.contract);
			
			String from_date = contract.from_date;
			String to_date = contract.to_date;
			if (period.offer.equals("1")) {
				Period period_offer = period.getPeriod_Offer(period.id, period.from_date, contract.id);
				from_date = period_offer.from_date;
				to_date = period_offer.to_date;
			}
			if (this.areConsecutive(period.from_date, period.to_date)) {
				if (this.between(from_date, period.from_date, to_date, true) && 
						this.between(from_date, period.to_date, to_date, true)) {
					// is valid
				} else {
					valid = false;
				}
			} else {
				valid = false;
			}
		}	
		return (period);
	}
	
	private Contract validDates(Contract contract) {
		
		
		this.cadena = contract.from_date;
		this.validDate();
		contract.from_date = this.cadena;
		
		this.cadena = contract.to_date;
		this.validDate();
		contract.to_date = this.cadena;
		
		if (valid && this.areConsecutive(contract.from_date, contract.to_date)) {
			if (!this.pastDate(contract.from_date) || !this.pastDate(contract.to_date)) {
				Lists list = new Lists();
				java.util.Vector data_contracts = list.getContracts(contract.property);
				java.util.Enumeration e;
				Contract contract_n;

				for (e = data_contracts.elements() ; e.hasMoreElements() ;) {
					contract_n = (Contract)e.nextElement();
					if (!contract_n.id.equals(contract.id)) {
						if (this.between(contract_n.from_date, contract.from_date, contract_n.to_date, false) 
								|| this.between(contract_n.from_date, contract.to_date, contract_n.to_date, false) 
								|| this.between(contract.from_date, contract_n.from_date, contract.to_date, false)
								|| this.between(contract.from_date, contract_n.to_date, contract.to_date, false)) {
							valid = false;
						}
					}
				}
			} else {
				valid = false;
			}
		} else {
			valid = false;
		}
		return (contract);
	}
	
	private AddOn validDates(AddOn add_on) {
		
		
		this.cadena = add_on.from_date;
		this.validDate();
		add_on.from_date = this.cadena;
		
		this.cadena = add_on.to_date;
		this.validDate();
		add_on.to_date = this.cadena;
		
		if (valid) {
			
			Contract contract = new Contract();
			contract.get(add_on.contract);
			
			if (this.areConsecutiveOrEqual(add_on.from_date, add_on.to_date)) {
				if (this.between(contract.from_date, add_on.from_date, contract.to_date, true) && 
						this.between(contract.from_date, add_on.to_date, contract.to_date, true)) {
					// is valid
				} else {
					valid = false;
				}
			} else {
				valid = false;
			}
		}	
		return (add_on);
	}
	
	private Gala validDates(Gala gala) {
		
		
		this.cadena = gala.from_date;
		this.validDate();
		gala.from_date = this.cadena;
		
		this.cadena = gala.to_date;
		this.validDate();
		gala.to_date = this.cadena;
		
		if (valid) {			
			if (!this.areConsecutive(gala.from_date, gala.to_date)) {
				valid = false;
			}
		}	
		return (gala);
	}
	
	private Allocation validDates(Allocation allocation) {
		
		
		this.cadena = allocation.from_date;
		this.validDate();
		allocation.from_date = this.cadena;
		
		this.cadena = allocation.to_date;
		this.validDate();
		allocation.to_date = this.cadena;
		
		if (valid) {
			
			Contract contract = new Contract();
			contract.get(allocation.contract);
			
			if (this.areConsecutive(allocation.from_date, allocation.to_date)) {
				if (this.between(contract.from_date, allocation.from_date, contract.to_date, true) && 
						this.between(contract.from_date, allocation.to_date, contract.to_date, true)) {
					// is valid
				} else {
					valid = false;
				}
			} else {
				valid = false;
			}
		}	
		return (allocation);
	}	
	
	private StopSale validDates(StopSale stop_sale) {
		
		this.cadena = stop_sale.from_date;
		this.validDate();
		stop_sale.from_date = this.cadena;
		
		this.cadena = stop_sale.to_date;
		this.validDate();
		stop_sale.to_date = this.cadena;
		
		if (valid && this.areConsecutive(stop_sale.from_date, stop_sale.to_date)) {
			if (this.pastDate(stop_sale.from_date) || this.pastDate(stop_sale.to_date)) {
				valid = false;
			} else {
				valid = true;
			}
		} else {
			valid = false;
		}
		return (stop_sale);
	}
	
	/** identifies if a date string is a date in the past:  
	 * @param string_date
	 * @return true if the given date is a date in the past
	 */
	public boolean pastDate(String string_date) {
		
		java.util.Date date;
		java.util.Date today = new java.util.Date();

		String pattern="yyy-MM-dd";
		java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat(pattern);
		
		date = sdf.parse(string_date, new java.text.ParsePosition(0));
		
		return (today.after(date));
	}
	
	// -------------- objects validations ----------------
	
	/** validation of the user and password (for the user authorisation) 
	 * @param auth Auth object with the name and the password
	 * @return true if all the data has valid values
	 */
	public boolean validate(Auth auth) {
		
		valid = true;
		finished = false;
		
		this.cadena = auth.name;
		this.validString(1);
		auth.name = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid name";
			finished = true;
		}
		
		this.cadena = auth.password;
		this.validString(1);
		auth.password = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid password";
			finished = true;
		}
		
		return (valid);
	}
	
	/** validation of the contract's data and dates (the date strings are converted to SQL date strings)
	 * @param contract a Contract object with all the info
	 * @return true if all the data has valid values
	 */
	public boolean validate(Contract contract) {
		
		valid = true;
		finished = false;
		
		this.cadena = contract.name;
		this.validString(2);
		contract.name = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid name";
			finished = true;
		}
		
		contract = this.validDates(contract);
		if (!valid && !finished) {
			this.message = "invalid dates";
			finished = true;
		}
		
		this.cadena = contract.arrival_monday;
		this.validBoolean();
		contract.arrival_monday = this.cadena;
		
		this.cadena = contract.arrival_tuesday;
		this.validBoolean();
		contract.arrival_tuesday = this.cadena;
		
		this.cadena = contract.arrival_wednesday;
		this.validBoolean();
		contract.arrival_wednesday = this.cadena;
		
		this.cadena = contract.arrival_thursday;
		this.validBoolean();
		contract.arrival_thursday = this.cadena;
		
		this.cadena = contract.arrival_friday;
		this.validBoolean();
		contract.arrival_friday = this.cadena;
		
		this.cadena = contract.arrival_saturday;
		this.validBoolean();
		contract.arrival_saturday = this.cadena;
		
		this.cadena = contract.arrival_sunday;
		this.validBoolean();
		contract.arrival_sunday = this.cadena;
		
		this.cadena = contract.free_sale;
		this.validBoolean();
		contract.free_sale = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid free sale";
			finished = true;
		}
		
		this.cadena = contract.cot_suplement;
		this.validPriceOrNullToZero(30.00);
		contract.cot_suplement = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid cot supplement";
			finished = true;
		}
		
		this.cadena = contract.infant_age;
		this.validInteger(7);
		contract.infant_age = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid infant age";
			finished = true;
		}
		
		this.cadena = contract.child_age;
		this.validInteger(16);
		contract.child_age = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid child age";
			finished = true;
		}
		
		this.cadena = contract.text;
		this.validString(2);
		contract.text = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid text";
			finished = true;
		}
		
		this.cadena = contract.sales_period;
		this.validInteger(8);
		contract.sales_period = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid sales period";
			finished = true;
		}
		
		return (valid);
	}
	
	/** validation of the period data and dates (the date strings are converted to SQL date strings)
	 * @param period a Period object with all the info
	 * @return true if all the data has valid values
	 */
	public boolean validate(Period period) {
		
		valid = true;
		finished = false;

		period = this.validDates(period);
		if (!valid && !finished) {
			this.message = "invalid dates";
			finished = true;
		}
		
		if (period.offer.equals("0")) {
			this.cadena = period.minimum_stay;
			this.validPrice(15);
			period.minimum_stay = this.cadena;
			if (!valid && !finished) {
				this.message = "invalid minimum stay";
				finished = true;
			}
			
			this.cadena = period.active_monday;
			this.validBoolean();
			period.active_monday = this.cadena;
			
			this.cadena = period.active_tuesday;
			this.validBoolean();
			period.active_tuesday = this.cadena;
			
			this.cadena = period.active_wednesday;
			this.validBoolean();
			period.active_wednesday = this.cadena;
			
			this.cadena = period.active_thursday;
			this.validBoolean();
			period.active_thursday = this.cadena;
			
			this.cadena = period.active_friday;
			this.validBoolean();
			period.active_friday = this.cadena;
			
			this.cadena = period.active_saturday;
			this.validBoolean();
			period.active_saturday = this.cadena;
			
			this.cadena = period.active_sunday;
			this.validBoolean();
			period.active_sunday = this.cadena;
			if (!valid && !finished) {
				this.message = "invalid active days";
				finished = true;
			}
			
			this.cadena = period.short_stay_suplement;
			if (period.short_stay_percentage.equals("1")) {
				this.validPercentage(50);
			} else {
				this.validPriceOrNullToZero(100.00);
			}
			period.short_stay_suplement = this.cadena;
			if (!valid && !finished) {
				this.message = "invalid short stay";
				finished = true;
			}
					
			this.cadena = period.edit_date;
			this.validDate();
			period.edit_date = this.cadena;
			
			/*this.cadena = period.air_conditioned;
			this.validPriceOrNullToZero(99.00);
			period.air_conditioned = this.cadena;
			if (!valid && !finished) {
				this.message = "invalid air conditioned";
				finished = true;
			}*/
		}
		
		return (valid);
	}
	
	/** period prices validation; if they are offer prices, zero values are allowed
	 * @param prices a Prices object with all the info from board bases, discounts and rooms
	 * @param offer boolean indicating if this prices are offer prices  
	 * @return true if all the data has valid values
	 */
	public boolean validate(Prices prices, boolean offer, Period complementary) {
		
		valid = true;
		finished = false;
		
		Enumeration e;
		xcaping.BoardBasisPeriod board_basisPeriod = new xcaping.BoardBasisPeriod();
		xcaping.DiscountPeriod discountPeriod = new xcaping.DiscountPeriod();
		xcaping.RoomPeriod roomPeriod = new xcaping.RoomPeriod();
		
		Enumeration comp_e = null;
		xcaping.BoardBasisPeriod comp_board_basisPeriod = new xcaping.BoardBasisPeriod();
		if (complementary.hasComplementary) {
			Prices comp_prices = new Prices();
			comp_prices.setPeriod(complementary.id, complementary.contract);
			comp_prices.getPrices();
			comp_e = comp_prices.board_bases.elements();
		}
		
		double price = 0;
		for (e = prices.board_bases.elements(); e.hasMoreElements() ;) {
			board_basisPeriod = (xcaping.BoardBasisPeriod)e.nextElement();
			if (complementary.hasComplementary)  { comp_board_basisPeriod = (xcaping.BoardBasisPeriod)comp_e.nextElement(); } 
			
			this.cadena = board_basisPeriod.price;
			if (offer) {
				this.validPriceOrNull(500.00);
				// validaciÛn de ofertas m·s caras: - recuperar periodo base - localizar precio bb - comparar
			} else {
				this.validPrice(500.00);
			}
			if (valid) {
				// if the price's period is complementary, both board basis prices must be compared, so they aren't equal
				// if so, a 0.01 is added to the price, otehrwise the reading system would generate an error
				// - checking and fixing the complementary price:
				if (complementary.hasComplementary)  { this.validComplementary(comp_board_basisPeriod.price); }
				// -----------------------------
				
				// checking if each board basis price is equal or higher than the previous one (sc<=ro<=bb<=hb<=fb<=ai)  
				if (price>Double.valueOf(board_basisPeriod.price).doubleValue()) { 
					valid = false;
				} else {
					price=Double.valueOf(board_basisPeriod.price).doubleValue();
				}
				// -----------------------------
			}			
			board_basisPeriod.price = this.cadena;
			if (!valid && !finished) {
				this.message = "invalid board_basis";
				finished = true;
			}
			
		}
		
		for (e = prices.discounts.elements(); e.hasMoreElements() ;) {
			discountPeriod = (xcaping.DiscountPeriod)e.nextElement();
			
			this.cadena = discountPeriod.amount;
			if (offer) {
				if (discountPeriod.percentage.equals("1")) {
					this.validPercentageOrNull(100);
				} else {
					this.validPriceOrNull(500.00);
				}
			} else {
				if (discountPeriod.percentage.equals("1")) {
					this.validPercentage(100);
				} else {
					this.validPrice(500.00);
				}
			}			
			discountPeriod.amount = this.cadena;
			if (!valid && !finished) {
				this.message = "invalid discount";
				finished = true;
			}
		}
		for (e = prices.rooms.elements(); e.hasMoreElements() ;) {
			roomPeriod = (xcaping.RoomPeriod)e.nextElement();
			
			this.cadena = roomPeriod.price;
			if (roomPeriod.percentage.equals("1")) {
				this.validPercentage(100);
			} else {
				this.validPriceOrNullToZero(500.00);
			}
			roomPeriod.price = this.cadena;
			if (!valid && !finished) {
				this.message = "invalid room price";
				finished = true;
			}
			
			if (!offer) {
				this.cadena = roomPeriod.allotment;
				this.validInteger(50);
				roomPeriod.allotment = this.cadena;
				if (!valid && !finished) {
					this.message = "invalid allotment";
					finished = true;
				}
				
				this.cadena = roomPeriod.release;
				this.validInteger(300);
				roomPeriod.release = this.cadena;
				if (!valid && !finished) {
					this.message = "invalid release";
					finished = true;
				}
			}			
			
			this.cadena = roomPeriod.unit;
			this.validInteger(3);
			roomPeriod.unit = this.cadena;			
		}
		return (valid);
	}
	
	/** contract gala validation
	 * @param gala a Gala object with all the info
	 * @return true if all the data has valid values
	 */
	public boolean validateGalaContract(Gala gala) {
		
		valid = true;
		finished = false;
		
		this.cadena = gala.price;
		this.validPrice(500.00);
		gala.price = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid price";
			finished = true;
		}
		
		this.cadena = gala.obligatory;
		this.validBoolean();
		gala.obligatory = this.cadena;
		
		this.cadena = gala.child_discount;
		this.validPercentage(100);
		gala.child_discount = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid child discount";
			finished = true;
		}
		
		this.cadena = gala.adult_discount;
		this.validPercentage(100);
		gala.adult_discount = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid adult discount";
			finished = true;
		}
		
		return (valid);
	}
	
	/** system gala validation
	 * @param gala a Gala object with all the info
	 * @return true if all the data has valid values
	 */
	public boolean validate(Gala gala) {
		
		valid = true;
		finished = false;
		
		gala = this.validDates(gala);
		if (!valid && !finished) {
			this.message = "invalid dates";
			finished = true;
		}
		
		this.cadena = gala.name;
		this.validString(2);
		gala.name = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid name";
			finished = true;
		}
		
		this.cadena = gala.obligatory;
		this.validBoolean();
		gala.obligatory = this.cadena;
				
		return (valid);		
	}
	
	public boolean validate(AddOn add_on) {
		
		valid = true;
		finished = false;
		
		add_on = this.validDates(add_on);
		if (!valid) {
			this.message = "invalid dates";
			finished = true;
		}
		
		this.cadena = add_on.name;
		this.validString(2);
		add_on.name = this.cadena;
		
		this.cadena = add_on.price;
		this.validPrice(500.00);
		add_on.price = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid price";
			finished = true;
		}
		
		this.cadena = add_on.child_discount;
		this.validPercentage(100);
		add_on.child_discount = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid child discount";
			finished = true;
		}
		
		this.cadena = add_on.adult_discount;
		this.validPercentage(100);
		add_on.adult_discount = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid adult discount";
			finished = true;
		}
		
		this.cadena = add_on.edit_date;
		this.validDate();
		add_on.edit_date = this.cadena;
		
		return (valid);
	}
	
	public boolean validate(BoardBasisPeriod board_basisPeriod) {
		
		valid = true;
		
		this.cadena = board_basisPeriod.price;
		this.validPriceOrNullToZero(500.00);
		board_basisPeriod.price = this.cadena;
		
		return (valid);
	}
	
	public boolean validate(DiscountPeriod discountPeriod) {
		
		valid = true;
		
		this.cadena = discountPeriod.amount;
		if (discountPeriod.percentage.equals("1")) {
			this.validPercentage(100);
		} else {
			this.validPriceOrNullToZero(500.00);
		}
		discountPeriod.amount = this.cadena;
		
		return (valid);		
	}
	
	public boolean validate(StopSale stop_sale) {
		
		valid = true;
		
		stop_sale = this.validDates(stop_sale);
		if (!valid && !finished) {
			this.message = "invalid dates";
			finished = true;
		}
		
		if (!valid && !finished) {
			this.message = "invalid dates";
			finished = true;
		}
		
		this.cadena = stop_sale.edit_date;
		this.validDate();
		stop_sale.edit_date = this.cadena;
		
		return (valid);		
	}
	
	public boolean validate(Availability availability) {
		
		valid = true;
		
		this.cadena = availability.fromDate;
		this.validDate();
		availability.fromDate = this.cadena;
		
		this.cadena = availability.toDate;
		this.validDate();
		availability.toDate = this.cadena;
		
		this.cadena = availability.editDate;
		this.validDate();
		availability.editDate = this.cadena;
		
		return (valid);
	}
	
	public boolean validate(Priceline priceline) {
		
		valid = true;
		
		this.cadena = priceline.fromDate;
		this.validDate();
		priceline.fromDate = this.cadena;
		
		this.cadena = priceline.toDate;
		this.validDate();
		priceline.toDate = this.cadena;
		
		this.cadena = priceline.introDate;
		this.validDate();
		priceline.introDate = this.cadena;
		
		return (valid);
	}
	
	public boolean validate(Property property) {
		
		valid = true;
		
		this.cadena = property.name;
		this.validString(2);
		property.name = this.cadena;
		
		this.cadena = property.address;
		this.validString(2);
		property.address = this.cadena;
		
		this.cadena = property.city;
		this.validString(2);
		property.city = this.cadena;
		
		this.cadena = property.post_code;
		this.validInteger(100000);
		property.post_code = this.cadena;
		
		this.cadena = property.telephone;
		this.validString(2);
		property.telephone = this.cadena;
		
		this.cadena = property.fax;
		this.validString(2);
		property.fax = this.cadena;
		
		this.cadena = property.email;
		this.validString(2);
		property.email = this.cadena;
		
		this.cadena = property.web;
		this.validString(2);
		property.web = this.cadena;
		
		this.cadena = property.contact;
		this.validString(2);
		property.contact = this.cadena;
		
		return (valid);		
	}
	
	public boolean validate(DescriptionHeading description_heading) {
		
		valid = true;
		
		this.cadena = description_heading.heading;
		this.validString(2);
		description_heading.heading = this.cadena;
		
		return (valid);		
	}
	
	public boolean validate(Description description) {
		
		valid = true;
		
		this.cadena = description.description;
		this.validString(2);
		description.description = this.cadena;
		
		return (valid);		
	}
	
	public boolean validate(PhotoItem photo_item) {
		
		valid = true;
		
		this.cadena = photo_item.description;
		this.validString(2);
		photo_item.description = this.cadena;
				
		return (valid);		
	}
	
	public boolean validate(PropertyPhotoHeading heading) {
		
		valid = true;
		
		this.cadena = heading.heading;
		this.validString(2);
		heading.heading = this.cadena;
				
		return (valid);		
	}
	
	public boolean validate(Item item) {
		
		valid = true;
		
		this.cadena = item.name;
		this.validString(2);
		item.name = this.cadena;
				
		return (valid);		
	}
	
	/** allocation validation
	 * @param allocation an Allocation object with all the info
	 * @return true if all the data has valid values
	 */
	public boolean validate(Allocation allocation) {
		
		valid = true;
		finished = false;
		
		allocation = this.validDates(allocation);
		if (!valid && !finished) {
			this.message = "invalid dates";
			finished = true;
		}
		
		this.cadena = allocation.allocation;
		this.validInteger(50);
		allocation.allocation = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid allocation";
			finished = true;
		}
		
		this.cadena = allocation.edit_date;
		this.validDate();
		allocation.edit_date = this.cadena;
		
		return (valid);
	}
	
	
	/** supplier validation
	 * @param supplier a Supplier object with all the info
	 * @return true if all the data has valid values
	 */
	public boolean validate(Supplier supplier) {
		
		valid = true;
		finished = false;
		
		this.cadena = supplier.name;
		this.validString(2);
		supplier.name = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid supplier name";
			finished = true;
		}
		
		this.cadena = supplier.handling_fee_adult;
		this.validPriceOrNullToZero(100);
		supplier.handling_fee_adult = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid handling fee adult";
			finished = true;
		}
		
		this.cadena = supplier.handling_fee_child;
		this.validPriceOrNullToZero(100);
		supplier.handling_fee_child = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid handling fee child";
			finished = true;
		}
		
		return (valid);
	}
	
	/** tells if the gala dates are into the contract period - needed to show the galas available for the contract
	 * @param gala
	 * @param contract_id
	 * @return true if the gala is between the contract dates
	 */
	public boolean inContract(Gala gala, String contract_id) {
		
		boolean is = false;
		
		Contract contract = new Contract();
		contract.get(contract_id);
		
		String[] array = new String[3];
		int day;
		int month;
		int year1;
		int year2;
		int year_contract;
		year_contract = contract.getYear(contract_id);
		array = gala.from_date.split("/");
		day = Integer.parseInt(array[0]);
		month = Integer.parseInt(array[1]);
		year1 = Integer.parseInt(array[2]);
		
		gala.from_date = String.valueOf(year_contract)+"-"+month+"-"+day;
		
		array = gala.to_date.split("/");
		day = Integer.parseInt(array[0]);
		month = Integer.parseInt(array[1]);
		year2 = Integer.parseInt(array[2]);
		
		if (year1 < year2) {
			year_contract = year_contract+1;
		}
		
		gala.to_date = String.valueOf(year_contract)+"-"+month+"-"+day;
		
		if (this.between(contract.from_date, gala.from_date, contract.to_date, true) && 
				this.between(contract.from_date, gala.to_date, contract.to_date, true)) {
			is = true;
		} else {
			is = false;
		}
		return (is);
	}
	
	/** system supplements validation
	 */
	public boolean validate(Supplement supplement) {
		
		valid = true;
		finished = false;
		
		this.cadena = supplement.description;
		this.validString(2);
		supplement.description = this.cadena;
		if (!valid && !finished) {
			this.message = "invalid name";
			finished = true;
		}
				
		return (valid);		
	}
	
}