/**
 * 
 */
package gui.guiImpiegati;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import persona.Impiegato;

/**
 * @author Francolino Flavio Domenico
 * 
 *         classe per implementare il pannello per la visualizzazione di un
 *         impiegato
 *
 */
class PannelloImpiegato extends JPanel {

	private static final long serialVersionUID = 1L;

	Dimension dim = new Dimension(90, 25);// dimensione predefinita per le componenti del pannello
											// usata per migliorare l'allineamento

	// etichette per visualizzare le info di base di un impiegato
	private JLabel lblCodice;
	private JLabel lblNome;
	private JLabel lblCognome;

	// bottoni per effettuare operazioni relative ad un impiegato
	private static JButton btnDettagli;
	private static JButton btnLicenzia;
	private static JButton btnPromuovi;

	private Impiegato impiegato;// impiegato da visualizzare

	private BodyImpiegatoBtnListener btnListener;// ascoltatore click bottoni
	
	private static BodyImpiegati parentContainer;//jpanel nel quale viene visualizzare il determinato pannello

	/**
	 * costruttore per instaziare il pannello d'intestazione della lista d'impiegati
	 */
	public PannelloImpiegato() {

		this.setLayout(new GridBagLayout());

		this.aggiungiElementiIntestazione();

		this.revalidate();
		this.repaint();
	}

	/**
	 * costruttore per istanziare un pannello che visualizza un dato impiegato
	 * 
	 * @param impiegato l'impiegato da visualizzare
	 */
	public PannelloImpiegato(Impiegato impiegato, BodyImpiegati parentPanel) {

		this.impiegato = impiegato;
		
		PannelloImpiegato.parentContainer = parentPanel;

		this.setLayout(new GridBagLayout());

		this.aggiungiElementiImpiegato();

		this.triggerButtons();

		this.revalidate();
		this.repaint();

	}

	/**
	 * metodo per aggiungere le componenti grafiche all'intestazione
	 * 
	 */
	private void aggiungiElementiIntestazione() {

		// setto le proprieta del layout
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 5, 0, 5);

		// aggiungo l'etichetta per visualizzare l'intestazione del campo codice 
		// dell'impiegato con dimensioni predefinite per un migliore allineamento
		this.lblCodice = new JLabel("codice");
		this.lblCodice.setForeground(Color.red);
		this.lblCodice.setFont(new Font("verdana", Font.BOLD, 15));
		this.lblCodice.setMaximumSize(this.dim);
		this.lblCodice.setMinimumSize(this.dim);
		this.lblCodice.setPreferredSize(this.dim);
		gbc.gridx = 0;
		this.add(this.lblCodice, gbc);

		// aggiungo l'etichetta per visualizzare l'intestazione del campo nome 
		// dell impiegato con dimensioni predefinite per un migliore allineamento
		this.lblNome = new JLabel("nome");
		this.lblNome.setForeground(Color.red);
		this.lblNome.setFont(new Font("verdana", Font.BOLD, 15));
		this.lblNome.setMaximumSize(this.dim);
		this.lblNome.setMinimumSize(this.dim);
		this.lblNome.setPreferredSize(this.dim);
		gbc.gridx = 1;
		this.add(this.lblNome, gbc);

		// aggiungo l'etichetta per visualizzare l'intestazione del campo cognome 
		// dell'impiegato con dimensioni predefinite per un migliore allineamento
		this.lblCognome = new JLabel("cognome");
		this.lblCognome.setFont(new Font("verdana", Font.BOLD, 15));
		this.lblCognome.setForeground(Color.red);
		this.lblCognome.setMaximumSize(this.dim);
		this.lblCognome.setMinimumSize(this.dim);
		this.lblCognome.setPreferredSize(this.dim);
		gbc.gridx = 2;
		this.add(this.lblCognome, gbc);

