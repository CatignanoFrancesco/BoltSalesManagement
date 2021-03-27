/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Francolino Flavio Domenico
 * 
 *         questa classe implementa il pannello della mainWindows che gestisce
 *         le diverse schermate del software
 *
 */
public class ScreenManager extends JPanel {

	private static HeaderPanel header;
	private static JPanel body;// corpo delle schermata che cambiera dinamicamente asseconda delle bottone
								// cliccato

	private static JFrame parentWindow;// riferimento alla JFrame padre
										// che servirà per realizzare un effetto focus all'aprirsi di nuove finestre

	/**
	 * costruttore che inizalizza il layout del pannello, aggiunge gli elementi da
	 * visualizzare e inizializza il parentWindow
	 * 
	 * @param parentWindow il JFrame primario della gui
	 */
	public ScreenManager(JFrame parentContainer) {

		this.setLayout(new BorderLayout());
		parentWindow = parentContainer;
		this.addComponent();

		this.revalidate();
		this.repaint();

	}

	
	/**
	 * metodo per aggiunger i diversi body che cambieranno dinamicamente al click su un determinato bottone
	 */
	private void addComponent() {

		// aggiungo l'header
		header = new HeaderPanel();
		add(header, BorderLayout.NORTH);

		/*-----aggiungo il body che cambiera dinamicamente asseconda della schemata gui da visualizzare---*/
		body = new JPanel();
		body.setLayout(new CardLayout());
		
		
		// aggiungo il corpo che visualizza la home
		body.add(ScreenName.HOME.toString(), new BodyHome());
		// aggiungo il corpo che visualizza gli impiegati
		//body.add(ScreenName.HOME.toString(), new BodyImpiegati());
		// aggiungo il corpo che visualizza i bulloni
		//body.add(ScreenName.HOME.toString(), new BodyBulloni());
		// aggiungo il corpo che visualizza le vendite
		//body.add(ScreenName.HOME.toString(), new BodyVendite(parentWindow));
		
		this.add(body, BorderLayout.CENTER);
		/*--------------------------------------------------------------------------------------------------*/

	}

}
