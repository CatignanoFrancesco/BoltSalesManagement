/**
 * 
 */
package gestori.gestoreImpiegati;

/**
 * @author Francolino Flavio Domenico
 * 
 * intrefaccia contente tutti i metodi relativi alla modifica di un impiegato
 *
 */
public interface ModificaImpiegato {

	/**
	 * metodo per promuovere un impiegato ( aggiorna i valori degli attributi stipendio mesile e giornate lavorative annuali)
	 * 
	 * @param id id dell'impiegato da promuovere
	 * @param giornateLavorativeAnnuali nuove giornate da assegnare
	 * @param stipendioMensile nuovo stipendio da assegnare
	 * @throws Exception eventuale eccezione sollevate per valori errati
	 */
	public void promuoviImpiegato(int id, int giornateLavorativeAnnuali, float stipendioMensile) throws Exception;
	
	/**
	 * metodo per licenziare un impiegato(setta su true l'attributo isEliminato)
	 * 
	 * @param id id dell'impiegato da licenziare
	 * @throws Exception eventuali eccezione per valori errati
	 */
	public void licenziaImpiegato(int id) throws Exception;
}
