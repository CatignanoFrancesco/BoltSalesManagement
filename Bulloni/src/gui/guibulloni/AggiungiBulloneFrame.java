package gui.guibulloni;

import javax.swing.JFrame;

/**
 * Classe per modellare il layout ed il funzionamento della finestra di aggiunta di un bullone.
 * 
 * @author Catignano Francesco
 */
public class AggiungiBulloneFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static JFrame mainFrame;
	private BodyBulloni mainPanel;
	
	
	/*
	 * -------------
	 *  COSTRUTTORE
	 * -------------
	 */
	
	/**
	 * Costruisce una finestra per l'aggiunta di un bullone.
	 * 
	 * @param finestraPrincipale La finestra principale.
	 * @param pannelloPrincipale Il pannello principale (per effettuare le operazioni di refresh).
	 */
	public AggiungiBulloneFrame(JFrame finestraPrincipale, BodyBulloni pannelloPrincipale) {
		mainFrame = finestraPrincipale;
		mainPanel = pannelloPrincipale;
	}
	
	
	/*
	 * -----------------
	 *  METODI PUBBLICI
	 * -----------------
	 */
	
	
	/*
	 * ----------------
	 *  METODI PRIVATI
	 * ----------------
	 */
}
