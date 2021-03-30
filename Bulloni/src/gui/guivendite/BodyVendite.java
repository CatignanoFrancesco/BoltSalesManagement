/**
 * 
 */
package gui.guivendite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * @author GiannettaGerardo
 *
 */
public class BodyVendite extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// Costanti
	private static final int WIDTH_BODY = 750;
	private static final int HEIGHT_BODY = 652;
	private static final int X_BODY = 100;
	private static final int Y_BODY = 100;
	private static final int WIDTH_SCROLLPANE = 768;
	private static final int HEIGHT_SCROLLPANE = 507;
	private static final int X_SCROLLPANE = 10;
	private static final int Y_SCROLLPANE = 50;
	
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
	
	
	/**
	 * Costruttore del pannello principale delle vendite
	 * 
	 * @param mainMenu finestra principale nella quale questo pannello è situato
	 */
	public BodyVendite(JFrame mainMenu) {
		this.mainMenu = mainMenu;
		inizializza();
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

}
