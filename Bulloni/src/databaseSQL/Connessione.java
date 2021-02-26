package databaseSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


/**
 * @author GiannettaGerardo
 *
 */
public class Connessione {
	
	private final String DB_NOME = "Bulloni";
	private final String USERNAME;
	private final String PASSWORD;
	private final String HOST = "localhost";
	private final int PORTA = 3306;
	private final String TIMEZONE = "?serverTimezone=UTC";
	private final String URL = "jdbc:mysql://" + HOST + ":" + PORTA + "/" + DB_NOME + TIMEZONE;
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private Connection connection;
	
	

	private Connessione() {}

}
