/**
 * 
 */
package gestori.gestoreImpiegati;

import java.sql.SQLException;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import persona.exception.ExceptionImpiegato;

/**
 * 
 * intrefaccia contente tutti i metodi relativi alla modifica di un
 * impiegato
 *         
 *  @author Francolino Flavio domenico
 *
 */
public interface ModificaImpiegato {

	/**
	 * metodo per promuovere un impiegato ( aggiorna i valori degli attributi
	 * stipendio mesile e giornate lavorative annuali)
	 * 
	 * @param id                        id dell'impiegato da promuovere
	 * @param giornateLavorativeAnnuali nuove giornate da assegnare
	 * @param stipendioMensile          nuovo stipendio da assegnare
	 * @throws ExceptionGestoreImpiegato eventuale eccezione sollevate per valori
	 *                                   errati
	 * @throws SQLException              eventuale eccezione sollevate per valori
	 *                                   errati
	 * @throws DatabaseSQLException      eventuale eccezione sollevate per valori
	 *                                   errati
	 * @throws ExceptionImpiegato        eventuale eccezione sollevate per valori
	 *                                   errati
	 */
	public void promuoviImpiegato(int id, int giornateLavorativeAnnuali, float stipendioMensile)
			throws ExceptionGestoreImpiegato, SQLException, DatabaseSQLException, ExceptionImpiegato;

	/**
	 * metodo per licenziare un impiegato(setta su true l'attributo isEliminato)
	 * 
	 * @param id id dell'impiegato da licenziare
	 * @throws ExceptionGestoreImpiegato eventuale eccezione sollevate per valori
	 *                                   errati
	 * @throws SQLException              eventuale eccezione sollevate per valori
	 *                                   errati
	 * @throws DatabaseSQLException      eventuale eccezione sollevate per valori
	 *                                   errati
	 * @throws ExceptionImpiegato        eventuale eccezione sollevate per valori
	 *                                   errati
	 */
	public void licenziaImpiegato(int id)
			throws ExceptionGestoreImpiegato, SQLException, DatabaseSQLException, ExceptionImpiegato;
}
