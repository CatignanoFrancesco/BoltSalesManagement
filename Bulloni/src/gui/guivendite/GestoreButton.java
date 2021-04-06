package gui.guivendite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import databaseSQL.exception.DatabaseSQLException;
import gestori.gestorevendite.ContainerVendite;
import gestori.gestorevendite.EliminazioneVendite;
import gestori.gestorevendite.ModificaVendite;
import gestori.gestorevendite.VisualizzazioneVendite;
import gestori.gestorevendite.exception.GestoreVenditaException;
import gestori.gestoribulloni.VisualizzaBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;
import gui.guibulloni.InfoBulloneFrame;
import vendita.MerceVenduta;
import vendita.Vendita;

/**
 * @author GiannettaGerardo
 *
 * Classe che gestisce tutti i pulsanti delle liste che le interfacce grafiche mostrano;
 * questa classe avrà tanti costruttori diversi, quante sono le combinazioni di parametri
 * che servono ai vari pulsanti, questo perché questa classe serve solo a gestire quei
 * pulsanti di quelle due liste vendite
 */
public class GestoreButton implements ActionListener {

	/** la finestra JFrame che dovrà essere bloccata quando una nuova finestra si apre */
	private JFrame finestraJFrame;
	
	/** la finestra JDialog che dovrà essere bloccata quando una nuova finestra si apre */
	private JDialog finestraJDialog;
	
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
	
	/** istanza corrente del body vendite */
	private BodyVendite istanzaCorrente;
	
	
	
	/**
	 * Costruttore della classe GestoreButton
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param finestraJFrame finestra grafica padre
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 */
	public GestoreButton(ContainerVendite gestoreVendite, JFrame finestraJFrame, String codice) {
		this.gestoreVendite = gestoreVendite;
		this.finestraJFrame = finestraJFrame;
		this.codice = codice;
	}
	
	/**
	 * Costruttore della classe GestoreButton con istanza corrente di BodyVendite
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param finestraJFrame finestra grafica padre
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 * @param istanzaCorrente istanza corrente del body vendite
	 */
	public GestoreButton(ContainerVendite gestoreVendite, JFrame finestraJFrame, String codice, BodyVendite istanzaCorrente) {
		this.gestoreVendite = gestoreVendite;
		this.finestraJFrame = finestraJFrame;
		this.codice = codice;
		this.istanzaCorrente = istanzaCorrente;
	}
	
	
	
	/**
	 * Costruttore della classe GestoreButton con istanza corrente di BodyVendite, gestoreBulloni e finestra JFrame
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param gestoreBulloni gestore contenente tutti i bulloni presi dal database
	 * @param finestraJFrame finestra grafica padre
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 * @param istanzaCorrente istanza corrente del body vendite 
	 */
	public GestoreButton(ContainerVendite gestoreVendite, VisualizzaBulloni gestoreBulloni, JFrame finestraJFrame, String codice, BodyVendite istanzaCorrente) {
		this.gestoreVendite = gestoreVendite;
		this.gestoreBulloni = gestoreBulloni;
		this.finestraJFrame = finestraJFrame;
		this.codice = codice;
		this.istanzaCorrente = istanzaCorrente;
	}
	
	
	/**
	 * Costruttore della classe GestoreButton con istanza corrente di BodyVendite, gestoreBulloni e finestra JDialog
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param gestoreBulloni gestore contenente tutti i bulloni presi dal database
	 * @param finestraJDialog finestra grafica JDialog
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 * @param istanzaCorrente istanza corrente del body vendite 
	 */
	public GestoreButton(ContainerVendite gestoreVendite, VisualizzaBulloni gestoreBulloni, JDialog finestraJDialog, String codice, BodyVendite istanzaCorrente) {
		this.gestoreVendite = gestoreVendite;
		this.gestoreBulloni = gestoreBulloni;
		this.finestraJDialog = finestraJDialog;
		this.codice = codice;
		this.istanzaCorrente = istanzaCorrente;
	}
	
	
	/**
	 * Costruttore della classe GestoreButton con gestoreBulloni e finestra JDialog
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param gestoreBulloni gestore contenente tutti i bulloni presi dal database
	 * @param finestraJDialog finestra grafica JDialog
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 */
	public GestoreButton(ContainerVendite gestoreVendite, VisualizzaBulloni gestoreBulloni, JDialog finestraJDialog, String codice) {
		this.gestoreVendite = gestoreVendite;
		this.gestoreBulloni = gestoreBulloni;
		this.finestraJDialog = finestraJDialog;
		this.codice = codice;
	}
	
	
	
	/**
	 * Costruttore della classe GestoreButton con istanza corrente di BodyVendite, secondo codice e finestra JFrame
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param finestraJFrame finestra grafica padre
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 * @param secondoCodice secondo codice della tupla nella quale si clicca il pulsante
	 * @param istanzaCorrente istanza corrente del body vendite 
	 */
	public GestoreButton(ContainerVendite gestoreVendite, JFrame finestraJFrame, String codice, String secondoCodice, BodyVendite istanzaCorrente) {
		this.gestoreVendite = gestoreVendite;
		this.finestraJFrame = finestraJFrame;
		this.codice = codice;
		this.secondoCodice = secondoCodice;
		this.istanzaCorrente = istanzaCorrente;
	}
	
	
	/**
	 * Costruttore della classe GestoreButton con istanza corrente di BodyVendite, secondo codice e finestra JDialog
	 * 
	 * @param gestoreVendite gestore contenente tutte le vendite prese da database
	 * @param finestraJDialog finestra grafica JDialog
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 * @param secondoCodice secondo codice della tupla nella quale si clicca il pulsante
	 * @param istanzaCorrente istanza corrente del body vendite 
	 */
	public GestoreButton(ContainerVendite gestoreVendite, JDialog finestraJDialog, String codice, String secondoCodice, BodyVendite istanzaCorrente) {
		this.gestoreVendite = gestoreVendite;
		this.finestraJDialog = finestraJDialog;
		this.codice = codice;
		this.secondoCodice = secondoCodice;
		this.istanzaCorrente = istanzaCorrente;
	}
	
	
	
