package vendita;

import utility.Data;
import java.util.Set;

/**
 * @author GiannettaGerardo
 * @param <T> rappresenta la merce in vendita
 * @param <E> rappresenta il responsabile della vendita o più semplicemente colui che la effettua
 *
 * Interfaccia contenente tutte le operazioni disponibili per tutti i tipi di Vendite
 */
public interface Vendita<T, E> {
	
	public int getCodVendita();
	
	public Data getData();
	
	public double getPrezzoVenditaTotale();
	
	public int getQuantitaMerceTotale();
	
	public boolean addMerceVenduta(T merce);
	
	public boolean addAllMerceVenduta(Set<T> merce);
	
	public E getResponsabileVendita();
	
	public Set<T> getMerceVenduta();
	
	public void setQuantitaMerceByCodice(int codiceMerce, int nuovaQuantita);
	
	public int hashCode();
	
	public boolean equals(Object obj);
	
	public Object clone();
	
	public String toString();
}
