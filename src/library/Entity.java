package library;

import java.util.Date;
import java.util.UUID;

import library.interfaces.Deletable;
import library.interfaces.Saveable;
import library.interfaces.Serializable;

public abstract class Entity implements Serializable, Saveable, Deletable {
    private UUID id;
    private Date creationDate;

    public Entity(UUID id, Date creationDate) {
        this.id = id;
        this.creationDate = creationDate;
    }

    public Entity() {
        this(UUID.randomUUID(), new Date());
    }

    public UUID getID() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
