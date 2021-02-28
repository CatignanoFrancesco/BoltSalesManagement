package databaseSQL.exception;

/**
 * @author GiannettaGerardo
 * 
 * Classe che rappresenta l'eccezione che potrebbe sollevarsi nella classe DatabaseSQL
 */
public class DatabaseSQLException extends Exception {

	/**
	 * costruttore senza parametri
	 */
	public DatabaseSQLException() {
		super();
	}
	
	/**
	 * costruttore con parametri
	 * 
	 * @param msg messaggio di errore
	 * @param cause
	 */
	public DatabaseSQLException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
