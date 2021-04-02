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
	
}
