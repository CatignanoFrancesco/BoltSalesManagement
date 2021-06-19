package persona;

import persona.exception.ExceptionAnagraficaErrata;
import utility.Data;

/**
 * 
 * operazioni comuni a tutti gli oggetti derivanti da persona
 * 
 *  @author Francolino Flavio domenico
 */
public interface Persona {

	/**
	 * restituisce il nome di una persona
	 * 
	 * @return nomePersona il nome della persona
	 */
	public String getNome();

	/**
	 * restituisce il cognome di una persona
	 * 
	 * @return cognomePersona il cognome della persona
	 */
	public String getCognome();

	/**
	 * restituisce la data di nascita di una persona
	 * 
	 * @return dataNascita la data di nascita della persona
	 */
	public Data getDataNascita();

	/**
	 * restituisce il nome di una persona
	 * 
	 * @return sesso il sesso della persona
	 */
	public char getSesso();

	/**
	 * setta nuovi valori per il nome di una persona
	 * 
	 * @param nome il nuovo nome da assegnare alla persona
	 * @throws ExceptionAnagraficaErrata sollevata in caso di nome non valido
	 */
	public void setNome(String nome) throws ExceptionAnagraficaErrata;

	/**
	 * setta nuovi valori per il cognome di una persona
	 * 
	 * @param cognome il nuovo cognome da assegnare alla persona
	 * @throws ExceptionAnagraficaErrata sollevata in caso di cognome non valido
	 */
	public void setCognome(String cognome) throws ExceptionAnagraficaErrata;

	/**
	 * setta nuovi valori per la data di nascita di una persona
	 * 
	 * @param dataNascita la nuova data di nascita della persona
	 * @throws ExceptionAnagraficaErrata sollevata in caso di nascita futura alla
	 *                                   data attuale
	 */
	public void setDataNascita(Data dataNascita) throws ExceptionAnagraficaErrata;

	/**
	 * setta nuovi valori per il sesso di una persona
	 * 
	 * @param sesso il nuovo sesso della persona
	 * @throws ExceptionAnagraficaErrata sollevata in caso di sesso non valido
	 */
	public void setSesso(char sesso) throws ExceptionAnagraficaErrata;

	/**
	 * converte un oggetto AbstractPersona in stringa
	 * @return un oggetto persona convertito in stringa
	 */
	@Override
	public String toString();

}
