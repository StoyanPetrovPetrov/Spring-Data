package com.example.football.models.entity;

import com.example.football.util.Messages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stats")
public class Stat extends BaseEntity{

    @Column
    private float shooting;

    @Column
    private float passing;

    @Column
    private float endurance;
    @Override
    public String toString() {
        return shooting + Messages.DASH + passing + Messages.DASH + endurance;
    }

}
