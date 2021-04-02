package gui.guivendite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gestori.gestorevendite.ModificaVendite;
import gestori.gestorevendite.VisualizzazioneVendite;
import gestori.gestorevendite.exception.GestoreVenditaException;

/**
 * @author GiannettaGerardo
 *
 */
public class ModificaVenditaBullone extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
	
	/** coordinata x della finestra */
	private static final int X = 100;
	/** coordinata y della finestra */
	private static final int Y = 100;
	/** larghezza della finestra */
	private static final int WIDTH = 310;
	/** lunghezza della finestra */
	private static final int HEIGHT = 126;
	
	private JFrame mainJFrame;
	private JTextField textField;
	private JLabel messaggioPrincipale;
	private JButton modificaButton;
	private JFrame finestraCorrente = this;
	/** titolo della finestra grafica */
	private final String titoloFinestra = "Modifica";
	
	/** gestore delle vendite con interfaccia contenente i metodi di modifica */
	private ModificaVendite gestoreVendite;
	
	/** codice della vendita selezionata per la modifica */
	private int codiceVendita;
	
	/** codice del bullone selezionato per la modifica nella vendita */
	private int codiceBullone;
	
	
	
	/**
	 * Costruttore della classe ModificaVenditaBullone
	 * 
	 * @param mFrame finestra principale da bloccare
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param codiceVendita codice della vendita selezionata per la modifica 
	 * @param codiceBullone codice del bullone selezionato per la modifica nella vendita
	 */
	public ModificaVenditaBullone(JFrame mFrame, ModificaVendite gestoreVendite, int codiceVendita, int codiceBullone) {
		this.mainJFrame = mFrame;
		this.gestoreVendite = gestoreVendite;
		this.codiceVendita = codiceVendita;
		this.codiceBullone = codiceBullone;
		
		inizializza();
		createModificaButton();
		
	}
	
	
	
	/**
	 * Metodo che inizializza i valori della finestra
	 */
	public void inizializza() {
		
		// creo la finestra
		setResizable(false);
		setAlwaysOnTop(true);
		addWindowListener(this);
		setTitle(titoloFinestra);
		setBounds(X, Y, WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
	}
	
	
	
	/**
	 * Metodo che crea il pulsante di modifica insieme al textField che conterra' il nuovo valore da modificare e ne gestisce l'evento
	 */
	public void createModificaButton() {
		
		messaggioPrincipale = new JLabel("Inserisci una nuova quantita' di bulloni venduti:");
		messaggioPrincipale.setBounds(10, 5, 323, 13);
		getContentPane().add(messaggioPrincipale);
		
		textField = new JTextField();
		textField.setBounds(96, 28, 150, 25);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		modificaButton = new JButton("Modifica");
		messaggioPrincipale.setLabelFor(modificaButton);
		modificaButton.setBounds(10, 28, 88, 25);
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					gestoreVendite.updateNumeroBulloniVendutiByCodici(codiceVendita, codiceBullone, Integer.parseUnsignedInt(textField.getText()));
				}
				catch (NumberFormatException t) {
					JOptionPane.showMessageDialog(finestraCorrente, "E' stato inserito un numero negativo oppure non e' stato inserito un numero.", "Exception", JOptionPane.ERROR_MESSAGE);
				} 
				catch (GestoreVenditaException t) {
					JOptionPane.showMessageDialog(finestraCorrente, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				} 
				catch (ExceptionGestoreImpiegato t) {
					JOptionPane.showMessageDialog(finestraCorrente, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				} 
				catch (DatabaseSQLException t) {
					JOptionPane.showMessageDialog(finestraCorrente, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				} 
				catch (SQLException t) {
					JOptionPane.showMessageDialog(finestraCorrente, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		getContentPane().add(modificaButton);
		
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
