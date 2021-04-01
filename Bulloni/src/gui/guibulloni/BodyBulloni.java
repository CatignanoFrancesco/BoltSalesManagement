package gui.guibulloni;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;


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
	
	/*
	 * -------------
	 *  COSTRUTTORE
	 * -------------
	 */
	public BodyBulloni() {
		// AGGIUNGERE FINESTRA PRINCIPALE E INTERFACCIA VISUALIZZAZIONE
		
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
		this.creaListaContainerPanel();
	}
	
	
	/*
	 *-------------------
	 *  METODI PUBBLICI
	 *-------------------
	 */
	
	/**
	 * Metodo per "ricaricare" il pannello e mostrare tutti gli elementi.
	 * Utile, ad esempio, per mostrare tutta la lista, ad eccezione di un bullone che e' stato rimosso.
	 */
	public void refresh() {
		this.listaContainerPanel.removeAll();
		this.creaListaContainerPanel();
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
	 */
	private void creaListaContainerPanel() {
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
		this.listaContainerPanel.setBorder(new MatteBorder(1,1,1,1,Color.RED));
		this.listaContainerPanel.setLayout(gblForListaContainerPanel);
		
		// metodo per mostrare i bulloni
	}
}
