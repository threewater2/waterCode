package xyz.threewater.exception;

public class BuildFailedException extends Exception{
    public BuildFailedException(String message) {
        super(message);
    }

    public BuildFailedException(Throwable cause) {
        super(cause);
    }
}
