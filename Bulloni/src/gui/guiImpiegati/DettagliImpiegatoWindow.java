/**
 * 
 */
package gui.guiImpiegati;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import gui.ScreenManager;
import persona.Impiegato;
import persona.ImpiegatoBulloni;

/**
 * @author Francolino Flavio Domenico
 * 
 * questa classe implementa la finestra relativa alla visualizzazione dei dettagli di un impiegato
 *
 */
public class DettagliImpiegatoWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextArea txtaDettagli;//text area che visualizzera i dettagli di un impiegato
	
	private Impiegato impiegato;//impiegato di cui visualizzare i dettagli
	
	/**
	 * costruttore usato solo per inizializzare le proprieta base di questa finestra
	 * 
	 * @param impiegato l'impiegato di cui la finestra deve fare vedere i dettagli
	 */
	public DettagliImpiegatoWindow(Impiegato impiegato) {
		
		this.impiegato = impiegato;
		
		this.setTitle(this.impiegato.getNome() + " " + this.impiegato.getCognome());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setResizable(false);
		
		this.setLocationRelativeTo(ScreenManager.getParentWindow());
		
		this.setLayout(new BorderLayout());
		
		this.setModal(true);
		
		this.visualizzaDettagli();
		
		
		this.revalidate();
		this.repaint();
		
		this.setVisible(true);
		
		
	}
	
	/**
	 * questo metodo server per inizializzare la JTextArea in modo tale da far effettivamente visualizzarre i dettagli del dato impiegato
	 */
	private void visualizzaDettagli() {
		
		this.txtaDettagli = new JTextArea();
		
		this.txtaDettagli.setBackground(new Color(238, 238, 238));
		
		this.txtaDettagli.setEnabled(false);
		
		this.txtaDettagli.setDisabledTextColor(Color.black);
		
		this.txtaDettagli.setText(((ImpiegatoBulloni)this.impiegato).toString());
		
		this.txtaDettagli.setFont(new Font("verdana", Font.CENTER_BASELINE, 20));
		
		this.txtaDettagli.setMargin(new Insets(5, 10, 2, 10));
		
		this.add(txtaDettagli, BorderLayout.CENTER);
		
		this.pack();
		
	}
	
	

}
