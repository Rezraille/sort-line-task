package ru.uno.soft.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupProcessor {
  private final Map<CellData, Integer> groupIdByCell = new HashMap<>();
  private final Map<Integer, List<String>> lineByGroupId = new LinkedHashMap<>();
  private final Map<Integer, Set<Integer>> mergedGroups = new HashMap<>();
  private Integer counterGroup = 0;

  public Set<Integer> findMatchingGroupIds(List<CellData> cellDates) {
    if (groupIdByCell.size() == 0) {
      return Collections.emptySet();
    }
    Set<Integer> groupIds = cellDates.stream()
        .map(groupIdByCell::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
    return groupIds;
  }

  public void changeMapByGroupId(Set<Integer> groupIds, List<CellData> cellDates, String line) {

    Integer groupId;
    if (groupIds.isEmpty()) {
      groupId = createNewGroup(line);
    } else {
      if (groupIds.size() > 1) {
        mergeGroups(groupIds);
      }
      groupId = groupIds.iterator().next();
      addLineToGroup(groupId, line);
    }

    cellDates.forEach(cellData -> groupIdByCell.put(cellData, groupId));
  }

  public List<Set<String>> getSortedLines() {
    groupIdByCell.clear();
    Set<Set<String>> allLines = lineByGroupId.keySet()
        .stream()
        .map(this::getAllMergedGroups)
        .distinct()
        .map(this::getAllLinesByGroups)
        .collect(Collectors.toSet());

    return allLines.stream()
        .filter(lines -> lines.size() > 1)
        .sorted(Comparator.<Set<String>>comparingInt(Set::size).reversed())
        .toList();
  }

  private Set<Integer> getAllMergedGroups(Integer groupId)
  {
    return mergedGroups.getOrDefault(groupId, Set.of(groupId));
  }

  private Set<String> getAllLinesByGroups(Set<Integer> allMergedGroups)
  {
    return allMergedGroups.stream()
        .flatMap(groupId -> lineByGroupId.get(groupId).stream())
        .collect(Collectors.toSet());
  }

  private void mergeGroups(Set<Integer> groupIds) {
    Set<Integer> allGroups = new HashSet<>(groupIds);
    groupIds.forEach(groupId -> allGroups.addAll(mergedGroups.getOrDefault(groupId, Set.of())));

    allGroups.forEach(groupId -> mergedGroups.put(groupId, allGroups));
  }

  private int createNewGroup(String line) {
    counterGroup++;
    lineByGroupId.put(counterGroup, new ArrayList<>(List.of(line)));
    return counterGroup;
  }

  private void addLineToGroup(Integer groupId, String line) {
    List<String> lines = lineByGroupId.get(groupId);
    lines.add(line);
  }

}
