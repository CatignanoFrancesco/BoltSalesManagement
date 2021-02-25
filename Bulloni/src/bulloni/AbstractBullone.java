package bulloni;

import java.sql.Date;


/**
 * La seguente classe astratta implementa i metodi comuni a tutte le classi concrete.
 * Contiene anche i controlli sui valori ammessi dagli attributi al momento della creazione
 * di un oggetto di tipo Bullone.
 * @author fra
 *
 */
public abstract class AbstractBullone implements Bullone {
	/**
	 * Esprime la differenza di diametro tra la vite e il dado.
	 * In questo modo è possibile stabilire una dimensione corretta del diametro del dado
	 * a partire dal diametro della vite.
	 */
	private static final double DIFF_DIAMETRO_VITE_DADO = 0.01;
	
	private int codice;	// il codice identificativo del bullone
	private Date dataProduzione;
	private String luogoProduzione;
	private double peso;
	private double prezzo;
	private Materiale materiale;
	private double lunghezza;
	private double diametroVite;
	private double diametroDado;
	private Innesto innesto;	// L'innesto del bullone (a croce, esagonale...)

	
	@Override
	public int getCodice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getDataProduzione() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLuogoProduzione() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPeso() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPrezzo(double prezzo) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public double getPrezzo() {
		return 0;
	}

	@Override
	public Materiale getMateriale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getLunghezza() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiametroVite() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiametroDado() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Innesto getInnesto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTesta() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return null;
	}

}
