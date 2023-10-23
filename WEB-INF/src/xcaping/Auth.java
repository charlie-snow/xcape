package xcaping;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import sun.misc.BASE64Encoder;

public class Auth extends DBConnection{

	private String SQL;
	private String cadena;
	private ResultSet result;
	
	public String name;
	public String password;
	public User user;
	
	public String error = "error logging";
	
	public String next = "index.jsp?content=welcome";
	
	public boolean validate() {
		Validation validation = new Validation();
		boolean valid = validation.validate(this);
		if (valid == false) {
			this.error = validation.message;
		}
		return(valid);
	}
	
	public boolean getUser() {
		connect();
		boolean valid = true;
		this.user = new User();
		
		if (validate()) {
			try {
				SQL = "select * from "+tables_modifyer+"[user] " +
				"where name_user='"+name+"'";
				Statement sentencia = conexion.createStatement();
				ResultSet result = sentencia.executeQuery(SQL);
				result.next();
				if (result.getString("password").equals(Auth.encrypt(password))) {
					this.user.id = result.getString("user_id");
					this.user.type = result.getString("type_user");
					this.user.name = result.getString("name_user");
				} else {
					this.user.type = "0";
				}
	      	} catch (Exception ex) {
	      		this.error = "auth - "+ex.toString();
	      		valid = false;
	      	}
		} else {
			valid = false;
		}		
		
      	disconnect();
		return (valid);
	}
	
	public static String encrypt(String plaintext) throws IllegalStateException {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA"); // Instancia de generador SHA-1
		}
		catch(NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage());
		}

		try {
			md.update(plaintext.getBytes("UTF-8")); // Generación de resumen de mensaje
		}
		catch(UnsupportedEncodingException e) {
			throw new IllegalStateException(e.getMessage());
		}

		byte raw[] = md.digest(); // Obtención del resumen de mensaje
		String hash = (new BASE64Encoder()).encode(raw); // Traducción a BASE64
		return hash;
	}
	
}
