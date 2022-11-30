package softuni.exam.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

public class ReadFromXML {
    public static <T> T readFromXML(T data, Path path) throws FileNotFoundException, JAXBException {
        final FileReader fileReader = new FileReader(path.toFile());

        final JAXBContext context = JAXBContext.newInstance(data.getClass());
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(fileReader);
    }
}
