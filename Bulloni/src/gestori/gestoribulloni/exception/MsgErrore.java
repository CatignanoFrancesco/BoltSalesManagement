package gestori.gestoribulloni.exception;

/**
 * Contiene tutti i messaggi d'errore che riguardano l'eccezione sollevata.
 * 
 * @author Catignano Francesco
 */
public class MsgErrore {
	/**
	 * Messaggio d'errore generico.
	 */
	private static final String GESTORE_BULLONE_EXCEPTION = "GestoreBulloneException sollevata: ";
	
	/**
	 * Messaggio d'errore per indicare che non e' stato ricevuto in input alcun bullone.
	 */
	public static final String BULLONE_NULLO = GESTORE_BULLONE_EXCEPTION + "nessun bullone in input!";
	/**
	 * Messaggio d'errore per indicare che il set di bulloni e' vuoto.
	 */
	public static final String SET_BULLONI_VUOTO = GESTORE_BULLONE_EXCEPTION + "il set di bulloni non ha elementi al suo interno!";
	/**
	 * Messaggio d'errore relativo ad una ricerca fallita di un bullone nel set.
	 */
	public static final String BULLONE_NON_TROVATO = GESTORE_BULLONE_EXCEPTION + "non e' stato trovato alcun bullone!";
	/**
	 * Messaggio d'errore che indica che un bullone che si vuole inserire nel set (e di conseguenza anche in una tabella del databse), e' gia' presente.
	 */
	public static final String BULLONE_ESISTENTE = GESTORE_BULLONE_EXCEPTION + "il bullone che si sta tentando di inserire nel set e' gia' presente!";
	
	/*
	 * Costruttore privato, la classe non e' istanziabile.
	 */
	private MsgErrore() {}
}
