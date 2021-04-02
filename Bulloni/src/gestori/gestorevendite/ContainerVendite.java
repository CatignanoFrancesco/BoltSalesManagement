package gestori.gestorevendite;

import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;

/**
 * @author GiannettaGerardo
 *
 * Interfaccia che estende tutte le altre interfacce del GestoreVendita e contiene inoltre i metodi di esso che non hanno una precisa collocazione
 */
public interface ContainerVendite extends VisualizzazioneVendite, InserimentoVendite, ModificaVendite, EliminazioneVendite {
	
	/**
	 * Metodo di controllo utilizzato nell'aggiunta e modifica di una vendita.
     * Se l'impiegato che vuole effettuare questa vendita, ha già un numero di bulloni venduti, in quella specifica data,
	 * tale che sommato alla quantità di bulloni di questa vendita, supera il massimo consentito giornaliero 
	 * (500 bulloni per tutti gli impiegati) o il massimo consentito annuale (varia per ogni impiegato), allora
	 * la vendita viene annullata
	 * 
	 * @param impiegato codice univoco dell'impiegato
	 * @param cid chiave per l'HashMap impiegatoData
	 * @param cia chiave per l'HashMap impiegatoAnno
	 * @param nuovaQuantitaCIA nuovo valore calcolato da aggiungere all'HashMap impiegatoAnno
	 * @param nuovaQuantitaCID nuovo valore calcolato da aggiungere all'HashMap impiegatoData
	 * @return true se il nuovo valore è accettabile per l'impiegato, false altrimenti
	 */
	public boolean checkNumeroBulloniVendutiImpiegato(int impiegato, ChiaveImpiegatoData cid, ChiaveImpiegatoAnno cia, int nuovaQuantitaCIA, int nuovaQuantitaCID) throws ExceptionGestoreImpiegato;
	

	/**
	 * Metodo che controlla se il set locale delle vendite è vuoto o meno
	 * 
	 * @return true se il set è vuoto, false altrimenti
	 */
	public boolean isEmpty();
	
}
