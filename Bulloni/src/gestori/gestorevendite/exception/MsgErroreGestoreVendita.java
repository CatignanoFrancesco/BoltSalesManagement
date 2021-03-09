package gestori.gestorevendite.exception;

/**
 * @author GiannettaGerardo
 *
 * Classe contenete i messaggi di errore da utilizzare nel sollevamento
 * dell'eccezione GestoreVenditaException
 */
public class MsgErroreGestoreVendita {

	/** intestazione di ogni messaggio d'errore per la classe GestoreVenditaException */
	public static final String INTESTAZIONE = "GestoreVenditaException sollevata:\n";
	
	/** indica che il l'HashMap ritornato dal metodo selectMerceVenduta è vuoto */
	public static final String MERCE_VENDUTA_NULLA = "Il Database ha ritornato un insieme vuoto di merce venduta.";
	
	/** indica che la vendita cercata tramite codice non esiste nel Set interno della classe */
	public static final String VENDITA_NON_TROVATA = "Il codice vendita è inesistente.";
	
	/** indica che le vendite cercate tramite data non esistono nel Set interno della classe */
	public static final String VENDITE_NON_TROVATE_DATA = "Nessuna vendita è stata effettuata in questa data.";
	
	/** indica che le vendite cercate tramite codice impiegato non esistono nel Set interno della classe */
	public static final String VENDITE_NON_TROVATE_IMPIEGATO = "Nessuna vendita corrisponde all'impiegato cercato.";
	
	
	/**
	 * il costruttore di MsgErroreGestoreVendita deve essere privato
	 */
	private MsgErroreGestoreVendita() {}

}
