package databaseSQL.exception;


/**
 * @author GiannettaGerardo
 *
 * Classe contenete i messaggi di errore da utilizzare nel sollevamento
 * dell'eccezione DatabaseSQLException
 */
public class MsgErrore {
	
	/** intestazione di ogni messaggio d'errore per la classe DatabaseSQL */
	private static final String REGEX_NON_RISPETTATA = "DatabaseSQLException sollevata:\nla query inserita non è riconducibile ad un comando SQL ";
	
	/** intestazione di ogni messaggio d'errore per la classe Connessione */
	private static final String ERRORE_CONNESSIONE = "DatabaseSQLException sollevata:\n";
	
	/** insieme alla sua intestazione, indica che la query inserita in input sottoforma di stringa, non era una select */
	public static final String ERRORE_REGEX_SELECT = REGEX_NON_RISPETTATA + "SELECT.";
	
	/** insieme alla sua intestazione, indica che la query inserita in input sottoforma di stringa, non era una insert */
	public static final String ERRORE_REGEX_INSERT = REGEX_NON_RISPETTATA + "INSERT.";
	
	/** insieme alla sua intestazione, indica che la query inserita in input sottoforma di stringa, non era un update */
	public static final String ERRORE_REGEX_UPDATE = REGEX_NON_RISPETTATA + "UPDATE.";

	/** insieme alla sua intestazione, indica che la query inserita in input sottoforma di stringa, non era una delete */
	public static final String ERRORE_REGEX_DELETE = REGEX_NON_RISPETTATA + "DELETE.";
	
	/** insieme alla sua intestazione, indica che la connessione che si tenta di chiudere non esiste ancora come oggetto */
	public static final String ERRORE_CHIUSURA_CONN_NULLA = ERRORE_CONNESSIONE + "La connessione che si tenta di chiudere non esiste ancora.";
	
	/** insieme alla sua intestazione, indica che la connessione che si tenta di chiudere non è stata aperta prima */
	public static final String ERRORE_CHIUSURA_CONN_CHIUSA = ERRORE_CONNESSIONE + "La connessione che si tenta di chiudere non è ancora stata aperta.";
	
	/** insieme alla sua intestazione, indica che la connessione che si tenta di aprire è già aperta */
	public static final String ERRORE_APERTURA_CONN_APERTA = ERRORE_CONNESSIONE + "La connessione che si tenta di aprire è già aperta.";
	

	/**
	 * il costruttore di MsgErrore deve essere privato
	 */
	private MsgErrore() {}

}
