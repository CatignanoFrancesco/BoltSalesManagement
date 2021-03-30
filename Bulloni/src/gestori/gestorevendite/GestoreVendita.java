package gestori.gestorevendite;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;
import databaseSQL.*;
import databaseSQL.exception.DatabaseSQLException;

import utility.Data;
import vendita.*;
import vendita.exception.VenditaException;
import persona.ImpiegatoBulloni;
import gestori.gestoribulloni.*;
import gestori.gestoribulloni.exception.GestoreBulloniException;
import gestori.gestoreImpiegati.*;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gestori.gestorevendite.exception.*;

/**
 * @author GiannettaGerardo
 *
 * Questa classe mette in relazione il package vendita con il database. Nel costruttore crea
 * un set di oggetti Vendita<MerceVenduta> tramite database e uso di altri costruttori,
 * inoltre permette l'inseriemento di una nuova vendita, l'aggiornamento e l'eliminazione di una 
 * vendita esistente, oltre che la ricerca e restituzione di oggetti vendita in base a specifici parametri
 */
public class GestoreVendita {
	
	// gestore dei bulloni che si interfaccia con la parte del database che riguarda i bulloni
	GestoreBulloni gb;
	
	// gestore degli impiegati che si interfaccia con la parte del database che riguarda gli impiegati
	GestoreImpiegatiDb gi;
	
	/** Set contenente oggetti VenditaBulloni prelevati dal database */
	private Set<Vendita<MerceVenduta>> vendite = new HashSet<Vendita<MerceVenduta>>();
	
	/** Map contenente il numero di vendite effettuate da un impiegato in ogni data */
	private Map<ChiaveImpiegatoData, Integer> impiegatoData = new HashMap<ChiaveImpiegatoData, Integer>();
	
	/** Map contenente il numero di vendite effettuate da un impiegato in ogni anno */
	private Map<ChiaveImpiegatoAnno, Integer> impiegatoAnno = new HashMap<ChiaveImpiegatoAnno, Integer>();
	
	/** Codice per le vendita a costruzione automatica, utile per poter aggiungere una nuova vendita
	 * senza impostare manualmente un codice come parametro di input */
	private static int codVenditaAutomatico = 0;
	
	/** Nome della tabella Vendita del database */
	private static final String NOME_TABELLA_VENDITA = "Vendita";
	
	/** Nome della tabella MerceVenduta del database */
	private static final String NOME_TABELLA_MERCE_VENDUTA = "MerceVenduta";

	
	