		// aggiungo una compoente di offset per un migliore allineamento
		Dimension offsetDimension = new Dimension((int) (this.dim.getWidth() * 3), (int) (this.dim.getHeight() * 3));// per 3 poiche l'intestazione non presenta
																													// i bottoni quindi devo accupare lo stesso
																													// spazio che occupano i 3 bottoni di
																													// un pannello che visualizza un impiegato
		JPanel offsetPanel = new JPanel();
		offsetPanel.setMinimumSize(offsetDimension);
		offsetPanel.setMaximumSize(offsetDimension);
		offsetPanel.setPreferredSize(offsetDimension);
		gbc.gridx = 3;
		gbc.gridwidth = 3;
		gbc.weightx = 3;
		this.add(offsetPanel, gbc);
	}

	/**
	 * metodo per aggiungere le componenti grafiche per visualizzare un dato
	 * impiegato
	 */
	private void aggiungiElementiImpiegato() {

		// setto le proprieta del layout
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 0, 5);

		// aggiungo l'etichetta per visualizzare il codice dell impiegato con dimensioni
		// predefinite per un migliore allineamento
		this.lblCodice = new JLabel(((Integer) impiegato.getID()).toString());
		this.lblCodice.setMaximumSize(this.dim);
		this.lblCodice.setMinimumSize(this.dim);
		this.lblCodice.setPreferredSize(this.dim);
		gbc.gridx = 0;
		this.add(this.lblCodice, gbc);

		// aggiungo l'etichetta per visualizzare il nome dell impiegato con dimensioni
		// predefinite per un migliore allineamento
		this.lblNome = new JLabel(impiegato.getNome());
		this.lblNome.setMaximumSize(this.dim);
		this.lblNome.setMinimumSize(this.dim);
		this.lblNome.setPreferredSize(this.dim);
		gbc.gridx = 1;
		this.add(this.lblNome, gbc);

		// aggiungo l'etichetta per visualizzare il cognome dell impiegato con
		// dimensioni predefinite per un migliore allineamento
		this.lblCognome = new JLabel(impiegato.getCognome());
		this.lblCognome.setMaximumSize(this.dim);
		this.lblCognome.setMinimumSize(this.dim);
		this.lblCognome.setPreferredSize(this.dim);
		gbc.gridx = 2;
		this.add(this.lblCognome, gbc);

		// aggiungo il bottone per vedere i dettagli dell'impiegato
		btnDettagli = new JButton("dettagli");
		gbc.gridx = 3;
		this.add(btnDettagli, gbc);

		// aggiungo il bottone per promuovere l'impiegato
		btnPromuovi = new JButton("promuovi");
		gbc.gridx = 4;
		this.add(btnPromuovi, gbc);

		// aggiungo il bottone per vedere i dettagli dell'impiegato
		btnLicenzia = new JButton("licenzia");
		btnLicenzia.setBackground(Color.red);
		gbc.gridx = 5;
		this.add(btnLicenzia, gbc);

	}

	/**
	 * metodo per ritornare l'impiegato visualizzato nel pannello
	 * 
	 * @return l'impiegato visualizzato
	 */
	public Impiegato getImpiegato() {

		return this.impiegato;
	}

	/**
	 * questo metodo permette di triggerare i pulsanti presenti sul pannello
	 */
	private void triggerButtons() {

		this.btnListener = new BodyImpiegatoBtnListener(this, parentContainer);
		
		

		// triggero il bottone per visualizzare i dettagli dell'impiegato
		btnDettagli.addActionListener(this.btnListener);
		btnDettagli.setActionCommand(BodyImpiegatoBtnListener.BTN_DETTAGLI);

		// triggero il bottone per promuovere l'impiegato
		btnPromuovi.addActionListener(this.btnListener);
		btnPromuovi.setActionCommand(BodyImpiegatoBtnListener.BTN_PROMUOVI);

		// triggero il bottone per licenziare l'impiegato
		btnLicenzia.addActionListener(this.btnListener);
		btnLicenzia.setActionCommand(BodyImpiegatoBtnListener.BTN_LICENZIA);

	}

}
