package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Player extends BaseEntity{
    @Column(nullable = false)
    private String name;

    @Column(name = "squad_number", nullable = false)
    private short squadNumber;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Position position;

    @Column(name = "is_currently_injured")
    private Boolean isCurrentlyInjured;
}