	/**
	 * Costruttore che prima di tutto chiama il metodo selectMerceVenduta per prelevare dal database la merce venduta,
	 * successivamente preleva i dati sulle vendita dal daatabse, costruendo il Set di Vendita interno alla classe;
	 * infine imposta il codice di vendita automatico in base ai valori ritornati dal database
	 * 
	 * @param gb gestore dei bulloni
	 * @param gi gestore degli impiegati
	 */
	public GestoreVendita(GestoreBulloni gb, GestoreImpiegatiDb gi) throws GestoreVenditaException {
		
		boolean eccezioneGestori = false;
		String msgErrore = MsgErroreGestoreVendita.INTESTAZIONE;
		
		// controllo che i gestori passati come parametro non siano nulli
		if (gb == null) {
			eccezioneGestori = true;
			msgErrore += MsgErroreGestoreVendita.GESTORE_BULLONI_NULLO;
		}
		if (gi == null) {
			eccezioneGestori = true;
			msgErrore += MsgErroreGestoreVendita.GESTORE_IMPIEGATI_NULLO + "\n";
		}
		if (eccezioneGestori) {
			throw new GestoreVenditaException(msgErrore, new GestoreVenditaException());
		}
		
		eccezioneGestori = false;
		msgErrore = MsgErroreGestoreVendita.INTESTAZIONE;
		
		// salvo le istanze dei gestori in questa istanza di gestore vendite, serviranno per altri metodi
		this.gb = gb;
		this.gi = gi;
		
		// controllo che nel database esista almeno un bullone e/o un impiegato tramite gli appositi gestori
		if (gb.isEmpty()) {
			eccezioneGestori = true;
			msgErrore += MsgErroreGestoreVendita.SET_LOCALE_BULLONI_VUOTO;
		}
		if (gi.localSetIsEmpty()) {
			eccezioneGestori = true;
			msgErrore += MsgErroreGestoreVendita.SET_LOCALE_IMPIEGATI_VUOTO + "\n";
		}
		if (eccezioneGestori) {
			throw new GestoreVenditaException(msgErrore, new GestoreVenditaException());
		}
		
		
		// HashMap contenente come chiave il codice della vendita, come oggetto associato un Set di MerceVenduta
		Map<Integer, Set<MerceVenduta>> merce = selectMerceVenduta();
		
		// se l'HashMap non è vuoto, procede alla creazione degli oggetti vendita
		if (!merce.isEmpty()) {
			
			try {
				
				ResultSet rs = DatabaseSQL.select(Query.getSimpleSelect(NOME_TABELLA_VENDITA));
				
				while (rs.next()) {
					
					try {
						vendite.add(new VenditaBulloni(rs.getInt(CampiTabellaVendita.codVendita.toString()), 
			                                           new Data(rs.getDate(CampiTabellaVendita.data.toString())), 
			                                           gi.getImpiegatoByID(rs.getInt(CampiTabellaVendita.impiegato.toString())), 
			                                           merce.get(rs.getInt(CampiTabellaVendita.codVendita.toString()))));
					}
					catch (VenditaException e) {
						System.err.println(e.getMessage());
					}
					catch (ExceptionGestoreImpiegato e) {
						System.err.println(e.getMessage());
					}
					
				}
				
				DatabaseSQL.chiudiConnessione();
				
				// imposta il nuovo codice automatico delle vendite
				setCodVenditaAutomatico();
				
				// mappa le vendite in base all'impiegato che le ha effettuate e alla data/anno
				mappaVenditeImpiegato();
				
			}
			catch (DatabaseSQLException e) {
				System.err.println(e.getMessage());
			}
			catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	
	
	/**
	 * Metodo che, interfacciandosi con il database, crea un HashMap che ha per chiave il codice
	 * della vendita, per oggetto associato ad essa un Set di MerceVenduta che viene costruito man
	 * mano che si va avanti con l'ottenimento dei risultati dal DB. Sfrutta il GestoreBulloni per
	 * ottenere un riferimento di bullone e salvarlo direttamente nel Set di MerceVenduta, senza dover
	 * interrogare il database per ottenere anche i bulloni
	 * 
	 * @param gb gestore dei bulloni
	 * @return HashMap contenente i Set di MerceVenduta associati ad una chiave 
	 *         di tipo int che rappresenta il codice vendita
	 */
	private Map<Integer, Set<MerceVenduta>> selectMerceVenduta() {
		
		Map<Integer, Set<MerceVenduta>> merce = new HashMap<Integer, Set<MerceVenduta>>();
		
		try {
			ResultSet rs = DatabaseSQL.select(Query.getSimpleSelect(NOME_TABELLA_MERCE_VENDUTA));
			
			while(rs.next()) {
				
				/*
				 * Inizialmente interrogo l'HashMap per ottenere, in base al codice vendita passato in input, un Set di MerceVenduta;
				 * Successivamente:
				 * 
				 * - se l'HashMap ha ritornato null, quindi non conteneva quel codice vendita passato in input, si crea un nuovo
				 *   Set, si riempie con il nuovo oggetto MerceVenduta ritornato e si aggiunge, insieme al codice vendita sconosciuto,
				 *   nell'HashMap;
				 * 
				 * - se l'HashMap non ha ritornato null, quindi conteneva già quel codice vendita passato in input, ritorna un riferiemento
				 *   al Set di MerceVenduta, che sarà usato per aggiungere il nuovo oggetto MerceVenduta ritornato dal database.
				 */
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
	
	
	/**
	 * Metodo che riempie i 2 HashMap impiegatoData e impiegatoAnno;
	 * così facendo i controlli sul numero di bulloni vendibili per gli impiegati,
	 * sia giornalieri che annuali, saranno semplici e rapidi
	 */
	private void mappaVenditeImpiegato() {
		
		ChiaveImpiegatoData mapCID = null;
		ChiaveImpiegatoAnno mapCIA = null;
		Integer numeroVenditeInData = null;
		Integer numeroVenditeInAnno = null;
		
		for (Vendita<MerceVenduta> v : vendite) {
			
			mapCID = new ChiaveImpiegatoData(v.getResponsabileVendita(), (Data)v.getData().clone());
			mapCIA = new ChiaveImpiegatoAnno(v.getResponsabileVendita(), v.getData().getAnno());
			
			numeroVenditeInData = impiegatoData.get(mapCID);
			numeroVenditeInAnno = impiegatoAnno.get(mapCIA);
			
			if (numeroVenditeInData != null) {
				numeroVenditeInData += v.getQuantitaMerceTotale();
				impiegatoData.put(mapCID, numeroVenditeInData);
			}
			else
				impiegatoData.put(mapCID, v.getQuantitaMerceTotale());
			
			if (numeroVenditeInAnno != null) {
				numeroVenditeInAnno += v.getQuantitaMerceTotale();
				impiegatoAnno.put(mapCIA, numeroVenditeInAnno);
			}
			else
				impiegatoAnno.put(mapCIA, v.getQuantitaMerceTotale());
		}
	}
	
	
	
	/**
	 * Metodo che ritorna un Set<Vendita<MerceVenduta>> di cloni dell'originale
	 * 
	 * @return Set<Vendita<MerceVenduta>> di cloni
	 * @throws GestoreVenditaException
	 */
	public Set<Vendita<MerceVenduta>> getVendite() throws GestoreVenditaException {
		
		Set<Vendita<MerceVenduta>> risultato = new HashSet<Vendita<MerceVenduta>>();
		
		for (Vendita<MerceVenduta> v : vendite)
			risultato.add((Vendita<MerceVenduta>)v.clone());
		
		if (risultato.isEmpty())
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITE_VUOTE, new GestoreVenditaException());
		
		return risultato;
	}
	
	
	
	/**
	 * Metodo che ritorna il clone di un oggetto Vendita<MerceVenduta>
	 * in base ad un codice vendita passato in input
	 * 
	 * @param codiceVendita codice da cercare
	 * @return l'oggetto Vendita<MerceVenduta> trovato
	 * @throws GestoreVenditaException
	 */
	public Vendita<MerceVenduta> getVenditaByCodice(int codiceVendita) throws GestoreVenditaException {
		
		Vendita<MerceVenduta> risultato = null;
		
		for (Vendita<MerceVenduta> v : vendite) {
			if (v.getCodVendita() == codiceVendita)
				risultato = (Vendita<MerceVenduta>)v.clone();
		}
		
		if (risultato == null)
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITA_NON_TROVATA, new GestoreVenditaException());
		
		return risultato;
		
	}
	
	
	
	/**
	 * Metodo che ritorna il clone di un Set<Vendita<MerceVenduta>>
	 * in base ad una data passata in input
	 * 
	 * @param dataVendita data di effettuazione della vendita
	 * @return il Set<Vendita<MerceVenduta>> trovato
	 * @throws GestoreVenditaException
	 */
	public Set<Vendita<MerceVenduta>> getVenditeByData(Data dataVendita) throws GestoreVenditaException {
		
		Set<Vendita<MerceVenduta>> risultato = new HashSet<Vendita<MerceVenduta>>();
		
		for (Vendita<MerceVenduta> v : vendite) {
			if (v.getData().compareTo(dataVendita) == 0)
				risultato.add((Vendita<MerceVenduta>)v.clone());
		}
		
		if (risultato.isEmpty())
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITE_NON_TROVATE_DATA, new GestoreVenditaException());
		
		return risultato;
	}
	
	
	
	/**
	 * Metodo che ritorna il clone di un Set<Vendita<MerceVenduta>>
	 * in base al codice impiegato passato in input
	 * 
	 * @param codiceImpiegato codice impiegato da cercare nel Set
	 * @return il Set<Vendita<MerceVenduta>> trovato
	 * @throws GestoreVenditaException
	 */
	public Set<Vendita<MerceVenduta>> getVenditeByImpiegato(int codiceImpiegato) throws GestoreVenditaException {
		
		Set<Vendita<MerceVenduta>> risultato = new HashSet<Vendita<MerceVenduta>>();
		
		for (Vendita<MerceVenduta> v : vendite) {
			if (v.getResponsabileVendita() == codiceImpiegato)
				risultato.add((Vendita<MerceVenduta>)v.clone());
		}
		
		if (risultato.isEmpty())
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITE_NON_TROVATE_IMPIEGATO, new GestoreVenditaException());
		
		return risultato;
	}
	
	
	
	/**
	 * Metodo che inserisce nel Set di vendite e nel database un nuovo oggetto vendita.
	 * Effettua opportuni controlli prelinimari sull'oggetto, lo inserisce nel Set controllando
	 * che non sia già presente un oggetto con lo stesso codice chiave e infine si avvale di un
	 * altro metodo privato per inserire nel database anche il corrispondente Set di MerceVenduta
	 * 
	 * @param vendita oggetto vendita da inserire nel database
	 * @throws GestoreVenditaException
	 */
	public void aggiungiVendita(Vendita<MerceVenduta> vendita) throws GestoreVenditaException {
		
		// controllo che l'oggetto non sia nullo
		if (vendita == null)
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITA_NULLA, new GestoreVenditaException());
		
		// controllo che il codice dell'oggetto non sia negativo
		if (vendita.getCodVendita() < 0)
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.CODICE_VENDITA_NEGATIVO, new GestoreVenditaException());
		
