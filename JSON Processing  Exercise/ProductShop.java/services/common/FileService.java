package com.softuni.productsshop.services.common;

import java.io.IOException;

public interface FileService {
    <T> T[] readFile(String filePath, Class<?> clazz) throws IOException;
    //    <T> void writeToFile(String filePath, Iterable<T> records) throws IOException;
    <T> void writeToFile(String filePath, T record) throws IOException;
}
