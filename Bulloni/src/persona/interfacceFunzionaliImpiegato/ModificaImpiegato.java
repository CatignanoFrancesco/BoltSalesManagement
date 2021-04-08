/**
 * 
 */
package persona.interfacceFunzionaliImpiegato;

/**
 * @author Francolino Flavio Domenico
 * 
 * interfaccia contenente tuttti i metodi relativi alla modifica di un impiegato
 *
 */
public interface ModificaImpiegato {
	
	/**
	 * questo metodo serve per settare su true il valore dell'attributo isLicenziato di un impiegato
	 * @param id l'id dell'impiegato da modificare
	 */
	public void licenziaImpiegato(int id) throws Exception;
	
	
	/**
	 * 
	 * @param id id dell'impiegato da modificare
	 * @param giornateLavorativeAnnuali le nuove giornatye da asseganare all'impiegato
	 * @param stipendioMensile il nuovo stipendio da assegnare all'impiegato
	 */
	public void promuoviImpiegato(int id, int giornateLavorativeAnnuali, float stipendioMensile) throws Exception;

}
