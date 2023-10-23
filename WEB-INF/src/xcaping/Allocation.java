package xcaping;

import java.sql.*;

public class Allocation extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert the allocation";
	
	public String id;
	public String contract;
	public String room;
	public String from_date;
	public String to_date;
	public String allocation;
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
	
	public boolean insert(String contract_id) {
		connect();
		boolean valid = true;
		String output;
		java.text.SimpleDateFormat formatter;
		java.util.Date today = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd MM yy");
		this.edit_date = formatter.format(today);
				
		if (validate()) {
			try {				
				Statement sentencia = conexion.createStatement();
	          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[allocation] values ('"
				+this.contract+"','"+this.room+"', '"+this.from_date+"', '"+this.to_date+"', '"
				+this.allocation+"', '"+this.edited_by+"', '"+this.edit_date+"')";
	      		sentencia.execute(SQL);
			} catch (Exception ex) {
				this.error = ex.toString();
	      		valid = false;
	      	}
		} else {
			valid = false;
		}
		disconnect();
		this.getURLs(contract_id);
		return(valid);
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[allocation] where allocation_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("allocation_id");
      		this.contract = result.getString("contract_allocation");
      		this.room = result.getString("room_allocation");
      		this.from_date = result.getString("from_allocation");
      		this.to_date = result.getString("to_allocation");
      		this.allocation = result.getString("allocation_allocation");
      		this.edited_by = result.getString("edited_by_all");
      		this.edit_date = result.getString("edit_date_all");
      		     		
      		this.format();
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean delete(String id) {
      	this.get(id);
      	this.getURLs(this.contract);
      	connect();
		boolean valid = true;
		try {
			SQL = "delete from "+this.tables_modifyer+"[allocation] where allocation_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (valid);
	}
	
	public boolean modify(String id) {
		connect();
		boolean valid = true;
		String output;
		java.text.SimpleDateFormat formatter;
		java.util.Date today = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd MM yy");
		this.edit_date = formatter.format(today);
		
		if (validate()) {
				try {			
					SQL = this.SQLprefix+"update "+this.tables_modifyer+"[allocation] set "
					+"room_allocation='"+this.room
					+"', allocation_allocation='"+this.allocation
					+"', from_allocation='"+this.from_date
					+"', to_allocation='"+this.to_date
					+"', edited_by_all='"+this.edited_by
					+"', edit_date_all='"+this.edit_date+"' "
					+"where allocation_id='"+id+"'";
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
		this.get(id);
		this.getURLs(this.contract);
		return(valid);
	}
	
	public void getURLs (String contract_id) {
		this.returnURL = "index.jsp?content=contract&contract_id="
			+contract_id+"&subcontent=allocations";
		this.errorURL = "index.jsp?content=error&error=";
	}
}
