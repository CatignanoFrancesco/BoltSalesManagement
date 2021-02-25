package bulloni;

import java.sql.Date;	// classe per il tipo di dato Date

/**
 * Questa interfaccia contiene tutte le operazioni disponibili per tutti i tipi di bulloni.
 * 
 * @author Francesco Catignano
 *
 */

public interface Bullone {
	
	/**
	 * Restituisce il codice del bullone il codice del bullone.
	 * @return codice Il codice del bullone.
	 */
	public int getCodice();
	
	/**
	 * Restituisce la data di produzione del bullone.
	 * @return dataProduzione La data di produzione.
	 */
	public Date getDataProduzione();
	
	/**
	 * Restituisce il luogo di produzione del bullone.
	 * @return luogoProduzione Il luogo di produzione del bullone.
	 */
	public String getLuogoProduzione();
	
	/**
	 * Restituisce il peso del bullone, comprensivo del dado.
	 * @return peso Il peso del bullone.
	 */
	public double getPeso();
	
	/**
	 * Modifica il prezzo del bullone.
	 * @param prezzo Il nuovo prezzo del bullone.
	 */
	public void setPrezzo(double prezzo);
	
	/**
	 * Restituisce il prezzo del bullone.
	 * @return prezzo Il prezzo del bullone.
	 */
	public double getPrezzo();
	
	/**
	 * Restituisce il materiale con cui è stato costruito il bullone.
	 * @return materiale Il materiale del bullone.
	 */
	public Materiale getMateriale();
	
	/**
	 * Restituisce la lunghezza del bullone.
	 * @return lunghezza La lunghezza del bullone.
	 */
	public double getLunghezza();
	
	/**
	 * Restituisce il diametro della vite del bullone.
	 * @return diametroVite Il diametro della vite.
	 */
	public double getDiametroVite();
	
	/**
	 * Restituisce il diametro del dado del bullone.
	 * @return diametroDado Il diametro del dado del bullone.
	 */
	public double getDiametroDado();
	
	/**
	 * Restituisce il tipo di innesto del bullone (a croce, esagonale...).
	 * @return innesto Il tipo di innesto del bullone.
	 */
	public Innesto getInnesto();
	
	/**
	 * Restituisce il tipo di testa del bullone (quando disponibile).
	 * @return testa Il tipo di testa del bullone.
	 */
	public String getTesta();
	
	/**
	 * Confronta due oggetti e restituisce un valore che indica se sono uguali o meno.
	 * Il confronto si baserà sul codice del bullone.
	 * @param obj L'oggetto da confrontare con il bullone.
	 * @return true se i due oggetti sono uguali, flase altrimenti.
	 */
	public boolean equals(Object obj);
	
	/**
	 * Reimplementa il metodo hashCode() per restuire l'hash code dell'oggetto bullone.
	 * @return L'hash code dell'oggetto bullone.
	 */
	public int hashCode();
	
	/**
	 * Restituisce le informazioni sulla classe su cui si sta lavorando
	 * @return info Le informazioni da restituire.
	 */
	public String toString();
}
