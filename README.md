# Proiect PAO - Pirlog Vlad - Grupa 244

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7b5df857fe994b7084f59aa72da8d632)](https://www.codacy.com/gh/vladpirlog/pao-project/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=vladpirlog/pao-project&amp;utm_campaign=Badge_Grade)

Tema aleasa este **Tema 2**: implementarea unei biblioteci (sectiuni, carti, autori, clienti)

## Descrierea claselor

*  [Entity](src/library/Entity.java) - clasa de baza a proiectului, pe care toate celelalte clase o mostenesc
*  [Readable](src/library/Readable.java) - o clasa generala pentru toate entitatile cu pagini, ce pot fi citite
*  [Book](src/library/Book.java) - o subclasa a `Readable`, ce reprezinta o carte
*  [Magazine](src/library/Magazine.java) - o subclasa a `Readable`, ce reprezinta o revista
*  [Author](src/library/Author.java) - clasa ce reprezinta un autor; doar instantele clasei `Book` au un autor
*  [Publisher](src/library/Publisher.java) - clasa ce reprezinta o publicatie; doar instantele clasei `Book` au o publicatie
*  [Section](src/library/Section.java) - clasa ce reprezinta o sectiune a bibliotecii (SF, stiinta, arta, etc); toate instantele clasei `Readable` au o sectiune
*  [Rental](src/library/Rental.java) - o clasa generica pentru toate tipurile de inchirieri realizate in biblioteca
*  [Client](src/library/Client.java) - clasa ce reprezinta clientul, ce poate inchiria instante ale clasei `Readable`
*  [DatabaseSingleton](src/library/utils/DatabaseSingleton.java) - clasa singleton pentru interactiunile cu baza de date
*  [UserInteractionService](src/library/services/UserInteractionService.java) - o clasa de serviciu, care expune operatiile sistemului
*  [LoggingService](src/library/services/LoggingService.java) - un serviciu de logging ce scrie in fisierul [db/log.csv](db/log.csv)
*  [Main](src/Main.java) - clasa din care sunt facute apeluri catre servicii

## Descrierea interfetelor

*  [Saveable](src/library/interfaces/Saveable.java) - implementata de clasele care pot apela metoda `save` pentru a fi salvate in baza de date
*  [Deletable](src/library/interfaces/Deletable.java) - implementata de clasele care pot apela metoda `delete` pentru a fi sterse din baza de date
*  [Serializable](src/library/interfaces/Serializable.java) - implementata de clasele ce pot fi serializate in format CSV
*  [Connectable](src/library/interfaces/Connectable.java) - interfata functionala implementata de singleton-ul bazei de date
*  [Closeable](src/library/interfaces/Closeable.java) - interfata functionala implementata de singleton-ul bazei de date

## Descrierea exceptiilor

*  [ClientNotFoundException](src/library/exceptions/ClientNotFoundException.java) - nu exista un client valid asociat unei inchirieri
*  [NotAvailableForRentalException](src/library/exceptions/NotAvailableForRentalException.java) - cartea sau revista nu este disponibila pentru inchiriere
*  [SectionNotFoundException](src/library/exceptions/SectionNotFoundException.java) - nu exista o sectiune valida asociata unei carti sau reviste

## Adaugarea variabilelor de mediu

Variabilele de mediu necesare rularii aplicatiei trebuie incluse intr-un fisier numit `.env` sau date ca argumente in momentul lansarii. In fisierul [.env.example](.env.example) se afla un exemplu de fisier de configurare.
