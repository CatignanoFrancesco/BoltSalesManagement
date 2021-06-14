package gui.guivendite;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import bulloni.Bullone;
import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.VisualizzazioneImpiegato;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gestori.gestorevendite.InserimentoVendite;
import gestori.gestorevendite.VisualizzazioneVendite;
import gestori.gestorevendite.exception.GestoreVenditaException;
import gestori.gestoribulloni.VisualizzaBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;
import persona.ImpiegatoBulloni;
import utility.Data;
import vendita.MerceVenduta;
import vendita.Vendita;
import vendita.VenditaBulloni;
import vendita.exception.VenditaException;

/**
 * @author GiannettaGerardo
 *
 * Classe che rappresenta una finestra grafica che permette di aggiungere 
 * una nuova vendita al gestore vendite tramite un form di input apposito
 */
public class InputForm extends JDialog {

	private static final long serialVersionUID = 1L;
	
	/** coordinata x della finestra */
	private static final int X = 100;
	/** coordinata y della finestra */
	private static final int Y = 100;
	/** larghezza della finestra */
	private static final int WIDTH = 700;
	/** lunghezza della finestra */
	private static final int HEIGHT = 404;
	
	// oggetti per creare l'interfaccia grafica
	
	/** contiene il codice della vendita */
	private JTextField codiceVenditaTextField;
	/** contiene le matricole dgli impiegati assunti */
	private Integer[] matricoleImpiegato;
	/** contiene un messaggio informativo per l'utente in cima alla finestra */
	private JLabel headerLabel;
	/** label corrispondente al text field contenete il codice della vendita */
	private JLabel codiceVenditaLabel;
	/** label corrispondente alla data di vendita */
	private JLabel dataVenditaLabel;
	/** combo box contenente i giorni da 1 a 31 */
	private JComboBox<Integer> giornoComboBox;
	/** label corrispondente alla combo box per il giorno del mese */
	private JLabel giornoLabel;
	/** combo box contenente i mesi da 1 a 12 */
	private JComboBox<Integer> meseComboBox;
	/** label corrispondente alla combo box per il mese */
	private JLabel meseLabel;
	/** combo box contenente gli anni dal 1930 all'anno corrente  */
	private JComboBox<Integer> annoComboBox;
	/** label corrispondente alla combo box per l'anno */
	private JLabel annoLabel;
	/** label corrispondente alla combo box della matricola impiegato */
	private JLabel impiegatoLabel;
	/** combo box contenente la matricola dell'impiegato che effettua la vendita */
	private JComboBox<Integer> impiegatoComboBox;
	/** label corrispondente ai bulloni da vendere */
	private JLabel bulloniLabel;
	/** pulsante per aggiungere la nuova vendita creata */
	private JButton aggiungiVenditaButton;
	/**  pannello scorrevole che conterrà un panello con layout di tipo griglia */
	private JScrollPane scrollPane;
	/** pannello con layout di tipo griglia, che conterrà i bulloni da scegliere per la vendita */
	private JPanel panel;
	/** istanza corrente della classe BodyVendite */
	private BodyVendite istanzaCorrente;
	/** istanza della finestra corrente */
	private JDialog finestraCorrente = this;
	
	/** conterrà i codici dei bulloni */
	private JLabel[] codiceLabel;
	/** conterrà il tipo dei bulloni */
	private JLabel[] tipoBulloneLabel;
	/** conterrà il luogo di produzione dei bulloni */
	private JLabel[] luogoProduzioneLabel;
	/** conterrà il prezzo dei singoli bulloni */
	private JLabel[] prezzoLabel;
	/** conterrà la quantita' scelta da vendere dei bulloni con quel codice */
	private JSpinner[] quantitaSpinner;
	/** titolo della finestra grafica */
	private final String titoloFinestra = "Aggiungi vendita";
	
	/** gestore delle vendite con interfaccia contenente i metodi di inserimento */
	private InserimentoVendite gestoreVendite;

	/** gestore degli impiegati con interfaccia di visualizzazione */
	private VisualizzazioneImpiegato gestoreImpiegati;
	
	/** gestore dei bulloni con interfaccia di visualizzazione */
	private VisualizzaBulloni gestoreBulloni;
	
