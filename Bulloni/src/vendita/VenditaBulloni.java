package vendita;

import persona.Impiegato;
import utility.Data;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import vendita.exception.*;

/**
 * @author GiannettaGerardo
 * @param <MerceVenduta> classe associativa MerceVenduta per gesitire i bulloni venduti
 * @param <Impiegato> responsabile della vendita
 *
 * Classe che rappresenta una vendita di bulloni, effettuata da un responsabile vendita, ovvero
 * un Impiegato. Si avvala di una classe associativa, MerceVenduta, per gestire i bulloni e i 
 * relativi dati associati alla specifica vendita
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
		
		for (MerceVenduta mv : merce) {
			super.quantitaMerceTotale += mv.getNumeroBulloni();
			super.prezzoVenditaTotale += mv.getPrezzoBulloni();
		}
		
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
		
		boolean risultato = this.merce.add(merce);
		
		/* 
		 * se l'aggiunta è stata completata con successo, aggiorna gli attributi 
		 * quantitaMerceTotale
		 * prezzoVenditaTotale
		 * sommandogli i valori della nuova istanza di merce venduta
		 */
		if (risultato) {
			super.quantitaMerceTotale += merce.getNumeroBulloni();
			super.prezzoVenditaTotale += merce.getPrezzoBulloni();
		}
		
		return risultato;
	}

	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean addAllMerceVenduta(Set<MerceVenduta> merce) throws VenditaException {
		
		if (merce == null)
			throw new VenditaException(MsgErroreVendita.CREAZIONE_VENDITA + MsgErroreVendita.MERCE_VENDUTA_NULLA, new VenditaException());
		
		boolean risultato = this.merce.addAll(merce);
		
		/* 
		 * se l'aggiunta è stata completata con successo, aggiorna gli attributi 
		 * quantitaMerceTotale
		 * prezzoVenditaTotale
		 * sommandogli i valori del nuovo set di merce venduta
		 */
		if (risultato) {
			for (MerceVenduta mv : merce) {
				super.quantitaMerceTotale += mv.getNumeroBulloni();
				super.prezzoVenditaTotale += mv.getPrezzoBulloni();
			}
		}
		
		return risultato;
	}

	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public Impiegato getResponsabileVendita() {
		return (Impiegato)this.impiegato.clone();
	}

	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public Set<MerceVenduta> getMerceVenduta() {
		
		Set<MerceVenduta> clone = new HashSet<MerceVenduta>();
		
		for (MerceVenduta mv : this.merce) {
			clone.add((MerceVenduta)mv.clone());
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
				           "Quantità totale di merce venduta: " + super.quantitaMerceTotale + "\n" +
				           "Prezzo totale della merce venduta: " + super.prezzoVenditaTotale + "\n" +
				           "Responsabile vendita:\n" + this.getResponsabileVendita().toString() + "\n" +
				           "Merce venduta:\n";
		
		Iterator<MerceVenduta> i = merce.iterator();
		
		while(i.hasNext()) {
			risultato += i.next().toString() + "\n";
		}
		risultato += "\n";

		return risultato;
		       
	}
}
