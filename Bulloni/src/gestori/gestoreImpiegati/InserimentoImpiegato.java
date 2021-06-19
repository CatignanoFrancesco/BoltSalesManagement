/**
 * 
 */
package gestori.gestoreImpiegati;

import java.sql.SQLException;

import databaseSQL.exception.DatabaseSQLException;
import persona.Impiegato;
import persona.exception.ExceptionImpiegato;

/**
 * 
 * interfaccia contenenete tutti i metodi relativi all'inserimento di un impiegato
 *
 * @author Francolino Flavio domenico
 */
public interface InserimentoImpiegato {
	
	/**
	 * metodo per aggiungere un nuovo impiegato al db
	 * @param impiegato l'impiegato da aggiungere
	 * @throws SQLException sollevate per eventuali valori errati
	 * @throws DatabaseSQLException sollevate per eventuali valori errati
	 * @throws ExceptionImpiegato sollevate per eventuali valori errati
	 */
	public void aggiungiImpiegato(Impiegato impiegato) throws SQLException, DatabaseSQLException, ExceptionImpiegato;

}
