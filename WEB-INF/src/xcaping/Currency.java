package xcaping;

import java.sql.*;

public class Currency extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String id;
	public String name;
	public String symbol;
	public String exchange;
		
	public void insert() {
		connect();
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[CurrencyExchangeTBL] values ('','"+this.name
			+"','"+this.symbol+"', '"+this.exchange+"', '0', '0')";
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[CurrencyExchangeTBL] where exchangeID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("exchangeID");
			this.name = result.getString("currency");
      		this.symbol = result.getString("symble");
      		//this.exchange = result.getString("exchange");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[CurrencyExchangeTBL] where exchangeID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void modify(String id) {
		connect();
		try {
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[CurrencyExchangeTBL] set currency='"+this.name+"', "+
				"symble='"+this.symbol+"', '"+this.exchange+"'" +
				"where exchangeID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}

}
