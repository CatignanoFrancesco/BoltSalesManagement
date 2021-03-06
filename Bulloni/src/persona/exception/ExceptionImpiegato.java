/**
 * 
 */
package persona.exception;

/**
 * @author Francolino Flavio Domenico
 * 
 *         eccezzione personalizzate sollevate quando non si rispettano dei
 *         valori per gli attributi della classe Impiegatogenerale
 *
 */
public class ExceptionImpiegato extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * costruttore
	 */
	public ExceptionImpiegato() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * costruttore
	 * 
	 * @param message il messaggio che si vuole assegnare all'eccezione
	 * @param cause il tipo di causa che ha sollevato l'eccezione
	 */
	public ExceptionImpiegato(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
