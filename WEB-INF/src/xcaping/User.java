package xcaping;

import java.sql.*;

public class User extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String id;
	public String name;
	public String password;
	public String type;
	
	public void insert() {
		connect();
		try {
			this.password = Auth.encrypt(this.password);
          	Statement sentencia = conexion.createStatement();
          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[user] values ('"+this.name
			+"','"+this.password+"','"+this.type+"')";
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[user] where user_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("user_id");
			this.name = result.getString("name_user");
			this.password = result.getString("password");
      		this.type = result.getString("type_user");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[user] where user_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void modify(String id) {
		connect();
		try {
			this.password = Auth.encrypt(this.password);
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[user] set name_user='"+this.name+"', "+
				"password='"+this.password+"', " +
				"type_user='"+this.type+"' " +
				"where user_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
}
