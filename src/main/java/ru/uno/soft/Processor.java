package ru.uno.soft;

import java.util.List;
import java.util.Set;
import ru.uno.soft.util.CellData;
import ru.uno.soft.util.GroupProcessor;
import ru.uno.soft.util.LineConverter;

public class Processor {
  private final GroupProcessor groupProcessor;

  private final LineConverter lineConverter;

  public Processor(LineConverter lineConverter,
                   GroupProcessor groupProcessor) {

    this.lineConverter = lineConverter;
    this.groupProcessor = groupProcessor;
  }

  public void process(String line) {
    if (!lineConverter.isCorrectLine(line)) {
      return;
    }
    List<CellData> cellDates = lineConverter.getCells(line);
    Set<Integer> matchingGroupIds = groupProcessor.findMatchingGroupIds(cellDates);
    groupProcessor.changeMapByGroupId(matchingGroupIds, cellDates, line);
  }

}
