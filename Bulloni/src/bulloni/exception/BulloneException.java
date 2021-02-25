package bulloni.exception;


/**
 * Modella le eccezioni sollevate dai metodi presenti in Bullone quando non vengono
 * soddisfatti alcuni requisiti.
 * @author Catignano Francesco
 *
 */
public class BulloneException extends Exception {
	/**
	 * Costruisce un'eccezione senza specificare le informazioni.
	 */
	public BulloneException() {}
	
	/**
	 * Costruisce un'eccezione che quando viene sollevata specifica il messaggio di errore e la causa.
	 * @param msg Il messsaggio di errore.
	 * @param cause L'eccezione sollevata.
	 */
	public BulloneException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
