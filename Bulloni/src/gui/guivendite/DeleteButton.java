package gui.guivendite;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author GiannettaGerardo
 *
 */
public class DeleteButton extends JButton {

	private int indice;
	
	public DeleteButton(int indice) {
		this.indice = indice;
		setBackground(Color.red);
		setForeground(Color.white);
	}


}
