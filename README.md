# Szbizt-Segmentation-Fault-Group

Csapat tagok:

-   Ružinský Bálint Tamás
-   Horváth Kristóf
-   Szigeti Attila
-   Tóth-Baranyi Stella
-   Radványi Mátyás
 


## Bevezetés
A feladat egy olyan online áruházat elkészítése, amiben egyedi formátumú animált képeket lehet vásárolni. A szoftverrel szemben elvárás, hogy a CAFF (CrySyS Animated File Format) formátumot kell támogatnia. További elvárás, hogy a teljes rendszer tartalmazzon HTTP(S) protokollon elérhető távoli szolgáltatást, valamint az ahhoz tartozó webes klienst. A rendszerrel szemben támasztott követelmény, hogy a felhasználók belépés után képesek legyenek CAFF fájlt feltölteni, letölteni, a fájlok között keresni, illetve a fájlokhoz megjegyzést hozzáfűzni. A rendszerben kell hogy legyen adminisztrátor felhasználó, aki a felhasználók adatait láthatja, illetve az összes fájl adatait módosíthatja, törölheti.

Az alábbi dokumentum ezen rendszer architektúra terveit fekteti le és kiemelt figyelmet szentel a rendszer biztonsági követelményeire. A biztonsági követelmények és a szükséges biztonsági funkcionalitások meghatározásához a threat modeling eljárást használjuk. Az architektúra terveket UML diagramok formájában dokumentáljuk.

## A rendszer által használt formátumok

### CAFF fájlformátum

-   tömörítés nélküli animáció formátum
    
-   CIFF képek tárolására alkalmas
    
-   az animációhoz tartozó metaadatokat tárolja
    
-   Példa fájlok a teszteléshez
    

### CIFF fájlformátum

-   tömörítés nélküli képformátum
    
-   pixel információkat tartalmaz
    
-   a képhez tartozó metaadatokat tárolja
 # Követelmények

A rendszer fejlesztésének első lépése a követelmények meghatározása, ide értve a funkcionális és biztonsági követelményeket is. A funkcionális követelmények dokumentálásához UML use-case diagramokat használtunk.A biztonsági követelmények meghatározásához az előadáson megismert Threat, Risk, Vulnerability Analisys módszert használjuk.

## Funkcionális követelmények

Ezen fejezetben a rendszerrel szemben támasztott funkcionális követelmények kerülnek kifejtésre. Mivel a rendszernek többféle felhasználója van (általános felhasználó, illetve adminisztrátor), többféle felhasználói szcenárióra kell felkészíteni. A legfontosabb felhasználói szcenáriókat az 1. ábra foglalja össze.

-   Felhasználók tudnak regisztrálni és belépni a rendszerbe
    
-   Felhasználók képesek CAFF fájlt feltölteni, letölteni, keresni
    
-   Felhasználók hozzáfűzhetnek megjegyzéseket a CAFF fájlhoz
    
-   Felhasználónak megtekinthetik személyes adataikat
    
-   Adminisztrátor  tudja módosítani a rendszerbe feltöltött összes fájl adatait, illetve törölheti bármely fájlt, továbbá megtekintheti a felhasználók adatait

![use case diagram](https://github.com/Mradvanyi123/Szbizt-Segmentation-Fault-Group/blob/Mradvanyi123-patch-1/use_case.drawio.png)

### Szerver oldali funkció-követelmények

A rendszer felületén meg kell jelenjen egy előnézet a CAFF fájlból. Szerver oldali funkció-követelmény tehát a CAFF fájl feldolgozása C++ nyelven, vagyis a CAFF fájlból egy előnézet generálása a megjelenítéshez. A szerver oldali kód Java + SpringBoot keretrendszerben kerül lefejlesztésre, ehhez lesz illesztve a CAFF feldolgozásért felelős C++ modul.

### Kliens oldali követelmények

Elvárás, hogy a rendszer tartalmazzon HTTP protokollon elérhető távoli szolgáltatást egy REST Api-n keresztül, ezért az ahhoz tartozó webes kliens Angular keretrendszerben lesz megvalósítva.
## Biztonsági követelmények és célok

A funkcionális követelmények ismeretében nagy vonalakban felvázoltuk a rendszert és annak környezetét, ahogy azt a lenti ábra mutatja. A rendszert az általános felhasználók, és az adminisztrátorok használják, velük kerülhet interakcióba a rendszer. Mivel a tőlük érkező kéréseket, a viselkedésüket nem tudjuk kontrollálni, ezért a velük történő interakció bizalmi kérdéseket vet fel, ezt jelöli az ábrán a szaggatott piros vonal. A rendszer működtetéséhez szükség lesz a felhasználók (általános felhasználók és adminisztrátorok) adatainak tárolására, valamint a feltöltött fájlok adatait, illetve a hozzájuk fűzött megjegyzéseket is el kell tárolnunk. A tárolt adatok között kapcsolatot is kell létesíteni: a felhasználókat össze kell kötnünk az általuk feltöltött, letöltött fájlokkal, illetve az általuk írt megjegyzésekkel, továbbá a fájlok is összeköttetésben állnak a hozzájuk tartozó megjegyzésekkel.
![caff store system](https://github.com/Mradvanyi123/Szbizt-Segmentation-Fault-Group/blob/szigetiatti-specs-1/system.png)

A biztonsági követelményeket 6 kategóriába soroljuk:
#### Bizalmasság:

-   A felhasználók személyes adataikat csak ők maguk, illetve az adminisztrátor felhasználók ismerhetik meg.
    

#### Integritás:

-   A felhasználók csak az általuk feltöltött fájlok adatait módosíthatják/ törölhetik, más felhasználók fájljait kizárólag adminisztrátor módosíthatja/ törölheti.
    
-   A felhasználók személyes adataikat kizárólag ők maguk módosíthatják.
    

#### Elérhetőség:

-   A rendszer összes funkciója minden időpontban rendelkezésre kell álljon.
    
-   A rendszerbe feltöltött fájlok minden időpontban elérhetőek.
    

#### Autentikáció:

-   A felhasználók csak bejelentkezés után használhatják a rendszert.
    
-   A rendszerbe a felhasználó saját maga regisztrálhat.
    
-   Adminisztrátori fiókot csak a kód módosításával lehet létrehozni.
    

#### Autorizáció:

-   Adminisztrátori jogosultság szükséges a következő funkciók eléréséhez: Más felhasználók fájljainak módosítása/ törlése, más felhasználók személyes adatainak megtekintése.
    

#### Auditálás:

-   A felhasználók tevékenységét naplózni kell.
