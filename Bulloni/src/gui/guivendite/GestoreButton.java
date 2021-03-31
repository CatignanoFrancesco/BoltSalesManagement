package gui.guivendite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import gestori.gestorevendite.ContainerVendite;

/**
 * @author GiannettaGerardo
 *
 * Classe che gestisce tutti i pulsanti delle liste che le interfacce grafiche mostrano
 */
public class GestoreButton implements ActionListener {

	/** la finestra principale che dovrà essere bloccata quando una nuova finestra si apre */
	private JFrame mainMenu;
	
	/** codice della tupla nella quale si clicca il pulsante; è fondamentale per creare un collegamento
	 * tra il pulsante cliccato e il resto dei parametri mostrati sulla stessa riga */
	private String codice;
	
	/** il gestore delle vendite con interfaccia completa di tutti i metodi */
	private ContainerVendite gestoreVendite;
	
	
	/**
	 * Costruttore della classe GestoreButton
	 * 
	 * @param mainMenu finestra grafica padre
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 */
	public GestoreButton(ContainerVendite gestoreVendite, JFrame mainMenu, String codice) {
		this.gestoreVendite = gestoreVendite;
		this.mainMenu = mainMenu;
		this.codice = codice;
	}
	
	@Override
	/**
	 * Metodo che gestisce l'evento catturato
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Info merce")) {
			// creare VisualizzaMerceVenduta
			mainMenu.setEnabled(false);
		}
		else if (e.getActionCommand().equals("Impiegato")) {
			// creare la finestra di visualizzazione impiegato
			mainMenu.setEnabled(false);
		}
		else if (e.getActionCommand().equals("Elimina")) {
			// eliminare vendita
		}
		else {
			
		}

	}

}
