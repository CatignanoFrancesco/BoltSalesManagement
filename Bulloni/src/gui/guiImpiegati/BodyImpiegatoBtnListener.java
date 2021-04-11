/**
 * 
 */
package gui.guiImpiegati;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Francolino Flavio Domenico
 * 
 * classe che implementa l'action listener da assegnare ai bottoni presenti sulla schemata che visualizza gli impiegati
 *
 */
class BodyImpiegatoBtnListener implements ActionListener {
	
	//action command assegnabili ai bottoni presenti sulla schemata che visuaizza  gli impiegati
	public static final String BTN_AGGIUNGI = "aggiungiImpiegato";
	public static final String BTN_DETTAGLI = "dettagliImpiegato";
	public static final String BTN_PROMUOVI = "promuoviImpiegato";
	public static final String BTN_LICENZIA = "licenziaImpiegato";
	
	private PannelloImpiegato pannello;//pannelloImpiegato in cui è stato cliccato un bottone
	
	/**
	 * costruttore che inizializza il pannello di cui rilevare il click su un bottone
	 * 
	 * @param pannello il pannello di cui rilevare il click su un bottone
	 */
	public BodyImpiegatoBtnListener(PannelloImpiegato pannello){
		
		this.pannello = pannello;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String eAction = e.getActionCommand();//determino il bottone che è stato cliccato
		
		switch (eAction) {
		
		case BTN_DETTAGLI:
			
			new DettagliImpiegatoWindow(this.pannello.getImpiegato());
			break;
			
		case BTN_PROMUOVI:
			
			new PromuoviImpiegatoWindow(this.pannello.getImpiegato());
			break;
			
		case BTN_LICENZIA:
			
			//code licenzia
			break;

		default://BTN_AGGIUNGI
			
			//code aggiungiu
			break;
		}
		
		

	}

}
