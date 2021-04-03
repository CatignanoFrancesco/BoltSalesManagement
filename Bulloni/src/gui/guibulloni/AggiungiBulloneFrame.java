package gui.guibulloni;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import gestori.gestoribulloni.AggiungiBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;
import utility.Data;
import bulloni.Materiale;
import bulloni.exception.BulloneException;
import databaseSQL.exception.DatabaseSQLException;
import bulloni.Innesto;
import bulloni.BulloneGrano;

/**
 * Classe per modellare il layout ed il funzionamento della finestra di aggiunta di un bullone.
 * 
 * @author Catignano Francesco
 */
public class AggiungiBulloneFrame extends JFrame implements WindowListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_WIDTH = 410;
	private static final int MAX_HEIGHT = 520;
	private static final int MIN_ANNO = 1970;
	private static int MAX_ANNO = Data.getDataAttuale().getAnno();
	
	private static JFrame mainFrame;
	private static BodyBulloni mainPanel;
	private static AggiungiBulloni aggiungiBulloni;
	
	/*
	 * Elementi visibili nell'interfaccia
	 */
	private JPanel titlePanel = new JPanel();
	private JLabel lblTitolo = new JLabel();
	private JPanel footerPanel = new JPanel();
	private JButton btnAggiungi = new JButton();
	private JPanel formPanel = new JPanel();
	private JLabel lblTipoBullone = new JLabel();
	private JComboBox<String> comboBoxTipoBullone = new JComboBox<String>();
	JLabel lblCodice = new JLabel();
	JTextField txtFieldCodice = new JTextField();
	JLabel lblDataProduzione = new JLabel();
	JComboBox<Integer> comboBoxGiorni = new JComboBox<Integer>();
	JComboBox<String> comboBoxMesi = new JComboBox<String>();
	JComboBox<Integer> comboBoxAnni = new JComboBox<Integer>();
	JLabel lblLuogoProduzione = new JLabel();
	JTextField txtFieldLuogoProduzione = new JTextField();
	JLabel lblPeso = new JLabel();
	JSpinner spinnerPeso = new JSpinner();
	JLabel lblGrammi = new JLabel("gr.");
	JLabel lblPrezzo = new JLabel();
	JSpinner spinnerPrezzo = new JSpinner();
	JLabel lblEuro = new JLabel("\u20ac");
	JLabel lblMateriale = new JLabel();
	JComboBox<String> comboBoxMateriale = new JComboBox<String>();
	JLabel lblLunghezza = new JLabel();
	JSpinner spinnerLunghezza = new JSpinner();
	JLabel lblMillimetri = new JLabel("mm");
	JLabel lblDiametroVite = new JLabel();
	JSpinner spinnerDiametroVite = new JSpinner();
	JLabel lblCentimetri = new JLabel("cm");
	JLabel lblInnesto = new JLabel();
	JComboBox<String> comboBoxInnesto = new JComboBox<String>();
	
	
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
		
		// Creazione e modellazione degli elementi
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
	 * Trigger dei bottoni
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Bottone "Aggiungi"
		if(e.getSource()==this.btnAggiungi) {
			/*
			 * Creazione del bullone
			 */
			String tipoBullone = (String)this.comboBoxTipoBullone.getSelectedItem();
			int codBullone = Integer.valueOf(this.txtFieldCodice.getText());
			Data dataProduzione = new Data((Integer)this.comboBoxGiorni.getSelectedItem(),	// giorno
										   this.comboBoxMesi.getSelectedIndex() + 1,	// mese
										   (Integer)this.comboBoxAnni.getSelectedItem());	// anno
			String luogoProduzione = this.txtFieldLuogoProduzione.getText();
			double peso = (Double)this.spinnerPeso.getModel().getValue();
			double prezzo = (Double)this.spinnerPrezzo.getModel().getValue();
			Materiale materiale = Materiale.valueOf((String)this.comboBoxMateriale.getSelectedItem());
			double lunghezza = (Double)this.spinnerLunghezza.getModel().getValue();
			double diametroVite = (Double)this.spinnerDiametroVite.getModel().getValue();
			Innesto innesto = Innesto.valueOf((String)this.comboBoxInnesto.getSelectedItem());
			
			/*
			 * Aggiunta del bullone
			 */
			switch(tipoBullone) {
			case "Grano": {
				try {
					BulloneGrano b = new BulloneGrano(codBullone, dataProduzione, luogoProduzione, peso, prezzo, materiale, lunghezza, diametroVite, innesto);
					aggiungiBulloni.newBulloneGrano(b);
					// Chiusura finestra e aggiornamento
					mainFrame.setEnabled(true);
					mainPanel.refresh();
					this.dispose();
					
				} catch (BulloneException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore aggiunta bullone", JOptionPane.ERROR_MESSAGE);
				} catch (GestoreBulloniException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore aggiunta bullone", JOptionPane.ERROR_MESSAGE);
				} catch (DatabaseSQLException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore aggiunta bullone", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			}
			
		}
	}
	
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
	
	/**
	 * Metodo per creare ed impostare il layout al pannello contenente il titolo della finestra
	 */
	private void creaTitlePanel() {
		this.getContentPane().add(this.titlePanel, BorderLayout.NORTH);
		this.lblTitolo.setText("AGGIUNGI UN BULLONE");
		this.lblTitolo.setFont(new Font(this.lblTitolo.getFont().getFontName(), Font.BOLD, 16));
		this.titlePanel.add(this.lblTitolo);
	}
	
	
	/**
	 * Metodo per creare il pannello posizionato in basso, contenente il bottone per aggiungere effettivamente il bottone
	 */
	private void creaFooterPanel() {
		this.getContentPane().add(this.footerPanel, BorderLayout.SOUTH);
		this.btnAggiungi.setText("Aggiungi");
		this.footerPanel.add(this.btnAggiungi);
		this.btnAggiungi.addActionListener(this);
	}
	
	
	/**
	 * Metodo per creare il pannello contenente gli elementi da compilare per costruire un nuovo bullone.
	 */
	private void creaFormPanel() {
		/*
		 * Creazione e modellazione del pannello
		 */
		this.getContentPane().add(this.formPanel, BorderLayout.CENTER);
		GridBagLayout gblForFormPanel = new GridBagLayout();
		gblForFormPanel.columnWidths = new int[] {0, 0, 0, 0};
		gblForFormPanel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gblForFormPanel.columnWeights = new double[] {0.0, 0.0, 0.0, Double.MIN_VALUE};
		gblForFormPanel.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.formPanel.setLayout(gblForFormPanel);
		
		/*
		 * Label per il tipo di bullone
		 */
		this.lblTipoBullone.setText("Tipo di bullone: ");
		GridBagConstraints gbcForLblTipoBullone = new GridBagConstraints();
		gbcForLblTipoBullone.gridx = 0;
		gbcForLblTipoBullone.gridy = 0;
		gbcForLblTipoBullone.anchor = GridBagConstraints.LINE_START;
		gbcForLblTipoBullone.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblTipoBullone, gbcForLblTipoBullone);
		
		/*
		 * ComboBox per il tipo di bullone
		 */
		this.comboBoxTipoBullone.setMaximumRowCount(5);	// numero massimo di elementi visibili quando si apre la tendina
		this.comboBoxTipoBullone.setModel(new DefaultComboBoxModel<String>(new String[] {"Grano"}));
		GridBagConstraints gbcForComboBoxTipoBullone = new GridBagConstraints();
		gbcForComboBoxTipoBullone.gridx = 1;
		gbcForComboBoxTipoBullone.gridy = 0;
		gbcForComboBoxTipoBullone.gridwidth = 2;
		gbcForComboBoxTipoBullone.anchor = GridBagConstraints.LINE_START;
		gbcForComboBoxTipoBullone.insets = new Insets(10, 10, 10, 10);
		gbcForComboBoxTipoBullone.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.comboBoxTipoBullone, gbcForComboBoxTipoBullone);
		
		/*
		 * Label per il codice
		 */
		this.lblCodice.setText("Codice: ");
		GridBagConstraints gbcForLblCodice = new GridBagConstraints();
		gbcForLblCodice.gridx = 0;
		gbcForLblCodice.gridy = 1;
		gbcForLblCodice.anchor = GridBagConstraints.LINE_START;
		gbcForLblCodice.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblCodice, gbcForLblCodice);
		
		/*
		 * JTextField per il codice
		 */
		this.txtFieldCodice.setText(((Integer)aggiungiBulloni.getCodBulloneAutomatico()).toString());
		this.txtFieldCodice.setEnabled(false);
		this.txtFieldCodice.setEditable(false);
		this.txtFieldCodice.setColumns(3);
		GridBagConstraints gbcForTxtFieldCodice = new GridBagConstraints();
		gbcForTxtFieldCodice.gridx = 1;
		gbcForTxtFieldCodice.gridy = 1;
		gbcForTxtFieldCodice.anchor = GridBagConstraints.LINE_START;
		gbcForTxtFieldCodice.insets = new Insets(10, 10, 10, 10);
		gbcForTxtFieldCodice.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.txtFieldCodice, gbcForTxtFieldCodice);
		
		/*
		 * Label per la data di produzione
		 */
		this.lblDataProduzione.setText("Data di produzione: ");
		GridBagConstraints gbcForLblDataProduzione = new GridBagConstraints();
		gbcForLblDataProduzione.gridx = 0;
		gbcForLblDataProduzione.gridy = 2;
		gbcForLblDataProduzione.anchor = GridBagConstraints.LINE_START;
		gbcForLblDataProduzione.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblDataProduzione, gbcForLblDataProduzione);
		
		/*
		 * ComboBox per i giorni
		 */
		this.comboBoxGiorni.setMaximumRowCount(15);
		this.comboBoxGiorni.setModel(new DefaultComboBoxModel<Integer>());
		for(Integer i=1; i<=31; i++) {
			this.comboBoxGiorni.addItem(i);
		}
		GridBagConstraints gbcForComboBoxGiorni = new GridBagConstraints();
		gbcForComboBoxGiorni.gridx = 1;
		gbcForComboBoxGiorni.gridy = 2;
		gbcForComboBoxGiorni.gridwidth = 1;
		gbcForComboBoxGiorni.anchor = GridBagConstraints.LINE_START;
		gbcForComboBoxGiorni.insets = new Insets(10, 10, 10, 10);
		gbcForComboBoxGiorni.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.comboBoxGiorni, gbcForComboBoxGiorni);
		
		
		/*
		 * ComboBox per i mesi
		 */
		this.comboBoxMesi.setMaximumRowCount(6);
		this.comboBoxMesi.setModel(new DefaultComboBoxModel<String>(new String[] {"Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"}));
		GridBagConstraints gbcForComboBoxMesi = new GridBagConstraints();
		gbcForComboBoxMesi.gridx = 2;
		gbcForComboBoxMesi.gridy = 2;
		gbcForComboBoxMesi.gridwidth = 1;
		gbcForComboBoxMesi.anchor = GridBagConstraints.LINE_START;
		gbcForComboBoxMesi.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.comboBoxMesi, gbcForComboBoxMesi);
		
		
		/*
		 * ComboBox per gli anni
		 */
		this.comboBoxAnni.setMaximumRowCount(10);
		this.comboBoxAnni.setModel(new DefaultComboBoxModel<Integer>());
		for(Integer i=MAX_ANNO; i>=MIN_ANNO; i--) {
			this.comboBoxAnni.addItem(i);
		}
		GridBagConstraints gbcForComboBoxAnni = new GridBagConstraints();
		gbcForComboBoxAnni.gridx = 3;
		gbcForComboBoxAnni.gridy = 2;
		gbcForComboBoxAnni.gridwidth = 1;
		gbcForComboBoxAnni.anchor = GridBagConstraints.LINE_START;
		gbcForComboBoxAnni.insets = new Insets(10, 10, 10, 10);
		gbcForComboBoxAnni.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.comboBoxAnni, gbcForComboBoxAnni);
		
		/*
		 * Label per il luogo di produzione
		 */
		this.lblLuogoProduzione.setText("Luogo di produzione: ");
		GridBagConstraints gbcForLblLuogoProduzione = new GridBagConstraints();
		gbcForLblLuogoProduzione.gridx = 0;
		gbcForLblLuogoProduzione.gridy = 3;
		gbcForLblLuogoProduzione.anchor = GridBagConstraints.LINE_START;
		gbcForLblLuogoProduzione.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblLuogoProduzione, gbcForLblLuogoProduzione);
		
		/*
		 * JTextField per il luogo di produzione
		 */
		GridBagConstraints gbcForTxtFieldLuogoProduzione = new GridBagConstraints();
		gbcForTxtFieldLuogoProduzione.gridx = 1;
		gbcForTxtFieldLuogoProduzione.gridy = 3;
		gbcForTxtFieldLuogoProduzione.gridwidth = 2;
		gbcForTxtFieldLuogoProduzione.anchor = GridBagConstraints.LINE_START;
		gbcForTxtFieldLuogoProduzione.insets = new Insets(10, 10, 10, 10);
		gbcForTxtFieldLuogoProduzione.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.txtFieldLuogoProduzione, gbcForTxtFieldLuogoProduzione);
		
		/*
		 * Label per il peso
		 */
		this.lblPeso.setText("Peso: ");
		GridBagConstraints gbcForLblPeso = new GridBagConstraints();
		gbcForLblPeso.gridx = 0;
		gbcForLblPeso.gridy = 4;
		gbcForLblPeso.anchor = GridBagConstraints.LINE_START;
		gbcForLblPeso.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblPeso, gbcForLblPeso);
		
		/*
		 * Spinner per il peso
		 */
		this.spinnerPeso.setModel(new SpinnerNumberModel(1.5, 1.5, 15.0, 0.1));
		GridBagConstraints gbcForSpinnerPeso = new GridBagConstraints();
		gbcForSpinnerPeso.gridx = 1;
		gbcForSpinnerPeso.gridy = 4;
		gbcForSpinnerPeso.anchor = GridBagConstraints.LINE_START;
		gbcForSpinnerPeso.insets = new Insets(10, 10, 10, 10);
		gbcForSpinnerPeso.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.spinnerPeso, gbcForSpinnerPeso);
		
		/*
		 * Label per i grammi
		 */
		GridBagConstraints gbcForLblGrammi = new GridBagConstraints();
		gbcForLblGrammi.gridx = 2;
		gbcForLblGrammi.gridy = 4;
		gbcForLblGrammi.anchor = GridBagConstraints.LINE_START;
		gbcForLblGrammi.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblGrammi, gbcForLblGrammi);
		
		/*
		 * Label per il prezzo
		 */
		this.lblPrezzo.setText("Prezzo: ");
		GridBagConstraints gbcForLblPrezzo = new GridBagConstraints();
		gbcForLblPrezzo.gridx = 0;
		gbcForLblPrezzo.gridy = 5;
		gbcForLblPrezzo.anchor = GridBagConstraints.LINE_START;
		gbcForLblPrezzo.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblPrezzo, gbcForLblPrezzo);
		
		/*
		 * Spinner per il prezzo
		 */
		this.spinnerPrezzo.setModel(new SpinnerNumberModel(0.50, 0.50, 2.50, 0.1));
		GridBagConstraints gbcForSpinnerPrezzo = new GridBagConstraints();
		gbcForSpinnerPrezzo.gridx = 1;
		gbcForSpinnerPrezzo.gridy = 5;
		gbcForSpinnerPrezzo.anchor = GridBagConstraints.LINE_START;
		gbcForSpinnerPrezzo.insets = new Insets(10, 10, 10, 10);
		gbcForSpinnerPrezzo.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.spinnerPrezzo, gbcForSpinnerPrezzo);
		
		/*
		 * Label per l'euro
		 */
		GridBagConstraints gbcForLblEuro = new GridBagConstraints();
		gbcForLblEuro.gridx = 2;
		gbcForLblEuro.gridy = 5;
		gbcForLblEuro.anchor = GridBagConstraints.LINE_START;
		gbcForLblEuro.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblEuro, gbcForLblEuro);
		
		/*
		 * Label per il materiale
		 */
		this.lblMateriale.setText("Materiale: ");
		GridBagConstraints gbcForLblMateriale = new GridBagConstraints();
		gbcForLblMateriale.gridx = 0;
		gbcForLblMateriale.gridy = 6;
		gbcForLblMateriale.anchor = GridBagConstraints.LINE_START;
		gbcForLblMateriale.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblMateriale, gbcForLblMateriale);
		
		/*
		 * ComboBox per il tipo di bullone
		 */
		this.comboBoxMateriale.setMaximumRowCount(5);	// numero massimo di elementi visibili quando si apre la tendina
		this.comboBoxMateriale.setModel(new DefaultComboBoxModel<String>(new String[] {Materiale.ACCIAIO.toString(),
																					   Materiale.ACCIAIO_INOX.toString(),
																					   Materiale.BRONZO.toString(),
																					   Materiale.NYLON.toString(),
																					   Materiale.OTTONE.toString(),
																					   Materiale.TITANIO.toString(),
																					   Materiale.VETRO_PORCELLANA.toString()}));
		GridBagConstraints gbcForComboBoxMateriale = new GridBagConstraints();
		gbcForComboBoxMateriale.gridx = 1;
		gbcForComboBoxMateriale.gridy = 6;
		gbcForComboBoxMateriale.gridwidth = 0;
		gbcForComboBoxMateriale.anchor = GridBagConstraints.LINE_START;
		gbcForComboBoxMateriale.insets = new Insets(10, 10, 10, 10);
		gbcForComboBoxMateriale.fill = GridBagConstraints.NONE;
		this.formPanel.add(this.comboBoxMateriale, gbcForComboBoxMateriale);
		
		/*
		 * Label per la lunghezza
		 */
		this.lblLunghezza.setText("Lunghezza: ");
		GridBagConstraints gbcForLblLunghezza = new GridBagConstraints();
		gbcForLblLunghezza.gridx = 0;
		gbcForLblLunghezza.gridy = 7;
		gbcForLblLunghezza.anchor = GridBagConstraints.LINE_START;
		gbcForLblLunghezza.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblLunghezza, gbcForLblLunghezza);
		
		/*
		 * Spinner per la lunghezza
		 */
		this.spinnerLunghezza.setModel(new SpinnerNumberModel(15.0, 15.0, 200.0, 0.5));
		GridBagConstraints gbcForSpinnerLunghezza = new GridBagConstraints();
		gbcForSpinnerLunghezza.gridx = 1;
		gbcForSpinnerLunghezza.gridy = 7;
		gbcForSpinnerLunghezza.anchor = GridBagConstraints.LINE_START;
		gbcForSpinnerLunghezza.insets = new Insets(10, 10, 10, 10);
		gbcForSpinnerLunghezza.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.spinnerLunghezza, gbcForSpinnerLunghezza);
		
		/*
		 * Label per i millimetri
		 */
		GridBagConstraints gbcForLblMillimetri = new GridBagConstraints();
		gbcForLblMillimetri.gridx = 2;
		gbcForLblMillimetri.gridy = 7;
		gbcForLblMillimetri.anchor = GridBagConstraints.LINE_START;
		gbcForLblMillimetri.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblMillimetri, gbcForLblMillimetri);
		
		/*
		 * Label per il diametro della vite
		 */
		this.lblDiametroVite.setText("Diametro della vite: ");
		GridBagConstraints gbcForLblDiametroVite = new GridBagConstraints();
		gbcForLblDiametroVite.gridx = 0;
		gbcForLblDiametroVite.gridy = 8;
		gbcForLblDiametroVite.anchor = GridBagConstraints.LINE_START;
		gbcForLblDiametroVite.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblDiametroVite, gbcForLblDiametroVite);
		
		/*
		 * Spinner per il diametro della vite
		 */
		this.spinnerDiametroVite.setModel(new SpinnerNumberModel(4.0, 4.0, 10.0, 0.1));
		GridBagConstraints gbcForSpinnerDiametroVite = new GridBagConstraints();
		gbcForSpinnerDiametroVite.gridx = 1;
		gbcForSpinnerDiametroVite.gridy = 8;
		gbcForSpinnerDiametroVite.anchor = GridBagConstraints.LINE_START;
		gbcForSpinnerDiametroVite.insets = new Insets(10, 10, 10, 10);
		gbcForSpinnerDiametroVite.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.spinnerDiametroVite, gbcForSpinnerDiametroVite);
		
		/*
		 * Label per i centrimetri
		 */
		GridBagConstraints gbcForLblCentimetri = new GridBagConstraints();
		gbcForLblCentimetri.gridx = 2;
		gbcForLblCentimetri.gridy = 8;
		gbcForLblCentimetri.anchor = GridBagConstraints.LINE_START;
		gbcForLblCentimetri.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblCentimetri, gbcForLblCentimetri);
		
		/*
		 * Label per l'innesto
		 */
		this.lblInnesto.setText("Innesto: ");
		GridBagConstraints gbcForLblInnesto = new GridBagConstraints();
		gbcForLblInnesto.gridx = 0;
		gbcForLblInnesto.gridy = 9;
		gbcForLblInnesto.anchor = GridBagConstraints.LINE_START;
		gbcForLblInnesto.insets = new Insets(10, 10, 10, 10);
		this.formPanel.add(this.lblInnesto, gbcForLblInnesto);
		
		/*
		 * ComboBox per l'innesto
		 */
		this.comboBoxInnesto.setMaximumRowCount(5);	// numero massimo di elementi visibili quando si apre la tendina
		this.comboBoxInnesto.setModel(new DefaultComboBoxModel<String>(new String[] {Innesto.CROCE.toString(),
																					 Innesto.ESAGONALE.toString(),
																					 Innesto.TAGLIO.toString(),
																					 Innesto.TORX.toString()}));
		GridBagConstraints gbcForComboBoxInnesto = new GridBagConstraints();
		gbcForComboBoxInnesto.gridx = 1;
		gbcForComboBoxInnesto.gridy = 9;
		gbcForComboBoxInnesto.gridwidth = 2;
		gbcForComboBoxInnesto.anchor = GridBagConstraints.LINE_START;
		gbcForComboBoxInnesto.insets = new Insets(10, 10, 10, 10);
		gbcForComboBoxInnesto.fill = GridBagConstraints.HORIZONTAL;
		this.formPanel.add(this.comboBoxInnesto, gbcForComboBoxInnesto);
	}
	
}