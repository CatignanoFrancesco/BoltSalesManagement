package databaseSQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import databaseSQL.exception.*;

/**
 * 
 * Classe che permette di eseguire su un database le principali operazioni:
 * 
 * SELECT
 * INSERT
 * UPDATE
 * DELETE
 * 
 * Fornisce per esse l'implementazione generale, comune a qualuque query richiesta,
 * ma il fulcro della query rimane il parametro di input di tipo String che sarà passato
 * a tutti i principali metodi; infatti questa classe dovrebbe avvalersi della classe Query,
 * per creare e restituire query sottoforma di stringa, tuttavia le query restituite sono 
 * molto semplici, quindi questa classe implementa anche un controllo su questi parametri
 * di input utilizzando espressioni regolari e sollevando eccezioni quando tali 
 * espressioni regolari sono violate
 * 
 * @author GiannettaGerardo
 * 
 */
public class DatabaseSQL {
	
	/** Questa espressione regolare accetta stringa del tipo:
	 * select * from tabella
	 * select * from tabella1, tabella2
	 * select campo from tabella
	 * select campo1, campo2 from tabella1, tabella2
	 * e altre tipologie di combinazioni; la clausola where è opzionale e quello che viene dopo di essa
	 * non verrà controllato, perché l'uso richiesto del comando potrebbe essere troppo complesso per controllarlo */
	private static final String selectRegex = "(select )(((\\w+, )*\\w+)|[*])( from )(\\w+, )*\\w+( where .+)?";
	
	/** Questa espressione regolare accetta stringa del tipo:
	 * insert into tabella values ('qualcosa')
	 * insert into tabella values ('qualcosa', 'numero') 
	 * insert into tabella values ('qualcosa'), ('qualcosaAncora', 'fine') */
	private static final String insertRegex = "(insert into \\w+ values ([(]('(\\p{ASCII})+', )*'\\p{ASCII}+'[)], )*([(]('(\\p{ASCII})+', )*'\\p{ASCII}+'[)]))";
	
	/** Questa espressione regolare accetta stringa del tipo:
	 * update tabella set campo='numero'
	 * update tabella set campo = 'numero'
	 * Quello che viene dopo il simbolo "=" è molto generico, perché il comando richiesto potrebbe risultare
	 * troppo complesso da analizzare nella sua interezza, stessa cosa vale per la clausola where */
	private static final String updateRegex = "(update \\w+ set \\w+((=)|( = )).+( where .+)?)";
	
	/** Questa espressione regolare accetta stringa del tipo:
	 * delete from tabella where campo='qualcosa'
	 * delete from tabella where campo = 'numero' */
	private static final String deleteRegex = "(delete from \\w+ where \\w+((=)|( = ))'(\\p{ASCII})+')";
	

