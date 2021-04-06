package gui.guibulloni;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import bulloni.Bullone;
import gestori.gestoribulloni.VisualizzaBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;
import utility.Data;

/**
 * Questa classe contiene tutti gli attributi, i metodi e le impostazioni del layout per creare una finestra capace di cercare un bullone.
 * 
 * @author Catignano Francesco
 */
public class RicercaBulloneFrame extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int MAX_WIDTH = 320;
	private static final int MAX_HEIGHT = 200;
	private static final int MIN_ANNO = 1970;
	private static int MAX_ANNO = Data.getDataAttuale().getAnno();
	
	private static BodyBulloni mainPanel;
	private static VisualizzaBulloni visualizzaBulloni;
	
	/*
	 * Elementi visibili nell'interfaccia
	 */
	JLabel lblCercaPerCodice = new JLabel();
	JButton btnCercaPerCodice = new JButton();
	JTextField txtFieldCercaPerCodice = new JTextField();
	JLabel lblCercaPerAnno = new JLabel();
	JButton btnCercaPerAnno = new JButton();
	JComboBox<Integer> comboBoxCercaPerAnno = new JComboBox<Integer>();
	
	
	/*
	 * -------------
	 *  COSTRUTTORE
	 * -------------
	 */
	
	/**
	 * Costruisce una finestra per effettuare la ricerca dei bulloni.
	 * @param finestraPrincipale La finestra principale.
	 * @param pannelloPrincipale Il pannello principale.
	 * @param visualBulloni L'interfaccia di visualizzazione dei bulloni.
	 */
	public RicercaBulloneFrame(BodyBulloni pannelloPrincipale, VisualizzaBulloni visualBulloni) {
		mainPanel = pannelloPrincipale;
		visualizzaBulloni = visualBulloni;
		
		// Creazione finestra
		this.setTitle("Ricerca bullone");
		this.setBounds(100, 100, MAX_WIDTH, MAX_HEIGHT);
		this.setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Creazione layout
		GridBagLayout gblForRicercaBulloneFrame = new GridBagLayout();
		gblForRicercaBulloneFrame.columnWidths = new int[] {0, 0};
		gblForRicercaBulloneFrame.rowHeights = new int[] {0, 0, 0, 0};
		gblForRicercaBulloneFrame.columnWeights = new double[] {0.0, Double.MIN_VALUE};
		gblForRicercaBulloneFrame.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0};
		this.getContentPane().setLayout(gblForRicercaBulloneFrame);
		
		this.creaCercaPerCodice();
		this.cercaPerAnno();
	}
	
	
	/*
	 *-------------------
	 *  METODI PUBBLICI
	 *-------------------
	 */
	
	/*
	 * Trigger dei pulsanti
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Pulsante "cerca per codice"
		if(e.getSource()==this.btnCercaPerCodice) {
			try {
				int codice = Integer.valueOf(this.txtFieldCercaPerCodice.getText());
				Set<Bullone> bulloniTrovati = new HashSet<Bullone>();
				bulloniTrovati.add(visualizzaBulloni.getBulloneByCodice(codice));
				mainPanel.setBtnCercaPerVisible(false);
				mainPanel.setBtnVisualizzaTuttoVisible(true);
				mainPanel.refresh(bulloniTrovati);
				this.dispose();
			}
			catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Inserisci un numero!", "Errore ricerca!", JOptionPane.ERROR_MESSAGE);
			}
			catch(GestoreBulloniException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore ricerca!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// Pulsante "cerca per anno"
		if(e.getSource()==this.btnCercaPerAnno) {
			int anno = (Integer)this.comboBoxCercaPerAnno.getSelectedItem();
			try {
				Set<Bullone> bulloniTrovati = new HashSet<Bullone>();
				bulloniTrovati.addAll(visualizzaBulloni.getBulloniByAnno(anno));
				mainPanel.refresh(bulloniTrovati);
				mainPanel.setBtnCercaPerVisible(false);
				mainPanel.setBtnVisualizzaTuttoVisible(true);
				this.dispose();
			}
			catch(GestoreBulloniException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore ricerca!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
	/*
	 *------------------
	 *  METODI PRIVATI
	 *------------------
	 */
	
	/**
	 * Metodo per creare il layout e aggiungere alla finestra gli elementi per effettuare una ricerca per codice.
	 * Il layout utilizzato e' il GridBagLayout.
	 */
	private void creaCercaPerCodice() {
		/*
		 * Label per la ricerca per codice
		 */
		this.lblCercaPerCodice.setText("Cerca per codice");
		GridBagConstraints gbcForLblCercaPerCodice = new GridBagConstraints();
		gbcForLblCercaPerCodice.gridx = 1;
		gbcForLblCercaPerCodice.gridy = 0;
		gbcForLblCercaPerCodice.anchor = GridBagConstraints.LINE_START;
		gbcForLblCercaPerCodice.insets = new Insets(5, 5, 0, 5);
		this.getContentPane().add(this.lblCercaPerCodice, gbcForLblCercaPerCodice);
		
		/*
		 * Bottone per la ricerca per codice
		 */
		this.btnCercaPerCodice.setText("Cerca");
		GridBagConstraints gbcForBtnCercaPerCodice = new GridBagConstraints();
		gbcForBtnCercaPerCodice.gridx = 0;
		gbcForBtnCercaPerCodice.gridy = 1;
		gbcForBtnCercaPerCodice.anchor = GridBagConstraints.LINE_START;
		gbcForBtnCercaPerCodice.insets = new Insets(5, 5, 5, 5);
		this.getContentPane().add(this.btnCercaPerCodice, gbcForBtnCercaPerCodice);
		this.btnCercaPerCodice.addActionListener(this);
		
		/*
		 * Text field per la ricerca per codice
		 */
		GridBagConstraints gbcForTxtFieldCercaPerCodice = new GridBagConstraints();
		gbcForTxtFieldCercaPerCodice.gridx = 1;
		gbcForTxtFieldCercaPerCodice.gridy = 1;
		gbcForTxtFieldCercaPerCodice.anchor = GridBagConstraints.LINE_START;
		gbcForTxtFieldCercaPerCodice.insets = new Insets(5, 5, 1, 5);
		gbcForTxtFieldCercaPerCodice.fill = GridBagConstraints.HORIZONTAL;
		this.getContentPane().add(this.txtFieldCercaPerCodice, gbcForTxtFieldCercaPerCodice);
	}
	
	
	/**
	 * Metodo per creare il layout e aggiungere alla finestra gli elementi per effettuare una ricerca per anno di produzione.
	 * Poiche' e' molto piu' probabile che venga effettuata una ricerca per anno di produzione, piuttosto che per data precisa, questa finestra
	 * permette solamente la ricerca per anno.
	 * Il layout utilizzato e' il GridBagLayout.
	 */
	private void cercaPerAnno() {
		/*
		 * Label per la ricerca per anno
		 */
		this.lblCercaPerAnno.setText("Cerca per anno di produzione");
		GridBagConstraints gbcForLblCercaPerAnno = new GridBagConstraints();
		gbcForLblCercaPerAnno.gridx = 1;
		gbcForLblCercaPerAnno.gridy = 2;
		gbcForLblCercaPerAnno.anchor = GridBagConstraints.LINE_START;
		gbcForLblCercaPerAnno.insets = new Insets(10, 5, 0, 5);
		this.getContentPane().add(this.lblCercaPerAnno, gbcForLblCercaPerAnno);
		
		/*
		 * Bottone per la ricerca per anno
		 */
		this.btnCercaPerAnno.setText("Cerca");
		GridBagConstraints gbcForBtnCercaPerAnno = new GridBagConstraints();
		gbcForBtnCercaPerAnno.gridx = 0;
		gbcForBtnCercaPerAnno.gridy = 3;
		gbcForBtnCercaPerAnno.anchor = GridBagConstraints.LINE_START;
		gbcForBtnCercaPerAnno.insets = new Insets(5, 5, 5, 5);
		this.getContentPane().add(this.btnCercaPerAnno, gbcForBtnCercaPerAnno);
		this.btnCercaPerAnno.addActionListener(this);
		
		/*
		 * ComboBox per la ricerca per anno
		 */
		this.comboBoxCercaPerAnno.setMaximumRowCount(10);
		this.comboBoxCercaPerAnno.setModel(new DefaultComboBoxModel<Integer>());
		for(Integer i=MAX_ANNO; i>=MIN_ANNO; i--) {
			this.comboBoxCercaPerAnno.addItem(i);
		}
		GridBagConstraints gbcForComboBoxCercaPerAnno = new GridBagConstraints();
		gbcForComboBoxCercaPerAnno.gridx = 1;
		gbcForComboBoxCercaPerAnno.gridy = 3;
		gbcForComboBoxCercaPerAnno.anchor = GridBagConstraints.LINE_START;
		gbcForComboBoxCercaPerAnno.insets = new Insets(5, 5, 1, 5);
		gbcForComboBoxCercaPerAnno.fill = GridBagConstraints.HORIZONTAL;
		this.getContentPane().add(this.comboBoxCercaPerAnno, gbcForComboBoxCercaPerAnno);
	}

}