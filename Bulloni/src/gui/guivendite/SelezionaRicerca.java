package gui.guivendite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gestori.gestorevendite.VisualizzazioneVendite;
import gestori.gestorevendite.exception.GestoreVenditaException;
import utility.Data;
import vendita.MerceVenduta;
import vendita.Vendita;

/**
 * @author GiannettaGerardo
 *
 */
public class SelezionaRicerca extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
	
	/** coordinata x della finestra */
	private static final int X = 100;
	/** coordinata y della finestra */
	private static final int Y = 100;
	/** larghezza della finestra */
	private static final int WIDTH = 370;
	/** lunghezza della finestra */
	private static final int HEIGHT = 297;
	
	private JFrame mainJFrame;
	private BodyVendite istanzaCorrente;
	private JLabel codiceVenditaLabel;
	private JLabel matricolaImpiegatoLabel;
	private JLabel dataVenditaLabel;
	private JButton cercaPerCodiceVenditaButton;
	private JButton cercaPerMatricolaImpiegatoButton;
	private JButton cercaPerDataVenditaButton;
	private JTextField codiceVenditaTextField;
	private JTextField matricolaImpiegatoTextField;
	private JComboBox<Integer> comboBoxGiorno;
	private JComboBox<Integer> comboBoxMese;
	private JComboBox<Integer> comboBoxAnno;
	private JLabel giornoLabel;
	private JLabel meseLabel;
	private JLabel annoLabel;
	private JFrame finestraCorrente = this;
	/** titolo della finestra grafica */
	private final String titoloFinestra = "Ricerca per...";
	
	/** gestore delle vendite con interfaccia contenente i metodi di visualizzazione */
	private VisualizzazioneVendite gestoreVendite;
	
	
	
	/**
	 * Costruttore della classe SelezionaRicerca
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param mainJF finestra principale da bloccare
	 * @param istanzaCorrente istanza in uso del pannello BodyVendite
	 */
	public SelezionaRicerca(VisualizzazioneVendite gestoreVendite, JFrame mainJF, BodyVendite istanzaCorrente) {
		this.gestoreVendite = gestoreVendite;
		this.mainJFrame = mainJF;
		this.istanzaCorrente = istanzaCorrente;
		
		inizializza();
		createCercaPerCodiceVenditaButton();
		createCercaPerMatricolaImpiegatoButton();
		createCercaPerDataVenditaButton();
		
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
	 * Metodo che crea il pulsante di ricerca per codice vendita e ne gestisce l'evento
	 */
	public void createCercaPerCodiceVenditaButton() {
		
		codiceVenditaLabel = new JLabel("Codice vendita:");
		codiceVenditaLabel.setBounds(10, 6, 118, 13);
		getContentPane().add(codiceVenditaLabel);
		
		// area di testo in cui si potrà inserire il codice della vendita
		codiceVenditaTextField = new JTextField();
		codiceVenditaTextField.setHorizontalAlignment(SwingConstants.CENTER);
		codiceVenditaTextField.setToolTipText("Codice vendita");
		codiceVenditaTextField.setBounds(99, 29, 199, 31);
		getContentPane().add(codiceVenditaTextField);
		codiceVenditaTextField.setColumns(10);
		
		// pulsante per avviare la ricerca in base a cio' che e' stato scritto nell'area di testo
		cercaPerCodiceVenditaButton = new JButton("Cerca");
		codiceVenditaLabel.setLabelFor(cercaPerCodiceVenditaButton);
		cercaPerCodiceVenditaButton.setBounds(10, 29, 88, 31);
		cercaPerCodiceVenditaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// utilizzo il gestore vendite per cercare un vendita con il codice inserito;
				// se non è stato inserito nulla verrà segnalato l'errore
				if (!codiceVenditaTextField.getText().trim().equals("")) {
					try {
						int codiceVendita = Integer.parseUnsignedInt(codiceVenditaTextField.getText().trim());
						
						Vendita<MerceVenduta> vendita = gestoreVendite.getVenditaByCodice(codiceVendita);
						
						Set<Vendita<MerceVenduta>> vendite = new HashSet<Vendita<MerceVenduta>>(1);
						vendite.add(vendita);
						
						istanzaCorrente.printListaVendite(vendite);
						
						mainJFrame.setEnabled(true);
						dispose();
					}
					catch (NumberFormatException t) {
						JOptionPane.showMessageDialog(finestraCorrente, "Non e' stato inserito un codice numerico.", "Errore", JOptionPane.ERROR_MESSAGE);
					} 
					catch (GestoreVenditaException t) {
						JOptionPane.showMessageDialog(finestraCorrente, "Nessuna vendita corrisponde a questo codice.", "Attenzione", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(finestraCorrente, "E' necessario inserire un numero nell'apposita area.", "Attenzione", JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		getContentPane().add(cercaPerCodiceVenditaButton);
		
	}
	
	
	
	/**
	 * Metodo che crea il pulsante di ricerca per matricola impiegato e ne gestisce l'evento
	 */
	public void createCercaPerMatricolaImpiegatoButton() {
		
		matricolaImpiegatoLabel = new JLabel("Matricola impiegato:");
		matricolaImpiegatoLabel.setBounds(10, 92, 163, 13);
		getContentPane().add(matricolaImpiegatoLabel);
		
		// area di testo in cui si potrà inserire la matricola dell'impiegato
		matricolaImpiegatoTextField = new JTextField();
		matricolaImpiegatoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		matricolaImpiegatoTextField.setToolTipText("Matricola impiegato");
		matricolaImpiegatoTextField.setBounds(99, 115, 199, 31);
		getContentPane().add(matricolaImpiegatoTextField);
		matricolaImpiegatoTextField.setColumns(10);
		
		// pulsante per avviare la ricerca in base a cio' che e' stato scritto nell'area di testo
		cercaPerMatricolaImpiegatoButton = new JButton("Cerca");
		matricolaImpiegatoLabel.setLabelFor(cercaPerMatricolaImpiegatoButton);
		cercaPerMatricolaImpiegatoButton.setBounds(10, 115, 88, 31);
		cercaPerMatricolaImpiegatoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// utilizzo il gestore vendite per cercare un vendita con la matricola dell'impiegato inserita;
				// se non è stato inserito nulla verrà segnalato l'errore
				if (!matricolaImpiegatoTextField.getText().trim().equals("")) {
					try {
						int matricolaImpiegato = Integer.parseUnsignedInt(matricolaImpiegatoTextField.getText().trim());
						
						Set<Vendita<MerceVenduta>> vendite = new HashSet<Vendita<MerceVenduta>>();
						vendite.addAll(gestoreVendite.getVenditeByImpiegato(matricolaImpiegato));
						
						istanzaCorrente.printListaVendite(vendite);
						
						mainJFrame.setEnabled(true);
						dispose();
					}
					catch (NumberFormatException t) {
						JOptionPane.showMessageDialog(finestraCorrente, "Non e' stato inserito un codice numerico.", "Errore", JOptionPane.ERROR_MESSAGE);
					} 
					catch (GestoreVenditaException t) {
						JOptionPane.showMessageDialog(finestraCorrente, "Nessuna vendita salvata per questo impiegato.", "Attenzione", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(finestraCorrente, "E' necessario inserire un numero nell'apposita area.", "Attenzione", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		getContentPane().add(cercaPerMatricolaImpiegatoButton);
		
	}
	
	
	
	/**
	 * Metodo che crea il pulsante di ricerca per data vendita e ne gestisce l'evento
	 */
	public void createCercaPerDataVenditaButton() {
		
		// label corrispondente alla data della vendita
		dataVenditaLabel = new JLabel("Data vendita:");
		dataVenditaLabel.setBounds(10, 180, 118, 16);
		getContentPane().add(dataVenditaLabel);
		
		// menu combobox èer scegliere il giorno del mese
		comboBoxGiorno = new JComboBox<Integer>();
		comboBoxGiorno.setBounds(135, 205, 60, 26);
		comboBoxGiorno.setModel(new DefaultComboBoxModel<Integer>(new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}));
		getContentPane().add(comboBoxGiorno);
		
		// label corrispondente al giorno del mese
		giornoLabel = new JLabel("Giorno:");
		giornoLabel.setLabelFor(comboBoxGiorno);
		giornoLabel.setBounds(135, 183, 53, 13);
		getContentPane().add(giornoLabel);
		
		// menu combobox per scegliere il mese dell'anno
		comboBoxMese = new JComboBox<Integer>();
		comboBoxMese.setModel(new DefaultComboBoxModel<Integer>(new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12}));
		comboBoxMese.setBounds(212, 205, 60, 26);
		getContentPane().add(comboBoxMese);
		
		// label corrispondente al mese dell'anno
		meseLabel = new JLabel("Mese:");
		meseLabel.setBounds(212, 183, 53, 13);
		getContentPane().add(meseLabel);
		meseLabel.setLabelFor(comboBoxMese);
		
		// creo una lista di anni che vanno dal 1930 ad oggi; questa lista andrà inserita nella combobox degli anni sottoforma di array
		ArrayList<Integer> listaAnni = new ArrayList<Integer>(100);
		for (int i = LocalDate.now().getYear(); i >= 1930; i--) {
			listaAnni.add(i);
		}
		listaAnni.add(0, 0);
		
		// menu combobox per scegliere l'anno
		comboBoxAnno = new JComboBox<Integer>();
		comboBoxAnno.setModel(new DefaultComboBoxModel<Integer>(listaAnni.toArray(new Integer[0])));
		comboBoxAnno.setBounds(290, 205, 65, 26);
		getContentPane().add(comboBoxAnno);
		
		// label corrispondente all'anno
		annoLabel = new JLabel("Anno:");
		annoLabel.setBounds(290, 183, 53, 13);
		getContentPane().add(annoLabel);
		annoLabel.setLabelFor(comboBoxAnno);
		
		// pulsante per avviare la ricerca in base a cio' che e' stato selezionato nelle comboBox
		cercaPerDataVenditaButton = new JButton("Cerca");
		dataVenditaLabel.setLabelFor(cercaPerDataVenditaButton);
		cercaPerDataVenditaButton.setBounds(10, 203, 88, 31);
		cercaPerDataVenditaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Integer giorno = (Integer)comboBoxGiorno.getSelectedItem();
				Integer mese = (Integer)comboBoxMese.getSelectedItem();
				Integer anno = (Integer)comboBoxAnno.getSelectedItem();
				
				// utilizzo il gestore vendite per cercare un vendita con la data inserita;
				// se non è stato inserito nulla verrà segnalato l'errore
				if ((giorno != 0) && (mese != 0) && (anno != 0)) {
					try {
						Data dataVendita = new Data(giorno, mese, anno);
						
						Set<Vendita<MerceVenduta>> vendite = new HashSet<Vendita<MerceVenduta>>();
						vendite.addAll(gestoreVendite.getVenditeByData(dataVendita));
						
						istanzaCorrente.printListaVendite(vendite);
						
						mainJFrame.setEnabled(true);
						dispose();
					}
					catch (DateTimeException t) {
						JOptionPane.showMessageDialog(finestraCorrente, "Data inserita non valida.", "Errore", JOptionPane.ERROR_MESSAGE);
					} 
					catch (GestoreVenditaException t) {
						JOptionPane.showMessageDialog(finestraCorrente, "Nessuna vendita salvata in questa data.", "Attenzione", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(finestraCorrente, "E' necessario selezionare una data reale.", "Attenzione", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		getContentPane().add(cercaPerDataVenditaButton);
		
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
