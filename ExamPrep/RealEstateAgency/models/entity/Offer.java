package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.util.Messages;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "local_date",nullable = false)
    private LocalDate publishedOn;

    @ManyToOne
    private Apartment apartment;

    @ManyToOne
    private Agent agent;

    public String importInfo() {
        return String.format(Messages.FORMAT_DOUBLE, this.price);
    }

    @Override
    public String toString() {

        return String.format(Messages.PRINT_AGENT, this.agent.getFirstName(), this.agent.getLastName(), this.getId()) +
                System.lineSeparator() +
                String.format(Messages.PRINT_APARTMENT_AREA, this.apartment.getArea()) + System.lineSeparator() +
                String.format(Messages.PRINT_TOWN, this.apartment.getTown().getTownName()) + System.lineSeparator() +
                String.format(Messages.PRINT_PRICE, this.price);
    }
}
