/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.GestoreImpiegatiDb;
import gestori.gestorevendite.GestoreVendita;
import gestori.gestorevendite.exception.GestoreVenditaException;
import gestori.gestoribulloni.GestoreBulloni;
import gui.guibulloni.BodyBulloni;
import gui.guivendite.BodyVendite;

/**
 * @author Francolino Flavio Domenico
 * 
 *         questa classe implementa il pannello della mainWindows che gestisce
 *         le diverse schermate del software
 *
 */
public class ScreenManager extends JPanel {
	
	private static GestoreImpiegatiDb gi;
	private static GestoreBulloni gb;
	private static GestoreVendita gv;

	private static HeaderPanel header;
	private static JPanel body;// corpo delle schermata che cambiera dinamicamente asseconda delle bottone
								// cliccato

	private static JFrame parentWindow;// riferimento alla JFrame padre
										// che servir� per realizzare un effetto focus all'aprirsi di nuove finestre

	/**
	 * costruttore che inizalizza il layout del pannello, aggiunge gli elementi da
	 * visualizzare e inizializza il parentWindow
	 * 
	 * @param parentWindow il JFrame primario della gui
	 * @throws SQLException 
	 * @throws DatabaseSQLException 
	 */
	public ScreenManager(JFrame parentContainer) throws DatabaseSQLException, SQLException {
		
		this.istanziaGestori();

		this.setLayout(new BorderLayout());
		parentWindow = parentContainer;
		this.addComponent();

		this.revalidate();
		this.repaint();

	}

	/**
	 * metodo per aggiungere i diversi body che cambieranno dinamicamente al click
	 * su un determinato bottone
	 * @throws SQLException 
	 * @throws DatabaseSQLException 
	 */
	private void addComponent() throws DatabaseSQLException, SQLException {

		// aggiungo l'header
		header = new HeaderPanel();
		add(header, BorderLayout.NORTH);

		/*-----aggiungo il body che cambiera dinamicamente asseconda della schemata gui da visualizzare---*/
		body = new JPanel();
		body.setLayout(new CardLayout());

		// aggiungo il corpo che visualizza la home
		body.add(ScreenName.HOME.toString(), new BodyHome());
		// aggiungo il corpo che visualizza gli impiegati
		// body.add(ScreenName.HOME.toString(), new BodyImpiegati());
		// aggiungo il corpo che visualizza i bulloni
		body.add(ScreenName.BULLONI.toString(), new BodyBulloni(parentWindow, gb));
		// aggiungo il corpo che visualizza le vendite
		body.add(ScreenName.VENDITE.toString(), new BodyVendite(parentWindow, gv, gb, gi));

		this.add(body, BorderLayout.CENTER);
		/*--------------------------------------------------------------------------------------------------*/

	}

	/**
	 * questo metodo serve per far cambiare dinamicamente la schemata da
	 * visualizzare nella gui al verificarsi di un evento(click bottone)
	 * 
	 * @param nameScreen  nome della schemata da far visualizzare
	 * @param titleScreen titolo da far stampare nella schemata visualizzata
	 */
	public static void switchScreen(ScreenName nameScreen, String titleScreen) {

		JPanel parentCardLayout = (JPanel) body;// selezione il pannello padre che devo switchare
		CardLayout layout = (CardLayout) parentCardLayout.getLayout();// seleziono il layout del pannello padre

		if (nameScreen == ScreenName.HOME) {// devo visualizzare la home

			header.setVisibleBtnBack(false);// nascondo il bottone back

			header.setScreenTitleText("HOME");// setto il titolo della schemata su home

			layout.show(parentCardLayout, nameScreen.toString());// switcho il pannello per mostrare il pannello
																		// della home

		} else {

			header.setVisibleBtnBack(true);// mostro il bottone back

			header.setScreenTitleText(titleScreen);// setto il titolo della schemata con il titolo deciso

			layout.show(parentCardLayout, nameScreen.toString());// switcho il pannello per mostrare il pannello
																// richiesto
		}
	}
	
	/**
	*questo metodo instanzia semplicemente i diversi gestori che verranno passate ai diversi pannelli
	*affinche si possano visulizzare i dati
	**/
	private void istanziaGestori() {
		
		try {
			gi = new GestoreImpiegatiDb();
			gb = new GestoreBulloni();
			gv = new GestoreVendita(gb, gi);
		} catch (SQLException | DatabaseSQLException e) {
			
			JOptionPane.showMessageDialog(parentWindow, e.getMessage(), "exception", JOptionPane.ERROR_MESSAGE);
			
		} catch (GestoreVenditaException e) {
			
			JOptionPane.showMessageDialog(parentWindow, e.getMessage(), "exception", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		
	}

}
