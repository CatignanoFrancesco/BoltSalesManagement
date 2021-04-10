/**
 * 
 */
package gestori.gestoreImpiegati;

import persona.Impiegato;

/**
 * @author Francolino Flavio Domenico
 * 
 * interfaccia contenenete tutti i metodi relativi all'inserimento di un impiegato
 *
 */
public interface InserimentoImpiegato {
	
	/**
	 * metodo per aggiungere un nuovo impiegato al db
	 * @param impiegato l'impiegato da aggiungere
	 */
	public void aggiungiImpiegato(Impiegato impiegato) throws Exception;

}
