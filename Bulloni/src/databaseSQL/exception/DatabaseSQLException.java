package databaseSQL.exception;

/**
 * 
 * Classe che rappresenta l'eccezione che potrebbe sollevarsi nella classe DatabaseSQL
 * 
 * @author GiannettaGerardo
 * 
 */
public class DatabaseSQLException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * costruttore senza parametri
	 */
	public DatabaseSQLException() {}
	
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
