/**
 * 
 */
package gestori.gestoreImpiegati;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.mysql.cj.protocol.Resultset;

import databaseSQL.DatabaseSQL;
import databaseSQL.Query;
import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gestori.gestoreImpiegati.exception.MsgExceptionGestoreImpiegato;
import utility.Data;

/**
 * @author Francolino Flavio Domenico
 * 
 * classe per gestire la lettura/scrittura di un impiegato dal/sul db
 *
 */

import persona.ImpiegatoBulloni;
import persona.exception.ExceptionAnagraficaErrata;
import persona.exception.ExceptionImpiegato;

public class GestoreImpiegatiDb {

	private static final String NOME_TABELLA_IMPIEGATI = "Impiegato";

	private Set<ImpiegatoBulloni> impiegati = new HashSet<ImpiegatoBulloni>();// set per salvare localmente gli
																				// impiegati letti dal db

	/**
	 * costruttore.
	 * 
	 * questo costruttore si occupa di caricare il set locale con gli impiegati
	 * letti dal db
	 */
	public GestoreImpiegatiDb() throws SQLException, DatabaseSQLException {

		ResultSet rs = null;

		rs = DatabaseSQL.select(Query.getSimpleSelect(NOME_TABELLA_IMPIEGATI));

		while (rs.next()) {

			try {

				// instanzio in impiegato per ogni tupla letta dal db
				ImpiegatoBulloni i = new ImpiegatoBulloni(rs.getString(CampiTabellaImpiegati.nome.toString()),
						rs.getString(CampiTabellaImpiegati.cognome.toString()),
						rs.getString(CampiTabellaImpiegati.sesso.toString()).charAt(0),
						new Data(rs.getDate(CampiTabellaImpiegati.dataNascita.toString())),
						rs.getInt(CampiTabellaImpiegati.matricola.toString()),
						rs.getInt(CampiTabellaImpiegati.giornateLavorativeAnnuali.toString()),
						rs.getFloat(CampiTabellaImpiegati.stipendioMensile.toString()),
						rs.getInt(CampiTabellaImpiegati.bulloniVendibiliAnnualmente.toString()),
						Boolean.valueOf(rs.getString(CampiTabellaImpiegati.eliminato.toString())));

				impiegati.add(i);// aggiungo l'impiegato al set locale

			} catch (ExceptionAnagraficaErrata | ExceptionImpiegato e) {

				System.err.println(e.getMessage());
			}

		}
		DatabaseSQL.chiudiConnessione();
	}

	/**
	 * questo metodo serve per rendere disponibili gli impiegati letti dal db a
	 * tutte le classi che hanno bisogno di accedere ai medesimi impiegati letti
	 * 
	 * @return returnSetImpiegati clone del set locale di impiegati letti dal db o
	 *         che sono stati aggiunti in locale
	 */
	public Set<ImpiegatoBulloni> getSetImpiegati() {

		Set<ImpiegatoBulloni> returnSetImpiegati = new HashSet<ImpiegatoBulloni>();// set nel quale clonare gli
																					// impiegati da restituire

		for (ImpiegatoBulloni i : this.impiegati) {

			returnSetImpiegati.add((ImpiegatoBulloni) i.clone());

		}

		return returnSetImpiegati;

	}
	
	
	public ImpiegatoBulloni getImpiegatoByID(int id) throws ExceptionGestoreImpiegato {
		
		boolean flag = false;//flag per segnalare se viene trovato il dipendente cercato
		
		
		ImpiegatoBulloni impiegato = new ImpiegatoBulloni();
		
		for(ImpiegatoBulloni i : this.impiegati) {
			
			if(id == i.getID()) {
				
				impiegato = (ImpiegatoBulloni)i.clone();
				
				flag = true;
				
				break;
			}
		}
		
		if (flag == false)// non ho trovato l'impiegato richiesto

			throw new ExceptionGestoreImpiegato(MsgExceptionGestoreImpiegato.IMPIEGATO_NON_TROVATO,
					new ExceptionGestoreImpiegato());
		
		return impiegato;
	}

	/**
	 * questo metodo si occupa di inserire nel set locale e quindi poi sul db un
	 * nuovo impiegato passatoli come parametro assegnandoli quindi come
	 * id/matricola il numero successivo all'ultimo id presente nel db
	 * 
	 * @param impiegato l'impiegato che si vuole aggiungere
	 */
	public void aggiungiImpiegatoDB(ImpiegatoBulloni impiegato) throws SQLException, DatabaseSQLException {

		try {

			impiegato.setID(this.impiegati.size() - 1);

		} catch (ExceptionImpiegato e) {

			System.err.println(e.getMessage());
		}

		this.impiegati.add(impiegato);

		String[] valoriCampiTabella = { ((Integer) impiegato.getID()).toString(), impiegato.getNome(),
				impiegato.getCognome(), impiegato.getDataNascita().toSqlDate().toString(),
				String.valueOf(impiegato.getSesso()), ((Float) impiegato.getStipendioMensile()).toString(),
				((Integer) impiegato.getBulloniVendibiliAnnualmente()).toString(),
				((Integer) impiegato.getGiornateLavorativeAnnuali()).toString(),
				((Boolean) impiegato.getIsAssunto()).toString() };

		DatabaseSQL.insert(Query.getSimpleInsert(NOME_TABELLA_IMPIEGATI, valoriCampiTabella));

	}

