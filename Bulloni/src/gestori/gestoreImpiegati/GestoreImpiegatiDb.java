/**
 * 
 */
package gestori.gestoreImpiegati;

import java.sql.SQLException;
import java.util.HashSet;

import com.mysql.cj.protocol.Resultset;

import databaseSQL.DatabaseSQL;
import databaseSQL.Query;
import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;

/**
 * @author Francolino Flavio Domenico
 * 
 * classe per gestire la lettura/scrittura di un impiegato dal/sul db
 *
 */

import persona.ImpiegatoBulloni;

public class GestoreImpiegatiDb {

	private static final String NOME_TABELLA_IMPIEGATI = "impiegati";

	private HashSet<ImpiegatoBulloni> impiegati = new HashSet<ImpiegatoBulloni>();// set per salvare localmente gli
																					// impiegati letti dal db

	/**
	 * costruttore.
	 * 
	 * questo costruttore si occupa di caricare il set locale con gli impiegati
	 * letti dal db
	 */
	public GestoreImpiegatiDb() {

		
		//blocco commentato poiche ci son problemi con la connessione al server mysql locale
		/*
		 * try {
		 * 
		 * Resultset rs =
		 * (Resultset)DatabaseSQL.select(Query.getSimpleSelect(NOME_TABELLA_IMPIEGATI));
		 * 
		 * //strrs
		 * 
		 * while(rs.getNextResultset() != null) {
		 * 
		 * ImpiegatoBulloni i = new ImpiegatoBulloni(rs.get, cognome, sesso,
		 * dataNascita, id, giornateLavorativeAnnuali, stipendioMensile)
		 * 
		 * impiegati.add(new impi)
		 * 
		 * System.out.println(rs.getRows()); }
		 * 
		 * } catch (SQLException | DatabaseSQLException e) {
		 * 
		 * //System.err.println(e.getMessage());
		 * 
		 * e.getStackTrace(); }
		 */

	}

	/**
	 * questo metodo serve per rendere disponibili gli impiegati letti dal db a
	 * tutte le classi che hanno bisogno di accedere ai medesimi impiegati letti
	 * 
	 * @return impiegati l'insieme di impiegati letti dal db o che sono stati
	 *         aggiunti in locale
	 */
	public HashSet<ImpiegatoBulloni> getSetImpiegati() {

		return impiegati;

	}

	/**
	 * questo metdo si occupa di inserire nel set locale e quindi poi sul db un
	 * nuovo impiegato passatoli come parametro
	 * 
	 * @param impiegato l'impiegato che si vuole aggiungere
	 * @throws ExceptionGestoreImpiegato sollevate se non si riesce ad aggiungere un
	 *                                   impiegato
	 */
	public void aggiungiImpiegatoDB(ImpiegatoBulloni impiegato) throws ExceptionGestoreImpiegato {

	}

	/**
	 * questo metodo si occupa di aggionare sul db i valori di stipendio e giornate
	 * lavorative dell'impiegato con il determinato id passatoli
	 * 
	 * @param id id dell'impiegato da aggiornare
	 * @throws ExceptionGestoreImpiegato sollevate se non riescono ad aggiornare i
	 *                                   valori
	 */
	public void aggiornaImpiegatoDB(int id) throws ExceptionGestoreImpiegato {

	}

	/**
	 * questo metodo si occupa di aggionare sul db il valore di eliminato(da true a
	 * false) dell'impiegato con il determinato id passatoli
	 * 
	 * @param id id dell'impiegato da aggiornare
	 * @throws ExceptionGestoreImpiegato sollevata se è impossibe aggiornare il
	 *                                   valore dell'attributo eliminato
	 */
	public void licenziaImpiegatoDB(int id) throws ExceptionGestoreImpiegato {

	}

	/**
	 * questo metodo si occupa di aggionare sul db il valore di eliminato(da false a
	 * true) dell'impiegato con il determinato id passatoli
	 * 
	 * @param id id dell'impiegato da aggiornare
	 * @throws ExceptionGestoreImpiegato sollevata se è impossibe aggiornare il
	 *                                   valore dell'attributo eliminato
	 */
	public void assumiImpiegatDB(int id) throws ExceptionGestoreImpiegato {

	}

}
