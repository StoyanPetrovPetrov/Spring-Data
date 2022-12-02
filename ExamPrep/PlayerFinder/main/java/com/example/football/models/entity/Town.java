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
@Table(name = "towns")
public class Town extends BaseEntity{

    @Column(nullable = false,unique = true)
    private String name;

    @Column
    private int population;

    @Column(name = "travel_guide",nullable = false,columnDefinition = "TEXT")
    private String travelGuide;

    @Override
    public String toString() {
        return name + Messages.DASH + population;
    }
}
