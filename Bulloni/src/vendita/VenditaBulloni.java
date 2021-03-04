package vendita;

import persona.Impiegato;
import utility.Data;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import vendita.exception.*;

/**
 * @author GiannettaGerardo
 *
 */
public class VenditaBulloni extends AbstractVendita<MerceVenduta, Impiegato> implements Vendita<MerceVenduta, Impiegato> {

	private Impiegato impiegato;
	private HashSet<MerceVenduta> merce = new HashSet<MerceVenduta>();
	
	/**
	 * Costruttore di VenditaBulloni
	 * 
	 * @param codVendita codice univoco della vendita
	 * @param data data della vendita
	 * @param impiegato responsabile vendita
	 * @param merce insieme di merce venduta
	 * @throws VenditaException
	 */
	public VenditaBulloni(int codVendita, Data data, Impiegato impiegato, HashSet<MerceVenduta> merce) throws VenditaException {
		super(codVendita, data);
		
		String errore = MsgErroreVendita.CREAZIONE_VENDITA + MsgErroreVendita.CREAZIONE_VENDITA_BULLONI;
		boolean eccezione = false;
		
		if (impiegato == null) {
			errore += MsgErroreVendita.RESPONSABILE_VENDITA_NULLO + "\n";
			eccezione = true;
		}
		
		if (merce == null || merce.isEmpty()) {
			errore += MsgErroreVendita.MERCE_VENDUTA_NULLA + "\n";
			eccezione = true;
		}
		
		if (eccezione)
			throw new VenditaException(errore, new VenditaException());
		
		this.impiegato = impiegato;
		this.merce = merce;
		
	}


	@Override
	/**
	 * {@inheritDoc}
	 */
	public double getPrezzoVenditaTotale() {
		return super.prezzoVenditaTotale;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getQuantitaMerceTotale() {
		return super.quantitaMerceTotale;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean addMerceVenduta(MerceVenduta merce) throws VenditaException {
		
		if (merce == null)
			throw new VenditaException(MsgErroreVendita.CREAZIONE_VENDITA + MsgErroreVendita.MERCE_VENDUTA_NULLA, new VenditaException());
		
		return this.merce.add(merce);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean addAllMerceVenduta(Set<MerceVenduta> merce) throws VenditaException {
		
		if (merce == null)
			throw new VenditaException(MsgErroreVendita.CREAZIONE_VENDITA + MsgErroreVendita.MERCE_VENDUTA_NULLA, new VenditaException());
		
		return this.merce.addAll(merce);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Impiegato getResponsabileVendita() {
		return this.impiegato;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * {@inheritDoc}
	 */
	public Set<MerceVenduta> getMerceVenduta() {
		
		Iterator<MerceVenduta> i = merce.iterator();
		
		HashSet<MerceVenduta> clone = (HashSet<MerceVenduta>)merce.clone();
		clone.clear();
		
		while(i.hasNext()) {
			clone.add(i.next());
		}
		
		return clone;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setQuantitaMerceByCodice(int codiceMerce, int nuovaQuantita) {
		
		Iterator<MerceVenduta> i = merce.iterator();
		MerceVenduta mv = null;
		
		while(i.hasNext()) {
			mv = i.next();
			if (mv.getCodiceBullone() == codiceMerce) {
				mv.setNumeroBulloni(nuovaQuantita);
				mv.setPrezzoBulloni(mv.getPrezzoVenditaBullone() * nuovaQuantita);
				break;
			}
		}
	}
	
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		
		String risultato = "Classe: " + this.getClass().getSimpleName() + "\n" + super.toString() +
				           "Responsabile vendita:\n" + this.getResponsabileVendita().toString() + "\n" +
				           "Merce venduta :";
		
		Iterator<MerceVenduta> i = merce.iterator();
		
		while(i.hasNext()) {
			risultato += i.next().toString() + "\n";
		}
		risultato += "\n";

		return risultato;
		       
	}
}
