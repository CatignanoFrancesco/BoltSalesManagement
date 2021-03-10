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
	
	/** indica che il Set di vendite è vuoto, quindi il database ha le tabelle Vendita e MerceVenduta vuote */
	public static final String VENDITE_VUOTE = "Non sono presenti vendite nel database.";
	
	/** indica che nel metodo aggiungiVendita si sta tentando di aggiungere una vendita già esistente */
	public static final String VENDITA_ESISTENTE = "La vendita che si tenta di aggiungere esiste già.";
	
	/** indica che si sta tentando di aggiungere col metodo aggiungiVendita un oggetto null */
	public static final String VENDITA_NULLA = "La vendita che si tenta di aggiungere è nulla.";
	
	/** indica che si sta tendando di aggiungere un codice negativo ad una nuova vendita in aggiungiVendits */
	public static final String CODICE_VENDITA_NEGATIVO = "Il codice vendita assegnato alla nuova vendita è negativo.";
	
	
	/**
	 * il costruttore di MsgErroreGestoreVendita deve essere privato
	 */
	private MsgErroreGestoreVendita() {}

}