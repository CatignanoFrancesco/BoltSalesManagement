package gestori.gestorevendite;

import utility.Data;

/**
 * @author GiannettaGerardo
 *
 * Classe che permette di mappare facilmente le vendite effettuate da un impiegato in una certa data;
 * Verrà utilizzata per creare le chiavi di un HashMap nel GestoreVendita
 */
public class ChiaveImpiegatoData {

	private int matricolaImpiegato;
	private Data dataVendita;
	
	public ChiaveImpiegatoData(int matricola, Data data) {
		this.matricolaImpiegato = matricola;
		this.dataVendita = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataVendita == null) ? 0 : (dataVendita.getAnno() + dataVendita.getMese() + dataVendita.getGiorno()));
		result = prime * result + matricolaImpiegato;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiaveImpiegatoData other = (ChiaveImpiegatoData) obj;
		if (dataVendita == null) {
			if (other.dataVendita != null)
				return false;
		} else if (dataVendita.compareTo(other.dataVendita) != 0)
			return false;
		if (matricolaImpiegato != other.matricolaImpiegato)
			return false;
		return true;
	}
	
	
}
