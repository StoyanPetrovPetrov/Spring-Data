package com.example.football.models.dto;

import com.example.football.models.entity.PlayerPosition;
import com.example.football.util.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerImportDto {
    @XmlElement(name = "first-name")
    @Size(min = 3)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 3)
    private String lastName;

    @Email
    @NotNull
    @XmlElement
    private String email;

    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "birth-date")
    private LocalDate birthDate;

    @NotNull
    @XmlElement
    private PlayerPosition position;

    @XmlElement
    @NotNull
    private NameDto town;

    @XmlElement
    @NotNull
    private NameDto team;

    @XmlElement
    @NotNull
    private IdDto stat;
}
