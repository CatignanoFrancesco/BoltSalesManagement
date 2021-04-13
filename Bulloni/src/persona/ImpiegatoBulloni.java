/**
 * 
 */
package persona;

import persona.exception.ExceptionAnagraficaErrata;
import persona.exception.ExceptionImpiegato;
import persona.exception.MsgExceptionImpiegato;
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
	private static final int MIN_BULLONI_VENDIBILI_ANNUALMENTE = 365;// si suppone che ogni impiegato deve vendere
																		// almeno 365 bulloni l'anno (1 al di circa)
																		// se si sceglie di assegnare i bulloni in
																		// maniera arbitraria

	private int bulloniVendibiliAnnualmente;// settabili arbitrariamente o in base al fatto che ogni impiegato ne puo
											// vendere al massimo 500 al di
											// ad ogno modo non sara possibile assegnare piu di n bulloni
											// con n=BULLONI_VENDIBILI_GIORNALMENTE*this.giornateLAvorativeAnnuali
	
	
	/**
	 * costruttore da usare qundo devo instanziare in impiegato che prendere i valori da un altro impiegato
	 */
	public ImpiegatoBulloni() {
		
		super();
	};
	
	
	/**
	 * costruttore che setta il numero di bulloni vendibili annualmente in maniera
	 * arbitraria tramite il parametro apposito, da usare quando s'instanzia un impiegato con dati fatti inserire dall'utente
	 * poiche l'id viene assegnata in maniera automatica dal software
	 * 
	 * @param nome                        nome impiegato
	 * @param cognome                     cognome impiegato
	 * @param sesso                       sesso impiegato
	 * @param dataNascita                 data nascita impiegato
	 * @param giornateLavorativeAnnuali   giornate lavorative annuali impiegato
	 * @param stipendioMensile            stipendio mensile impiegato
	 * @param bulloniVendibiliAnnualmente massimo di bulloni vendibili annualmente
	 *                                    da un impiegato
	 * @exception ExceptionAnagraficaErrata sollevata per errori di valore di
	 *                                      attributi proprio dell'anagrafica
	 * @exception ExceptionImpiegato        sollevata per errori di valore di
	 *                                      attributi propri impiegatoGenerale e
	 *                                      impiegatoBulloni
	 */
	public ImpiegatoBulloni(String nome, String cognome, char sesso, Data dataNascita,
			int giornateLavorativeAnnuali, float stipendioMensile, int bulloniVendibiliAnnualmente)
			throws ExceptionAnagraficaErrata, ExceptionImpiegato {

		super(nome, cognome, sesso, dataNascita, giornateLavorativeAnnuali, stipendioMensile);

		if (!checkMaxNumeroBulloniVendibiliAnnualmente(bulloniVendibiliAnnualmente))
			// per vendere tutti i bulloni dovrebbe vendere piu di 500 bulloni al di
			// cosa non concesso dalle specifiche per cui sollevo un eccezione

			throw new ExceptionImpiegato(MsgExceptionImpiegato.ECCESSO_BULLONI_ASSEGNATI, new ExceptionImpiegato());
		
		else if(bulloniVendibiliAnnualmente < MIN_BULLONI_VENDIBILI_ANNUALMENTE)
			
			throw new ExceptionImpiegato(MsgExceptionImpiegato.POCHI_BULLONI_ASSEGNATI, new ExceptionImpiegato());

		else

			this.bulloniVendibiliAnnualmente = bulloniVendibiliAnnualmente;
	}

	/**
	 * costruttore che setta il numero di bulloni vendibili annualmente in base alle
	 * giornate lavorative dell'impiegato, da usare quando si instanzia un impiegato con dati presi dall'utente
	 * poiche l'id viene assegnato automatcamente dal software e l'attributo isAssunto avrà dei volori di defoult
	 * 
	 * @param nome                      nome impiegato
	 * @param cognome                   cognome impiegato
	 * @param sesso                     sesso impiegato
	 * @param dataNascita               data nascita impiegato
	 * @param giornateLavorativeAnnuali giornate lavorative annuali impiegato
	 * @param stipendioMensile          stipendio mensile impiegato
	 * @exception ExceptionAnagraficaErrata sollevata per errori di valore di
	 *                                      attributi proprio dell'anagrafica
	 * @exception ExceptionImpiegato        sollevata per errori di valore di
	 *                                      attributi propri impiegatoGenerale e
	 *                                      impiegatoBulloni
	 */
	public ImpiegatoBulloni(String nome, String cognome, char sesso, Data dataNascita,
			int giornateLavorativeAnnuali, float stipendioMensile)
			throws ExceptionAnagraficaErrata, ExceptionImpiegato {

		super(nome, cognome, sesso, dataNascita, giornateLavorativeAnnuali, stipendioMensile);
		this.bulloniVendibiliAnnualmente = BULLONI_VENDIBILI_GIORNALMENTE * giornateLavorativeAnnuali;//calcolo i bulloni che puoi vendere un determinato impiegato
																										//in base alle giornate assegnatoli
	}

	/**
	 * costruttore che setta il numero di bulloni vendibili annualmente in maniera
	 * arbitraria tramite il parametro apposito, da usare quando s'instanzia un impiegato con dati letti dal db
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
	 * @param isAssunto					  segnale se un impiegato risulta assunto o meno
	 * @exception ExceptionAnagraficaErrata sollevata per errori di valore di
	 *                                      attributi proprio dell'anagrafica
	 * @exception ExceptionImpiegato        sollevata per errori di valore di
	 *                                      attributi propri impiegatoGenerale e
	 *                                      impiegatoBulloni
	 */
	public ImpiegatoBulloni(String nome, String cognome, char sesso, Data dataNascita, int id,
			int giornateLavorativeAnnuali, float stipendioMensile, int bulloniVendibiliAnnualmente, boolean isAssunto)
			throws ExceptionAnagraficaErrata, ExceptionImpiegato {

		super(nome, cognome, sesso, dataNascita, id, giornateLavorativeAnnuali, stipendioMensile, isAssunto);

		if (!checkMaxNumeroBulloniVendibiliAnnualmente(bulloniVendibiliAnnualmente))
			// per vendere tutti i bulloni dovrebbe vendere piu di 500 bulloni al di
			// cosa non concesso dalle specifiche per cui sollevo un eccezione

			throw new ExceptionImpiegato(MsgExceptionImpiegato.ECCESSO_BULLONI_ASSEGNATI, new ExceptionImpiegato());
		
		else if(bulloniVendibiliAnnualmente < MIN_BULLONI_VENDIBILI_ANNUALMENTE)
			
			throw new ExceptionImpiegato(MsgExceptionImpiegato.POCHI_BULLONI_ASSEGNATI, new ExceptionImpiegato());

		else

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
	 * @throws ExceptionImpiegato sollevata se il numero di bulloni supera soglia n
	 *                            con
	 *                            n=BULLONI_VENDIBILI_GIORNALMENTE*this.giornateLAvorativeAnnuali
	 */
	public void setBulloniVendibiliAnnualmente(int bulloniVendibiliAnnualmente) throws ExceptionImpiegato {

		if (!checkMaxNumeroBulloniVendibiliAnnualmente(bulloniVendibiliAnnualmente))
			// per vendere tutti i bulloni assegnatogli dovrebbe vendere piu di 500 bulloni al di cosa non concesso dalle
			// specifiche per cui sollevo un eccezione

			throw new ExceptionImpiegato(MsgExceptionImpiegato.ECCESSO_BULLONI_ASSEGNATI, new ExceptionImpiegato());
		
		else if(bulloniVendibiliAnnualmente < MIN_BULLONI_VENDIBILI_ANNUALMENTE)
			
			throw new ExceptionImpiegato(MsgExceptionImpiegato.POCHI_BULLONI_ASSEGNATI, new ExceptionImpiegato());

		else

			this.bulloniVendibiliAnnualmente = bulloniVendibiliAnnualmente;
	}	

	/**
	 * controllo se il numero di bulloni che sto assegnando ad un impiegato rispetti
	 * la soglia di n con
	 * n=BULLONI_VENDIBILI_GIORNALMENTE*this.giornateLAvorativeAnnuali
	 * 
	 * @param bulloniVendibiliAnnualmente
	 * @return ret true se il numero di bulloni non supera la soglia n, false
	 *         altrimenti
	 */
	private boolean checkMaxNumeroBulloniVendibiliAnnualmente(int bulloniVendibiliAnnualmente) {

		boolean ret = true;// valore di ritorno

		if (bulloniVendibiliAnnualmente > (BULLONI_VENDIBILI_GIORNALMENTE * this.getGiornateLavorativeAnnuali()))

			ret = false;

		return ret;

	}

	/**
	 * converte l'oggetto impiegatoBulloni in stringa
	 * @return l'impiegato bulloni convertito
	 */
	@Override
	public String toString() {
		
		return super.toString() + "\nBulloni vendibili annualmente: " + this.getBulloniVendibiliAnnualmente() + "\n";
	}

}
