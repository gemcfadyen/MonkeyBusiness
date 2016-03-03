package server;

public class RootDirectoryException extends RuntimeException {
    public RootDirectoryException(Throwable cause) {
       super("Exception thrown whilst determining repository root directory", cause);
    }
}
