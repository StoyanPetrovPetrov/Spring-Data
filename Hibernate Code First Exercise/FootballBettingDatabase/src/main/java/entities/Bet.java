package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Bet extends BaseEntity{
    @Column
    private BigDecimal betMoney;

    @Column
    private Date timeOfBet;

    @ManyToOne
    private User user;
}
