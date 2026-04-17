package ru.uno.soft;


import java.util.Iterator;
import java.util.Set;
import ru.uno.soft.util.FileReader;
import ru.uno.soft.util.FileWriter;
import ru.uno.soft.util.GroupProcessor;
import ru.uno.soft.util.LineConverter;


public class Main {
  public static void main(String[] args) {

    long start = System.nanoTime();
    if (args.length == 0) {
      System.out.println("Не передан путь к входному файлу");
      return;
    }
    String filePath = args[0];
    Set<String> lines = FileReader.readFile(filePath);


    GroupProcessor groupProcessor = new GroupProcessor();
    Processor processor = new Processor(new LineConverter(), groupProcessor);

    Iterator<String> iterator = lines.iterator();
    while (iterator.hasNext()) {
      String line = iterator.next();
      processor.process(line);
      iterator.remove();
    }


    FileWriter.writeToFile("src", "result.txt", groupProcessor.getSortedLines());//по заданию не понятно в какой файл выводить
    long end = System.nanoTime();
    double duration = (end - start) / 1_000_000_000.0;

    System.out.println(String.format("Время: %.2f c", duration));
  }

}