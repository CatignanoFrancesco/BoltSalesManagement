package gestori.gestoribulloni;

import java.util.Set;
import java.util.HashSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;

import utility.Data;
/*
 * Classi e interfacce per i bulloni e le eccezioni
 */
import bulloni.Bullone;
import bulloni.BulloneGrano;
import bulloni.Materiale;
import bulloni.Innesto;
import bulloni.exception.BulloneException;
import gestori.gestoribulloni.exception.GestoreBulloniException;
import gestori.gestoribulloni.exception.MsgErrore;
/*
 * Classi per gestire il database e le sue eccezioni (effettuare query, insert, update ecc...) e per creare le query da far eseguire al DBMS.
 */
import databaseSQL.DatabaseSQL;
import databaseSQL.Query;
import databaseSQL.exception.DatabaseSQLException;


/**
 * Classe per la gestione dei bulloni di qualsiasi tipo.
 * All'interno ci saranno tutti i metodi per gestire un set di bulloni (di qualsiasi tipo) e per farlo interfacciare con il database SQL,
 * inserendo i dati all'interno delle tabelle o selezionarli. Sarà anche possibile modificare gli attributi dei bulloni e sincronizzare
 * le modifiche con il database.
 *  
 * @author Catignano Francesco
 *
 */
public class GestoreBulloni implements ContainerBulloni {
	private Set<Bullone> bulloni = new HashSet<Bullone>();	// Set di bulloni
	private static int codBulloneAutomatico = 0;	// Contiene un codice di un bullone non presente tra i codici dei bulloni presenti nel set. Questo servira' per tutti i casi in cui il codice del bullone deve essere creato dal gestore.
	private static final String NOME_TABELLA_BULLONI = "Bullone";	// Nome della tabella generica dei bulloni per eseguire le insert, le query e le modifiche.
	private static final String NOME_TABELLA_BULLONE_GRANO = "Bullone_grano";	// Nome della tabella specifica per eseguire le insert, le query e le modifiche.
	
	
	// COSTRUTTORE
	/**
	 * Costruisce un gestore per i bulloni.
	 * Prende singolarmente i dati dal database e costruisce un oggetto specifico di tipo Bullone a partire dal
	 * risultato della query. Successivamente aggiunge l'oggetto costruito all'interno del set di bulloni.
	 */
	public GestoreBulloni() throws DatabaseSQLException, SQLException {
		/*
		 * Se il set non e' vuoto, lo pulisce prima del riempimento.
		 * In questo modo sicuramente verra' riempito con i dati aggiornati presenti all'interno della base di dati. 
		 */
		if(!bulloni.isEmpty()) {
			bulloni.clear();
		}
		
		/*
		 * Riempimento del set di bulloni di tipo grano, prendendo i dati dal database.
		 */
		// Creazione della query ed esecuzione della select
		ResultSet rs = DatabaseSQL.select(Query.getSimpleSelectEquiJoin(NOME_TABELLA_BULLONI, NOME_TABELLA_BULLONE_GRANO, CampiTabellaBullone.codice.toString(), CampiTabellaBulloneGrano.codice.toString()));
		while(rs.next()) {
			bulloni.add(costruisciBulloneGrano(rs));
		}
		DatabaseSQL.chiudiConnessione();	// Chiusura della connessione al db (l'apertura è fatta automaticamente al momento della chiamata ad una select
		// Viene assegnato all'attributo codBulloneAutomatico un valore non esistente nel set di bulloni.
		codBulloneAutomatico = this.getMaxCodiceBullone() + 1;
	}
	
	
	
