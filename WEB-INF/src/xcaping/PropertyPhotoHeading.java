package xcaping;

import java.sql.*;

public class PropertyPhotoHeading extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert heading";
	
	public String id;
	public String heading;
	
	public String returnURL = "index.jsp?content=propertyPhotoHeadingsList";	
	public String errorURL = "index.jsp?content=error&error=";
	
	public boolean validate() {
		Validation validation = new Validation();
		return (validation.validate(this));
	}
	
	private boolean exists() {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from PropertyPhotoHeadingTBL " +
			"where PhotoHeading='"+this.heading+"'";
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
		          	SQL = "insert into PropertyPhotoHeadingTBL ([PhotoHeading])"
		          	+" values ('"+this.heading+"')";
		      		sentencia.execute(SQL);
				} catch (Exception ex) {
		      		valid = false;
		      	}
			}
		} else {
			this.error = "error: check if you are using legal characters for the name";
			valid = false;
		}
		disconnect();
		return(valid);
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from PropertyPhotoHeadingTBL where PhotoHeadingID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("PhotoHeadingID");
			this.heading = result.getString("PhotoHeading");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean delete(String id) {
		boolean valid = true;
		connect();
		if (this.isEmpty(id)) {
			try {
				SQL = "delete from PropertyPhotoHeadingTBL where PhotoHeadingID='"+id+"'";
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
          	SQL = "select * from PropertyPhotoSelectTBL " +
			"where PhotoHeading='"+id+"'";
          	result = sentencia.executeQuery(SQL);
      		empty = !result.next();
      	} catch (Exception ex) {
      	}
      	return (empty);
	}
	
	public boolean modify(String id) {
		connect();
		boolean valid = true;
		if (validate()) {
				try {
					SQL = "update PropertyPhotoHeadingTBL set PhotoHeading='"+this.heading+"' "
					+"where PhotoHeadingID='"+id+"'";
		          	Statement sentencia = conexion.createStatement();
		      		sentencia.execute(SQL);
		      	} catch (Exception ex) {
		      	}
		} else {
			this.error = "error: check if you are using legal characters for the name";
			valid = false;
		}
		disconnect();
		return(valid);
	}
}
