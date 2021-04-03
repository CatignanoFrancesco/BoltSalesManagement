package gui.guibulloni;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import gestori.gestoribulloni.AggiungiBulloni;

/**
 * Classe per modellare il layout ed il funzionamento della finestra di aggiunta di un bullone.
 * 
 * @author Catignano Francesco
 */
public class AggiungiBulloneFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_WIDTH = 400;
	private static final int MAX_HEIGHT = 500;
	
	private static JFrame mainFrame;
	private BodyBulloni mainPanel;
	private static AggiungiBulloni aggiungiBulloni;
	
	
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
	 * @param addBulloni L'interfaccia contenente le operazioni per effettuare l'aggiunta di un bullone.
	 */
	public AggiungiBulloneFrame(JFrame finestraPrincipale, BodyBulloni pannelloPrincipale, AggiungiBulloni addBulloni) {
		mainFrame = finestraPrincipale;
		mainPanel = pannelloPrincipale;
		aggiungiBulloni = addBulloni;
		
		// Modellazione finestra
		this.setTitle("Aggiungi bullone");
		this.setBounds(100, 100, MAX_WIDTH, MAX_HEIGHT);
		this.setResizable(false);
		this.getContentPane().setLayout(new BorderLayout());
		this.addWindowListener(this);
	}
	
	
	/*
	 * -----------------
	 *  METODI PUBBLICI
	 * -----------------
	 */
	
	@Override
	public void windowOpened(WindowEvent e) {}


	@Override
	public void windowClosing(WindowEvent e) {
		mainFrame.setEnabled(true);
	}


	@Override
	public void windowClosed(WindowEvent e) {}


	@Override
	public void windowIconified(WindowEvent e) {}


	@Override
	public void windowDeiconified(WindowEvent e) {}


	@Override
	public void windowActivated(WindowEvent e) {}


	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	
	/*
	 * ----------------
	 *  METODI PRIVATI
	 * ----------------
	 */
}
