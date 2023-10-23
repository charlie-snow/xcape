package xcaping;

import java.sql.*;

public class DBConnection {

 private final String DRIVER = "net.sourceforge.jtds.jdbc.Driver";
 //private static final String connectionString = "jdbc:jtds://192.168.1.3:1433/xcdb";
 //private static final String connectionString = "jdbc:jtds://80.35.99.18:1433/xcdb";
 private static final String connectionString = "jdbc:jtds://127.0.0.1:1433/xcapeTest";
 private final String user="carlos", passwd="xcape";
 public Connection conexion;
 
 public String tables_modifyer = "[dbo].";

 public String SQLprefix = "set dateformat ymd ";
 
 //public String default_photos_path = "http://www.xcapewithus.com/a2zphotos/";
 
 
 public String default_photos_path = "/home/angeles/test/"; //acces to the photos physical folder (for activation)
 public String default_photos_url = "/xcaping/photos/"; //url to view the photos (in this case a tomcat context pointing to the physical folder)
 
 public String default_photo = "1042-001.jpg";

public DBConnection(){
  try {
  	Driver d = (Driver)Class.forName(DRIVER).newInstance();
  } catch (Exception ex) {  }
}

/*******************************************************************/

public void connect(){
	try {
		conexion = DriverManager.getConnection(DBConnection.connectionString,user,passwd);
  	} catch (Exception ex) {
  	}
}

/*******************************************************************/

public void disconnect() {
	try {
		conexion.close();
  	} catch (Exception ex) {
  	}
}

}
