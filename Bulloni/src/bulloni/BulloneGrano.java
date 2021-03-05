package bulloni;

import bulloni.exception.BulloneException;
import utility.Data;


/**
 * Classe concreta per il bullone di tipo "grano".
 * @author Catignano Francesco
 *
 */
public class BulloneGrano extends AbstractBullone {
	
	// COSTRUTTORE
	/**{@inheritDoc}
	 * 
	 */
	public BulloneGrano(int codice, Data dataProduzione, String luogoProduzione, double peso, double prezzo, Materiale materiale, double lunghezza, double diametroVite, Innesto innesto) throws BulloneException {
		super(codice, dataProduzione, luogoProduzione, peso, prezzo, materiale, lunghezza, diametroVite, innesto);
	}
	
	
	// OPERAZIONI
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public String toString() {
		return "Classe: " + this.getClass().getSimpleName() + "\n" + super.toString();
	}

}
