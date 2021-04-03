package gestori.gestoribulloni;

import java.sql.SQLException;

import bulloni.Bullone;
import bulloni.exception.BulloneException;
import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoribulloni.exception.GestoreBulloniException;

/**
 * Interfaccia contenente i metodi per l'aggiunta di un bullone nel gestore dei bulloni.
 * @author Catignano Francesco
 *
 */
public interface AggiungiBulloni {
	
	/**
	 * Metodo per aggiungere al set di bulloni il bullone di tipo grano e per inserirlo nel database.
	 * Se non e' stato ricevuto alcun bullone in input, verra' sollevata un'eccezione.
	 * Se viene ricevuto un bullone il cui codice esiste gia', ne viene cambiato il codice (attraverso l'attributo codBulloneAutomatico)
	 * e viene inserito nel set e successivamente nella relativa tabella del database.
	 * @param b Il bullone grano
	 * @throws GestoreBulloniException L'eccezione sollevata se non e' stato ricevuto in input alcun bullone.
	 * @throws BulloneException L'eccezione sollevata se i dati passati al costruttore di BulloneGrano non rispettano le specifiche semantiche.
	 * @throws DataBaseSQLException L'eccezione sollevata quando ci sono errori con la connessione al database o con l'esecuzione di query.
	 * @throws SQLException L'eccezione sollevata quando ci sono errori con la connessione al database o con l'esecuzione di query.
	 */
	public void newBulloneGrano(Bullone b) throws GestoreBulloniException, BulloneException, DatabaseSQLException, SQLException;
	
	
	/**
	 * Metodo per restituire il codice del prossimo bullone da aggiungere. Questo metodo torna utile per evitare fallimenti nell'aggiunta di un bullone
	 * a causa del codice che non e' univoco.
	 * @return codBulloneAutomatico Il codice del prossimo bullone da aggiungere.
	 */
	public int getCodBulloneAutomatico();
}
