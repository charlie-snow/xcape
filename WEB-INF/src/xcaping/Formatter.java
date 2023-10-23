package xcaping;

public class Formatter {
	
	private String cadena;
	boolean valid = true;
	
	private void formatDate() {
		
		String[] array = new String[3];
		String date = "";
		try {
			date = cadena.substring(0, 10);
			array = date.split("-");
			int year = Integer.parseInt(array[0].substring(2, 4));
			int month = Integer.parseInt(array[1]);
			int day = Integer.parseInt(array[2]);				
			date = day+"/"+month+"/"+year;
			cadena = date;
		} catch (Exception ex) {
		}
	}
	
	/** formats an SQL format date (yyyy-mm-dd:...) into an standard format (dd/mm/yy) 
	 * @param date
	 * @return
	 */
	public String formatDate(String date) {
		
		String[] array = new String[3];
		try {
			date = date.substring(0, 10);
			array = date.split("-");
			int year = Integer.parseInt(array[0].substring(2, 4));
			int month = Integer.parseInt(array[1]);
			int day = Integer.parseInt(array[2]);				
			date = day+"/"+month+"/"+year;				
		} catch (Exception ex) {
			int i = 1;
		}
		return (date);
	}
	
	public String formatCurrency(String currency) {
		
		double value = Double.parseDouble(currency);
		java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("###.##");
		currency = myFormatter.format(value);
		return (currency);
	}
	
	public void formatCurrency() {
		
		double value = Double.parseDouble(cadena);
		java.util.Locale locale = new java.util.Locale("en");
		java.text.NumberFormat form;
		form = java.text.NumberFormat.getInstance(locale);
		//java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("###.##");
		//cadena = myFormatter.format(value);
		cadena = form.format(value);
	}
	
	public String formatPercentage(String percentage) {
		
		double value = Double.parseDouble(percentage);
		java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("###.##");
		percentage = myFormatter.format(value)+"%";
		return (percentage);
	}
	
	/*private void formatNumber() {
		int point_pos = cadena.indexOf(".");
		if (point_pos != -1) {
			if (Integer.parseInt(cadena.substring(point_pos+1)) == 0) {
				cadena = cadena.substring(0, point_pos);
			}
		}
	}*/
	
	public void format(Contract contract) {
		
		this.cadena = contract.from_date;
		this.formatDate();
		contract.from_date = this.cadena;
		
		this.cadena = contract.to_date;
		this.formatDate();
		contract.to_date = this.cadena;		
	}
	
	public void format(Period period) {
		
		this.cadena = period.from_date;
		this.formatDate();
		period.from_date = this.cadena;
		
		this.cadena = period.to_date;
		this.formatDate();
		period.to_date = this.cadena;
		
		this.cadena = period.short_stay_suplement;
		this.formatCurrency();
		period.short_stay_suplement = this.cadena;
		
		this.cadena = period.air_conditioned;
		this.formatCurrency();
		period.air_conditioned = this.cadena;
		
	}
	
	public void format(BoardBasisPeriod board_basisPeriod ) {
		
		this.cadena = board_basisPeriod.price;
		this.formatCurrency();
		board_basisPeriod.price = this.cadena;
	}
	
	public void format(DiscountPeriod discountPeriod ) {
		
		this.cadena = discountPeriod.amount;
		this.formatCurrency();
		discountPeriod.amount = this.cadena;
	}
	
	public void format(RoomPeriod roomPeriod ) {
		
		this.cadena = roomPeriod.price;
		this.formatCurrency();
		roomPeriod.price = this.cadena;
		
	}
	
	public void format(AddOn addOn) {
		
		this.cadena = addOn.from_date;
		this.formatDate();
		addOn.from_date = this.cadena;
		
		this.cadena = addOn.to_date;
		this.formatDate();
		addOn.to_date = this.cadena;
		
		this.cadena = addOn.price;
		this.formatCurrency();
		addOn.price = this.cadena;
		
		this.cadena = addOn.child_discount;
		this.formatCurrency();
		addOn.child_discount = this.cadena;
		
		this.cadena = addOn.adult_discount;
		this.formatCurrency();
		addOn.adult_discount = this.cadena;
	}
	
	public void format(Gala gala) {
		
		this.cadena = gala.from_date;
		this.formatDate();
		gala.from_date = this.cadena;
		
		this.cadena = gala.to_date;
		this.formatDate();
		gala.to_date = this.cadena;		
	}
	
	public void format(StopSale stopSale) {
		
		this.cadena = stopSale.from_date;
		this.formatDate();
		stopSale.from_date = this.cadena;
		
		this.cadena = stopSale.to_date;
		this.formatDate();
		stopSale.to_date = this.cadena;
		
		this.cadena = stopSale.edit_date;
		this.formatDate();
		stopSale.edit_date = this.cadena;
	}
	
	public void format(Allocation allocation) {
		
		this.cadena = allocation.from_date;
		this.formatDate();
		allocation.from_date = this.cadena;
		
		this.cadena = allocation.to_date;
		this.formatDate();
		allocation.to_date = this.cadena;
		
		this.cadena = allocation.edit_date;
		this.formatDate();
		allocation.edit_date = this.cadena;
	}
}
