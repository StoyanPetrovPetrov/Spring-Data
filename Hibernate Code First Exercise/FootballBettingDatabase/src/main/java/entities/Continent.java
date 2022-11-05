package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Continent extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "continents")
    private Set<Country> countries;
}
