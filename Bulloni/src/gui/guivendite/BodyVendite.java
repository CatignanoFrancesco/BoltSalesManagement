package gui.guivendite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import gestori.gestoreImpiegati.ContainerImpiegato;
import gestori.gestoreImpiegati.GestoreImpiegatiDb;
import gestori.gestoreImpiegati.VisualizzazioneImpiegato;
import gestori.gestorevendite.ContainerVendite;
import gestori.gestorevendite.InserimentoVendite;
import gestori.gestorevendite.exception.GestoreVenditaException;
import gestori.gestoribulloni.ContainerBulloni;
import gestori.gestoribulloni.GestoreBulloni;
import gestori.gestoribulloni.VisualizzaBulloni;
import vendita.MerceVenduta;
import vendita.Vendita;

/**
 * Classe principale per la gestione delle vendite;
 * Permette di visualizzare una lista completa di tutte le vendite, di visualizzarne i dettagli sulla merce venduta,
 * di modificare una vendita a partire dalla merce, di eliminare una vendita, di aggiungere una nuova vendita o di 
 * visualizzare tutti i dettagli riguardanti l'impiegato che ha effettuato la vendita o della singola merce nello specifico
 * 
 * @author GiannettaGerardo
 */
public class BodyVendite extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/** coordinata x del body */
	private static final int X_BODY = 100;
	/** coordinata y del body */
	private static final int Y_BODY = 100;
	/** larghezza del body */
	private static final int WIDTH_BODY = 950;
	/** lunghezza del body */
	private static final int HEIGHT_BODY = 652;
	
	/** coordinata x dello scrollPane */
	private static final int X_SCROLLPANE = 10;
	/** coordinata y dello scrollPane */
	private static final int Y_SCROLLPANE = 50;
	/** larghezza dello scrollPane */
	private static final int WIDTH_SCROLLPANE = 975;
	/** lunghezza dello scrollPane */
	private static final int HEIGHT_SCROLLPANE = 507;
	
	// oggetti per creare l'interfaccia grafica
	
	/**  pannello scorrevole che conterrà un panello con layout di tipo griglia */
	private JScrollPane scrollPane;
	/** pannello con layout di tipo griglia, che conterrà le vendite */
	private JPanel panel;
	/** label corrispondente all'intestazione della tabella riguardante i codici delle vendite */
	private JLabel codiceLabel;
	/** label corrispondente all'intestazione della tabella riguardante le matricole degli impiegati */
	private JLabel impiegatoLabel;
	/** label corrispondente all'intestazione della tabella riguardante la data di effettuazione della vendita */
	private JLabel dataLabel;
	/** label corrispondente all'intestazione della tabella riguardante la quantita' totale dei bulloni venduti */
	private JLabel quantitaTotaleBulloniLabel;
	/** label corrispondente all'intestazione della tabella riguardante il prezzo totale dei bulloni venduti, ovvero il conto finale */
	private JLabel prezzoTotaleBulloniLabel;
	/** label corrispondente all'intestazione della tabella riguardante la merce venduta */
	private JLabel merceVendutaLabel;
	/** pulsante che apre la finestra per aggiungere una nuova vendita */
	private JButton aggiungiVenditaButton;
	/** pulsante che apre la finestra per la ricerca di vendite */
	private JButton cercaPerButton;
	/** pulsante che ristampa la lista completa delle vendite, utile dopo una ricerca di vendite specifiche */
	private JButton visualizzaTuttoButton;
	/** istanza corrente della classe BodyVendite */
	private BodyVendite istanzaCorrente = this;
	/** istanza corrente della classe JFrame principale sulla quale è posizionato questa classe JPanel */
	private JFrame mainMenu;
	
	/** gestore delle vendite con interfaccia generale contenente tutti i metodi */
	private ContainerVendite gestoreVendite;
	
	/** gestore dei bulloni */
	private VisualizzaBulloni gestoreBulloni;
	
	/** gestore degli impiegati con interfaccia di visualizzazione */
	private VisualizzazioneImpiegato gestoreImpiegati;
	
	/** oltre questa soglia, il pannello contenente la lista cambierà tipo di layout, in modo da attivare la scrollbar senza problemi di layout */
	private static final int SOGLIA_MASSIMA_LISTA_VENDITE = 17;
	
	
	/**
	 * Costruttore del pannello principale delle vendite
	 * 
	 * @param mainMenu finestra principale nella quale questo pannello è situato
	 */
	public BodyVendite(JFrame mainMenu, ContainerVendite gestoreVendite, ContainerBulloni gestoreBulloni, ContainerImpiegato gestoreImpiegati) {
		this.mainMenu = mainMenu;
		this.gestoreVendite = gestoreVendite;
		this.gestoreBulloni = (GestoreBulloni) gestoreBulloni;
		this.gestoreImpiegati = (GestoreImpiegatiDb) gestoreImpiegati;
		inizializza();
		createAggiungiVenditaButton();
		createCercaPerButton();
		creareVisualizzaTuttoButton();
		
		try {
			printListaVendite(gestoreVendite.getVendite());
		}
		catch (GestoreVenditaException t) {
			printListaVendite(new HashSet<Vendita<MerceVenduta>>());
		}
		
		setVisible(true);
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
		gbc_codiceLabel.insets = new Insets(5, 5, 5, 8);
		gbc_codiceLabel.gridx = 0;
		gbc_codiceLabel.gridy = 0;
		panel.add(codiceLabel, gbc_codiceLabel);
		
		impiegatoLabel = new JLabel("Impiegato");
		impiegatoLabel.setForeground(Color.red);
		GridBagConstraints gbc_impiegatoLabel = new GridBagConstraints();
		gbc_impiegatoLabel.insets = new Insets(5, 5, 5, 8);
		gbc_impiegatoLabel.gridx = 1;
		gbc_impiegatoLabel.gridy = 0;
		panel.add(impiegatoLabel, gbc_impiegatoLabel);
		
		dataLabel = new JLabel("Data_vendita");
		dataLabel.setForeground(Color.red);
		GridBagConstraints gbc_dataLabel = new GridBagConstraints();
		gbc_dataLabel.insets = new Insets(5, 5, 5, 8);
		gbc_dataLabel.gridx = 2;
		gbc_dataLabel.gridy = 0;
		panel.add(dataLabel, gbc_dataLabel);
		
		quantitaTotaleBulloniLabel = new JLabel("Quantita'_totale_bulloni");
		quantitaTotaleBulloniLabel.setForeground(Color.red);
		GridBagConstraints gbc_quantitaTotaleBulloniLabel = new GridBagConstraints();
		gbc_quantitaTotaleBulloniLabel.insets = new Insets(5, 5, 5, 8);
		gbc_quantitaTotaleBulloniLabel.gridx = 3;
		gbc_quantitaTotaleBulloniLabel.gridy = 0;
		panel.add(quantitaTotaleBulloniLabel, gbc_quantitaTotaleBulloniLabel);
		
		prezzoTotaleBulloniLabel = new JLabel("Prezzo_totale_bulloni");
		prezzoTotaleBulloniLabel.setForeground(Color.red);
		GridBagConstraints gbc_prezzoTotaleBulloniLabel = new GridBagConstraints();
		gbc_prezzoTotaleBulloniLabel.insets = new Insets(5, 5, 5, 8);
		gbc_prezzoTotaleBulloniLabel.gridx = 4;
		gbc_prezzoTotaleBulloniLabel.gridy = 0;
		panel.add(prezzoTotaleBulloniLabel, gbc_prezzoTotaleBulloniLabel);
		
		merceVendutaLabel = new JLabel("Merce_venduta");
		merceVendutaLabel.setForeground(Color.red);
		GridBagConstraints gbc_merceVendutaLabel = new GridBagConstraints();
		gbc_merceVendutaLabel.insets = new Insets(5, 5, 5, 8);
		gbc_merceVendutaLabel.gridx = 5;
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
				
				if (gestoreImpiegati.localSetIsEmpty() || gestoreBulloni.isEmpty()) {
					JOptionPane.showMessageDialog(mainMenu, "Impossibile aggiungere una vendita, non ci sono impiegati o bulloni registrati.", "Warning", JOptionPane.ERROR_MESSAGE);
				}
				else if ((gestoreBulloni.getBulloniDisponibili()).isEmpty()) {
					JOptionPane.showMessageDialog(mainMenu, "Impossibile aggiungere una vendita, non ci sono impiegati o bulloni registrati.", "Warning", JOptionPane.ERROR_MESSAGE);
				}
				else {
					InputForm inpf = new InputForm((InserimentoVendite)gestoreVendite, gestoreImpiegati, gestoreBulloni, istanzaCorrente);
					inpf.setVisible(true);
				}
			}
		});
		aggiungiVenditaButton.setBounds(10, 575, 165, 30);
		add(aggiungiVenditaButton);
	}
	
	
	/**
	 * Metodo che crea il pulsante per iniziare la ricerca delle vendite in base a dei parametri di ricerca
	 */
	public void createCercaPerButton() {
		
		cercaPerButton = new JButton("Cerca per...");
		cercaPerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!gestoreVendite.isEmpty()) {
					
					SelezionaRicerca sr = new SelezionaRicerca(gestoreVendite, mainMenu, istanzaCorrente);
					sr.setVisible(true);
				}
				else 
					JOptionPane.showMessageDialog(mainMenu, "Non ci sono vendite da mostrare.", "Attenzione", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		cercaPerButton.setBounds(10, 10, 145, 30);
		add(cercaPerButton);
	}
	
	
	/**
	 * Metodo che crea il pulsante per ristampare tutta la lista completa di vendite, utile dopo una ricerca di vendite
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
					printListaVendite(new HashSet<Vendita<MerceVenduta>>());
					JOptionPane.showMessageDialog(mainMenu, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		visualizzaTuttoButton.setBounds(812, 10, 170, 30);
		add(visualizzaTuttoButton);
	}
	
	
	/**
	 * Metodo che stampa una lista di vendite
	 */
	public void printListaVendite(Set<Vendita<MerceVenduta>> vendite) {
		
		// rimuove tutto dal pannello
		panel.removeAll();
		
		// se la lista e' vuota, stampo una JLabel di avviso
		if (vendite.isEmpty()) {
			
			// ricreo lo scrollPane, altrimeti non stamparebbe la label di avviso dopo l'eliminazione dell'ultima vendita nella lista
			scrollPane.setColumnHeaderView(panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			
			/* label per avvisare che non ci sono vendite da mostrare;
			 * appare quando all'avvio del programma non ci sono label e
			 * dopo l'eliminazione dell'ultima vendita nella lista */
			JLabel avvisoListaVuota = new JLabel("Non ci sono vendite da mostrare.");
			GridBagConstraints gbc_avvisoListaVuota = new GridBagConstraints();
			gbc_avvisoListaVuota.insets = new Insets(5, 5, 5, 5);
			gbc_avvisoListaVuota.gridx = 0;
			gbc_avvisoListaVuota.gridy = 0;
			panel.add(avvisoListaVuota, gbc_avvisoListaVuota);
		}
		else {
			// stampa le intestazioni delle colonne per la lista di vendite
			printIntestazioniColonne();
			
			final int DIMENSIONE = vendite.size();
			
			// controllo necessario per mantenere un layout visivamente corretto quando si supera un certo numero di vendite
			if (DIMENSIONE >= SOGLIA_MASSIMA_LISTA_VENDITE)
				scrollPane.setViewportView(panel);
			else
				scrollPane.setColumnHeaderView(panel);
			
			// oggetti componenti della lista di vendite
	
			JLabel[] codLabel = new JLabel[DIMENSIONE]; 				// codice vendita
			JLabel[] matricolaImpLabel = new JLabel[DIMENSIONE];		// matricola impiegato
			JLabel[] dataVenditaLabel = new JLabel[DIMENSIONE];			// data vendita
			JLabel[] quantitaTotLabel = new JLabel[DIMENSIONE];			// quantita totale di bulloni venduti
			JLabel[] prezzoTotLabel = new JLabel[DIMENSIONE];			// prezzo totale dei bulloni venduti
			JButton[] visualButton = new JButton[DIMENSIONE];			// pulsante per visualizzare tutte le info sulla merce venduta
			JButton[] infoImpiegatoButton = new JButton[DIMENSIONE];	// pulsante per visualizzare tutte le info sull'impiegato che ha effettuato la vendita
			JButton[] deleteButton = new JButton[DIMENSIONE];			// pulsante per eliminare una vendita
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
				
				// stampo la data di vendita nella lista
				dataVenditaLabel[i] = new JLabel(vendita.getData().toLocalDate().toString());
				GridBagConstraints gbc_dataVenditaLabel = new GridBagConstraints();
				gbc_dataVenditaLabel.insets = new Insets(0, 0, 5 ,1);
				gbc_dataVenditaLabel.gridx = ++posizioneX;
				gbc_dataVenditaLabel.gridy = i+1;
				panel.add(dataVenditaLabel[i], gbc_dataVenditaLabel);
				
				// stampo la quantita' totale di merce venduta nella lista
				quantitaTotLabel[i] = new JLabel(((Integer)vendita.getQuantitaMerceTotale()).toString());
				GridBagConstraints gbc_quantitaTotLabel = new GridBagConstraints();
				gbc_quantitaTotLabel.insets = new Insets(0, 0, 5, 1);
				gbc_quantitaTotLabel.gridx = ++posizioneX;
				gbc_quantitaTotLabel.gridy = i+1;
				panel.add(quantitaTotLabel[i], gbc_quantitaTotLabel);
				
				// stampo il prezzo totale della merce venduta nella lista
				prezzoTotLabel[i] = new JLabel(((Float)(((Double)vendita.getPrezzoVenditaTotale()).floatValue())).toString());
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
				visualButton[i].addActionListener(new GestoreButton(gestoreVendite, gestoreBulloni, mainMenu, codLabel[i].getText(), istanzaCorrente));
				panel.add(visualButton[i], gbc_visualButton);
				
				// pulsate che permette di visualizzare tutte le info sull'impiegato che ha effettuato la vendita in un'apposita finestra
				infoImpiegatoButton[i] = new JButton("Impiegato");
				GridBagConstraints gbc_infoImpiegatoButton = new GridBagConstraints();
				gbc_infoImpiegatoButton.insets = new Insets(0, 0, 5, 5);
				gbc_infoImpiegatoButton.gridx = ++posizioneX;
				gbc_infoImpiegatoButton.gridy = i+1;
				infoImpiegatoButton[i].addActionListener(new GestoreButton(gestoreImpiegati, mainMenu, matricolaImpLabel[i].getText()));
				panel.add(infoImpiegatoButton[i], gbc_infoImpiegatoButton);
				
				// pulsante che permette di eliminare questa vendita dal set originale, dal database e dalla lista
				deleteButton[i] = new JButton("Elimina");
				deleteButton[i].setBackground(Color.red);
				deleteButton[i].setForeground(Color.white);
				GridBagConstraints gbc_deleteButton = new GridBagConstraints();
				gbc_deleteButton.insets = new Insets(0, 15, 5, 25);
				gbc_deleteButton.gridx = ++posizioneX;
				gbc_deleteButton.gridy = i+1;
				deleteButton[i].addActionListener(new GestoreButton(gestoreVendite, mainMenu, codLabel[i].getText(), istanzaCorrente));
				panel.add(deleteButton[i], gbc_deleteButton);
				
				i++;
			}
		}
	}

}
