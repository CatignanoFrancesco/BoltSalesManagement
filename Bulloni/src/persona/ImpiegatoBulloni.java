/**
 * 
 */
package persona;

import utility.Data;

/**
 * @author Francolino Flavio Domenico
 * 
 *         classe derivata da impiegato generale che contine gli attributi e le
 *         operazioni uniche per un impiegato che vende sollo bulloni
 *
 */
public class ImpiegatoBulloni extends ImpiegatoGenerale {

	private static final int BULLONI_VENDIBILI_GIORNALMENTE = 500;

	private int bulloniVendibiliAnnualmente;// settabili arbitrariamente o in base al fatto che ogni impiegato ne puo
											// vendere al massimo 500 al di

	/**
	 * costruttore che setta il numero di bulloni vendibili annalmente in base alle
	 * giornate lavorative dell'impiegato
	 * 
	 * @param nome                      nome impiegato
	 * @param cognome                   cognome impiegato
	 * @param sesso                     sesso impiegato
	 * @param dataNascita               data nascita impiegato
	 * @param id                        id impiegato
	 * @param giornateLavorativeAnnuali giornate lavorative annuali impiegato
	 * @param stipendioMensile          stipendio mensile impiegato
	 */
	public ImpiegatoBulloni(String nome, String cognome, char sesso, Data dataNascita, int id,
			int giornateLavorativeAnnuali, float stipendioMensile) {

		super(nome, cognome, sesso, dataNascita, id, giornateLavorativeAnnuali, stipendioMensile);
		this.bulloniVendibiliAnnualmente = BULLONI_VENDIBILI_GIORNALMENTE * giornateLavorativeAnnuali;
	}

	/**
	 * costruttore che setta il numero di bulloni vendibili annualmente in maniera
	 * arbitraria tramite il parametro apposito
	 * 
	 * @param nome                        nome impiegato
	 * @param cognome                     cognome impiegato
	 * @param sesso                       sesso impiegato
	 * @param dataNascita                 data nascita impiegato
	 * @param id                          id impiegato
	 * @param giornateLavorativeAnnuali   giornate lavorative annuali impiegato
	 * @param stipendioMensile            stipendio mensile impiegato
	 * @param bulloniVendibiliAnnualmente massimo di bulloni vendibili annualmente
	 *                                    da un impiegato
	 */
	public ImpiegatoBulloni(String nome, String cognome, char sesso, Data dataNascita, int id,
			int giornateLavorativeAnnuali, float stipendioMensile, int bulloniVendibiliAnnualmente) {

		super(nome, cognome, sesso, dataNascita, id, giornateLavorativeAnnuali, stipendioMensile);
		this.bulloniVendibiliAnnualmente = bulloniVendibiliAnnualmente;
	}

	/**
	 * ritorna il numero di bulloni vendibili giornalmente da ogni impiegato
	 * 
	 * @return BULLONI_VENDIBILI_GIORNALMENTE il numero di bulloni vendibili
	 *         giornarlmente
	 */
	public static int getBulloniVendibiliGiornalmente() {
		return BULLONI_VENDIBILI_GIORNALMENTE;
	}

	/**
	 * ritorna il numero di bulloni vendibili da un impiegato che vende bulloni
	 * 
	 * @return bulloniVendibiliAnnaulemente il numero di bulloni vendibili nell'arco
	 *         di un anno da un impiegato che vende bulloni
	 */
	public int getBulloniVendibiliAnnualmente() {

		return bulloniVendibiliAnnualmente;
	}

	/**
	 * setta un nuovo valore per i bulloni vendibili annualmente
	 * 
	 * @param bulloniVendibiliAnnualmente il nuovo valore di bulloni vendibili
	 *                                    annualmente da un impiegato
	 */
	public void setBulloniVendibiliAnnualmente(int bulloniVendibiliAnnualmente) {

		this.bulloniVendibiliAnnualmente = bulloniVendibiliAnnualmente;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "\nbulloni vendibili annualmente: " + this.getBulloniVendibiliAnnualmente() + "\n";
	}

}
