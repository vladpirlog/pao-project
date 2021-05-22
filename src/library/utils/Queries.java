package library.utils;

public class Queries {
    public static final String AUTHOR__FIND_ALL = "SELECT a.id, a.creationDate, a.firstName, a.lastName, "
            + "a.birthDate, a.birthPlace, a.deathDate FROM authors a";

    public static final String AUTHOR__FIND_BY_ID = "SELECT a.id, a.creationDate, a.firstName, a.lastName, "
            + "a.birthDate, a.birthPlace, a.deathDate FROM authors a WHERE a.id = ?";

    public static final String AUTHOR__FIND_BY_FULL_NAME = "SELECT a.id, a.creationDate, a.firstName, a.lastName, "
            + "a.birthDate, a.birthPlace, a.deathDate FROM authors a WHERE a.firstName = ? AND a.lastName = ?";

    public static final String AUTHOR__FIND_BY_BOOK = "SELECT a.id, a.creationDate, a.firstName, a.lastName, "
            + "a.birthDate, a.birthPlace, a.deathDate FROM authors a JOIN books b ON a.id = b.authorId "
            + "WHERE b.id = ?";

    public static final String AUTHOR__SAVE = "INSERT INTO authors(id, creationDate, firstName, lastName, "
            + "birthDate, birthPlace, deathDate) VALUES(?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
            + "firstName = VALUES(firstName), lastName = VALUES(lastName), birthDate = VALUES(birthDate), "
            + "birthPlace = VALUES(birthPlace), deathDate = VALUES(deathDate)";

    public static final String AUTHOR__DELETE = "DELETE FROM authors WHERE id = ?";

    public static final String BOOK__FIND_ALL = "SELECT b.id, b.creationDate, b.title, b.sectionId, "
            + "b.ISBN, b.language, b.authorId, b.releaseYear, b.publisherId FROM books b";

    public static final String BOOK__FIND_BY_ID = "SELECT b.id, b.creationDate, b.title, b.sectionId, "
            + "b.ISBN, b.language, b.authorId, b.releaseYear, b.publisherId FROM books b WHERE b.id = ?";

    public static final String BOOK__FIND_BY_TITLE = "SELECT b.id, b.creationDate, b.title, b.sectionId, "
            + "b.ISBN, b.language, b.authorId, b.releaseYear, b.publisherId FROM books b WHERE b.title = ?";

    public static final String BOOK__FIND_BY_ISBN = "SELECT b.id, b.creationDate, b.title, b.sectionId, "
            + "b.ISBN, b.language, b.authorId, b.releaseYear, b.publisherId FROM books b WHERE b.ISBN = ?";

    public static final String BOOK__FIND_BY_SECTION = "SELECT b.id, b.creationDate, b.title, b.sectionId, "
            + "b.ISBN, b.language, b.authorId, b.releaseYear, b.publisherId FROM books b WHERE b.sectionId = ?";

    public static final String BOOK__FIND_BY_AUTHOR = "SELECT b.id, b.creationDate, b.title, b.sectionId, "
            + "b.ISBN, b.language, b.authorId, b.releaseYear, b.publisherId FROM books b WHERE b.authorId = ?";

    public static final String BOOK__FIND_BY_PUBLISHER = "SELECT b.id, b.creationDate, b.title, b.sectionId, "
            + "b.ISBN, b.language, b.authorId, b.releaseYear, b.publisherId FROM books b WHERE b.publisherId = ?";

    public static final String BOOK__FIND_BY_RENTAL = "SELECT b.id, b.creationDate, b.title, b.sectionId, b.ISBN, "
            + "b.language, b.authorId, b.releaseYear, b.publisherId FROM books b JOIN rentals r ON b.id = r.readableId "
            + "WHERE r.id = ?";

    public static final String BOOK__SAVE = "INSERT INTO books(id, creationDate, title, sectionId, ISBN, "
            + "language, authorId, releaseYear, publisherId) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
            + "title = VALUES(title), sectionId = VALUES(sectionId), ISBN = VALUES(ISBN), language = VALUES(language), "
            + "authorId = VALUES(authorId), releaseYear = VALUES(releaseYear), publisherId = VALUES(publisherId)";

    public static final String BOOK__DELETE = "DELETE FROM books WHERE id = ?";

    public static final String CLIENT__FIND_ALL = "SELECT c.id, c.creationDate, c.firstName, c.lastName, c.CNP "
            + "FROM clients c";

    public static final String CLIENT__FIND_BY_ID = "SELECT c.id, c.creationDate, c.firstName, c.lastName, c.CNP "
            + "FROM clients c WHERE c.id = ?";

    public static final String CLIENT__FIND_BY_CNP = "SELECT c.id, c.creationDate, c.firstName, c.lastName, c.CNP "
            + "FROM clients c WHERE c.CNP = ?";

    public static final String CLIENT__FIND_BY_FULL_NAME = "SELECT c.id, c.creationDate, c.firstName, c.lastName, c.CNP "
            + "FROM clients c WHERE c.firstName = ? AND c.lastName = ?";

    public static final String CLIENT__FIND_BY_RENTAL = "SELECT c.id, c.creationDate, c.firstName, c.lastName, c.CNP "
            + "FROM clients c JOIN rentals r ON c.id = r.clientId WHERE r.id = ?";

    public static final String CLIENT__SAVE = "INSERT INTO clients(id, creationDate, firstName, lastName, CNP) "
            + "VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE firstName = VALUES(firstName), lastName = VALUES(lastName), "
            + "CNP = VALUES(CNP)";

