package persona;

import persona.exception.ExceptionAnagraficaErrata;
import persona.exception.ExceptionImpiegato;
import persona.exception.MsgExceptionImpiegato;
import utility.Data;

/**
 * 
 * classe per implementare l'interfaccia Impiegato
 * 
 * @author Francolino Flavio domenico
 *
 */
public class ImpiegatoGenerale extends AbstractPersona implements Impiegato, Cloneable {

	static final int MIN_GIORNATE_LAVORATIVE_ANNUALI = 144;// si suppone che ogni impiegato debba lavorare
																	// almeno 3 volte a settimana
	static final int MAX_GIORNATE_LAVORATIVE_ANNUALI = 365;
	static final int MIN_STIPENDIO_MENSILE = 1000;
	static final int MAX_STIPENDIO_MENSILE = 2000;

	private int id = -1;
	private int giornateLavorativeAnnuali;
	private float stipendioMensile;
	private boolean isLicenziato = false;// alla crazione di un istanza Impiegato si presuppone che sia anche stato
										// assunto
	
	/**
	 * costruttore da usare qundo devo istanziare in impiegato che prende i valori da un altro impiegato
	 */
	public ImpiegatoGenerale() {
		
		super();
	};

	/**
	 * costruttore di classe da usare per istanziare dipendenti con dati presi dal db
	 * 
	 * @param nome                      nome dell'impiegato
	 * @param cognome                   cognome dell'impiegato
	 * @param sesso                     sesso dell'impiegato
	 * @param dataNascita               data nascita dell'impiegato
	 * @param id                        id dell'impiegato
	 * @param giornateLavorativeAnnuali giornate lavorative annuali dell'impiegato
	 * @param stipendioMensile          stipendio mensile dell'impiegato
	 * @param isLicenziato				attributo  che segnala se � assunto o meno
	 * @throws ExceptionImpiegato        sollevata per errori riguardanti i valori
	 *                                   di attributi propri di impiegato
	 * @throws ExceptionAnagraficaErrata sollevata per errori riguardatni i valori
	 *                                   di attributo propri di AbstractPersona
	 */
	public ImpiegatoGenerale(String nome, String cognome, char sesso, Data dataNascita, int id,
			int giornateLavorativeAnnuali, float stipendioMensile, boolean isLicenziato)
			throws ExceptionImpiegato, ExceptionAnagraficaErrata {

		super(nome, cognome, sesso, dataNascita);

		boolean flagException = false;// flag per segnalare il sollevamento di un eccezione
		String msgException = new String();// messaggio di eccezzione

		if ((stipendioMensile < MIN_STIPENDIO_MENSILE) || (stipendioMensile > MAX_STIPENDIO_MENSILE)) {
			// lo stipedendio non rispetta le soglie minime e massime

			flagException = true;
			msgException = MsgExceptionImpiegato.STIPENDIO_NON_VALIDO;

		}
		if ((giornateLavorativeAnnuali < MIN_GIORNATE_LAVORATIVE_ANNUALI) || (giornateLavorativeAnnuali > MAX_GIORNATE_LAVORATIVE_ANNUALI)) {
			// giornate minimi o massimo non rispettate

			flagException = true;
			msgException = MsgExceptionImpiegato.GIORNATE_NON_VALIDE;
		}

		if (flagException == true)// ho rilevato un errore quindi sollevo un eccezione

			throw new ExceptionImpiegato(msgException, new ExceptionImpiegato());

		else {// nessun errore rilevato

			this.id = id;
			this.giornateLavorativeAnnuali = giornateLavorativeAnnuali;
			this.stipendioMensile = stipendioMensile;
			this.isLicenziato = isLicenziato;

		}

	}
	
	
	/**
	 * costruttore di classe da usare se si fa inserire un nuovo dipendente all'utente
	 * poiche il campo id verra' assegnato automaticamente dal software e l'attributo isAssunto avra'� dei volori di defoult
	 * 
	 * @param nome                      nome dell'impiegato
	 * @param cognome                   cognome dell'impiegato
	 * @param sesso                     sesso dell'impiegato
	 * @param dataNascita               data nascita dell'impiegato
	 * @param giornateLavorativeAnnuali giornate lavorative annuali dell'impiegato
	 * @param stipendioMensile          stipendio mensile dell'impiegato
	 * @throws ExceptionImpiegato        sollevata per errori riguardanti i valori
	 *                                   di attributi propri di impiegato
	 * @throws ExceptionAnagraficaErrata sollevata per errori riguardatni i valori
	 *                                   di attributo propri di AbstractPersona
	 */
	public ImpiegatoGenerale(String nome, String cognome, char sesso, Data dataNascita,
			int giornateLavorativeAnnuali, float stipendioMensile)
			throws ExceptionImpiegato, ExceptionAnagraficaErrata {

		super(nome, cognome, sesso, dataNascita);

		boolean flagException = false;// flag per segnalare il sollevamento di un eccezione
		String msgException = new String();// messaggio di eccezzione

		if ((stipendioMensile < MIN_STIPENDIO_MENSILE) || (stipendioMensile > MAX_STIPENDIO_MENSILE)) {
			// lo stipedendio non rispetta le soglie minime e massime

			flagException = true;
			msgException = MsgExceptionImpiegato.STIPENDIO_NON_VALIDO;

		}
		if ((giornateLavorativeAnnuali < MIN_GIORNATE_LAVORATIVE_ANNUALI) || (giornateLavorativeAnnuali > MAX_GIORNATE_LAVORATIVE_ANNUALI)) {
			// giornate minimi o massimo non rispettate

			flagException = true;
			msgException = MsgExceptionImpiegato.GIORNATE_NON_VALIDE;
		}

		if (flagException == true)// ho rilevato un errore quindi sollevo un eccezione

			throw new ExceptionImpiegato(msgException, new ExceptionImpiegato());

		else {// nessun errore rilevato

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
	 * {@inheritDoc}
	 */
	@Override
	public void promuovi(float stipendioMensile, int giornateLavorativeAnnuali) throws ExceptionImpiegato {

		boolean flagException = false;// flag per segnalare il verificarsi di un eccezzione
		String msgException = new String();

		if (this.isLicenziato == true) {// l'impiegato risulta esere licenziato

			flagException = true;
			msgException = MsgExceptionImpiegato.IMPIEGATO_LICENZIATO;

		} else if (stipendioMensile <= this.stipendioMensile) {// il nuovo stipendio e' minore uguale dello stipendio
																// attuale

			flagException = true;
			msgException = MsgExceptionImpiegato.STIPENDIO_PROMOZIONE_NON_VALIDO;

		} else if (stipendioMensile > MAX_STIPENDIO_MENSILE) {// nuovo stipendio minore dell'attuale

			flagException = true;
			msgException = MsgExceptionImpiegato.STIPENDIO_NON_VALIDO;

		} else if (giornateLavorativeAnnuali < this.giornateLavorativeAnnuali) {// giornate lavorative minori delle
																				// attuali

			flagException = true;
			msgException = MsgExceptionImpiegato.GIORNATE_PROMOZIONE_NON_VALIDO;

		} else if (giornateLavorativeAnnuali > MAX_GIORNATE_LAVORATIVE_ANNUALI) {// giornate lavorative superiori ai
																					// giorni di un anno

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

		if (this.isLicenziato == true) {// se l'impiegato risulta gia' licenziato
	
			throw new ExceptionImpiegato(MsgExceptionImpiegato.IMPIEGATO_GIA_LICENZIATO, new ExceptionImpiegato());
			
		} else {
			
			this.isLicenziato = true;
			
		}
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean getIsLicenziato() {
		
		return this.isLicenziato;
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	public void setID(int id) throws ExceptionImpiegato {
		
		
		if(this.id >= 0)

			throw new ExceptionImpiegato(MsgExceptionImpiegato.ID_GIA_ASSEGNATO, new ExceptionImpiegato());
		
		else if (id < 0)
			
			throw new ExceptionImpiegato(MsgExceptionImpiegato.ID_NON_VALIDO, new ExceptionImpiegato());
		
		else
			
			this.id = id;
			
		
	}

	/**
	 * @inheritDoc}
	 */
	@Override
	public String toString() {

		return super.toString() + "\nID: " + this.getID() + "\nStipendio mensile: " + this.getStipendioMensile()
				+ " \nGiornate lavorative annuali: " + this.getGiornateLavorativeAnnuali();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;

		result = prime * result * this.id;

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {

		boolean ret;// valore di ritorno

		if (this == obj) {

			ret = true;
		}

		if (obj == null) {

			ret = false;
		}

		// confronto in base all'id del dipendente

		ImpiegatoGenerale i = (ImpiegatoGenerale) obj;

		if (i.getID() == this.id) {

			ret = true;
		} else {

			ret = false;
		}

		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clone() {

		Object o = null;

		try {

			o = super.clone();
		} catch (CloneNotSupportedException e) {

			System.err.println("impossibile clonare questo elemento");
		}

		return o;
	}
	
}
