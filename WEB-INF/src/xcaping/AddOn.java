package xcaping;

import java.sql.*;

public class AddOn extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert the add on";
	
	public String id;
	public String supplement;
	public String contract;
	public String from_date;
	public String to_date;
	public String name;	
	public String price;
	public String child_discount;
	public String child_percentage;
	public String adult_discount;
	public String adult_percentage;
	public String edit_date;
	public String edited_by;
	
	public String returnURL = "";
	public String editURL = "";
	public String deleteURL = "";
	public String errorURL = "";
	
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
			Validation validation = new Validation();
  			this.from_date = validation.getSQLDate(this.from_date);
			this.to_date = validation.getSQLDate(this.to_date);
			
          	Statement sentencia = conexion.createStatement();
          	SQL = this.SQLprefix+"select * from "+this.tables_modifyer+"[add_on_contract] " +
          		"where supplement_add_on='"+this.supplement+"' " +
          		"and contract_add_on='"+this.contract+"' " +
          		"and from_date_add_on='"+this.from_date+"' " +
          		"and to_date_add_on='"+this.to_date+"'";
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	connect();
      	return (exists);
	}
	
	public boolean insert() {
		connect();
		boolean valid = true;
		if (!this.exists()) {
			if (validate()) {
				try {
					
					// old system: if generated, insert the priceline
					Contract contract = new Contract();
					contract.get(this.contract);
					if (contract.state.equals("active") || contract.state.equals("inactive")) {
						Converter converter = new Converter();
						converter.setContract(this.contract);
						converter.insertAddOn(this, true);
					}
					//-----------------------------------------
					
					Statement sentencia = conexion.createStatement();
		          	SQL = "select max(add_on_id) as max from "+this.tables_modifyer+"[add_on_contract]";
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
					
		          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[add_on_contract] " +
		          		"([add_on_id], [supplement_add_on], [contract_add_on], [name_add_on], " +
		          		"[from_date_add_on], [to_date_add_on], [price_add_on], [child_discount], " +
		          		"[percentage_child_add_on], [adult_discount], [percentage_adult_add_on], " +
		          		"[edit_date_add_on], [edited_by_add_on]) " +
		          		"values ('"+this.id+"' ,'"+this.supplement+"', '"
						+this.contract+"', '"+this.name+"', '"+this.from_date+"', '"+this.to_date+"', '"
						+this.price+"', '"+this.child_discount+"', '"+this.child_percentage+"', '"
						+this.adult_discount+"', '"+this.adult_percentage+"', '"
						+this.edit_date+"', '"+this.edited_by+"')";
		      		sentencia.execute(SQL);
		      		
				} catch (Exception ex) {
		      		valid = false;
		      		this.error = ex.toString();
		      	}
			} else {
				valid = false;
			}
		} else {
			valid = false;
			this.error = "this supplement already exist for this dates";
		}
		
		disconnect();
		return(valid);
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[add_on_contract] where add_on_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("add_on_id");
      		this.supplement = result.getString("supplement_add_on");
      		this.contract = result.getString("contract_add_on");
			this.name = result.getString("name_add_on");
      		this.from_date = result.getString("from_date_add_on");
      		this.to_date = result.getString("to_date_add_on");
      		this.price = result.getString("price_add_on");
      		this.child_discount = result.getString("child_discount");
      		this.child_percentage = result.getString("percentage_child_add_on");
      		this.adult_discount = result.getString("adult_discount");
      		this.adult_percentage = result.getString("percentage_adult_add_on");
      		this.edit_date = result.getString("edit_date_add_on");
      		this.edited_by = result.getString("edited_by_add_on");
      		this.format();
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String id) {
		this.get(id);
		connect();
		try {
			// old system: if generated delete the priceline			
			Contract contract = new Contract();
			contract.get(this.contract);
			if (contract.state.equals("active") || contract.state.equals("inactive")) {
				Converter converter = new Converter();
				converter.setContract(this.contract);
				converter.deletePricelines(contract.property, this.from_date, this.to_date, this.supplement, true);
			}
			
			SQL = "delete from "+this.tables_modifyer+"[add_on_contract] where add_on_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void modify(String id) {
		connect();
		try {
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[add_on_contract] set " +
				"supplement_add_on='"+this.supplement+", from_date_add_on='"+this.from_date
				+"', to_date_add_on='"+this.to_date+"', name_add_on='"+this.name+"', price_add_on='"+this.price
				+"', child_discount='"+this.child_discount+"', child_percentage='"+this.child_percentage
				+"', adult_discount='"+this.adult_discount+"', adult_percentage='"+this.adult_percentage
				+"', edit_date_add_on='"+this.edit_date+"', edited_by_add_on='"+this.edited_by+"' " +
				"where add_on_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void getURLs (String contract_id) {
		this.returnURL = "index.jsp?content=contract&contract_id="+contract_id+"&subcontent=contract_add_ons";
		//this.editURL = "index.jsp?content=contract&contract_id="+contract_id+"&subcontent=formEditPeriod&period_id="+this.id;
		this.deleteURL = "delete_add_on.jsp?add_on_id="+this.id+"&contract_id="+contract_id;
		
		this.errorURL = "index.jsp?content=contract&contract_id="+contract_id+"&subcontent=error&error=";
	}
	
}