    public static final String CLIENT__DELETE = "DELETE FROM clients WHERE id = ?";

    public static final String MAGAZINE__FIND_ALL = "SELECT m.id, m.creationDate, m.title, m.sectionId, m.ISBN, "
            + "m.language, m.issueDate FROM magazines m";

    public static final String MAGAZINE__FIND_BY_ID = "SELECT m.id, m.creationDate, m.title, m.sectionId, m.ISBN, "
            + "m.language, m.issueDate FROM magazines m WHERE m.id = ?";

    public static final String MAGAZINE__FIND_BY_TITLE = "SELECT m.id, m.creationDate, m.title, m.sectionId, m.ISBN, "
            + "m.language, m.issueDate FROM magazines m WHERE m.title = ?";

    public static final String MAGAZINE__FIND_BY_ISBN = "SELECT m.id, m.creationDate, m.title, m.sectionId, m.ISBN, "
            + "m.language, m.issueDate FROM magazines m WHERE m.ISBN = ?";

    public static final String MAGAZINE__FIND_BY_SECTION = "SELECT m.id, m.creationDate, m.title, m.sectionId, m.ISBN, "
            + "m.language, m.issueDate FROM magazines m WHERE m.sectionId = ?";

    public static final String MAGAZINE__FIND_BY_RENTAL = "SELECT m.id, m.creationDate, m.title, m.sectionId, m.ISBN, "
            + "m.language, m.issueDate FROM magazines m JOIN rentals r ON m.id = r.readableId WHERE r.id = ?";

    public static final String MAGAZINE__SAVE = "INSERT INTO magazines(id, creationDate, title, sectionId, ISBN, language, "
            + "issueDate) VALUES(?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE title = VALUES(title), sectionId = "
            + "VALUES(sectionId), ISBN = VALUES(ISBN), language = VALUES(language), issueDate = VALUES(issueDate)";

    public static final String MAGAZINE__DELETE = "DELETE FROM magazines WHERE id = ?";

    public static final String PUBLISHER__FIND_ALL = "SELECT p.id, p.creationDate, p.name FROM publishers p";

    public static final String PUBLISHER__FIND_BY_ID = "SELECT p.id, p.creationDate, p.name FROM publishers p WHERE p.id = ?";

    public static final String PUBLISHER__FIND_BY_NAME = "SELECT p.id, p.creationDate, p.name FROM publishers p WHERE p.name = ?";

    public static final String PUBLISHER__FIND_BY_BOOK = "SELECT p.id, p.creationDate, p.name FROM publishers p "
            + "JOIN books b ON p.id = b.publisherId WHERE b.id = ?";

    public static final String PUBLISHER__SAVE = "INSERT INTO publishers(id, creationDate, name) VALUES(?, ?, ?) ON "
            + "DUPLICATE KEY UPDATE name = VALUES(name)";

    public static final String PUBLISHER__DELETE = "DELETE FROM publishers WHERE id = ?";

    public static final String RENTAL__FIND_ALL = "SELECT r.id, r.creationDate, r.clientId, r.startDate, "
            + "r.predictedEndDate, r.endDate, r.readableId FROM rentals r";

    public static final String RENTAL__FIND_BY_ID = "SELECT r.id, r.creationDate, r.clientId, r.startDate, "
            + "r.predictedEndDate, r.endDate, r.readableId FROM rentals r WHERE r.id = ?";

    public static final String RENTAL__FIND_BY_READABLE = "SELECT r.id, r.creationDate, r.clientId, r.startDate, "
            + "r.predictedEndDate, r.endDate, r.readableId FROM rentals r WHERE r.readableId = ?";

    public static final String RENTAL__FIND_BY_CLIENT = "SELECT r.id, r.creationDate, r.clientId, r.startDate, "
            + "r.predictedEndDate, r.endDate, r.readableId FROM rentals r WHERE r.clientId = ?";

    public static final String RENTAL__SAVE = "INSERT INTO rentals(id, creationDate, clientId, startDate, "
            + "predictedEndDate, endDate, readableId) VALUES(?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
            + "clientId = VALUES(clientId), startDate = VALUES(startDate), predictedEndDate = VALUES(predictedEndDate), "
            + "endDate = VALUES(endDate), readableId = VALUES(readableId)";

    public static final String RENTAL__DELETE = "DELETE FROM rentals WHERE id = ?";

    public static final String SECTION__FIND_ALL = "SELECT s.id, s.creationDate, s.name FROM sections s";

    public static final String SECTION__FIND_BY_ID = "SELECT s.id, s.creationDate, s.name FROM sections s WHERE s.id = ?";

    public static final String SECTION__FIND_BY_NAME = "SELECT s.id, s.creationDate, s.name FROM sections s WHERE s.name = ?";

    public static final String SECTION__FIND_BY_READABLE = "SELECT s.id, s.creationDate, s.name FROM sections s WHERE "
            + "s.id = ( SELECT b.sectionId FROM books b WHERE b.id = ? UNION SELECT m.sectionId FROM magazines m WHERE "
            + "m.id = ? )";

    public static final String SECTION__SAVE = "INSERT INTO sections(id, creationDate, name) VALUES(?, ?, ?) ON "
            + "DUPLICATE KEY UPDATE name = VALUES(name)";

    public static final String SECTION__DELETE = "DELETE FROM sections WHERE id = ?";
}
