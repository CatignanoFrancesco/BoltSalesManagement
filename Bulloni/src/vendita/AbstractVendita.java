package vendita;

import java.util.Set;
import utility.Data;
import vendita.exception.*;
import java.time.LocalDate;;

/**
 * 
 * Classe astratta rappresentante una vendita di merce generica, effettuata da un certo responsabile vendita
 * 
 * @author GiannettaGerardo
 * @param <T> rappresenta la merce in vendita
 * 
 */
public abstract class AbstractVendita<T> implements Vendita<T> {

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
	public AbstractVendita(int codVendita, Data data) throws VenditaException {

		String errore = MsgErroreVendita.CREAZIONE_VENDITA;
		int anno = data.getAnno();
		
		if ((anno > LocalDate.now().getYear()) && (anno < LocalDate.now().getYear() - 150))
			throw new VenditaException(errore + MsgErroreVendita.DATA_NON_REALE, new VenditaException());
		
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
	public abstract boolean addMerceVenduta(T merce) throws VenditaException;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract boolean addAllMerceVenduta(Set<T> merce) throws VenditaException;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract int getResponsabileVendita();

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract Set<T> getMerceVenduta();

	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract boolean setQuantitaMerceByCodice(int codiceMerce, int nuovaQuantita);

	
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


	@SuppressWarnings("unchecked")
	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractVendita<T> other = (AbstractVendita<T>) obj;
		if (codVendita != other.codVendita)
			return false;
		return true;
	}


	@Override
	/**
	 * {@inheritDoc}
	 */
	public abstract Vendita<T> clone();
	
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
