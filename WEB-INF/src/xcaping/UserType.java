package xcaping;

import java.sql.ResultSet;
import java.sql.Statement;

public class UserType extends DBConnection{

	private String SQL;
	private String cadena;
	
	public String id = "";
	public String name = "";
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[user_type] " +
					"where user_type_id="+id;
          	Statement sentencia = conexion.createStatement();
      		ResultSet result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("user_type_id");
    		this.name = result.getString("name_user_type");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	
}