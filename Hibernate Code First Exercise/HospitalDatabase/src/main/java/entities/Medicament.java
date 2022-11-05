package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Medicament extends BaseEntity{
    @Column(nullable = false, length = 40)
    private String name;

    @ManyToMany(mappedBy = "prescriptions")
    private Set<Patient> patients;


}
