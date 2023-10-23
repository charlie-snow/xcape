package xcaping;

import java.sql.*;

public class BoardBasis extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String id;
	public String name;
	
	public void setData(String name) {
		this.name = name;
	}
	
	public void insert() {
		connect();
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[board_basis] values ('','"+this.name+"')";
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void get(String id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[board_basis] where board_basis_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		this.id = result.getString("board_basis_id");
			this.name = result.getString("name_board_basis");
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public void delete(String board_basis_id) {
		connect();
		try {
			deleteAccContract(board_basis_id);
			deleteAccPeriod(board_basis_id);
			SQL = "delete from "+this.tables_modifyer+"[board_basis] where board_basis_id='"
				+board_basis_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	private void deleteAccContract(String board_basis_id) {
		try {			
			SQL = "delete from "+this.tables_modifyer+"[board_basis_contract] where " +
				"board_basis_bb_con='"+board_basis_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}

	private void deleteAccPeriod(String board_basis_id) {
		try {			
			SQL = "delete from "+this.tables_modifyer+"[board_basis_period] where " +
				"board_basis_bp='"+board_basis_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
	}
	
	public void modify(String id) {
		connect();
		try {
			SQL = this.SQLprefix+"update "+this.tables_modifyer+"[board_basis] set name_board_basis='"+this.name+"' "+
				"where board_basis_id='"+id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}

}
