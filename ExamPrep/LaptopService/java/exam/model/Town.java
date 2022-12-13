package exam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private int population;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String travelGuide;

    @OneToMany(targetEntity = Shop.class, mappedBy = "town")
    private List<Shop> shops;

    @OneToMany(targetEntity = Customer.class, mappedBy = "town")
    private List<Customer> customers;

}
