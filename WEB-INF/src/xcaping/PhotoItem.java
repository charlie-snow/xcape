package xcaping;

import java.sql.*;

public class PhotoItem extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert photo";
		
	public String photo;
	public String item;
	public String heading;
	public String description = "";
	public String order;
	
	private String item_type;
	
	// from the "PhotosTBL"
	public String file;
	
	public String itemURL = "";
	public String returnURL = "";	
	public String errorURL = "";
	
	private Data data = new Data();
	
	public void setNames(String item_type) {
		data.getNames(item_type);
		this.item_type = item_type;
	}
	
	public boolean validate() {
		Validation validation = new Validation();
		return (validation.validate(this));
	}
	
	private boolean exists(String item_type) {
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	String SQL2 = "select * from "+data.name+data.photos_table_name+" " +
			"where PhotosID='"+this.photo+"' and "+data.name+"ID="+this.item;
          	result = sentencia.executeQuery(SQL2);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	public boolean insert(String item_type) {
		connect();
		boolean valid = true;
		if (!this.exists(item_type)) {
			if (validate()) {
				try {
					Statement sentencia = conexion.createStatement();
		          	SQL = "insert into "+data.name+data.photos_table_name+" (PhotosID, "+data.name+"ID, ";
		          		if (item_type.equals("5")) { 
		          			SQL = SQL + "PhotoHeading, ";
		          			this.description = "";
		          		}
		          	SQL = SQL + "PhotoDescription, PhotoOrder) values ('"+this.photo+"','"+this.item+"',";
		          		if (item_type.equals("5")) { SQL = SQL + " '"+this.heading+"',"; }
		          	SQL = SQL + " '"+this.description+"', '"+this.order+"')";
		      		sentencia.execute(SQL);
				} catch (Exception ex) {
					this.error = ex.toString();
		      		valid = false;
		      	}
			} else {
				valid = false;
			}
		}
		disconnect();
		return(valid);
	}
	
	public boolean insertPhotos(String name, String item_id, String item_type) {
		connect();
		boolean valid = true;
		if (validate()) {
			Lists list = new Lists();
			Photo photo = new Photo();
			java.util.Vector data = new java.util.Vector();
			data = list.searchPhotos(name);
			java.util.Enumeration e;
			for (e = data.elements() ; e.hasMoreElements() ;) {
				photo = (Photo)e.nextElement();
				this.item = item_id;
				this.photo = photo.id;
				this.heading = "";
				this.description = "";
				this.order = "";
				this.insert(item_type);
			}
		} else {
			valid = false;
		}
		disconnect();
		return(valid);
	}
	
	public void delete(String photo_id, String item_id) {
		connect();
		try {
			SQL = "delete from "+data.name+data.photos_table_name+" " +
			"where PhotosID='"+photo_id+"' and "+data.name+"ID="+item_id;
          	Statement sentencia = conexion.createStatement();
          	sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void deleteGroup(String photo_group, String item_id) {
		connect();
		try {
			Lists list = new Lists();
			PhotoItem photo_item = new PhotoItem();
			java.util.Vector data = new java.util.Vector();
			data = list.searchPhotos(photo_group, item_id, this.item_type);
			java.util.Enumeration e;
			for (e = data.elements() ; e.hasMoreElements() ;) {
				photo_item = (PhotoItem)e.nextElement();
				this.delete(photo_item.photo, item_id);
			}
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean modify(String item_type) {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
				SQL = "update "+data.name+data.photos_table_name+" set ";
				if (item_type.equals("5")) { 
					SQL = SQL + "PhotoHeading='"+this.heading+"', ";
				}
				SQL = SQL +"PhotoDescription='"+this.description
				+"', PhotoOrder='"+this.order+"' "
				+"where PhotosID='"+this.photo+"' and "+data.name+"ID='"+this.item+"'";
	          	Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      	}
		} else {
			this.error = "error: check if you are using legal characters for the description";
			valid = false;
		}
		disconnect();
		return(valid);
	}
	
	public String getGroup(String photo) {
		String group = "";
		String[] array = new String[3];
		if (photo.indexOf("-") != -1) {
			array = photo.split("-");
			group = array[0];
		}
		return(group);
	}
	
	public void getURLs (String item_id, String item_type) {
		this.itemURL = "index.jsp?content=item&item_id="+item_id+"&item_type="+item_type;
		this.returnURL = this.itemURL+"&subcontent=photosItem";
		this.errorURL = this.itemURL+"&subcontent=error&error=";
	}
}

