package com.example.football.models.entity;

import com.example.football.util.Messages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player extends BaseEntity{

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(name = "birth_date",nullable = false)
    private LocalDate birthDate;

    @Column
    private PlayerPosition position;

    @ManyToOne(optional = false)
    private Town town;

    @ManyToOne(optional = false)
    private Team team;

    @OneToOne
    private Stat stat;
    @Override
    public String toString() {

        return String.format(Messages.PRINT_PLAYER, firstName, lastName) + System.lineSeparator() +
                String.format(Messages.PRINT_POSITION, position.toString()) + System.lineSeparator() +
                String.format(Messages.PRINT_TEAM, team.getName()) + System.lineSeparator() +
                String.format(Messages.PRINT_STADIUM, team.getStadiumName());
    }

    public String importInfo(){
        return firstName + Messages.INTERVAL + lastName + Messages.DASH + position.toString();
    }

}
