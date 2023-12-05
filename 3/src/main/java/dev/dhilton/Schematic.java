package dev.dhilton;

import java.util.ArrayList;
import java.util.List;

public class Schematic {
    private List<List<Character>> raw;
    private final String SYMBOL_REGEX = "[^\\.|\\d]";

    public Schematic(List<List<Character>> raw) {
        this.raw = raw;
    }

    public int GetPartNumberTotal() {
        int total = 0;
        for (int i = 0; i < raw.size(); i++) {
            List<Integer> partNos = this.ParsePartNumbersInLine(i);
            int diff = partNos.stream().reduce(0, Integer::sum);
            System.out.println("ADDING: " + diff);
            total += diff;
            System.out.println("TOTAL: " + total);
        }

        return total;
    }

    private List<Integer> ParsePartNumbersInLine(int lineNumber) {
        System.out.println("Line: " + lineNumber);
        List<Integer> result = new ArrayList<>();
        List<Character> prevLine = null;
        if (lineNumber != 0) {
            prevLine = raw.get(lineNumber - 1);
        }

        List<Character> line = raw.get(lineNumber);
        List<Character> nextLine = null;
        if (lineNumber != raw.size() - 1) {
            nextLine = raw.get(lineNumber + 1);
        }

        boolean foundInt = false;
        String currentPartNumber = "";
        boolean isValidPartNumber = false;

        for (int i = 0; i < line.size(); i++) {
            if (!Character.isDigit(line.get(i))) {
                if (foundInt) {
                    if (isValidPartNumber) {
                        System.out.println(currentPartNumber);
                        result.add(Integer.parseInt(currentPartNumber));
                    }
                }

                isValidPartNumber = false;
                currentPartNumber = "";
                foundInt = false;
            } else {
                foundInt = true;
                currentPartNumber += line.get(i);
                boolean checkLeft = i > 0;
                boolean checkRight = i < line.size() - 1;

                // Find adjacent characters and check them if we've not found its valid yet
                if (!isValidPartNumber) {
                    if (checkLeft) {
                        isValidPartNumber = line.get(i - 1).toString().matches(SYMBOL_REGEX);
                    }
                    if (!isValidPartNumber && checkRight) {
                        isValidPartNumber = line.get(i + 1).toString().matches(SYMBOL_REGEX);
                    }

                    if (!isValidPartNumber && prevLine != null) {
                        if (checkLeft) {
                            isValidPartNumber = prevLine.get(i - 1).toString().matches(SYMBOL_REGEX);
                        }
                        if (!isValidPartNumber && checkRight) {
                            isValidPartNumber = prevLine.get(i + 1).toString().matches(SYMBOL_REGEX);
                        }

                        if (!isValidPartNumber) {
                            isValidPartNumber = prevLine.get(i).toString().matches(SYMBOL_REGEX);
                        }
                    }

                    if (!isValidPartNumber && nextLine != null) {
                        if (checkLeft) {
                            isValidPartNumber = nextLine.get(i - 1).toString().matches(SYMBOL_REGEX);
                        }

                        if (!isValidPartNumber && checkRight) {
                            isValidPartNumber = nextLine.get(i + 1).toString().matches(SYMBOL_REGEX);
                        }

                        if (!isValidPartNumber) {
                            isValidPartNumber = nextLine.get(i).toString().matches(SYMBOL_REGEX);
                        }
                    }
                }
            }
        }

        return result;
    }
}
