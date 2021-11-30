
# CAFF Catalog Backend

## Setup

**Előkövetelmények**
- Maven
- Java 11
- [MinGW](http://sourceforge.net/projects/mingw-w64/files/Toolchains%20targetting%20Win32/Personal%20Builds/mingw-builds/installer/mingw-w64-install.exe/download)  (ha ez nincs telepítve és rárakva a PATH-ra (környezeti változó bállítás) a CAFF parser nem fog futni)
- [CMake](https://cmake.org/download/) (ez nem biztos, hogy kell, de ha nem futna a parse, akkor telepíteni és rárakva a PATH-ra (környezeti változó bállítás))

## Használat
**!!!CSAK H2 DB-VEL MEGY, MERT A POSTGRE NEM KEZELI JÓL A BYTE FÁJLOKAT!!!**

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

mvn clean install  -Ppostgresql

**Futtatás**

mvn spring-boot:run -Dspring-boot.run.profiles=postgresql

## FYI
Ezután a végpontok elérhetőek a http://localhost:8080/api/ url-en. Illetve a gyökérmappán található egy postman collection (*.../CAFF-API [szbizt].postman_collection.json*), amiben mintha request-ek találhatóak.

## Parseres végpont működése
Először be kell állítani a *catalogbackend\src\main\resources\application.properties* fájlban a *catalog.caff.path.base* property-t, ez azt adja meg, hogy a *catalogbackend* mappa pontosan hol található a gépen. (Ha ez nincs beállítva, akkor nem fogja megtalálni a parser-t és a többi fájlt.

A Postman collection update-elve lett, így ott egy minta caff fájl felötltésével már ki is lehet próbálni.
Van a POST http://localhost:8080/api/picture végpont, ami most már nem egy json fájlt vár bemenetnek, hanem a *Content-Type* header-nek *multipart/form-data*-ának kell lennie.
A body pedig egy ilyen *form-data* lesz, két dolgot kell megadni:
- *name* : ez lesz a kép neve
- *caffFile* : de kell a caff file-t föltölteni

A response body marad ugyanúgy ahogy eddig.
Egy minta:
Request:
```
curl --location --request POST 'http://localhost:8080/api/picture' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJDQUZGIENhdGFsb2cgQmFja2VuZCBKV1QgSXNzdWVyIiwiYXVkIjoiQ0FGRiBDYXRhbG9nIEJhY2tlbmQiLCJzdWIiOiJhbm5lIiwiZXhwIjoxNjM4MzA0MTk2fQ.cxOZJTy1aksiGI5nGEdeQCpHWGL1AUVqgqKjlOJ9Lr_D9WPfuk1t_Xx9lumVEWKkNsjG6aej9AVBffLXuaoYvw' \
--form 'caffFile=@"/C:/Users/stell/Desktop/caff_files/1.caff"' \
--form 'name="Ez egy teszt kép"'
```
Response:
```
{
	"comments":  null,
	"id":  "2fe11144-9c41-4bf1-a8a2-a072f50dd3d3",
	"name":  "Ez egy teszt kép",
	"user":  {
		"email":  "anne@email.hu",
		"id":  "599ed9a2-a4ab-42fa-bf11-e82832560313",
		"username":  "anne",
		"role":  "USER"
	},
	"content":  "Qk1oiB4AAAAA...sok sok byte..."
}
```