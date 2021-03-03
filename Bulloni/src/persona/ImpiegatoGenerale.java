/**
 * 
 */
package persona;

import persona.exception.ExceptionAnagraficaErrata;
import persona.exception.ExceptionImpiegato;
import persona.exception.MsgExceptionImpiegato;
import utility.Data;

/**
 * @author Francolino Flavio Domenico
 * 
 *         classe per implementare l'interfaccia Impiegato
 *
 */
public class ImpiegatoGenerale extends AbstractPersona implements Impiegato {

	private static final int MIN_GIORNATE_LAVORATIVE_ANNUALI = 144;
	private static final int MAX_GIORNATE_LAVORATIVE_ANNUALI = 365;
	private static final int MIN_STIPENDIO_MENSILE = 1000;
	private static final int MAX_STIPENDIO_MENSILE = 2000;

	private int id, giornateLavorativeAnnuali;
	private float stipendioMensile;
	private boolean isAssunto = true;// alla crazione di un instanza Impiegato si presuppone che sia anche stato
										// assunto

	/**
	 * costruttore di classe
	 * 
	 * @param nome                      nome dell'impiegato
	 * @param cognome                   cognome dell'impiegato
	 * @param sesso                     sesso dell'impiegato
	 * @param dataNascita               data nascita dell'impiegato
	 * @param id                        id dell'impiegato
	 * @param giornateLavorativeAnnuali giornate lavorative annuali dell'impiegato
	 * @param stipendioMensile          stipendio mensile dell'impiegato
	 * @throws ExceptionImpiegato sollevata per errori riguardanti i valori di attributi propri di impiegato
	 * @throws ExceptionAnagraficaErrata sollevata per errori riguardatni i valori di attributo propri di AbstractPersona
	 */
	public ImpiegatoGenerale(String nome, String cognome, char sesso, Data dataNascita, int id,
			int giornateLavorativeAnnuali, float stipendioMensile) throws ExceptionImpiegato, ExceptionAnagraficaErrata{
		
		super(nome, cognome, sesso, dataNascita);

		boolean flagException = false;// flag per segnalare il sollevamento di un eccezione
		String msgException;// messaggio di eccezzione
		
		
		
		if ((stipendioMensile < MIN_STIPENDIO_MENSILE) || (stipendioMensile > MAX_STIPENDIO_MENSILE)) {// lo stipedendio non rispetta
																											// le soglie minime e massime

			flagException = true;
			msgException = MsgExceptionImpiegato.STIPENDIO_NON_VALIDO;

		} else if (this.giornateLavorativeAnnuali < MIN_GIORNATE_LAVORATIVE_ANNUALI
				|| this.giornateLavorativeAnnuali > MAX_GIORNATE_LAVORATIVE_ANNUALI) {//giornate minimi o massimo non rispettate

			flagException = true;
			msgException = MsgExceptionImpiegato.GIORNATE_NON_VALIDE;
		}

		if (flagException == true)
			
			throw new ExceptionImpiegato(msgException, new ExceptionImpiegato());

		else {

			this.id = id;
			this.giornateLavorativeAnnuali = giornateLavorativeAnnuali;
			this.stipendioMensile = stipendioMensile;

		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getID() {

		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getStipendioMensile() {

		return this.stipendioMensile;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getGiornateLavorativeAnnuali() {

		return this.giornateLavorativeAnnuali;
	}

	/**
	 * {@inheritDoc} ideale per impiegati prima licenziati e poi magari riassunti
	 */
	@Override
	public void assumi(int stipendioMensile, int giornateLavorativeAnnuali) throws ExceptionImpiegato {

		boolean flagException = false;// flag per segnalare il sollevamento di un eccezione
		String msgException;// messaggio di eccezzione

		if (this.isAssunto == true) {// l'impiegato risulta essere gia assunto
			// non è possibile riassumerlo

			flagException = true;
			msgException = MsgExceptionImpiegato.IMPIEGATO_GIA_ASSUNTO;

		} else if ((stipendioMensile < MIN_STIPENDIO_MENSILE) || (stipendioMensile > MAX_STIPENDIO_MENSILE)) {// lo stipedendio non rispetta
																												// le soglie minime e massime

			flagException = true;
			msgException = MsgExceptionImpiegato.STIPENDIO_NON_VALIDO;

		} else if (this.giornateLavorativeAnnuali < MIN_GIORNATE_LAVORATIVE_ANNUALI
				|| this.giornateLavorativeAnnuali > MAX_GIORNATE_LAVORATIVE_ANNUALI) {

			flagException = true;
			msgException = MsgExceptionImpiegato.GIORNATE_NON_VALIDE;
		}

		if (flagException == true)
			throw new ExceptionImpiegato(msgException, new ExceptionImpiegato());

		else {

			this.isAssunto = true;

			this.stipendioMensile = stipendioMensile;
			this.giornateLavorativeAnnuali = giornateLavorativeAnnuali;

		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void promuovi(float stipendioMensile, int giornateLavorativeAnnuali) {

		boolean flagException = false;// flag per segnalare il verificarsi di un eccezzione
		String msgException;

		if (this.isAssunto == false) {// l'impiegato risulta esere licenziato

			flagException = true;
			msgException = MsgExceptionImpiegato.IMPIEGATO_LICENZIATO;

		} else if (stipendioMensile <= this.stipendioMensile) {// il nuovo stipendio è minore uguale dello stipendio
																// attuale

			flagException = true;
			msgException = MsgExceptionImpiegato.STIPENDIO_PROMOZIONE_NON_VALIDO;

		} else if (stipendioMensile > MAX_STIPENDIO_MENSILE) {

			flagException = true;
			msgException = MsgExceptionImpiegato.STIPENDIO_NON_VALIDO;

		} else if (giornateLavorativeAnnuali < this.giornateLavorativeAnnuali) {

			flagException = true;
			msgException = MsgExceptionImpiegato.GIORNATE_NON_VALIDE;
		} else if (giornateLavorativeAnnuali > MAX_GIORNATE_LAVORATIVE_ANNUALI) {

			flagException = true;
			msgException = MsgExceptionImpiegato.GIORNATE_NON_VALIDE;
		}

		if (flagException == true)
			throw new ExceptionImpiegato(msgException, new ExceptionImpiegato());

		else {

			this.stipendioMensile = stipendioMensile;
			this.giornateLavorativeAnnuali = giornateLavorativeAnnuali;

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void licenzia() throws ExceptionImpiegato {

		if (this.isAssunto == true) {// se l'impiegato non è gia licenziato

			this.isAssunto = false;
			this.stipendioMensile = 0;
			this.giornateLavorativeAnnuali = 0;

		} else {// non si puo licenziare un impiegato gia licenziato

			throw new ExceptionImpiegato(MsgExceptionImpiegato.IMPIEGATO_GIA_LICENZIATO, new ExceptionImpiegato());
		}
	}

	@Override
	public String toString() {

		return super.toString() + "\nid: " + this.getID() + "\nstipendio mensile: " + this.getStipendioMensile()
				+ " \ngiornate lavorative annuali " + this.getGiornateLavorativeAnnuali();
	}

}
