package ru.uno.soft.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;


public class FileWriter {

  public static void writeToFile(String pack, String name,
                                 List<Set<String>> allLines){
    Path path = Path.of(pack, name);
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      String groupLine = "Line ";
      int countGroup = 0;

      for (Set<String> lines : allLines) {
        countGroup++;
        writer.write(groupLine + countGroup);
        writer.newLine();
        for (String line : lines) {
          writer.write(line);
          writer.newLine();
        }
      }

      System.out.println(countGroup);
    } catch (IOException exception) {
      System.out.println("exception" + exception.getMessage());
    }
  }

}
