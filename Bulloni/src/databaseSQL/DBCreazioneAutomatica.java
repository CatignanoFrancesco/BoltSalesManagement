package databaseSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


/**
 * @author GiannettaGerardo
 * 
 * 
 */
class DBCreazioneAutomatica {

	/**
	 * Il costruttore di DBCreazioneAutomatica rimane privato
	 */
	private DBCreazioneAutomatica() {}
	
	
	public static void eseguiDBCreazioneAutomatica(Connection conn, String url, String user, String pass, String dbName, String timeZone) throws SQLException {
		
		createAndUseDB(conn, url, user, pass, dbName, timeZone);
	}
	
	
	private static void createAndUseDB(Connection conn, String url, String user, String pass, String dbName, String timeZone) throws SQLException {
		
		conn = DriverManager.getConnection(url, user, pass);
		
		PreparedStatement pst = conn.prepareStatement("create database " + dbName);
		pst.execute();
		
		conn.close();
		
		conn = DriverManager.getConnection(url + "/" + dbName + timeZone, user, pass);
	}
}
