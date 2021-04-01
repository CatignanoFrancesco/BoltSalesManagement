package gui.guivendite;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import gestori.gestoreImpiegati.GestoreImpiegatiDb;
import gestori.gestorevendite.InserimentoVendite;;

/**
 * @author GiannettaGerardo
 *
 */
public class InputForm extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
	
	// costanti
	private static final int X = 100;
	private static final int Y = 100;
	private static final int WIDTH = 450;
	private static final int HEIGHT = 404;
	
	// oggetti per creare l'interfaccia grafica
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
	
	// gestore delle vendite con interfaccia contenente i metodi di inserimento
	private InserimentoVendite gestoreVendite;
	
	// gestore degli impiegati con interfaccia di visualizzazione
	private GestoreImpiegatiDb gestoreImpiegati;
	
	
	public InputForm(JFrame mainJF, InserimentoVendite gestoreVendite, GestoreImpiegatiDb gestoreImpiegati) {
		
		this.mainJFrame = mainJF;
		this.gestoreVendite = gestoreVendite;
		this.gestoreImpiegati = gestoreImpiegati;
		
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
		setBounds(X, Y, WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		// creo una label informativa per l'utente
		headerLabel = new JLabel("Compila tutti i campi per aggiungere una nuova vendita:");
		headerLabel.setBounds(10, 10, 346, 13);
		getContentPane().add(headerLabel);
	}
	
	
	/**
	 * Metodo che crea il form per il codice della vendita;
	 * Il form non sarà editabile perché il codice viene inserito automaticamente dal gestore vendite
	 */
	public void createFormCodiceVendita() {
		
		// label corrispondente al codice della vendita
		codiceVenditaLabel = new JLabel("Codice vendita:");
		codiceVenditaLabel.setBounds(10, 55, 95, 13);
		getContentPane().add(codiceVenditaLabel);
		
		// text field per contenere il codice della vendita;
		codiceVenditaTextField = new JTextField();
		codiceVenditaLabel.setLabelFor(codiceVenditaTextField);
		codiceVenditaTextField.setText(((Integer)gestoreVendite.getCodVenditaAutomatico()).toString());
		codiceVenditaTextField.setEditable(false);
		codiceVenditaTextField.setBounds(104, 52, 69, 19);
		getContentPane().add(codiceVenditaTextField);
		codiceVenditaTextField.setColumns(10);
		
	}
	
	
	
	public void createFormData() {
		
		// label corrispondente alla data della vendita
		dataVenditaLabel = new JLabel("Data vendita:");
		dataVenditaLabel.setBounds(10, 110, 83, 13);
		getContentPane().add(dataVenditaLabel);
		
		// menu combobox èer scegliere il giorno del mese
		giornoComboBox = new JComboBox<Integer>();
		giornoComboBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}));
		giornoComboBox.setBounds(104, 106, 37, 21);
		getContentPane().add(giornoComboBox);
		
		giornoLabel = new JLabel("Giorno:");
		giornoLabel.setBounds(104, 90, 45, 13);
		getContentPane().add(giornoLabel);
		meseLabel.setLabelFor(giornoComboBox);
		
		meseComboBox = new JComboBox<Integer>();
		meseComboBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12}));
		meseComboBox.setBounds(156, 106, 37, 21);
		getContentPane().add(meseComboBox);
		
		meseLabel = new JLabel("Mese:");
		meseLabel.setBounds(156, 90, 45, 13);
		getContentPane().add(meseLabel);
		meseLabel.setLabelFor(meseComboBox);
		
		annoComboBox = new JComboBox<Integer>();
		
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
