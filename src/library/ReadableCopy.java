package library;

public class ReadableCopy extends Entity {
    private Readable readable;

    public ReadableCopy(Readable readable) {
        super();
        this.readable = readable;
    }

    public Readable getReadable() {
        return readable;
    }

    public void setReadable(Readable readable) {
        this.readable = readable;
    }
}
