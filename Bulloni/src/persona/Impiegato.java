package persona;

import persona.exception.ExceptionImpiegato;

/**
 * 
 * interfaccia contenente le principali oprazione comuni ad un impiegato
 * di qualunque tipo
 * 
 * @author Francolino Flavio domenico
 *
 */
public interface Impiegato extends Persona {

	/**
	 * ritorna l'id di un impiegato
	 * 
	 * @return id il valore dell'id dell'impiegato
	 */
	public int getID();

	/**
	 * ritorna lo stipendio mensile di un impiegato
	 * 
	 * 
	 * @return stipendioMensile lo stipendio dell'impiegato
	 *         
	 */
	public float getStipendioMensile();

	/**
	 * ritorna le giornate lavorative annuali di un impiegato
	 * 
	 * @return giornateLavorativeAnnuali le giornate annuali dell'impiegato
	 */
	public int getGiornateLavorativeAnnuali();

	/**
	 * permette l'aggiornamento degli attributi stipendioMensile e
	 * giornateLavorativeAnnuali di un impiegato
	 * 
	 * @param stipendioMensile          il nuovo stipednio da assegnare all'impiegato
	 * @param giornateLavorativeAnnuali le nuove giornate da assegnare all'impiegato
	 * @throws ExceptionImpiegato sollevata se il nuovo stipendio e' minore uguale
	 *                            dell' attuale o se le nuove giornate sono minori
	 *                            delle attuali
	 */
	public void promuovi(float stipendioMensile, int giornateLavorativeAnnuali) throws ExceptionImpiegato;

	/**
	 * setta il valore dell'attributo isAssunto su false
	 * 
	 * @throws ExceptionImpiegato se un impiegato risulta gia licenziato
	 */
	public void licenzia() throws ExceptionImpiegato;
	
	/**
	 * setta il valore dell'id di un determinato impiegato
	 * 
	 * @param id il valore da assegnare all'id del impiegato
	 * @exception ExceptionImpiegato sollevata se il determinato impiegato ha gia un id
	 */
	public void setID(int id) throws ExceptionImpiegato;
	
	/**
	 * permette di vedere se un impiegato risulta assunto o meno
	 * 
	 * @return ret true se l'impiegato e' assunto, false altrimenti
	 */
	public boolean getIsLicenziato();
	
	/**
	 * converte un istanza di un impiegato in stringa
	 * @return l'oggetto convertito
	 */
	public String toString();
	
	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public int hashCode();
	
	/**
	 * {@inheritDoc}
	 * @param obj l'ggetto con cui confrontare
	 * @return true se l'oggetto e' uguale, false altrimenti
	 */
	@Override
	public boolean equals(Object obj);
	
	/**
	 * @return il clone dell'oggetto clonato
	 */
	Object clone();
	
	/**
	 * ritorna il valore della costante MAX_GIORNATE_LAVORATIVE_ANNUALI
	 * 
	 * @return il valore della costante MIN_GIORNATE_LAVORATIVE_ANNUALI
	 */
	public static int getMaxGiornateLavorativeAnnuali() {
		
		return ImpiegatoGenerale.MAX_GIORNATE_LAVORATIVE_ANNUALI;
	}
	
	/**
	 * ritorna il valore della costante MIN_GIORNATE_LAVORATIVE_ANNUALI
	 * 
	 * @return il valore della costante MIN_GIORNATE_LAVORATIVE_ANNUALI
	 */
	public static int getMinGiornateLavorativeAnnuali() {
		
		return ImpiegatoGenerale.MIN_GIORNATE_LAVORATIVE_ANNUALI;
	}
	
	/**
	 * ritorna il valore della costante MAX_STIPENDIO_MENSILE
	 * 
	 * @return il valore della costante MAX_STIPENDIO_MENSILE
	 */
	public static float getMaxStipendioMensile() {
		
		return ImpiegatoGenerale.MAX_STIPENDIO_MENSILE;
	}
	
	/**
	 * ritorna il valore della costante MIN_STIPENDIO_MENSILE
	 * 
	 * @return il valore della costante MIN_STIPENDIO_MENSILE
	 */
	public static float getMinStipendioMensile() {
		
		return ImpiegatoGenerale.MIN_STIPENDIO_MENSILE;
	}

}
