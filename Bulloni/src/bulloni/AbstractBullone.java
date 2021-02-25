package bulloni;

import java.sql.Date;


/**
 * La seguente classe astratta implementa i metodi comuni a tutte le classi concrete.
 * Contiene anche i controlli sui valori ammessi dagli attributi al momento della creazione
 * di un oggetto di tipo Bullone.
 * @author Catignano Francesco
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
	
	
	// COSTRUTTORE
	public AbstractBullone(int codice, Date dataProduzione, String luogoProduzione, double peso, double prezzo, Materiale materiale, double lunghezza, double diametroVite, Innesto innesto) {
		this.codice = codice;
		this.dataProduzione = dataProduzione;
		this.luogoProduzione = luogoProduzione;
		this.peso = peso;
		this.prezzo = prezzo;
		this.materiale = materiale;
		this.lunghezza = lunghezza;
		this.diametroVite = diametroVite;
		/*
		 * al diametro della vite è aggiunta la differenza tra il diametro della vite e del dado.
		 * In questo modo si ottiene una misura del diametro del dado coerente col diametro della vite del bullone.
		 */
		this.diametroDado = this.diametroVite + DIFF_DIAMETRO_VITE_DADO;
		this.innesto = innesto;
	}

	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public int getCodice() {
		return this.codice;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public Date getDataProduzione() {
		return this.dataProduzione;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public String getLuogoProduzione() {
		return this.luogoProduzione;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public double getPeso() {
		return this.peso;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public double getPrezzo() {
		return this.prezzo;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public Materiale getMateriale() {
		return this.materiale;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public double getLunghezza() {
		return this.lunghezza;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public double getDiametroVite() {
		return this.diametroVite;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public double getDiametroDado() {
		return this.diametroDado;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public Innesto getInnesto() {
		return this.innesto;
	}
	
	/**
	 * Restituisce il tipo di testa del bullone (piatta, tonda...).
	 * In questo caso il metodo restituisce valore null perchè è possibile che alcuni tipi
	 * di bulloni possano non avere la testa (ad esempio il bullone grano).
	 */
	@Override
	public String getTesta() {
		return null;
	}
	
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		// Controllo del riferimento
		if( this==obj ) {
			return true;
		}
		if( obj==null ) {
			return false;
		}
		
		// Controllo della classe
		if( getClass()!=obj.getClass() ) {
			return false;
		}
		
		// Controllo dei valori che rendono un oggetto uguale ad un altro
		Bullone other = (Bullone) obj;
		if( other.getCodice() == this.codice ) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + this.codice;
		
		return result;
	}
	
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public String toString() {
		return "Bullone";
	}

}
