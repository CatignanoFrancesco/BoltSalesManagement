package databaseSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


/**
 * @author GiannettaGerardo
 * Classe statica che mi permette di creare e restituire una connessione di tipo 
 * Connection ad un database e mi permette di chiudere la connessione creata
 */
public class Connessione {
	
	private static final String DB_NOME = "Bulloni";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "pass";
	private static final String HOST = "localhost";
	private static final int PORTA = 3306;
	private static final String TIMEZONE = "?serverTimezone=UTC";
	private static final String URL = "jdbc:mysql://" + HOST + ":" + PORTA;
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static Connection connection;
	
	/**
	 * Il costruttore di Connessione rimane privato
	 */
	private Connessione() {}
	
	
	/**
	 * Metodo che crea una connessione utilizzando gli attributi costanti della classe
	 * @return la connessione creata in formato Connection
	 */
	private static Connection getConnection() {
		
		/*
		 * tento di stabilire la connessione ad un preciso database, in caso di fallimento e sollevamento di eccezione,
		 * controllo se il problema è dato dal database non esistente o meno. Se così fosse, ritento la connessione senza
		 * connettermi allo specifico database, lo creo e mi riconnetto allo specifico database creato
		 */
		try {
			
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL + "/" + DB_NOME + TIMEZONE, USERNAME, PASSWORD);
			
		}
		catch (SQLException e) {
			
			String errMsg = e.getMessage();
			
			if (errMsg.equals("Unknown database '" + DB_NOME + "'")) {
				
				try {
					// mi riconnetto senza usare un preciso database
					connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					
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
		
		return connection;
	}
	
	
	/**
	 * Metodo che chiude la connessione dell'attributo connection
	 */
	private void closeConnection() {
		
		try {
			connection.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
