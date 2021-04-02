package gui.guibulloni;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import bulloni.Bullone;
import gestori.gestoribulloni.ModificaBulloni;
import gestori.gestoribulloni.VisualizzaBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;


/**
 * Pannello principale che contiene una lista riepilogativa di tutti i bulloni. Estende la classe JPanel in modo da renderlo utilizzabile
 * con una finestra che gestisce piu' pannelli.
 * 
 * @author Catignano Francesco
 */
public class BodyBulloni extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_WIDTH = 750;
	private static final int MAX_HEIGHT = 650;
	
	private JFrame mainFrame;
	private VisualizzaBulloni visualizzaBulloni;	// Interfaccia di visualizzazione
	
	/*
	 * Elementi visibili nel pannello
	 */
	JPanel pannelloRicerca = new JPanel();
	JButton btnCercaPer = new JButton();
	JButton btnVisualizzaTutto = new JButton();
	JPanel footerPanel = new JPanel();
	JButton btnAggiungiBullone = new JButton();
	JScrollPane scrollPane = new JScrollPane();	// Contiene il pannello con la barra di scorrimento
	JPanel listaContainerPanel = new JPanel();	// Pannello contenente la lista di bulloni
	JLabel lblMancanzaBulloni = new JLabel();	// Label per informare l'utente della mancanza di bulloni

	
	/*
	 * -------------
	 *  COSTRUTTORE
	 * -------------
	 */
	/**
	 * Costruisce il pannello principale contenente la lista di bulloni.
	 * @param mainFrame La finestra principale in cui si trova questo pannello.
	 * @param visualizzaBulloni L'interfaccia di visualizzazione per i bulloni.
	 */
	public BodyBulloni(JFrame mainFrame, VisualizzaBulloni visualizzaBulloni) {
		this.mainFrame = mainFrame;
		this.visualizzaBulloni = visualizzaBulloni;
		
		/*
		 * Questo layout servira' per posizionare le label e i pulsanti in alto e in basso
		 */
		this.setLayout(new BorderLayout());
		this.setSize(MAX_WIDTH, MAX_HEIGHT);
		
		/*
		 * Creazione degli elementi
		 */
		this.creaPannelloRicerca();
		this.creaFooterPanel();
		this.creaListaContainerPanel(this.visualizzaBulloni.getAll());
	}
	
	
	/*
	 *-------------------
	 *  METODI PUBBLICI
	 *-------------------
	 */
	
	/**
	 * Metodo per "ricaricare" il pannello e mostrare tutti gli elementi.
	 * Utile, ad esempio, per mostrare tutta la lista, ad eccezione di un bullone che e' stato rimosso, oppure per mostrare i risultati di una ricerca.
	 * 
	 * @param bulloni. I bulloni che devono essere mostrati dopo il refresh della pagina
	 */
	public void refresh(Set<Bullone> bulloni) {
		this.listaContainerPanel.removeAll();
		this.creaListaContainerPanel(bulloni);
	}
	
	
	/**
	 * Variante aparametrica del metodo refresh().
	 * Il metodo si occupa da solo di rimediare l'intero set di bulloni e di mostrarlo.
	 */
	public void refresh() {
		this.listaContainerPanel.removeAll();
		this.creaListaContainerPanel(this.visualizzaBulloni.getAll());
	}
	
	
	/*
	 *------------------
	 *  METODI PRIVATI
	 *------------------
	 */
	
	/**
	 * Metodo per creare il pannello contenente il pulsante per effettuare una ricerca di bulloni.
	 * 
	 * Viene aggiunto, al pannello principale, un ulteriore pannello, posizionato in alto.
	 * Questo pannello contiene due pulsanti: uno per effettuare la ricerca e uno per far riapparire tutta la lista originale, dopo che sono apparsi i risultati di ricerca.
	 * Quest'ultimo non e' subito visibile, ma appare solamente quando appaiono anche i risultati di ricerca, facendo scomparire il primo pulsante.
	 */
	private void creaPannelloRicerca() {
		this.add(this.pannelloRicerca, BorderLayout.NORTH);
		this.pannelloRicerca.setLayout(new BoxLayout(this.pannelloRicerca, BoxLayout.X_AXIS));	// Layout per il pannello di ricerca
		
		/*
		 * Aggiunta del bottone "Cerca Per..."
		 */
		this.btnCercaPer.setText("Cerca per...");
		this.pannelloRicerca.add(this.btnCercaPer);
		// Aggiungere action listener
		
		/*
		 * Aggiunta del bottone "Visualizza tutto"
		 */
		this.btnVisualizzaTutto.setText("Visualizza tutto");
		this.pannelloRicerca.add(this.btnVisualizzaTutto);
		this.btnVisualizzaTutto.setVisible(false);
		// Aggiungere action listener
	}
	
	
	/**
	 * Metodo per la creazione del footer panel.
	 * Viene creato un pannello posizionato in basso e contiene solo il pulsante "Aggiungi un bullone".
	 */
	private void creaFooterPanel() {
		this.add(this.footerPanel, BorderLayout.SOUTH);
		this.footerPanel.setLayout(new BorderLayout());
		
		/*
		 * Aggiunta del bottone "Aggiungi un bullone"
		 */
		this.btnAggiungiBullone.setText("+ Aggiungi un bullone");
		this.footerPanel.add(this.btnAggiungiBullone);
		// Aggiungere action listener
	}
	
	
	/**
	 * Metodo per creare e impostare un pannello contenente la lista di bulloni.
	 * Viene aggiunto uno JScrollPane per utilizzare la barra laterale di scorrimento. Viene aggiunto anche un pannello all'interno per impostare un layout
	 * piu' flessibile (GridBagLayout) per lo scopo.
	 * Viene effettuato un controllo sulla presenza o meno dei bulloni nel set; se non sono presenti, viene aggiunta una label che informa l'utente della mancanza
	 * di bulloni, mentre il tasto per effettuare la ricerca scompare. In caso contrario, viene chiamato il metodo che si occupa di mostrare i bulloni.
	 * 
	 * @param bulloni Il set di bulloni da mostrare
	 */
	private void creaListaContainerPanel(Set<Bullone> bulloni) {
		/*
		 * Impostazione di JScrollPane
		 */
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(this.scrollPane, BorderLayout.CENTER);
		
		/*
		 * Impostazione di listaContainerPane, il pannello contenente la lista di bulloni
		 */
		this.scrollPane.setColumnHeaderView(this.listaContainerPanel);	// Aggiunto il pannello a scrollpane
		GridBagLayout gblForListaContainerPanel = new GridBagLayout();	// Layout per il pannello
		gblForListaContainerPanel.columnWidths = new int[] {0};	// 1 colonna
		gblForListaContainerPanel.columnWeights = new double[] {0};
		gblForListaContainerPanel.rowHeights = new int[] {0};	// 1 riga
		gblForListaContainerPanel.rowWeights = new double[] {0};
		// Vincoli per il pannello
		GridBagConstraints gbcForListaContainerPanel = new GridBagConstraints();
		gbcForListaContainerPanel.gridwidth = GridBagConstraints.REMAINDER;
		gbcForListaContainerPanel.gridheight = GridBagConstraints.RELATIVE;
		gbcForListaContainerPanel.weightx = 1;
		gbcForListaContainerPanel.weighty = 1;
		this.listaContainerPanel.setLayout(gblForListaContainerPanel);
		
		// Controllo sulla presenza dei bulloni
		try {
			if(visualizzaBulloni.isEmpty()) {
				this.btnCercaPer.setVisible(false);
				this.lblMancanzaBulloni.setText("Non sono presenti bulloni. Aggiungine uno!");
				GridBagConstraints gbcForLblMancanzaBulloni = new GridBagConstraints();
				gbcForLblMancanzaBulloni.anchor = GridBagConstraints.CENTER;
				this.listaContainerPanel.add(lblMancanzaBulloni, gbcForLblMancanzaBulloni);
			} else {
				this.lblMancanzaBulloni.setVisible(false);
				this.btnCercaPer.setVisible(true);
				this.mostraBulloni(bulloni);	
			}
		}
		catch(GestoreBulloniException e) {
			System.err.println(e.getMessage());
		}
 	}
	
	
	/**
	 * Metodo per creare i pannelli relativi ad ogni singolo bullone e per mostrarli nell'interfaccia.
	 * Riceve in input un set di bulloni e per ogni codice, cerca le sue informazioni che verranno passate al pannello.
	 * Mostra solamente i bulloni che non sono stati eliminati.
	 * @param bulloni Il set di bulloni da mostrare.
	 * @throws GestoreBulloniException 
	 */
	private void mostraBulloni(Set<Bullone> bulloni) throws GestoreBulloniException {
		int posY = 0;
		for(Bullone b : bulloni) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = posY;
			
			if(b.isEliminato()==false) {
				this.listaContainerPanel.add(new SimpleInfoBullonePanel(this.mainFrame, this, b.getCodice(), this.visualizzaBulloni.getInfoBulloneByCodice(b.getCodice()), (ModificaBulloni)this.visualizzaBulloni), gbc);
			}
			
			posY++;
		}
	}
}
