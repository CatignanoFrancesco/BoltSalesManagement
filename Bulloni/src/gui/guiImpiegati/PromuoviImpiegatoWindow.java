/**
 * 
 */
package gui.guiImpiegati;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import gestori.gestoreImpiegati.exception.ExceptionGestoreImpiegato;
import gui.ScreenManager;
import persona.Impiegato;
import persona.exception.ExceptionImpiegato;

/**
 * @author Francolino Flavio Domenico
 * 
 *         questa classe implementa la finestra relativa all'aquisizione dei
 *         nuovi valori per promuovre un impiegato
 *
 */
class PromuoviImpiegatoWindow extends JDialog {

	// attributi relativi all'aquisizione del nuovo stipedio
	private static JLabel lblStipendio;
	private JSpinner spnStipendio;

	// attributi relativi all'aquisizione delle nuove giornate lavorative annuali
	private static JLabel lblGiornate;
	private JSpinner spnGiornate;

	private static JButton btnInvia;

	private Impiegato impiegato;// impiegato da promuovere

	public PromuoviImpiegatoWindow(Impiegato impiegato) {

		this.impiegato = impiegato;

		this.setTitle(this.impiegato.getNome() + " " + this.impiegato.getCognome());

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setResizable(false);

		this.setModal(true);
		
		this.setLocationRelativeTo(ScreenManager.getParentWindow());

		this.setLayout(new GridBagLayout());

		this.aggiungiElementi();

		this.triggerButton();

		this.revalidate();
		this.repaint();

		this.setVisible(true);
	}

	/**
	 * questo metodo si occupa di aggiungere alla finestra le componenti necessarie
	 * all'aquisizione dei dati
	 */
	private void aggiungiElementi() {

		// setto le impostazione base del layout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;

		//aggiungo la label stipendio
		this.lblStipendio = new JLabel("stipendio mesile (" + (impiegato.getStipendioMensile() + 1) + "-"
				+ Impiegato.getMaxStipendioMensile() + ") : ");// indico il minimo stipendio assegnabile al medesimo
																// impiegato
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(lblStipendio, gbc);

		//aggiungo lo spinner per far inserire lo stipendio
		SpinnerNumberModel spnModelStipendio = new SpinnerNumberModel(this.impiegato.getStipendioMensile() + 50,
				this.impiegato.getStipendioMensile(), Impiegato.getMaxStipendioMensile(), 50);// al di sotto
																									// del'attuale
																									// stipendio non si
																									// puo scendere
		this.spnStipendio = new JSpinner(spnModelStipendio);
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(spnStipendio, gbc);

		
		//aggiungo la label giornate
		this.lblGiornate = new JLabel("giornate lavorative annuali (" + impiegato.getGiornateLavorativeAnnuali()
				+ "-" + Impiegato.getMaxGiornateLavorativeAnnuali() + ") : ");// indico le minime gionate assegnabili al
																				// medesimo impiegato
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(lblGiornate, gbc);

		//aggiungo lo spinner per far inserire le nuove giornate
		SpinnerNumberModel spnModelGiornate = new SpinnerNumberModel(this.impiegato.getGiornateLavorativeAnnuali() + 5,
				this.impiegato.getGiornateLavorativeAnnuali(), Impiegato.getMaxGiornateLavorativeAnnuali(), 5);// al di sotto delle attuali
																													// giornate non si puo scendere
		this.spnGiornate = new JSpinner(spnModelGiornate);
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add(spnGiornate, gbc);

		
		//aggiungo il bottone per dare conforma dei dati
		btnInvia = new JButton("invia");
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.add(btnInvia, gbc);

		this.pack();

	}

	/**
	 * questo metodo si occupa di prendere i nuovi valori immessi dall'utente ed
	 * effettuare la modifica sul db
	 */
	private void promuoviImpiegato() {
		
		try {
			
			//leggo lo stipendio
			Double valoreStipendioMensile = (Double) spnStipendio.getValue();
			float stipendioMensile = valoreStipendioMensile.floatValue();
			
			//leggo le giornate
			int giornateLavorativeAnnuali = (int) spnGiornate.getValue();
			
			this.impiegato.promuovi(stipendioMensile, giornateLavorativeAnnuali);//effettuo la modifica sul db
			
			ScreenManager.getGi().promuoviImpiegato(this.impiegato.getID(), giornateLavorativeAnnuali, stipendioMensile);
			
			JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), "promozione avvenuta con successo");
			
			dispose();
			
		} catch (ExceptionImpiegato e) {
			
			JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), e.getMessage(), "exception", JOptionPane.ERROR_MESSAGE);
			
		} catch (ExceptionGestoreImpiegato e) {
			
			JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), e.getMessage(), "exception", JOptionPane.ERROR_MESSAGE);
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), e.getMessage(), "exception", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * questo metodo si occupa di triggerare i bottoni presenti nella finestra
	 */
	private void triggerButton() {
		
		btnInvia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				promuoviImpiegato();
				
			}
		});

	}

}
