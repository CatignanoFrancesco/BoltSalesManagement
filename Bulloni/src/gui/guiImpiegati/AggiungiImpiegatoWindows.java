package gui.guiImpiegati;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import databaseSQL.exception.DatabaseSQLException;
import gui.ScreenManager;
import persona.Impiegato;
import persona.ImpiegatoBulloni;
import persona.exception.ExceptionAnagraficaErrata;
import persona.exception.ExceptionImpiegato;
import utility.Data;;

/**
 * 
 * questa classe implementa la fiestra grafica utile alla
 * creazione/inserimento di un nuovo impiegato
 * 
 * @author Francolino Flavio domenico
 *
 */
public class AggiungiImpiegatoWindows extends JDialog {
	
	static final long serialVersionUID = 1L;

	private static GridBagConstraints gbc = new GridBagConstraints();//proprieta layout

	// componenti relativi all'aquisizione del nome
	private static JLabel lblNome;
	private JTextField txtfNome;

	// componenti relativi all'aquisizione del cognome
	private static JLabel lblCognome;
	private JTextField txtfCognome;

	// componenti relativi all'aquisizione della data di nascita
	private static JLabel lblDataNascita;
	private JComboBox<Integer> cmbBoxGiornoNascita = new JComboBox<Integer>();
	private JComboBox<String> cmbBoxMeseNascita = new JComboBox<String>();
	private JComboBox<Integer> cmbBoxAnnoNascita = new JComboBox<Integer>();

	// componenti relativi all'aquisizione del sesso
	private static JLabel lblSesso;
	private JComboBox<Character> cmbSesso;

	// componenti relativi all'aquisizione dello stipendio mensile
	private static JLabel lblStipendioMensile;
	private JSpinner spnStipendioMensile;

	// componenti relativi all'aquisizione delle giornate lavorative annuali
	private static JLabel lblGionrateLavorativeAnnuali;
	private JSpinner spnGIornateLavorativeAnnuali;

	// componenti relativi all'aquisizione dei bulloni vendibili annualmente
	private static JLabel lblBulloniVendibiliAnnualmente;
	private JSpinner spnBulloniVendibiliAnnualmente;
	private JComboBox<String> cmbTipoInserimentoBulloni;// servira per fa decidere all'utente se usare i valori di
														// default o inserirli manualmente

	private static JButton btnInvia;// bottone per confermare l'aggiunta

	Impiegato impiegato;// impiegato da aggiungere

	/**
	 * costruttore per inizializzare le proprieta' base della finestra di aggiunta
	 */
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
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
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
		gbc.gridwidth = 3;
		this.add(this.txtfNome, gbc);

