package entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class MagicWand {
    @Id
    @Column
    private long id;
    @Column(length = 100)
    private String creator;
    @Column
    private long size;

}
