package hu.nye.progkor.warehouse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class WareHouse {

    @Id
    @GeneratedValue
    private Long id;

    private String location;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Storage> storages = new ArrayList<>();

    public List<Storage> getStorages() {
        return Collections.unmodifiableList(storages);
    }
}