		// aggiungo la label per il cognome
		lblCognome = new JLabel("COGNOME : ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		this.add(lblCognome, gbc);

		// aggiungo il text field per il cognome
		this.txtfCognome = new JTextField(10);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		this.add(this.txtfCognome, gbc);

		// aggiungo la label per la data di nascita
		lblDataNascita = new JLabel("DATA DI NASCITA : ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(lblDataNascita, gbc);
		
		//aggiungo la comboBox giorno nascita
		this.cmbBoxGiornoNascita.setMaximumRowCount(15);
		this.cmbBoxGiornoNascita.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}));
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(this.cmbBoxGiornoNascita, gbc);
		
		//aggiungo la comboBox mese nascita
		this.cmbBoxMeseNascita.setMaximumRowCount(6);
		this.cmbBoxMeseNascita.setModel(new DefaultComboBoxModel<String>(new String[] {"Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"}));
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(this.cmbBoxMeseNascita, gbc);
		
		//aggiungo la comboBox anno nascita
		final int MAX_ANNO =  (Data.getDataAttuale().getAnno() - 18), MIN_ANNO = 1970;//limite di anni inseribili
		this.cmbBoxAnnoNascita.setMaximumRowCount(10);
		this.cmbBoxAnnoNascita.setModel(new DefaultComboBoxModel<Integer>());
		for(Integer i=MAX_ANNO; i>=MIN_ANNO; i--) {
			this.cmbBoxAnnoNascita.addItem(i);
		}
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(this.cmbBoxAnnoNascita, gbc);

		// aggiungo la label per il sesso
		lblSesso = new JLabel("SESSO : ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		this.add(lblSesso, gbc);

		// aggiungo la comboBox per il sesso
		cmbSesso = new JComboBox<Character>(new DefaultComboBoxModel<Character>(new Character[] { 'M', 'F' }));
		this.cmbSesso.setBackground(Color.white);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		this.add(cmbSesso, gbc);

		// aggiungo la label per lo stipendio
		lblStipendioMensile = new JLabel("STIPENDIO MENSILE (" + Impiegato.getMinStipendioMensile() + "-"
				+ Impiegato.getMaxStipendioMensile() + ") : ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		this.add(lblStipendioMensile, gbc);

		// aggiungo lo spinner per lo stipendio
		SpinnerNumberModel spnModelStipendio = new SpinnerNumberModel(Impiegato.getMinStipendioMensile(),
				Impiegato.getMinStipendioMensile(), Impiegato.getMaxStipendioMensile(), 50);// non posso andare sotto lo
																							// stipendio minimo o sopra
																							// il massim
		spnStipendioMensile = new JSpinner(spnModelStipendio);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		this.add(spnStipendioMensile, gbc);
		


		// aggiungo la label per le giornate
		lblGionrateLavorativeAnnuali = new JLabel("GIORNATE ANNUALI (" + Impiegato.getMinGiornateLavorativeAnnuali()
				+ "-" + Impiegato.getMaxGiornateLavorativeAnnuali() + ") : ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		this.add(lblGionrateLavorativeAnnuali, gbc);

		// aggiungo lo spinner per le giornate
		SpinnerNumberModel spnModelGiornate = new SpinnerNumberModel(Impiegato.getMinGiornateLavorativeAnnuali(),
				Impiegato.getMinGiornateLavorativeAnnuali(), Impiegato.getMaxGiornateLavorativeAnnuali(), 20);// non posso andare sotto le
																												// giornate minime o sopra le massime
		spnGIornateLavorativeAnnuali = new JSpinner(spnModelGiornate);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 3;
		this.add(spnGIornateLavorativeAnnuali, gbc);

		// aggiungo la label per i bulloni
		lblBulloniVendibiliAnnualmente = new JLabel("BULLONI VENDIBILI ANNUALMENTE : ");
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		this.add(lblBulloniVendibiliAnnualmente, gbc);
		
		//aggiungo il componente d'input per i bulloni
		cmbTipoInserimentoBulloni = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] { "Default (in base alla giornate)", "Manuale" }));
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 3;
		this.add(cmbTipoInserimentoBulloni, gbc);
		this.setTipoInserimento();//individuo il tipo d'inserimento scelto

		// aggiungo il bottone per confermare l'inserimento
		btnInvia = new JButton("inserisci");
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 3;
		gbc.gridy = 7;
		this.add(btnInvia, gbc);

		this.pack();

	}
	
	/**
	 * questo metodo controlla se l'utente vuole inserire manualmente 
	 * il numero di bulloni vendibili annualmente
	 * nel qual caso cambia la componennte dell'input dei bulloni
	 */
	private void setTipoInserimento() {

		this.cmbTipoInserimentoBulloni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (cmbTipoInserimentoBulloni.getSelectedItem().equals("Manuale")) {

					cambiaTipoInserimento();
				}

			}
		});

	}

	/**
	 * se l'utente ha deciso di inserire manualmente i bulloni
	 * questo metodo cambia la componente d'input dei bulloni
	 */
	private void cambiaTipoInserimento() {

		lblBulloniVendibiliAnnualmente
				.setText("BULLONI VENDIBILI ANNUALMENTE (" + ImpiegatoBulloni.getMinBulloniVendibiliAnnualmente() + "-"
						+ (ImpiegatoBulloni.getBulloniVendibiliGiornalmente()
								* ((int) this.spnGIornateLavorativeAnnuali.getValue()))
						+ ") : ");// setto il nuovo testo della label bulloni

		this.remove(this.cmbTipoInserimentoBulloni);// rimuovo il JComboBox

		// aggiungo lo spinner per i bulloni
		SpinnerNumberModel spnModelBulloni = new SpinnerNumberModel(
				ImpiegatoBulloni.getMinBulloniVendibiliAnnualmente(),
				ImpiegatoBulloni.getMinBulloniVendibiliAnnualmente(),
				(ImpiegatoBulloni.getBulloniVendibiliGiornalmente() * ((int) spnGIornateLavorativeAnnuali.getValue())),
				20);// non posso scendere sotto il numero minimo di bulloni vendibili annualmente
		spnBulloniVendibiliAnnualmente = new JSpinner(spnModelBulloni);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 3;
		this.add(this.spnBulloniVendibiliAnnualmente, gbc);

		// rivalido il tutto
		this.revalidate();
		this.repaint();

		this.pack();
	}

	
	/**
	 * questo metodo si occupa di triggerare il pulsante di conferma d'inserimento
	 * presente nella finestra
	 */
	private void triggerButton() {
		
		btnInvia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				aggiungiImpiegato();
				
			}
		});

	}
	
	
	/**
	 * questo metodo si occupa di leggere i dati inseriti dall'utente e istanziare
	 * il nuovo Impiegato aggiungendolo al db
	 */
	public void aggiungiImpiegato() {

		try {

			// leggo il nome
			String nome = this.txtfNome.getText();

			// leggo il cognome
			String cognome = this.txtfCognome.getText();

			// leggo il sesso
			char sesso = (char) this.cmbSesso.getSelectedItem();

			// leggo la data di nascita
			//int giornoNascita = 
			Data dataNascita = new Data((Integer)this.cmbBoxGiornoNascita.getSelectedItem(), this.cmbBoxMeseNascita.getSelectedIndex() + 1, (Integer)this.cmbBoxAnnoNascita.getSelectedItem());

			// leggo lo stipendio
			Double valoreStipendioMensile = (Double) spnStipendioMensile.getValue();
			float stipendioMensile = valoreStipendioMensile.floatValue();

			// leggo le giornate
			int giornateLavorativeAnnuali = (int) spnGIornateLavorativeAnnuali.getValue();

			// leggo i bulloni
			int bulloniVendibiliAnnualmente = 0;
			if (spnBulloniVendibiliAnnualmente != null) {// l'utente ha scelto d'inserire manualmente il numero di
														// bulloni vendibili

				bulloniVendibiliAnnualmente = (int) spnBulloniVendibiliAnnualmente.getValue();
			}
			

			// istazio l'impiegato e l'aggiungo al db
			if (bulloniVendibiliAnnualmente == 0) {// l'utente ha deciso di usare il valore di default

				this.impiegato = new ImpiegatoBulloni(nome, cognome, sesso, dataNascita, giornateLavorativeAnnuali,
						stipendioMensile);// chiamo il cotruttore che permette di assegnare bulloni in maniera
											// arbitraria

				ScreenManager.getGi().aggiungiImpiegato(this.impiegato);// aggiungo al db

				JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), "operazione avvenuta con successo");

				this.dispose();

			} else {

				this.impiegato = new ImpiegatoBulloni(nome, cognome, sesso, dataNascita, giornateLavorativeAnnuali,
						stipendioMensile, bulloniVendibiliAnnualmente);// chiamo il cotruttore che calcola in automatico i
																		// bulloni vendibili annaulmente

				ScreenManager.getGi().aggiungiImpiegato(this.impiegato);// aggiungo al db

				JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), "operazione avvenuta con successo");

				this.dispose();
			}

		} catch (ExceptionAnagraficaErrata | ExceptionImpiegato e) {

			JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), e.getMessage(), "exception",
					JOptionPane.ERROR_MESSAGE);

		} catch (SQLException | DatabaseSQLException e) {

			JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), e.getMessage(), "exception",
					JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

			JOptionPane.showMessageDialog(ScreenManager.getParentWindow(), e.getMessage(), "exception",
					JOptionPane.ERROR_MESSAGE);

		}

	}
	
	/**
	 * questo metodo ritorna l'impiegato creato affinche si possa aggiungere il nuovo
	 * pannello che lo visualizzera
	 * 
	 * @return impiegato l'impiegato che è stato creato
	 */
	public Impiegato getImpiegato() {
		
		return this.impiegato;
	}
	
}
