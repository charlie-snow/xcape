package xcaping;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * <div align="center"><strong>item (country, region, area, resort or property)</strong></div>
 * <p>description: contains all the item's data (name), and provides methods to check, create, erase 
 * and modify all type of items. <br> as special features there are methods to generate a total 
 * stop sale for any type of item, and move a property across resorts</p>
 * <p>notes:<br>
 * - item_type makes reference to the category: 
 * <ol type="1">
 * 	<li>country</li>
 * 	<li>region</li>
 * 	<li>area</li>
 * 	<li>resort</li>
 * 	<li>property</li>
 * </ol>
 * - the Class "Property" has more data and methods for the properties
 * </p>
 * @author carlos nieves lameiro
 * @version 1.2
 */
public class Item extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;

	public String error = "failed to insert item";
	
	public String id = "";
	public String father = "";
	public String name = "";
	
	public String item_type = "";
	
	/**
	 * this fields are only used for properties
	 */
	public String active;
	public String rating = "";
	public String address;
	public String city;
	public String post_code;
	public String telephone;
	public String fax;
	public String email;
	public String web;
	public String contact;
	public String featured_resort;
	public String featured_area;
	public String luxury;
	public String business;
	public String design;
	public String budget;
	public String student;
	public String spa;
	public String golf;
	public String character;
	public String rustic;
	public String mid;
	public String club;
	public String family;
	
	public String desc_from;
	public String desc_table;
	public String desc_code;
	
	
	public String itemURL = "";
	public String browseURL = "";
	public String editURL = "";
	public String contractsURL = "";
	public String roomingURL = "";
	public String photosURL = "";
	public String descriptionsURL = "";
	public String brochureURL = "";
	public String availabilitiesURL = "";
	public String pricelinesURL = "";
	
	public String availabilitiesHistoryURL = "";
	public String pricelinesHistoryURL = "";	
	
	public String deleteURL = "";
	public String insertURL ="";
	
	public String errorURL = "";
	
	private Data data = new Data();
	
	public Item(String item_type) {
		data.getNames(item_type);
		this.item_type = item_type;
	}
	
	public boolean validate() {
		Validation validation = new Validation();
		return (validation.validate(this));
	}

	public void get(String item_id) {
		connect();
		try {
			SQL = "select * from "+data.table_name+" " +
					"where "+data.name+"ID="+item_id;
          	Statement sentencia = conexion.createStatement();
      		ResultSet result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString(data.name+"ID");
    		this.name = result.getString(data.name+"Name");
    		if (!this.item_type.equals("1")) {
    			this.father = result.getString(data.father_name+"ID");
    		}
    		if (this.item_type.equals("5")) {
    			this.active = result.getString("active");
    			if (this.active == null) {
    				this.active = "0";
    			}
    			this.rating = result.getString("ratingID");
    			this.address = result.getString("address");
    			this.city = result.getString("city");
    			this.post_code = result.getString("postCode");
    			this.telephone = result.getString("tel");
    			this.fax = result.getString("fax");
    			this.email = result.getString("email");
    			this.web = result.getString("web");
    			this.contact = result.getString("contact");    			
    			this.featured_resort = result.getString("FeaturedResort");
    			this.featured_area = result.getString("FeaturedArea");
    			this.luxury = result.getString("Luxury");
    			this.business = result.getString("Business");
    			this.design = result.getString("Design");
    			this.budget = result.getString("Budget");
    			this.student = result.getString("Student");
    			this.spa = result.getString("Spa");
    			this.golf = result.getString("Golf");
    			this.character = result.getString("With_Character");
    			this.rustic = result.getString("Rustic_Country");
    			this.mid = result.getString("Mid_range");
    			this.club = result.getString("Club");
    			this.family = result.getString("Family");
    		}
    		if (this.item_type.equals("1") || this.item_type.equals("2") || this.item_type.equals("3")) {
    			this.desc_from = result.getString("desc_from");
    			this.desc_table = result.getString("desc_table");
    			this.desc_code = result.getString("desc_code");
          	}
      	} catch (Exception ex) {
      		int i = 0;
      	}
      	this.getURLs(item_id, this.item_type);
      	disconnect();
	}
	
	private boolean exists(String item_type) {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+data.table_name+" " +
			"where "+data.name+"Name='"+this.name+"'";
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	/** this method inserts an item into the system and identifies it as the "son"(sub-item)
	 * of the "father" (item_id_father).<br>
	 * as a new item, it comes with default photo and description.<br>
	 *  
	 * - default photo:
	 * <ol type="a">
	 *	<li>photo: id of "1042-001.jpg" (value of DBConnection.default_photo)
	 * 	<li>heading: "2"  (Photo.default_heading_id) (coming soon)
	 * 	<li>description: "coming soon" (Photo.default_description)
	 * 	<li>order: "3" (Photo.default_order_id)
	 * </ol>
	 *  - default description: 
	 * <ol type="a">
	 * 	<li>heading: "first" (Description.default_heading_id) 
	 * Description.insert() will search for the first id on the item's description headings
	 * 	<li>description: "" (Description.default_description)
	 * </ol>
	 * @param item_id_father the id of the item the new item is coing to belong to
	 * @param item_type_father type (country, region,... ) of the father
	 * @return true if there are no errors on the insert, false in other case
	 */
	public boolean insert(String item_id_father, String item_type_father) {
		connect();
		boolean valid = true;
		String item_type = String.valueOf(Integer.parseInt(item_type_father)+1);
		if (!this.exists(item_type)) {
			if (this.validate()) {
				try {
		          	Statement sentencia = conexion.createStatement();
		          	SQL = "insert into "+data.table_name+" ("+data.father_name+"ID, ";
		          	if (item_type.equals("5")) {
		          		SQL = SQL + "ratingID, active, commissionable, ";
		          		SQL = SQL + "address, city, postCode, tel, fax, email, web, contact, ";	
		          		SQL = SQL + "FeaturedResort, FeaturedArea, Luxury, Business, Design, Budget, Student, " +
		          			"Spa, Golf, With_Character, Rustic_Country, Mid_Range, Club, Family, ";
		          	}
		          	if (this.item_type.equals("1") || this.item_type.equals("2") || this.item_type.equals("3")) {
		          		SQL = SQL + "desc_from, desc_table, desc_code, ";
		          	}
		          	SQL = SQL + data.name+"Name) values ('"+item_id_father+"', ";
		          	if (item_type.equals("5")) {
		          		SQL = SQL + "'"+this.rating+"', '"+this.active+"', '1', ";
		          		SQL = SQL + "'"+this.address+"', '"+this.city+"', '"+this.post_code+"', " +
          					"'"+this.telephone+"', '"+this.fax+"', '"+this.email+"', '"+this.web+"', " +
							"'"+this.contact+"', ";
		          		SQL = SQL + "'"+this.featured_resort+"', '"+this.featured_area+"', '"+this.luxury+"', " +
		          				"'"+this.business+"', '"+this.design+"', '"+this.budget+"', '"+this.student+"', " +
		          				"'"+this.spa+"', '"+this.golf+"', '"+this.character+"', '"+this.rustic+"', " +
		          				"'"+this.mid+"', '"+this.club+"', '"+this.family+"', ";
		          	}
		          	if (this.item_type.equals("1") || this.item_type.equals("2") || this.item_type.equals("3")) {
		          		SQL = SQL + "'"+this.desc_from+"', '"+this.desc_table+"', '"+this.desc_code+"'";
		          		
		          	}
		          	SQL = SQL + "'"+this.name+"')";
		      		sentencia.execute(SQL);
		      		
		      		SQL = "SELECT @@identity AS itemID FROM "+data.table_name;
		          	sentencia = conexion.createStatement();
		      		ResultSet result = sentencia.executeQuery(SQL);
		      		result.next();
		      		this.id = result.getString("itemID");
		      	} catch (Exception ex) {
		      		this.error = ex.toString();
		      		valid = false;
		      	}
		      	this.addDefaultPhotoDescription(item_type);
			} else {
				this.error = "error: check if you are using legal characters for the name";
				valid = false;
			}
		} else {
			this.error = "error: the "+data.name+" name already exists";
			valid = false;
		}
		disconnect();
		this.getURLs(item_id_father, item_type_father);
		return (valid);
	}
	
	private void addDefaultPhotoDescription(String item_type) {
		Photo photo = new Photo();
		PhotoItem photo_item = new PhotoItem();
		photo_item.setNames(item_type);
		photo_item.photo = photo.defaultPhotoID();
		photo_item.item = this.id;
		photo_item.heading = photo.default_heading_id;
		photo_item.description = photo.default_description;
		photo_item.order = photo.default_order_id;
		photo_item.insert(item_type);
		
		Description description = new Description(item_type, this.id);
		description.heading = description.default_heading_id;
		description.description = description.default_description;
		description.insert();
	}
	
	/** deletes the item only if it's empty: it has no sub-items, photos or descriptions
	 * @param item_id
	 * @param item_type
	 * @return true if the item is empty, false if it isn't: in this case, the error string value identifies why
	 */
	public boolean delete(String item_id, String item_type) {
		boolean valid = true;
		this.get(item_id); 
		this.getURLs(this.father, String.valueOf(Integer.parseInt(item_type)-1));
		if (this.isEmpty(item_id, item_type)) {
			connect();
			try {
				this.id = item_id;
				SQL = "delete from "+data.table_name+" where "+data.name+"ID='"+item_id+"'";
	          	Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      	}
	      	disconnect();				
		} else {
			valid = false;
			this.error = "this "+data.name+" can't be deleted: "+this.error;
		}
		return (valid);
	}
	
	private boolean isEmpty(String item_id, String item_type) {
		boolean empty = true;
		try {
			java.util.Vector data = new java.util.Vector();
			Lists list = new Lists();
			java.util.Enumeration e;
			
			data = list.getItems(item_id, item_type);
			e = data.elements();
			if (e.hasMoreElements()) {
				empty = false;
				this.error = "it has items";
			}
			
			data = list.getPhotos(item_id, item_type);
			e = data.elements();
			if (e.hasMoreElements()) {
				empty = false;
				this.error = "it has photos";
			}
			
			data = list.getDescriptions(item_id, item_type);
			e = data.elements();
			if (e.hasMoreElements()) {
				empty = false;
				this.error = "it has descriptions";
			}
			
			if (item_type.equals("5")) {
				data = list.getContracts(item_id);
				e = data.elements();
				if (e.hasMoreElements()) {
					empty = false;
					this.error = "it has contracts";
				}
			}
      	} catch (Exception ex) {
      	}
      	return (empty);
	}
	
	/** changes the item (item_id) data (name) to the values stored on the object "item".<br>
	 *  if the item is a property, it also changes the rating, featured, luxury,... values 
	 * @param item_id 
	 * @return true if there are no errors modifying, false in other case
	 */
	public boolean modify(String item_id, String list) {
		connect();
		boolean valid = true;
		if (this.validate()) {
			try {
				SQL = "update "+data.table_name+" " +
				"set "+data.name+"Name='"+this.name+"'";
				if (item_type.equals("5")) {
	          		SQL = SQL + ", ratingID='"+this.rating+"', active='"+this.active+"', ";
	          		SQL = SQL + "address='"+this.address+"', city='"+this.city+"', " +
          				"postCode='"+this.post_code+"', tel='"+this.telephone+"', fax='"+this.fax+"', " +
						"email='"+this.email+"', web='"+this.web+"', contact='"+this.contact+"', ";
	          		SQL = SQL + "FeaturedResort='"+this.featured_resort+"', FeaturedArea='"+this.featured_area+"', " +
	          			"Luxury='"+this.luxury+"', Business='"+this.business+"', Design='"+this.design+"', " +
	          			"Budget='"+this.budget+"', Student='"+this.student+"', Spa='"+this.spa+"', " +
	          			"Golf='"+this.golf+"', With_Character='"+this.character+"', " +
	          			"Rustic_Country='"+this.rustic+"', Mid_Range='"+this.mid+"', " +
	          			"Club='"+this.club+"', Family='"+this.family+"'";
				}
				if (this.item_type.equals("1") || this.item_type.equals("2") || this.item_type.equals("3")) {
	          		SQL = SQL + ", desc_from='"+this.desc_from+"', desc_table='"+this.desc_table+"', " +
      				"desc_code='"+this.desc_code+"'";
	          	}
				SQL = SQL + " where "+data.name+"ID='"+item_id+"'";
	          	Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      		this.error = ex.toString();
	      		valid = false;
	      	}
	      	this.addDefaultPhotoDescription(item_type);
		} else {
			this.error = "error: check if you are using legal characters for the name";
			valid = false;
		}
		disconnect();
		this.get(item_id);
		if (list.equals("1")) {
			String item_type_father = String.valueOf(Integer.parseInt(this.item_type)-1);
			this.getURLs(this.father, item_type_father);
		} else {
			this.getURLs(this.id, item_type);
		}
		
		
		return(valid);
	}
	
	/** move a property (item_id) from one resort to another (father_id)
	 * @param item_id id of the property
	 * @param father_id id of the new resort
	 * @return true if there are no errors moving, false in other case
	 */
	public boolean move(String item_id, String father_id) {
		connect();
		boolean valid = true;
		if (validate()) {
				try {
					SQL = "update "+data.table_name+" " +
					"set "+data.father_name+"ID='"+father_id+"' where "+data.name+"ID='"+item_id+"'";
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
		this.get(item_id);
		this.getURLs(this.id, this.item_type);
		return(valid);
	}
	
	/** automated method to do total stop sales for an item (even for countries).<br>
	 *  to accomplish this, generates stop sales for each room, for each contract, for each property on the item
	 * @param item_id 
	 * @param stop_sale here comes the dates and the user and date of creation
	 * @return true if there are no errors, false in other case
	 */
	public boolean stopSales (String item_id, String item_type, StopSale stop_sale) {
		
		boolean valid = true;
		Validation validation = new Validation();
		if (validation.validate(stop_sale)){
			if (!validation.pastDate(stop_sale.from_date) && !validation.pastDate(stop_sale.to_date)){
				if (item_type.equals("5")) {
					if (!stop_sale.insert(item_id)) {
						valid = false;
						this.error = stop_sale.error;
					}
				} else {
					
					java.util.Vector items = new java.util.Vector();
					Lists list = new Lists();
					items = list.getPropertiesItem(item_id, item_type);
					
					Item item = new Item(item_type);
					java.util.Enumeration e;
					e = items.elements();
					while (e.hasMoreElements()) {
						item = (Item)e.nextElement();
						if (!stop_sale.insert(item.id)) {
							valid = false;
							this.error = stop_sale.error;
						}
					}
				}
			} else {
				this.error = "wrong dates: you cannot enter days in the past";
				valid = false;
			}
		} else {
			this.error = "error: invalid dates";
			valid = false;
		}
		return (valid);
	}
	
	/*private void stopSalesProperty (String item_id, StopSale stop_sale) {
		
		Contract contract_stopsale = new Contract();
		contract_stopsale.property = item_id;
		contract_stopsale.supplier = "1"; // what supplier???
		StopSale stop_sale_insert = new StopSale();
		contract_stopsale.edited_by = stop_sale.edited_by;
		String from_date = stop_sale.from_date;
		String to_date = stop_sale.to_date;
		Property property = new Property();
		property.get(item_id);
		
		String output;
		java.text.SimpleDateFormat formatter;
		java.util.Date today = new java.util.Date();
		formatter = new java.text.SimpleDateFormat("dd MM yy");
		output = formatter.format(today);
		
		if (!property.hasContracts()) {
			// insert contract-stopsale
			contract_stopsale.from_date = from_date;
			contract_stopsale.to_date = to_date;
			contract_stopsale.insertContract_stopsale();
		} else {
			Period period = new Period();
			Validation validation = new Validation();
			Contract contract = new Contract();
			
			// convert string dates to date objects 
			java.util.Date stop_sale_from_date;
			java.util.Date stop_sale_to_date;
			java.util.Date contract_from_date;
			java.util.Date contract_to_date;
			
			String pattern="yyy-MM-dd";
			java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat(pattern);
			
			stop_sale_from_date = sdf.parse(validation.getSQLDate(stop_sale.from_date), new java.text.ParsePosition(0));
			stop_sale_to_date = sdf.parse(validation.getSQLDate(stop_sale.to_date), new java.text.ParsePosition(0));
			// convert string dates to date objects
			
			Lists list = new Lists();
			java.util.Vector data = new java.util.Vector();
			java.util.Enumeration e;
				
			data = list.getContracts(item_id);
			e = data.elements();			
			contract = (Contract)e.nextElement();
			contract_from_date = sdf.parse(validation.getSQLDate(contract.from_date), new java.text.ParsePosition(0));
			contract_to_date = sdf.parse(validation.getSQLDate(contract.to_date), new java.text.ParsePosition(0));
			
			if (contract_from_date.after(stop_sale_from_date) && contract_from_date.after(stop_sale_to_date)) {
				// if the stop sale is out of the contracts period, a new period must be inserted
				// insertar contrato-stopsale
				contract_stopsale.from_date = from_date;
				contract_stopsale.to_date = to_date;
				contract_stopsale.insertContract_stopsale();
			} else {
				from_date = stop_sale.from_date;
				if (contract_from_date.after(stop_sale_from_date)) {
					// if part of the stop sale is out of the contracts period, a new period must be inserted
					to_date = contract.from_date;
					// insertar contrato-stopsale 
					contract_stopsale.from_date = from_date;
					contract_stopsale.to_date = to_date;
					contract_stopsale.insertContract_stopsale();
					
					from_date = contract.from_date;
				}
				while ((stop_sale_from_date.after(contract_to_date) || stop_sale_from_date.compareTo(contract_to_date) == 0) && e.hasMoreElements()) {
					// searching in what contract does the stop sale start
					contract = (Contract)e.nextElement();
					contract_from_date = sdf.parse(validation.getSQLDate(contract.from_date), new java.text.ParsePosition(0));
					contract_to_date = sdf.parse(validation.getSQLDate(contract.to_date), new java.text.ParsePosition(0));
				}
				
				while (stop_sale_to_date.after(contract_to_date) && e.hasMoreElements()) {
					// move along the contracts list, inserting the stop sales
					to_date = contract.to_date;
					// insertar stop sale en el contrato
					
					stop_sale_insert.from_date = from_date;
					stop_sale_insert.to_date = to_date;
					stop_sale_insert.room = "0";
					stop_sale_insert.edited_by = stop_sale.edited_by;
					stop_sale_insert.edit_date = output;
					stop_sale_insert.insert(contract.id);
					
					contract = (Contract)e.nextElement();
					contract_from_date = sdf.parse(validation.getSQLDate(contract.from_date), new java.text.ParsePosition(0));
					contract_to_date = sdf.parse(validation.getSQLDate(contract.to_date), new java.text.ParsePosition(0));
					
					from_date = contract.from_date;
				}
				
				if (contract_to_date.after(stop_sale_to_date)) {
					// if the last contract is covered partially by the stop sale, insert the stop sale for it
					to_date = stop_sale.to_date;
					// insertar stop sale en el contrato
					
					stop_sale_insert.from_date = from_date;
					stop_sale_insert.to_date = to_date;
					stop_sale_insert.room = "0";
					stop_sale_insert.edited_by = stop_sale.edited_by;
					stop_sale_insert.edit_date = output;
					stop_sale_insert.insert(contract.id);
					
				} else {
					// if the last contract is covered totally by the stop sale, insert the stop sale for it
					to_date = contract.to_date;
					// insertar stop sale en el contrato
					
					stop_sale_insert.from_date = from_date;
					stop_sale_insert.to_date = to_date;
					stop_sale_insert.room = "0";
					stop_sale_insert.edited_by = stop_sale.edited_by;
					stop_sale_insert.edit_date = output;
					stop_sale_insert.insert(contract.id);
					
					if (stop_sale_to_date.after(contract_to_date)) {
						// if the stop sale goes beyond the last contract, insert a contract-stop sale
						from_date = contract.to_date;
						to_date = stop_sale.to_date;
						// insertar contrato-stopsale
						contract_stopsale.from_date = from_date;
						contract_stopsale.to_date = to_date;
						contract_stopsale.insertContract_stopsale();
					}
				}
			}
			
		}
	}*/
	
	/** generate all the urls for the browsing for items/properties
	 * @param item_id
	 * @param item_type
	 */
	public void getURLs (String item_id, String item_type) {
		this.itemURL = "index.jsp?content=item&item_id="+item_id+"&item_type="+item_type;
		this.browseURL = this.itemURL+"&subcontent=itemsList";
		this.editURL = this.itemURL+"&subcontent=formItem&modify=1";
		this.contractsURL = this.itemURL+"&subcontent=contracts";
		this.roomingURL = this.itemURL+"&subcontent=rooming";
		this.photosURL = this.itemURL+"&subcontent=photosItem";
		this.descriptionsURL = this.itemURL+"&subcontent=descriptionsItem";
		this.availabilitiesURL = this.itemURL+"&subcontent=availabilities&history=0";
		this.pricelinesURL = this.itemURL+"&subcontent=pricelines&history=0";
		
		this.availabilitiesHistoryURL = this.itemURL+"&subcontent=availabilitiesProperty&history=1";
		this.pricelinesHistoryURL = this.itemURL+"&subcontent=pricelinesPropertyList&history=1";
		
		this.deleteURL = "deleteItem.jsp?item_id="+item_id+"&item_type="+item_type;
		this.insertURL = this.itemURL+"&subcontent=formItem&modify=0";
		
		this.errorURL = this.itemURL+"&subcontent=error&error=";
		
		data.getBrochures(item_type);
		this.brochureURL = data.brochure+item_id+"&site=513CIX";
	}
}
