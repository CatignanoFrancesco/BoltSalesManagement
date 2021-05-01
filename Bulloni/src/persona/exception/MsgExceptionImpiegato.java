package persona.exception;

/**
 * @author Francolino Flavio Domenico
 * 
 *         questa classe contiene tutti i possibili messaggi usabili da classi
 *         che sollevano eccezione ti tipo personalizzato rigurardanti valori
 *         non validi per attributi di Impiegato
 * 
 */

public class MsgExceptionImpiegato {

	public static final String STIPENDIO_PROMOZIONE_NON_VALIDO = "Sollevata ExceptionImpiegato:\n stipendio assegnato al dipendente minore uguale dello stipendio attuale";

	public static final String GIORNATE_PROMOZIONE_NON_VALIDO = "Sollevata ExceptionImpiegato:\n nuovo giornate assegnate al dipendente lavorative minori delle attuali";

	public static final String STIPENDIO_NON_VALIDO = "Sollevata ExceptionImpiegato:\n stipendio assegnato al dipendente non valido";

	public static final String GIORNATE_NON_VALIDE = "Sollevata ExceptionImpiegato:\n giornate di lavoro assegnate al dipendente non valide";

	public static final String IMPIEGATO_LICENZIATO = "Sollevata ExceptionImpiegato:\n impossibile modificare valori attributi di questo dipendente poiche l'impiegato risulta licenziato";

	public static final String IMPIEGATO_GIA_LICENZIATO = "Sollevata ExceptionImpiegato:\n non puoi licenziare un impiegato gia licenziato";
	
	public static final String ECCESSO_BULLONI_ASSEGNATI = "Sollevata ExceptionImpiegato:\n troppi bulloni da vendere in confronto alle giornate assegnate al medesimo dipendente";
	
	public static final String POCHI_BULLONI_ASSEGNATI = "Sollevata ExceptionImpiegato:\n Ogni impiegato deve vendere almeno 365 bulloni l'anno";
	
	public static final String ID_GIA_ASSEGNATO = "Sollevata ExceptionImpiegato:\n questo impiegato ha gia un id";
	
	public static final String ID_NON_VALIDO = "Sollevata ExceptionImpiegato:\n id non valido";
}
