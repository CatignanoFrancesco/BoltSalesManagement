/**
 * 
 */
package gui.guiImpiegati;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gui.ScreenManager;
import persona.Impiegato;
import persona.exception.ExceptionImpiegato;

/**
 * @author Francolino Flavio Domenico
 * 
 *         classe che implementa l'action listener da assegnare ai bottoni
 *         presenti sulla schemata che visualizza gli impiegati
 *
 */
class BodyImpiegatoBtnListener implements ActionListener {

	// action command assegnabili ai bottoni 
	// presenti sulla schemata che visuaizza gli impiegati
	public static final String BTN_AGGIUNGI = "aggiungiImpiegato";
	public static final String BTN_DETTAGLI = "dettagliImpiegato";
	public static final String BTN_PROMUOVI = "promuoviImpiegato";
	public static final String BTN_LICENZIA = "licenziaImpiegato";

	private PannelloImpiegato pannello;// pannelloImpiegato in cui è stato cliccato un bottone

	private static BodyImpiegati bodyImpiegati;// BodyImpiegato in cui è stato cliccato un bottone o nel quale aggiungere dei
										// pannelli Impiegati

	/**
	 * costruttore che inizializza il pannelloImpiegati  di cui rilevare il click su un
	 * bottone
	 * 
	 * @param pannello il pannelloImpiegato di cui rilevare il click su un bottone
	 */
	public BodyImpiegatoBtnListener(PannelloImpiegato pannello, BodyImpiegati bodyImpiegati) {

		this.pannello = pannello;
		BodyImpiegatoBtnListener.bodyImpiegati = bodyImpiegati;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		String eAction = e.getActionCommand();// determino il bottone che è stato cliccato

		switch (eAction) {

		case BTN_DETTAGLI:

			new DettagliImpiegatoWindow(this.pannello.getImpiegato());
			break;

		case BTN_PROMUOVI:

			new PromuoviImpiegatoWindow(this.pannello.getImpiegato());
			break;

		case BTN_LICENZIA:

			this.licenzia();
			break;

		default:// BTN_AGGIUNGI

			this.aggiungi();
			break;
		}

	}

	/**
	 * questo metodo si occupa d'implementare le funzionalità associate al bottone licenzia
	 */
	private void licenzia() {

		int conferma = JOptionPane.showConfirmDialog(ScreenManager.getParentWindow(), "sicuro di voler licenziare questo impiegato");
		
		if (conferma == 0) {

			try {

				ScreenManager.getGi().licenziaImpiegato(this.pannello.getImpiegato().getID());//effettuo la modicfica sul db

				bodyImpiegati.rimuoviPannelloImpiegato(this.pannello);//rimuovo il panello grafico che lo visualizza
				
				if(BodyImpiegati.getListaImpiegati().getComponentCount() == 0) {//se l'impiegato licenziato era l'unico visualzzato
					
					bodyImpiegati.rimuoviLista();//nascondo la lista e faccio visualizzare il messaggio che non ci sono impiegati
					
				}

			} catch (ExceptionGestoreImpiegato | SQLException | DatabaseSQLException | ExceptionImpiegato e) {

				JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), e.getMessage(), "exception",
						JOptionPane.ERROR_MESSAGE);

			} catch (Exception e) {

				JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), e.getMessage(), "exception",
						JOptionPane.ERROR_MESSAGE);
			}
			
			
		}

	}
	
	/**
	 * questo metodo si occupa d'implementare le funzionalità associate al bottone aggiungi
	 */
	private void aggiungi() {
		
		AggiungiImpiegatoWindows ai = new AggiungiImpiegatoWindows();//aggiungo l'impiegato al db
		
		Impiegato i = ai.getImpiegato();
		
		if (i != null) {//se  è stato effetivamengte creato l'impiegato e non si è uscito dalla finestra premendo la x
			
			if(BodyImpiegati.getListaImpiegati().isVisible()) {//se lista risulta visibile poiche visualizza gia alcuni impiegati
			
				bodyImpiegati.aggiungiPannelloImpiegato(new PannelloImpiegato(i, bodyImpiegati));//aggiungo il pannello che visualizza il nuovo impiegato
			
			} else {//devo visualizzare il primo impiegato creato
				
				bodyImpiegati.aggiungiLista();//visualizzo la lista
				
				bodyImpiegati.aggiungiPannelloImpiegato(new PannelloImpiegato(i, bodyImpiegati));//aggiungo il pannello che visualizza il nuovo impiegato/
				
			}
	
		}
	}
}
