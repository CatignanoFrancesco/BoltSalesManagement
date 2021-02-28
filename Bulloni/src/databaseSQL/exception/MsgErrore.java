package databaseSQL.exception;


/**
 * @author GiannettaGerardo
 *
 * Classe contenete i messaggi di errore da utilizzare nel sollevamento
 * dell'eccezione DatabaseSQLException
 */
public class MsgErrore {
	
	/** intestazione di ogni messaggio d'errore */
	private static final String REGEX_NON_RISPETTATA = "DatabaseSQLException sollevata:\nla query inserita non è riconducibile ad un comando SQL ";
	
	/** insieme all'intestazione, indica che la query inserita in input sottoforma di stringa, non era una select */
	public static final String ERRORE_REGEX_SELECT = REGEX_NON_RISPETTATA + "SELECT";
	
	/** insieme all'intestazione, indica che la query inserita in input sottoforma di stringa, non era una insert */
	public static final String ERRORE_REGEX_INSERT = REGEX_NON_RISPETTATA + "INSERT";
	
	/** insieme all'intestazione, indica che la query inserita in input sottoforma di stringa, non era un update */
	public static final String ERRORE_REGEX_UPDATE = REGEX_NON_RISPETTATA + "UPDATE";

	/** insieme all'intestazione, indica che la query inserita in input sottoforma di stringa, non era una delete */
	public static final String ERRORE_REGEX_DELETE = REGEX_NON_RISPETTATA + "DELETE";
	

	/**
	 * il costruttore di MsgErrore deve essere privato
	 */
	private MsgErrore() {}

}
