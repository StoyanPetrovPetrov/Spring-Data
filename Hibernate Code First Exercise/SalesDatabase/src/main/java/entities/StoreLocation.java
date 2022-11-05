package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class StoreLocation extends baseEntity {
    @Column(length = 50, nullable = false, unique = true)
    private String locationName;

    @OneToMany(mappedBy = "storeLocation")
    private Set<Sale> sales;
}
