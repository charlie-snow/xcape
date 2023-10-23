package xcaping;

import java.sql.*;

public class DescriptionHeading extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert the heading";
	
	public String id;
	public String heading;
	public String order;
	
	private String item_type;
	
	public String returnURL = "";
	public String errorURL = "";
	
	private Data data = new Data();
	
	public DescriptionHeading(String item_type) {
		data.getNames(item_type);
		this.item_type = item_type;
		this.getURLs(this.item_type);
	}
	
	public boolean validate() {
		Validation validation = new Validation();
		return (validation.validate(this));
	}
	
	private boolean exists(String item_type) {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+data.name+data.headings_table_name+" " +
			"where "+data.name+"Heading='"+this.heading+"'";
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
				try {
					SQL = "select max("+data.name+"OrderBy) as 'max' from "+data.name+data.headings_table_name;
		          	Statement sentencia = conexion.createStatement();
		      		result = sentencia.executeQuery(SQL);
		      		result.next();
		      		String max = result.getString("max");
		      		if (max == null) {
		      			max = "0";
		      		}
					
					sentencia = conexion.createStatement();
		          	SQL = "insert into "+data.name+data.headings_table_name+" " +
		          	"("+data.name+"Heading, "+data.name+"OrderBy) " +
		          	"values ('"+this.heading+"','"+(Integer.parseInt(max)+1)+"')";
		      		sentencia.execute(SQL);
				} catch (Exception ex) {
					this.error = ex.toString();
		      		valid = false;
		      	}
			} else {
				this.error = "error: check if you are using legal characters for the name";
				valid = false;
			}
		} else {
			this.error = "error: this heading already exists";
			valid = false;
		}
		disconnect();
		return(valid);
	}
	
	public void get(String heading_id) {
		connect();
		String name = "";
		try {
			SQL = "select * from "+data.name+data.headings_table_name+" " +
			"where "+data.name+"HeadingID='"+heading_id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString(data.name+"HeadingID");
      		this.heading = result.getString(data.name+"Heading");
      		this.order = result.getString(data.name+"OrderBy");
      		
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean delete(String heading_id) {
		boolean valid = true;
		connect();
		if (this.isEmpty(heading_id)) {
			try {
				SQL = "delete from "+data.name+data.headings_table_name+" " +
				"where "+data.name+"HeadingID='"+heading_id+"'";
				Statement sentencia = conexion.createStatement();
				sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      	}		
		} else {
			valid = false;
			this.error = "this heading can't be deleted: it is in use";
		}
      	disconnect();
      	return (valid);
	}
	
	private boolean isEmpty(String id) {
		boolean empty = true;
		try {
			Lists list = new Lists();
			java.util.Enumeration e;
			
			Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+data.name+"DescriptionTBL " +
			"where "+data.name+"HeadingID='"+id+"'";
          	result = sentencia.executeQuery(SQL);
      		empty = !result.next();
      	} catch (Exception ex) {
      	}
      	return (empty);
	}
	
	public boolean modify(String heading_id) {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
				SQL = "update "+data.name+data.headings_table_name+" set "
				+data.name+"Heading='"+this.heading
				+"', "+data.name+"OrderBy='"+this.order+"' "
				+"where "+data.name+"HeadingID='"+heading_id+"'";
	          	Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      		this.error = ex.toString();
	      		valid = false;
	      	}
		} else {
			this.error = "error: check if you are using legal characters for the name";
			valid = false;
		}
		disconnect();
		return(valid);
	}
	
	public boolean order(String heading_id, String up) {
		this.get(heading_id);
		connect();
		boolean valid = true;
		int new_order;
		if (up.equals("1")) {
			new_order = Integer.parseInt(this.order) + 1;
		} else {
			new_order = Integer.parseInt(this.order) - 1;
		}
		try {
			Statement sentencia = conexion.createStatement();
          	SQL = "select * from "+data.name+data.headings_table_name+" " +
			"where "+data.name+"OrderBy='"+new_order+"'";
          	result = sentencia.executeQuery(SQL);
      		boolean exists = result.next();
      		
      		if (exists) {
      			String id = result.getString(data.name+"HeadingID");
      			SQL = "update "+data.name+data.headings_table_name+" set "
				+data.name+"OrderBy='"+this.order+"' "
				+"where "+data.name+"HeadingID='"+id+"'";
	          	sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
      		}
      		
      		SQL = "update "+data.name+data.headings_table_name+" set "
			+data.name+"OrderBy='"+new_order+"' "
			+"where "+data.name+"HeadingID='"+heading_id+"'";
          	sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
			
      	} catch (Exception ex) {
      		this.error = ex.toString();
      		valid = false;
      	}
		disconnect();
		return(valid);
	}
	
	private void getURLs (String item_type) {
		this.returnURL = "index.jsp?content=descriptionHeadingsList&item_type="+item_type;
		this.errorURL = "index.jsp?content=error&error=";
	}
}
