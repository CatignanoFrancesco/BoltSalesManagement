/**
 * 
 */
package persona;

import persona.exception.ExceptionImpiegato;
import persona.exception.MsgExceptionImpiegato;

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
	 * @return stipendioMensile lo stipendio dell'impiegato, uguale a 0 se questo ï¿½
	 *         licenziato
	 */
	public float getStipendioMensile();

	/**
	 * ritorna le giornate lavorative annuali di un impiegato
	 * 
	 * @return giornateLavorativeAnnuali le giornate annuali del dipendente,uguali a
	 *         0 se questo ï¿½ licenziato
	 */
	public int getGiornateLavorativeAnnuali();

	/**
	 * setta il valore dell'attributo isAssunto su true e imposta lo stipendio
	 * mensile e le giornate lavorative con i valori passatoli
	 * 
	 * @param stipendioMensile          lo stipendio con cu assumere un impiegato
	 * @param giornateLavorativeannuali le giornate lavorative con cui assumere un
	 *                                  impiegato
	 * @exception ExceptionImpiegato sollevata se un impiegato ï¿½ gia assunto o lo
	 *                               stipendio o le giornate lavorative non sono
	 *                               valide
	 */
	public void assumi(int stipendioMensile, int giornateLavorativeAnnuali) throws ExceptionImpiegato;

	/**
	 * permette l'aggiornamento degli attributi stipendioMensile e
	 * giornateLavorativeAnnuali di un impiegato
	 * 
	 * @param stipendioMensile          il nuovo stipednio da assegnare
	 * @param giornateLavorativeAnnuali le nuove giornate da assegnare
	 * @throws ExceptionImpiegato sollevata se il nuovo stipendio ï¿½ minore uguale
	 *                            dell' attuale o se le nuove giornate sono minori
	 *                            delle attuali
	 */
	public void promuovi(float stipendioMensile, int giornateLavorativeAnnuali) throws ExceptionImpiegato;

	/**
	 * setta il valore dell'attributo isAssunto su false e azzera gli attributi
	 * stipendioMensile e giornateLavorativeAnnuali
	 * 
	 * @throws ExceptionImpiegato se un impiegato ï¿½ gia licenziato
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
	public boolean getIsAssunto();
	
	public String toString();
	
	@Override
	public int hashCode();
	
	@Override
	public boolean equals(Object obj);
	
	Object clone();

}
