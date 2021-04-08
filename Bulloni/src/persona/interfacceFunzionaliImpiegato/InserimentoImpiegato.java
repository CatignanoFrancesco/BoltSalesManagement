/**
 * @author Francolino Flavio Dommenico
 * 
 * interfaccia contenente i metodi relativi all'aggiunta di un impiegato
 * in qual si voglia struttra dati o sistema di permanenza dei dati
 */

package persona.interfacceFunzionaliImpiegato;

import persona.Impiegato;

public interface InserimentoImpiegato {
	
	/**
	 * metodo per aggiungere un impiegato a qual si voglia struttura dati o sistema di permanenza di dati
	 * @param impiegato l'impiegato da aggiungere
	 */
	public void aggiungiImpiegato(Impiegato impiegato) throws Exception;

}