	/*
	 * ------------------------------------------
	 * 				METODI PUBBLICI
	 * ------------------------------------------
	 */
	/**{@inheritDoc}
	 *
	 */
	public void newBulloneGrano(Bullone b) throws GestoreBulloniException, BulloneException, DatabaseSQLException, SQLException {
		if(b!=null) {
			
			// Se il bullone esiste gia', ne viene cambiato il codice e viene inserito nel db
			if(bulloni.add(b) == false) {
				b = new BulloneGrano(codBulloneAutomatico, b.getDataProduzione(), b.getLuogoProduzione(), b.getPeso(), b.getPrezzo(), b.getMateriale(), b.getLunghezza(), b.getDiametroDado(), b.getInnesto());
				bulloni.add(b);
				codBulloneAutomatico++;
			} else {
				codBulloneAutomatico = this.getMaxCodiceBullone() + 1;	// Aggiornato nuovamente
			}
			
			// Valori del bullone da inserire nel database
			String[] valoriTabellaBullone = { ((Integer)b.getCodice()).toString(), b.getDataProduzione().toSqlDate().toString(), b.getLuogoProduzione(), ((Double)b.getPeso()).toString(), ((Double)b.getPrezzo()).toString(), ((Double)b.getLunghezza()).toString(), ((Double)b.getDiametroVite()).toString(), b.getInnesto().toString(), b.getMateriale().toString(), (b.isEliminato()==true) ? "T" : "F" };
			String[] valoriTabellaBulloneGrano = { ((Integer)b.getCodice()).toString() };
			
			/*
			 * Inserimento nel database
			 */
			// Inserimento nella tabella generale Bullone
			DatabaseSQL.insert(Query.getSimpleInsert(NOME_TABELLA_BULLONI, valoriTabellaBullone));
			// Inserimento nella tabella specifica Bullone_grano
			DatabaseSQL.insert(Query.getSimpleInsert(NOME_TABELLA_BULLONE_GRANO, valoriTabellaBulloneGrano));
			
		} else {
			throw new GestoreBulloniException(MsgErrore.BULLONE_NULLO, new GestoreBulloniException());
		}
	}
	
	
	/**{@inheritDoc}
	 *
	 */
	public Set<Bullone> getAll() {
		Set<Bullone> bulloniCopy = new HashSet<Bullone>();	// La copia del set di bulloni
		
		// Riempimento bulloniCopy
		for(Bullone b : this.bulloni) {
			bulloniCopy.add((Bullone)b.clone());
		}
		
		return bulloniCopy;
	}
	
	
	/**{@inheritDoc}
	 * 
	 */
	public Bullone getBulloneByCodice(int codice) throws GestoreBulloniException {
		boolean trovato = false;
		
		for(Bullone b : this.bulloni) {
			if(b.getCodice() == codice) {
				trovato = true;
				return (Bullone) b.clone();
			}
		}
		
		if( trovato==false ) {
			throw new GestoreBulloniException(MsgErrore.BULLONE_NON_TROVATO, new GestoreBulloniException());
		}
		return null;
	}
	
	
	/**{@inheritDoc}
	 *
	 */
	public String[] getInfoBulloneByCodice(int codice) throws GestoreBulloniException {
		return this.getBulloneByCodice(codice).getInfo();
	}
	
	
	/**{@inheritDoc}
	 * 
	 */
	public void updatePrezzoBulloneByCodice(int codice, double nuovoPrezzo) throws GestoreBulloniException, BulloneException, DatabaseSQLException, SQLException {
		boolean trovato = false;	// Vale true se il bullone e' stato trovato
		
		// Ricerca e update
		for(Bullone b : this.bulloni) {
			if(b.getCodice() == codice) {
				trovato = true;
				b.setPrezzo(nuovoPrezzo);
				
				// Update nel DB
				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_BULLONI, CampiTabellaBullone.prezzo.toString(), ((Double)nuovoPrezzo).toString(), CampiTabellaBullone.codice.toString(), ((Integer)codice).toString()));
			}
		}
		
		if(trovato==false) {
			throw new GestoreBulloniException(MsgErrore.BULLONE_NON_TROVATO, new GestoreBulloniException());
		}
	}
	
	
	/**{@inheritDoc}
	 * 
	 */
	public void rimuoviBulloneByCodice(int codice) throws GestoreBulloniException, DatabaseSQLException, SQLException {
		boolean trovato = false;	// Vale true se il bullone e' stato trovato.
		
		for(Bullone b : this.bulloni) {
			if(b.getCodice()==codice) {
				trovato = true;
				b.elimina();	// Elimina il bullone
				
				// Update nel DB
				DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_BULLONI, CampiTabellaBullone.eliminato.toString(), "T", CampiTabellaBullone.codice.toString(), ((Integer)b.getCodice()).toString()));
			}
		}
		
		if(trovato==false) {
			throw new GestoreBulloniException(MsgErrore.BULLONE_NON_TROVATO, new GestoreBulloniException());
		}
	}
	
	
	/**{@inheritDoc}
	 * 
	 */
	public boolean isEmpty() {
		return this.bulloni.isEmpty();
	}
	
	
	
	/*
	 * ------------------------------------------
	 * 				METODI PRIVATI
	 * ------------------------------------------
	 */
	/**
	 * A partire dal risultato di una query, costruisce un oggetto Bullone di tipo grano.
	 * @param rs Il risultato della query.
	 * @return bullone Il bullone costruito.
	 */
	private Bullone costruisciBulloneGrano(ResultSet rs) {
		try {
			Bullone bullone = new BulloneGrano(rs.getInt(1), new Data(rs.getDate(2)), rs.getString(3), rs.getDouble(4), rs.getDouble(5), Materiale.valueOf(rs.getString(9)), rs.getDouble(6), rs.getDouble(7), Innesto.valueOf(rs.getString(8)));
			if(rs.getString(10).equals("T")) {
				bullone.elimina();
			}
			return bullone;
		} catch(BulloneException e) {
			System.err.println(e.getMessage());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch(DateTimeException e) {
			System.err.println(e.getMessage());
		}
				
		return null;
	}

	
	/**
	 * Restituisce il massimo tra i codici dei bulloni presenti nel set.
	 * Questo metodo servira' per evitare che dopo il riempimento del set di bulloni, il valore dell'attributo "codBulloneAutomatico"
	 * contenga un valore corrispondente ad un codice gia' presente nel set. In questo modo viene evitato il sollevamento
	 * di qualche eccezione relativa all'inserimento nel database di un bullone con un codice gia' presente (con il conseguente rifiuto
	 * della insert).
	 * La ricerca del codice avente valore massimo avviene mediante una ricerca lineare all'interno del set, confrontando il codice
	 * con un valore massimo provvisorio, che verra' aggiornato quando viene trovato un codice il cui valore e' superiore.
	 * @return max Il codice del bullone avente valore massimo.
	 */
	private int getMaxCodiceBullone() {
		int max = 0;
		
		for(Bullone b : this.bulloni) {
			if(b.getCodice()>max) {
				max = b.getCodice();
			}
		}
		return max;
	}
	
}