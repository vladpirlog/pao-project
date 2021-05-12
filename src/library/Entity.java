package library;

import java.util.Date;
import java.util.UUID;

public class Entity {
    private UUID id;
    private Date creationDate;

    protected Entity(UUID id, Date creationDate) {
        this.id = id;
        this.creationDate = creationDate;
    }

    protected Entity() {
        this(UUID.randomUUID(), new Date());
    }

    public UUID getID() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
