package library.repository.actions;

import java.util.Optional;
import java.util.UUID;

import library.Readable;
import library.Section;
import library.exceptions.SectionNotFoundException;

public interface SectionRepository {
    Section[] findAllSections();

    Optional<Section> findSectionByID(UUID id);

    Optional<Section> findSectionByName(String name);

    Section findSectionByReadable(Readable readable) throws SectionNotFoundException;

    boolean saveSection(Section section);

    boolean deleteSection(Section section);
}
