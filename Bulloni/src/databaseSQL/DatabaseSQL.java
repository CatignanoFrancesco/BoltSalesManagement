package databaseSQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	
	
	
	public static ResultSet select(String query) throws SQLException {
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		return rs;
	}
	
	
	
	public static void insert(String query) throws SQLException {
		
		Connection conn = apriConnessione();
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		chiudiConnessione();
	}

}
