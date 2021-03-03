package vendita;

import persona.Impiegato;
import utility.Data;
import java.util.Set;
import java.util.HashSet;
import vendita.exception.*;

/**
 * @author GiannettaGerardo
 *
 */
public class VenditaBulloni extends AbstractVendita<MerceVenduta, Impiegato> implements Vendita<MerceVenduta, Impiegato> {

	private Impiegato impiegato;
	private Set<MerceVenduta> merce = new HashSet<MerceVenduta>();
	
	/**
	 * Costruttore di VenditaBulloni
	 * 
	 * @param codVendita codice univoco della vendita
	 * @param data data della vendita
	 * @param impiegato responsabile vendita
	 * @param merce insieme di merce venduta
	 * @throws VenditaException
	 */
	public VenditaBulloni(int codVendita, Data data, Impiegato impiegato, Set<MerceVenduta> merce) throws VenditaException {
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
	public boolean addMerceVenduta(MerceVenduta merce) {
		return false;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean addAllMerceVenduta(Set<MerceVenduta> merce) {
		return false;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Impiegato getResponsabileVendita() {
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Set<MerceVenduta> getMerceVenduta() {
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setQuantitaMerceByCodice(int codiceMerce, int nuovaQuantita) {
		
	}

	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		return false;
	}
	
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return null;
	}
}
