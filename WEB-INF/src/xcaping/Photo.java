package xcaping;

import java.sql.*;
import java.io.File;
import java.util.Vector;

public class Photo extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "photo error";
	
	public String id;
	public String file;
	
	public String default_heading_id = "2";	// coming soon
	public String default_description = "coming soon";
	public String default_order_id = "3"; 		// 1st
	
	private boolean exists() {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select * from PhotosTBL " +
			"where PhotosFiles='"+this.file+"'";
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	return (exists);
	}
	
	public void insert() {
		connect();
		String property_id = "";
		if (!this.exists()) {
			try {
	          	Statement sentencia = conexion.createStatement();
	          	SQL = "insert into PhotosTBL (PhotosFiles) values ('"+this.file+"')";
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      	}
		}		
		disconnect();
	}
	
	public void get(String photo_id) {
		connect();
		try {
			SQL = "select * from PhotosTBL " +
			"where PhotosID='"+photo_id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("PhotosID");
      		this.file = result.getString("PhotosFiles");
      		
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String id) {
		connect();
		try {
			SQL = "delete from PhotosTBL where PhotosID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean activatePhotos() {
		boolean valid = true;
		Lists list = new Lists();
		Vector photos = new Vector();
		java.util.Enumeration e;
		Photo photo = new Photo();
		photos = list.getPhotosToActivate();
		File file;
		for (e = photos.elements() ; e.hasMoreElements() ;) {
			photo = (Photo)e.nextElement();
			this.file = photo.file;
			this.insert();
			file = new File(this.default_photos_path+"activate/"+photo.file);
			file.delete();
		}
		return (valid);
	}
	
	public String defaultPhotoID() {
		connect();
		String photo_id = "";
		try {
			SQL = "select * from PhotosTBL where PhotosFiles='"+this.default_photo+"'";
          	Statement sentencia = conexion.createStatement();
          	result = sentencia.executeQuery(SQL);
          	if (result.next()) {
          		photo_id = result.getString("PhotosID");
          	}
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (photo_id);
	}
}
