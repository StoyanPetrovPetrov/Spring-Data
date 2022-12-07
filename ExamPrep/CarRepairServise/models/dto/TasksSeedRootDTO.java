package softuni.exam.models.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TasksSeedRootDTO {

    @XmlElement(name = "task")
    private List<TaskSeedDTO> tasks;
}
