package gui.guibulloni;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Pannello che corrisponde ad una singola riga nella lista di bulloni.
 * In questa classe sono presenti tutte le etichette per riassumere in maniera generale le informazioni su uno specifico bullone (es. codice, tipo e prezzo)
 * e i bottoni per la manipolazione (informazioni aggiuntive, modifica ed eliminazione). L'utilizzo di un singolo pannello per ogni bullone permette di creare
 * una migliore associazione tra il singolo bullone e le varie etichette e pulsanti.
 * 
 * @author Catignano Francesco
 */
class SimpleInfoBullonePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JFrame mainFrame;	// Finestra principale
	private BodyBulloni mainPanel;	// Pannello principale da cui deriva
	
	
	/*
	 * -------------
	 *  COSTRUTTORE
	 * -------------
	 */
	SimpleInfoBullonePanel(JFrame mainFrame, BodyBulloni mainPanel) {
		this.mainFrame = mainFrame;
		this.mainPanel = mainPanel;
	}
}
