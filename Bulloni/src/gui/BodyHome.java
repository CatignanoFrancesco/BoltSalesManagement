/**
 * @author Francolino Flavio Domenico
 * 
 *classe che implementa il corpo della schemata home della gui
 */

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BodyHome extends JPanel {

	GridBagConstraints gbc = new GridBagConstraints();// server per settare il layout degli elementi

	private static JButton btnDipendenti;
	private static JButton btnBulloni;
	private static JButton btnVendite;
	private static JButton btnExit;

	/**
	 * costruttore per inizializzaere il layout e aggiungere i diversi bottoni
	 * btnDipendenti,btnVendite,btnBulloni,btnExit;
	 */
	public BodyHome() {

		this.setLayout(new GridBagLayout());

		// aggiungo i diversi bottoni
		this.addButtons();
		//triggero i bottoni
		this.triggerButtons();

		this.revalidate();
		this.repaint();
	}

	/**
	 * metodo per aggiungere i diversi bottoni alla home
	 * btnDipendenti,btnVendite,btnBulloni,btnExit
	 * 
	 */
	private void addButtons() {

		// setto i paramentri principali del layout
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(50, 15, 50, 15);// distanza fra i diversi elementi del pannello
		gbc.weightx = 1;

		// aggiungo il bottone per visualizzare i dipendenti
		btnDipendenti = new JButton("vedi dipendenti");
		btnDipendenti.setFont(new Font("verdana", Font.BOLD, 20));
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(btnDipendenti, gbc);

		// aggiungo il bottone per visualizzare i bulloni
		btnBulloni = new JButton("vedi bulloni");
		btnBulloni.setFont(new Font("verdana", Font.BOLD, 20));
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add(btnBulloni, gbc);

		// aggiungo il bottone per visualizzare le vendite
		btnVendite = new JButton("vedi vendite");
		btnVendite.setFont(new Font("verdana", Font.BOLD, 20));
		gbc.gridx = 2;
		gbc.gridy = 1;
		this.add(btnVendite, gbc);

		// aggiungo il bottone exit
		btnExit = new JButton("exit");
		btnExit.setFont(new Font("verdana", Font.BOLD, 20));
		gbc.gridx = 1;
		gbc.gridy = 3;
		this.add(btnExit, gbc);
	}

	/**
	 * metodo usato per triggerare i bottoni della schemata home
	 */
	private void triggerButtons() {

		btnDipendenti.addActionListener(new ScrennManagerBtnClickListener());
		btnDipendenti.setActionCommand(ScrennManagerBtnClickListener.BTN_IMPIEGATI);

		btnBulloni.addActionListener(new ScrennManagerBtnClickListener());
		btnBulloni.setActionCommand(ScrennManagerBtnClickListener.BTN_BULLONI);

		btnVendite.addActionListener(new ScrennManagerBtnClickListener());
		btnVendite.setActionCommand(ScrennManagerBtnClickListener.BTN_VENDITE);

		btnExit.addActionListener(new ScrennManagerBtnClickListener());
		btnExit.setActionCommand(ScrennManagerBtnClickListener.BTN_EXIT);
	}

}
