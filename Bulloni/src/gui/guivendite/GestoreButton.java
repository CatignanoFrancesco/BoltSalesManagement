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
import gestori.gestoreImpiegati.VisualizzazioneImpiegato;
import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gestori.gestorevendite.ContainerVendite;
import gestori.gestorevendite.EliminazioneVendite;
import gestori.gestorevendite.ModificaVendite;
import gestori.gestorevendite.VisualizzazioneVendite;
import gestori.gestorevendite.exception.GestoreVenditaException;
import gestori.gestoribulloni.VisualizzaBulloni;
import gestori.gestoribulloni.exception.GestoreBulloniException;
import gui.guiImpiegati.DettagliImpiegatoWindow;
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
	
	// COSTANTI
	
	/** costante che rappresenta il costruttore GestoreButton per il button "Info merce" */
	private final static int INFO_MERCE = 0;
	/** costante che rappresenta il costruttore GestoreButton per il button "Impiegato" */
	private final static int IMPIEGATO = 1;
	/** costante che rappresenta il costruttore GestoreButton per il button "Elimina" */
	private final static int ELIMINA = 2;
	/** costante che rappresenta il costruttore GestoreButton per il button "Modifica" */
	private final static int MODIFICA = 3;
	/** costante che rappresenta il costruttore GestoreButton per il button "Bullone" */
	private final static int BULLONE = 4;
	
	
	// VARIABILI
	
	/** questa variabile sara' impostata con la costante relativa al costruttore usato, 
	 * e serve per un controllo all'interno del metodo actionPerformed */
	private int buttonCorrente;

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
	
	/** il gestore degli impiegati con interfaccia di visualizzazione, contenente quindi solo metodi per la visualizzazione */
	private VisualizzazioneImpiegato gestoreImpiegati;
	
	/** istanza corrente del body vendite */
	private BodyVendite istanzaCorrente;
	
	
	
	/**
	 * Costruttore della classe GestoreButton per il button "Impiegato"
	 * 
	 * @param gestoreImpiegati gestore contenente tutti gli impiegati presi dal database
	 * @param finestraJFrame finestra grafica padre
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 */
	public GestoreButton(VisualizzazioneImpiegato gestoreImpiegati, JFrame finestraJFrame, String codice) {
		this.gestoreImpiegati = gestoreImpiegati;
		this.finestraJFrame = finestraJFrame;
		this.codice = codice;
		this.buttonCorrente = IMPIEGATO;
	}
	
	/**
	 * Costruttore della classe GestoreButton per il button "Elimina"
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
		this.buttonCorrente = ELIMINA;
	}
	
	
	
	/**
	 * Costruttore della classe GestoreButton per il button "Info merce"
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
		this.buttonCorrente = INFO_MERCE;
	}
	
	
	
	/**
	 * Costruttore della classe GestoreButton per il button "Bullone"
	 * 
	 * @param gestoreBulloni gestore contenente tutti i bulloni presi dal database
	 * @param finestraJDialog finestra grafica JDialog
	 * @param codice codice della tupla nella quale si clicca il pulsante
	 */
	public GestoreButton(VisualizzaBulloni gestoreBulloni, JDialog finestraJDialog, String codice) {
		this.gestoreBulloni = gestoreBulloni;
		this.finestraJDialog = finestraJDialog;
		this.codice = codice;
		this.buttonCorrente = BULLONE;
	}
	
	
	
	/**
	 * Costruttore della classe GestoreButton per il button "Modifica"
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
		this.buttonCorrente = MODIFICA;
	}
	
	
	
	@Override
	/**
	 * Metodo che gestisce l'evento catturato dai pulsanti delle due liste vendite
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Info merce")) {
			
			if (this.buttonCorrente == INFO_MERCE)
				eventoInfoMerce();
			
		}
		else if (e.getActionCommand().equals("Impiegato")) {
			
			if (this.buttonCorrente == IMPIEGATO) {
				eventoImpiegato();
			}
		}
		else if (e.getActionCommand().equals("Elimina")) {
			
			if (this.buttonCorrente == ELIMINA)
				eventoElimina();
			
		}
		else if (e.getActionCommand().equals("Modifica")) {
			
			if (this.buttonCorrente == MODIFICA)
				eventoModifica();
			
		}
		else if (e.getActionCommand().equals("Bullone")) {
			
			if (this.buttonCorrente == BULLONE)
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
			int codiceBullone = Integer.parseInt(codice);
			InfoBulloneFrame ibf = new InfoBulloneFrame(gestoreBulloni.getInfoBulloneByCodice(codiceBullone));
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
		
		int risultato = JOptionPane.showConfirmDialog(finestraJFrame, "Sei sicuro di voler eliminare?", "Eliminazione", JOptionPane.YES_NO_OPTION);
		
		// se risultato e' 0, significa che e' stato selezionato si
		if (risultato == 0) {
			try {
				int numero = Integer.parseUnsignedInt(codice);
				
				((EliminazioneVendite)gestoreVendite).rimuoviVenditaByCodice(numero);
				
				istanzaCorrente.printListaVendite(((VisualizzazioneVendite)gestoreVendite).getVendite());
			} 
			catch (NumberFormatException t) {
				JOptionPane.showMessageDialog(finestraJFrame, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			} 
			catch (GestoreVenditaException t) {
				istanzaCorrente.printListaVendite(new HashSet<Vendita<MerceVenduta>>());
			} 
			catch (DatabaseSQLException t) {
				JOptionPane.showMessageDialog(finestraJFrame, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			} 
			catch (SQLException t) {
				JOptionPane.showMessageDialog(finestraJFrame, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
	
	/**
	 * Metodo che elabora l'evento di click sul pulsante "Impiegato";
	 */
	public void eventoImpiegato() {
		
		try {
			int matricolaImpiegato = Integer.parseInt(codice);
			@SuppressWarnings("unused")
			DettagliImpiegatoWindow diw = new DettagliImpiegatoWindow(gestoreImpiegati.getImpiegatoByID(matricolaImpiegato));
		} 
		catch (NumberFormatException t) {
			JOptionPane.showMessageDialog(finestraJFrame, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		}
		catch (ExceptionGestoreImpiegato t) {
			JOptionPane.showMessageDialog(finestraJFrame, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		}
	}

}
