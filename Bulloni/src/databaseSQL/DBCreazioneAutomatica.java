package databaseSQL;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


/**
 * @author GiannettaGerardo
 * 
 * Classe che crea in modo automatizzato il database dei bulloni; sarà utilizzata dalla classe Connessione.
 * Inoltre verranno anche riempite in modo automatizzato le tabelle dei bulloni e degli impiegati
 * per effettuare test più rapidi.
 * Questa classe ha visibilità package perché non deve poter essere usata al di fuori di questo package,
 * essa serve solo per automattizzare il processo di creazione del db, non verrebbe mai usata in un progetto reale
 */
class DBCreazioneAutomatica {

	/**
	 * Il costruttore di DBCreazioneAutomatica rimane privato
	 */
	private DBCreazioneAutomatica() {}
	
	
	public static void eseguiDBCreazioneAutomatica(String url, String user, String pass, String dbName, String timeZone) throws SQLException {
		
		// crea il database e lo usa per le operazioni successive
		createAndUseDB(url, user, pass, dbName, timeZone);
		
		// crea la tabella Impiegato nel db e la riempie con 3 tuple standard per test
		createAndInsertImpiegato();
		
		// crea le tabelle Bullone e Bullone_grano nel db e le riempie con 3 tuple standard per test
		createAndInsertBulloneGrano();
		
		// crea la tabella Vendita nel db
		createVendita();
		
		// crea la tabella MerceVenduta nel db
		createMerceVenduta();
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
	private static void createAndUseDB(String url, String user, String pass, String dbName, String timeZone) throws SQLException {
		
		Connessione.connection = DriverManager.getConnection(url, user, pass);
		//conn = DriverManager.getConnection(url, user, pass);
		
		PreparedStatement pst = Connessione.connection.prepareStatement("create database " + dbName);
		pst.executeUpdate();
		
		Connessione.connection.close();
		
		Connessione.connection = DriverManager.getConnection(url + "/" + dbName + timeZone, user, pass);
		
	}
	
	
	/**
	 * Metodo che crea la tabella Impiegato e la riempie con 3 tuple per effettuare test
	 * 
	 * @param conn connessione al database
	 * @throws SQLException
	 * @throws SQLTimeoutException
	 */
	private static void createAndInsertImpiegato() throws SQLException {
		
		PreparedStatement pst = Connessione.connection.prepareStatement("create table Impiegato ( " +
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
		
		pst = Connessione.connection.prepareStatement("insert into Impiegato values (0, 'Gerardo', 'Giannetta', '2000-05-19', 'M', 1500, 0, 190, 'F'), " +
		                                                         "(1, 'Francesco', 'Catignano', '2000-02-03', 'M', 1400, 0, 230, 'F'), " +
				                                                 "(2, 'Flavio', 'Francolino', '2000-11-23', 'M', 1700, 0, 300, 'F')");
		pst.executeUpdate();
		
		pst = Connessione.connection.prepareStatement("update Impiegato set bulloniVendibiliAnnualmente = 500 * giornateLavorativeAnnuali");
		pst.executeUpdate();
	}
	
	
	/**
	 * Metodo che crea le tabelle Bullone e Bullone_grano e le riempie con 3 tuple per effettuare test
	 * 
	 * @param conn connessione al database
	 * @throws SQLException
	 * @throws SQLTimeoutException
	 */
	private static void createAndInsertBulloneGrano() throws SQLException {
		
		PreparedStatement pst = Connessione.connection.prepareStatement("create table Bullone ( " +
		                                              "codice int not null primary key, " +
				                                      "dataProduzione date not null, " +
		                                              "luogoProduzione varchar(50) not null, " +
				                                      "peso float not null, " +
		                                              "prezzo float not null, " +
		                                              "lunghezza float not null, " +
				                                      "diametroVite float not null, " +
		                                              "innesto enum('TAGLIO', 'ESAGONALE', 'CROCE', 'TORX') not null, " +
				                                      "materiale enum('ACCIAIO', 'ACCIAIO_INOX', 'OTTONE', 'BRONZO', 'TITANIO', 'NYLON', 'VETRO_PORCELLANA') not null, " +
		                                              "eliminato enum('T', 'F') not null default 'F' )");
		pst.executeUpdate();
		
		pst = Connessione.connection.prepareStatement("create table Bullone_grano ( " + 
		                            "codice int not null primary key, " + 
				                    "constraint codice_fk_grano foreign key (codice) " + 
		                            "references Bullone(codice) on delete cascade on update cascade )");
		pst.executeUpdate();
		
		pst = Connessione.connection.prepareStatement("insert into Bullone values (0, '2012-05-30', 'Genova', 0.07, 2, 0.5, 'ESAGONALE', 'ACCIAIO', 'F'), " +
		                                                       "(1, '2019-02-28', 'Bari', 0.1, 3, 1, 'CROCE', 'BRONZO', 'F'), " +
				                                               "(2, '2018-09-09', 'Bari', 0.1, 3, 1, 'TORX', 'TITANIO', 'F')");
		pst.executeUpdate();
		
		pst = Connessione.connection.prepareStatement("insert into Bullone_grano values (0), (1), (2)");
		pst.executeUpdate();
	}
	
	
	/**
	 * Metodo che crea la tabella Vendita nel database
	 * 
	 * @param conn connessione al database
	 * @throws SQLException
	 * @throws SQLTimeoutException
	 */
	private static void createVendita() throws SQLException {
		
		PreparedStatement pst = Connessione.connection.prepareStatement("create table Vendita ( " +
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
	private static void createMerceVenduta() throws SQLException {
	
		PreparedStatement pst = Connessione.connection.prepareStatement("create table MerceVenduta ( " +
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
