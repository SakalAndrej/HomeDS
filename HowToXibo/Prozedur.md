# JavaEE Backend Funktionsweise
### Timer: 

Jeden Tag um 01:30 prüfen ob:

	- DataSet Time valide geworden ist
		- Ja -> DataSetAdd Prozedur
		- Nein -> nichts tun
	- Ein DataSet im Xibo ist dann isADataSetInXibo Prozedur 

### DataSetAdd Prozedur:

- DataSet hinzufügen -> DataSet wird geprüft ob Datum valide ist
	- JA -> in Xibo und server db speichern 
		- Wenn erfolgreich dann -> isADataSetInXibo Prozedur
	- NEIN -> in serverdb speichern datarowid = -1 

### isADataSetInXibo Prozedur:

Wenn ein DataSet sich im XIBO befindet dann:

- Kampagne erstellen Priority 10 und in server datenbank speichern

Wenn kein DataSet sich mehr im XIBO befindet dann 

- Kampagne löschen und in server datenbank löschen

### PlayMedia Prozedur:

Wenn ein Media abgespielt werden soll:

- falls kein Media zurzeit abgespielt wird
	- Im Layout die Region ändern dass, die richtige media abgespielt wird. Das ganze Schedulen ohne Zeitintervall  
	- eine neues event schedulen mit dem richtigen Layout
- falls media schon abgespielt wird
	- altes event löschen
	- Im Layout die Region ändern dass, die richtige media abgespielt wird. Das ganze Schedulen ohne Zeitintervall  
	- eine neues event schedulen mit dem richtigen Layout

- 10 sek bis nächstes media abgespielt werden kann (loading sperren ganzer screen)