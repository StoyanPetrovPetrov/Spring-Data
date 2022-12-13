package exam.util;

import java.nio.file.Path;

public enum Paths {
    ;

    public static final Path CUSTOMERS_JSON_PATH =
            Path.of("src", "main", "resources", "files", "json", "customers.json");

    public static final Path LAPTOPS_JSON_PATH =
            Path.of("src", "main", "resources", "files", "json", "laptops.json");

    public static final Path SHOPS_XML_PATH =
            Path.of("src", "main", "resources", "files", "xml", "shops.xml");

    public static final Path TOWNS_XML_PATH =
            Path.of("src", "main", "resources", "files", "xml", "towns.xml");

}
