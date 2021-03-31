package gui.guivendite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GestoreDeleteButton implements ActionListener {

	private JFrame mainMenu;
	private String codice;
	
	public GestoreDeleteButton(JFrame mainMenu, String codice) {
		this.mainMenu = mainMenu;
		this.codice = codice;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JOptionPane.showMessageDialog(mainMenu, codice);

	}

}
