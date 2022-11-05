package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class PlayerStatistics implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "scoredGoals")
    private short scoredGoals;

    @Column(name = "assists")
    private short assists;

    @Column(name = "minutes_played")
    private short minutesPlayed;
}
