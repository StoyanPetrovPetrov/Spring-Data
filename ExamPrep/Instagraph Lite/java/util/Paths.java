package softuni.exam.instagraphlite.util;

import java.nio.file.Path;

public enum Paths {
    ;

    public static final Path PICTURES_JSON_PATH =
            Path.of("src", "main", "resources", "files", "pictures.json");

    public static final Path POSTS_XML_PATH =
            Path.of("src", "main", "resources", "files", "posts.xml");

    public static final Path USERS_JSON_PATH =
            Path.of("src", "main", "resources", "files", "users.json");
}
