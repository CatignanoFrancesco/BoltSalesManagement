/**
 * 
 */
package persona;

import utility.Data;

/**
 * @author Francolino Flavio Domenico
 * 
 * implementazione dell'interfaccia Persona tramite la classe astratta persona
 * riutilizzabile per progetti futuri che comprendono oggetti di tipo persona
 *
 */
abstract class AbstractPersona implements Persona {
	
	private String nome, cognome;
	private char sesso;
	private Data dataNascita;
	
	public AbstractPersona(String nome, String cognome, char sesso, Data dataNascita) {
		
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.dataNascita = dataNascita;
	}

	@Override
	public String getNome() {
		
		return this.nome;
	}

	@Override
	public String getCognome() {

		return this.cognome;
	}

	@Override
	public Data getDataNascita() {
		
		return this.dataNascita;
	}

	@Override
	public char getSesso() {
		
		return this.sesso;
	}

	@Override
	public void setNome(String nome) {
		
		this.nome = nome;

	}

	@Override
	public void setCognome(String cognome) {
		
		this.cognome = cognome;

	}

	@Override
	public void setDataNascita(Data dataNascita) {
		
		this.dataNascita = dataNascita;

	}

	@Override
	public void setSesso(char sesso) {
		
		this.sesso = sesso;

	}
	
	@Override
	public String toString() {
		
		return "nome: " + this.getNome() + " \ncognome " + this.getCognome() + "\nsesso: " + this.getSesso() + "\ndata nascita: " + this.getDataNascita().toFormattedDate();
	}

}
