/**
 * 
 */
package gestori.gestoreImpiegati.exception;

/**
 * @author Francolino Flavio Domenico
 * 
 *         eccezzione personalizzate sollevate dal GestoreImpiegati
 *
 */
public class ExceptionGestoreImpiegato extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * costruttore
	 */
	public ExceptionGestoreImpiegato() {
		
	}

	/**
	 * costruttore
	 * 
	 * @param message il messaggio che si vuole assegnare all'eccezione
	 * @param cause   il tipo di causa che ha sollevato l'eccezione
	 */
	public ExceptionGestoreImpiegato(String message, Throwable cause) {
		
		super(message, cause);
	}

}
