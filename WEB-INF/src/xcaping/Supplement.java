package xcaping;

import java.sql.*;

public class Supplement extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert the add on";
	
	public String id;
	public String description;
	
	public boolean validate() {
		Validation validation = new Validation();
		boolean valid = validation.validate(this);
		if (valid == false) {
			this.error = validation.message;
		}
		return(valid);
	}
		
	public boolean insert() {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
				Statement sentencia = conexion.createStatement();
	          	
	          	SQL = "select max(SupplementID) as max from "+this.tables_modifyer+"[SupplementsTBL]";
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
				
	          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[SupplementsTBL] " +
	          		"(SupplementID, suplDescription)" +
	          		"values ('"+this.id+"', '"+this.description+"')";
	      		sentencia.execute(SQL);
			} catch (Exception ex) {
	      		valid = false;
	      	}
		} else {
			valid = false;
		}
		disconnect();
		return(valid);
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[SupplementsTBL] where SupplementID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("SupplementID");
      		this.description = result.getString("suplDescription");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[SupplementsTBL] where SupplementID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void modify(String id) {
		connect();
		try {
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[SupplementsTBL] set " +
				"suplDescription='"+this.description+
				"where SupplementID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
}
