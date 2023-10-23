package xcaping;

import java.sql.*;

public class Rating extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String id;
	public String name;
	public String symble;
	
	public void insert() {
		connect();
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[rating] values ('','"+this.name
			+"','"+this.symble+"')";
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[PropertyRatingTBL] where ratingID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("ratingID");
			this.name = result.getString("rating");
      		this.symble = result.getString("symble");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[rating] where rating_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void modify(String id) {
		connect();
		try {
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[rating] set name_rating='"+this.name+"', "+
				"symble='"+this.symble+"' " +
				"where rating_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}

}
