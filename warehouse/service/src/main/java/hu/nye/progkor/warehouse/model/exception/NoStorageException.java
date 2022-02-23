package hu.nye.progkor.warehouse.model.exception;

import java.io.Serial;

public class NoStorageException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4771967027378481188L;

    public NoStorageException(final String message) {
        super(message);
    }

    public NoStorageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
