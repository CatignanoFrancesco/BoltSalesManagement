package gestori.gestorevendite;

import java.util.Set;
import java.util.HashSet;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import utility.Data;
import vendita.*;
import vendita.exception.*;
import persona.Impiegato;
import databaseSQL.*;
import databaseSQL.exception.*;
import gestori.gestoribulloni.*;
import gestori.gestoribulloni.exception.*;
import bulloni.Bullone;

/**
 * @author GiannettaGerardo
 *
 */
public class GestoreVendita {
	
	Set<Vendita<MerceVenduta, Impiegato>> vendite = new HashSet<Vendita<MerceVenduta, Impiegato>>();
	private static int codVenditaAutomatico = 0;
	private static final String NOME_TABELLA_VENDITA = "Vendita";
	private static final String NOME_TABELLA_MERCE_VENDUTA = "MerceVenduta";
	private static final String NOME_TABELLA_IMPIEGATO = "Impiegato";
	private static final String NOME_TABELLA_BULLONE = "Bullone";

	
	public GestoreVendita(GestoreBulloni gb) {
		
		selectVendite();
		selectMerceVenduta(gb);
	}
	
	
	private void selectMerceVenduta(GestoreBulloni gb) {
		
		Set<Bullone> bulloni = gb.getAll();
		Set<MerceVenduta> merce = new HashSet<MerceVenduta>();
		
		try {
			ResultSet rs = DatabaseSQL.select(Query.getSimpleSelect(NOME_TABELLA_MERCE_VENDUTA));
			
			while(rs.next()) {
				for (Bullone b : bulloni) {
					if (b.getCodice() == rs.getInt(2)) {
						
					}
				}
			}
		}
		catch (DatabaseSQLException e) {
			System.out.println(e.getMessage());
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	private void selectVendite() {
		
		try {
			ResultSet rs = DatabaseSQL.select(Query.getSimpleSelectEquiJoin (
					                           NOME_TABELLA_VENDITA, 
	                                           "Impiegato", 
	                                           CampiTabellaVendita.impiegato.toString(), 
	                                           "matricola")
					                           );
			
			while (rs.next()) {
				
			}
			
			DatabaseSQL.chiudiConnessione();
		}
		catch (DatabaseSQLException e) {
			System.out.println(e.getMessage());
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
