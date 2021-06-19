package gestori.gestorevendite;

import java.sql.SQLException;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gestori.gestorevendite.exception.GestoreVenditaException;

/**
 *
 * Interfaccia contenente i metodi che si occupano della modifica nel GestoreVendita
 * 
 * @author GiannettaGerardo
 * 
 */
public interface ModificaVendite {

	/**
	 * Metodo che effettua l'update sul Set di vendite e sul database del numero di bulloni venduti per un dato bullone
	 * in una data vendita. Utilizzando il metodo setQuantitaMerceByCodice degli oggetti Vendita ci si assicura anche 
	 * l'aggiornamento del numero totale di bulloni venduti nell'intera vendita e del prezzo totale della vendita
	 * 
	 * @param codVendita codice identificativo della vendita
	 * @param codBullone codice identificativo del bullone nella vendita
	 * @param nuovoNumero nuova quantità di bulloni per un dato bullone
	 * @return true se il metodo è terminato con successo, false altrimenti
	 * @throws GestoreVenditaException 
	 */
	public boolean updateNumeroBulloniVendutiByCodici(int codVendita, int codBullone, int nuovoNumero) throws GestoreVenditaException, ExceptionGestoreImpiegato, DatabaseSQLException, SQLException;
	
}
