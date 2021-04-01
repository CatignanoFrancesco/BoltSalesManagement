/**
 * 
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Francolino Flavio Domenico
 * 
 *         questa classe si occupa di triggerare i pulsanti della schemata home
 *         della gui
 *
 */
public class ScrennManagerBtnClickListener implements ActionListener {

	public final static String BTN_BACK = "back";
	public final static String BTN_IMPIEGATI = "vediImepiegati";
	public final static String BTN_BULLONI = "vediBulloni";
	public final static String BTN_VENDITE = "vediVendite";
	public final static String BTN_EXIT = "exit";

	@Override
	public void actionPerformed(ActionEvent e) {

		String eAction = e.getActionCommand();

		switch (eAction) {
		case BTN_BACK:

			ScreenManager.switchScreen(ScreenName.HOME, ScreenName.HOME.toString());
			break;

		case BTN_IMPIEGATI:

			ScreenManager.switchScreen(ScreenName.IMPIEGATI, ScreenName.IMPIEGATI.toString());
			break;

		case BTN_BULLONI:

			ScreenManager.switchScreen(ScreenName.BULLONI, ScreenName.BULLONI.toString());
			break;

		case BTN_VENDITE:

			ScreenManager.switchScreen(ScreenName.VENDITE, ScreenName.VENDITE.toString());
			break;

		default:

			System.exit(0);
			break;
		}

	}

}
