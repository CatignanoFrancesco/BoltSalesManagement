package gui.guivendite;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gestori.gestorevendite.ContainerVendite;
import gestori.gestorevendite.ModificaVendite;
import gestori.gestoribulloni.VisualizzaBulloni;
import vendita.MerceVenduta;

/**
 * @author GiannettaGerardo
 *
 */
public class VisualizzaMerceVenduta extends JDialog {

	private static final long serialVersionUID = 1L;
	
	/** coordinata x della finestra */
	private static final int X = 100;
	/** coordinata y della finestra */
	private static final int Y = 100;
	/** larghezza della finestra */
	private static final int WIDTH = 870;
	/** lunghezza della finestra */
	private static final int HEIGHT = 345;
	
	/** coordinata x dello scrollPane */
	private static final int X_SCROLLPANE = 10;
	/** coordinata y dello scrollPane */
	private static final int Y_SCROLLPANE = 10;
	/** larghezza dello scrollPane */
	private static final int WIDTH_SCROLLPANE = 850;
	/** lunghezza dello scrollPane */
	private static final int HEIGHT_SCROLLPANE = 280;
	
	// oggetti per creare l'interfaccia grafica
	private JDialog thisDialog = this;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel codiceBulloneLabel;
	private JLabel quantitaBulloniLabel;
	private JLabel prezzoPerQuantitaLabel;
	private JLabel prezzoDiVenditaBulloneLabel;
	/** titolo della finestra grafica */
	private final String titoloFinestra = "Merce venduta";
	
	/** gestore delle vendite con interfaccia contenente i metodi di modifica */
	private ModificaVendite gestoreVendite;
	
	/** gestore dei bulloni con interfaccia contenente i metodi di visualizzazione */
	private VisualizzaBulloni gestoreBulloni;
	
	/** codice univoco della vendita alla quale il set di merce venduta è associato */
	private int codiceVendita;
	
	/** set di merce venduta associata alla vendita selezionata */
	private Set<MerceVenduta> merce;
	
	/** istanza corrente del body vendite */
	private BodyVendite istanzaCorrente;
	
