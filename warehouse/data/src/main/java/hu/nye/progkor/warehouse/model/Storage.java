package hu.nye.progkor.warehouse.model;

import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
public class Storage {

    @Id
    @GeneratedValue
    private Long id;

    private FoodStorageType foodStorageType;

    private Long capacity;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Product> products;

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProduct(final Product product) {
        products.add(product);
    }
}
