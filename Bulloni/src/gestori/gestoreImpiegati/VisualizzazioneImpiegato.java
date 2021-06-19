package gestori.gestoreImpiegati;

import java.util.Set;

import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import persona.ImpiegatoBulloni;

/**
 * 
 * interfaccia contenente tutti i metodi utili alla visualazzazione di uno o piu impiegati
 * 
 * @author Francolino Flavio domenico
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
	 * metodo usato per controllare se ci sono o meno dati da visualizzare
	 * 
	 * @return true se non ci sono dati da visualizzare/leggere, false altrimenti
	 */
	public boolean localSetIsEmpty();
	
	/**
	 * metodo per ottenere tutti gli impiegati (assunti e non) da visualizzare
	 * @return il set di impiegati assunti che è possibile visualizzare
	 */
	public Set<ImpiegatoBulloni> getSetImpiegatiCompleto();
	
	/**
	 * metodo per ottenere i soli impiegati assunti da visualizzare
	 * @return il set di impiegati che è possibile visualizzare
	 */
	public Set<ImpiegatoBulloni> getSetImpiegatiAssunti();

}
