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

import databaseSQL.DatabaseSQL;
import databaseSQL.exception.DatabaseSQLException;
import gestori.gestoreImpiegati.ContainerImpiegato;
import gestori.gestoreImpiegati.GestoreImpiegatiDb;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gestori.gestorevendite.ContainerVendite;
import gestori.gestorevendite.GestoreVendita;
import gestori.gestorevendite.exception.GestoreVenditaException;
import gestori.gestoribulloni.ContainerBulloni;
import gestori.gestoribulloni.GestoreBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;
import gui.guiImpiegati.BodyImpiegati;
import gui.guibulloni.BodyBulloni;
import gui.guivendite.BodyVendite;
import vendita.exception.VenditaException;

/**
 * @author Francolino Flavio Domenico
 * 
 *         questa classe implementa il pannello della mainWindows che gestisce
 *         le diverse schermate del software
 *
 */
public class ScreenManager extends JPanel {
	
	private static ContainerImpiegato gi;
	private static ContainerBulloni gb;
	private static ContainerVendite gv;

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
	public ScreenManager(JFrame parentContainer){
		
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
		body.add(ScreenName.IMPIEGATI.toString(), new BodyImpiegati());
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
	 * questo metodo instanzia semplicemente i diversi gestori che verranno passate
	 * ai diversi pannelli affinche si possano visulizzare i dati
	 **/
	private void istanziaGestori() {

		String passwordDB = JOptionPane.showInputDialog(this.parentWindow, "inserire la password del dbms", "password");// faccio inserire la password
																														// per connettersi al db

		if (passwordDB == null) {// l'utente ha premuto la x

			JOptionPane.showMessageDialog(parentWindow, "software concluso", "software concluso",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);

		} else {

			DatabaseSQL.setPassword(passwordDB);// setto la password

			//istanzio i gestori
			try {

				gi = new GestoreImpiegatiDb();
				gb = new GestoreBulloni();
				gv = new GestoreVendita(gb, gi);

			} catch (DatabaseSQLException | SQLException e) {// problemi di connessione

				JOptionPane.showMessageDialog(parentWindow, e.getMessage() + "\npassword errata o server irraggiungibile", "Exception", JOptionPane.ERROR_MESSAGE);
				this.istanziaGestori();// faccio reinserire la password

			} catch (GestoreVenditaException | VenditaException e) {

				JOptionPane.showMessageDialog(parentWindow, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);

			} catch (ExceptionGestoreImpiegato e) {

				JOptionPane.showMessageDialog(parentWindow, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			} catch (GestoreBulloniException e) {

				JOptionPane.showMessageDialog(parentWindow, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);

			}
		}

	}
	
	/**
	 * metodo da utilizare per ottenere il jframe principale affinche ulteriori finestre si possono aprire
	 * centralmente ad essa o effettuare operazione che richiedono sapere la finestra principale
	 * @return
	 */
	public static JFrame getParentWindow() {
		
		return parentWindow;
	}

	/**
	 * questo metodo ritorna il gestore impiegato locale
	 * 
	 * @return il gestore impiegato locale
	 */
	public static ContainerImpiegato getGi() {
		return gi;
	}

	/**
	 * questo metodo ritorna il gestore bulloni locale
	 * 
	 * @return il gestore bulloni locale
	 */
	public static ContainerBulloni getGb() {
		return gb;
	}

	/**
	 * questo metodo ritorna il gestore vendite locale
	 * 
	 * @return il gestore vendite locale
	 */
	public static ContainerVendite getGv() {
		return gv;
	}

	
	

}
