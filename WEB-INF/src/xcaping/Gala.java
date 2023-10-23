package xcaping;

import java.sql.*;

public class Gala extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert the gala";
	
	public String id;
	public String name;
	public String obligatory;
	public String from_date;
	public String to_date;
	
	// contract galas
	public String contract;
	public String price;
	public String child_discount;
	public String adult_discount;
	
	public String returnURL;
	public String errorURL;
	public String returnContractURL;
	public String errorContractURL;
	
	public boolean validateGalaContract() {
		Validation validation = new Validation();
		boolean valid = validation.validateGalaContract(this);
		if (valid == false) {
			this.error = validation.message;
		}
		return(valid);
	}
	
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
	
	private boolean exists() {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+this.tables_modifyer+"[gala] " +
			"where name_gala='"+this.name+"'";
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	public boolean insert() {
		connect();
		boolean valid = true;
		if (validate()) {
			if (!this.exists()) {
				try {
					Statement sentencia = conexion.createStatement();
		          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[gala] values ('"+this.name
					+"','"+this.obligatory+"', '"+this.from_date+"', '"
					+this.to_date+"')";
		      		sentencia.execute(SQL);
				} catch (Exception ex) {
					this.error = ex.toString();
		      		valid = false;
		      	}
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
			SQL = "select * from "+this.tables_modifyer+"[gala] where gala_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("gala_id");
			this.name = result.getString("name_gala");
      		this.obligatory = result.getString("obligatory_gala");
      		this.from_date = result.getString("from_date_gala");
      		this.to_date = result.getString("to_date_gala");
      		
      		this.format();
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean delete(String id) {
		connect();
		boolean valid = true;
		if (this.isEmpty(id)) {
			try {
				deleteGalaContract(id);
				SQL = "delete from "+this.tables_modifyer+"[gala] where gala_id='"+id+"'";
	          	Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      	}
		} else {
			this.error = "this gala is in use by a contract";
			valid = false;
		}
      	disconnect();
      	return (valid);
	}
	
	private boolean isEmpty(String gala_id) {
		connect();
		boolean empty = true;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+this.tables_modifyer+"gala_contract " +
			"where gala_gala_contract='"+gala_id+"'";
          	result = sentencia.executeQuery(SQL);
      		if (result.next()) {
      			empty = false;
      		}
      	} catch (Exception ex) {
      	}
      	return (empty);
	}
	
	private void deleteGalaContract(String id) {
		try {
			SQL = "delete from "+this.tables_modifyer+"[gala_contract] where gala_gala_contract='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	public boolean modify(String id) {
		connect();
		boolean valid = true;
		if (validate()) {
				try {
					SQL = this.SQLprefix+"update "+this.tables_modifyer+"[gala] set name_gala='"+this.name
					+"', "+"obligatory_gala='"+this.obligatory
					+"', from_date_gala='"+this.from_date
					+"', to_date_gala='"+this.to_date+"' "
					+"where gala_id='"+id+"'";
		          	Statement sentencia = conexion.createStatement();
		      		sentencia.execute(SQL);
		      	} catch (Exception ex) {
		      		this.error = ex.toString();
		      		valid = false;
		      	}
		} else {
			valid = false;
		}
		disconnect();
		return(valid);
	}
	
	public void getURLs (String contract_id) {
		this.returnURL = "index.jsp?content=galasList";
		this.errorURL = "index.jsp?content=error&error=";
		
		this.returnContractURL = "galasContractList.jsp?contract_id="+contract_id+"&modify=1";
		this.errorContractURL = "error.jsp?error=";
	}
}
