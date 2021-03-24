package gestori.gestorevendite;

/**
 * @author GiannettaGerardo
 *
 * Classe che permette di mappare facilmente le vendite effettuate da un impiegato in un certo anno;
 * Verrà utilizzata per creare le chiavi di un HashMap nel GestoreVendita
 */
public class ChiaveImpiegatoAnno {

	private int matricolaImpiegato;
	private int annoVendita;
	
	public ChiaveImpiegatoAnno(int matricola, int anno) {
		this.matricolaImpiegato = matricola;
		this.annoVendita = anno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + annoVendita;
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
		ChiaveImpiegatoAnno other = (ChiaveImpiegatoAnno) obj;
		if (annoVendita != other.annoVendita)
			return false;
		if (matricolaImpiegato != other.matricolaImpiegato)
			return false;
		return true;
	}
	
	
}
