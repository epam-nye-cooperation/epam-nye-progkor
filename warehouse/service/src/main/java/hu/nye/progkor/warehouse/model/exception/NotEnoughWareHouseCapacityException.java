package hu.nye.progkor.warehouse.model.exception;

import java.io.Serial;

public class NotEnoughWareHouseCapacityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8095457141059544951L;

    public NotEnoughWareHouseCapacityException(final String message) {
        super(message);
    }

    public NotEnoughWareHouseCapacityException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
