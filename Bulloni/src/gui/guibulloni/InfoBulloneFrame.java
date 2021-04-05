package gui.guibulloni;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoBulloneFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_WIDTH = 350;
	private static final int MAX_HEIGHT = 450;
	
	private JFrame mainFrame;
	private String[] infoBullone;
	
	/*
	 * Elementi visibili nella finestra
	 */
	private JPanel titlePanel = new JPanel();
	private JLabel lblTitolo = new JLabel();
	private JPanel infoPanel = new JPanel();
	private JLabel lblTipoBullone = new JLabel();
	private JLabel lblTipoBulloneValue = new JLabel();
	private JLabel lblCodice = new JLabel();
	private JLabel lblCodiceValue = new JLabel();
	private JLabel lblDataProduzione = new JLabel();
	private JLabel lblDataProduzioneValue = new JLabel();
	private JLabel lblLuogoProduzione = new JLabel();
	private JLabel lblLuogoProduzioneValue = new JLabel();
	private JLabel lblPeso = new JLabel();
	private JLabel lblPesoValue = new JLabel();
	private JLabel lblPrezzo = new JLabel();
	private JLabel lblPrezzoValue = new JLabel();
	private JLabel lblMateriale = new JLabel();
	private JLabel lblMaterialeValue = new JLabel();
	private JLabel lblLunghezza = new JLabel();
	private JLabel lblLunghezzaValue = new JLabel();
	private JLabel lblDiametroVite = new JLabel();
	private JLabel lblDiametroViteValue = new JLabel();
	private JLabel lblDiametroDado = new JLabel();
	private JLabel lblDiametroDadoValue = new JLabel();
	private JLabel lblTipoInnesto = new JLabel();
	private JLabel lblTipoInnestoValue = new JLabel();
	
	
	/*
	 * -------------
	 *  COSTRUTTORE
	 * -------------
	 */
	
	/**
	 * Questo costruttore si occupa di mostrare le informazioni complete su un singolo bullone.
	 * @param mainFrame La finestra principale.
	 * @param infoBullone Le informazioni da mostrare.
	 */
	public InfoBulloneFrame(JFrame mainFrame, String[] infoBullone) {
		this.mainFrame = mainFrame;
		this.infoBullone = infoBullone;
		
		this.setTitle("Informazioni bullone");
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setBounds(100, 100, MAX_WIDTH, MAX_HEIGHT);
		this.getContentPane().setLayout(new BorderLayout());
		this.addWindowListener(this);
		
		this.creaTitlePanel();
		this.creaInfoPanel();
	}
	
	
	/*
	 * -----------------
	 *  METODI PUBBLICI
	 * -----------------
	 */
	
	@Override
	public void windowOpened(WindowEvent e) {}


	@Override
	public void windowClosing(WindowEvent e) {
		this.mainFrame.setEnabled(true);
		this.mainFrame.setFocusable(true);
	}


	@Override
	public void windowClosed(WindowEvent e) {}


	@Override
	public void windowIconified(WindowEvent e) {}


	@Override
	public void windowDeiconified(WindowEvent e) {}


	@Override
	public void windowActivated(WindowEvent e) {}


	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	
	/*
	 * ----------------
	 *  METODI PRIVATI
	 * ----------------
	 */
	
	/**
	 * Questo metodo si occupa di creare il pannello per il titolo e di impostarne il layout.
	 */
	private void creaTitlePanel() {
		this.getContentPane().add(this.titlePanel, BorderLayout.NORTH);
		
		this.lblTitolo.setText("INFORMAZIONI BULLONE " + this.infoBullone[1]);
		this.lblTitolo.setFont(new Font(this.lblTitolo.getFont().getFontName(), Font.BOLD, 16));
		this.titlePanel.add(this.lblTitolo);
	}
	
	
	/**
	 * Questo metodo si occupa di creare e impostare il layout contenente le informazioni sul bullone.
	 * Il layout utilizzato e' il GridBagLayout.
	 */
	private void creaInfoPanel() {
		/*
		 * Pannello informazioni
		 */
		this.getContentPane().add(this.infoPanel, BorderLayout.CENTER);
		GridBagLayout gblForInfoPanel = new GridBagLayout();
		gblForInfoPanel.columnWidths = new int[] {0, 0};
		gblForInfoPanel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gblForInfoPanel.columnWeights = new double[] {0.0, Double.MIN_VALUE};
		gblForInfoPanel.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		this.infoPanel.setLayout(gblForInfoPanel);
		
		/*
		 * Label per il tipo di bullone
		 */
		this.lblTipoBullone.setText("Tipo di Bullone:");
		this.lblTipoBullone.setFont(new Font(this.lblTipoBullone.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblTipoBullone = new GridBagConstraints();
		gbcForLblTipoBullone.gridx = 0;
		gbcForLblTipoBullone.gridy = 0;
		gbcForLblTipoBullone.anchor = GridBagConstraints.LINE_START;
		gbcForLblTipoBullone.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblTipoBullone, gbcForLblTipoBullone);
		
		/*
		 * Valore per il tipo di bullone
		 */
		this.lblTipoBulloneValue.setText(this.infoBullone[0]);
		this.lblTipoBulloneValue.setFont(new Font(this.lblTipoBullone.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblTipoBulloneValue = new GridBagConstraints();
		gbcForLblTipoBulloneValue.gridx = 1;
		gbcForLblTipoBulloneValue.gridy = 0;
		gbcForLblTipoBulloneValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblTipoBulloneValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblTipoBulloneValue, gbcForLblTipoBulloneValue);
		
		/*
		 * Label per il codice
		 */
		this.lblCodice.setText("Codice:");
		this.lblCodice.setFont(new Font(this.lblCodice.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblCodice = new GridBagConstraints();
		gbcForLblCodice.gridx = 0;
		gbcForLblCodice.gridy = 1;
		gbcForLblCodice.anchor = GridBagConstraints.LINE_START;
		gbcForLblCodice.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblCodice, gbcForLblCodice);
		
		/*
		 * Valore per il codice
		 */
		this.lblCodiceValue.setText(this.infoBullone[1]);
		this.lblCodiceValue.setFont(new Font(this.lblCodiceValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblCodiceValue = new GridBagConstraints();
		gbcForLblCodiceValue.gridx = 1;
		gbcForLblCodiceValue.gridy = 1;
		gbcForLblCodiceValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblCodiceValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblCodiceValue, gbcForLblCodiceValue);
		
		/*
		 * Label per la data di produzione
		 */
		this.lblDataProduzione.setText("Data di produzione:");
		this.lblDataProduzione.setFont(new Font(this.lblDataProduzione.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblDataProduzione = new GridBagConstraints();
		gbcForLblDataProduzione.gridx = 0;
		gbcForLblDataProduzione.gridy = 2;
		gbcForLblDataProduzione.anchor = GridBagConstraints.LINE_START;
		gbcForLblDataProduzione.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblDataProduzione, gbcForLblDataProduzione);
		
		/*
		 * Valore per la data di produzione
		 */
		this.lblDataProduzioneValue.setText(this.infoBullone[2]);
		this.lblDataProduzioneValue.setFont(new Font(this.lblDataProduzioneValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblDataProduzioneValue = new GridBagConstraints();
		gbcForLblDataProduzioneValue.gridx = 1;
		gbcForLblDataProduzioneValue.gridy = 2;
		gbcForLblDataProduzioneValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblDataProduzioneValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblDataProduzioneValue, gbcForLblDataProduzioneValue);
		
		/*
		 * Label per il luogo di produzione
		 */
		this.lblLuogoProduzione.setText("Luogo di produzione:");
		this.lblLuogoProduzione.setFont(new Font(this.lblLuogoProduzione.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblLuogoProduzione = new GridBagConstraints();
		gbcForLblLuogoProduzione.gridx = 0;
		gbcForLblLuogoProduzione.gridy = 3;
		gbcForLblLuogoProduzione.anchor = GridBagConstraints.LINE_START;
		gbcForLblLuogoProduzione.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblLuogoProduzione, gbcForLblLuogoProduzione);
		
		/*
		 * Valore per il luogo di produzione
		 */
		this.lblLuogoProduzioneValue.setText(this.infoBullone[3]);
		this.lblLuogoProduzioneValue.setFont(new Font(this.lblLuogoProduzioneValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblLuogoProduzioneValue = new GridBagConstraints();
		gbcForLblLuogoProduzioneValue.gridx = 1;
		gbcForLblLuogoProduzioneValue.gridy = 3;
		gbcForLblLuogoProduzioneValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblLuogoProduzioneValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblLuogoProduzioneValue, gbcForLblLuogoProduzioneValue);
		
		/*
		 * Label per il peso
		 */
		this.lblPeso.setText("Peso:");
		this.lblPeso.setFont(new Font(this.lblPeso.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblPeso = new GridBagConstraints();
		gbcForLblPeso.gridx = 0;
		gbcForLblPeso.gridy = 4;
		gbcForLblPeso.anchor = GridBagConstraints.LINE_START;
		gbcForLblPeso.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblPeso, gbcForLblPeso);
		
		/*
		 * Valore per il peso
		 */
		this.lblPesoValue.setText(this.infoBullone[4] + " gr.");
		this.lblPesoValue.setFont(new Font(this.lblPesoValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblPesoValue = new GridBagConstraints();
		gbcForLblPesoValue.gridx = 1;
		gbcForLblPesoValue.gridy = 4;
		gbcForLblPesoValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblPesoValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblPesoValue, gbcForLblPesoValue);
		
		/*
		 * Label per il prezzo
		 */
		this.lblPrezzo.setText("Prezzo:");
		this.lblPrezzo.setFont(new Font(this.lblPrezzo.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblPrezzo = new GridBagConstraints();
		gbcForLblPrezzo.gridx = 0;
		gbcForLblPrezzo.gridy = 5;
		gbcForLblPrezzo.anchor = GridBagConstraints.LINE_START;
		gbcForLblPrezzo.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblPrezzo, gbcForLblPrezzo);
		
		/*
		 * Valore per il prezzo
		 */
		this.lblPrezzoValue.setText(this.infoBullone[5] + " \u20ac");
		this.lblPrezzoValue.setFont(new Font(this.lblPesoValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblPrezzoValue = new GridBagConstraints();
		gbcForLblPrezzoValue.gridx = 1;
		gbcForLblPrezzoValue.gridy = 5;
		gbcForLblPrezzoValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblPrezzoValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblPrezzoValue, gbcForLblPrezzoValue);
		
		/*
		 * Label per il materiale
		 */
		this.lblMateriale.setText("Materiale:");
		this.lblMateriale.setFont(new Font(this.lblMateriale.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblMateriale = new GridBagConstraints();
		gbcForLblMateriale.gridx = 0;
		gbcForLblMateriale.gridy = 6;
		gbcForLblMateriale.anchor = GridBagConstraints.LINE_START;
		gbcForLblMateriale.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblMateriale, gbcForLblMateriale);
		
		/*
		 * Valore per il materiale
		 */
		this.lblMaterialeValue.setText(this.infoBullone[6]);
		this.lblMaterialeValue.setFont(new Font(this.lblMaterialeValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblMaterialeValue = new GridBagConstraints();
		gbcForLblMaterialeValue.gridx = 1;
		gbcForLblMaterialeValue.gridy = 6;
		gbcForLblMaterialeValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblMaterialeValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblMaterialeValue, gbcForLblMaterialeValue);
		
		/*
		 * Label per la lunghezza
		 */
		this.lblLunghezza.setText("Lunghezza:");
		this.lblLunghezza.setFont(new Font(this.lblLunghezza.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblLunghezza = new GridBagConstraints();
		gbcForLblLunghezza.gridx = 0;
		gbcForLblLunghezza.gridy = 7;
		gbcForLblLunghezza.anchor = GridBagConstraints.LINE_START;
		gbcForLblLunghezza.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblLunghezza, gbcForLblLunghezza);
		
		/*
		 * Valore per la lunghezza
		 */
		this.lblLunghezzaValue.setText(this.infoBullone[7] + " mm");
		this.lblLunghezzaValue.setFont(new Font(this.lblLunghezzaValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblLunghezzaValue = new GridBagConstraints();
		gbcForLblLunghezzaValue.gridx = 1;
		gbcForLblLunghezzaValue.gridy = 7;
		gbcForLblLunghezzaValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblLunghezzaValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblLunghezzaValue, gbcForLblLunghezzaValue);
		
		/*
		 * Label per il diametro della vite
		 */
		this.lblDiametroVite.setText("Diametro della vite:");
		this.lblDiametroVite.setFont(new Font(this.lblDiametroVite.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblDiametroVite = new GridBagConstraints();
		gbcForLblDiametroVite.gridx = 0;
		gbcForLblDiametroVite.gridy = 8;
		gbcForLblDiametroVite.anchor = GridBagConstraints.LINE_START;
		gbcForLblDiametroVite.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblDiametroVite, gbcForLblDiametroVite);
		
		/*
		 * Valore per il diametro della vite
		 */
		this.lblDiametroViteValue.setText(this.infoBullone[8] + " cm");
		this.lblDiametroViteValue.setFont(new Font(this.lblDiametroViteValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblDiametroViteValue = new GridBagConstraints();
		gbcForLblDiametroViteValue.gridx = 1;
		gbcForLblDiametroViteValue.gridy = 8;
		gbcForLblDiametroViteValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblDiametroViteValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblDiametroViteValue, gbcForLblDiametroViteValue);
		
		/*
		 * Label per il diametro del dado
		 */
		this.lblDiametroDado.setText("Diametro del dado:");
		this.lblDiametroDado.setFont(new Font(this.lblDiametroDado.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblDiametroDado = new GridBagConstraints();
		gbcForLblDiametroDado.gridx = 0;
		gbcForLblDiametroDado.gridy = 9;
		gbcForLblDiametroDado.anchor = GridBagConstraints.LINE_START;
		gbcForLblDiametroDado.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblDiametroDado, gbcForLblDiametroDado);
		
		/*
		 * Valore per il diametro del dado
		 */
		this.lblDiametroDadoValue.setText(this.infoBullone[9] + " cm");
		this.lblDiametroDadoValue.setFont(new Font(this.lblDiametroDadoValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblDiametroDadoValue = new GridBagConstraints();
		gbcForLblDiametroDadoValue.gridx = 1;
		gbcForLblDiametroDadoValue.gridy = 9;
		gbcForLblDiametroDadoValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblDiametroDadoValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblDiametroDadoValue, gbcForLblDiametroDadoValue);
		
		/*
		 * Label per il tipo d'innesto
		 */
		this.lblTipoInnesto.setText("Tipo di innesto:");
		this.lblTipoInnesto.setFont(new Font(this.lblTipoInnesto.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblTipoInnesto = new GridBagConstraints();
		gbcForLblTipoInnesto.gridx = 0;
		gbcForLblTipoInnesto.gridy = 10;
		gbcForLblTipoInnesto.anchor = GridBagConstraints.LINE_START;
		gbcForLblTipoInnesto.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblTipoInnesto, gbcForLblTipoInnesto);
		
		/*
		 * Valore per il tipo d'innesto
		 */
		this.lblTipoInnestoValue.setText(this.infoBullone[10]);
		this.lblTipoInnestoValue.setFont(new Font(this.lblTipoInnestoValue.getFont().getFontName(), Font.BOLD, 11));
		GridBagConstraints gbcForLblTipoInnestoValue = new GridBagConstraints();
		gbcForLblTipoInnestoValue.gridx = 1;
		gbcForLblTipoInnestoValue.gridy = 10;
		gbcForLblTipoInnestoValue.anchor = GridBagConstraints.LINE_START;
		gbcForLblTipoInnestoValue.insets = new Insets(10, 10, 10, 10);
		this.infoPanel.add(this.lblTipoInnestoValue, gbcForLblTipoInnestoValue);
	}
	
}
