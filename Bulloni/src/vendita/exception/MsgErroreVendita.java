package vendita.exception;

/**
 * @author GiannettaGerardo
 *
 * Classe contenete i messaggi di errore da utilizzare nel sollevamento
 * dell'eccezione VenditaException
 */
public class MsgErroreVendita {
	
	private static final String CREAZIONE_VENDITA = "VenditaException sollevata:\n";
	
	private static final String CREAZIONE_VENDITA_BULLONI = "Classe VenditaBulloni\n";
	
	public static final String DATA_NON_REALE = "La data di vendita non corrisponde ad una data reale.";
	
	public static final String MERCE_VENDUTA_NULLA = "";
	
	public static final String RESPONSABILE_VENDITA_NULLO = "";

	/**
	 * Costruttore privato
	 */
	private MsgErroreVendita() {
		// TODO Auto-generated constructor stub
	}

}
