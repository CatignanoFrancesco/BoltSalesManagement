package gestori.gestorevendite;

import java.util.Set;

import gestori.gestorevendite.exception.GestoreVenditaException;
import utility.Data;
import vendita.MerceVenduta;
import vendita.Vendita;

/**
 *
 * Interfaccia contenente i metodi che si occupano della visualizzazione nel GestoreVendita
 * 
 * @author GiannettaGerardo
 * 
 */
public interface VisualizzazioneVendite {
	
	/**
	 * Metodo che ritorna un Set<Vendita<MerceVenduta>> di cloni dell'originale
	 * 
	 * @return Set<Vendita<MerceVenduta>> di cloni
	 * @throws GestoreVenditaException
	 */
	public Set<Vendita<MerceVenduta>> getVendite() throws GestoreVenditaException;
	
	
	/**
	 * Metodo che ritorna il clone di un oggetto Vendita<MerceVenduta>
	 * in base ad un codice vendita passato in input
	 * 
	 * @param codiceVendita codice da cercare
	 * @return l'oggetto Vendita<MerceVenduta> trovato
	 * @throws GestoreVenditaException
	 */
	public Vendita<MerceVenduta> getVenditaByCodice(int codiceVendita) throws GestoreVenditaException;
	
	
	/**
	 * Metodo che ritorna il clone di un Set<Vendita<MerceVenduta>>
	 * in base ad una data passata in input
	 * 
	 * @param dataVendita data di effettuazione della vendita
	 * @return il Set<Vendita<MerceVenduta>> trovato
	 * @throws GestoreVenditaException
	 */
	public Set<Vendita<MerceVenduta>> getVenditeByData(Data dataVendita) throws GestoreVenditaException;
	
	
	/**
	 * Metodo che ritorna il clone di un Set<Vendita<MerceVenduta>>
	 * in base al codice impiegato passato in input
	 * 
	 * @param codiceImpiegato codice impiegato da cercare nel Set
	 * @return il Set<Vendita<MerceVenduta>> trovato
	 * @throws GestoreVenditaException
	 */
	public Set<Vendita<MerceVenduta>> getVenditeByImpiegato(int codiceImpiegato) throws GestoreVenditaException;
	
	
	/**
	 * Metodo che interroga l'HashMap impiegatoData contenente il numero di bulloni venduti in base
	 * all'impiegato e alla data in cui è stata effettuata la vendita.
	 * 
	 * @param matricolaImpiegato codice univoco di un impiegato
	 * @param dataVendita data in cui la vendita è stata effettuata
	 * @return -1 se i dati inseriti in input non sono contenuti nell'HashMap, un numero >= a 0 altrimenti
	 */
	public int getNumBulloniVendutiByImpiegatoData(int matricolaImpiegato, Data dataVendita);
	
	
	/**
	 * Metodo che interroga l'HashMap impiegatoAnno contenente il numero di bulloni venduti in base
	 * all'impiegato e all'anno in cui sono state effettuate le vendite.
	 * 
	 * @param matricolaImpiegato codice univoco di un impiegato
	 * @param anno anno in cui sono state effettuate delle vendite
	 * @return -1 se i dati inseriti in input non sono contenuti nell'HashMap, un numero >= a 0 altrimenti
	 */
	public int getNumBulloniVendutiByImpiegatoAnno(int matricolaImpiegato, int anno);
	
	
	/**
	 * Metodo che ritorna una stringa contenente tutte le informazioni dettagliate dell'istanza dell'oggetto
	 * con il quale si chiama questo metodo
	 * 
	 * @return la stringa con tutte le informazioni dell'oggetto
	 */
	public String toString();
	
}
