package xcaping;

import java.sql.*;

public class Description extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert the description";
	
	public String id;
	public String item;
	public String heading;
	public String description;
	public String active;
	
	public String item_type;
	
	public String default_heading_id = "first";	// search for the first on the oder
	public String default_description = "";
	
	public String itemURL;
	public String returnURL;
	public String errorURL;
	
	private Data data = new Data();
	
	public Description(String item_type, String item_id) {
		data.getNames(item_type);
		this.item_type = item_type;
		this.item = item_id;
		this.getURLs(item_id, item_type);
	}
	
	public boolean validate() {
		Validation validation = new Validation();
		return (validation.validate(this));
	}
	
	private boolean exists(String item_type) {
		connect();
		boolean exists = false;
		Data data = new Data();
		data.getNames(item_type);
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+data.name+data.descriptions_table_name+" " +
			"where "+data.name+"HeadingID='"+this.heading+"' "+
			"and "+data.name+"TBLid="+this.item;
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	public boolean insert() {
		connect();
		boolean valid = true;
		if (!this.exists(item_type)) {
			if (validate()) {
				if (this.heading.equals(this.default_heading_id)) {
					this.heading = this.getFirstHeadingID();
				}
				try {
					Statement sentencia = conexion.createStatement();
		          	SQL = "insert into "+data.name+data.descriptions_table_name+" " +
		          	"("+data.name+"TBLid, "+data.name+"HeadingID, "+data.name+"Description, "+data.name+"Activate) " +
		          	"values ('"+this.item+"','"+this.heading+"','"+this.description+"', '1')";
		      		sentencia.execute(SQL);
				} catch (Exception ex) {
					this.error = ex.toString();
		      		valid = false;
		      	}
			} else {
				this.error = "error: check if you are using legal characters for the description";
				valid = false;
			}
		} else {
			this.error = "the description for this heading already exists";
			valid = false;
		}
		disconnect();
		return(valid);
	}
	
	private String getFirstHeadingID() {
		String id = "";
		try {
			SQL = "select "+data.name+"HeadingID from "+data.name+data.headings_table_name+" " +
			"where (select min("+data.name+"OrderBy) as 'min' from "
			+data.name+data.headings_table_name+") = "+data.name+"OrderBy";
			Statement sentencia = conexion.createStatement();
	      	result = sentencia.executeQuery(SQL);
	  		result.next();
	  		id = result.getString(data.name+"HeadingID");
		} catch (Exception ex) {
			this.error = ex.toString();
      	}
		return id;
	}
	
	public void get(String description_id) {
		connect();
		try {
			SQL = "select * from "+data.name+data.descriptions_table_name+" " +
			"where "+data.name+"DescriptionID='"+description_id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString(data.name+"DescriptionID");
      		this.item = result.getString(data.name+"TBLid");
      		this.heading = result.getString(data.name+"HeadingID");
      		this.description = result.getString(data.name+"Description");
      		this.active = result.getString(data.name+"Activate");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String description_id) {
		connect();
		try {
			SQL = "delete from "+data.name+data.descriptions_table_name+" " +
			"where "+data.name+"DescriptionID='"+description_id+"'";
          	Statement sentencia = conexion.createStatement();
          	sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean modify(String description_id) {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
				SQL = "update "+data.name+data.descriptions_table_name+" set "
				+data.name+"HeadingID='"+this.heading
				+"', "+data.name+"Description='"+this.description+"' "+
				"where "+data.name+"DescriptionID='"+description_id+"'";
	          	Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      		this.error = ex.toString();
	      		valid = false;
	      	}
		} else {
			this.error = "error: check if you are using legal characters for the description";
			valid = false;
		}
		disconnect();
		return(valid);
	}
	
	public void getURLs (String item_id, String item_type) {
		this.itemURL = "index.jsp?content=item&item_id="+item_id+"&item_type="+item_type;
		this.returnURL = this.itemURL+"&subcontent=descriptionsItem";
		this.errorURL = this.itemURL+"&subcontent=error&error=";
	}
}
