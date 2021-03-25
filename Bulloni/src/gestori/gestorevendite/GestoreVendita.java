package gestori.gestorevendite;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;

import utility.Data;
import vendita.*;
import vendita.exception.VenditaException;
import persona.ImpiegatoBulloni;
import databaseSQL.*;
import databaseSQL.exception.DatabaseSQLException;
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
	
	GestoreBulloni gb;
	
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
			msgErrore += MsgErroreGestoreVendita.GESTORE_BULLONI_NULLO + "\n";
		}
		if (gi == null) {
			eccezioneGestori = true;
			msgErrore += MsgErroreGestoreVendita.GESTORE_IMPIEGATI_NULLO + "\n";
		}
		if (eccezioneGestori) {
			throw new GestoreVenditaException(msgErrore, new GestoreVenditaException());
		}
		
		// salvo le istanze dei gestori in questa istanza di gestore vendite, serviranno per altri metodi
		this.gb = gb;
		this.gi = gi;
		
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
				
			}
			catch (DatabaseSQLException e) {
				System.err.println(e.getMessage());
			}
			catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		else {
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.MERCE_VENDUTA_NULLA, new GestoreVenditaException());
		}	
		
		// imposta il nuovo codice automatico delle vendite
		setCodVenditaAutomatico();
		
		// mappa le vendite in base all'impiegato che le ha effettuate e alla data/anno
		mappaVenditeImpiegato();
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
			
			/* controllo che l'impiegato che sta cercando di effettuare la vendita possa effettivamente effettuarla, 
			 * controllando il numero di bulloni già venduti in quella data e anno.
			 * Questo controllo viene effettuato dopo l'inserimento nel Set di vendite principale, perché prima bisogna
			 * assicurarsi che la vendita sia univoca; in caso lo fesse ma non fosse possibile salvarla per via del controllo
			 * qui effettuato, la vendita verrà rimossa dal Set */
			if (!checkNumeroBulloniVendutiImpiegato(vendita)) {
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
	 * Metodo di controllo utilizzato nell'aggiunta e modifica di una vendita.
     * Se l'impiegato che vuole effettuare questa vendita, ha già un numero di bulloni venduti in quella specifica data
	 * tale che, sommato alla quantità di bulloni di questa vendita, supera il massimo consentito giornaliero 
	 * (500 bulloni per tutti gli impiegati) o il massimo consentito annuale (varia per ogni impiegato), allora
	 * la vendita viene annullata
	 * 
	 * @param vendita vendita che si vuole aggiungere o modificare
	 * @return true se la vendita è accettabile, false se non lo è
	 */
	private boolean checkNumeroBulloniVendutiImpiegato(Vendita<MerceVenduta> vendita) {
		
		// risultato del metodo
		boolean risultato = false;
		
		// classi chiave per interrogare gli HashMap
		ChiaveImpiegatoData cid = new ChiaveImpiegatoData(vendita.getResponsabileVendita(), (Data)vendita.getData().clone());
		ChiaveImpiegatoAnno cia = new ChiaveImpiegatoAnno(vendita.getResponsabileVendita(), vendita.getData().getAnno());
		
		// impiegato responsabile di questa vendita
		ImpiegatoBulloni imp = null;
		
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
		
		
		try {
			imp = gi.getImpiegatoByID(vendita.getResponsabileVendita());

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
	 */
	public boolean updateNumeroBulloniVendutiByCodici(int codVendita, int codBullone, int nuovoNumero) {
		
		boolean codiceTrovato = false;
		
		Vendita<MerceVenduta> clone = null;
		
		for (Vendita<MerceVenduta> v : vendite) {
			if (v.getCodVendita() == codVendita) {
				codiceTrovato = v.setQuantitaMerceByCodice(codBullone, nuovoNumero);
				clone = (Vendita<MerceVenduta>)v.clone();
			}
		}
		// se il metodo setQuantitaMerceByCodice ha ritornato false, allora il codice del bullone non è stato trovato
		if (!codiceTrovato)
			return codiceTrovato;
		
		/* ricavo il Set clonato di MerceVenduta dall'oggetto vendita trovato e da esso prendo il clone del singolo oggetto 
		 * MerceVenduta relativo al bullone che mi serve */
		Set<MerceVenduta> cloneMerce = clone.getMerceVenduta();
		MerceVenduta merce = null;
		for (MerceVenduta mv : cloneMerce) {
			if (mv.getCodiceBullone() == codBullone)
				merce = (MerceVenduta)mv.clone();
		}
		
		try {
			// aggiorno nel database il numero dei bulloni totali nella tabella Vendita
			DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_VENDITA, 
					                                      CampiTabellaVendita.numeroBulloniTotali.toString(), 
					                                      ((Integer)clone.getQuantitaMerceTotale()).toString(), 
					                                      CampiTabellaVendita.codVendita.toString(), 
					                                      ((Integer)codVendita).toString()));
			
			// aggiorno nel database il prezzo di vendita totale nella tabella Vendita
			DatabaseSQL.update(Query.getSimpleUpdateByKey(NOME_TABELLA_VENDITA, 
                                                          CampiTabellaVendita.prezzoVenditaTotale.toString(), 
                                                          ((Double)clone.getPrezzoVenditaTotale()).toString(), 
                                                          CampiTabellaVendita.codVendita.toString(), 
                                                          ((Integer)codVendita).toString()));
			
			// aggiorno nel database il numero dei bulloni totali di quello specifico bullone nella tabella MerceVenduta
			DatabaseSQL.update(Query.getSimpleUpdateByDoubleKey(NOME_TABELLA_MERCE_VENDUTA, 
			                                                    CampiTabellaMerceVenduta.numeroBulloni.toString(), 
			                                                    ((Integer)merce.getNumeroBulloni()).toString(), 
			                                                    CampiTabellaMerceVenduta.codVendita.toString(), 
			                                                    ((Integer)clone.getCodVendita()).toString(), 
			                                                    CampiTabellaMerceVenduta.bullone.toString(), 
			                                                    ((Integer)merce.getCodiceBullone()).toString()));
			
			// aggiorno nel database il prezzo di vendita totale di quello specifico bullone nella tabella MerceVenduta
			DatabaseSQL.update(Query.getSimpleUpdateByDoubleKey(NOME_TABELLA_MERCE_VENDUTA, 
                                                                CampiTabellaMerceVenduta.prezzoBulloni.toString(), 
                                                                ((Double)merce.getPrezzoBulloni()).toString(), 
                                                                CampiTabellaMerceVenduta.codVendita.toString(), 
                                                                ((Integer)clone.getCodVendita()).toString(), 
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
	 * Metodo che elimina dal Set di vendite e dal database una certa vendita
	 * 
	 * @param codiceVendita codice identificativo della vendita da eliminare
	 * @return true se il metodo è terminato con successo, false altrimenti
	 */
	public boolean rimuoviVenditaByCodice(int codiceVendita) {
		
		boolean codiceTrovato = false;
		
		/* se il metodo getVenditaByCodice solleva l'eccezione GestoreVenditaException, 
		 * allora non esiste alcuna vendita con questo codice all'interno del Set;
		 * se il metodo remove rimuove con successo l'oggetto dal set originale, allora
		 * si procede alla rimozione dal database */
		try {
			if (vendite.remove(this.getVenditaByCodice(codiceVendita))) {
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