	/**
	 * questo metodo si occupa di aggionare sul db i valori di stipendio e giornate
	 * lavorative dell'impiegato con il determinato id passatoli
	 * 
	 * @param id id dell'impiegato da aggiornare
	 * @throws ExceptionGestoreImpiegato sollevate se non riescono ad aggiornare i
	 *                                   valori
	 * @throws DatabaseSQLException
	 * @throws SQLException
	 */
	public void aggiornaImpiegatoDB(int id) throws ExceptionGestoreImpiegato, SQLException, DatabaseSQLException {

		boolean flag = false;// flag per indicare se si � trovato l'impiegato richiesto o meno

		if (id < 0)

			throw new ExceptionGestoreImpiegato(MsgExceptionGestoreImpiegato.IMPIEGATO_NON_TROVATO,
					new ExceptionGestoreImpiegato());

		for (ImpiegatoBulloni i : this.impiegati) {

			if (i.getID() == id) {// ho trovate l'impiegato richiesto

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.stipendioMensile.toString(), ((Float) i.getStipendioMensile()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// aggiorno lo
																										// stipendio

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.giornateLavorativeAnnuali.toString(),
						((Integer) i.getGiornateLavorativeAnnuali()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// aggiorno le
																										// giornate
																										// lavorative
				flag = true;
				break;
			}

		}

		if (flag == false)// non ho trovato l'impiegato richiesto

			throw new ExceptionGestoreImpiegato(MsgExceptionGestoreImpiegato.IMPIEGATO_NON_TROVATO,
					new ExceptionGestoreImpiegato());

	}

	/**
	 * questo metodo si occupa di aggionare sul db il valore di eliminato(da true a
	 * false) dell'impiegato con il determinato id passatoli
	 * 
	 * @param id id dell'impiegato da aggiornare
	 * @throws ExceptionGestoreImpiegato sollevata se � impossibe aggiornare il
	 *                                   valore dell'attributo eliminato
	 * @throws DatabaseSQLException
	 * @throws SQLException
	 */
	public void licenziaImpiegatoDB(int id) throws ExceptionGestoreImpiegato, SQLException, DatabaseSQLException {

		boolean flag = false;// flag per indicare se si � trovato l'impiegato richiesto o meno

		if (id < 0)

			throw new ExceptionGestoreImpiegato(MsgExceptionGestoreImpiegato.IMPIEGATO_NON_TROVATO,
					new ExceptionGestoreImpiegato());

		for (ImpiegatoBulloni i : this.impiegati) {

			if (i.getID() == id) {// ho trovate l'impiegato richiesto

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.stipendioMensile.toString(), ((Float) i.getStipendioMensile()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// azzero lo
																										// stipendio

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.giornateLavorativeAnnuali.toString(),
						((Integer) i.getGiornateLavorativeAnnuali()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// azzero le
																										// giornate
																										// lavorative

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.bulloniVendibiliAnnualmente.toString(),
						((Integer) i.getBulloniVendibiliAnnualmente()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// azzero i
																										// bulloni
																										// vendibili

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.eliminato.toString(), ((Boolean) i.getIsAssunto()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// setto
																										// eliminato su
																										// vero
				flag = true;
				break;
			}

		}

		if (flag == false)// non ho trovato l'impiegato richiesto

			throw new ExceptionGestoreImpiegato(MsgExceptionGestoreImpiegato.IMPIEGATO_NON_TROVATO,
					new ExceptionGestoreImpiegato());

	}

	/**
	 * questo metodo si occupa di aggionare sul db il valore di eliminato(da false a
	 * true) dell'impiegato con il determinato id passatoli
	 * 
	 * @param id id dell'impiegato da aggiornare
	 * @throws ExceptionGestoreImpiegato sollevata se � impossibe aggiornare il
	 *                                   valore dell'attributo eliminato
	 * @throws DatabaseSQLException
	 * @throws SQLException
	 */
	public void assumiImpiegatoDB(int id) throws ExceptionGestoreImpiegato, SQLException, DatabaseSQLException {

		boolean flag = false;// flag per indicare se si � trovato l'impiegato richiesto o meno

		if (id < 0)

			throw new ExceptionGestoreImpiegato(MsgExceptionGestoreImpiegato.IMPIEGATO_NON_TROVATO,
					new ExceptionGestoreImpiegato());

		for (ImpiegatoBulloni i : this.impiegati) {

			if (i.getID() == id) {// ho trovate l'impiegato richiesto

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.stipendioMensile.toString(), ((Float) i.getStipendioMensile()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// setto lo
																										// stipendio

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.giornateLavorativeAnnuali.toString(),
						((Integer) i.getGiornateLavorativeAnnuali()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// setto le
																										// giornate
																										// lavorative

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.bulloniVendibiliAnnualmente.toString(),
						((Integer) i.getBulloniVendibiliAnnualmente()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// setto i
																										// bulloni
																										// vendibili

				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_IMPIEGATI,
						CampiTabellaImpiegati.eliminato.toString(), ((Boolean) i.getIsAssunto()).toString(),
						CampiTabellaImpiegati.matricola.toString(), ((Integer) i.getID()).toString()));// setto
																										// eliminato su
																										// falso
				flag = true;
				break;
			}

		}

		if (flag == false)// non ho trovato l'impiegato richiesto

			throw new ExceptionGestoreImpiegato(MsgExceptionGestoreImpiegato.IMPIEGATO_NON_TROVATO,
					new ExceptionGestoreImpiegato());

	}

}
