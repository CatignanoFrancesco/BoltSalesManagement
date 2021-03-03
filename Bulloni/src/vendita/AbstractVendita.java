package vendita;

import java.util.Set;
import utility.Data;

/**
 * @author GiannettaGerardo
 * @param <T> rappresenta la merce in vendita
 * @param <E> rappresenta il responsabile della vendita o più semplicemente colui che la effettua
 *
 * Classe astratta rappresentante una vendita di merce generica, effettuata da un certo responsabile vendita
 */
public abstract class AbstractVendita<T, E> implements Vendita<T, E> {

	/** Codice univoco della vendita */
	private int codVendita;
	
	/** Data in cui la vendita è effettuata */
	private Data data;
	
	/** Prezzo totale della vendita, da calcolare sulla base del valore totale di tutta la merce venduta in tale vendita */
	protected double prezzoVenditaTotale;
	
	/** Quantità totale della merce veduta in tale vendita */
	protected int quantitaMerceTotale;
	
	
	/**
	 * Costruttore della classe astratta AbstractVendita
	 * 
	 * @param codVendita codice univoco della vendita
	 * @param data data in cui la vendita è effettuata
	 */
	public AbstractVendita(int codVendita, Data data) {
		this.codVendita = codVendita;
		this.data = data;
	}
	
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getCodVendita() {
		return this.codVendita;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Data getData() {
		return (Data)this.data.clone();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract double getPrezzoVenditaTotale();

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract int getQuantitaMerceTotale();

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract boolean addMerceVenduta(T merce);

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract boolean addAllMerceVenduta(Set<T> merce);

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract E getResponsabileVendita();

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract Set<T> getMerceVenduta();

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract void setQuantitaMerceByCodice(int codiceMerce, int nuovaQuantita);

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codVendita;
		return result;
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract boolean equals(Object obj);
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract Object clone();
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		
		Data d = this.getData();
		
		return "Codice vendita: " + this.getCodVendita() + "\n" +
	           "Data vendita: " + d.getGiorno() + "/" + d.getMese() + "/" + d.getAnno() + "\n";
	}
	

}
