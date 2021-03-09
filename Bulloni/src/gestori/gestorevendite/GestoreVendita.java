package gestori.gestorevendite;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

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

	
	public GestoreVendita(GestoreBulloni gb) {
		
		Map<Integer, Set<MerceVenduta>> merce = selectMerceVenduta(gb);
	}
	
	
	private Map<Integer, Set<MerceVenduta>> selectMerceVenduta(GestoreBulloni gb) {
		
		Set<Bullone> bulloni = gb.getAll();
		Map<Integer, Set<MerceVenduta>> merce = new HashMap<Integer, Set<MerceVenduta>>();
		
		try {
			ResultSet rs = DatabaseSQL.select(Query.getSimpleSelect(NOME_TABELLA_MERCE_VENDUTA));
			
			while(rs.next()) {
				
				try {
					Set<MerceVenduta> setMerce = merce.get(rs.getInt(CampiTabellaMerceVenduta.codVendita.toString()));
					
					if (setMerce == null) {
						
						setMerce = new HashSet<MerceVenduta>();
						
						setMerce.add(new MerceVenduta(gb.getBulloneByCodice(rs.getInt(CampiTabellaMerceVenduta.bullone.toString())), 
                                                      rs.getInt(CampiTabellaMerceVenduta.numeroBulloni.toString()), 
                                                      rs.getDouble(CampiTabellaMerceVenduta.prezzoBulloni.toString()), 
                                                      rs.getDouble(CampiTabellaMerceVenduta.prezzoVenditaBullone.toString())
                                                      ));
						
						merce.put(rs.getInt(CampiTabellaMerceVenduta.codVendita.toString()), setMerce);
					}
					else {
						setMerce.add(new MerceVenduta(gb.getBulloneByCodice(rs.getInt(CampiTabellaMerceVenduta.bullone.toString())), 
                                                      rs.getInt(CampiTabellaMerceVenduta.numeroBulloni.toString()), 
                                                      rs.getDouble(CampiTabellaMerceVenduta.prezzoBulloni.toString()), 
                                                      rs.getDouble(CampiTabellaMerceVenduta.prezzoVenditaBullone.toString())
                                                      ));
					}
				}
				catch (GestoreBulloniException e) {
					System.err.println(e.getMessage());
				}
				catch (VenditaException e) {
					System.err.println(e.getMessage());
				}
				
			}
			
			DatabaseSQL.chiudiConnessione();
			
		}
		catch (DatabaseSQLException e) {
			System.err.println(e.getMessage());
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return merce;
	}

}
