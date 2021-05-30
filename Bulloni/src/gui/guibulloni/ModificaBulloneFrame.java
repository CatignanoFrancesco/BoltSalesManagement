package gui.guibulloni;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import bulloni.exception.BulloneException;
import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoribulloni.ModificaBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;

/**
 * Questa classe contiene tutti i metodi e tutte le impostazioni per un layout di una finestra il cui compito e' quello di modificare un bullone.
 * 
 * @author Catignano Francesco
 */
public class ModificaBulloneFrame extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int MAX_WIDTH = 300;
	private static final int MAX_HEIGHT = 300;
	
	private static BodyBulloni mainPanel;
	private static ModificaBulloni modificaBulloni;
	private int codBullone;
	
	/*
	 * Elementi visibili nell'interfaccia
	 */
	JPanel titlePanel = new JPanel();
	JLabel lblTitolo = new JLabel();
	JPanel footerPanel = new JPanel();
	JButton btnSalva = new JButton();
	JPanel formPanel = new JPanel();
	JLabel lblPrezzo = new JLabel();
	JSpinner spinnerPrezzo = new JSpinner();
	JLabel lblEuro = new JLabel("\u20ac");
	
	
	/*
	 * -------------
	 *  COSTRUTTORE
	 * -------------
	 */
	
	/**
	 * Costruisce una finestra contenente le etichette e i campi principali per modificare un bullone.
	 * @param finestraPrincipale La finestra principale.
	 * @param pannelloPrincipale Il pannello principale.
	 * @param modBulloni L'interfaccia di modifica dei bulloni.
	 * @param codBullone Il codice del bullone che si sta modificando.
	 */
	public ModificaBulloneFrame(BodyBulloni pannelloPrincipale, ModificaBulloni modBulloni, int codBullone) {
		mainPanel = pannelloPrincipale;
		modificaBulloni = modBulloni;
		this.codBullone = codBullone;
		
		/*
		 * Impostazioni generali finestra
		 */
		this.setTitle("Modifica Bullone");
		this.setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, MAX_WIDTH, MAX_HEIGHT);
		this.getContentPane().setLayout(new BorderLayout());
		
		this.creaTitlePanel();
		this.creaFormPanel();
		this.creaFooterPanel();
	}

	
	/*
	 * -----------------
	 *  METODI PUBBLICI
	 * ----------------- 
	 */
	
	/*
	 * Trigger dei pulsanti
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Pulsante "Salva"
		if(e.getSource()==this.btnSalva) {
			int risultato = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler salvare?", "Salvataggio...", JOptionPane.YES_NO_OPTION);
			
			if(risultato==0) {	// 0 = si
				double nuovoPrezzo = (Double)this.spinnerPrezzo.getModel().getValue();	// ottiene il prezzo nuovo
				// Modifica
				try {
					modificaBulloni.updatePrezzoBulloneByCodice(this.codBullone, nuovoPrezzo);
					mainPanel.refresh();
					this.dispose();
				}
				catch (GestoreBulloniException | BulloneException | DatabaseSQLException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore salvataggio", JOptionPane.ERROR_MESSAGE);
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	
	/*
	 * ----------------
	 *  METODI PRIVATI
	 * ---------------- 
	 */
	
	/**
	 * Metodo per creare il pannello contenente il titolo.
	 * Usa il BorderLayout per posizionare il pannello in alto e aggiungere il titolo.
	 */
	private void creaTitlePanel() {
		this.getContentPane().add(this.titlePanel, BorderLayout.NORTH);
		
		this.lblTitolo.setText("MODIFICA BULLONE " + this.codBullone);
		this.setFont(new Font(this.lblTitolo.getFont().getFontName(), Font.BOLD, 16));
		this.titlePanel.add(this.lblTitolo);
	}
	
	
	/**
	 * Metodo per creare il form di modifica del bullone. Viene usato il GridBagLayout per posizionare gli elementi.
	 * I layout utilizzati sono: BorderLayout per il posizionamento di formPanel e GridBagLayout per il posizionamento degli elementi all'interno di formPanel.
	 */
	private void creaFormPanel() {
		this.getContentPane().add(this.formPanel, BorderLayout.CENTER);
		
		// Creazione layout
		GridBagLayout gblForFormPanel = new GridBagLayout();
		gblForFormPanel.columnWidths = new int[] {0, 0, 0};
		gblForFormPanel.rowHeights = new int[] {0, 0};
		gblForFormPanel.columnWeights = new double[] {0.0, 0.0, Double.MIN_VALUE};
		gblForFormPanel.rowWeights = new double[] {0.0, 0.0};
		this.formPanel.setLayout(gblForFormPanel);
		
		// Aggiunta elementi
		/*
		 * Label per il prezzo
		 */
		this.lblPrezzo.setText("Prezzo:");
		GridBagConstraints gbcForLblPrezzo = new GridBagConstraints();
		gbcForLblPrezzo.gridx = 0;
		gbcForLblPrezzo.gridy = 0;
		gbcForLblPrezzo.anchor = GridBagConstraints.LINE_START;
		gbcForLblPrezzo.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblPrezzo, gbcForLblPrezzo);
		
		/*
		 * Spinner per il prezzo
		 */
		this.spinnerPrezzo.setModel(new SpinnerNumberModel(0.50, 0.50, 2.50, 0.1));
		GridBagConstraints gbcForSpinnerPrezzo = new GridBagConstraints();
		gbcForSpinnerPrezzo.gridx = 1;
		gbcForSpinnerPrezzo.gridy = 0;
		gbcForSpinnerPrezzo.anchor = GridBagConstraints.LINE_START;
		gbcForSpinnerPrezzo.insets = new Insets(10, 10, 10, 10);
		gbcForSpinnerPrezzo.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.spinnerPrezzo, gbcForSpinnerPrezzo);
		
		/*
		 * Label per l'euro
		 */
		GridBagConstraints gbcForLblEuro = new GridBagConstraints();
		gbcForLblEuro.gridx = 2;
		gbcForLblEuro.gridy = 0;
		gbcForLblEuro.anchor = GridBagConstraints.LINE_START;
		gbcForLblEuro.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblEuro, gbcForLblEuro);
	}
	
	
	/**
	 * Metodo per creare il footer panel, contenente il bottone per salvare le modifiche.
	 * Usa il BorderLayout per posizionare il pannello in basso e aggiungere il pulsante.
	 */
	private void creaFooterPanel() {
		this.getContentPane().add(this.footerPanel, BorderLayout.SOUTH);
		
		this.btnSalva.setText("Salva");
		this.footerPanel.add(this.btnSalva);
		this.btnSalva.addActionListener(this);
	}

}
