package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.util.Messages;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apartments")
public class Apartment extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "apartment_type",nullable = false)
    private ApartmentType apartmentType;

    @Column
    private double area;

    @ManyToOne
    private Town town;

    @Override
    public String toString() {
        return apartmentType.toString() + Messages.DASH + String.format(Messages.FORMAT_DOUBLE, area);
    }

}
