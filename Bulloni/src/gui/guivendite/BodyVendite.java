package gui.guivendite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import gestori.gestoreImpiegati.GestoreImpiegatiDb;
import gestori.gestorevendite.ContainerVendite;
import gestori.gestorevendite.exception.GestoreVenditaException;
import gestori.gestoribulloni.GestoreBulloni;
import vendita.MerceVenduta;
import vendita.Vendita;

/**
 * @author GiannettaGerardo
 *
 * Classe principale per la gestione delle vendite;
 * Permette di visualizzare una lista completa di tutte le vendite, di visualizzarne i dettagli sulla merce venduta,
 * di modificare una vendita a partire dalla merce, di eliminare una vendita, di aggiungere una nuova vendita o di 
 * visualizzare tutti i dettagli riguardanti l'impiegato che ha effettuato la vendita o della singola merce nello specifico
 */
public class BodyVendite extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// costanti
	private static final int WIDTH_BODY = 750;
	private static final int HEIGHT_BODY = 652;
	private static final int X_BODY = 100;
	private static final int Y_BODY = 100;
	private static final int WIDTH_SCROLLPANE = 768;
	private static final int HEIGHT_SCROLLPANE = 507;
	private static final int X_SCROLLPANE = 10;
	private static final int Y_SCROLLPANE = 50;
	
	// oggetti per creare l'interfaccia grafica
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel codiceLabel;
	private JLabel impiegatoLabel;
	private JLabel quantitaTotaleBulloniLabel;
	private JLabel prezzoTotaleBulloniLabel;
	private JLabel merceVendutaLabel;
	private JButton aggiungiVenditaButton;
	private JButton cercaPerButton;
	private JButton visualizzaTuttoButton;
	private BodyVendite istanzaCorrente = this;
	private JFrame mainMenu;
	
	// gestore delle vendite con interfaccia generale contenente tutti i metodi
	private ContainerVendite gestoreVendite;
	
	// gestore dei bulloni
	private GestoreBulloni gestoreBulloni;
	
	// gestore degli impiegati con interfaccia di visualizzazione
	private GestoreImpiegatiDb gestoreImpiegati;
	
	private static final int SOGLIA_MASSIMA_LISTA_VENDITE = 17;
	
	
	/**
	 * Costruttore del pannello principale delle vendite
	 * 
	 * @param mainMenu finestra principale nella quale questo pannello è situato
	 */
	public BodyVendite(JFrame mainMenu, ContainerVendite gestoreVendite, GestoreBulloni gestoreBulloni, GestoreImpiegatiDb gestoreImpiegati) {
		this.mainMenu = mainMenu;
		this.gestoreVendite = gestoreVendite;
		this.gestoreBulloni = gestoreBulloni;
		this.gestoreImpiegati = gestoreImpiegati;
		inizializza();
		createAggiungiVenditaButton();
		createCercaPerButton();
		creareVisualizzaTuttoButton();
		
		try {
			printListaVendite(gestoreVendite.getVendite());
		}
		catch (GestoreVenditaException t) {
			JOptionPane.showMessageDialog(mainMenu, t.getMessage());
		}
	}
	
	
	/**
	 * Metodo che inizializza tutto il layout del pannello
	 */
	private void inizializza() {
		
		setBounds(X_BODY, Y_BODY, WIDTH_BODY, HEIGHT_BODY);
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(X_SCROLLPANE, Y_SCROLLPANE, WIDTH_SCROLLPANE, HEIGHT_SCROLLPANE);
		add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
	}
	
	
	/**
	 * Metodo che stampa le instestazioni delle colonne per la lista di vendite
	 */
	private void printIntestazioniColonne() {
		
		codiceLabel = new JLabel("Codice");
		codiceLabel.setForeground(Color.red);
		GridBagConstraints gbc_codiceLabel = new GridBagConstraints();
		gbc_codiceLabel.insets = new Insets(0, 3, 5, 8);
		gbc_codiceLabel.gridx = 0;
		gbc_codiceLabel.gridy = 0;
		panel.add(codiceLabel, gbc_codiceLabel);
		
		impiegatoLabel = new JLabel("Impiegato");
		impiegatoLabel.setForeground(Color.red);
		GridBagConstraints gbc_impiegatoLabel = new GridBagConstraints();
		gbc_impiegatoLabel.insets = new Insets(0, 3, 5, 8);
		gbc_impiegatoLabel.gridx = 1;
		gbc_impiegatoLabel.gridy = 0;
		panel.add(impiegatoLabel, gbc_impiegatoLabel);
		
		quantitaTotaleBulloniLabel = new JLabel("Quantita'_totale_bulloni");
		quantitaTotaleBulloniLabel.setForeground(Color.red);
		GridBagConstraints gbc_quantitaTotaleBulloniLabel = new GridBagConstraints();
		gbc_quantitaTotaleBulloniLabel.insets = new Insets(0, 3, 5, 8);
		gbc_quantitaTotaleBulloniLabel.gridx = 2;
		gbc_quantitaTotaleBulloniLabel.gridy = 0;
		panel.add(quantitaTotaleBulloniLabel, gbc_quantitaTotaleBulloniLabel);
		
		prezzoTotaleBulloniLabel = new JLabel("Prezzo_totale_bulloni");
		prezzoTotaleBulloniLabel.setForeground(Color.red);
		GridBagConstraints gbc_prezzoTotaleBulloniLabel = new GridBagConstraints();
		gbc_prezzoTotaleBulloniLabel.insets = new Insets(0, 3, 5, 8);
		gbc_prezzoTotaleBulloniLabel.gridx = 3;
		gbc_prezzoTotaleBulloniLabel.gridy = 0;
		panel.add(prezzoTotaleBulloniLabel, gbc_prezzoTotaleBulloniLabel);
		
		merceVendutaLabel = new JLabel("Merce_venduta");
		merceVendutaLabel.setForeground(Color.red);
		GridBagConstraints gbc_merceVendutaLabel = new GridBagConstraints();
		gbc_merceVendutaLabel.insets = new Insets(0, 3, 5, 8);
		gbc_merceVendutaLabel.gridx = 4;
		gbc_merceVendutaLabel.gridy = 0;
		panel.add(merceVendutaLabel, gbc_merceVendutaLabel);
	}
	
	
	
	/**
	 * Metodo che crea il pulsante per aggiungere una nuova vendita
	 */
	public void createAggiungiVenditaButton() {
		
		aggiungiVenditaButton = new JButton("Aggiungi vendita");
		aggiungiVenditaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// creare InputForm
				mainMenu.setEnabled(false);
			}
		});
		aggiungiVenditaButton.setBounds(10, 565, 136, 30);
		add(aggiungiVenditaButton);
	}
	
	
	/**
	 * Metodo che crea il pulsante per iniziare la ricerca delle vendite in base a dei parametri di ricerca
	 */
	public void createCercaPerButton() {
		
		cercaPerButton = new JButton("Cerca per...");
		cercaPerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// creare SelezionaRicerca
				mainMenu.setEnabled(false);
			}
		});
		cercaPerButton.setBounds(10, 10, 118, 30);
		add(cercaPerButton);
	}
	
	
	/**
	 * Metodo che crea il pulsante per ristampare tutta la lista completa di vendite, utile dopo una ricerca
	 */
	public void creareVisualizzaTuttoButton() {
		
		visualizzaTuttoButton = new JButton("Visualizza tutto");
		visualizzaTuttoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				
				try {
					printListaVendite(gestoreVendite.getVendite());
				}
				catch (GestoreVenditaException t) {
					JOptionPane.showMessageDialog(mainMenu, t.getMessage());
				}
			}
		});
		visualizzaTuttoButton.setBounds(657, 10, 120, 30);
		add(visualizzaTuttoButton);
	}
	
	
	/**
	 * Metodo che stampa una lista di vendite
	 */
	public void printListaVendite(Set<Vendita<MerceVenduta>> vendite) {
		
		// stampa le intestazioni delle colonne per la lista di vendite
		printIntestazioniColonne();
		
		final int DIMENSIONE = vendite.size();
		
		// controllo necessario per mantenere un layout visivamente corretto quando si supera un certo numero di vendite
		if (DIMENSIONE >= SOGLIA_MASSIMA_LISTA_VENDITE)
			scrollPane.setViewportView(panel);
		else
			scrollPane.setColumnHeaderView(panel);
		
		// oggetti componenti della lista di vendite
		JLabel[] codLabel = new JLabel[DIMENSIONE];
		JLabel[] matricolaImpLabel = new JLabel[DIMENSIONE];
		JLabel[] quantitaTotLabel = new JLabel[DIMENSIONE];
		JLabel[] prezzoTotLabel = new JLabel[DIMENSIONE];
		JButton[] visualButton = new JButton[DIMENSIONE];
		JButton[] infoImpiegatoButton = new JButton[DIMENSIONE];
		JButton[] deleteButton = new JButton[DIMENSIONE];
		int posizioneX = 0;
		int i = 0;
		
		for (Vendita<MerceVenduta> vendita : vendite) {
			
			posizioneX = 0;
			
			// stampo i codici vendita nella lista
			codLabel[i] = new JLabel(((Integer)vendita.getCodVendita()).toString());
			GridBagConstraints gbc_codLabel = new GridBagConstraints();
			gbc_codLabel.insets = new Insets(0, 2, 5, 5);
			gbc_codLabel.gridx = posizioneX;
			gbc_codLabel.gridy = i+1;
			panel.add(codLabel[i], gbc_codLabel);
			
			// stampo le matricole degli impiegati nella lista
			matricolaImpLabel[i] = new JLabel(((Integer)vendita.getResponsabileVendita()).toString());
			GridBagConstraints gbc_matricolaImpLabel = new GridBagConstraints();
			gbc_matricolaImpLabel.insets = new Insets(0, 0, 5 ,1);
			gbc_matricolaImpLabel.gridx = ++posizioneX;
			gbc_matricolaImpLabel.gridy = i+1;
			panel.add(matricolaImpLabel[i], gbc_matricolaImpLabel);
			
			// stampo la quantita' totale di merce venduta nella lista
			quantitaTotLabel[i] = new JLabel(((Integer)vendita.getQuantitaMerceTotale()).toString());
			GridBagConstraints gbc_quantitaTotLabel = new GridBagConstraints();
			gbc_quantitaTotLabel.insets = new Insets(0, 0, 5, 1);
			gbc_quantitaTotLabel.gridx = ++posizioneX;
			gbc_quantitaTotLabel.gridy = i+1;
			panel.add(quantitaTotLabel[i], gbc_quantitaTotLabel);
			
			// stampo il prezzo totale della merce venduta nella lista
			prezzoTotLabel[i] = new JLabel(((Double)vendita.getPrezzoVenditaTotale()).toString());
			GridBagConstraints gbc_prezzoTotLabel = new GridBagConstraints();
			gbc_prezzoTotLabel.insets = new Insets(0, 0, 5, 1);
			gbc_prezzoTotLabel.gridx = ++posizioneX;
			gbc_prezzoTotLabel.gridy = i+1;
			panel.add(prezzoTotLabel[i], gbc_prezzoTotLabel);
			
			// pulsante che permette di visualizzare tutte le info sulla merce venduta tramite un'apposita finestra
			visualButton[i] = new JButton("Info merce");
			GridBagConstraints gbc_visualButton = new GridBagConstraints();
			gbc_visualButton.insets = new Insets(0, 0, 5, 0);
			gbc_visualButton.gridx = ++posizioneX;
			gbc_visualButton.gridy = i+1;
			visualButton[i].addActionListener(new GestoreButton(gestoreVendite, mainMenu, codLabel[i].getText()));
			panel.add(visualButton[i], gbc_visualButton);
			
			// pulsate che permette di visualizzare tutte le info sull'impiegato che ha effettuato la vendita in un'apposita finestra
			infoImpiegatoButton[i] = new JButton("Impiegato");
			GridBagConstraints gbc_infoImpiegatoButton = new GridBagConstraints();
			gbc_infoImpiegatoButton.insets = new Insets(0, 0, 5, 0);
			gbc_infoImpiegatoButton.gridx = ++posizioneX;
			gbc_infoImpiegatoButton.gridy = i+1;
			infoImpiegatoButton[i].addActionListener(new GestoreButton(gestoreVendite, mainMenu, matricolaImpLabel[i].getText()));
			panel.add(infoImpiegatoButton[i], gbc_infoImpiegatoButton);
			
			// pulsante che permette di eliminare questa vendita dal set originale, dal database e dalla lista
			deleteButton[i] = new JButton("Elimina");
			deleteButton[i].setBackground(Color.red);
			deleteButton[i].setForeground(Color.white);
			GridBagConstraints gbc_deleteButton = new GridBagConstraints();
			gbc_deleteButton.insets = new Insets(0, 0, 5, 25);
			gbc_deleteButton.gridx = ++posizioneX;
			gbc_deleteButton.gridy = i+1;
			deleteButton[i].addActionListener(new GestoreButton(gestoreVendite, mainMenu, codLabel[i].getText()));
			panel.add(deleteButton[i], gbc_deleteButton);
			
			i++;
		}
		
	}

}
