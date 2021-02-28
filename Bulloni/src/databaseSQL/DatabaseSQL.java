package databaseSQL;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author GiannettaGerardo
 * 
 * Classe che permette di eseguire su un database le principali operazioni:
 * SELECT, INSERT, UPDATE, DELETE
 */
public class DatabaseSQL {

	/**
	 * Il costruttore di Query rimane privato
	 */
	private DatabaseSQL() {}
	
	
	/**
	 * Metodo che restituisce una connessione utilizzando la classe Connessione.
	 * Sarà utilizzato solo all'interno dei principali metodi di questa classe
	 * 
	 * @return un oggetto Connection già creato e inizializzato
	 */
	private static Connection apriConnessione() {
		return Connessione.getConnection();
	}
	
	
	/**
	 * Metodo che chiude la connessione aperta dal metodo apriConnessione.
	 * Deve lavorare sullo stesso oggetto Connection di apriConnessione, anche mediante alias
	 */
	public static void chiudiConnessione() {
		Connessione.closeConnection();
	}

}
