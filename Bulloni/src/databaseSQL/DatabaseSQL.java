package databaseSQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

/**
 * @author GiannettaGerardo
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
	 * insert into tabella values ('qualcosa', 'numero') */
	private static final String insertRegex = "(insert into \\w+ values [(]('\\w+', )*'\\w+'[)])";
	
	/** Questa espressione regolare accetta stringa del tipo:
	 * update tabella set campo='numero'
	 * update tabella set campo = 'numero'
	 * Quello che viene dopo il simbolo "=" è molto generico, perché il comando richiesto potrebbe risultare
	 * troppo complesso da analizzare nella sua interezza, stessa cosa vale per la clausola where */
	private static final String updateRegex = "(update \\w+ set \\w+((=)|( = )).+( where .+)?)";
	
	/** Questa espressione regolare accetta stringa del tipo:
	 * delete from tabella where campo='qualcosa'
	 * delete from tabella where campo = 'numero' */
	private static final String deleteRegex = "(delete from \\w+ where \\w+((=)|( = ))'\\w+')";
	

	/**
	 * Il costruttore di Query rimane privato
	 */
	private DatabaseSQL() {}
	
	
	/**
	 * Metodo che restituisce una connessione utilizzando la classe Connessione.
	 * Sarà utilizzato solo all'interno dei principali metodi di questa classe
	 * 
	 * @return un oggetto Connection già creato e inizializzato
	 */
	private static Connection apriConnessione() {
		return Connessione.getConnection();
	}
	
	
	/**
	 * Metodo che chiude la connessione aperta dal metodo apriConnessione.
	 * Deve lavorare sullo stesso oggetto Connection di apriConnessione, anche mediante alias
	 */
	public static void chiudiConnessione() {
		Connessione.closeConnection();
	}
	
	
	
	public static ResultSet select(String query) throws SQLException {
		
		if (!Pattern.matches(selectRegex, query)) {
			// creare eccezione
		}
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		return rs;
	}
	
	
	
	public static void insert(String query) throws SQLException {
		
		if (!Pattern.matches(insertRegex, query)) {
			// creare eccezione
		}
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		chiudiConnessione();
	}
	
	
	
	public static void update(String query) throws SQLException {
		
		if (!Pattern.matches(updateRegex, query)) {
			// creare eccezione
		}
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		chiudiConnessione();
	}
	
	
	
	public static void delete(String query) throws SQLException {
		
		if (!Pattern.matches(deleteRegex, query)) {
			// creare eccezione
		}
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		chiudiConnessione();
	}

}
