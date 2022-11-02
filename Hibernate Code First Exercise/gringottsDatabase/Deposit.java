package entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
@Entity
@Table(name= "magic_wand")
public class Deposit {
    @Id
    @Column
    private long id;
    @Column(length = 20)
    private long group;
    @Column
    private LocalDate startDate;
    @Column
    private Double amount;
    @Column
    private Double interest;
    @Column
    private Double charge;
    @Column
    private LocalDate expiration_date;
    @Column
    private boolean isExpired;
}
