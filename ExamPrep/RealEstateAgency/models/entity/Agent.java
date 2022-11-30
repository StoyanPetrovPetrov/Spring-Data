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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agents")
public class Agent extends BaseEntity{

    @Column(name = "first_name",nullable = false,unique = true)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(unique = true,nullable = false)
    private String email;

    @ManyToOne
    private Town town;
    @Override
    public String toString() {
        return firstName + Messages.INTERVAL + lastName;
    }


}
