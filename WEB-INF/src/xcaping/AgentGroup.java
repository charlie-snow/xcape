package xcaping;

import java.sql.ResultSet;
import java.sql.Statement;

public class AgentGroup extends DBConnection{

	private String SQL;
	private String cadena;
	
	public String group_id = "";
	public String agent_id = "";
	public String group_name = "";
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[AgentGroupTBL] " +
					"where groupID="+id;
          	Statement sentencia = conexion.createStatement();
      		ResultSet result = sentencia.executeQuery(SQL);
      		result.next();
      		this.group_id = result.getString("groupID");
    		this.agent_id = result.getString("agentID");
    		this.group_name = result.getString("groupName");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	
}