	/** numero di bulloni presenti nell'apposito set del gestore bulloni */
	private int numeroBulloni;
	
	
	/**
	 * Costruttore della finestra per aggiungere una vendita;
	 * Controlla prima di tutto che una vendita sia aggiungibile, e lo fa controllando che i set dei
	 * gestori impiegati e bulloni non sia vuoti
	 * 
	 * @param gvInserimento gestore contenente tutte le vendite prese da database
	 * @param gestoreImpiegati gestore contenente tutti gli impiegati presi dal database
	 * @param gestoreBulloni gestore contenente tutti i bulloni presi dal database
	 */
	public InputForm(InserimentoVendite gestoreVendite, VisualizzazioneImpiegato gestoreImpiegati, VisualizzaBulloni gestoreBulloni, BodyVendite istanzaCorrente) {
		
		this.gestoreVendite = gestoreVendite;
		this.gestoreImpiegati = gestoreImpiegati;
		this.gestoreBulloni = gestoreBulloni;
		this.istanzaCorrente = istanzaCorrente;
		
		inizializza();
		createFormCodiceVendita();
		createFormData();
		createFormMatricolaImpiegato();
		createFormBulloniDaVendere();
		createAggiungiVenditaButton();
		
	}
	
	
	
	/**
	 * Metodo che inizializza i valori della finestra e aggiunge una label per informare l'utente di quello che dovrà fare
	 */
	private void inizializza() {
		
		// creo la finestra
		setModal(true);
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle(titoloFinestra);
		setBounds(X, Y, WIDTH, HEIGHT);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		// creo una label informativa per l'utente
		headerLabel = new JLabel("Compila tutti i campi per aggiungere una nuova vendita:");
		headerLabel.setBounds(10, 10, 405, 16);
		getContentPane().add(headerLabel);
		
	}
	
	
	
	/**
	 * Metodo che crea il form per il codice della vendita;
	 * Il form non sarà editabile perché il codice viene inserito automaticamente dal gestore vendite
	 */
	public void createFormCodiceVendita() {
		
		// label corrispondente al codice della vendita
		codiceVenditaLabel = new JLabel("Codice vendita:");
		codiceVenditaLabel.setBounds(10, 55, 130, 16);
		getContentPane().add(codiceVenditaLabel);
		
		// text field per contenere il codice della vendita;
		codiceVenditaTextField = new JTextField();
		codiceVenditaLabel.setLabelFor(codiceVenditaTextField);
		codiceVenditaTextField.setText(((Integer)gestoreVendite.getCodVenditaAutomatico()).toString());
		codiceVenditaTextField.setEditable(false);
		codiceVenditaTextField.setBounds(130, 52, 80, 19);
		getContentPane().add(codiceVenditaTextField);
		codiceVenditaTextField.setColumns(10);
		
	}
	
	
	
