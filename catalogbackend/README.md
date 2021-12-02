
# CAFF Catalog Backend

## Setup

**Prerequisites**
- Maven
- Java 11
- [MinGW](http://sourceforge.net/projects/mingw-w64/files/Toolchains%20targetting%20Win32/Personal%20Builds/mingw-builds/installer/mingw-w64-install.exe/download)
- [CMake](https://cmake.org/download/)
- you have to set the *catalog.caff.path.base* property in the *application.properties* to the full path where your catalogbackend folder is on your computer

## Usage
***1. H2 in-memory database***

It's a local, in-memory database, no setup is required.

**Build**

```mvn clean install```

**Run**

```mvn spring-boot:run```

***2. PostgreSQL database***

**Prerequisites**
- PostgreSQL

First, after installing PostgreSQL, you must create a database named *caffdb* and *caff_user* named user with the password described in the application-postgresql.properties.

**Build**

```mvn clean install  -Ppostgresql```

**Run**

```mvn spring-boot:run -Dspring-boot.run.profiles=postgresql```

## FYI
The endpoints are available at http://localhost:8080/api/url. Alternatively, the root folder contains a postman collection (*.../CAFF-API[szbizt].postman_collection.json*) that contains executable sample requests.
