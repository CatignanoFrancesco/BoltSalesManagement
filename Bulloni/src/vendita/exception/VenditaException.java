package vendita.exception;

/**
 *
 * Classe che rappresenta l'eccezione che potrebbe sollevarsi nella classe AbstractVendita e nelle sue derivate
 * 
 * @author GiannettaGerardo
 * 
 */
public class VenditaException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore dell'eccezione senza parametri
	 */
	public VenditaException() {}

	/**
	 * Costruttore dell'eccezione con parametri
	 * 
	 * @param message messaggio di errore
	 * @param cause
	 */
	public VenditaException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
