package gestori.gestoribulloni;


import java.util.Set;

import bulloni.Bullone;
import gestori.gestoribulloni.exception.GestoreBulloniException;

/**
 * Interfaccia contenente tutti i metodi pubblici presenti nella classe GestoreBulloni.
 * Estende le interfacce AggiungiBulloni, VisualizzaBulloni e ModificaBulloni, aggiungendo tutti i metodi che non sono presenti in nessuna delle tre interfacce.
 * @author Catignano Francesco
 *
 */
public interface ContainerBulloni extends AggiungiBulloni, VisualizzaBulloni, ModificaBulloni {
	/**
	 * Restituisce una copia del bullone, partendo da un codice ricevuto in input.
	 * Effettua una ricerca nel set confrontando il codice ricevuto in input con il codice
	 * di ogni bullone presente nel set. Se trova il bullone lo restituisce,
	 * altrimenti viene sollevata un'eccezione.
	 * Viene restituito un clone, in modo tale da evitare modifiche accidentali al bullone presente nel set,
	 * senza che questa modifica sia sincronizzata con il database.
	 * @param codice Il codice del bullone da cercare.
	 * @return b Il clone del bullone trovato.
	 * @throws GestoreBulloniException L'eccezione sollevata se il bullone non e' stato trovato.
	 */
	public Bullone getBulloneByCodice(int codice) throws GestoreBulloniException;
	
	/**
	 * Questo metodo controlla lo stato del set interno "bulloni" per accertarsi che ci siano dei bulloni all'interno.
	 * @return true se il set e' vuoto, false altrimenti.
	 */
	public boolean isEmpty();
}
