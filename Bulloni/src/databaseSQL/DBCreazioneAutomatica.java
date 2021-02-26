package databaseSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.PreparedStatement;


/**
 * @author GiannettaGerardo
 * 
 * Classe che crea in modo automatizzato il database dei bulloni, utilizzando la classe Connessione.
 * Inoltre verranno riempite in modo automatizzato anche le tabelle dei bulloni e degli impiegati
 * per effettuare test più rapidi 
 */
class DBCreazioneAutomatica {

	/**
	 * Il costruttore di DBCreazioneAutomatica rimane privato
	 */
	private DBCreazioneAutomatica() {}
	
	
	public static void eseguiDBCreazioneAutomatica(Connection conn, String url, String user, String pass, String dbName, String timeZone) throws SQLException, SQLTimeoutException {
		
		// crea il database e lo usa per le operazioni successive
		createAndUseDB(conn, url, user, pass, dbName, timeZone);
		
		// crea la tabella Impiegato e la riempie con 3 tuple standard per i test
		createAndInsertImpiegato(conn);
	}
	
	
	/**
	 * Metodo che crea automaticamente un database completo per il caso di studio bulloni,
	 * inoltre riempie le tabelle Impiegato e Bullone con 3-4 tuple per facilitare i test.
	 * Questo metodo sfrutta tutti gli altri metodi statici di questa classe per effettuare
	 * le creazioni e gli inserimenti
	 * 
	 * @param conn connessione al database
	 * @param url url del server
	 * @param user username per connettersi al server
	 * @param pass password per connettersi al server
	 * @param dbName nome del database per connettersi
	 * @param timeZone evita errori di connessione dovuti al timezone
	 * @throws SQLException
	 * @throws SQLTimeoutException
	 */
	private static void createAndUseDB(Connection conn, String url, String user, String pass, String dbName, String timeZone) throws SQLException, SQLTimeoutException {
		
		conn = DriverManager.getConnection(url, user, pass);
		
		PreparedStatement pst = conn.prepareStatement("create database " + dbName);
		pst.executeUpdate();
		
		conn.close();
		
		conn = DriverManager.getConnection(url + "/" + dbName + timeZone, user, pass);
		
	}
	
	
	/**
	 * Metodo che crea la tabella Impiegato e la riempie con 3 tuple per effettuare test
	 * 
	 * @param conn connessione al database
	 * @throws SQLException
	 * @throws SQLTimeoutException
	 */
	private static void createAndInsertImpiegato(Connection conn) throws SQLException, SQLTimeoutException {
		
		PreparedStatement pst = conn.prepareStatement("create table Impiegato ( " +
		                                              "matricola int not null primary key, " +
				                                      "nome char(20) not null, " +
		                                              "cognome char(20) not null, " +
				                                      "dataNascita date not null, " +
		                                              "sesso enum(M, F, NB) not null, " +
				                                      "stipendioMensile float not null, " +
		                                              "bulloniVendibiliAnnualmente int not null, " +
				                                      "giornateLavorativeAnnuali int not null, " +
		                                              "eliminato enum(T, F) not null default F )");
		pst.executeUpdate();
		
		pst = conn.prepareStatement("insert into Impiegato values (0, 'Gerardo', 'Giannetta', '2000-05-19', 'M', 1500, 0, 190, 'F'), " +
		                                                         "(1, 'Francesco', 'Catignano', '2000-02-03', 'M', 1400, 0, 230, 'F'), " +
				                                                 "(2, 'Flavio', 'Francolino', '2000-11-23', 'M', 1700, 0, 300, 'F')");
		pst.executeUpdate();
		
		pst = conn.prepareStatement("update Impiegato set bulloniVendibiliAnnualmente = 500 * giornateLavorativeAnnuali");
		pst.executeUpdate();
	}
}
