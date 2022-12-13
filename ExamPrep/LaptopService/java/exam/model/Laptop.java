package exam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String macAddress;

    @Column(nullable = false)
    private double cpuSpeed;

    @Column(nullable = false)
    private int ram;

    @Column(nullable = false)
    private int storage;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false,columnDefinition = "DECIMAL(19,2)")
    private double price;

    @Column(nullable = false)
    private WarrantyType warrantyType;

    @ManyToOne
    private Shop shop;

    @Override
    public String toString() {
        return String.format("Laptop - %s\n" +
                        "*Cpu speed - %.2f\n" +
                        "**Ram - %d\n" +
                        "***Storage - %d\n" +
                        "****Price - %.2f\n" +
                        "#Shop name - %s\n" +
                        "##Town - %s\n",
                this.macAddress,
                this.cpuSpeed,
                this.ram,
                this.storage,
                this.price,
                this.shop.getName(),
                this.shop.getTown().getName());
    }





}
