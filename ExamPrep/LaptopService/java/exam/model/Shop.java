package exam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false,columnDefinition = "DECIMAL(19,2)")
    private double income;

    @Column(nullable = false)
    private String address;

    @Column(name = "employee_count",nullable = false)
    private int employeeCount;

    @Column(name = "shop_area",nullable = false)
    private int shopArea;

    @ManyToOne
    private Town town;

    @OneToMany(targetEntity = Laptop.class, mappedBy = "shop")
    private List<Laptop> laptops;


}
