package xyz.threewater.exception;

public class UnsupportedFileException extends RuntimeException {
    public UnsupportedFileException(Throwable cause) {
        super(cause);
    }

    public UnsupportedFileException(String message) {
        super(message);
    }
}
