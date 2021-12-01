
# CAFF Catalog Backend

## Setup

**Prerequisites**
- Maven
- Java 11
- [MinGW](http://sourceforge.net/projects/mingw-w64/files/Toolchains%20targetting%20Win32/Personal%20Builds/mingw-builds/installer/mingw-w64-install.exe/download)
- [CMake](https://cmake.org/download/)

## Usage
***1. H2 in-memory database***

Mivel lokális H2 adatbázist használunk, így nincs szükség semmilyen setup-ra. Elérni a futtatás indítása után a  url-en lehet.
Because we use a local, in-memory H2 database, no setup is required. It can be accessed at [http://localhost:8080/api/h2-console](http://localhost:8080/api/h2-console) after starting the run.
*The JDBC url, username, and password are available in the application.properties*

**Build**

mvn clean install
**Run**

mvn spring-boot:run
***2. PostgreSQL adatbázissal***

**Prerequisites**
- PostgreSQL

First, after installing PostgreSQL, you must create a database named *caffdb* and *caff_user* named user with the password described in the application-postgresql.properties.

**Build**

mvn clean install  -Ppostgresql

**Run**

mvn spring-boot:run -Dspring-boot.run.profiles=postgresql

## FYI
The endpoints are then available at http://localhost:8080/api/url. Alternatively, the root folder contains a postman collection (*.../CAFF-API[szbizt].postman_collection.json*) that contains executable sample requests.
