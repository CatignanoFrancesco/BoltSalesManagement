package gestori.gestorevendite;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import java.sql.ResultSet;
import java.sql.SQLException;

import utility.Data;
import vendita.*;
import vendita.exception.VenditaException;
import persona.Impiegato;
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
 * un set di oggetti Vendita<MerceVenduta, Impiegato> tramite database e uso di altri costruttori,
 * inoltre permette l'inseriemento di una nuova vendita, l'aggiornamento e l'eliminazione di una 
 * vendita esistente, oltre che la ricerca e restituzione di oggetti vendita in base a specifici parametri
 */
public class GestoreVendita {
	
	/** Set contenente oggetti VenditaBulloni prelevati dal database */
	Set<Vendita<MerceVenduta, Impiegato>> vendite = new HashSet<Vendita<MerceVenduta, Impiegato>>();
	
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
		
		// aggiungere controlli sui gestori
		
		// HashMap contenente come chiave il codice della vendita, come oggetto associato un Set di MerceVenduta
		Map<Integer, Set<MerceVenduta>> merce = selectMerceVenduta(gb);
		
		// se l'HashMap ritornato è vuoto, significa che il database ha la tabella MerceVenduta vuota, quindi si solleva un'eccezione
		if (merce.isEmpty()) {
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.MERCE_VENDUTA_NULLA, new GestoreVenditaException());
		}
		else {
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
		
		// imposta il nuovo codice automatico delle vendite
		codVenditaAutomatico = getNewCodVenditaAutomatico();
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
	private Map<Integer, Set<MerceVenduta>> selectMerceVenduta(GestoreBulloni gb) {
		
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
	
	
	
	@SuppressWarnings("unchecked")
	/**
	 * Metodo che ritorna un Set<Vendita<MerceVenduta, Impiegato>> di cloni dell'originale
	 * 
	 * @return Set<Vendita<MerceVenduta, Impiegato>> di cloni
	 * @throws GestoreVenditaException
	 */
	public Set<Vendita<MerceVenduta, Impiegato>> getVendite() throws GestoreVenditaException {
		
		Set<Vendita<MerceVenduta, Impiegato>> risultato = new HashSet<Vendita<MerceVenduta, Impiegato>>();
		
		for (Vendita<MerceVenduta, Impiegato> v : vendite)
			risultato.add((Vendita<MerceVenduta, Impiegato>)v.clone());
		
		if (risultato.isEmpty())
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITE_VUOTE, new GestoreVenditaException());
		
		return risultato;
	}
	
	
	
	@SuppressWarnings("unchecked")
	/**
	 * Metodo che ritorna il clone di un oggetto Vendita<MerceVenduta, Impiegato>
	 * in base ad un codice vendita passato in input
	 * 
	 * @param codiceVendita codice da cercare
	 * @return l'oggetto Vendita<MerceVenduta, Impiegato> trovato
	 * @throws GestoreVenditaException
	 */
	public Vendita<MerceVenduta, Impiegato> getVenditaByCodice(int codiceVendita) throws GestoreVenditaException {
		
		Vendita<MerceVenduta, Impiegato> risultato = null;
		
		for (Vendita<MerceVenduta, Impiegato> v : vendite) {
			if (v.getCodVendita() == codiceVendita)
				risultato = (Vendita<MerceVenduta, Impiegato>)v.clone();
		}
		
		if (risultato == null)
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITA_NON_TROVATA, new GestoreVenditaException());
		
		return risultato;
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	/**
	 * Metodo che ritorna il clone di un Set<Vendita<MerceVenduta, Impiegato>>
	 * in base ad una data passata in input
	 * 
	 * @param dataVendita data di effettuazione della vendita
	 * @return il Set<Vendita<MerceVenduta, Impiegato>> trovato
	 * @throws GestoreVenditaException
	 */
	public Set<Vendita<MerceVenduta, Impiegato>> getVenditeByData(Data dataVendita) throws GestoreVenditaException {
		
		Set<Vendita<MerceVenduta, Impiegato>> risultato = new HashSet<Vendita<MerceVenduta, Impiegato>>();
		
		for (Vendita<MerceVenduta, Impiegato> v : vendite) {
			if (v.getData().compareTo(dataVendita) == 0)
				risultato.add((Vendita<MerceVenduta, Impiegato>)v.clone());
		}
		
		if (risultato.isEmpty())
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITE_NON_TROVATE_DATA, new GestoreVenditaException());
		
		return risultato;
	}
	
	
	
	@SuppressWarnings("unchecked")
	/**
	 * Metodo che ritorna il clone di un Set<Vendita<MerceVenduta, Impiegato>>
	 * in base al codice impiegato passato in input
	 * 
	 * @param codiceImpiegato codice impiegato da cercare nel Set
	 * @return il Set<Vendita<MerceVenduta, Impiegato>> trovato
	 * @throws GestoreVenditaException
	 */
	public Set<Vendita<MerceVenduta, Impiegato>> getVenditeByImpiegato(int codiceImpiegato) throws GestoreVenditaException {
		
		Set<Vendita<MerceVenduta, Impiegato>> risultato = new HashSet<Vendita<MerceVenduta, Impiegato>>();
		
		for (Vendita<MerceVenduta, Impiegato> v : vendite) {
			if (v.getResponsabileVendita().getID() == codiceImpiegato)
				risultato.add((Vendita<MerceVenduta, Impiegato>)v.clone());
		}
		
		if (risultato.isEmpty())
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITE_NON_TROVATE_IMPIEGATO, new GestoreVenditaException());
		
		return risultato;
	}
	
	
	
	
	public void aggiungiVendita(Vendita<MerceVenduta, Impiegato> vendita) throws GestoreVenditaException {
		
		if (vendita == null) {
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITA_NULLA, new GestoreVenditaException());
		}
		
		for (Vendita<MerceVenduta, Impiegato> v : vendite) {
			if (v.getCodVendita() == vendita.getCodVendita())
				throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.VENDITA_ESISTENTE, new GestoreVenditaException());
		}
		
		if (vendita.getCodVendita() < 0)
			throw new GestoreVenditaException(MsgErroreGestoreVendita.INTESTAZIONE + MsgErroreGestoreVendita.CODICE_VENDITA_NEGATIVO, new GestoreVenditaException());
		
		vendite.add(vendita);
		
        // aggiungere la insert nel database

		
	}
	
	
	
	/**
	 * Metodo che ritorna il codice della vendita automatico uguale al massimo tra i codici presenti nel Set + 1; 
	 * 
	 * @return il codice massimo tra quelli presenti nel Set + 1
	 */
	public int getNewCodVenditaAutomatico() {
		
		int max = 0;
		
		for (Vendita<MerceVenduta, Impiegato> v : vendite) {
			if (v.getCodVendita() > max)
				max = v.getCodVendita();
		}
		
		return ++max;
	}

}
