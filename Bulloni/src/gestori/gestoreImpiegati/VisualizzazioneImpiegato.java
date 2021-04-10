/**
 * 
 */
package gestori.gestoreImpiegati;

import java.util.Set;

import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import persona.ImpiegatoBulloni;

/**
 * @author Francolino Flavio Domenico
 * 
 * interfaccia contenente tutti i metodi utili alla visualazzazione di uno o piu impiegati
 *
 */
public interface VisualizzazioneImpiegato {
	
	/**
	 * metodo per ottenere un impiegato in base al suo id
	 * @param id id dell'impiegato da ottenere
	 * @throws Exception eventuale eccezzioni per valori errati
	 */
	public ImpiegatoBulloni getImpiegatoByID(int id) throws ExceptionGestoreImpiegato;
	
	/**
	 * metodo usato per controllare se ci sono o meno dati da visualizzare/leggere/modificare
	 * 
	 * @return true se non ci sono dati da visualizzare/leggere, false altrimenti
	 */
	public boolean localSetIsEmpty();
	
	/**
	 * metodo per ottenere tutti gli impiegati (assunti e non) da visualizzare/leggere/modificare
	 * @return il set di impiegati assunti che è possibile leggere/modificare
	 */
	public Set<ImpiegatoBulloni> getSetImpiegatiCompleto();
	
	/**
	 * metodo per ottenere i soli impiegati assunti da visualizzare/leggere/modificare
	 * @return il set di impiegati che è possibile leggere/modificare
	 */
	public Set<ImpiegatoBulloni> getSetImpiegatiAssunti();

}
