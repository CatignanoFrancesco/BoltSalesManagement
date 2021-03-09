package gestori.gestorevendite.exception;

/**
 * @author GiannettaGerardo
 * 
 * Classe che rappresenta l'eccezione che potrebbe sollevarsi nella classe GestoreVendita
 */
public class GestoreVenditaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * costruttore senza parametri
	 */
	public GestoreVenditaException() {}
	
	/**
	 * costruttore con parametri
	 * 
	 * @param msg messaggio di errore
	 * @param cause
	 */
	public GestoreVenditaException(String msg, Throwable cause) {
		super(msg, cause);
	}
}