package persona.exception;

/**
 * 
 * questa classe contine tutti i possibili messaggi usabili da classi
 * che sollevano eccezione ti tipo personalizzato rigurardanti un
 * angrafica errata di una persona
 *         
 * @author Francolino Flavio domenico
 *
 */
public class MsgExceptionAnagraficaErrata {
	
	public static final String NOME_NON_VALIDO = "Sollevata ExceptionAnagraficaErrata:\nformato del nome assegnato al dipendente non valido";
	
	public static final String COGNOME_NON_VALIDO = "Sollevata ExceptionAnagraficaErrata:\nformato del cognome assegnato al dipendente non valido";
	
	public static final String SESSO_NON_VALIDO = "Sollevata ExceptionAnagraficaErrata:\nsesso assegnato al dipendente non valido";
	
	public static final String DATA_NASCITA_FUTURA = "Sollevata ExceptionAnagraficaErrata:\ndata di nascita assegnata al dipendente futura";

}
