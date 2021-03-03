package gestori;

import java.util.Set;
import java.util.HashSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;

import utility.Data;
/*
 * Classi e interfacce per i bulloni e le eccezioni
 */
import bulloni.Bullone;
import bulloni.BulloneGrano;
import bulloni.Materiale;
import bulloni.Innesto;
import bulloni.exception.BulloneException;
/*
 * Classi per gestire il database e le sue eccezioni (effettuare query, insert, update ecc...) e per creare le query da far eseguire al DBMS.
 */
import databaseSQL.DatabaseSQL;
import databaseSQL.Query;
import databaseSQL.exception.DatabaseSQLException;


/**
 * Classe per la gestione dei bulloni di qualsiasi tipo.
 * All'interno ci saranno tutti i metodi per gestire un set di bulloni (di qualsiasi tipo) e per farlo interfacciare con il database SQL,
 * inserendo i dati all'interno delle tabelle o selezionarli. Sarà anche possibile modificare gli attributi dei bulloni e sincronizzare
 * le modifiche con il database.
 *  
 * @author Catignano Francesco
 *
 */
public class GestoreBulloni {
	private Set<Bullone> bulloni = new HashSet<Bullone>();	// Set di bulloni
	private static int codBulloneAutomatico = 1;	// Genera automaticamente il codice per i bulloni in modo che siano unici. Viene incrementato ogni volta che viene utilizzato.
	private static final String NOME_TABELLA_BULLONI = "Bullone";	// Nome della tabella generica dei bulloni per eseguire le insert, le query e le modifiche.
	private static final String NOME_TABELLA_BULLONE_GRANO = "Bullone_grano";	// Nome della tabella specifica per eseguire le insert, le query e le modifiche.
	
	
	// COSTRUTTORE
	/**
	 * Costruisce un gestore per i bulloni.
	 * Prende singolarmente i dati dal database e costruisce un oggetto specifico di tipo Bullone a partire dal
	 * risultato della query. Successivamente aggiunge l'oggetto costruito all'interno del set di bulloni.
	 */
	public GestoreBulloni() {
		/*
		 * Se il set non e' vuoto, lo pulisce prima del riempimento.
		 * In questo modo sicuramente verra' riempito con i dati aggiornati presenti all'interno della base di dati. 
		 */
		if(!bulloni.isEmpty()) {
			bulloni.clear();
		}
		
		// Aggiunta di bulloni di tipo grano dal database
		try {
			// Creazione della query ed esecuzione della select
			ResultSet rs = DatabaseSQL.select(Query.getSimpleSelectEquiJoin(NOME_TABELLA_BULLONI, NOME_TABELLA_BULLONE_GRANO, CampiTabellaBullone.codice.toString(), CampiTabellaBulloneGrano.codice.toString()));
			while(rs.next()) {
				bulloni.add(costruisciBulloneGrano(rs));
				codBulloneAutomatico++;	// Aggiorna il suo valore, cosi' quando verra' utilizzato, codBulloneAutomatico avra' un valore che sicuramente non e' mai stato utilizzato per il codice del bullone.
			}
			DatabaseSQL.chiudiConnessione();	// Chiusura della connessione al db (l'apertura è fatta automaticamente al momento della chiamata ad una select
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(DatabaseSQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	
	// OPERAZIONI
	/**
	 * A partire dal risultato di una query, costruisce un oggetto Bullone di tipo grano.
	 * @param rs Il risultato della query.
	 * @return bullone Il bullone costruito.
	 */
	private Bullone costruisciBulloneGrano(ResultSet rs) {
		try {
			Bullone bullone = new BulloneGrano(rs.getInt(1), new Data(rs.getDate(2)), rs.getString(3), rs.getDouble(4), rs.getDouble(5), Materiale.valueOf(rs.getString(9)), rs.getDouble(6), rs.getDouble(7), Innesto.valueOf(rs.getString(10)));
			if(rs.getString(11)=="T") {
				bullone.elimina();
			}
			return bullone;
		} catch(BulloneException e) {
			System.err.println(e.getMessage());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch(DateTimeException e) {
			System.err.println(e.getMessage());
		}
				
		return null;
	}
	
}
