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

	public ExceptionAnagraficaErrata() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExceptionAnagraficaErrata(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
