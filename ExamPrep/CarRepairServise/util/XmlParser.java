package softuni.exam.util;

import java.io.FileNotFoundException;
import javax.xml.bind.JAXBException;

public interface XmlParser {

    <T> T parseXml(Class<T> objectClass, String filePath) throws JAXBException, FileNotFoundException;
}
