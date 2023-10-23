package xcaping;

import java.sql.*;
//import java.util.Vector;

public class Property extends DBConnection{

	private String SQL;
	private String cadena;
	
	public String error = "failed to insert property";
	
	public String id = "";
	public String name = "";
	
	public String board_basis = "99";
	public String rating = "";
	public String resort = "";
	public String active = "";
	public String featuredResort = "99";
	public String featuredArea = "99";
	public String commissionable = "1";
	
	public String address = "";
	public String city = "";
	public String post_code = "";
	public String telephone = "";
	public String fax = "";
	public String email = "";
	public String web = "";
	public String contact = "";
	
	// search data
	
	public String resort_id = "";
	public String resort_name = "";
	public String area_id = "";
	public String area_name = "";
	
	public String edited_by;
	
	public boolean validate() {
		Validation validation = new Validation();
		return (validation.validate(this));
	}
	
	/*public boolean delete(String id) {
		boolean valid = true;
		if (!hasContracts()) {
			if (!hasPhotos()) {
				if (!hasDescriptions()) {
					connect();
					try {
						this.id = id;
						SQL = "delete from Property where propertyID='"+id+"'";
			          	Statement sentencia = conexion.createStatement();
			      		sentencia.execute(SQL);
			      	} catch (Exception ex) {
			      	}
			      	disconnect();
				} else {
					valid = false;
					this.error = "this property can't be deleted: it has descriptions";
				}
			} else {
				valid = false;
				this.error = "this property can't be deleted: it has photos";
			}
		} else {
			valid = false;
			this.error = "this property can't be deleted: it has contracts";
		}
		return (valid);
	}
	
	private void deletePhotos() {
		try {
      	} catch (Exception ex) {
      	}
	}
	
	private void deleteDescriptions() {
		try {
		} catch (Exception ex) {
      	}
	}*/
	
	/*public void get(String id) {
		connect();
		try {
			SQL = "select * from Property " +
					"where propertyID="+id;
          	Statement sentencia = conexion.createStatement();
      		ResultSet result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("propertyID");
    		this.name = result.getString("propertyName");
    		this.rating = result.getString("ratingID");
    		this.resort = result.getString("resortID");
    		this.active = result.getString("active");
    		
    		this.address = result.getString("address_property");
    		this.city = result.getString("city_property");
    		this.post_code = result.getString("post_code_property");
    		this.country = result.getString("country_property");
    		this.email = result.getString("email_property");
    		this.contact = result.getString("contact_property");
    		this.currency = result.getString("currency");
    		this.edited_by = result.getString("edited_by_property");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}*/
	
	public boolean addRoomExtra (String room_id, String property_id) {
		connect();
		boolean valid = true;
		try {
			if (!this.existsRoomExtra(room_id, property_id)) {
				Statement sentencia = conexion.createStatement();
	          	SQL = this.SQLprefix+"insert into PropBedsTBL (extraBedsID, propID, description) " +
	          	"values ('"+room_id+"','"+property_id+"','0')";
	      		sentencia.execute(SQL);
				
	      		// regenerate all-rooms stop sales
				java.util.Vector data_stop_sales = new java.util.Vector();
				Lists list_stop_sales = new Lists();
				java.util.Enumeration enum_data_stop_sales;
				StopSale stopSale = new StopSale();
				
				data_stop_sales = list_stop_sales.getStopSalesProperty(property_id);
				for (enum_data_stop_sales = data_stop_sales.elements(); enum_data_stop_sales.hasMoreElements();) {
					stopSale = (xcaping.StopSale)enum_data_stop_sales.nextElement();
					if (stopSale.room.equals("0")) {
						stopSale.delete(stopSale.id);
						stopSale.insert(property_id);
					}
				}
	      		
				java.util.Vector data_contracts = new java.util.Vector();
				Lists list_contracts = new Lists();
				java.util.Enumeration enum_data_contracts;
				
				java.util.Vector data_periods = new java.util.Vector();
				Lists list_periods = new Lists();
				java.util.Enumeration enum_data_periods;
				
				Contract contract = new Contract();
				Period period = new Period();
				data_contracts = list_contracts.getContracts(property_id);
				for (enum_data_contracts = data_contracts.elements() ; enum_data_contracts.hasMoreElements() ;) {
					contract = (xcaping.Contract)enum_data_contracts.nextElement();
					
					Room room = new Room();
					contract.deactivateRoom(room.getRoomIdFromExtra(room_id), contract.id);
					
					//if (contract.state.equals("not_generated")){
						data_periods = list_periods.getPeriods(contract.id);
						for (enum_data_periods = data_periods.elements() ; enum_data_periods.hasMoreElements() ;) {
							period = (xcaping.Period)enum_data_periods.nextElement();
							period.addNewRooms(property_id);
						}
					//}
				}
			}			
      	} catch (Exception ex) {
      		valid = true;
      		this.error = ex.toString();
      	}
      	disconnect();
      	return (valid);
	}
	
