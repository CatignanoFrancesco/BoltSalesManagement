package databaseSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


/**
 * @author GiannettaGerardo
 * 
 * Classe statica che mi permette di creare e restituire una connessione di tipo 
 * Connection ad un database e mi permette di chiudere la connessione creata
 */
public class Connessione {
	
	/** Nome del Database */
	private static final String DB_NOME = "Bulloni";
	
	/** Username per connettersi al DBMS */
	private static final String USERNAME = "root";
	
	/** Password per connettersi al DBMS */
	private static final String PASSWORD = "pass";
	
	/** Indirizzo del server */
	private static final String HOST = "localhost";
	
	/** Porta del server */
	private static final int PORTA = 3306;
	
	/** Timezone per evitare eventuali errori */
	private static final String TIMEZONE = "?serverTimezone=UTC";
	
	/** URL a cui connettersi */
	private static final String URL = "jdbc:mysql://" + HOST + ":" + PORTA;
	
	/** Driver JDBC */
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	/** Oggetto Connection per effettuare una connessione al database */
	private static Connection connection;
	
	
	/**
	 * Il costruttore di Connessione rimane privato
	 */
	private Connessione() {}
	
	
	/**
	 * Metodo che crea una connessione utilizzando gli attributi costanti della classe.
	 * Se la connessione al server viene effettuata correttamente ma il database al quale
	 * ci si deve connettere non esiste, il database viene creato automaticamente e ne vengono
	 * settate 3 tabelle con circa 3 tuple standard per facilitare i test
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
					// crea automaticamente il database e ne riempie 3 tabelle con qualche tupla standard
					DBCreazioneAutomatica.eseguiDBCreazioneAutomatica(connection, URL, USERNAME, PASSWORD, DB_NOME, TIMEZONE);
					
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
	private static void closeConnection() {
		
		try {
			connection.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
