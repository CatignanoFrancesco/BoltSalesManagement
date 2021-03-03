package vendita.exception;

/**
 * @author GiannettaGerardo
 *
 * Classe contenete i messaggi di errore da utilizzare nel sollevamento
 * dell'eccezione VenditaException
 */
public class MsgErroreVendita {
	
	/** Intestazione per la creazione di una vendita */
	public static final String CREAZIONE_VENDITA = "VenditaException sollevata:\n";
	
	/** Intestazione per la creazione di una vendita di bulloni */
	public static final String CREAZIONE_VENDITA_BULLONI = "Classe VenditaBulloni\n";
	
	/** Intestazione per la creazione della classe MerceVenduta */
	public static final String CREAZIONE_MERCE_VENDUTA = "Classe MerceVenduta\n";
	
	/** Messaggio di errore nel caso di data non reale, ad esempio una data contenente l'anno 3000 o 1900 */
	public static final String DATA_NON_REALE = "La data di vendita non corrisponde ad una data reale.";
	
	/** Messaggio di errore nel caso il Set passato in input al costruttore, contenente la merce venduta, sia vuoto o nullo */
	public static final String MERCE_VENDUTA_NULLA = "L'insieme contenente la merce venduta è vuoto o nullo.";
	
	/** Messaggio di errore nel caso il responsabile vendita passato in input al costruttore sia nullo */
	public static final String RESPONSABILE_VENDITA_NULLO = "Il responsabile della vendita è nullo.";
	
	/** Messaggio di errore nel caso il bullone inserito in input nel costruttore di MerceVenduta sia nullo */
	public static final String BULLONE_NULLO = "Il bullone è nullo.";

	/**
	 * Costruttore privato
	 */
	private MsgErroreVendita() {
		// TODO Auto-generated constructor stub
	}

}
