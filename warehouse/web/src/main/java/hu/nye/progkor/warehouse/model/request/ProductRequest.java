package hu.nye.progkor.warehouse.model.request;


import hu.nye.progkor.warehouse.model.FoodStorageType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@Getter
@JsonDeserialize(builder = ProductRequest.ProductRequestBuilder.class)
@ToString
public class ProductRequest {

    private final String name;
    private final Integer netValue;
    private final double size;
    private final FoodStorageType foodStorageType;
    private final String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductRequestBuilder {

    }
}
