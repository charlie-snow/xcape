package xcaping;

import java.sql.*;

public class StopSale extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert the stop sale";
	
	public String id;
	public String property;
	public String from_date;
	public String to_date;
	public String room;
	public String edited_by;
	public String edit_date;
	
	public String returnURL;
	public String errorURL;
	
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
	
	public boolean insert(String property_id) {
		this.property = property_id;
		connect();
		boolean valid = true;
		if (validate()) {
			try {
	          	Statement sentencia = conexion.createStatement();
	          	
	          	SQL = "select max(stop_sale_id) as max from "+this.tables_modifyer+"[stop_sale]";
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
	          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[stop_sale] values ('"
	          	+this.id+"', '"+property_id+"', '"
				+this.from_date+"', '"+this.to_date
				+"', '"+this.room+"', '"+this.edited_by
				+"', '"+this.edit_date+"')";
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      		this.error = ex.toString();
	      		valid = false;
	      	}
		} else {
			valid = false;
		}
		//  old system - inserts the stop sale not caring about the contract state or existance
		Converter converter = new Converter();
		if (valid) {
			converter.insertAvailabilitiesStopSale(this.id);
		}
		// -- old system
		disconnect();
		return (valid);
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[stop_sale] where stop_sale_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("stop_sale_id");
      		this.property = result.getString("property_stop_sale");
      		this.from_date = result.getString("from_date_stop_sale");
      		this.to_date = result.getString("to_date_stop_sale");
      		this.room = result.getString("room_stop_sale");
      		this.edited_by = result.getString("edited_by_stop_sale");
      		this.edit_date = result.getString("edit_date_stop_sale");
      		
      		this.format();
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String id) {
		this.get(id);
		connect();
		try {			
			// delete from the old system
			Converter converter = new Converter();
			/*converter.deleteAvailability(this.property, this.from_date, this.to_date, "0", true);*/
			converter.deleteAvailabilitiesStopSale(id);
			
			SQL = "delete from "+this.tables_modifyer+"[stop_sale] where stop_sale_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);			
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean modify(String id) {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
				SQL = this.SQLprefix+"update "+this.tables_modifyer+"[stop_sale] set " +
				"property_stop_sale='"+this.property+"', "+
				"from_date_stop_sale='"+this.from_date+"', "+
				"to_date_stop_sale='"+this.to_date+"', "+
				"room_stop_sale='"+this.room+"' " +
				"edited_by_stop_sale='"+this.edited_by+"', "+
				"edit_date_stop_sale='"+this.edit_date+"' " +
				"where stop_sale_id='"+id+"'";
				Statement sentencia = conexion.createStatement();
				sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      		valid = false;
	      	}
		} else {
			valid = false;
		}
		disconnect();
		return (valid);
	}
	
	public void getURLs (String item_id, String item_type) {
		this.returnURL = "index.jsp?content=item&item_id="+item_id+"&item_type="+item_type+"&subcontent=stop_sales";
		this.errorURL = "index.jsp?content=item&item_id="+item_id+"&item_type="+item_type+"&subcontent=error&error=";
	}
}
