package gestori.gestoribulloni.exception;

/**
 * Modella le eccezioni sollevate dai metodi presenti nella classe
 * GestoreBulloni.
 * @author Catignano Francesco
 *
 */
public class GestoreBulloniException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Costruttore senza parametri: l'eccezione sollevata non mostra alcun
	 * parametro.
	 */
	public GestoreBulloniException() {}
	
	/**
	 * Costruttore con i parametri che specificano la causa e il messaggio d'errore.
	 * @param msg Il messaggio d'errore.
	 * @param cause L'eccezione sollevata.
	 */
	public GestoreBulloniException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
