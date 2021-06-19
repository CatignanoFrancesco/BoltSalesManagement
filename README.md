# CS_IT_2021_gruppo30
Caso di studio appello Programmazione II 2020-2021 

### SEZIONE "VEDI BULLONI"
In questa sezione, è possibile visualizzare una lista di tutti i bulloni presenti nel database. Vengono mostrate alcune informazioni rapide come il codice, il tipo di bullone e il suo prezzo. A destra di ogni riga della lista sono presenti tre tasti: _Informazioni_, _Modifica_ e _Elimina_.
- Selezionando _Informazioni_, viene aperta una finestra contenente tutte le informazioni complete sul bullone in questione (data e luogo di produzione, peso, diametro vite ecc.).
- Selezionando _Modifica_ si accede ad una finestra che permette di modificare alcuni aspetti del bullone. Attualmente è possibile modificare solamente il prezzo del bullone. Una volta effettuate tutte le modifiche appropriate, attraverso i campi appositi, si può cliccare il tasto per confermare la modifica e visualizzare le informazioni aggiornate.
- Selezionando _Elimina_ si potrà eliminare il bullone, in modo da non renderlo più visibile nell'interfaccia. Prima di eliminarlo, il programma chiede all'utente una conferma sull'azione che sarà IRREVERSIBILE. Dopo la cancellazione non sarà possibile vendere o modificare il bullone, ma sarà possibile visualizzare le informazioni su di esso solamente se è stato precedentemente venduto. Le informazioni potranno essere visualizzate solo dalla SEZIONE "VEDI BULLONI".
In alto è presente un tasto _Cerca per..._ per cercare un bullone nella lista. Cliccandolo verrà aperta una finestra contenente due campi: uno per cercare un bullone tramite il suo codice ed uno per cercare i bulloni tramite la loro data di produzione. Cliccando il bottone _Cerca_ a fianco di uno dei due campi, verrà effettuata la relativa ricerca che mostrerà (se sono stati trovati) i risultati. Si può notare che dopo aver effettuato la ricerca, al posto del tasto _Cerca per..._ sarà comparso un nuovo tasto: _Visualizza tutto_. Cliccandolo verrà ricaricata la pagina e verrà mostrata nuovamente la lista completa dei bulloni.
Infine, in basso è presente il tasto _Aggiungi un bullone_. Cliccandolo si aprirà una finestra per personalizzare il bullone che si vuole aggiungere. Gli unici aspetti non personalizzabili sono il codice (che verrà deciso automaticamente dal software) e il diametro del dado (che viene calcolato automaticamente dal software, basandosi sul diametro della vite).


### SEZIONE "VEDI IMPIEGATI"
In questa sezione, è possibile visualizzare una lista di tutti i dipendenti presenti nel database e che risultano assunti.
A destra di ogni riga della lista sono presenti tre tasti:
- DETTAGLI -> cliccando su di esso, viene aperta una finestra contenente tutte le informazioni complete sul dipendente in questione (matricola, nome, cognome, stipendio, ecc...).
- PROMUOVI -> cliccando su di esso si accede ad una finestra che permette di modificarei valori relativi allo stipendio e le giornare lavorativi. Una volta effettuate tutte le modifiche    desiderate, attraverso i campi appositi, si può cliccare il tasto per "Invia" confermare la modifica e aggiornare i dati.
-LICENZIA -> cliccando su di esso si potrà eliminare l'impiegato, in modo da non renderlo più visibile nell'interfaccia. Prima di eliminarlo, il programma chiede all'utente una  conferma sull'azione che sarà IRREVERSIBILE. Dopo la cancellazione non sarà possibile vendere o modificare l'impieagato, ma sarà possibile visualizzare le informazioni su di esso solamente dalla sezione "vendite" se ne ha effetivamente effettuatuato qualcuna. 

Infine, in basso è presente il tasto "Aggiungi", cliccandolo su di esso si aprirà una form per inserire un nuovo impiegato e alla conferma dei dati inseriti, cliccando "Invia" l'impiegato verrà aggiunta effettivamente alla schemata e nel db con un codice autogenerato dal software.
