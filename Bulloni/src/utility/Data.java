package utility;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

/**
 * 
 * Questa classe modella il tipo Data a partire da tipi gia' esistenti.
 * 
 * @author Catignano Francesco
 */
public class Data implements Cloneable, Comparable<Data> {
	public static final int GENNAIO = 1;
	public static final int FEBBRAIO = 2;
	public static final int MARZO = 3;
	public static final int APRILE = 4;
	public static final int MAGGIO = 5;
	public static final int GIUGNO = 6;
	public static final int LUGLIO = 7;
	public static final int AGOSTO = 8;
	public static final int SETTEMBRE = 9;
	public static final int OTTOBRE = 10;
	public static final int NOVEMBRE = 11;
	public static final int DICEMBRE = 12;
	
	private LocalDate localdate;
	
	
	/* 
	 * -------------
	 *  COSTRUTTORI
	 * -------------
	 */
	/**
	 * Costruisce il tipo data partendo da un giorno del mese, un mese e un anno.
	 * E' buona norma chiamare il costruttore all'interno del blocco try-catch in modo da evitare eventuali arresti del programma in caso di data errata.
	 * @param giornoDelMese Il giorno del mese.
	 * @param mese Il mese dell'anno. Puo' ricevere il parametro sia esplicitamente, che sotto forma di costante.
	 * @param anno
	 */
	public Data(int giornoDelMese, int mese, int anno) {
		this.localdate = LocalDate.of(anno, mese, giornoDelMese);
	}
	
	/**
	 * Costruisce il tipo data partendo da un tipo java.sql.Date.
	 * Utile quando si vuole creare una data, partendo da un risultato di una query in un DB.
	 * @param data La data presa in input.
	 */
	public Data(Date data) {
		this.localdate = new Date(data.getTime()).toLocalDate();
	}
	
	
	/* 
	 * -----------------
	 *  METODI PUBBLICI
	 * -----------------
	 */
	/**
	 * Questo metodo restituisce l'anno.
	 * @return L'anno.
	 */
	public int getAnno() {
		return this.localdate.getYear();
	}
	
	/**
	 * Questo metodo restituisce il mese.
	 * @return Il mese.
	 */
	public int getMese() {
		return this.localdate.getMonthValue();
	}
	
	/**
	 * Questo metodo restituisce il giorno del mese.
	 * @return Il giorno del mese.
	 */
	public int getGiorno() {
		return this.localdate.getDayOfMonth();
	}
	
	/**
	 * Restituisce un oggetto di tipo data avvalorato con il giorno, il mese e l'anno corrispondenti alla data attuale.
	 * @return La data attuale.
	 */
	public static Data getDataAttuale() {
		LocalDate ldDataAttuale = LocalDate.now();
		
		return new Data(ldDataAttuale.getDayOfMonth(), ldDataAttuale.getMonthValue(), ldDataAttuale.getYear());
	}
	
	/**
	 * Restituisce la data nel tipo LocalDate.
	 * @return localdate La data nel tipo LocalDate.
	 */
	public LocalDate toLocalDate() {
		return this.localdate;
	}
	
	/**
	 * Restituisce la data nel tipo java.sql.Date. E' utile soprattutto quando bisogna memorizzare la data in un Database.
	 * @return La data nel tipo java.sql.Date
	 */
	public Date toSqlDate() {
		return Date.valueOf(localdate);
	}
	
	/**
	 * Restituisce la data sotto forma di stringa seguendo il pattern dd/MM/yyyy.
	 * @return La data sotto forma di stringa.
	 */
	public String toFormattedDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return this.localdate.format(formatter);
	}
	
	/**
	 * Permette la modifica della data.
	 * @param giornoDelMese Il giorno del mese.
	 * @param mese Il mese (puo' essere ricevuto sia esplicitamente, che sotto forma di costante).
	 * @param anno L'anno.
	 */
	public void set(int giornoDelMese, int mese, int anno) {
		try {
			this.localdate = LocalDate.of(anno, mese, giornoDelMese);
		}
		catch(DateTimeException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Permette di modificare la data nel tipo localdate, partendo da una data nel tipo java.sql.Date
	 * @param data La data da impostare
	 */
	public void setFromSql(Date data) {
		try {
			this.localdate = new Date(data.getTime()).toLocalDate();
		}
		catch(DateTimeException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Metodo per confrontare due date.
	 * @param d La data da confrontare
	 * @return Restituisce un numero maggiore di 0 se l'istanza è maggiore della data ricevuta come parametro, restituisce un numero negativo se l'istanza è minore della data ricevuta come parametro e restituisce 0 se sono uguali.
	 */
	@Override
	public int compareTo(Data d) {
		if( this.localdate.getYear() != d.getAnno() ) {	// Confronto dell'anno
			return this.localdate.getYear() - d.getAnno();
		}
		else if( this.localdate.getMonthValue() != d.getMese() ) {	// Confronto del mese
			return this.localdate.getMonthValue() - d.getMese();
		}
		else if( this.localdate.getDayOfMonth() != d.getGiorno() ) {	// Confronto del giorno.
			return this.localdate.getDayOfMonth() - d.getGiorno();
		}
		return 0;
	}
	
	@Override
	/**
	 * Controlla che questa data e quella ricevuta come parametro siano uguali.
	 * @param o L'oggetto da confrontare.
	 * @return true se le date sono uguali, false altrimenti.
	 */
	public boolean equals(Object o) {
		// Controllo del riferimento
		if(o==null) {
			return false;
		}
		if(this==o) {
			return true;
		}
		
		// Controllo delle classi di appartenenza
		if(this.getClass()!=o.getClass()) {
			return false;
		}
		
		// Controllo dell'oggetto Data
		Data other = (Data) o;
		if(this.getAnno()==other.getAnno() && this.getMese()==other.getMese() && this.getGiorno()==other.getGiorno()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Clona l'istanza di un oggetto. Utile per evitare che la restituzione di un oggetto privato di tipo Data, ne permetta la modifica.
	 * @return obj L'oggetto clonato.
	 */
	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch(CloneNotSupportedException e) {
			System.err.println("Non puo' essere clonato!");
		}
		
		return obj;
	}
	
}
