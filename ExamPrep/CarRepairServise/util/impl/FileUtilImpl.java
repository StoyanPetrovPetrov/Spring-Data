package softuni.exam.util.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import softuni.exam.util.FileUtil;

public class FileUtilImpl implements FileUtil {

    @Override
    public String readFile(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }
}
