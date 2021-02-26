package databaseSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


/**
 * @author GiannettaGerardo
 *
 */
public class Connessione {
	
	private final String DB_NOME = "Bulloni";
	private final String USERNAME = "root";
	private final String PASSWORD = "pass";
	private final String HOST = "localhost";
	private final int PORTA = 3306;
	private final String TIMEZONE = "?serverTimezone=UTC";
	private final String URL = "jdbc:mysql://" + HOST + ":" + PORTA;
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private Connection connection;
	
	
	private Connessione() {}
	
	/**
	 * Metodo che crea una connessione utilizzando gli attributi costanti della classe
	 * @return la connessione creata in formato Connection
	 */
	private Connection getConnection() {
		
		try {
			
			Class.forName(DRIVER);
			this.connection = DriverManager.getConnection(URL + "/" + DB_NOME + TIMEZONE, USERNAME, PASSWORD);
			
		}
		catch (SQLException e) {
			
			String errMsg = e.getMessage();
			
			if (errMsg.equals("Unknown database '" + DB_NOME + "'")) {
				
				try {
					
					this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					
				}
				catch (SQLException t) {
					System.out.println(t.getMessage());
					t.printStackTrace();
				}
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return this.connection;
	}

}
