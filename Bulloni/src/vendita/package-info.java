/**
 * @author GiannettaGerardo
 * 
 * Package contenete le classi java per creare e gestire una vendita.
 * 
 * Interfacce contenute:
 * Vendita<T, E>
 * 
 * Classi contenute:
 * AbastractVendita<T, E>		(classe astratta che implementa l'interfaccia Vendita<T, E> e generalizza alcuni metodi 
 *                               e attributi per le vendite)
 * 
 * VenditaBulloni				(classe concreta che realizza la classe astratta AbstractVendita<MerceVenduta, Impiegato>) 
 *                              [Dipende fortemente dall'interfaccia Impiegato]
 *                              
 * MerceVenduta 				(classe associativa tra VenditaBulloni e Bullone)
 *                              [Dipende fortemente dall'Interfaccia Bullone]
 */
package vendita;