package xcaping;

import java.sql.*;

public class Supplier extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert the supplier";
	
	public String id;
	public String name;
	public String handling_fee_adult;
	public String handling_fee_child;
	public String contact_name;
	public String contact_title;
	public String address1;
	public String address2;
	public String city;
	public String county;
	public String post_code;
	public String country;
	public String tel;
	public String tel_ext;
	public String fax;
	public String email;
	public String www;
	public String type = "1";
	public String customer_service;

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
	
	private boolean exists() {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+this.tables_modifyer+"[SuppliersTBL] " +
			"where supplierName='"+this.name+"'";
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
		          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[SuppliersTBL] " +
		          	"(suppliersName, handlingFeeAdult, handlingFeeChild, contactName, contactTitle, " +
		          	"address1, address2, city, county, postCode, country, tel, telExt, fax, email, www, type, customerService)" +
		          	"values ('"+this.name+"','"+this.handling_fee_adult+"', '"+this.handling_fee_child+"', '"
		          	+this.contact_name+"', '"+this.contact_title+"', '"+this.address1+"', '"+this.address2+"', '"
		          	+this.city+"', '"+this.county+"', '"+this.post_code+"', '"+this.country+"', '"
		          	+this.tel+"', '"+this.tel_ext+"', '"+this.fax+"', '"+this.email+"', '"
		          	+this.www+"', '"+this.type+"', '"+this.customer_service+"')";
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
			SQL = "select * from "+this.tables_modifyer+"[SuppliersTBL] where supplierID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("supplierID");
      		this.name = result.getString("suppliersName");
      		this.handling_fee_adult = result.getString("handlingFeeAdult");
      		this.handling_fee_child = result.getString("handlingFeeChild");
      		this.contact_name = result.getString("contactName");
      		this.contact_title = result.getString("contactTitle");
      		this.address1 = result.getString("address1");
      		this.address2 = result.getString("address2");
      		this.city = result.getString("city");
      		this.county = result.getString("county");
      		this.post_code = result.getString("postCode");
      		this.country = result.getString("country");
      		this.tel = result.getString("tel");
      		this.tel_ext = result.getString("telExt");
      		this.fax = result.getString("fax");
      		this.email = result.getString("email");
      		this.www = result.getString("www");
      		this.type = result.getString("type");
      		this.customer_service = result.getString("customerService");
      		
      		String chain = "";
      		for (int x=0; x < this.name.length(); x++) {
      		  if (this.name.charAt(x) != ' ')
      		    chain += this.name.charAt(x);
      		}
      		this.name = chain;
      		
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean delete(String id) {
		connect();
		boolean valid = true;
		if (this.isEmpty(id)) {
			try {
				SQL = "delete from "+this.tables_modifyer+"[SuppliersTBL] where supplierID='"+id+"'";
	          	Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      	}
		} else {
			this.error = "this supplier is used on a contract";
			valid = false;
		}
      	disconnect();
      	return (valid);
	}
	
	private boolean isEmpty(String id) {
		connect();
		boolean empty = true;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+this.tables_modifyer+"contract " +
			"where supplier_contract='"+id+"'";
          	result = sentencia.executeQuery(SQL);
      		if (result.next()) {
      			empty = false;
      		}
      		
      		// old system: check if this supplier is used on any priceline
      		SQL = "select * from "+this.tables_modifyer+"PriceTBL " +
			"where supplierID='"+id+"'";
          	result = sentencia.executeQuery(SQL);
      		if (result.next()) {
      			empty = false;
      		}
      	} catch (Exception ex) {
      	}
      	return (empty);
	}
		
	public boolean modify(String id) {
		connect();
		boolean valid = true;
		if (validate()) {
				try {
					SQL = this.SQLprefix+"update "+this.tables_modifyer+"[SuppliersTBL] " +
					"set suppliersName='"+this.name+"', handlingFeeAdult='"+this.handling_fee_adult
					+"', handlingFeeChild='"+this.handling_fee_child+"', contactName='"+this.contact_name
					+"', contactTitle='"+this.contact_title+"', address1='"+this.address1
					+"', address2='"+this.address2+"', city='"+this.city
					+"', county='"+this.county+"', postCode='"+this.post_code
					+"', country='"+this.country+"', tel='"+this.tel
					+"', telExt='"+this.tel_ext+"', fax='"+this.fax
					+"', email='"+this.email+"', www='"+this.www
					+"', type='"+this.type+"', customerService='"+this.customer_service
					+"' "+"where supplierID='"+id+"'";
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
	
	public void getURLs () {
		this.returnURL = "index.jsp?content=suppliersList";
		this.errorURL = "index.jsp?content=error&error=";
	}
}
