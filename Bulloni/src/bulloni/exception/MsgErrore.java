package bulloni.exception;

/**
 * In questa classe sono contenuti i messaggi d'errore relativi alle eccezioni sollevate.
 * @author Catignano Francesco
 *
 */
public class MsgErrore {
	/**
	 * Messaggio d'errore generico
	 */
	private static final String CREAZIONE_BULLONE = "Creazione del Bullone: ";
	
	public static final String DATA_NON_VALIDA = CREAZIONE_BULLONE + "data non consentita!";
	public static final String PESO_NON_VALIDO = CREAZIONE_BULLONE + "valore relativo al peso non consentito!";
	public static final String PREZZO_NON_VALIDO = CREAZIONE_BULLONE + "valore realativo al prezzo non consentito!";
	public static final String LUNGHEZZA_NON_VALIDA = CREAZIONE_BULLONE + "valore realtivo alla lunghezza del bullone non consentito!";
	public static final String DIAMETRO_VITE_NON_VALIDO = CREAZIONE_BULLONE + "valore relativo al diametro della vite non consentito!";
	
	// La classe non è istanziabile, quindi il costruttore è privato
	private MsgErrore() {}
}
