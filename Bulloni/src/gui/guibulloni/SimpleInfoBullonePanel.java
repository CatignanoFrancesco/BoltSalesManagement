package gui.guibulloni;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoribulloni.ModificaBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;

/**
 * Pannello che corrisponde ad una singola riga nella lista di bulloni.
 * In questa classe sono presenti tutte le etichette per riassumere in maniera generale le informazioni su uno specifico bullone (es. codice, tipo e prezzo) e i bottoni per la manipolazione (informazioni aggiuntive, modifica ed eliminazione).
 * L'utilizzo di un singolo pannello per ogni bullone permette di creare una migliore associazione tra il singolo bullone e le varie etichette e pulsanti.
 * 
 * @author Catignano Francesco
 */
class SimpleInfoBullonePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JFrame mainFrame;	// Finestra principale
	private BodyBulloni mainPanel;	// Pannello principale da cui deriva
	private int codBullone;	// Il codice del bullone a cui si fa riferimento
	private String[] infoBullone;	// Le informazioni del bullone
	private ModificaBulloni modificaBulloni;	// Interfaccia di modifica dei bulloni
	
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
	 * Il layout utilizzato e' il GridBagLayout.
	 * @param mainFrame La finestra principale da cui deriva.
	 * @param mainPanel Il pannello principale da cui deriva.
	 * @param codBullone Il codice del bullone a cui il pannello fa riferimento. La sua funzione principale e' quella di permettere l'utilizzo dei metodi che usano i codici dei bulloni.
	 * @param infoBullone L'array di stringhe contenente le informazioni del bullone.
	 * @param modificaBulloni L'interfaccia di modifica per i bulloni
	 */
	SimpleInfoBullonePanel(JFrame mainFrame, BodyBulloni mainPanel, int codBullone, String[] infoBullone, ModificaBulloni modificaBulloni) {
		this.mainFrame = mainFrame;
		this.mainPanel = mainPanel;
		this.codBullone = codBullone;
		this.infoBullone = infoBullone;
		this.modificaBulloni = modificaBulloni;
		
		/*
		 * Impostazioni layout pannello
		 */
		GridBagConstraints gbcForSimpleInfoBullonePanel = new GridBagConstraints();
		gbcForSimpleInfoBullonePanel.insets = new Insets(5, 5, 10, 5);	// su, sx, giu', dx
		gbcForSimpleInfoBullonePanel.fill = GridBagConstraints.HORIZONTAL;
		gbcForSimpleInfoBullonePanel.gridx = 0;
		gbcForSimpleInfoBullonePanel.gridy = 0;
		
		GridBagLayout gblForSimpleInfoBullonePanel = new GridBagLayout();
		gblForSimpleInfoBullonePanel.columnWidths = new int[] {40, 30, 30, 30, 40, 30, 30, 30, 30, 30, 30};
		gblForSimpleInfoBullonePanel.rowHeights = new int[] {0};
		gblForSimpleInfoBullonePanel.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gblForSimpleInfoBullonePanel.rowWeights = new double[] {0.0};
		
		this.setLayout(gblForSimpleInfoBullonePanel);
		this.creaInfoBulloni();
		this.creaBottoni();
	}
	
	/**
	 * Trigger dei bottoni
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Bottone per mostrare le informazioni
		if(e.getSource()==this.btnInformazioni) {
			InfoBulloneFrame infoBulloneFrame = new InfoBulloneFrame(this.infoBullone);
			infoBulloneFrame.setVisible(true);
		}
		
		// Bottone per la modifica
		if(e.getSource()==this.btnModifica) {
			ModificaBulloneFrame modificaBulloneFrame = new ModificaBulloneFrame(this.mainPanel, this.modificaBulloni, this.codBullone);
			modificaBulloneFrame.setVisible(true);
		}
		
		// Bottone per l'eliminazione
		if(e.getSource()==this.btnElimina) {
			/*
			 * 0 = si
			 * 1 = no
			 * 2 = annulla
			 * -1 = finestra chiusa
			 */
			int risposta = JOptionPane.showConfirmDialog(this.mainFrame, "Vuoi davvero eliminare questo bullone?");
			if(risposta==0) {
				try {
					this.modificaBulloni.rimuoviBulloneByCodice(this.codBullone);
				}
				catch(GestoreBulloniException ex) {
					System.err.println(ex.getMessage());	// In questo caso l'errore non va comunicato all'utente poiche' il bullone che si sta cercando di eliminare sicuramente esiste.
				}
				catch(DatabaseSQLException | SQLException ex) {
					// Errore mostrato all'utente
					JOptionPane.showConfirmDialog(this.mainFrame, ex.getMessage(), "Errore di connessione!", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				
				this.mainPanel.refresh();	// Refresh del pannello
			}
		}
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
	 * Il layout utilizzato e': GridBagLayout.
	 */
	private void creaInfoBulloni() {
		/*
		 * Codice
		 */
		this.lblCodice.setText(this.infoBullone[1]);
		GridBagConstraints gbcForLblCodice = new GridBagConstraints();
		gbcForLblCodice.gridx = 0;
		gbcForLblCodice.gridy = 0;
		gbcForLblCodice.insets = new Insets(5, 0, 5, 20);
		this.add(this.lblCodice, gbcForLblCodice);
		
		/*
		 * Tipo di bullone
		 */
		this.lblTipoBullone.setText(this.infoBullone[0]);
		GridBagConstraints gbcForLblTipoBullone = new GridBagConstraints();
		gbcForLblTipoBullone.gridx = 2;
		gbcForLblTipoBullone.gridy = 0;
		gbcForLblTipoBullone.insets = new Insets(5, 0, 5, 20);
		this.add(this.lblTipoBullone, gbcForLblTipoBullone);
		
		/*
		 * Prezzo
		 */
		this.lblPrezzo.setText(this.infoBullone[5] + " \u20ac");
		GridBagConstraints gbcForlblPrezzo = new GridBagConstraints();
		gbcForlblPrezzo.gridx = 4;
		gbcForlblPrezzo.gridy = 0;
		gbcForlblPrezzo.insets = new Insets(5, 0, 5, 320);
		this.add(this.lblPrezzo, gbcForlblPrezzo);
	}
	
	
	/**
	 * Imposta il layout per i bottoni che compongono il pannello.
	 * In particolare i bottoni serviranno a:
	 * - mostrare maggiori informazioni sui bulloni;
	 * - permettere la modifica di un bullone;
	 * - eliminare il bullone.
	 * Il layout utilizzato e': GridBagLayout.
	 */
	private void creaBottoni() {
		/*
		 * Bottone "Informazioni"
		 */
		this.btnInformazioni.setText("Informazioni");
		GridBagConstraints gbcForBtnInformazioni = new GridBagConstraints();
		gbcForBtnInformazioni.gridx = 8;
		gbcForBtnInformazioni.gridy = 0;
		gbcForBtnInformazioni.insets = new Insets(5, 0, 5, 5);
		this.add(this.btnInformazioni, gbcForBtnInformazioni);
		this.btnInformazioni.addActionListener(this);
		
		/*
		 * Bottone "Modifica"
		 */
		this.btnModifica.setText("Modifica");
		GridBagConstraints gbcForBtnModifica = new GridBagConstraints();
		gbcForBtnModifica.gridx = 9;
		gbcForBtnModifica.gridy = 0;
		gbcForBtnModifica.insets = new Insets(5, 0, 5, 5);
		this.add(this.btnModifica, gbcForBtnModifica);
		this.btnModifica.addActionListener(this);
		
		/*
		 * Bottone "Elimina"
		 */
		this.btnElimina.setText("Elimina");
		this.btnElimina.setBackground(Color.RED);
		this.btnElimina.setForeground(Color.WHITE);
		GridBagConstraints gbcForBtnElimina = new GridBagConstraints();
		gbcForBtnElimina.gridx = 10;
		gbcForBtnElimina.gridy = 0;
		gbcForBtnElimina.insets = new Insets(5, 0, 5, 5);
		this.add(this.btnElimina, gbcForBtnElimina);
		this.btnElimina.addActionListener(this);
	}
	
}
