package hu.nye.progkor.warehouse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer netValue;

    private double size;

    private FoodStorageType suggestedFoodStorageType;

    private String description;
}
