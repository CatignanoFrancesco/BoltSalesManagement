/**
 * @author Francolino Flavio Domenico
 * 
 *classe che implementa il corpo della schemata home della gui
 */

package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BodyHome extends JPanel {
	
	GridBagConstraints gbc = new GridBagConstraints();//server per settare il layout degli elementi
	
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
		this.setBackground(Color.green);
		
		//aggiungo i diversi bottoni
		this.addButtons();
		
		this.revalidate();
		this.repaint();
	}
	
	
	/**
	 * metodo per aggiungere i diversi bottoni 
	 * btnDipendenti,btnVendite,btnBulloni,btnExit
	 * 
	 */
	private void addButtons() {

		//setto i paramentri principali del layout
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 5, 10, 5);//distanza fra i diversi elementi del pannello
		
		//aggiungo il bottone per visualizzare i dipendenti
		btnDipendenti = new JButton("vedi dipendenti");
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(btnDipendenti, gbc);
		
		//aggiungo il bottone per visualizzare i bulloni
		btnBulloni = new JButton("vedi bulloni");
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(btnBulloni, gbc);
		
		//aggiungo il bottone per visualizzare le vendite
		btnVendite = new JButton("vedi vendite");
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.add(btnVendite, gbc);
		
		//aggiungo il bottone exit
		btnExit = new JButton("exit");
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.add(btnExit, gbc);
	}

}