	/**
	 * Il costruttore di Query rimane privato
	 */
	private DatabaseSQL() {}
	
	
	/**
	 * Metodo che restituisce una connessione utilizzando la classe Connessione.
	 * Sarà utilizzato solo all'interno dei principali metodi di questa classe
	 * 
	 * @return un oggetto Connection già creato e inizializzato
	 * @throws DatabaseSQLException
	 * @throws SQLException
	 */
	private static Connection apriConnessione() throws DatabaseSQLException, SQLException {
		return Connessione.getConnection();
	}
	
	
	/**
	 * Metodo che chiude la connessione aperta dal metodo apriConnessione.
	 * Lavora sullo stesso oggetto Connection di apriConnessione, mediante alias
	 *
	 * @throws SQLException
	 * @throws DatabaseSQLException
	 */
	public static void chiudiConnessione() throws SQLException, DatabaseSQLException {
		Connessione.closeConnection();
	}
	
	
	/**
	 * Metodo che permette di impostare una nuova password per la connessione.
	 * Lavora sullo stesso oggetto Connection di apriConnessione e chiudiConnessione, mediante alias
	 * 
	 * @param newPassword nuova password
	 */
	public static void setPassword(String newPassword) {
		Connessione.setPassword(newPassword);
	}
	
	
	/**
	 * Metodo che permette di eseguire un comando SELECT su di un database SQL.
	 * Controlla prima di tutto che sia stata inserita, come parametro di input, 
	 * una query di tipo SELECT, per quanto possibile; successivamente apre
	 * una connessione tramite il metodo apriConnessione, poi esegue 
	 * la query e ne restituisce il ResultSet; è indispensabile che la connessione
	 * venga chiusa (tramite metodo chiudiConnessione) successivamente dalla 
	 * classe che utilizzerà questo metodo
	 * 
	 * @param query stringa che deve rappresentare il comando SELECT
	 * @return ResultSet contenente il risultato del comando SELECT eseguito
	 * @throws SQLException
	 * @throws DatabaseSQLException
	 */
	public static ResultSet select(String query) throws SQLException, DatabaseSQLException {
		
		/* se la regex non è rispettata dalla query passata in input, solleva un eccezione di tipo 
		 * DatabaseSQLException con un messaggio personalizzato per il tipo di errore specifico */
		Pattern p = Pattern.compile(selectRegex);
		Matcher m = p.matcher(query);
		if (!m.matches()) {
			throw new DatabaseSQLException(MsgErrore.ERRORE_REGEX_SELECT, new DatabaseSQLException());
		}
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		return rs;
	}
	
	
	/**
	 * Metodo che permette di eseguire un comando INSERT su di un database SQL.
	 * Controlla prima di tutto che sia stata inserita, come parametro di input, 
	 * una query di tipo INSERT, per quanto possibile; successivamente apre
	 * una connessione tramite il metodo apriConnessione, poi esegue il comando
	 * e chiude la connessione tramite metodo chiudiConnessione
	 * 
	 * @param query stringa che deve rappresentare il comando INSERT
	 * @throws SQLException
	 * @throws DatabaseSQLException
	 */
	public static void insert(String query) throws SQLException, DatabaseSQLException {
		
		/* se la regex non è rispettata dalla query passata in input, solleva un eccezione di tipo 
		 * DatabaseSQLException con un messaggio personalizzato per il tipo di errore specifico */
		Pattern p = Pattern.compile(insertRegex);
		Matcher m = p.matcher(query);
		if (!m.matches()) {
			throw new DatabaseSQLException(MsgErrore.ERRORE_REGEX_INSERT, new DatabaseSQLException());
		}
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		chiudiConnessione();
	}
	
	
	/**
	 * Metodo che permette di eseguire un comando UPDATE su di un database SQL.
	 * Controlla prima di tutto che sia stata inserita, come parametro di input, 
	 * una query di tipo UPDATE, per quanto possibile; successivamente apre
	 * una connessione tramite il metodo apriConnessione, poi esegue il comando
	 * e chiude la connessione tramite metodo chiudiConnessione
	 * 
	 * @param query stringa che deve rappresentare il comando UPDATE
	 * @throws SQLException
	 * @throws DatabaseSQLException
	 */
	public static void update(String query) throws SQLException, DatabaseSQLException {
		
		/* se la regex non è rispettata dalla query passata in input, solleva un eccezione di tipo 
		 * DatabaseSQLException con un messaggio personalizzato per il tipo di errore specifico */
		Pattern p = Pattern.compile(updateRegex);
		Matcher m = p.matcher(query);
		if (!m.matches()) {
			throw new DatabaseSQLException(MsgErrore.ERRORE_REGEX_UPDATE, new DatabaseSQLException());
		}
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		chiudiConnessione();
	}
	
	
	/**
	 * Metodo che permette di eseguire un comando DELETE su di un database SQL.
	 * Controlla prima di tutto che sia stata inserita, come parametro di input, 
	 * una query di tipo DELETE, per quanto possibile; successivamente apre
	 * una connessione tramite il metodo apriConnessione, poi esegue il comando
	 * e chiude la connessione tramite metodo chiudiConnessione
	 * 
	 * @param query stringa che deve rappresentare il comando DELETE
	 * @throws SQLException
	 * @throws DatabaseSQLException
	 */
	public static void delete(String query) throws SQLException, DatabaseSQLException {
		
		/* se la regex non è rispettata dalla query passata in input, solleva un eccezione di tipo 
		 * DatabaseSQLException con un messaggio personalizzato per il tipo di errore specifico */
		Pattern p = Pattern.compile(deleteRegex);
		Matcher m = p.matcher(query);
		if (!m.matches()) {
			throw new DatabaseSQLException(MsgErrore.ERRORE_REGEX_DELETE, new DatabaseSQLException());
		}
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		chiudiConnessione();
	}

}
