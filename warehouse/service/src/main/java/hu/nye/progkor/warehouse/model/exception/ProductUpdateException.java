package hu.nye.progkor.warehouse.model.exception;

public class ProductUpdateException extends RuntimeException {

    public ProductUpdateException(final String message) {
        super(message);
    }

    public ProductUpdateException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
