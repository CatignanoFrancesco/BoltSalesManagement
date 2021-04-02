package gui.guivendite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import gestori.gestorevendite.ContainerVendite;
import gestori.gestorevendite.ModificaVendite;
import gestori.gestorevendite.exception.GestoreVenditaException;
import gestori.gestoribulloni.VisualizzaBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;
import gui.guibulloni.InfoBulloneFrame;
import vendita.MerceVenduta;
import vendita.Vendita;

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
	
	/** codice della tupla nella quale si clicca il pulsante; può servire nel caso in cui servano 2 parametri codice
	 * per distinguere una tupla da un'altra */
	private String secondoCodice;
	
	/** il gestore delle vendite con interfaccia completa di tutti i metodi */
	private ContainerVendite gestoreVendite;
	
	/** il gestore dei bulloni con interfaccia di visualizzazione, contenente quindi solo metodi per la visualizzazione */
	private VisualizzaBulloni gestoreBulloni;
	
	
	
	/**
	 * Costruttore della classe GestoreButton
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param mainMenu finestra grafica padre
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 */
	public GestoreButton(ContainerVendite gestoreVendite, JFrame mainMenu, String codice) {
		this.gestoreVendite = gestoreVendite;
		this.mainMenu = mainMenu;
		this.codice = codice;
	}
	
	
	
	/**
	 * Costruttore della classe GestoreButton
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param gestoreBulloni gestore contenente tutti i bulloni presi dal database
	 * @param mainMenu finestra grafica padre
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 */
	public GestoreButton(ContainerVendite gestoreVendite, VisualizzaBulloni gestoreBulloni, JFrame mainMenu, String codice) {
		this.gestoreVendite = gestoreVendite;
		this.gestoreBulloni = gestoreBulloni;
		this.mainMenu = mainMenu;
		this.codice = codice;
	}
	
	
	
	/**
	 * Costruttore della classe GestoreButton
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param mainMenu finestra grafica padre
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 */
	public GestoreButton(ContainerVendite gestoreVendite, JFrame mainMenu, String codice, String secondoCodice) {
		this.gestoreVendite = gestoreVendite;
		this.mainMenu = mainMenu;
		this.codice = codice;
		this.secondoCodice = secondoCodice;
	}
	
	
	
	@Override
	/**
	 * Metodo che gestisce l'evento catturato
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Info merce")) {
			
			// recupero la vendita che ha quel preciso codice e da essa recupero il set di merce venduta
			try {
				Vendita<MerceVenduta> vendita = gestoreVendite.getVenditaByCodice(Integer.parseInt(codice));
				
				Set<MerceVenduta> merce = vendita.getMerceVenduta();
				
				// creo la finestra che permette di visualizzare la lista di merce venduta, modificare la vendita e visualizzare tutte le info del bullone venduto
				VisualizzaMerceVenduta mvm = new VisualizzaMerceVenduta(mainMenu, gestoreVendite, Integer.parseInt(codice), merce);
				mvm.setVisible(true);
				mainMenu.setEnabled(false);
			} 
			catch (GestoreVenditaException t) {
				JOptionPane.showMessageDialog(mainMenu, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else if (e.getActionCommand().equals("Impiegato")) {
			// creare la finestra di visualizzazione impiegato
		}
		else if (e.getActionCommand().equals("Elimina")) {
			// eliminare vendita
		}
		else if (e.getActionCommand().equals("Modifica")) {
			
			if (secondoCodice != null) {

				ModificaVenditaBullone mvb = new ModificaVenditaBullone(mainMenu, (ModificaVendite)gestoreVendite, Integer.parseUnsignedInt(codice), Integer.parseUnsignedInt(secondoCodice));
				mvb.setVisible(true);
				mainMenu.setEnabled(false);
			}
			
		}
		else if (e.getActionCommand().equals("Bullone")) {
			
			if (this.gestoreBulloni != null) {
				try {
					InfoBulloneFrame ibf = new InfoBulloneFrame(mainMenu, gestoreBulloni.getInfoBulloneByCodice(Integer.parseInt(codice)));
					ibf.setVisible(true);
					mainMenu.setEnabled(false);
				} 
				catch (NumberFormatException t) {
					JOptionPane.showMessageDialog(mainMenu, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				} 
				catch (GestoreBulloniException t) {
					JOptionPane.showMessageDialog(mainMenu, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(mainMenu, "Il pulsante cliccato non è riconosciuto.", "Errore", JOptionPane.ERROR_MESSAGE);
		}

	}

}
