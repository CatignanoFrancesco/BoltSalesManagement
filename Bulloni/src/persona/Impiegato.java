/**
 * 
 */
package persona;

import persona.exception.ExceptionImpiegato;

/**
 * @author Francolino Flavio Domenico
 * 
 *         interfaccia contenente le principali oprazione comuni ad un impiegato
 *         di qualunque tipo
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
	 * @return stipendioMensile lo stipendio dell'impiegato, uguale a 0 se questo �
	 *         licenziato
	 */
	public float getStipendioMensile();

	/**
	 * ritorna le giornate lavorative annuali di un impiegato
	 * 
	 * @return giornateLavorativeAnnuali le giornate annuali del dipendente,uguali a
	 *         0 se questo � licenziato
	 */
	public int getGiornateLavorativeAnnuali();

	/**
	 * permette l'aggiornamento degli attributi stipendioMensile e
	 * giornateLavorativeAnnuali di un impiegato
	 * 
	 * @param stipendioMensile          il nuovo stipednio da assegnare
	 * @param giornateLavorativeAnnuali le nuove giornate da assegnare
	 * @throws ExceptionImpiegato sollevata se il nuovo stipendio � minore uguale
	 *                            dell' attuale o se le nuove giornate sono minori
	 *                            delle attuali
	 */
	public void promuovi(float stipendioMensile, int giornateLavorativeAnnuali) throws ExceptionImpiegato;

	/**
	 * setta il valore dell'attributo isAssunto su false e azzera gli attributi
	 * stipendioMensile e giornateLavorativeAnnuali
	 * 
	 * @throws ExceptionImpiegato se un impiegato � gia licenziato
	 */
	public void licenzia() throws ExceptionImpiegato;
	
	/**
	 * setta il valore dell'id di un determinato dipendente
	 * è usabile solo se il determinato dipendeten non ha gia un id altrimenti solleva un eccezione
	 * 
	 * @param id il valore da assegnare all'id del dipendente
	 * @exception ExceptionImpiegato sollevate se il determinato dipendente ha gia un id
	 */
	public void setID(int id) throws ExceptionImpiegato;
	
	/**
	 * permmette di vedere se un impiegato risulta assunto o meno
	 * 
	 * @return ret true se l'impiegato è assunto,false altrimenti
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
	 * @return true se l'oggetto è uguale, false altrimenti
	 */
	@Override
	public boolean equals(Object obj);
	
	/**
	 * @return il clone dell'oggetto clonato
	 */
	Object clone();

}
