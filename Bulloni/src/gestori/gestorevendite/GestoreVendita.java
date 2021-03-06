package gestori.gestorevendite;

import java.util.Set;
import java.sql.Date;
import java.sql.ResultSet;
import utility.Data;
import vendita.*;
import vendita.exception.*;
import persona.Impiegato;
import databaseSQL.*;
import databaseSQL.exception.*;

/**
 * @author GiannettaGerardo
 *
 */
public class GestoreVendita {
	
	Set<Vendita<MerceVenduta, Impiegato>> vendite;
	private static int codVenditaAutomatico = 0;
	private static final String NOME_TABELLA_VENDITA = "Vendita";
	private static final String NOME_TABELLA_MERCE_VENDUTA = "MerceVenduta";

	
	public GestoreVendita() {
		// TODO Auto-generated constructor stub
	}

}
