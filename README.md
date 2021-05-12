# Proiect PAO - Pirlog Vlad - Grupa 244

Tema aleasa este **Tema 2**: implementarea unei biblioteci (sectiuni, carti, autori, clienti)

## Descrierea claselor

* [Entity](src/library/Entity.java) - clasa de baza a proiectului, pe care toate celelalte clase o mostenesc
* [Readable](src/library/Readable.java) - o clasa generala pentru toate entitatile cu pagini, ce pot fi citite
* [Book](src/library/Book.java) - o subclasa a `Readable`, ce reprezinta o carte
* [Magazine](src/library/Magazine.java) - o subclasa a `Readable`, ce reprezinta o revista
* [Author](src/library/Author.java) - clasa ce reprezinta un autor; doar instantele clasei `Book` au un autor
* [Publisher](src/library/Publisher.java) - clasa ce reprezinta o publicatie; doar instantele clasei `Book` au o publicatie
* [Section](src/library/Section.java) - clasa ce reprezinta o sectiune a bibliotecii (SF, stiinta, arta, etc); toate instantele clasei `Readable` au o sectiune
* [Rental](src/library/Rental.java) - o clasa generica pentru toate tipurile de inchirieri realizate in biblioteca
* [Client](src/library/Client.java) - clasa ce reprezinta clientul, ce poate inchiria instante ale clasei `Readable`
* [DatabaseSingleton](src/library/DatabaseSingleton.java) - clasa singleton pentru interactiunile cu baza de date sau fisierele CSV
* [Service](src/library/Service.java) - o clasa de serviciu, care expune operatiile sistemului; fiecare apel catre o metoda este adaugat intr-un fisierul [db/log.csv](db/log.csv)
* [Main](src/library/Main.java) - clasa din care sunt facute apeluri catre servicii

## Descrierea interfetelor

* [Saveable](src/library/interfaces/Saveable.java) - implementata de clasele care pot apela metoda `save` pentru a fi salvate intr-un fisier sau in baza de date
* [Deletable](src/library/interfaces/Deletable.java) - implementata de clasele care pot apela metoda `delete` pentru a fi sterse dintr-un fisier sau din baza de date
* [Serializable](src/library/interfaces/Serializable.java) - implementata de clasele ce pot fi serializate si salvate intr-un fisier CSV
* [Connectable](src/library/interfaces/Connectable.java) - interfata functionala implementata de singleton-ul bazei de date
* [Closeable](src/library/interfaces/Closeable.java) - interfata functionala implementata de singleton-ul bazei de date
