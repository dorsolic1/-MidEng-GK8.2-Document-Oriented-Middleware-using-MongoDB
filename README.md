# MongoDB
Dominik Orsolic

## Vorbereitung
**Container erstellen**:
```bash
docker run -d --name mongo mongo -p 27017:27017 mongo:latest
```

**Mongo Shell öffnen**:
```bash
docker exec -it mongo mongosh
```

**Aktive Datenbank auswählen**:
```bash
use warehouse_db
```

## Schritt 1: READ
**Befehl**:
```bash
db.warehouseData.find()
```

**Ausgabe**:
```bash
[
  {
    _id: '1',
    warehouseName: 'Hauptlager Linz',
    warehousePostalCode: 4020,
    warehouseCity: 'Linz',
    warehouseCountry: 'Austria',
    productData: [
      {
        productID: '00-443175',
        productName: 'Bio Orangensaft Sonne',
        productCategory: 'Getraenk',
        productQuantity: 2500
      },
      {
        productID: '00-871895',
        productName: 'Bio Apfelsaft Gold',
        productCategory: 'Getraenk',
        productQuantity: 3420
      },
      {
        productID: '01-926885',
        productName: 'Ariel Waschmittel Color',
        productCategory: 'Waschmittel',
        productQuantity: 478
      },
      {
        productID: '02-234811',
        productName: 'Mampfi Katzenfutter Rind',
        productCategory: 'Tierfutter',
        productQuantity: 1324
      }
    ],
    _class: 'warehouse.model.WarehouseData'
  },
  {
    _id: '2',
    warehouseName: 'Filiale Wien',
    warehousePostalCode: 1010,
    warehouseCity: 'Wien',
    warehouseCountry: 'Austria',
    productData: [
      {
        productID: '03-893173',
        productName: 'Saugstauberbeutel Ingres',
        productCategory: 'Reinigung',
        productQuantity: 7390
      }
    ],
    _class: 'warehouse.model.WarehouseData'
  }
]
```

## Schritt 2: UPDATE
**Befehl**:
```bash
db.warehouseData.updateOne(
   { "_id": "1", "productData.productID": "00-443175" },
   { $set: { "productData.$.productQuantity": 3000.0 } }
)
```


**Testen**:
```bash
db.warehouseData.find({ "_id": "1" })
```
```bash
...
{
	productID: '00-443175',
    productName: 'Bio Orangensaft Sonne',
    productCategory: 'Getraenk',
    productQuantity: 3000
}
... 
```
`productQuantity` wurde erfolgreich angepasst.

## Schritt 3: CREATE
**Befehl**:
Fügt über ein $push ein neues Produkt-Unterdokument direkt in das Array der Filiale Wien (_id: "2") ein.
```bash
db.warehouseData.updateOne(
   { "_id": "2" },
   { $push: { "productData": { "productID": "05-999999", "productName": "Energy Drink", "productCategory": "Getraenk", "productQuantity": 500.0 } } }
)
```

**Testen**:
```bash
db.warehouseData.find({ "_id": "2" })
```
```bash
{
	productID: '05-999999',
    productName: 'Energy Drink',
    productCategory: 'Getraenk',
    productQuantity: 500
}
```
Produkt wurde erfolgreich hinzugefügt

## Schritt 4: DELETE
**Befehl**:
Löscht das eben hinzugefügte Testprodukt mittels $pull wieder rückstandslos aus dem Array von Lager 2.
```bash
db.warehouseData.updateOne(
   { "_id": "2" },
   { $pull: { "productData": { "productID": "05-999999" } } }
)
```

**Testen**:
![[Pasted image 20260522103656.png]]
Das vorhin erstellte Produkt wurde wieder gelöscht.

## Fragestellungen

**1. Vor- & Nachteile vs. Relational (RDBMS)**
* **Vorteile:** Flexibles Schema (schemalos), hohe Performance bei großen Datenmengen, einfache horizontale Skalierung, objektnahe (verschachtelte) Datenstruktur.
* **Nachteile:** Kein standardisiertes SQL (eigene Syntax), schwächere ACID-Garantien (Eventual Consistency), Gefahr von Datenredundanz, komplexe Abfragen über mehrere Entitäten hinweg sind schwieriger.

**2. Probleme bei Datenzusammenführung**
* **Inkonsistente Formate:** Unterschiedliche Datentypen (z.B. String vs. Objekt) für dieselbe Information.
* **ID-Konflikte:** Uneinheitliche Primärschlüssel-Logiken der Quellsysteme.
* **Synchronisationsaufwand:** Aktualisierungen müssen manuell an allen redundanten Stellen nachgezogen werden.

**3. NoSQL-Arten & Vertreter**
* **Dokumentenorientiert:** MongoDB
* **Key-Value:** Redis
* **Spaltenorientiert (Column-Family):** Apache Cassandra
* **Graphdatenbanken:** Neo4j

**4. CAP-Theorem**
* **CA (Konsistenz + Verfügbarkeit):** Alle sehen dieselben Daten und das System läuft immer, verträgt aber keine Netzwerk-Trennungen.
* **CP (Konsistenz + Ausfallsicherheit):** Daten sind immer strikt korrekt, bei Netzwerkfehlern blockiert das System aber neue Anfragen.
* **AP (Verfügbarkeit + Ausfallsicherheit):** System antwortet immer trotz Netzwerkfehlern, liefert aber kurzzeitig veraltete Daten (Eventual Consistency).

**5. MongoDB-Befehle (Lagerstand)**

**Über alle Standorte:**
```javascript
db.warehouseData.find({ "productData.productID": "00-443175" }, { "warehouseName": 1, "productData.$": 1 })
```

**Für einen bestimmten Standort:**
```bash
db.warehouseData.find({ "_id": "1", "productData.productID": "00-443175" }, { "warehouseName": 1, "productData.$": 1 })
```


## Quellen
- [Was bedeutet NoSQL](https://www.oracle.com/at/database/nosql/what-is-nosql)
- [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
- [MongoDB Installation](https://docs.mongodb.com/manual/administration/install-community/)
- [mongo Shell Quick Reference](https://docs.mongodb.com/manual/reference/mongo-shell/)
- [mongo Shell Query Reference](https://www.mongodb.com/docs/manual/tutorial/query-embedded-documents/)
- [Grundlagen Spring Framework](https://spring.io/)
- [Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [Spring RESTful Web Service](https://spring.io/guides/gs/rest-service/#use-maven)
- NoSQL Introduction
    - [NoSQL on w3resource](https://www.w3resource.com/mongodb/nosql.php)
    - [Introduction to NoSQL Database](https://www.edureka.co/blog/introduction-to-nosql-database/)
    - [NoSQL im Überblick](https://www.heise.de/ct/artikel/NoSQL-im-Ueberblick-1012483.html)