package library.services;

import java.util.Optional;
import java.util.UUID;

import library.Magazine;
import library.Rental;
import library.Section;
import library.repository.actionsImpl.MagazineRepositoryImpl;

public class MagazineService {
    private static MagazineRepositoryImpl magazineRepository = new MagazineRepositoryImpl();

    public static Magazine[] findAllMagazines() {
        return magazineRepository.findAllMagazines();
    }

    public static Optional<Magazine> findMagazineByID(UUID id) {
        return magazineRepository.findMagazineByID(id);
    }

    public static Magazine[] findMagazineByTitle(String title) {
        return magazineRepository.findMagazineByTitle(title);
    }

    public static Optional<Magazine> findMagazineByISBN(String ISBN) {
        return magazineRepository.findMagazineByISBN(ISBN);
    }

    public static Magazine[] findMagazinesBySection(Section section) {
        return magazineRepository.findMagazinesBySection(section);
    }

    public static Optional<Magazine> findMagazineByRental(Rental rental) {
        return magazineRepository.findMagazineByRental(rental);
    }

    public static boolean saveMagazine(Magazine magazine) {
        return magazineRepository.saveMagazine(magazine);
    }

    public static boolean deleteMagazine(Magazine magazine) {
        return magazineRepository.deleteMagazine(magazine);
    }
}
