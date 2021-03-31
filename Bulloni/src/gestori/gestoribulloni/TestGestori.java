package gestori.gestoribulloni;

import java.sql.SQLException;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoribulloni.exception.GestoreBulloniException;

public class TestGestori {
	public static void main(String args[]) {
		// test del resultset vuoto
		try {
			GestoreBulloni gb = new GestoreBulloni();
			System.out.println(gb.isEmpty());
		}
		catch(DatabaseSQLException e) {
			System.out.println(e.getMessage());
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
 }
