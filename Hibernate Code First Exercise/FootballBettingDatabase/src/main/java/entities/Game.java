package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Game extends BaseEntity implements Serializable {
    @OneToOne
    @JoinColumn
    private Team homeTeam;

    @OneToOne
    @JoinColumn
    private Team awayTeam;

    @Column
    private Short homeGoals;

    @Column
    private Short awayGoals;

    @Column
    private Date dateAndTime;

    @Column
    private double homeTeamWinBetRate;

    @Column
    private double awayTeamWinBetRate;

    @Column
    private double drawGameBetRate;

    @ManyToOne
    @JoinColumn
    private Round round;

    @ManyToOne
    @JoinColumn
    private Competition competition;

}
