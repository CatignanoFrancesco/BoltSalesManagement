/**
 * 
 */
package gui.guiImpiegati;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.ScreenManager;
import persona.Impiegato;
import persona.ImpiegatoBulloni;

/**
 * @author Francolino Flavio Domenico
 * 
 *         questa classe implementa la visualizzazione della lista d'impiegati
 *         assunti disponibili
 *
 */
public class BodyImpiegati extends JPanel {

	private static PannelloImpiegato intestazioneLista;
	private static JPanel listaImpiegati;// pannello per la visualizzazione degli impiegati
	private static JScrollPane scroolPaneListaImpiegati;
	
	private static JLabel lblListaVuota;

	private static JButton btnAggiungi;
	private static BodyImpiegatoBtnListener btnListener;

	public BodyImpiegati() {

		this.setLayout(new GridBagLayout());

		this.aggiungiElementi();

		this.triggerButton();

		this.revalidate();
		this.repaint();
	}

	/**
	 * questo metodo aggiunge la componenenti grafiche utili alla realizzazione
	 * della schemata che visualizza gli impiegati assunti
	 */
	private void aggiungiElementi() {
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		
		if (ScreenManager.getGi().getSetImpiegatiAssunti().isEmpty()) {// non ci sono impiegati da visualizzare

			lblListaVuota = new JLabel("non sono presenti Impiegati assunti");
			lblListaVuota.setFont(new Font("verdana", Font.BOLD, 25));
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridheight = 2;
			gbc.fill = GridBagConstraints.BOTH;
			this.add(lblListaVuota, gbc);
			
		} else {

			this.aggiungiLista();
		}
		
		//aggiungo il bottone per l'aggiunta di un nuovo impiegato
		btnAggiungi = new JButton("+ aggiungi");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets.bottom = 3;
		gbc.insets.top = 3;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.PAGE_END;
		this.add(btnAggiungi, gbc);

	}
	
	void aggiungiLista() {
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		// aggiungo l'intestazione della lista
		intestazioneLista = new PannelloImpiegato();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(intestazioneLista, gbc);

		// aggiungo i diversi pannelli che visualizzano un impiegato
		listaImpiegati = new JPanel();
		listaImpiegati.setLayout(new BoxLayout(listaImpiegati, BoxLayout.PAGE_AXIS));
		this.riempiLista();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 2;
		gbc.weightx = 2;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		scroolPaneListaImpiegati = new JScrollPane(listaImpiegati);
		this.add(scroolPaneListaImpiegati, gbc);
	}
	
	/**
	 * questo metodo si occupa di riempire la schemata con gli impiegati assunti da visualizzare
	 */
	private void riempiLista() {
		
		
		Set<ImpiegatoBulloni> setI = new HashSet<ImpiegatoBulloni>();
		
		setI = ScreenManager.getGi().getSetImpiegatiAssunti();
		
		for(Impiegato i : setI) {
			
			this.aggiungiPannelloImpiegato(new PannelloImpiegato(i, this));
		
		}
		
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * questo metodo si occuopa di rimuovere un determinato pannello dalla schemata
	 * che visualizzata i diversi impiegati assunti
	 * 
	 * @param p il pannello da rimuovere
	 */
	void rimuoviPannelloImpiegato(PannelloImpiegato p) {
		
		listaImpiegati.remove(p);	
		
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * questo metodo si occuopa di aggiunger un determinato pannello nella schemata
	 * che visualizzata i diversi impiegati assunti
	 * 
	 * @param p il pannello da aggiungere
	 */
	void aggiungiPannelloImpiegato(PannelloImpiegato p) {
		
		listaImpiegati.add(p);
		
		this.revalidate();
		this.repaint();
	}
		
	/**
	 * questo metodo si occupa di triggerare i bottoni presenti nella schermata
	 */
	private void triggerButton() {
		
		btnListener = new BodyImpiegatoBtnListener(null, this);
		
		btnAggiungi.addActionListener(btnListener);
		btnAggiungi.setActionCommand(BodyImpiegatoBtnListener.BTN_AGGIUNGI);
		
		
	}

}
