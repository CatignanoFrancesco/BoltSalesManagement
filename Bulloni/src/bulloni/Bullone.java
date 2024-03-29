package bulloni;

import utility.Data;	// classe per il tipo di dato Data

import bulloni.exception.BulloneException;

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
	public Data getDataProduzione();
	
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
	 * @throws BulloneException L'eccezione sollevata quando il prezzo non rispetta le specifiche semantiche.
	 */
	public void setPrezzo(double prezzo) throws BulloneException;
	
	/**
	 * Restituisce il prezzo del bullone.
	 * @return prezzo Il prezzo del bullone.
	 */
	public double getPrezzo();
	
	/**
	 * Restituisce il materiale con cui e' stato costruito il bullone.
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
	 * Restituisce il tipo di testa del bullone (piatta, tonda...).
	 * In questo caso il metodo restituisce valore null perche' e' possibile che alcuni tipi
	 * di bulloni possano non avere la testa (ad esempio il bullone grano).
	 */
	public String getTesta();
	
	/**
	 * Restituisce un valore che rappresenta la possibilita' o meno di modificare un oggetto di questo tipo.
	 * @return eliminato Lo stato del bullone.
	 */
	public boolean isEliminato();
	
	/**
	 * Rende non disponibile l'oggetto bullone ad eventuali modifiche.
	 * In particolare porta a "true" lo stato dell'attributo "eliminato".
	 */
	public void elimina();
	
	/**
	 * Restituisce un array di stringhe contenente le informazioni generali di un bullone.
	 * Si differenzia dal metodo toString() perche' permette di avere singolarmente, sotto forma di stringa i valori degli attributi dei bulloni.
	 * Le informazioni ottenute rispettano quest'ordine:
	 * - tipo (la classe a cui appartiene);
	 * - codice;
	 * - data di produzione;
	 * - luogo di produzione;
	 * - il peso;
	 * - il prezzo;
	 * - il materiale;
	 * - la lunghezza;
	 * - il diametro della vite;
	 * - il diametro del dado;
	 * - il tipo di innesto.
	 * @return L'array di stringhe contenente le informazioni
	 */
	public String[] getInfo();
	
	/**
	 * Confronta due oggetti e restituisce un valore che indica se sono uguali o meno.
	 * Il confronto si basera' sul codice del bullone.
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
	
	/**
	 * Restituisce un clone del bullone.
	 * @return o Il clone dell'oggetto bullone.
	 */
	public Object clone();
}