	/** oltre questa soglia, il pannello contenente la lista cambierà tipo di layout, in modo da attivare la scrollbar senza problemi di layout */
	private static final int SOGLIA_MASSIMA_LISTA_MERCE = 13;
	
	
	/**
	 * Costruttore della classe VisualizzaMerceVenduta
	 * 
	 * @param mainJF finestra principale da bloccare
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param codiceVendita codice univoco della vendita selezionata
	 * @param merce set di merce venduta nella vendita selezionata
	 */
	public VisualizzaMerceVenduta(ModificaVendite gestoreVendite, VisualizzaBulloni gestoreBulloni, int codiceVendita, Set<MerceVenduta> merce, BodyVendite istanzaCorrente) {
		this.gestoreVendite = gestoreVendite;
		this.gestoreBulloni = gestoreBulloni;
		this.codiceVendita = codiceVendita;
		this.merce = merce;
		this.istanzaCorrente = istanzaCorrente;
		
		inizializza();
		printMerceVenduta(this.merce);
		
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(X_SCROLLPANE, Y_SCROLLPANE, WIDTH_SCROLLPANE, HEIGHT_SCROLLPANE);
		getContentPane().add(scrollPane);
		
		// pannello con layout di tipo griglia, per contenere la lista di merce venduta
		panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
	}
	
	
	/**
	 * Metodo che crea le instestazioni per le colonne della lista di merce venduta
	 */
	private void createInstestazioniColonne() {
		
		codiceBulloneLabel =  new JLabel("Codice bullone");
		codiceBulloneLabel.setForeground(Color.red);
		GridBagConstraints gbc_codiceBulloneLabel = new GridBagConstraints();
		gbc_codiceBulloneLabel.insets = new Insets(0, 3, 5, 8);
		gbc_codiceBulloneLabel.gridx = 0;
		gbc_codiceBulloneLabel.gridy = 0;
		panel.add(codiceBulloneLabel, gbc_codiceBulloneLabel);
		
		quantitaBulloniLabel =  new JLabel("Quantita'_bulloni");
		quantitaBulloniLabel.setForeground(Color.red);
		GridBagConstraints gbc_quantitaBulloniLabel = new GridBagConstraints();
		gbc_quantitaBulloniLabel.insets = new Insets(0, 3, 5, 8);
		gbc_quantitaBulloniLabel.gridx = 1;
		gbc_quantitaBulloniLabel.gridy = 0;
		panel.add(quantitaBulloniLabel, gbc_quantitaBulloniLabel);
		
		prezzoPerQuantitaLabel =  new JLabel("Prezzo_per_quantita'");
		prezzoPerQuantitaLabel.setForeground(Color.red);
		GridBagConstraints gbc_prezzoPerQuantitaLabel = new GridBagConstraints();
		gbc_prezzoPerQuantitaLabel.insets = new Insets(0, 3, 5, 8);
		gbc_prezzoPerQuantitaLabel.gridx = 2;
		gbc_prezzoPerQuantitaLabel.gridy = 0;
		panel.add(prezzoPerQuantitaLabel, gbc_prezzoPerQuantitaLabel);
		
		prezzoDiVenditaBulloneLabel =  new JLabel("Prezzo_di_vendita_bullone");
		prezzoDiVenditaBulloneLabel.setForeground(Color.red);
		GridBagConstraints gbc_prezzoDiVenditaBulloneLabel = new GridBagConstraints();
		gbc_prezzoDiVenditaBulloneLabel.insets = new Insets(0, 3, 5, 8);
		gbc_prezzoDiVenditaBulloneLabel.gridx = 3;
		gbc_prezzoDiVenditaBulloneLabel.gridy = 0;
		panel.add(prezzoDiVenditaBulloneLabel, gbc_prezzoDiVenditaBulloneLabel);
		
	}
	
	
	/**
	 * Metodo che stampa la lista di merce venduta corrispondente alla vendita selezionata nella finestra precedente
	 */
	public void printMerceVenduta(Set<MerceVenduta> merce) {
		
		// rimuove tutto dal pannello
		panel.removeAll();
		
		// stampa le intestazioni delle colonne per la lista di merce venduta
		createInstestazioniColonne();
		
		final int DIMENSIONE = merce.size();
		
		// controllo necessario per mantenere un layout visivamente corretto quando si supera un certo numero di merce venduta
		if (DIMENSIONE >= SOGLIA_MASSIMA_LISTA_MERCE)
			scrollPane.setViewportView(panel);
		else
			scrollPane.setColumnHeaderView(panel);
		
		// oggetti componenti della lista di merce venduta
		JLabel[] codBullone = new JLabel[DIMENSIONE];
		JLabel[] quantita = new JLabel[DIMENSIONE];
		JLabel[] prezzoTotale = new JLabel[DIMENSIONE];
		JLabel[] prezzoVendita = new JLabel[DIMENSIONE];
		JButton[] modificaButton = new JButton[DIMENSIONE];
		JButton[] infoButton = new JButton[DIMENSIONE];
		
		int x = 0; // indice per posizionare gli elementi nella griglia
		int i = 0; // contatore per la merce
		
		for (MerceVenduta m : merce) {
			
			x = 0;
			
			// stampo i codici dei bulloni nella lista 
			codBullone[i] = new JLabel(((Integer)m.getCodiceBullone()).toString());
			GridBagConstraints gbc_codBullone = new GridBagConstraints();
			gbc_codBullone.insets = new Insets(0, 2, 5, 5);
			gbc_codBullone.gridx = x;
			gbc_codBullone.gridy = i+1;
			panel.add(codBullone[i], gbc_codBullone);
			
			// stampo la quantita' di merce venduta per un certo bullone
			quantita[i] = new JLabel(((Integer)m.getNumeroBulloni()).toString());
			GridBagConstraints gbc_quantita = new GridBagConstraints();
			gbc_quantita.insets = new Insets(0, 0, 5, 1);
			gbc_quantita.gridx = ++x;
			gbc_quantita.gridy = i+1;
			panel.add(quantita[i], gbc_quantita);
			
			// stampo il prezzo totale di vendita di un certo bullone
			prezzoTotale[i] = new JLabel(((Float)(((Double)m.getPrezzoBulloni()).floatValue())).toString());
			GridBagConstraints gbc_prezzoTotale = new GridBagConstraints();
			gbc_prezzoTotale.insets = new Insets(0, 0, 5, 1);
			gbc_prezzoTotale.gridx = ++x;
			gbc_prezzoTotale.gridy = i+1;
			panel.add(prezzoTotale[i], gbc_prezzoTotale);
			
			// stampo il prezzo di un singolo certo bullone al momento della vendita
			prezzoVendita[i] = new JLabel(((Float)(((Double)m.getPrezzoVenditaBullone()).floatValue())).toString());
			GridBagConstraints gbc_prezzoVendita = new GridBagConstraints();
			gbc_prezzoVendita.insets = new Insets(0, 0, 5, 1);
			gbc_prezzoVendita.gridx = ++x;
			gbc_prezzoVendita.gridy = i+1;
			panel.add(prezzoVendita[i], gbc_prezzoVendita);
			
			// pulsante che permette di modificare la merce venduta
			modificaButton[i] = new JButton("Modifica");
			GridBagConstraints gbc_modificaButton = new GridBagConstraints();
			gbc_modificaButton.insets = new Insets(0, 0, 5, 1);
			gbc_modificaButton.gridx = ++x;
			gbc_modificaButton.gridy = i+1;
			modificaButton[i].addActionListener(new GestoreButton((ContainerVendite)gestoreVendite, thisDialog, ((Integer)codiceVendita).toString(), codBullone[i].getText(), istanzaCorrente));
			panel.add(modificaButton[i], gbc_modificaButton);
			
			// pulsante che permette di visualizzare tutte le informazioni sul bullone corrispondente
			infoButton[i] = new JButton("Bullone");
			GridBagConstraints gbc_infoButton = new GridBagConstraints();
			gbc_infoButton.insets = new Insets(0, 0, 5, 20);
			gbc_infoButton.gridx = ++x;
			gbc_infoButton.gridy = i+1;
			infoButton[i].addActionListener(new GestoreButton(gestoreBulloni, thisDialog, codBullone[i].getText()));
			panel.add(infoButton[i], gbc_infoButton);
			
			i++;
		}
		
	}
}
