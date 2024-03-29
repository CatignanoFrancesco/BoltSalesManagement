package gestori.gestorevendite;

import java.sql.SQLException;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gestori.gestorevendite.exception.GestoreVenditaException;
import vendita.MerceVenduta;
import vendita.Vendita;

/**
 * 
 * Interfaccia contenente i metodi che si occupano dell'inserimento nel GestoreVendita
 * 
 * @author GiannettaGerardo
 * 
 */
public interface InserimentoVendite {

	/**
	 * Metodo che inserisce nel Set di vendite e nel database un nuovo oggetto vendita.
	 * Effettua opportuni controlli prelinimari sull'oggetto, lo inserisce nel Set controllando
	 * che non sia già presente un oggetto con lo stesso codice chiave e infine si avvale di un
	 * altro metodo privato per inserire nel database anche il corrispondente Set di MerceVenduta
	 * 
	 * @param vendita oggetto vendita da inserire nel database
	 * @throws GestoreVenditaException
	 */
	public void aggiungiVendita(Vendita<MerceVenduta> vendita) throws GestoreVenditaException, ExceptionGestoreImpiegato, DatabaseSQLException, SQLException;
	
	
	/**
	 * Metodo che ritorna il codice delle vendite automatico;
	 * È possibile usarlo per inserire un nuovo oggetto Vendita nel gestore,
	 * assicurandosi così di inserire un codice valido
	 * 
	 * @return il codice delle vendite automatico
	 */
	public int getCodVenditaAutomatico();
	
}
