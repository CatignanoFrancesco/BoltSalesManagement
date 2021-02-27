package databaseSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


/**
 * @author GiannettaGerardo
 * 
 * Classe che crea in modo automatizzato il database dei bulloni; sarà utilizzata dalla classe Connessione.
 * Inoltre verranno anche riempite in modo automatizzato le tabelle dei bulloni e degli impiegati
 * per effettuare test più rapidi 
 */
class DBCreazioneAutomatica {

	/**
	 * Il costruttore di DBCreazioneAutomatica rimane privato
	 */
	private DBCreazioneAutomatica() {}
	
	
	public static void eseguiDBCreazioneAutomatica(Connection conn, String url, String user, String pass, String dbName, String timeZone) throws SQLException {
		
		// crea il database e lo usa per le operazioni successive
		createAndUseDB(conn, url, user, pass, dbName, timeZone);
		
		// crea la tabella Impiegato nel db e la riempie con 3 tuple standard per test
		createAndInsertImpiegato(conn);
		
		// crea le tabelle Bullone e Bullone_grano nel db e le riempie con 3 tuple standard per test
		createAndInsertBulloneGrano(conn);
		
		// crea la tabella Vendita nel db
		createVendita(conn);
		
		// crea la tabella MerceVenduta nel db
		createMerceVenduta(conn);
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
	private static void createAndUseDB(Connection conn, String url, String user, String pass, String dbName, String timeZone) throws SQLException {
		
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
	private static void createAndInsertImpiegato(Connection conn) throws SQLException {
		
		PreparedStatement pst = conn.prepareStatement("create table Impiegato ( " +
		                                              "matricola int not null primary key, " +
				                                      "nome char(20) not null, " +
		                                              "cognome char(20) not null, " +
				                                      "dataNascita date not null, " +
		                                              "sesso enum('M', 'F', 'NB') not null, " +
				                                      "stipendioMensile float not null, " +
		                                              "bulloniVendibiliAnnualmente int not null, " +
				                                      "giornateLavorativeAnnuali int not null, " +
		                                              "eliminato enum('T', 'F') not null default 'F' )");
		pst.executeUpdate();
		
		pst = conn.prepareStatement("insert into Impiegato values (0, 'Gerardo', 'Giannetta', '2000-05-19', 'M', 1500, 0, 190, 'F'), " +
		                                                         "(1, 'Francesco', 'Catignano', '2000-02-03', 'M', 1400, 0, 230, 'F'), " +
				                                                 "(2, 'Flavio', 'Francolino', '2000-11-23', 'M', 1700, 0, 300, 'F')");
		pst.executeUpdate();
		
		pst = conn.prepareStatement("update Impiegato set bulloniVendibiliAnnualmente = 500 * giornateLavorativeAnnuali");
		pst.executeUpdate();
	}
	
	
	/**
	 * Metodo che crea le tabelle Bullone e Bullone_grano e le riempie con 3 tuple per effettuare test
	 * 
	 * @param conn connessione al database
	 * @throws SQLException
	 * @throws SQLTimeoutException
	 */
	private static void createAndInsertBulloneGrano(Connection conn) throws SQLException {
		
		PreparedStatement pst = conn.prepareStatement("create table Bullone ( " +
		                                              "codice int not null primary key, " +
				                                      "dataProduzione date not null, " +
		                                              "luogoProduzione varchar(50) not null, " +
				                                      "peso float not null, " +
		                                              "lunghezza float not null, " +
				                                      "diametroVite float not null, " +
		                                              "innesto enum('taglio', 'esagonale', 'croce', 'torx') not null, " +
				                                      "materiale enum('acciaio', 'acciaio_inox', 'ottone', 'bronzo', 'titanio', 'nylon', 'vetro_porcellana') not null, " +
		                                              "eliminato enum('T', 'F') not null default 'F' )");
		pst.executeUpdate();
		
		pst = conn.prepareStatement("create table Bullone_grano ( " + 
		                            "codice int not null primary key, " + 
				                    "constraint codice_fk_grano foreign key (codice) " + 
		                            "references Bullone(codice) on delete cascade on update cascade )");
		pst.executeUpdate();
		
		pst = conn.prepareStatement("insert into Bullone values (0, '2012-05-30', 'Genova', 0.07, 2, 0.5, 'esagonale', 'acciaio', 'F'), " +
		                                                       "(1, '2019-02-28', 'Bari', 0.1, 3, 1, 'croce', 'bronzo', 'F'), " +
				                                               "(2, '2018-09-09', 'Bari', 0.1, 3, 1, 'torx', 'titanio', 'F')");
		pst.executeUpdate();
		
		pst = conn.prepareStatement("insert into Bullone_grano values (0), (1), (2)");
		pst.executeUpdate();
	}
	
	
	/**
	 * Metodo che crea la tabella Vendita nel database
	 * 
	 * @param conn connessione al database
	 * @throws SQLException
	 * @throws SQLTimeoutException
	 */
	private static void createVendita(Connection conn) throws SQLException {
		
		PreparedStatement pst = conn.prepareStatement("create table Vendita ( " +
		                                              "codVendita int not null primary key, " +
				                                      "impiegato int not null, " +
		                                              "data date not null, " +
				                                      "prezzoVenditaTotale float not null, " +
		                                              "numeroBulloniTotali int not null, " +
				                                      "constraint impiegato_fk_vendita foreign key (impiegato) " +
		                                              "references Impiegato(matricola) on delete restrict on update restrict )");
		pst.executeUpdate();
	}
	
	
	/**
	 * Metodo che crea la tabella MerceVenduta nel database
	 * 
	 * @param conn connessione al database
	 * @throws SQLException
	 * @throws SQLTimeoutException
	 */
	private static void createMerceVenduta(Connection conn) throws SQLException {
	
		PreparedStatement pst = conn.prepareStatement("create table MerceVenduta ( " +
		                                              "codVendita int not null, " +
				                                      "bullone int not null, " +
		                                              "numeroBulloni int not null, " +
				                                      "prezzoBulloni float not null, " +
		                                              "prezzoVenditaBullone float not null, " +
				                                      "primary key (codVendita, bullone), " +
		                                              "constraint codVendita_fk_merce foreign key (codVendita) " +
				                                      "references Vendita(codVendita) on delete cascade on update cascade, " +
		                                              "constraint grano_fk_merce foreign key (bullone) " +
				                                      "references Bullone_grano(codice) on delete restrict on update restrict )");
		pst.executeUpdate();
	}
}
