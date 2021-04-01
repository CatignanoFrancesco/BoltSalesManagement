package gui.guivendite;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @author GiannettaGerardo
 *
 */
public class InputForm extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
	private JTextField codiceVenditaTextField;
	private JFrame inputFormFrame = this;
	private JFrame mainJFrame;
	private Integer[] matricoleImpiegato;
	private JLabel headerLabel;
	private JLabel codiceVenditaLabel;
	private JLabel dataVenditaLabel;
	private JComboBox<Integer> giornoComboBox;
	private JLabel giornoLabel;
	private JComboBox<Integer> meseComboBox;
	private JLabel meseLabel;
	private JComboBox<Integer> annoComboBox;
	private JLabel annoLabel;
	private JLabel impiegatoLabel;
	private JComboBox<Integer> impiegatoComboBox;
	private JLabel bulloniLabel;
	private JButton aggiungiVenditaButton;
	private JScrollPane scrollPane;
	private JPanel panel;
	private final String titoloFinestra = "Aggiungi vendita";
	
	
	public InputForm(JFrame mainJF) {
		
		this.mainJFrame = mainJF;
		
		setResizable(false);
		setAlwaysOnTop(true);
		addWindowListener(this);
		inizializza();
		
	}
	
	
	/**
	 * Metodo che inizializza i valori della finestra e aggiunge una label per informare l'utente di quello che dovrà fare
	 */
	private void inizializza() {
		
		// creo la finestra
		setTitle(titoloFinestra);
		setBounds(100, 100, 450, 404);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		// creo una label informativa per l'utente
		headerLabel = new JLabel("Compila tutti i campi per aggiungere una nuova vendita:");
		headerLabel.setBounds(10, 10, 346, 13);
		getContentPane().add(headerLabel);
	}
	
	
	
	
	

	@Override
	/**
	 * Metodo che alla chiusura di questa finestra, riattiva la finestra precedente
	 */
	public void windowClosing(WindowEvent e) {
		if (this.mainJFrame != null) {
			this.mainJFrame.setEnabled(true);
		}

	}


	@Override
	/**
	 * Metodo dell'interfaccia WindowListener che non serve, quindi rimarrà vuoto
	 */
	public void windowOpened(WindowEvent e) {}
	@Override
	/**
	 * Metodo dell'interfaccia WindowListener che non serve, quindi rimarrà vuoto
	 */
	public void windowClosed(WindowEvent e) {}
	@Override
	/**
	 * Metodo dell'interfaccia WindowListener che non serve, quindi rimarrà vuoto
	 */
	public void windowIconified(WindowEvent e) {}
	@Override
	/**
	 * Metodo dell'interfaccia WindowListener che non serve, quindi rimarrà vuoto
	 */
	public void windowDeiconified(WindowEvent e) {}
	@Override
	/**
	 * Metodo dell'interfaccia WindowListener che non serve, quindi rimarrà vuoto
	 */
	public void windowActivated(WindowEvent e) {}
	@Override
	/**
	 * Metodo dell'interfaccia WindowListener che non serve, quindi rimarrà vuoto
	 */
	public void windowDeactivated(WindowEvent e) {}

}
