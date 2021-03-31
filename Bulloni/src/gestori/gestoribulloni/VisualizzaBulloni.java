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
}
