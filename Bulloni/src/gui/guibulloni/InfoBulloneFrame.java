package gui.guibulloni;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoBulloneFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_WIDTH = 300;
	private static final int MAX_HEIGHT = 500;
	
	private JFrame mainFrame;
	private String[] infoBullone;
	
	/*
	 * Elementi visibili nella finestra
	 */
	private JPanel titlePanel = new JPanel();
	private JLabel titolo = new JLabel();
	
	
	/*
	 * -------------
	 *  COSTRUTTORE
	 * -------------
	 */
	
	/**
	 * Questo costruttore si occupa di mostrare le informazioni complete su un singolo bullone.
	 * @param mainFrame La finestra principale.
	 * @param infoBullone Le informazioni da mostrare.
	 */
	public InfoBulloneFrame(JFrame mainFrame, String[] infoBullone) {
		this.mainFrame = mainFrame;
		this.infoBullone = infoBullone;
		
		this.setTitle("Informazioni bullone");
		this.setResizable(false);
		this.setBounds(100, 100, MAX_WIDTH, MAX_HEIGHT);
		this.getContentPane().setLayout(new BorderLayout());
		this.addWindowListener(this);
		
		this.creaTitlePanel();
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
		this.mainFrame.setEnabled(true);
		this.mainFrame.setFocusable(true);
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
	
	/**
	 * Questo metodo si occupa di creare il pannello per il titolo e di impostarne il layout.
	 */
	private void creaTitlePanel() {
		
	}
	
	
	/**
	 * Questo metodo si occupa di creare e impostare il layout contenente le informazioni sul bullone.
	 */
	private void creaInfoPanel() {
		
	}
	
}
