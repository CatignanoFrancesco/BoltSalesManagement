package databaseSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import databaseSQL.exception.*;


/**
 * @author GiannettaGerardo
 * 
 * Classe che mi permette di creare e restituire una connessione di tipo 
 * Connection ad un database e mi permette di chiudere la connessione creata
 */
class Connessione {
	
	/** Nome del Database */
	private static final String DB_NOME = "bulloni";
	
	/** Username per connettersi al DBMS */
	private static final String USERNAME = "root";
	
	/** Password per connettersi al DBMS */
	private static String PASSWORD = "pass";
	
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
	protected static Connection connection;
	
	
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
	 * @throws DatabaseSQLException 
	 */
	public static Connection getConnection() throws DatabaseSQLException, SQLException {
		
		/*
		 * se l'oggetto connection è null, questo metodo non avrà alcun problema e non servono ulteriori pre-controlli;
		 * se l'oggetto connection non è null, bisogna controllare prima che la connessione sia stata già chiusa
		 * correttamente prima di essere riaperta, nel caso non fosse così, si solleva un'eccezione
		 */
		if (connection != null) {
			
			if (!connection.isClosed())
				throw new DatabaseSQLException(MsgErrore.ERRORE_APERTURA_CONN_APERTA, new DatabaseSQLException());
			
		}
			
		
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
				
				// crea automaticamente il database e ne riempie 3 tabelle con qualche tupla standard
				DBCreazioneAutomatica.eseguiDBCreazioneAutomatica(URL, USERNAME, PASSWORD, DB_NOME, TIMEZONE);
					
			}
			else
				throw new SQLException("La connessione al database e' fallita.", new SQLException());
			
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		return connection;
	}
	
	
	/**
	 * Metodo che chiude la connessione dell'attributo connection
	 */
	public static void closeConnection() throws SQLException, DatabaseSQLException {
		
		if (connection == null) {
			throw new DatabaseSQLException(MsgErrore.ERRORE_CHIUSURA_CONN_NULLA, new DatabaseSQLException());
		}
		if (connection.isClosed()) {
			throw new DatabaseSQLException(MsgErrore.ERRORE_CHIUSURA_CONN_CHIUSA, new DatabaseSQLException());
		}
		connection.close();
		
	}
	
	
	/**
	 * Metodo che permette di impostare una nuova password per la connessione
	 * 
	 * @param newPassword nuova password
	 */
	public static void setPassword(String newPassword) {
		PASSWORD = newPassword;
	}

}
