package library.repository.actions;

import java.util.Optional;
import java.util.UUID;

import library.Section;
import library.Magazine;
import library.Rental;

public interface MagazineRepository {
    Magazine[] findAllMagazines();

    Optional<Magazine> findMagazineByID(UUID id);

    Magazine[] findMagazineByTitle(String title);

    Optional<Magazine> findMagazineByISBN(String ISBN);

    Magazine[] findMagazinesBySection(Section section);

    Optional<Magazine> findMagazineByRental(Rental rental);

    boolean saveMagazine(Magazine magazine);

    boolean deleteMagazine(Magazine magazine);
}