	private boolean existsRoomExtra(String room_id, String property_id) {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+this.tables_modifyer+"[propBedsTBL] " +
			"where extraBedsID='"+room_id+"' and propID="+property_id;
          	ResultSet result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	public void subRoomExtra (String room_id, String property_id) {
		connect();
		try {      		
      		java.util.Vector data_contracts = new java.util.Vector();
			Lists list_contracts = new Lists();
			java.util.Enumeration enum_data_contracts;
			
			java.util.Vector data_periods = new java.util.Vector();
			Lists list_periods = new Lists();
			java.util.Enumeration enum_data_periods;
			
			Contract contract = new Contract();
			Period period = new Period();
			data_contracts = list_contracts.getContracts(property_id);
			for (enum_data_contracts = data_contracts.elements() ; enum_data_contracts.hasMoreElements() ;) {
				contract = (xcaping.Contract)enum_data_contracts.nextElement();
				
				// all the prices for this room are deleted, even on the generated and finished contracts
				data_periods = list_periods.getPeriods(contract.id);
				for (enum_data_periods = data_periods.elements() ; enum_data_periods.hasMoreElements() ;) {
					period = (xcaping.Period)enum_data_periods.nextElement();
					period.subNewRooms(property_id);
				}
			}			
			Converter converter = new Converter();
			Room room = new Room();
			room.id = room.getRoomIdFromProperty(room_id);
      		converter.deleteRoomLines(property_id, room.id);
			
			// delete the room from the property
			Statement sentencia = conexion.createStatement();
          	SQL = "delete from "+this.tables_modifyer+"propBedsTBL where propBedsID="+room_id;
      		sentencia.execute(SQL);
      		
      		// delete the not active marks
      		SQL = "select * from "+this.tables_modifyer+"not_active_room_contract " +
      			"join contract on contract.contract_id = not_active_room_contract.contract_narc " +
      			"join Property on Property.propertyID = contract.property_contract " +
      			"where Property.propertyID='"+property_id+"' and room_narc='"+room.id+"'";
      		ResultSet result = sentencia.executeQuery(SQL);
      		
      		boolean more = result.next();
          	Statement sentencia2 = conexion.createStatement();
      		while (more) {
      			SQL = "delete from "+this.tables_modifyer+"[not_active_room_contract] " +
    				"where room_narc = '"+result.getString("room_narc")+"' " +
    				"and contract_narc = '"+result.getString("contract_narc")+"'";
          		sentencia2.execute(SQL);
          		more = result.next();
      		}
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean modify(String id) {
		connect();
		boolean valid = true;
		if (validate()) {
				try {
					SQL = "update Property set propertyName='"+this.name
					+"', ratingID='"+this.rating+"' "
					+"where propertyID='"+id+"'";
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
	
	private void addDefaultPhotoDescription() {
		Photo photo = new Photo();
		PhotoItem photo_item = new PhotoItem();
		photo_item.photo = photo.defaultPhotoID();
		photo_item.item = this.id;
		photo_item.heading = photo.default_heading_id;
		photo_item.description = "";
		photo_item.order = photo.default_order_id;
		photo_item.insert("5");
	}
	
	public boolean hasContracts() {
		boolean has = false;
		try {
			java.util.Vector data = new java.util.Vector();
			Lists list = new Lists();
			java.util.Enumeration e;
			
			data = list.getContracts(this.id);
			e = data.elements();
			if (e.hasMoreElements()) {
				has = true;
			}
      	} catch (Exception ex) {
      	}
      	return (has);
	}
	
	private boolean hasPhotos() {
		boolean has = false;
		try {
			java.util.Vector data = new java.util.Vector();
			Lists list = new Lists();
			java.util.Enumeration e;
			
			data = list.getPhotos(this.id, "5");
			e = data.elements();
			if (e.hasMoreElements()) {
				has = true;
			}
      	} catch (Exception ex) {
      	}
      	return (has);
	}
	
	private boolean hasDescriptions() {
		boolean has = false;
		/*try {
			java.util.Vector data = new java.util.Vector();
			Lists list = new Lists();
			java.util.Enumeration e;
			
			data = list.getDescriptions(this.id);
			e = data.elements();
			if (e.hasMoreElements()) {
				has = true;
			}
      	} catch (Exception ex) {
      	}*/
      	return (has);
	}
	
	/*private void deleteContracts() {
	 try {

		java.util.Vector data = new java.util.Vector();
		Lists list = new Lists();
		java.util.Enumeration enum_data;
		
		Contract contract = new Contract();
		data = list.getContracts(this.id);
		for (enum_data = data.elements() ; enum_data.hasMoreElements() ;) {
			contract = (this.Contract)enum_data.nextElement();
			contract.delete(contract.id);
		}
  	} catch (Exception ex) {
  	}
  	}*/
	
	public void switchFeatured(String item_id, String resort, String featured) {
		connect();
		try {
			SQL = "update "+this.tables_modifyer+"[Property] ";
				if (resort.equals("1")) {
					SQL = SQL + "set FeaturedResort='"+featured+"' ";
				} else {
					SQL = SQL + "set FeaturedArea='"+featured+"' ";
				}				
				SQL = SQL + "where propertyID='"+item_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	
	/** changes the property state to the value of the parameter "value"
	 * @param property_id
	 * @param value
	 */
	public void setActive(String property_id, String value) {
		connect();
		try {
			SQL = "update "+this.tables_modifyer+"[Property] set active='"+value+"' "+
				"where propertyID='"+property_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
}

