
# CAFF Catalog Backend

## Setup

**Előkövetelmények**
- Maven
- Java 11

## Használat
***1. H2 in-memory adatbázissal***

Mivel lokális H2 adatbázist használunk, így nincs szükség semmilyen setup-ra. Elérni a futtatás indítása után a http://localhost:8080/api/h2-console url-en lehet elérni.
*A JDBC url, username és a password elérhetőek az application-h2.properties fájlban.*

**Fordítás**

    mvn clean install -Ph2

**Futtatás**

    mvn spring-boot:run -Dspring-boot.run.profiles=h2

***2. PostgreSQL adatbázissal***

**Előkövetelmények**
- PostgreSQL

Először is  a PostgreSQL telepítése után létre kell hozni egy *caffdb* nevű adatbázist, illetve egy *caff_user* user-t az application.properties fájlban leírt hozzátartozó jelszóval. Ezután elérhető az adatbázis és futtathatók a következők (már ha üres az összes tábla):

**Fordítás**

    mvn clean install

**Futtatás**

    mvn spring-boot:run

## FYI
Ezután a végpontok elérhetőek a http://localhost:8080/api/ url-en. Illetve a gyökérmappán található egy postman collection (*.../CAFF-API [szbizt].postman_collection.json*), amiben mintha request-ek találhatóak.