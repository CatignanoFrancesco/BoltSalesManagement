/**
 * @author Francolino Flavio Domenico
 * 
 * questa classe implementa il JPanel che funge da header della gui del software
 */

package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HeaderPanel extends JPanel {
	
	private static JLabel lblScreenTitle;
	private static JButton btnBack;
	
	
	/**
	 * costruttore che inizializza il layout dell'header delle gui e aggiunge gli elemente in esso presente
	 * 
	 */
	public HeaderPanel() {
		
		this.setLayout(new GridBagLayout());
		this.addElement();
		
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * questo metodo aggiunge all'header i singolo elementi btnBack e lblScreenTitile 
	 * settando loro le posizioni
	 */
	private void addElement() {
		
		//setto le proprieta base del layout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		//aggiungo il bottone back
		btnBack = new JButton("back");
		btnBack.setVisible(false);//si rendera visibile dinamicamente quando vengono visualizzate apposite schemate
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.weightx = 0;
		this.add(btnBack,gbc);
		
		//aggiungo la label del titolo di ogni schemata
		lblScreenTitle = new JLabel("HOME");
		gbc.gridx = 1;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		this.add(lblScreenTitle,gbc);
		
		//aggiungo una componente per migliorare il layout
		gbc.gridx = 2;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		this.add(Box.createHorizontalBox(),gbc);
	}
	
	
	/**
	 * 
	 * questo metodo serve per dinacimizzare la visualiazzazione del bottone di back
	 * asseconda delle schemata che viene visualizzata dal software
	 * @param visible la visibilita del bottone
	 */
	public void setVisibleBtnBack(boolean visible) {
		
		this.btnBack.setVisible(visible);
	}
	
	/**
	 * metodo per settare dinamicamente il titolo dell'header asseconda della schemata visualizzata dal software
	 * 
	 * @param screenTitleText ill titolo ce si vuol fare apparire sulla schemata
	 */
	public void setScreenTitleText(String screenTitleText) {
		
		this.lblScreenTitle.setText(screenTitleText);
	}

}