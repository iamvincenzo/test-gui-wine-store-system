ISTRUZIONI PER UTILIZZO:

	1. Modificare in MonitorDB la porta di ascolto del server del database se necessario. (default 3306)
	2. Modificare credenziali di accesso al database se necessario. (default user: root, password: root)
	3. Avviare servizio MySQL80 se non attivo nel CMD da amministratore con il comando: net start mysql80
	4. Avvviare WineStoreServer.
	5. Eseguire i Test nel package test.


STRUTTURA DATABASE:

	1. Tabella wines: contiene tutti i vini del sistema.
	2. Tabella administrator: contiene tutti gli amministratori del sistem.
	3. Tabella employee: contiene tutti gli impiegati del sistema.
	4. Tabella customer: contiene tutti i clienti del sistema.
	5. Tabella orders: contiene tutti gli ordini di tutti i clienti del sistema.
	6. Tabella notification: contiene tutte le notifiche di disponibilità sottoscritte dai clienti del sistema.
	7. Tabella orderedwineemployee: contiene tutti gli ordini dei vini fatti dagli impiegati del sistema.