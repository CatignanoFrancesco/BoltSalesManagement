/**
 * 
 */
package gestori.gestoreImpiegati.exception;

/**
 * 
 * eccezzione personalizzate sollevate dal GestoreImpiegati
 * @author Francolino Flavio domenico
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
