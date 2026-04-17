package ru.uno.soft.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LineConverter {

  private static final String ELEMENT_REGEX = "\"([^\"]*)\";?";
  private static final String LINE_REGEX = "^(\"[^\"]*\";)*(\"[^\"]*\")$";

  public Boolean isCorrectLine(String line) {
    if (line.length() == 0) return false;
    Matcher matcher = getMatcher(line, LINE_REGEX);
    return matcher.matches();
  }

  public List<CellData> getCells(String line) {
    return filterEmptyCells(lineToCell(line));
  }

  private static Matcher getMatcher(String line, String regex) {
    Pattern pattern = Pattern.compile(regex);
    return pattern.matcher(line);
  }

  private List<CellData> lineToCell(String line) {
    Matcher matcher = getMatcher(line, ELEMENT_REGEX);
    List<CellData> cellDates = new ArrayList<>();
    while (matcher.find()) {
      String element = matcher.group(1);
      cellDates.add(new CellData(element,cellDates.size()));
    }
    return cellDates;
  }

  private List<CellData> filterEmptyCells(List<CellData> cellDates) {
    return cellDates.stream()
        .filter(cell -> !cell.element().isEmpty())
        .collect(Collectors.toList());
  }

}
