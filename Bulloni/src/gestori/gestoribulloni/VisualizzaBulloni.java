package gestori.gestoribulloni;

import java.util.Set;

import bulloni.Bullone;
import gestori.gestoribulloni.exception.GestoreBulloniException;

/**
 * Interfaccia contenente i metodi per visualizzare le informazioni dei bulloni.
 * @author Catignano Francesco
 *
 */
public interface VisualizzaBulloni {
	
	/**
	 * Restituisce una copia del set di bulloni, in modo da evitare modifiche o rimozioni che non coincidono
	 * con quanto memorizzato nel database.
	 * @return bulloniCopy La copia del set di bulloni
	 */
	public Set<Bullone> getAll();
	
	/**
	 * Questo metodo si occupa di cercare un bullone nel set a partire dal codice ricevuto in input e restituire un array di stringhe contenente le informazioni
	 * generali del bullone, gia' pronte per essere visualizzate.
	 * Le informazioni seguono quest'ordine:
	 * - tipo (la classe a cui appartiene);
	 * - codice;
	 * - data di produzione;
	 * - luogo di produzione;
	 * - il peso;
	 * - il prezzo;
	 * - il materiale;
	 * - la lunghezza;
	 * - il diametro della vite;
	 * - il diametro del dado;
	 * - il tipo di innesto.
	 * @param codice Il codice del bullone da cercare
	 * @return L'array contenente le informazioni
	 * @throws GestoreBulloniException L'eccezione sollevata quando non e' stato trovato alcun bullone.
	 */
	public String[] getInfoBulloneByCodice(int codice) throws GestoreBulloniException;
	
	/**
	 * Questo metodo cerca nel set di bulloni tutti i bulloni che non sono stati eliminati e restituisce un set.
	 * Il set contiene solo i cloni dei bulloni.
	 * @return bulloniDisponibili, il set di bulloni che non sono stati eliminati.
	 */
	public Set<Bullone> getBulloniDisponibili();
	
	/**
	 * Restituisce una copia del bullone, partendo da un codice ricevuto in input.
	 * Effettua una ricerca nel set confrontando il codice ricevuto in input con il codice
	 * di ogni bullone presente nel set. Se trova il bullone, lo restituisce,
	 * altrimenti viene sollevata un'eccezione.
	 * Viene restituito un clone, in modo tale da evitare modifiche accidentali al bullone presente nel set,
	 * senza che questa modifica sia sincronizzata con il database.
	 * @param codice Il codice del bullone da cercare.
	 * @return b Il clone del bullone trovato.
	 * @throws GestoreBulloniException L'eccezione sollevata se il bullone non e' stato trovato.
	 */
	public Bullone getBulloneByCodice(int codice) throws GestoreBulloniException;
	
	/**
	 * Restituisce una copia del bullone, partendo da un codice ricevuto in input.
	 * Effettua una ricerca nel set confrontando il codice ricevuto in input con il codice
	 * di ogni bullone presente nel set. Se trova il bullone, e questo non è stato eliminato, lo restituisce,
	 * altrimenti viene sollevata un'eccezione.
	 * Viene restituito un clone, in modo tale da evitare modifiche accidentali al bullone presente nel set,
	 * senza che questa modifica sia sincronizzata con il database.
	 * @param codice Il codice del bullone da cercare.
	 * @return b Il clone del bullone trovato.
	 * @throws GestoreBulloniException L'eccezione sollevata se il bullone non e' stato trovato.
	 */
	public Bullone getBulloneDisponibileByCodice(int codice) throws GestoreBulloniException;
	
	/**
	 * Restituisce un set di bulloni il cui anno di produzione coincide con l'anno ricevuto come parametro. Vengono restituiti solo bulloni NON eliminati.
	 * Se il set e' vuoto, viene sollevata un'eccezione.
	 * @param anno L'anno di produzione dei bulloni da ricercare.
	 * @return bulloni Il set di bulloni cercato.
	 * @throws GestoreBulloniException L'eccezione sollevata se il set di bulloni e' vuoto (cioe' se non esistono bulloni corrispondenti a quell'anno).
	 */
	public Set<Bullone> getBulloniByAnno(int anno) throws GestoreBulloniException;
	
	/**
	 * Questo metodo controlla lo stato del set interno "bulloni" per accertarsi che ci siano dei bulloni all'interno.
	 * @return true se il set e' vuoto, false altrimenti.
	 */
	public boolean isEmpty();
}
