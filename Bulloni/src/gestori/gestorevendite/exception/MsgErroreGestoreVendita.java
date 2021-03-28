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
	
	/** indica che la vendita cercata tramite codice non esiste nel Set interno della classe */
	public static final String VENDITA_NON_TROVATA = "Il codice vendita è inesistente.\n";
	
	/** indica che le vendite cercate tramite data non esistono nel Set interno della classe */
	public static final String VENDITE_NON_TROVATE_DATA = "Nessuna vendita è stata effettuata in questa data.\n";
	
	/** indica che le vendite cercate tramite codice impiegato non esistono nel Set interno della classe */
	public static final String VENDITE_NON_TROVATE_IMPIEGATO = "Nessuna vendita corrisponde all'impiegato cercato.\n";
	
	/** indica che il Set di vendite è vuoto, quindi il database ha le tabelle Vendita e MerceVenduta vuote */
	public static final String VENDITE_VUOTE = "Non sono presenti vendite nel database.\n";
	
	/** indica che nel metodo aggiungiVendita si sta tentando di aggiungere una vendita già esistente */
	public static final String VENDITA_ESISTENTE = "La vendita che si tenta di aggiungere esiste già.\n";
	
	/** indica che si sta tentando di aggiungere col metodo aggiungiVendita un oggetto null */
	public static final String VENDITA_NULLA = "La vendita che si tenta di aggiungere è nulla.\n";
	
	/** indica che si sta tendando di aggiungere un codice negativo ad una nuova vendita in aggiungiVendits */
	public static final String CODICE_VENDITA_NEGATIVO = "Il codice vendita assegnato alla nuova vendita è negativo.\n";
	
	/** indica che è stato passato un oggetto nullo al posto del gestore dei bulloni nel costruttore del gestore vendita */
	public static final String GESTORE_BULLONI_NULLO = "Il gestore dei bulloni è un oggetto nullo.\n";
	
	/** indica che è stato passato un oggetto nullo al posto del gestore degli impiegati nel costruttore del gestore vendita */
	public static final String GESTORE_IMPIEGATI_NULLO = "Il gestore degli impiegati è un oggetto nullo.\n";
	
	/** indica che è il numero di bulloni venduti in una certa data/anno da un certo impiegato, sommati ai bulloni che si sta cercando di vendere, supera il massimo consentito */
	public static final String BULLONI_MASSIMI_SUPERATI = "L'impiegato ha superato il numero di bulloni giornaliero e/o annuale massimo consentito. Operazione annullata.\n";
	
	/** indica che il set locale del gestore bulloni è vuoto, quindi il database non ha bulloni salvati */
	public static final String SET_LOCALE_BULLONI_VUOTO = "Il database non ha bulloni salvati.\n";
	
	/** indica che il set locale del gestore impiegati è vuoto, quindi il database non ha impiegati salvati */
	public static final String SET_LOCALE_IMPIEGATI_VUOTO = "Il database non ha impiegati salvati.\n";
	
	
	/**
	 * il costruttore di MsgErroreGestoreVendita deve essere privato
	 */
	private MsgErroreGestoreVendita() {}

}
