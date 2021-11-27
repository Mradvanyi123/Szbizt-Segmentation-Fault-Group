# CAFF Catalog Backend

## Setup

**Előkövetelmények**
- Maven
- Java 11

**Adatbázis**
Mivel lokális H2 adatbázist használunk, így nincs szükség semmilyen setup-ra. Elérni a futtatás indítása után a http://localhost:8080/api/h2-console url-en lehet elérni.
*A JDBC url, username és a password elérhetőek az application.properties fájlban.*


## Futtatás
**Fordítás**

    mvn clean install

**Futtatás**

    mvn spring-boot:run
Ezután a végpontok elérhetőek a http://localhost:8080/api/ url-en.
