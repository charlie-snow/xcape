package xcaping;

import java.sql.*;

public class Room extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String id;
	public String name;
	
	public boolean active;
	
	public void setData(String name) {
		this.name = name;
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "SELECT * FROM RoomNameTBL where roomNameID='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = id;
			this.name = result.getString("roomName");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public String getRoomIdFromExtra(String extra_room_id) {
		String room_id = "";
		connect();
		try {
			SQL = "select distinct RoomNameTBL.roomNameID from RoomNameTBL " +
				"join ExtraBedsTBL on RoomNameTBL.roomNameID=ExtraBedsTBL.roomNameID " +
				"where extraBedsID='"+extra_room_id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
			room_id = result.getString("roomNameID");
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (room_id);
	}
	
	public String getRoomIdFromProperty(String property_room_id) {
		String room_id = "";
		connect();
		try {
			SQL = "select distinct RoomNameTBL.roomNameID from RoomNameTBL " +
				"join ExtraBedsTBL on RoomNameTBL.roomNameID=ExtraBedsTBL.roomNameID " +
				"join PropBedsTBL on ExtraBedsTBL.extraBedsID = PropBedsTBL.extraBedsID " +
				"where propBedsID='"+property_room_id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
			room_id = result.getString("roomNameID");
      	} catch (Exception ex) {
      	}
      	disconnect();
      	return (room_id);
	}
}
