package bulloni;

import utility.Data;
import bulloni.exception.BulloneException;
import bulloni.exception.MsgErrore;


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
	 * In questo modo e' possibile stabilire una dimensione corretta del diametro del dado
	 * a partire dal diametro della vite.
	 */
	private static final double DIFF_DIAMETRO_VITE_DADO = 0.01;
	/**
	 * La data minima accettabile per la produzione di un bullone.
	 */
	private static final Data MIN_DATA = new Data(01, Data.GENNAIO, 1920);
	/**
	 * La data massima accettabile per la produzione di un bullone.
	 * L'attributo non e' final perchè la data puo' cambiare.
	 */
	private static Data MAX_DATA = Data.getDataAttuale();
	private static final double MIN_PESO = 1.5;	// Espresso in grammi
	private static final double MAX_PESO = 15.0;
	private static final double MIN_PREZZO = 0.5;
	private static final double MAX_PREZZO = 2.5;
	/**
	 * Lunghezza minima della vite espressa in mm.
	 */
	private static final double MIN_LUNGHEZZA = 15;
	private static final double MAX_LUNGHEZZA = 200;
	/**
	 * Dimensione minima del diametro della vite di un bullone espressa in mm.
	 */
	private static final double MIN_DIAMETRO_VITE = 4;
	private static final double MAX_DIAMETRO_VITE = 10;
	
	private int codice;	// Il codice identificativo del bullone
	private Data dataProduzione;
	private String luogoProduzione;
	private double peso;
	private double prezzo;
	private Materiale materiale;
	private double lunghezza;
	private double diametroVite;
	private double diametroDado;
	private Innesto innesto;	// L'innesto del bullone (a croce, esagonale...)
	/**
	 * Questo attributo serve per controllare che il bullone sia disponibile o meno a modifiche (se e' eliminato oppure no).
	 * Si preferisce all'eliminazione vera e propria dell'oggetto perchè in questo modo sara' comunque possibile accedere
	 * alle informazioni del bullone senza pero' modificarne il suo stato.
	 */
	private boolean eliminato = false;
	
	
	// COSTRUTTORE
	/**
	 * Costruisce un bullone a partire dai parametri forniti in input.
	 * @param codice Il codice identificativo del bullone.
	 * @param dataProduzione La data di produzione del bullone.
	 * @param luogoProduzione Il luogo di produzione del bullone.
	 * @param peso Il peso del bullone, comprensivo del dado.
	 * @param prezzo Il prezzo del bullone.
	 * @param materiale Il materiale di cui il bullone e' fatto.
	 * @param lunghezza La lunghezza della vite del bullone.
	 * @param diametroVite Il diametro della vite del bullone.
	 * @param innesto Il tipo di innesto (a croce, torx...).
	 * 
	 * @throws BulloneException L'eccezione sollevata quando non sono rispettate le specifiche semantiche.
	 */
	public AbstractBullone(int codice, Data dataProduzione, String luogoProduzione, double peso, double prezzo, Materiale materiale, double lunghezza, double diametroVite, Innesto innesto) throws BulloneException {
		this.codice = codice;	// Il codice non ha bisogno di controllo, poichè sono ammessi tutti i valori.
		
		// Se la data non e' corretta, viene sollevata un'eccezione.
		if( dataCorretta(dataProduzione) ) {
			this.dataProduzione = (Data) dataProduzione.clone();	 // Viene assegnato un clone per evitare modifiche.
		} else {
			throw new BulloneException(MsgErrore.DATA_NON_VALIDA, new BulloneException());
		}
		
		this.luogoProduzione = luogoProduzione;
		
		// Se il peso non e' corretto, viene sollevata un'eccezione.
		if( pesoCorretto(peso) ) {
			this.peso = peso;
		} else {
			throw new BulloneException(MsgErrore.PESO_NON_VALIDO, new BulloneException());
		}
		
		// Se il prezzo non e' corretto viene sollevata un'eccezione.
		if( prezzoCorretto(prezzo) ) {
			this.prezzo = prezzo;
		} else {
			throw new BulloneException(MsgErrore.PREZZO_NON_VALIDO, new BulloneException());
		}
		
		this.materiale = materiale;
		
		// Se la lunghezza non e' corretta, viene sollevata un'eccezione.
		if( lunghezzaCorretta(lunghezza) ) {
			this.lunghezza = lunghezza;
		} else {
			throw new BulloneException(MsgErrore.LUNGHEZZA_NON_VALIDA, new BulloneException());
		}
		
		// Se il diametro della vite non e' corretto, viene sollevata un'eccezione, altrimenti vengono inizializzati sia i valori relativi al diametro della vite che al diametro del dado.
		if( diametroViteCorretto(diametroVite) ) {
			this.diametroVite = diametroVite;
			/*
			 * Al diametro della vite e' aggiunta la differenza tra il diametro della vite e del dado.
			 * In questo modo si ottiene una misura del diametro del dado coerente col diametro della vite del bullone.
			 */
			this.diametroDado = this.diametroVite + DIFF_DIAMETRO_VITE_DADO;
		} else {
			throw new BulloneException(MsgErrore.DIAMETRO_VITE_NON_VALIDO, new BulloneException());
		}
		
		this.innesto = innesto;
	}

	
	// OPERAZIONI
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
	public Data getDataProduzione() {
		return (Data) this.dataProduzione.clone();
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
	public void setPrezzo(double prezzo) throws BulloneException {
		// Se il bullone non e' stato eliminato, si puo' procedere alla modifica, altrimenti viene sollevata un'eccezione.
		if( eliminato==false ) {
			// Se il prezzo non e' corretto viene sollevata un'eccezione.
			if( prezzoCorretto(prezzo) ) {
				this.prezzo = prezzo;
			} else {
				throw new BulloneException(MsgErrore.PREZZO_NON_VALIDO, new BulloneException());
			}
		} else {
			throw new BulloneException(MsgErrore.BULLONE_ELIMINATO, new BulloneException());
		}
		
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
	public boolean isEliminato() {
		return this.eliminato;
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public void elimina() {
		this.eliminato = true;
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
	
	
	/**
	 * Operazione a servizio del costruttore per controllare se la data di produzione di un bullone rientra nel range prestabilito.
	 * @param data La data da controllare.
	 * @return true se la data di produzione e' corretta, false altrimenti.
	 */
	private boolean dataCorretta(Data data) {
		return data.compareTo(MIN_DATA)>=0 && data.compareTo(MAX_DATA)<0;
	}
	
	/**
	 * Operazione a servizio del costruttore per controllare se il peso di un bullone rientra nel range prestabilito.
	 * @param peso Il peso del bullone da controllare.
	 * @return true se il peso e' corretto, false altrimenti.
	 */
	private boolean pesoCorretto(double peso) {
		return peso>=MIN_PESO && peso<=MAX_PESO;
	}
	
	/**
	 * Operazione a servizio del costruttore o dell'operazione setPrezzo() per controllare se il prezzo di un bullone
	 * rientra nel range prestabilito.
	 * @param prezzo Il prezzo di un bullone da controllare.
	 * @return true se il prezzo e' corretto, false altrimenti.
	 */
	private boolean prezzoCorretto(double prezzo) {
		return prezzo>=MIN_PREZZO && prezzo<=MAX_PREZZO;
	}
	
	/**
	 * Operazione a servizio del costruttore per controllare se la lunghezza della vite di un bullone
	 * rientra nel range prestabilito.
	 * @param lunghezza La lunghezza di un bullone da controllare.
	 * @return true se la lunghezza e' corretto, false altrimenti.
	 */
	private boolean lunghezzaCorretta(double lunghezza) {
		return lunghezza>=MIN_LUNGHEZZA && lunghezza<=MAX_LUNGHEZZA;
	}
	
	/**
	 * Operazione a servizio del costruttore per controllare se il diametro della vite di un bullone rientra nel range prestabilito.
	 * @param diametroVite Il diametro della vite del bullone da controllare.
	 * @return true se il diametro e' corretto, false altrimenti.
	 */
	private boolean diametroViteCorretto(double diametroVite) {
		return diametroVite>=MIN_DIAMETRO_VITE && diametroVite<=MAX_DIAMETRO_VITE;
	}

}
