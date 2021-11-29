
# CAFF Catalog Backend

## Setup

**Előkövetelmények**
- Maven
- Java 11

## Használat
***1. H2 in-memory adatbázissal***

Mivel lokális H2 adatbázist használunk, így nincs szükség semmilyen setup-ra. Elérni a futtatás indítása után a [http://localhost:8080/api/h2-console](http://localhost:8080/api/h2-console) url-en lehet elérni.
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

## Autentikáció használata
***Csak admin által elérhető végpontok***
- DELETE http://localhost:8080/api/picture

***Használat***

1. Regisztráció:
   POST http://localhost:8080/api/user/register végponton ezzel a felépítésű body-val:
 ```
 {
	"email": "stella@email.hu",
	"username": "stella",
	"password": "password"
}
```
2. Bejelentkezés:
   POST http://localhost:8080/api/login végponton ezzel a felépítésű body-val:
 ```
{
	"username": "stella",
	"password": "password"
}
 ```
Itt egy ilyen response-t ad vissza:
 ```
 {
	"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJDQUZGIENhdGFsb2cgQmFja2VuZCBKV1QgSXNzdWVyIiwiYXVkIjoiQ0FGRiBDYXRhbG9nIEJhY2tlbmQiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTYzODEyNjIxN30.VbPlHxptl2yRv72X_m-g55v_UM-VpbgMiTPHrkGsOUmC_31bQVFIsDFXcekq5esEwAWMLAFQDxSNugktuVg7cQ"
}
 ```
Ezt úgy tudod használni, hogy bemásolod az Authorization nevű header-be Bearer előtaggal a tokent. Vagyis így:
 ```
Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJDQUZGIENhdGFsb2cgQmFja2VuZCBKV1QgSXNzdWVyIiwiYXVkIjoiQ0FGRiBDYXRhbG9nIEJhY2tlbmQiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTYzODEyNjIxN30.VbPlHxptl2yRv72X_m-g55v_UM-VpbgMiTPHrkGsOUmC_31bQVFIsDFXcekq5esEwAWMLAFQDxSNugktuVg7cQ
 ```
A token pedig 30 percig érvényes. Refresh token nincs, szóval újra kell authentikálni.

3. Kijelentkezés
   POST http://localhost:8080/api/logout -en (nincs body, de az token kell). Alapvetően nem semmisíti meg a tokent, de olya mintha lenne logout.