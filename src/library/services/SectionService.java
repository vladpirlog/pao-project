package library.services;

import java.util.Optional;
import java.util.UUID;

import library.Readable;
import library.Section;
import library.exceptions.SectionNotFoundException;
import library.repository.actionsImpl.SectionRepositoryImpl;

public class SectionService {
    private static SectionRepositoryImpl sectionRepository = new SectionRepositoryImpl();

    public static Section[] findAllSections() {
        return sectionRepository.findAllSections();
    }

    public static Optional<Section> findSectionByID(UUID id) {
        return sectionRepository.findSectionByID(id);
    }

    public static Optional<Section> findSectionByName(String name) {
        return sectionRepository.findSectionByName(name);
    }

    public static Section findSectionByReadable(Readable readable) throws SectionNotFoundException {
        return sectionRepository.findSectionByReadable(readable);
    }

    public static boolean saveSection(Section section) {
        return sectionRepository.saveSection(section);
    }

    public static boolean deleteSection(Section section) {
        return sectionRepository.deleteSection(section);
    }
}
