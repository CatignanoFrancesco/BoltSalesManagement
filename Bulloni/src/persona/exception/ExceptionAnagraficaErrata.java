/**
 * 
 */
package persona.exception;

/**
 * 
 * eccezzione personalizzate sollevate quando non si rispettano dei
 * valori per gli attributi della classe AbstractPersona
 * 
 *  @author Francolino Flavio domenico
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
	}

}
