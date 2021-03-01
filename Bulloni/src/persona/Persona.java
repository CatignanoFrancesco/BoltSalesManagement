package persona;

import utility.Data;
/**
 * @author Francolino Flavio Domenico
 * 
 *
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
	 */
	public void setNome(String nome);
	
	/**
	 * setta nuovi valori per il cognome di una persona
	 * 
	 * @param cognome il nuovo cognome da assegnare alla persona
	 */
	public void setCognome(String cognome);
	
	/**
	 * setta nuovi valori per la data di nascita di una persona
	 * 
	 * @param dataNascita la nuova data di nascita della persona
	 */
	public void setDataNascita(Data dataNascita);
	
	/**
	 * setta nuovi valori per il sesso di una persona
	 * 
	 * @param sesso il nuovo sesso della persona
	 */
	public void setSesso(char sesso);
	
	
	String toString();

}
