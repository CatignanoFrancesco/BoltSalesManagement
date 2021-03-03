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

	public static final String STIPENDIO_PROMOZIONE_NON_VALIDO = "nuovo stipendio minore uguale dello stipendio attuale";

	public static final String GIORNATE_PROMOZIONE_NON_VALIDO = "nuovo giornate lavorative minori delle attuali";

	public static final String STIPENDIO_NON_VALIDO = "stipendio non valido";

	public static final String GIORNATE_NON_VALIDE = "giornate di lavoro non valide";

	public static final String IMPIEGATO_LICENZIATO = "impossibile modificare valori attributi poiche l'impiegato risulta licenziato";

	public static final String IMPIEGATO_GIA_LICENZIATO = "non puoi licenziare un impiegato gia licenziato";

	public static final String IMPIEGATO_GIA_ASSUNTO = "non puoi assumere un impiegato gia assunto";

}
