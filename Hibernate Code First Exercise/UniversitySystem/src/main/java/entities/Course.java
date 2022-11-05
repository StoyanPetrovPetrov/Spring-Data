package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Course extends baseEntity{
    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 3000, nullable = false)
    private String description;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private int credits;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;
}
