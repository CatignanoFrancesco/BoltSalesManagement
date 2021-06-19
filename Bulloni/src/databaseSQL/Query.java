package databaseSQL;

import java.util.ArrayList;

/**
 *
 * Classe che permette di creare e restituire query SQL tramite concatenazione di stringhe costanti e parametri in input.
 * La classe è pubblica e potrà essere usata da qualunque classe esterna che abbia bisogno di utilizzare query semplici.
 * 
 * @author GiannettaGerardo
 * 
 */
public class Query {
	
	private static final String WHERE = " where ";
	private static final String SELECT = "select ";
	private static final String ALL = "*";
	private static final String FROM = " from ";
	private static final String UPDATE = "update ";
	private static final String SET = " set ";
	private static final String INSERT = "insert into ";
	private static final String VALUES = " values ";
	private static final String DELETE = "delete from ";

	
	/**
	 * Il costruttore di Query rimane privato
	 */
	private Query() {}
	
	
	/**
	 * Metodo che ritorna una query SQL di tipo SELECT molto semplice
	 * 
	 * @param table tabella del database
	 * @return una query SQL di tipo SELECT
	 */
	public static String getSimpleSelect(String table) {
		return SELECT + ALL + FROM + table;
	}
	
	
	/**
	 * Metodo che ritorna una query SQL di tipo SELECT EQUI JOIN molto semplice
	 * 
	 * @param table1 prima tabella del database
	 * @param table2 seconda tabella del database
	 * @param field1 campo della prima tabella del database
	 * @param field2 campo della seconda tabella del database
	 * @return una query SQL di tipo SELECT EQUI JOIN
	 */
	public static String getSimpleSelectEquiJoin(String table1, String table2, String field1, String field2) {
		return SELECT + ALL + FROM + table1 + ", " + table2 + WHERE + table1+"."+field1 + "=" + table2+"."+field2;
	} 
	
	
	/**
	 * Metodo che ritorna una query SQL di tipo UPDATE che agisce su tutte le tuple di un certo campo
	 * 
	 * @param table tabella del database
	 * @param field campo della tabella del database
	 * @param value valore del campo della tabella
	 * @return una query SQL di tipo UPDATE
	 */
	public static String getSimpleUpdate(String table, String field, String value) {
		return UPDATE + table + SET + field + "=" + "'"+value+"'";
	}
	
	
	/**
	 * Metodo che ritorna una query SQL di tipo UPDATE che dovrebbe agire su una tupla precisa tramite la propria chiave
	 * 
	 * @param table tabella del database
	 * @param field campo della tabella del database
	 * @param value valore del campo della tabella 
	 * @param keyField campo chiave
	 * @param keyValue valore chiave
	 * @return una query SQL di tipo UPDATE che dovrebbe aggiornare una singola tupla precisa, riconosciuta tramite l'uso di 1 chiave
	 */
	public static String getSimpleUpdateByKey(String table, String field, String value, String keyField, String keyValue) {
		
		String query = getSimpleUpdate(table, field, value);
		
		return query + WHERE + keyField + "=" + "'"+keyValue+"'";
	}
	
	
	/**
	 * Metodo che ritorna una query SQL di tipo UPDATE che dovrebbe agire su una tupla precisa tramite le 2 proprie chiavi
	 * 
	 * @param table tabella del database
	 * @param field campo della tabella del database
	 * @param value valore del campo della tabella 
	 * @param keyField1 primo campo chiave
	 * @param keyValue1 primo valore chiave
	 * @param keyField2 secondo campo chiave
	 * @param keyValue2 secondo valore chiave
	 * @return una query SQL di tipo UPDATE che dovrebbe aggiornare una singola tupla precisa, riconosciuta tramite l'uso di 2 chiavi
	 */
	public static String getSimpleUpdateByDoubleKey(String table, String field, String value, String keyField1, String keyValue1, String keyField2, String keyValue2) {
		
		String query = getSimpleUpdateByKey(table, field, value, keyField1, keyValue1);
		
		return query + " and " + keyField2 + "=" + "'"+keyValue2+"'"; 
	}
	
	
	/**
	 * Metodo che ritorna una query SQL di tipo INSERT
	 * 
	 * @param table tabella del database
	 * @param values valori da inserire nella tabella
	 * @return una query SQL di tipo INSERT
	 */
	public static String getSimpleInsert(String table, String[] values) {
		
		String risultato = INSERT + table + VALUES + "(";
		
		for (int i = 0; i < values.length; i++) {
			
			if (i == (values.length - 1))
				risultato += "'" + values[i] + "')";
			else
				risultato += "'" + values[i] + "', ";
		}
		
		// se l'array di stringhe inserito in input fosse vuoto, alla query creata mancherebbe una parentesi tonda
		if ((risultato.indexOf("(") == -1) || (risultato.indexOf(")") == -1)) {
			risultato = INSERT + table + VALUES + "()";
		}
		
		return risultato;
	}
	
	
	/**
	 * Metodo che ritorna una query SQL di tipo INSERT che accetta tuple multiple
	 * 
	 * @param table tabella del database
	 * @param values valori da inserire nella tabella
	 * @return una query SQL di tipo INSERT
	 */
	public static String getInsertMultipli(String table, ArrayList<String[]> values) {
		
		String risultato = INSERT + table + VALUES;
		int i = 0;
		
		for (i = 0; i < values.size(); i++) {
			
			String[] stringhe = values.get(i);
			
			for (int j = 0; j < stringhe.length; j++) {
				
				if ((j == (stringhe.length - 1)) && (j == 0))
					if (i == (values.size() - 1))
						risultato += "('" + stringhe[j] + "')";
					else
						risultato += "('" + stringhe[j] + "'), ";
				
				else if (j == (stringhe.length - 1)) {
					
					if (i == (values.size() - 1))
						risultato += "'" + stringhe[j] + "')";
					else
						risultato += "'" + stringhe[j] + "'), ";
				}
				else if (j == 0)
					risultato += "('" + stringhe[j] + "', ";
				else
					risultato += "'" + stringhe[j] + "', ";
			}
		}
		
		// se l'ArrayList inserito in input fosse vuoto, alla query creata mancherebbero delle parentesi tonde
		if ((risultato.indexOf("(") == -1) || (risultato.indexOf(")") == -1)) {
			risultato = INSERT + table + VALUES + "()";
		}
		
		return risultato;
	}
	
	
	/**
	 * Metodo che ritorna una query SQL di tipo DELETE che agisce in base al valore di un certo campo
	 * 
	 * @param table tabella del database
	 * @param field campo della tabella del database
	 * @param value valore del campo della tabella
	 * @return una query SQL di tipo DELETE
	 */
	public static String getSimpleDelete(String table, String field, String value) {
		return DELETE + table + WHERE + field + "=" + "'"+value+"'";
	}
	

}
