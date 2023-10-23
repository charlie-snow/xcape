package xcaping;

import java.sql.*;

public class RoomPeriod extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String name;
	
	public String period_id;
	public String room_id;
	public String price;
	public String percentage;
	public String allotment;
	public String unit;
	public String release;
		
	public void format() {
		Formatter formatter = new Formatter();
		formatter.format(this);
	}
	
	public boolean exists() {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select period_rp from "+this.tables_modifyer+"[room_period] where period_rp='"+this.period_id+"' " +
          			"and room_rp='"+this.room_id+"'";
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (exists);
	}
	
	public void insert() {
		connect();
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[room_period] values ('"+this.period_id
			+"','"+this.room_id+"', '"+this.price+"', '"+this.percentage
			+"', '"+this.allotment+"', '"+this.unit+"', '"+this.release+"')";
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void set() {
		
		if (!exists()) {
			this.insert();
		} else {
			this.modify();
		}
	}
	
	public void create() {
		
		if (!exists()) {
			this.insert();
		}
	}
	
	public void get(String period_id, String room_id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[room_period] where " +
				"period_rp='"+period_id+"' and " +
				"room_rp='"+room_id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		Room room = new Room();
      		room.get(room_id);
      		this.name = room.name;
      		this.period_id = result.getString("period_rp");
			this.room_id = result.getString("room_rp");
			this.price = result.getString("price_rp");
			this.percentage = result.getString("percentage_rp");
			this.allotment = result.getString("allotment");
			this.unit = result.getString("unit");
			this.release = result.getString("release");
			
			this.format();
			
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[room_period] " +
			"where period_rp='"+this.period_id+"' and room_rp='"+this.room_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void modify() {
		connect();
		try {
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[room_period] set price_rp='"+this.price+"', "+
			"percentage_rp='"+this.percentage+"', " +
			"allotment='"+this.allotment+"', " +
			"unit='"+this.unit+"', " +
			"release='"+this.release+"' " +
			"where period_rp='"+this.period_id+"' and room_rp='"+this.room_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}

}
