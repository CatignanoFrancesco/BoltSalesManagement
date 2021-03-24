package vendita;

import utility.Data;
import java.util.Set;
import vendita.exception.VenditaException;

/**
 * @author GiannettaGerardo
 * @param <T> rappresenta la merce in vendita
 *
 * Interfaccia contenente tutte le operazioni disponibili per tutti i tipi di Vendite
 */
public interface Vendita<T> {
	
	/**
	 * Metodo che ritorna il codice univoco della vendita
	 * 
	 * @return codice vendita
	 */
	public int getCodVendita();
	
	/**
	 * Metodo che ritorna la data in cui la vendita è stata effettuata
	 * 
	 * @return data della vendita
	 */
	public Data getData();
	
	/**
	 * Metodo che ritorna il prezzo totale della vendita
	 * 
	 * @return prezzo totale della vendita
	 */
	public double getPrezzoVenditaTotale();
	
	/**
	 * Metodo che ritorna la quantità totale di merce venduta (nella rispettiva vendita)
	 * 
	 * @return quantità totale di merce venduta
	 */
	public int getQuantitaMerceTotale();
	
	/**
	 * Metodo che aggiunge (all'insieme già esistente) un singolo oggetto rappresentante la merce venduta
	 * 
	 * @param merce merce venduta nella rispettiva vendita
	 * @return true se è possibile aggiungere alla vendita tale merce, false altrimenti
	 */
	public boolean addMerceVenduta(T merce) throws VenditaException;
	
	/**
	 * Metodo che aggiunge (all'insieme già esistente) un insieme di oggetti rappresentanti la merce venduta
	 * 
	 * @param merce insieme di merce venduta nella rispettiva vendita
	 * @return true se è possibile aggiungere alla vendita tale merce, false altrimenti
	 */
	public boolean addAllMerceVenduta(Set<T> merce) throws VenditaException;
	
	/**
	 * Metodo che ritorna il responsabile della vendita (o comuque chi ha effettuato la vendita)
	 * 
	 * @return responsabile della vendita
	 */
	public int getResponsabileVendita();
	
	/**
	 * Metodo che ritorna l'insieme di merce venduta nella rispettiva vendita;
	 * ritorna un insieme anche nel caso ci sia una singola merce venduta
	 * 
	 * @return insieme di merce venduta
	 */
	public Set<T> getMerceVenduta();
	
	/**
	 * Metodo che modifica la quantità di merce venduta di un singolo oggetto merce all'interno dell'insieme
	 * 
	 * @param codiceMerce codice univoco rappresentante un oggetto della merce venduta
	 * @param nuovaQuantita nuova quantità da modificare
	 * 
	 * @return true se il settaggio è andato a buon fine, false se il codice merce non è stato trovato
	 */
	public boolean setQuantitaMerceByCodice(int codiceMerce, int nuovaQuantita);
	
	/**
	 * Metodo che calcola e restituisce l'hashCode dell'oggetto, rendendolo di fatto univoco
	 * 
	 * @return hashCode dell'oggetto
	 */
	public int hashCode();
	
	/**
	 * Metodo che determina se un oggetto passato in input è uguale all'istanza dell'oggetto con il quale
	 * si chiama il metodo stesso
	 * 
	 * @param obj oggetto da confrontare
	 * @return true se gli oggetti sono considerabili uguali, false altrimenti
	 */
	public boolean equals(Object obj);
	
	/**
	 * Metodo che clona l'istanza dell'oggetto con il quale si chiama il metodo stesso
	 * 
	 * @return l'oggetto clonato
	 */
	public Vendita<T> clone();
	
	/**
	 * Metodo che ritorna una stringa contenente tutte le informazioni dettagliate dell'istanza dell'oggetto
	 * con il quale si chiama questo metodo
	 * 
	 * @return la stringa con tutte le informazioni dell'oggetto
	 */
	public String toString();
}