	@Override
	/**
	 * Metodo che gestisce l'evento catturato dai pulsanti delle due liste vendite
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Info merce")) {
			
			if ((this.gestoreBulloni != null) && (this.istanzaCorrente != null))
				eventoInfoMerce();
			
		}
		else if (e.getActionCommand().equals("Impiegato")) {
			// creare la finestra di visualizzazione impiegato
		}
		else if (e.getActionCommand().equals("Elimina")) {
			
			if (this.istanzaCorrente != null)
				eventoElimina();
			
		}
		else if (e.getActionCommand().equals("Modifica")) {
			
			if ((this.secondoCodice != null) && (this.istanzaCorrente != null) && (this.finestraJDialog != null))
				eventoModifica();
			
		}
		else if (e.getActionCommand().equals("Bullone")) {
			
			if ((this.gestoreBulloni != null) && (this.finestraJDialog != null))
				eventoBullone();
			
		}
		else {
			if (this.finestraJFrame != null) 
				JOptionPane.showMessageDialog(finestraJFrame, "Il pulsante cliccato non è riconosciuto.", "Errore", JOptionPane.ERROR_MESSAGE);
			else if (this.finestraJDialog != null) 
				JOptionPane.showMessageDialog(finestraJDialog, "Il pulsante cliccato non è riconosciuto.", "Errore", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	
	/**
	 * Metodo che elabora l'evento di click sul pulsante "Info merce";
	 * costruisce un oggetto (finestra grafica) VisualizzaMerceVenduta, 
	 * per visualizzare la lista di merce venduta in una specifica vendita
	 */
	public void eventoInfoMerce() {
		// recupero la vendita che ha quel preciso codice e da essa recupero il set di merce venduta
		try {
			Vendita<MerceVenduta> vendita = gestoreVendite.getVenditaByCodice(Integer.parseInt(codice));
			
			Set<MerceVenduta> merce = vendita.getMerceVenduta();
			
			// creo la finestra che permette di visualizzare la lista di merce venduta, modificare la vendita e visualizzare tutte le info del bullone venduto
			VisualizzaMerceVenduta mvm = new VisualizzaMerceVenduta(gestoreVendite, gestoreBulloni, Integer.parseInt(codice), merce, istanzaCorrente);
			mvm.setVisible(true);
		} 
		catch (GestoreVenditaException t) {
			JOptionPane.showMessageDialog(finestraJFrame, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	/**
	 * Metodo che elabora l'evento di click sul pulsante "Modifica";
	 * costruisce un oggetto (finestra grafica) ModificaVenditaBullone, per permettere la modifica di una vendita
	 */
	public void eventoModifica() {
		ModificaVenditaBullone mvb = new ModificaVenditaBullone(finestraJDialog, (ModificaVendite)gestoreVendite, Integer.parseUnsignedInt(codice), Integer.parseUnsignedInt(secondoCodice), istanzaCorrente);
		mvb.setVisible(true);
	}
	
	
	
	/**
	 * Metodo che elabora l'evento di click sul pulsante "Bullone";
	 * costruisce un oggetto (finestra grafica) InfoBulloneFrame per visualizzare 
	 * tutti i dettagli su un bullone contenuto in una vendita
	 */
	public void eventoBullone() {
		try {
			InfoBulloneFrame ibf = new InfoBulloneFrame(gestoreBulloni.getInfoBulloneByCodice(Integer.parseInt(codice)));
			ibf.setVisible(true);
		} 
		catch (NumberFormatException t) {
			JOptionPane.showMessageDialog(finestraJDialog, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		} 
		catch (GestoreBulloniException t) {
			JOptionPane.showMessageDialog(finestraJDialog, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	/**
	 * Metodo che elabora l'evento di click sul pulsante "Elimina";
	 * richiama il metodo di rimozione vendite per codice del gestore vendite,
	 * successivamente ristampa la lista nel body vendite (senza la vendite che 
	 * è stata appena eliminata)
	 */
	public void eventoElimina() {
		
		try {
			int numero = Integer.parseUnsignedInt(codice);
			
			((EliminazioneVendite)gestoreVendite).rimuoviVenditaByCodice(numero);
			
			istanzaCorrente.printListaVendite(((VisualizzazioneVendite)gestoreVendite).getVendite());
		} 
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(finestraJFrame, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		} 
		catch (GestoreVenditaException e) {
			istanzaCorrente.printListaVendite(new HashSet<Vendita<MerceVenduta>>());
		} 
		catch (DatabaseSQLException e) {
			JOptionPane.showMessageDialog(finestraJFrame, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(finestraJFrame, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	/**
	 * Metodo che elabora l'evento di click sul pulsante "Impiegato";
	 */
	public void eventoImpiegato() {
		
		/*DettagliImpiegatoWindow diw = new DettagliImpiegatoWindow() */
	}

}
