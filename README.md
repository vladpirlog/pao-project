# Proiect PAO - Pirlog Vlad - Grupa 244

Tema aleasa este **Tema 2**: implementarea unei biblioteci (sectiuni, carti, autori, clienti)

## Descrierea claselor

* `Entity` - clasa de baza a proiectului, pe care toate celelalte clase o mostenesc
* `Readable` - o clasa generala pentru toate entitatile cu pagini, ce pot fi citite
* `Book` - o subclasa a `Readable`, ce reprezinta o carte ca entitate abstracta
* `Magazine` - o subclasa a `Readable`, ce reprezinta o revista ca entitate abstracta
* `ReadableCopy` - o copie fizica a unei entitati `Readable`
* `Author` - clasa ce reprezinta un autor; doar instantele clasei `Book` au un autor
* `Publisher` - clasa ce reprezinta o publicatie; doar instantele clasei `Book` au o publicatie
* `Section` - clasa ce reprezinta o sectiune a bibliotecii (SF, stiinta, arta, etc); toate instantele clasei `Readable` au o sectiune
* `Rental` - o clasa generica pentru toate tipurile de inchirieri realizate in biblioteca
* `Client` - clasa ce reprezinta clientul, ce poate inchiria instante ale claselor derivate `ReadableCopy`
* `DatabaseSingleton` - clasa singleton pentru interactiunile cu baza de date
* `Service` - o clasa de serviciu, care expune operatiile sistemului
* `Main` - clasa din care sunt facute apeluri catre servicii

## Descrierea interfetelor

* `Copyable` - implementata de clasa `Readable`; reprezinta entitati ce pot crea si manipula copii
* `Saveable` - implementata de clasele care pot apela metoda `save` pentru a fi salvate intr-un fisier sau in baza de date
* `Connectable` - interfata functionala implementata de singleton-ul bazei de date
