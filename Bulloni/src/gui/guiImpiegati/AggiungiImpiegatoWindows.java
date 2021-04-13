/**
 * 
 */
package gui.guiImpiegati;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import gui.ScreenManager;
import persona.Impiegato;
import persona.ImpiegatoBulloni;;

/**
 * @author Francolino Flavio Domenico
 * 
 *         questa classe implementa la fiestra grafica utile alla
 *         creazione/inserimento di un nuovo impiegato
 *
 */
public class AggiungiImpiegatoWindows extends JDialog {

	// attributi relativi all'aquisizione del nome
	private static JLabel lblNome;
	private JTextField txtfNome;

	// attributi relativi all'aquisizione del cognome
	private static JLabel lblCognome;
	private JTextField txtfCognome;

	// attributi relativi all'aquisizione della data di nascita
	private static JLabel lblDataNascita;
	private DatePicker dtaPickDataNascita;

	// attributi relativi all'aquisizione del sesso
	private static JLabel lblSesso;
	private JComboBox<Character> cmbSesso;

	// attributi relativi all'aquisizione dello stipendio mensile
	private static JLabel lblStipendioMensile;
	private JSpinner spnStipendioMensile;

	// attributi relativi all'aquisizione delle giornate lavorative annuali
	private static JLabel lblGionrateLavorativeAnnuali;
	private JSpinner spnGIornateLavorativeAnnuali;

	// attributi relativi all'aquisizione dei bulloni vendibili annualmente
	private static JLabel lblBulloniVendibiliAnnualmente;
	private JSpinner spnBulloniVendibiliAnnualmente;

	private static JButton btnInvia;// bottone per confermare la creazione

	Impiegato impiegato;// impiegato da aggiungere

	public AggiungiImpiegatoWindows() {

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setTitle("assumi un nuovo Impiegato");

		this.setResizable(false);

		this.setLocationRelativeTo(ScreenManager.getParentWindow());

		this.setModal(true);

		this.setLayout(new GridBagLayout());

		this.aggiungiElementi();

		this.triggerButton();

		this.revalidate();
		this.repaint();

		this.setVisible(true);
	}

	/**
	 * questo metodo aggiunge le componenenti grafice alla finestra
	 */
	private void aggiungiElementi() {

		// setto le proprieta base del layout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		// aggiungo la label per il nome
		lblNome = new JLabel("NOME : ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(lblNome, gbc);

		// aggiungo il text field per il nome
		this.txtfNome = new JTextField(10);
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(this.txtfNome, gbc);

		// aggiungo la label per il cognome
		lblCognome = new JLabel("COGNOME : ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(lblCognome, gbc);

		// aggiungo il text field per il cognome
		this.txtfCognome = new JTextField(10);
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add(this.txtfCognome, gbc);

		// aggiungo la label per la data di nascita
		lblNome = new JLabel("DATA DI NASCITA : ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(lblNome, gbc);

		// aggiungo il data picker per la data di nascita
		this.dtaPickDataNascita = new DatePicker();
		this.dtaPickDataNascita.setDateToToday();
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.add(this.dtaPickDataNascita, gbc);

		// aggiungo la label per il sesso
		lblSesso = new JLabel("SESSO : ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		this.add(lblSesso, gbc);

		// aggiungo la comboBox per il sesso
		cmbSesso = new JComboBox<Character>(new DefaultComboBoxModel<Character>(new Character[] { 'M', 'F' }));
		this.cmbSesso.setBackground(Color.white);
		gbc.gridx = 1;
		gbc.gridy = 3;
		this.add(cmbSesso, gbc);

		// aggiungo la label per lo stipendio
		lblStipendioMensile = new JLabel("STIPENDIO MENSILE (" + Impiegato.getMinStipendioMensile() + "-"
				+ Impiegato.getMaxStipendioMensile() + ") : ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		this.add(lblStipendioMensile, gbc);

		// aggiungo lo spinner per lo stipendio
		SpinnerNumberModel spnModelStipendio = new SpinnerNumberModel(Impiegato.getMinStipendioMensile(),
				Impiegato.getMinStipendioMensile(), Impiegato.getMaxStipendioMensile(), 50);// non posso andare sotto lo
																							// stipendio minimo o sopra
																							// il massim
		spnStipendioMensile = new JSpinner(spnModelStipendio);
		gbc.gridx = 1;
		gbc.gridy = 4;
		this.add(spnStipendioMensile, gbc);

		// aggiungo la label per le giornate
		lblGionrateLavorativeAnnuali = new JLabel("GIORNATE ANNUALI (" + Impiegato.getMinGiornateLavorativeAnnuali()
				+ "-" + Impiegato.getMaxGiornateLavorativeAnnuali() + ") : ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		this.add(lblGionrateLavorativeAnnuali, gbc);

		// aggiungo lo spinner per le giornate
		SpinnerNumberModel spnModelGiornate = new SpinnerNumberModel(Impiegato.getMinGiornateLavorativeAnnuali(),
				Impiegato.getMinGiornateLavorativeAnnuali(), Impiegato.getMaxGiornateLavorativeAnnuali(), 20);// non
																												// posso
																												// andare
																												// sotto
																												// le
																												// giornate
																												// minime
																												// o
																												// sopra
																												// le
																												// massime
		spnGIornateLavorativeAnnuali = new JSpinner(spnModelGiornate);
		gbc.gridx = 1;
		gbc.gridy = 5;
		this.add(spnGIornateLavorativeAnnuali, gbc);

		// aggiungo la label per i bulloni
		lblBulloniVendibiliAnnualmente = new JLabel(
				"BULLONI VENDIBILI ANNUALMENTE (" + ImpiegatoBulloni.getMinBulloniVendibiliAnnualmente() + "-"
						+ (500 * ((int) spnGIornateLavorativeAnnuali.getValue())) + ") : ");
		gbc.gridx = 0;
		gbc.gridy = 6;
		this.add(lblBulloniVendibiliAnnualmente, gbc);

		// aggiungo lo spinner per i bulloni
		SpinnerNumberModel spnModelBulloni = new SpinnerNumberModel(
				ImpiegatoBulloni.getMinBulloniVendibiliAnnualmente(),
				ImpiegatoBulloni.getMinBulloniVendibiliAnnualmente(),
				(ImpiegatoBulloni.getBulloniVendibiliGiornalmente() * ((int) spnGIornateLavorativeAnnuali.getValue())),
				20);//non posso scendere sotto il numero minimo di bulloni vendibili annualmente
		spnBulloniVendibiliAnnualmente = new JSpinner(spnModelBulloni);
		gbc.gridx = 1;
		gbc.gridy = 6;
		this.add(spnBulloniVendibiliAnnualmente, gbc);
		
		//aggiungo il bottone per confermare l'inserimento
		btnInvia = new JButton("inserisci");
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridy = 7;
		this.add(btnInvia, gbc);

		this.pack();

	}

	private void triggerButton() {

	}

}
