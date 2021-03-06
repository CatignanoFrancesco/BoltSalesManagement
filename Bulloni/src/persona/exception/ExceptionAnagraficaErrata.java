/**
 * 
 */
package persona.exception;

/**
 * @author Francolino Flavio Domenico
 * 
 *         eccezzione personalizzate sollevate quando non si rispettano dei
 *         valori per gli attributi della classe AbstractPersona
 *
 */
public class ExceptionAnagraficaErrata extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * costruttore
	 */
	public ExceptionAnagraficaErrata() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * costruttore
	 * 
	 * @param message il messaggio che si vuole assegnare all'eccezione
	 * @param cause il tipo di causa che ha sollevato l'eccezione
	 */
	public ExceptionAnagraficaErrata(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
