package xcaping;

import java.sql.*;

public class DiscountPeriod extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert discount";
	public String name;
	
	public String period_id;
	public String discount_id;
	public String amount;
	public String percentage;
		
	public boolean validate() {
		Validation validation = new Validation();
		boolean valid = validation.validate(this);
		if (valid == false) {
			this.error = validation.message;
		}
		return(valid);
	}
	
	public void format() {
		Formatter formatter = new Formatter();
		formatter.format(this);
	}
	
	private boolean exists() {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select period_dp from "+this.tables_modifyer+"[discount_period] where period_dp='"+this.period_id+"' " +
          			"and discount_dp='"+this.discount_id+"'";
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (exists);
	}
	
	private boolean insert() {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
	          	Statement sentencia = conexion.createStatement();
	          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[discount_period] values ('"+this.period_id+
				"','"+this.discount_id+"', '"+this.amount+"', '"+this.percentage+"')";
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
	
	public void set() {
		
		if (!exists()) {
			this.insert();
		} else {
			this.modify();
		}
	}
	
	public void get(String period_id, String discount_id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[discount_period] where " +
				"period_dp='"+period_id+
				"' and discount_dp='"+discount_id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		Discount discount = new Discount();
      		discount.get(discount_id);
      		this.name = discount.name;
      		this.period_id = result.getString("period_dp");
			this.discount_id = result.getString("discount_dp");
			this.amount = result.getString("amount_dp");
			this.percentage = result.getString("percentage_dp");
			
			this.format();
			
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[discount_period] " +
			"where period_dp='"+this.period_id+"' and discount_dp='"+this.discount_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean modify() {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
				SQL = this.SQLprefix+"update "+this.tables_modifyer+"[discount_period] set amount_dp='"+this.amount+"', "+
				"percentage_dp='"+this.percentage+"' " +
				"where period_dp='"+this.period_id+"' and discount_dp='"+this.discount_id+"'";
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

}
