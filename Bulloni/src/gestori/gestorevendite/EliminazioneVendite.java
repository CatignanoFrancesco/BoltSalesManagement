package gestori.gestorevendite;

import java.sql.SQLException;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestorevendite.exception.GestoreVenditaException;

/**
 * @author GiannettaGerardo
 * 
 * Interfaccia contenente i metodi che si occupano dell'eliminazione nel GestoreVendita
 */
public interface EliminazioneVendite {

	/**
	 * Metodo che elimina dal Set di vendite e dal database una certa vendita
	 * 
	 * @param codiceVendita codice identificativo della vendita da eliminare
	 * @return true se il metodo è terminato con successo, false altrimenti
	 */
	public boolean rimuoviVenditaByCodice(int codiceVendita) throws GestoreVenditaException, DatabaseSQLException, SQLException;
	
}
