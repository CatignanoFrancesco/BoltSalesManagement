package gui.guibulloni;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gestori.gestoribulloni.VisualizzaBulloni;

/**
 * Pannello che corrisponde ad una singola riga nella lista di bulloni.
 * In questa classe sono presenti tutte le etichette per riassumere in maniera generale le informazioni su uno specifico bullone (es. codice, tipo e prezzo)
 * e i bottoni per la manipolazione (informazioni aggiuntive, modifica ed eliminazione). L'utilizzo di un singolo pannello per ogni bullone permette di creare
 * una migliore associazione tra il singolo bullone e le varie etichette e pulsanti.
 * 
 * @author Catignano Francesco
 */
class SimpleInfoBullonePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JFrame mainFrame;	// Finestra principale
	private BodyBulloni mainPanel;	// Pannello principale da cui deriva
	private String[] infoBullone;	// Le informazioni del bullone
	private VisualizzaBulloni visualizzaBulloni;	// Interfaccia di visualizzazione dei bulloni
	
	/*
	 * Elementi visibili nel pannello
	 */
	JLabel lblCodice = new JLabel();
	JLabel lblTipoBullone = new JLabel();
	JLabel lblPrezzo = new JLabel();
	JButton btnInformazioni = new JButton();
	JButton btnModifica = new JButton();
	JButton btnElimina = new JButton();
	
	
	/*
	 * -------------
	 *  COSTRUTTORE
	 * -------------
	 */
	
	/**
	 * Costruisce il pannello contenente le informazioni principali di un bullone, insieme a tutti i bottoni per l'interazione con essi.
	 * Un singolo pannello costituisce una riga della lista presente nella classe BodyPanel.
	 * Le informazioni del bullone sono prese da un'interfaccia software che contiene tutti i metodi per visualizzarle.
	 * @param mainFrame La finestra principale da cui deriva.
	 * @param mainPanel Il pannello principale da cui deriva.
	 * @param infoBullone L'array di stringhe contenente le informazioni del bullone.
	 * @param visualizzaBulloni L'interfaccia di visualizzazione per i bulloni
	 */
	SimpleInfoBullonePanel(JFrame mainFrame, BodyBulloni mainPanel, String[] infoBullone, VisualizzaBulloni visualizzaBulloni) {
		this.mainFrame = mainFrame;
		this.mainPanel = mainPanel;
		this.infoBullone = infoBullone;
		this.visualizzaBulloni = visualizzaBulloni;
		
		/*
		 * Impostazioni layout pannello
		 */
		GridBagConstraints gbcForSimpleInfoBullonePanel = new GridBagConstraints();
		gbcForSimpleInfoBullonePanel.insets = new Insets(5, 5, 10, 5);	// su, sx, giu', dx
		gbcForSimpleInfoBullonePanel.fill = GridBagConstraints.HORIZONTAL;
		gbcForSimpleInfoBullonePanel.gridx = 0;
		gbcForSimpleInfoBullonePanel.gridy = 0;
		
		GridBagLayout gblForSimpleInfoBullonePanel = new GridBagLayout();
		gblForSimpleInfoBullonePanel.columnWidths = new int[] {0, 20, 0, 20, 0, 200, 0, 0, 0, 0};
		gblForSimpleInfoBullonePanel.rowHeights = new int[] {0};
		gblForSimpleInfoBullonePanel.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gblForSimpleInfoBullonePanel.rowWeights = new double[] {0.0};
		
		this.setLayout(gblForSimpleInfoBullonePanel);
		this.creaInfoBulloni();
		this.creaBottoni();
	}
	
	
	/*
	 * ----------------
	 *  METODI PRIVATI
	 * ----------------
	 */
	/**
	 * Imposta il layout per le componenti che contengono le informazioni sul bullone.
	 * In particolare mostra:
	 * - il codice del bullone;
	 * - il tipo di bullone (grano, ecc.);
	 * - il prezzo.
	 */
	private void creaInfoBulloni() {
		/*
		 * Codice
		 */
		this.lblCodice.setText(this.infoBullone[1]);
		GridBagConstraints gbcForLblCodice = new GridBagConstraints();
		gbcForLblCodice.gridx = 0;
		gbcForLblCodice.gridy = 0;
		gbcForLblCodice.anchor = GridBagConstraints.LINE_START;
		gbcForLblCodice.insets = new Insets(5, 5, 5, 20);
		this.add(this.lblCodice, gbcForLblCodice);
		
		/*
		 * Tipo di bullone
		 */
		this.lblTipoBullone.setText(this.infoBullone[0]);
		GridBagConstraints gbcForLblTipoBullone = new GridBagConstraints();
		gbcForLblTipoBullone.gridx = 2;
		gbcForLblTipoBullone.gridy = 0;
		gbcForLblTipoBullone.anchor = GridBagConstraints.LINE_START;
		gbcForLblTipoBullone.insets = new Insets(5, 5, 5, 20);
		this.add(this.lblTipoBullone, gbcForLblTipoBullone);
		
		/*
		 * Prezzo
		 */
		this.lblPrezzo.setText(this.infoBullone[5]);
		GridBagConstraints gbcForlblPrezzo = new GridBagConstraints();
		gbcForlblPrezzo.gridx = 4;
		gbcForlblPrezzo.gridy = 0;
		gbcForlblPrezzo.anchor = GridBagConstraints.LINE_START;
		gbcForlblPrezzo.insets = new Insets(5, 5, 5, 20);
		this.add(this.lblPrezzo, gbcForlblPrezzo);
	}
	
	
	/**
	 * Imposta il layout per i bottoni che compongono il pannello.
	 * In particolare i bottoni serviranno a:
	 * - mostrare maggiori informazioni sui bulloni;
	 * - permettere la modifica di un bullone;
	 * - eliminare il bullone.
	 */
	private void creaBottoni() {
		/*
		 * Bottone "Informazioni"
		 */
		this.btnInformazioni.setText("Informazioni");
		GridBagConstraints gbcForBtnInformazioni = new GridBagConstraints();
		gbcForBtnInformazioni.gridx = 7;
		gbcForBtnInformazioni.gridy = 0;
		gbcForBtnInformazioni.anchor = GridBagConstraints.LINE_START;
		gbcForBtnInformazioni.insets = new Insets(5, 5, 5, 5);
		this.add(this.btnInformazioni, gbcForBtnInformazioni);
		// Aggiungere action listener
		
		/*
		 * Bottone "Modifica"
		 */
		this.btnModifica.setText("Modifica");
		GridBagConstraints gbcForBtnModifica = new GridBagConstraints();
		gbcForBtnModifica.gridx = 8;
		gbcForBtnModifica.gridy = 0;
		gbcForBtnModifica.anchor = GridBagConstraints.LINE_START;
		gbcForBtnModifica.insets = new Insets(5, 5, 5, 5);
		this.add(this.btnModifica, gbcForBtnModifica);
		// Aggiungere action listener
		
		/*
		 * Bottone "Elimina"
		 */
		this.btnElimina.setText("Elimina");
		this.btnElimina.setBackground(Color.RED);
		this.btnElimina.setForeground(Color.WHITE);
		GridBagConstraints gbcForBtnElimina = new GridBagConstraints();
		gbcForBtnElimina.gridx = 9;
		gbcForBtnElimina.gridy = 0;
		gbcForBtnElimina.anchor = GridBagConstraints.LINE_START;
		gbcForBtnElimina.insets = new Insets(5, 5, 5, 5);
		this.add(this.btnElimina, gbcForBtnElimina);
		// Aggiungere action listener
	}
}
