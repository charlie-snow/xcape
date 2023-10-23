package xcaping;

import java.sql.ResultSet;
import java.sql.Statement;

public class Availability extends DBConnection{
	
	public String SQL = "";
	private String cadena;
	private ResultSet result;
	
	public String idColumn;
	public String fromDate;
	public String toDate;
	public String allocation;
	public String freeSale;
	public String releasePeriod;
	public String minimumStay;
	public String roomNameID;
	public String propID;
	public String supplier;
	public String active;
	public String stopStart;
	public String editDate;
	public String editBy;
	
	public String roomName;
		
	public void delete(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[AvailPropertiesTBL] " +
					"where idColumn='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
}
