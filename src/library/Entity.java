package library;

import java.util.Date;
import java.util.UUID;

public class Entity {
    private UUID id;
    private Date creationDate;

    protected Entity(UUID id) {
        this.id = id;
        this.creationDate = new Date();
    }

    protected Entity() {
        this(UUID.randomUUID());
    }

    public UUID getID() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
