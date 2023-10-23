package xcaping;

import java.sql.*;

public class BoardBasisPeriod extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String error = "failed to insert board basis";
	public String name;
	
	public String period_id;
	public String board_basis_id;
	public String price;
	
	public boolean validate() {
		Validation validation = new Validation();
		boolean valid = validation.validate(this);
		if (valid == false) {
			this.error = validation.message;
		}
		return(valid);
	}
	
	public void format() {
		Formatter formatter = new Formatter();
		formatter.format(this);
	}
	
	private boolean exists() {
		connect();
		boolean exists = false;
		try {
          	Statement sentencia = conexion.createStatement();
          	SQL = "select period_bbp from "+this.tables_modifyer+"[board_basis_period] where period_bbp='"+this.period_id+"' " +
          			"and board_basis_bbp='"+this.board_basis_id+"'";
          	result = sentencia.executeQuery(SQL);
      		exists = result.next();
      	} catch (Exception ex) {
      	}
      	connect();
      	return (exists);
	}
	
	private boolean insert() {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
	          	Statement sentencia = conexion.createStatement();
	          	SQL = this.SQLprefix+"insert into "+this.tables_modifyer+"[board_basis_period] values ('"+this.period_id+
					"','"+this.board_basis_id+"', "+this.price+")";
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      		valid = false;
	      	}
		} else {
			valid = false;
		}
		disconnect();
		return (valid);
	}
	
	public void set() {
		
		if (!exists()) {
			this.insert();
		} else {
			this.modify();
		}
	}
	
	public void get(String period_id, String board_basis_id) {
		connect();
		try {
			SQL = "select * from "+this.tables_modifyer+"[board_basis_period] where " +
				"period_bbp='"+period_id+"' and " +
				"board_basis_bbp='"+board_basis_id+"'";
          	Statement sentencia = conexion.createStatement();
      		result = sentencia.executeQuery(SQL);
      		result.next();
      		BoardBasis board_basis = new BoardBasis();
      		board_basis.get(board_basis_id);
      		this.name = board_basis.name;
      		this.period_id = result.getString("period_bbp");
    		this.board_basis_id = result.getString("board_basis_bbp");
    		this.price = result.getString("price_bbp");
    		
    		this.format();
    		
      	} catch (Exception ex) {
      	}  
      	disconnect();
	}
	
	public void delete(String id) {
		connect();
		try {
			SQL = "delete from "+this.tables_modifyer+"[board_basis_period] " +
			"where period_bbp='"+this.period_id+"' and board_basis_bbp='"+this.board_basis_id+"'";
          	Statement sentencia = conexion.createStatement();
      		sentencia.execute(SQL);
      	} catch (Exception ex) {
      	}
      	disconnect();
	}
	
	public boolean modify() {
		connect();
		boolean valid = true;
		if (validate()) {
			try {
				// old system: updating the value of this board basis
				/*String basis_name = "";
				switch (Integer.parseInt(this.board_basis_id)) {
	    			case 1:  basis_name = "selfCatering"; break;
	    			case 2:  basis_name = "roomOnly"; break;
	    			case 3:  basis_name = "bedBreakfast"; break;
	    			case 4:  basis_name = "halfBoard"; break;
	    			case 5:  basis_name = "fullBoard"; break;
	    			case 6:  basis_name = "allInclusive"; break;
				}
				Period period = new Period();
				period.get(this.period_id);
				Contract contract = new Contract();
				contract.get(period.contract);
				Validation validation = new Validation();
				String from_date = validation.getSQLDate(period.from_date);
				String to_date = validation.getSQLDate(period.to_date);
      			
				SQL = this.SQLprefix+"update "+this.tables_modifyer+"[PriceTBL] " +
				"set "+basis_name+"='"+this.price+"' "+
				"where fromDate='"+from_date+"' " +
				"AND toDate='"+to_date+"' AND propID='"+contract.property+"'";
	          	Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);*/
				
				// xcaping
				SQL = this.SQLprefix+"update "+this.tables_modifyer+"[board_basis_period] " +
				"set price_bbp='"+this.price+"' "+
				"where period_bbp='"+this.period_id+"' and board_basis_bbp='"+this.board_basis_id+"'";
				Statement sentencia = conexion.createStatement();
	      		sentencia.execute(SQL);
	      	} catch (Exception ex) {
	      		valid = false;
	      	}
		} else {
			valid = false;
		}
		disconnect();
		return (valid);
	}

}

