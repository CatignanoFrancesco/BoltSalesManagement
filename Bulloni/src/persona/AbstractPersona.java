/**
 * 
 */
package persona;

import java.io.ObjectInputStream.GetField;
import java.time.LocalDate;
import java.util.regex.Pattern;

import persona.exception.ExceptionAnagraficaErrata;
import persona.exception.MsgExceptionAnagraficaErrata;
import utility.Data;

/**
 * @author Francolino Flavio Domenico
 * 
 *         implementazione dell'interfaccia Persona tramite la classe astratta
 *         persona riutilizzabile per progetti futuri che comprendono oggetti di
 *         tipo persona
 *
 */
abstract class AbstractPersona implements Persona {

	private String nome, cognome;
	private char sesso;
	private Data dataNascita;

	private static final String regExpNameOrSurname = "^[a-zA-z]+";// espressione regolare per validare il nome o il
																	// cognome senza fare distinzione fra minuscolo e
																	// maiuscolo

	/**
	 * costruttore
	 * 
	 * @param nome        nome da assegnare alla persona
	 * @param cognome     cognome da assegnare alla persona
	 * @param sesso       sesso da assegnare alla persona
	 * @param dataNascita dataNascita da assegnare alla persona
	 * @throws ExceptionAnagraficaErrata sollevata per dati errati su attributi anagrafici
	 */
	public AbstractPersona(String nome, String cognome, char sesso, Data dataNascita) throws ExceptionAnagraficaErrata {

		boolean flagEccezione = false;// flag per segnalare se si verifica un eccezzione riguardante l'anagrafica
		String msgExcpetion = new String();

		if (!Pattern.matches(regExpNameOrSurname, nome)) {//nome non valido

			flagEccezione = true;
			msgExcpetion = MsgExceptionAnagraficaErrata.NOME_NON_VALIDO;
			
		} else if (!Pattern.matches(regExpNameOrSurname, cognome)) {//cognome non valido

			flagEccezione = true;
			msgExcpetion = MsgExceptionAnagraficaErrata.COGNOME_NON_VALIDO;

		} else if (sesso != 'm' && sesso != 'M' && sesso != 'f' && sesso != 'F') {//sesso non valido

			flagEccezione = true;
			msgExcpetion = MsgExceptionAnagraficaErrata.SESSO_NON_VALIDO;

		} else if (dataNascita.compareTo(Data.getDataAttuale()) > 0) {//dataNascita non valida

			flagEccezione = true;
			msgExcpetion = MsgExceptionAnagraficaErrata.DATA_NASCITA_FUTURA;
		}

		if (flagEccezione != true) {//non rilevato errori quindi potenziali eccezzioni

			this.nome = nome;
			this.cognome = cognome;
			this.sesso = sesso;
			this.dataNascita = dataNascita;
			
		} else {
			
			throw new ExceptionAnagraficaErrata(msgExcpetion, new ExceptionAnagraficaErrata());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNome() {

		return this.nome;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCognome() {

		return this.cognome;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data getDataNascita() {

		return this.dataNascita;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public char getSesso() {

		return this.sesso;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNome(String nome) throws ExceptionAnagraficaErrata {

		if (!Pattern.matches(regExpNameOrSurname, nome))//nome non valido

			throw new ExceptionAnagraficaErrata(MsgExceptionAnagraficaErrata.NOME_NON_VALIDO, new ExceptionAnagraficaErrata());
		else

			this.nome = nome;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCognome(String cognome) throws ExceptionAnagraficaErrata {

		if (!Pattern.matches(regExpNameOrSurname, cognome))//cognome non valido

			throw new ExceptionAnagraficaErrata(MsgExceptionAnagraficaErrata.COGNOME_NON_VALIDO, new ExceptionAnagraficaErrata());

		else

			this.cognome = cognome;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDataNascita(Data dataNascita) throws ExceptionAnagraficaErrata {

		if (dataNascita.compareTo(Data.getDataAttuale()) > 0) {//dataNascita non valida

			throw new ExceptionAnagraficaErrata(MsgExceptionAnagraficaErrata.DATA_NASCITA_FUTURA, new ExceptionAnagraficaErrata());

		} else {

			this.dataNascita = dataNascita;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSesso(char sesso) throws ExceptionAnagraficaErrata {

		if (sesso != 'm' && sesso != 'M' && sesso != 'f' && sesso != 'F')//sesso non valido

			throw new ExceptionAnagraficaErrata(MsgExceptionAnagraficaErrata.SESSO_NON_VALIDO, new ExceptionAnagraficaErrata());

		else
			
			this.sesso = sesso;

	}

	public String toString() {

		return "Nome: " + this.getNome() + " \nCognome: " + this.getCognome() + "\nSesso: " + this.getSesso()
				+ "\nData nascita: " + this.getDataNascita().toFormattedDate();
	}

}
