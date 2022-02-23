package hu.nye.progkor.warehouse.model;

public enum FoodStorageType {

    NORMAL_FOOD("Szárazon"),        // Normál, száraz helyen való tárolás szükséges.
    TO_BE_FROZEN_FOOD("Fagyasztva"),  // Hűtött tárolás szükséges (6 fokon szükséges tárolni)
    TO_BE_COOLED_FOOD("Hűtve");   // Fagyasztásos tárolás szükséges (-16 celsius fokon)

    private final String value;

    FoodStorageType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