		// controllo che il nuovo oggetto sia univoco all'interno del Set
		if(!vendite.add(vendita)) {
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITA_ESISTENTE, new GestoreVenditaException());
		}
		else {
			
			// classi chiave per interrogare gli HashMap
			ChiaveImpiegatoData cid = new ChiaveImpiegatoData(vendita.getResponsabileVendita(), (Data)vendita.getData().clone());
			ChiaveImpiegatoAnno cia = new ChiaveImpiegatoAnno(vendita.getResponsabileVendita(), vendita.getData().getAnno());
	
			// valori di ritorno dall'interrogazione degli HashMap (potrebbero essere null)
			Integer nuovaQuantitaCID = this.impiegatoData.get(cid);
			Integer nuovaQuantitaCIA = this.impiegatoAnno.get(cia);
			
			// se i valori ritornati dagli HashMap fossero nulli, si solleverebbe una NullPointerException
			if (nuovaQuantitaCID == null)
				nuovaQuantitaCID = new Integer(vendita.getQuantitaMerceTotale());
			else
				nuovaQuantitaCID += vendita.getQuantitaMerceTotale();
			
			if (nuovaQuantitaCIA == null)
				nuovaQuantitaCIA = new Integer(vendita.getQuantitaMerceTotale());
			else
				nuovaQuantitaCIA += vendita.getQuantitaMerceTotale();
			
			/* controllo che l'impiegato che sta cercando di effettuare la vendita possa effettivamente effettuarla, 
			 * controllando il numero di bulloni già venduti in quella data e anno.
			 * Questo controllo viene effettuato dopo l'inserimento nel Set di vendite principale, perché prima bisogna
			 * assicurarsi che la vendita sia univoca; in caso lo fesse ma non fosse possibile salvarla per via del controllo
			 * qui effettuato, la vendita verrà rimossa dal Set */
			if (!checkNumeroBulloniVendutiImpiegato(vendita.getResponsabileVendita(), cid, cia, nuovaQuantitaCIA, nuovaQuantitaCID)) {
				vendite.remove(vendita);
				throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.BULLONI_MASSIMI_SUPERATI, new GestoreVenditaException());
			}
			
			// mi assicuro che il codice di vendita automatico sia sempre maggiore del codice vendita più grande inserito nel Set
			if (vendita.getCodVendita() >= codVenditaAutomatico) {
				codVenditaAutomatico = vendita.getCodVendita() + 1;
			}
			
			// creo i valori per la tabella vendita del database covertendoli tutti in formato String
			String[] valoriTabellaVendita = {((Integer)vendita.getCodVendita()).toString(), 
					                         ((Integer)vendita.getResponsabileVendita()).toString(),
					                         vendita.getData().toSqlDate().toString(),
					                         ((Double)vendita.getPrezzoVenditaTotale()).toString(),
					                         ((Integer)vendita.getQuantitaMerceTotale()).toString()};
			
			try {
				// eseguo la insert nella tabella vendita del database
				DatabaseSQL.insert(Query.getSimpleInsert(NOME_TABELLA_VENDITA, valoriTabellaVendita));
				
				// chiamo il metodo che inserisce opportunatamente il Set di MerceVenduta nel database aprendo un solo canale TCP
				aggiungiMerceVenduta(vendita.getMerceVenduta(), vendita);
			}
			catch (DatabaseSQLException e) {
				System.err.println(e.getMessage());
			}
			catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			
		}
	}
	
	
	
	/**
	 * Metodo che inserisce, aprendo un solo canale TCP e quindi chiamando una sola volta il metodo
	 * insert di DatabaseSQL, un intero Set di MerceVenduta nel database
	 * 
	 * @param mv set di merce venduta corrispondente all'oggetto vendita da aggiungere al database
	 * @param vendita oggetto vendita da aggiungere al database
	 * @throws SQLException
	 * @throws DatabaseSQLException
	 */
	private void aggiungiMerceVenduta(Set<MerceVenduta> mv, Vendita<MerceVenduta> vendita) throws SQLException, DatabaseSQLException {
		
		// creo i valori per la tabella merce venduta del database covertendoli tutti in formato String
		ArrayList<String[]> valoriTabellaMerceVenduta = new ArrayList<String[]>(mv.size());
		
		for (MerceVenduta m : mv) {
			valoriTabellaMerceVenduta.add(new String[]{((Integer)vendita.getCodVendita()).toString(),
					                                   ((Integer)m.getCodiceBullone()).toString(),
					                                   ((Integer)m.getNumeroBulloni()).toString(),
					                                   ((Double)m.getPrezzoBulloni()).toString(),
					                                   ((Double)m.getPrezzoVenditaBullone()).toString()});
		}
		// eseguo la insert nel database
		DatabaseSQL.insert(Query.getInsertMultipli(NOME_TABELLA_MERCE_VENDUTA, valoriTabellaMerceVenduta));
	}
	
	
	
	/**
	 * Metodo che effettua l'update sul Set di vendite e sul database del numero di bulloni venduti per un dato bullone
	 * in una data vendita. Utilizzando il metodo setQuantitaMerceByCodice degli oggetti Vendita ci si assicura anche 
	 * l'aggiornamento del numero totale di bulloni venduti nell'intera vendita e del prezzo totale della vendita
	 * 
	 * @param codVendita codice identificativo della vendita
	 * @param codBullone codice identificativo del bullone nella vendita
	 * @param nuovoNumero nuova quantità di bulloni per un dato bullone
	 * @return true se il metodo è terminato con successo, false altrimenti
	 * @throws GestoreVenditaException 
	 */
	public boolean updateNumeroBulloniVendutiByCodici(int codVendita, int codBullone, int nuovoNumero) throws GestoreVenditaException {
		
		boolean codiceTrovato = false;
		
		// classi chiave per interrogare gli HashMap
		ChiaveImpiegatoData cid = null;
		ChiaveImpiegatoAnno cia = null;
		
		Vendita<MerceVenduta> venditaReale = null;
		
		for (Vendita<MerceVenduta> v : vendite) {
			if (v.getCodVendita() == codVendita) {
				venditaReale = v;
				codiceTrovato = true;
				break;
			}
		}
		
		// si controlla che sia stata trovata la vendita nel Set principale
		if (!codiceTrovato)
			return codiceTrovato;
		
		// rendendo nuovamente falso il valore booleano, si può ripetere il controllo per la ricerca del bullone all'interno della vendita trovata
		codiceTrovato = false;
		
		/* ricavo il Set clonato di MerceVenduta dall'oggetto vendita trovato e da esso prendo il singolo oggetto 
		 * MerceVenduta relativo al bullone che mi serve */
		Set<MerceVenduta> cloneMerce = venditaReale.getMerceVenduta();
		MerceVenduta merce = null;
		for (MerceVenduta mv : cloneMerce) {
			if (mv.getCodiceBullone() == codBullone) {
				merce = mv;
				codiceTrovato = true;
				break;
			}
		}
		
		// si controlla che sia stato trovato quello specifico bullone nella vendita 
		if (!codiceTrovato)
			return codiceTrovato;
		
		// costruisco le chiavi per la ricerca negli HashMap
		cid = new ChiaveImpiegatoData(venditaReale.getResponsabileVendita(), (Data)venditaReale.getData().clone());
		cia = new ChiaveImpiegatoAnno(venditaReale.getResponsabileVendita(), venditaReale.getData().getAnno());
		
		/* questi 2 valori Integer ritornati sicuramente non saranno mai null, perché ci si sta riferendo ad una vendita già avvenuta,
		 * inoltre i controlli sull'esistenza di questa vendita sono stati fatti precedentemente. Inoltre ai valori ritornati viene sottratto
		 * il numero di bulloni venduti per quel tipo di bullone per cui si deve effettuare l'update; successivamente viene sommato il valore nuovo
		 * per quello specifico bullone, così da poter effettuare i controlli simulando la nuova quantità e decidendo se rispetta i vincoli imposti
		 * allo specifico impiegato */
		Integer numBulloniCID = (impiegatoData.get(cid) - merce.getNumeroBulloni()) + nuovoNumero;
		Integer numBulloniCIA = (impiegatoAnno.get(cia) - merce.getNumeroBulloni()) + nuovoNumero;
		
		// controllo che i valori calcolati siano accettabili per l'impiegato
		if (checkNumeroBulloniVendutiImpiegato(venditaReale.getResponsabileVendita(), cid, cia, numBulloniCIA, numBulloniCID)) {
			venditaReale.setQuantitaMerceByCodice(merce.getCodiceBullone(), nuovoNumero);
		}
		else {
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.BULLONI_MASSIMI_SUPERATI, new GestoreVenditaException());
		}
		
		// ottengo ulteriormente l'oggetto merce per poter salvare nel database le modifiche effettuate
		cloneMerce = venditaReale.getMerceVenduta();
		for (MerceVenduta mv : cloneMerce) {
			if (mv.getCodiceBullone() == codBullone) {
				merce = mv;
				break;
			}
		}
		
		// inizio aggiornamento nel database
		try {
			// aggiorno nel database il numero dei bulloni totali nella tabella Vendita
			DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_VENDITA, 
					                                      CampiTabellaVendita.numeroBulloniTotali.toString(), 
					                                      ((Integer)venditaReale.getQuantitaMerceTotale()).toString(), 
					                                      CampiTabellaVendita.codVendita.toString(), 
					                                      ((Integer)codVendita).toString()));
			
			// aggiorno nel database il prezzo di vendita totale nella tabella Vendita
			DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_VENDITA, 
                                                          CampiTabellaVendita.prezzoVenditaTotale.toString(), 
                                                          ((Double)venditaReale.getPrezzoVenditaTotale()).toString(), 
                                                          CampiTabellaVendita.codVendita.toString(), 
                                                          ((Integer)codVendita).toString()));
			
			// aggiorno nel database il numero dei bulloni totali di quello specifico bullone nella tabella MerceVenduta
			DatabaseSQL.update(Query.getSimpleUpdateByDoubleKey(NOME_TABELLA_MERCE_VENDUTA, 
			                                                    CampiTabellaMerceVenduta.numeroBulloni.toString(), 
			                                                    ((Integer)merce.getNumeroBulloni()).toString(), 
			                                                    CampiTabellaMerceVenduta.codVendita.toString(), 
			                                                    ((Integer)venditaReale.getCodVendita()).toString(), 
			                                                    CampiTabellaMerceVenduta.bullone.toString(), 
			                                                    ((Integer)merce.getCodiceBullone()).toString()));
			
			// aggiorno nel database il prezzo di vendita totale di quello specifico bullone nella tabella MerceVenduta
			DatabaseSQL.update(Query.getSimpleUpdateByDoubleKey(NOME_TABELLA_MERCE_VENDUTA, 
                                                                CampiTabellaMerceVenduta.prezzoBulloni.toString(), 
                                                                ((Double)merce.getPrezzoBulloni()).toString(), 
                                                                CampiTabellaMerceVenduta.codVendita.toString(), 
                                                                ((Integer)venditaReale.getCodVendita()).toString(), 
                                                                CampiTabellaMerceVenduta.bullone.toString(), 
                                                                ((Integer)merce.getCodiceBullone()).toString()));
		}
		catch (DatabaseSQLException e) {
			System.err.println(e.getMessage());
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return codiceTrovato;
	}
	
	
	
	/**
	 * Metodo di controllo utilizzato nell'aggiunta e modifica di una vendita.
     * Se l'impiegato che vuole effettuare questa vendita, ha già un numero di bulloni venduti, in quella specifica data,
	 * tale che sommato alla quantità di bulloni di questa vendita, supera il massimo consentito giornaliero 
	 * (500 bulloni per tutti gli impiegati) o il massimo consentito annuale (varia per ogni impiegato), allora
	 * la vendita viene annullata
	 * 
	 * @param impiegato codice univoco dell'impiegato
	 * @param cid chiave per l'HashMap impiegatoData
	 * @param cia chiave per l'HashMap impiegatoAnno
	 * @param nuovaQuantitaCIA nuovo valore calcolato da aggiungere all'HashMap impiegatoAnno
	 * @param nuovaQuantitaCID nuovo valore calcolato da aggiungere all'HashMap impiegatoData
	 * @return true se il nuovo valore è accettabile per l'impiegato, false altrimenti
	 */
	private boolean checkNumeroBulloniVendutiImpiegato(int impiegato, ChiaveImpiegatoData cid, ChiaveImpiegatoAnno cia, int nuovaQuantitaCIA, int nuovaQuantitaCID) {
		
		// risultato del metodo
		boolean risultato = false;
		
		// impiegato responsabile di questa vendita
		ImpiegatoBulloni imp = null;
			
		try {
			imp = gi.getImpiegatoByID(impiegato);

			if (nuovaQuantitaCIA <= imp.getBulloniVendibiliAnnualmente()) {
				
				if (nuovaQuantitaCID <= ImpiegatoBulloni.getBulloniVendibiliGiornalmente()) {
					
					impiegatoData.put(cid, nuovaQuantitaCID);
					impiegatoAnno.put(cia, nuovaQuantitaCIA);
					risultato = true;
				}
			}
		}
		catch (ExceptionGestoreImpiegato e) {
			System.err.println(e.getMessage());
		}
		
		return risultato;
	}
	
	
	
	/**
	 * Metodo che elimina dal Set di vendite e dal database una certa vendita
	 * 
	 * @param codiceVendita codice identificativo della vendita da eliminare
	 * @return true se il metodo è terminato con successo, false altrimenti
	 */
	public boolean rimuoviVenditaByCodice(int codiceVendita) {
		
		boolean codiceTrovato = false;
		
		// classi chiave per interrogare gli HashMap
		ChiaveImpiegatoData cid = null;
		ChiaveImpiegatoAnno cia = null;
		
		Vendita<MerceVenduta> vendita = null;
		
		/* se il metodo getVenditaByCodice solleva l'eccezione GestoreVenditaException, 
		 * allora non esiste alcuna vendita con questo codice all'interno del Set;
		 * se il metodo remove rimuove con successo l'oggetto dal set originale, allora
		 * si procede alla rimozione dal database */
		try {
			
			vendita = this.getVenditaByCodice(codiceVendita);
			cid = new ChiaveImpiegatoData(vendita.getResponsabileVendita(), vendita.getData());
			cia = new ChiaveImpiegatoAnno(vendita.getResponsabileVendita(), vendita.getData().getAnno());
			
			if (vendite.remove(vendita)) {
				
				// rimuovo la quantità di bulloni venduti salvata nei due HashMap che riguarda la vendita appena eliminata
				impiegatoData.put(cid, impiegatoData.get(cid) - vendita.getQuantitaMerceTotale());
				impiegatoAnno.put(cia, impiegatoAnno.get(cia) - vendita.getQuantitaMerceTotale());
				
				codiceTrovato = true;
				
				try {
					/* eseguo una delete nel database della vendita scelta. Nel database, una eliminazione di una vendita si ripercuote
					 * anche sulle corrispondenti tuple in MerceVenduta, quindi non c'è bisogno di agire anche sulla tabella MerceVenduta */
					DatabaseSQL.delete(Query.getSimpleDelete(NOME_TABELLA_VENDITA, CampiTabellaVendita.codVendita.toString(), ((Integer)codiceVendita).toString()));
				}
				catch (DatabaseSQLException e) {
					System.err.println(e.getMessage());
				}
				catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		catch (GestoreVenditaException e) {
			System.err.println(e.getMessage());
		}
		
		return codiceTrovato;
	}
	
	
	
	/**
	 * Metodo che interroga l'HashMap impiegatoData contenente il numero di bulloni venduti in base
	 * all'impiegato e alla data in cui è stata effettuata la vendita.
	 * 
	 * @param matricolaImpiegato codice univoco di un impiegato
	 * @param dataVendita data in cui la vendita è stata effettuata
	 * @return -1 se i dati inseriti in input non sono contenuti nell'HashMap, un numero >= a 0 altrimenti
	 */
	public int getNumBulloniVendutiByImpiegatoData(int matricolaImpiegato, Data dataVendita) {
		
		Integer risultato = 0;
		
		ChiaveImpiegatoData cid = new ChiaveImpiegatoData(matricolaImpiegato, (Data)dataVendita.clone());
		risultato = impiegatoData.get(cid);
		
		if (risultato == null)
			risultato = new Integer(-1);
		
		return risultato;
	}
	
	
	
	/**
	 * Metodo che interroga l'HashMap impiegatoAnno contenente il numero di bulloni venduti in base
	 * all'impiegato e all'anno in cui sono state effettuate le vendite.
	 * 
	 * @param matricolaImpiegato codice univoco di un impiegato
	 * @param anno anno in cui sono state effettuate delle vendite
	 * @return -1 se i dati inseriti in input non sono contenuti nell'HashMap, un numero >= a 0 altrimenti
	 */
	public int getNumBulloniVendutiByImpiegatoAnno(int matricolaImpiegato, int anno) {
		
		Integer risultato = 0;
		
		ChiaveImpiegatoAnno cia = new ChiaveImpiegatoAnno(matricolaImpiegato, anno);
		risultato = impiegatoAnno.get(cia);
		
		if (risultato == null)
			risultato = new Integer(-1);
		
		return risultato;
	}
	
	
	
	/**
	 * Metodo che imposta il codice della vendita automatico uguale al massimo tra i codici presenti nel Set + 1; 
	 */
	private void setCodVenditaAutomatico() {
		
		int max = 0;
		
		for (Vendita<MerceVenduta> v : vendite) {
			if (v.getCodVendita() > max)
				max = v.getCodVendita();
		}
		
		codVenditaAutomatico = ++max;
	}
	
	
	/**
	 * Metodo che ritorna il codice delle vendite automatico;
	 * È possibile usarlo per inserire un nuovo oggetto Vendita nel gestore,
	 * assicurandosi così di inserire un codice valido
	 * 
	 * @return il codice delle vendite automatico
	 */
	public int getCodVenditaAutomatico() {
		return codVenditaAutomatico;
	}
	
	
	/**
	 * Metodo che controlla se il set locale delle vendite è vuoto o meno
	 * 
	 * @return true se il set è vuoto, false altrimenti
	 */
	public boolean isEmpty() {
		return this.vendite.isEmpty();
	}
	
	
	/**
	 * Metodo che ritorna una stringa contenente tutte le informazioni dettagliate dell'istanza dell'oggetto
	 * con il quale si chiama questo metodo
	 * 
	 * @return la stringa con tutte le informazioni dell'oggetto
	 */
	public String toString() {
		
		String risultato = "Classe " + this.getClass().getSimpleName() + ":\n" +
		                   "Codice vendita automatico: " + codVenditaAutomatico + "\n" +
				           "Nome tabella vendita nel database: " + NOME_TABELLA_VENDITA + "\n" + 
		                   "Nome tabella merce nel database: " + NOME_TABELLA_MERCE_VENDUTA + "\n" +
				           "Set di vendite: \n";
		
		for (Vendita<MerceVenduta> v : vendite) {
			risultato += v.toString();
		}
		
		return risultato;
	}

}