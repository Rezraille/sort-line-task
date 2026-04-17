package ru.uno.soft.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FileReader {
  public static Set<String> readFile(String filePath) {
    Path path = Path.of(filePath);
    Set<String> lines = new HashSet<>();
    try {
      lines = Files.lines(path).collect(Collectors.toSet());
    } catch (IOException exception) {
      System.out.println("exception" + exception.getMessage());
    }
    return lines;
  }
}
