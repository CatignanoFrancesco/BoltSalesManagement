package persona.exception;

/**
 * @author Francolino Flavio Domenico
 * 
 *         questa classe conterra tutti i possibili messaggi usabili da classi
 *         che sollevano eccezione ti tipo personalizzato rigurardanti valori
 *         non validi per attributo di Impiegato
 * 
 */

public class MsgExceptionImpiegato {

	public static final String STIPENDIO_PROMOZIONE_NON_VALIDO = "sollevata ExceptionImpiegato:\n stipendio assegnato al dipendente minore uguale dello stipendio attuale";

	public static final String GIORNATE_PROMOZIONE_NON_VALIDO = "sollevata ExceptionImpiegato:\n nuovo giornate assegnate al dipendente lavorative minori delle attuali";

	public static final String STIPENDIO_NON_VALIDO = "sollevata ExceptionImpiegato:\n stipendio assegnato al dipendente non valido";

	public static final String GIORNATE_NON_VALIDE = "sollevata ExceptionImpiegato:\n giornate di lavoro assegnate al dipendente non valide";

	public static final String IMPIEGATO_LICENZIATO = "sollevata ExceptionImpiegato:\n impossibile modificare valori attributi di questo dipendente poiche l'impiegato risulta licenziato";

	public static final String IMPIEGATO_GIA_LICENZIATO = "sollevata ExceptionImpiegato:\n non puoi licenziare un impiegato gia licenziato";

	public static final String IMPIEGATO_GIA_ASSUNTO = "sollevata ExceptionImpiegato:\n non puoi assumere un impiegato gia assunto";
	
	public static final String ECCESSO_BULLONI_ASSEGNATI = "troppo bulloni da vendere in confronto alle giornate assegnate al medesimo dipendente";
	
	public static final String POCHI_BULLONI_ASSEGNATI = "ogni impiegato deve vendere almeno 365 bulloni l'anno";
}