	/**
	 * Metodo che crea il form per inserire la data della vendita;
	 * Usa 3 menu' combobox (menu' a tendita) per permettere di scegliere giorno, mese e anno
	 */
	public void createFormData() {
		
		// label corrispondente alla data della vendita
		dataVenditaLabel = new JLabel("Data vendita:");
		dataVenditaLabel.setBounds(10, 110, 100, 16);
		getContentPane().add(dataVenditaLabel);
		
		// menu combobox èer scegliere il giorno del mese
		giornoComboBox = new JComboBox<Integer>();
		giornoComboBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}));
		giornoComboBox.setBounds(130, 106, 50, 21);
		getContentPane().add(giornoComboBox);
		
		// label corrispondente al giorno del mese
		giornoLabel = new JLabel("Giorno:");
		giornoLabel.setBounds(130, 90, 60, 13);
		getContentPane().add(giornoLabel);
		giornoLabel.setLabelFor(giornoComboBox);
		
		// menu combobox per scegliere il mese dell'anno
		meseComboBox = new JComboBox<Integer>();
		meseComboBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12}));
		meseComboBox.setBounds(208, 106, 50, 21);
		getContentPane().add(meseComboBox);
		
		// label corrispondente al mese dell'anno
		meseLabel = new JLabel("Mese:");
		meseLabel.setBounds(208, 90, 60, 13);
		getContentPane().add(meseLabel);
		meseLabel.setLabelFor(meseComboBox);
		
		// creo una lista di anni che vanno dal 1930 ad oggi; questa lista andrà inserita nella combobox degli anni sottoforma di array
		ArrayList<Integer> listaAnni = new ArrayList<Integer>(100);
		for (int i = LocalDate.now().getYear(); i >= 1930; i--) {
			listaAnni.add(i);
		}
		
		// menu combobox per scegliere l'anno
		annoComboBox = new JComboBox<Integer>();
		annoComboBox.setModel(new DefaultComboBoxModel<Integer>(listaAnni.toArray(new Integer[0])));
		annoComboBox.setBounds(286, 106, 70, 21);
		getContentPane().add(annoComboBox);
		
		// label corrispondente all'anno
		annoLabel = new JLabel("Anno:");
		annoLabel.setBounds(286, 90, 60, 13);
		getContentPane().add(annoLabel);
		annoLabel.setLabelFor(annoComboBox);
		
	}
	
	
	
	/**
	 * Metodo che crea il form per inserire la matricola dell'impiegato che effettua la vendita
	 */
	public void createFormMatricolaImpiegato() {
		
		// recupero gli impiegati dal gestore corrispondente
		Set<ImpiegatoBulloni> impiegati = gestoreImpiegati.getSetImpiegatiAssunti();
		
		matricoleImpiegato = new Integer[impiegati.size()];
		int i = 0;
		
		// label corrispondente alla matricola dell'impiegato che ha effettuato la vendita
		impiegatoLabel = new JLabel("Matricola impiegato:");
		impiegatoLabel.setBounds(10, 165, 150, 16);
		getContentPane().add(impiegatoLabel);
		
		// ricavo le matricole di tutti gli impiegati e le salvo in un array di Integer, cosi' da poterle inserire in un menu combobox
		for (ImpiegatoBulloni impb : impiegati) {
			matricoleImpiegato[i] = impb.getID();
			i++;
		}
		
		// menu combobox per scegliere la matricola dell'impiegato che effettua la vendita
		impiegatoComboBox = new JComboBox<Integer>();
		impiegatoLabel.setLabelFor(impiegatoComboBox);
		impiegatoComboBox.setModel(new DefaultComboBoxModel<Integer>(matricoleImpiegato));
		impiegatoComboBox.setBounds(170, 161, 70, 21);
		getContentPane().add(impiegatoComboBox);
		
	}
	
	
	
	/**
	 * Metodo che crea il form per selezionare i bulloni da vendere e la quantità
	 */
	public void createFormBulloniDaVendere() {
		
		// label corrispondente ai bulloni da vendere
		bulloniLabel = new JLabel("Seleziona i bulloni da vendere:");
		bulloniLabel.setBounds(10, 219, 300, 13);
		getContentPane().add(bulloniLabel);
		
		// pannello scorrevole che conterrà un panello con layout di tipo griglia
		scrollPane = new JScrollPane();
		bulloniLabel.setLabelFor(scrollPane);
		scrollPane.setViewportBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(10, 242, 500, 103);
		getContentPane().add(scrollPane);
		
		// pannello con layout di tipo griglia, che conterrà i bulloni da scegliere per la vendita
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		Set<Bullone> bulloni = gestoreBulloni.getBulloniDisponibili();
		numeroBulloni = bulloni.size();  // numero di bulloni da mostrare
		codiceLabel = new JLabel[numeroBulloni];
		tipoBulloneLabel = new JLabel[numeroBulloni];
		luogoProduzioneLabel = new JLabel[numeroBulloni];
		prezzoLabel = new JLabel[numeroBulloni];
		quantitaSpinner = new JSpinner[numeroBulloni];
		SpinnerModel[] modelSpinner = new SpinnerModel[numeroBulloni]; // contiene le opzioni per il JSpinner
		
		int x = 0; // indice per posizionare gli elementi nella griglia
		int i = 0; // contatore per i bulloni
		
		for (Bullone bullone : bulloni) {
			
			x = 0;
			
			// nella griglia indica il codice del bullone
			codiceLabel[i] = new JLabel(((Integer)bullone.getCodice()).toString());
			GridBagConstraints gbc_codiceLabel = new GridBagConstraints();
			gbc_codiceLabel.insets = new Insets(5, 15, 5, 5);
			gbc_codiceLabel.gridx = x;
			gbc_codiceLabel.gridy = i;
			panel.add(codiceLabel[i], gbc_codiceLabel);
			
			// nella griglia indica il tipo del bullone
			tipoBulloneLabel[i] = new JLabel(bullone.getClass().getSimpleName());
			GridBagConstraints gbc_tipoBulloneLabel = new GridBagConstraints();
			gbc_tipoBulloneLabel.insets = new Insets(5, 15, 5, 5);
			gbc_tipoBulloneLabel.gridx = ++x;
			gbc_tipoBulloneLabel.gridy = i;
			panel.add(tipoBulloneLabel[i], gbc_tipoBulloneLabel);
			
			// nella griglia indica il luogo di produzione del bullone
			luogoProduzioneLabel[i] = new JLabel(bullone.getLuogoProduzione());
			GridBagConstraints gbc_luogoProduzioneLabel = new GridBagConstraints();
			gbc_luogoProduzioneLabel.insets = new Insets(5, 15, 5, 5);
			gbc_luogoProduzioneLabel.gridx = ++x;
			gbc_luogoProduzioneLabel.gridy = i;
			panel.add(luogoProduzioneLabel[i], gbc_luogoProduzioneLabel);
			
			// nella griglia indica il prezzo di un singolo bullone
			prezzoLabel[i] = new JLabel(((Double)bullone.getPrezzo()).toString());
			GridBagConstraints gbc_prezzoLabel = new GridBagConstraints();
			gbc_prezzoLabel.insets = new Insets(5, 15, 5, 5);
			gbc_prezzoLabel.gridx = ++x;
			gbc_prezzoLabel.gridy = i;
			panel.add(prezzoLabel[i], gbc_prezzoLabel);
			
			// significato dei valori nel costruttore = (valore_iniziale, valore_minimo, valore_massimo, step)
			modelSpinner[i] = new SpinnerNumberModel(0, 0, 500, 1);
			
			// nella griglia indica il contatore di bulloni da vendere con questo codice
			quantitaSpinner[i] = new JSpinner(modelSpinner[i]);
			GridBagConstraints gbc_quantitaSpinner = new GridBagConstraints();
			gbc_quantitaSpinner.insets = new Insets(5, 15, 5, 15);
			gbc_quantitaSpinner.gridx = ++x;
			gbc_quantitaSpinner.gridy = i;
			panel.add(quantitaSpinner[i], gbc_quantitaSpinner);
					
			i++;
		}
	}
	
	
	
	/**
	 * Metodo che crea il pulsante per aggiungere la vendita creata;
	 * Controlla che tutti i campi della finestra siano stati compilati correttamente e che la vendita 
	 * sia fattibile, ne ottiene i valori e li usa per creare l'oggetto vendita e salvarlo
	 */
	public void createAggiungiVenditaButton() {
		
		aggiungiVenditaButton = new JButton("Aggiungi vendita");
		aggiungiVenditaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Data data = null;
				ImpiegatoBulloni impiegato = null;
				
				// costruisco il set di merce venduta da inserire nella vendita
				Set<MerceVenduta> merce = new HashSet<MerceVenduta>();
				
				// costruisco la vendita utilizzando gli oggetti costruiti in precedenza
				Vendita<MerceVenduta> vendita = null;
				
				try {
					data = new Data((Integer)giornoComboBox.getSelectedItem(), (Integer)meseComboBox.getSelectedItem(), (Integer)annoComboBox.getSelectedItem());
					
					impiegato = gestoreImpiegati.getImpiegatoByID((Integer)impiegatoComboBox.getSelectedItem());
					
					for (int i = 0; i < numeroBulloni; i++) {
						Integer numeroJSpinner = (Integer)quantitaSpinner[i].getValue();
						if ((numeroJSpinner > 0) && (numeroJSpinner <= 500))
							merce.add(new MerceVenduta(gestoreBulloni.getBulloneByCodice(Integer.parseUnsignedInt(codiceLabel[i].getText())), (Integer)quantitaSpinner[i].getValue()));
					}
					
					vendita = new VenditaBulloni(Integer.parseUnsignedInt(codiceVenditaTextField.getText()), data, impiegato, merce);
					
					/* tento di aggiungere la vendita al set del gestore vendite e nel database, ma se
					 * e' stata sollevata una qualsiasi eccezione precedentemente, la vendita non verrà aggiunta */
					gestoreVendite.aggiungiVendita(vendita);
					
					// ristampo la lista di vendite nel pannello principale e chiudo questa finestra
					istanzaCorrente.printListaVendite(((VisualizzazioneVendite)gestoreVendite).getVendite());
					dispose();
					
				}
				catch (DateTimeException f) {
					JOptionPane.showMessageDialog(finestraCorrente, "Data inserita non valida.", "Exception", JOptionPane.ERROR_MESSAGE);
				}
				catch (ExceptionGestoreImpiegato t) {
					JOptionPane.showMessageDialog(finestraCorrente, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				}
				catch (VenditaException f) {
					JOptionPane.showMessageDialog(finestraCorrente, f.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				}
				catch (GestoreBulloniException f) {
					JOptionPane.showMessageDialog(finestraCorrente, f.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				}
				catch (GestoreVenditaException f) {
					JOptionPane.showMessageDialog(finestraCorrente, f.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				} 
				catch (DatabaseSQLException f) {
					JOptionPane.showMessageDialog(finestraCorrente, f.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				} 
				catch (SQLException f) {
					JOptionPane.showMessageDialog(finestraCorrente, f.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		aggiungiVenditaButton.setBounds(525, 315, 165, 30);
		getContentPane().add(aggiungiVenditaButton);
		
	}

}
