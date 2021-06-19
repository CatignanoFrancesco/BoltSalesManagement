package gui.guivendite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gestori.gestorevendite.ModificaVendite;
import gestori.gestorevendite.VisualizzazioneVendite;
import gestori.gestorevendite.exception.GestoreVenditaException;

/**
 * Classe che rappresenta una finestra grafica che permette di modificare 
 * la merce venduta in una specifica vendita
 * 
 * @author GiannettaGerardo
 */
public class ModificaVenditaBullone extends JDialog {

	private static final long serialVersionUID = 1L;
	
	/** coordinata x della finestra */
	private static final int X = 100;
	/** coordinata y della finestra */
	private static final int Y = 100;
	/** larghezza della finestra */
	private static final int WIDTH = 380;
	/** lunghezza della finestra */
	private static final int HEIGHT = 126;
	
	// oggetti per creare l'interfaccia grafica
	
	/** istanza corrente della finestra VisualizzaMerceVenduta dalla quale viene chiamata questa finestra */
	private JDialog mainJDialog;
	/** conterrà il nuovo numero di bulloni inserito dall'utente per la modifica */
	private JTextField textField;
	/** messaggio che informa l'utente sull'utilizzo della finestra */
	private JLabel messaggioPrincipale;
	/** pulsante per salvare la modifica */
	private JButton modificaButton;
	/** istanza della finestra corrente */
	private JDialog finestraCorrente = this;
	/** titolo della finestra corrente */
	private final String titoloFinestra = "Modifica";
	
	/** gestore delle vendite con interfaccia contenente i metodi di modifica */
	private ModificaVendite gestoreVendite;
	
	/** codice della vendita selezionata per la modifica */
	private int codiceVendita;
	
	/** codice del bullone selezionato per la modifica nella vendita */
	private int codiceBullone;
	
	/** istanza corrente del body vendite */
	private BodyVendite istanzaCorrente;
	
	
	
	/**
	 * Costruttore della classe ModificaVenditaBullone
	 * 
	 * @param mFrame finestra principale da bloccare
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param codiceVendita codice della vendita selezionata per la modifica 
	 * @param codiceBullone codice del bullone selezionato per la modifica nella vendita
	 */
	public ModificaVenditaBullone(JDialog mDialog, ModificaVendite gestoreVendite, int codiceVendita, int codiceBullone, BodyVendite istanzaCorrente) {
		this.mainJDialog = mDialog;
		this.gestoreVendite = gestoreVendite;
		this.codiceVendita = codiceVendita;
		this.codiceBullone = codiceBullone;
		this.istanzaCorrente = istanzaCorrente;
		
		inizializza();
		createModificaButton();
		
	}
	
	
	
	/**
	 * Metodo che inizializza i valori della finestra
	 */
	public void inizializza() {
		
		// creo la finestra
		setModal(true);
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle(titoloFinestra);
		setBounds(X, Y, WIDTH, HEIGHT);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
	}
	
	
	
	/**
	 * Metodo che crea il pulsante di modifica insieme al textField che conterra' il nuovo valore da modificare e ne gestisce l'evento
	 */
	public void createModificaButton() {
		
		messaggioPrincipale = new JLabel("Inserisci una nuova quantita' di bulloni venduti:");
		messaggioPrincipale.setBounds(10, 5, 350, 13);
		getContentPane().add(messaggioPrincipale);
		
		// conterrà il nuovo numero di bulloni inserito dall'utente per la modifica 
		textField = new JTextField();
		textField.setBounds(110, 28, 220, 25);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		modificaButton = new JButton("Modifica");
		messaggioPrincipale.setLabelFor(modificaButton);
		modificaButton.setBounds(10, 28, 100, 25);
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int nuovoNumeroBulloni = Integer.parseUnsignedInt(textField.getText());
					
					// richiesta di conferma modifiche
					int risultato = JOptionPane.showConfirmDialog(finestraCorrente, "Salvare le modifiche?", "Salvataggio", JOptionPane.YES_NO_OPTION);
					
					// se risultato e' 0, significa che e' stato selezionato SI nella precendente richiesta di conferma
					if (risultato == 0) {
						
						gestoreVendite.updateNumeroBulloniVendutiByCodici(codiceVendita, codiceBullone, nuovoNumeroBulloni);
						
						istanzaCorrente.printListaVendite(((VisualizzazioneVendite)gestoreVendite).getVendite());
						
						((VisualizzaMerceVenduta)mainJDialog).printMerceVenduta(((VisualizzazioneVendite)gestoreVendite).getVenditaByCodice(codiceVendita).getMerceVenduta());
						dispose();
					}
				
				}
				catch (NumberFormatException t) {
					JOptionPane.showMessageDialog(finestraCorrente, "Non e' stato inserito un numero (positivo e senza virgola/punto).", "Exception", JOptionPane.ERROR_MESSAGE);
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
}
