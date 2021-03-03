package vendita;

import bulloni.Bullone;

/**
 * @author GiannettaGerardo
 *
 * Classe associativa tra VenditaBulloni e Bullone;
 * contiene i dettagli sulla merce venduta registrata nella vendita
 */
public class MerceVenduta implements Cloneable {
	
	/** Bullone venduto */
	private Bullone bullone;
	
	/** Numero di bulloni venduti del tipo registrato */
	private int numeroBulloni;
	
	/** Prezzo totale dei bulloni registrati di questo tipo */
	private double prezzoBulloni;
	
	/** Prezzo del bullone al momento della vendita */
	private double prezzoVenditaBullone;

	/**
	 * Costruttore della classe MerceVenduta con costruzione parzialmente automatizzata.
	 * Registra il prezzo di vendita del bullone in modo che se in futuro il prezzo per quel tipo
	 * di bullone dovesse cambiare, verrebbe ugualmente registrato il prezzo che aveva al momento
	 * della vendita; inoltre viene calcolato il prezzo totale dei bulloni di questo tipo
	 * 
	 * @param bullone tipo di bullone venduto
	 * @param numeroBulloni numero di bulloni venduti di questo tipo
	 */
	public MerceVenduta(Bullone bullone, int numeroBulloni) {
		this.bullone = bullone;
		this.numeroBulloni = numeroBulloni;
		this.prezzoVenditaBullone = this.bullone.getPrezzo();
		this.prezzoBulloni = this.prezzoVenditaBullone * this.numeroBulloni;
	}
	
	
	/**
	 * Costruttore della classe MerceVenduta con costruzione non automatizzata.
	 * Registra il prezzo di vendita del bullone in modo che se in futuro il prezzo per quel tipo
	 * di bullone dovesse cambiare, verrebbe ugualmente registrato il prezzo che aveva al momento
	 * della vendita
	 * 
	 * @param bullone tipo di bullone venduto
	 * @param numeroBulloni numero di bulloni venduti di questo tipo
	 * @param prezzoBulloni prezzo totale dei bulloni venduti di questo tipo
	 * @param prezzoVenditaBullone prezzo del bullone al momento della vendita
	 */
	public MerceVenduta(Bullone bullone, int numeroBulloni, double prezzoBulloni, double prezzoVenditaBullone) {
		this.bullone = bullone;
		this.numeroBulloni = numeroBulloni;
		this.prezzoVenditaBullone = prezzoVenditaBullone;
		this.prezzoBulloni = prezzoBulloni;
	}
	
	
	public int getNumeroBulloni() {
		return this.numeroBulloni;
	}
	
	
	public double getPrezzoBulloni() {
		return this.prezzoBulloni;
	}
	
	
	public double getPrezzoVenditaBullone() {
		return this.prezzoVenditaBullone;
	}
	
	
	public void setNumeroBulloni(int nuovoNumero) {
		this.numeroBulloni = nuovoNumero;
	}
	
	
	public void setPrezzoBulloni(double nuovoPrezzo) {
		this.prezzoBulloni = nuovoPrezzo;
	}


	@Override
	/**
	 * Metodo che calcola e restituisce l'hashCode dell'oggetto, rendendolo di fatto univoco
	 * 
	 * @return hashCode dell'oggetto
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.bullone.getCodice();
		return result;
	}


	@Override
	/**
	 * Metodo che determina se un oggetto passato in input è uguale all'istanza dell'oggetto con il quale
	 * si chiama il metodo stesso
	 * 
	 * @param obj oggetto da confrontare
	 * @return true se gli oggetti sono considerabili uguali, false altrimenti
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MerceVenduta other = (MerceVenduta) obj;
		if (other.bullone.getCodice() == this.bullone.getCodice()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Metodo che clona l'istanza dell'oggetto con il quale si chiama il metodo stesso
	 * 
	 * @return l'oggetto clonato
	 */
	public Object clone() {
		
		Object o = null;
		
		try {
			o = super.clone();
		}
		catch(CloneNotSupportedException e) {
			System.err.println("Clone non supportato.");
		}
		return o;
	}
	
	
	/**
	 * Metodo che ritorna una stringa contenente tutte le informazioni dettagliate dell'istanza dell'oggetto
	 * con il quale si chiama questo metodo
	 * 
	 * @return la stringa con tutte le informazioni dell'oggetto
	 */
	public String toString() {
		
		return "Numero di bulloni venduti: " + this.numeroBulloni + "\n" +
		       "Prezzo totale di questi bulloni: " + this.prezzoBulloni + "\n" +
			   "Prezzo del singolo bullone al momento della vendita: " + this.prezzoVenditaBullone + "\n" +
		       "Dettagli sul bullone in questione: " + "\n" + this.bullone.toString();
	}

}
