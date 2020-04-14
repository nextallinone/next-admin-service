package studio.clouthin.next.miniadmin.framework.menu.exceptions;

public class ResourceLoadException extends RuntimeException {

    public ResourceLoadException() {
    }

    public ResourceLoadException(String message) {
        super(message);
    }

    public ResourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceLoadException(Throwable cause) {
        super(cause);
    }
}
