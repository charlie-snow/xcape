package xcaping;

import java.sql.*;

public class Discount extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String id;
	public String name;
		
	public void setData(String name) {
		this.name = name;
	}
	
	public void insert() {
		connect();
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = this.SQLprefix+"insert into discount values ('','"+this.name+"')";
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[discount] where discount_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("discount_id");
			this.name = result.getString("name_discount");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String discount_id) {
		connect();
		try {
			deleteDiscountContract(discount_id);
			deleteDiscountPeriod(discount_id);
			SQL = "delete from "+this.tables_modifyer+"[discount] where discount_id='"
				+discount_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	private void deleteDiscountContract(String discount_id) {
		try {
			SQL = "delete from "+this.tables_modifyer+"[discount_contract] where " +
				"discount_disc_con='"+discount_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	private void deleteDiscountPeriod(String discount_id) {
		try {
			SQL = "delete from "+this.tables_modifyer+"[discount_period] where " +
				"discount_dp='"+discount_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	public void modify(String id) {
		connect();
		try {
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[discount] set name_discount='"+this.name+"' "+
				"where discount_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}

}
