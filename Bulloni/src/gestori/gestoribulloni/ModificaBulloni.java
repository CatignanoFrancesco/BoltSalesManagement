package gestori.gestoribulloni;

import java.sql.SQLException;

import bulloni.exception.BulloneException;
import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoribulloni.exception.GestoreBulloniException;

/**
 * Interfaccia contenente tutti i metodi per la modifica e la rimozione dei bulloni.
 * 
 * @author Catignano Francesco
 */
public interface ModificaBulloni {
	
	/**
	 * Esegue la modifica del prezzo di un bullone presente nel set ed effettua l'update anche nel DB.
	 * Cerca all'interno del set un bullone avente quel codice e, se lo trova, ne modifica il prezzo.
	 * Se il bullone non viene trovato, viene sollevata un'eccezione. Viene sollevata un'eccezione anche quando
	 * il prezzo non rientra nel range stabilito in Bullone.
	 * Infine, se la ricerca e la modifica sono andate a buon fine, viene effettuato l'update nel database.
	 * @param codice Il codice del bullone da modificare.
	 * @param nuovoPrezzo Il nuovo valore dell'attributo "prezzo".
	 * @throws BulloneException L'eccezione sollevata se il prezzo non rispetta le specifiche semantiche.
	 * @throws GestoreBulloniException L'eccezione sollevata se il bullone non e' stato trovato.
	 * @throws DatabaseSQLException L'eccezione sollevata quando ci sono errori con la connessione al database o quando non e' possibile eseguire l'update.
	 * @throws SQLException L'eccezione sollevata quando ci sono errori con la connessione al database o quando non e' possibile eseguire l'update.
	 */
	public void updatePrezzoBulloneByCodice(int codice, double nuovoPrezzo) throws GestoreBulloniException, BulloneException, DatabaseSQLException, SQLException;
	
	/**
	 * Questo metodo permette di "rimuovere" un bullone dal set di bulloni e dal database.
	 * In realta' viene solamente portato a "true" il valore dell'attributo "eliminato" in bullone. In questo modo non vengono eliminate tutte
	 * le informazioni sui bulloni che potrebbero servire alle altre classi, ma sono rese inaccessibili tutte le operazioni di modifica.
	 * La ricerca del bullone richiesto avviene mediante il codice ricevuto come parametro. Se la ricerca non da alcun risultato, viene sollevata
	 * un'eccezione. 
	 * @param codice Il codice del bullone da cercare.
	 * @throws GestoreBulloniException L'eccezione sollevata se il bullone non e' stato trovato.
	 * @throws DatabaseSQLException L'eccezione sollevata quando ci sono errori con la connessione al database o quando non e' possibile eseguire l'update.
	 * @throws SQLException L'eccezione sollevata quando ci sono errori con la connessione al database o quando non e' possibile eseguire l'update.
	 */
	public void rimuoviBulloneByCodice(int codice) throws GestoreBulloniException, DatabaseSQLException, SQLException;
}
