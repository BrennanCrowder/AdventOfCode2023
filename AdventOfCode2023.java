import java.io.*;
import java.util.*;
import java.util.Hashtable;
import java.util.regex.*;

public class AdventOfCode2023 {
  public static void main(String[] args) {
    float day_part = getDayPart(args);
    try {
      if (day_part == 1.1f)
        solutionDay1_1();
      if (day_part == 1.2f)
        solutionDay1_2();
      if (day_part == 2.1f)
        solutionDay2_1();
      if (day_part == 2.2f)
        solutionDay2_2();
      if (day_part == 3.1f)
        solutionDay3_1();
      if (day_part == 3.2f)
        solutionDay3_2();

    } catch (IOException e) {
    }
  }

  public static void solutionDay1_1() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("./1_1Data.txt"));
    String pattern = "^[a-z]*(\\d?).*(\\d)[a-z]*$";
    int sum = 0;
    while (reader.ready()) {
      String line = reader.readLine();

      Matcher matchy = Pattern.compile(pattern).matcher(line);
      if (matchy.find()) {
        int first = 0;
        if (matchy.group(1) != null && !matchy.group(1).isEmpty()) {
          first = Integer.parseInt(matchy.group(1));
        }

        int second = 0;
        if (matchy.group(2) != null && !matchy.group(2).isEmpty()) {
          second = Integer.parseInt(matchy.group(2));
        }
        if (first == 0) {
          second = second * 10 + second;
        }
        first *= 10;

        sum += first + second;
      }
    }
    System.out.println("Sum: " + sum);

    reader.close();
  }

  public static void solutionDay1_2() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("./1_1Data.txt"));

    String valid = "\\d|one|two|three|four|five|six|seven|eight|nine";
    String patternString = "^.*?(" + valid + ")+?|.*(" + valid + ").*$";
    Pattern pattern = Pattern.compile(patternString);

    int sum = 0;
    while (reader.ready()) {
      String line = reader.readLine();
      Matcher matchy = pattern.matcher(line);

      // Matches on first 'number' in string
      if (matchy.find()) {

        // Converts "one","2","3","four",etc. to int"
        int first = getNumber(matchy.group(1));

        int second = 0;
        // Matches on last 'number' in string
        if (matchy.find()) {
          second = getNumber(matchy.group(2));
        }

        second = second == 0 ? first : second;
        sum += (first * 10) + second;
      }
    }

    System.out.println("Sum: " + sum);
    reader.close();
  }

  public static void solutionDay2_1() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("./2_1Data.txt"));
    Hashtable<String, Integer> table = new Hashtable<String, Integer>();
    table.put("red", 12);
    table.put("green", 13);
    table.put("blue", 14);

    int sum = 0;
    String patternString = "(?<gameID>\\d+)?:?\\s(?<count>\\d+)\\s(?<color>blue|red|green)";
    Pattern pattern = Pattern.compile(patternString);

    while (reader.ready()) {
      String line = reader.readLine();
      // System.out.println(line);
      Matcher matchy = pattern.matcher(line);
      int gameID = -1;

      // Iterate through game
      while (matchy.find()) {
        if (gameID == -1) {
          gameID = Integer.parseInt(matchy.group("gameID"));
        }

        String color = matchy.group("color");
        int count = Integer.parseInt(matchy.group("count"));
        // System.out.println("Game " + gameID + ": " + color + " " + count);

        if (table.get(color) < count) {
          gameID = 0;
          // System.out.println("Violation: " + color + " " + count);
          break;
        }
      }
      sum += gameID;
    }

    System.out.println(sum);

    reader.close();
  }

  public static void solutionDay2_2() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("./2_1Data.txt"));

    int sum = 0;
    String patternString = "\\s(?<count>\\d+)\\s(?<color>blue|red|green)";
    Pattern pattern = Pattern.compile(patternString);

    while (reader.ready()) {
      String line = reader.readLine();
      Matcher matchy = pattern.matcher(line);

      int minRed = Integer.MIN_VALUE;
      int minGreen = Integer.MIN_VALUE;
      int minBlue = Integer.MIN_VALUE;

      // Iterate through game
      while (matchy.find()) {
        String color = matchy.group("color");
        int count = Integer.parseInt(matchy.group("count"));

        if (color.equals("red")) {
          if (minRed < count) {
            minRed = count;
          }

        } else if (color.equals("blue")) {
          if (minBlue < count) {
            minBlue = count;
          }

        } else if (color.equals("green")) {
          if (minGreen < count) {
            minGreen = count;
          }
        }
      }
      sum += (minRed * minBlue * minGreen);
    }

    System.out.println(sum);
    reader.close();
  }

  public static void solutionDay3_1() throws IOException {
    BufferedReader reader = getReader("./3_1Data.txt");
    String[] lines = reader.lines().toArray(String[]::new);
    int sum = 0;
    // For each line
    for (int i = 0; i < lines.length; i++) {
      // For each character in line
      char[] charArr = lines[i].toCharArray();
      for (int j = 0; j < charArr.length; j++) {
        if (Character.isDigit(charArr[j])) {
          int endIndex = getEndNumberIndex(charArr, j);
          boolean symbol = hasSymbol(lines, i, j, endIndex);
          int number = getNumber(combineNumberChar(charArr, j, endIndex));
          j = endIndex;
          if (symbol) {
            sum += number;
          }
          // System.out.println(number + ": " + symbol);
        }
      }
      // break;
    }
    System.out.println(sum);
    reader.close();
  }

  public static void solutionDay3_2() throws IOException {
    {
      BufferedReader reader = getReader("./3_1Data.txt");
      String[] lines = reader.lines().toArray(String[]::new);
      int sum = 0;
      // For each line
      for (int i = 0; i < lines.length; i++) {
        // For each character in line
        char[] charArr = lines[i].toCharArray();
        for (int j = 0; j < charArr.length; j++) {
          if (charArr[j] == '*') {
            int mult = getConnectedNumbers(lines, i, j);
            if (mult != -1) {
              sum += mult;
            }
          }
        }
        // break;
      }
      System.out.println(sum);
      reader.close();
    }
  }

  private static int getConnectedNumbers(String[] arr, int line, int index) {
    List<Integer> numbers = new ArrayList<Integer>();
    // Above
    if (line - 1 >= 0) {
      // if above is number
      if (Character.isDigit(arr[line - 1].charAt(index))) {

        numbers.add(getNumberFromIndex(arr[line - 1].toCharArray(), index));
      } else {
        // if above left
        if (index - 1 >= 0) {
          if (Character.isDigit(arr[line - 1].charAt(index - 1))) {

            numbers.add(
                getNumberFromIndex(arr[line - 1].toCharArray(), index - 1));
          }
        }
        // if above right
        if (index + 1 < arr[line - 1].length()) {
          if (Character.isDigit(arr[line - 1].charAt(index + 1))) {

            numbers.add(
                getNumberFromIndex(arr[line - 1].toCharArray(), index + 1));
          }
        }
      }
    }
    // Below
    if (line + 1 < arr.length) {
      if (Character.isDigit(arr[line + 1].charAt(index))) {
        numbers.add(getNumberFromIndex(arr[line + 1].toCharArray(), index));
      } else {
        // if below left
        if (index - 1 >= 0) {

          if (Character.isDigit(arr[line + 1].charAt(index - 1))) {
            numbers.add(
                getNumberFromIndex(arr[line + 1].toCharArray(), index - 1));
          }
        }
        // if below right
        if (index + 1 < arr[line + 1].length()) {
          if (Character.isDigit(arr[line + 1].charAt(index + 1))) {
            numbers.add(
                getNumberFromIndex(arr[line + 1].toCharArray(), index + 1));
          }
        }
      }
    }
    // Left
    if (index - 1 >= 0) {
      if (Character.isDigit(arr[line].charAt(index - 1))) {
        numbers.add(getNumberFromIndex(arr[line].toCharArray(), index - 1));
      }
    }
    // Right
    if (index + 1 < arr[line].length()) {
      if (Character.isDigit(arr[line].charAt(index + 1))) {
        numbers.add(getNumberFromIndex(arr[line].toCharArray(), index + 1));
      }
    }
    if (numbers.size() == 2) {
      // System.out.println(numbers.get(0) + " " + numbers.get(1));
      return (numbers.get(0) * numbers.get(1));
    } else {
      return -1;
    }
  }

  private static int getNumberFromIndex(char[] arr, int i) {
    while (i - 1 >= 0 && Character.isDigit(arr[i - 1])) {
      i--;
    }
    return Integer.parseInt(
        combineNumberChar(arr, i, getEndNumberIndex(arr, i)));
  }

  private static String combineNumberChar(char[] arr, int start, int end) {
    StringBuilder str = new StringBuilder();
    for (int i = start; i < end; i++) {
      str.append(arr[i]);
    }

    return str.toString();
  }

  private static boolean hasSymbol(String[] arr, int line, int start, int end) {

    boolean symbol = false;
    if (line - 1 > 0) {
      symbol |= hasSymbol(arr[line - 1].toCharArray(), start, end);
    }
    if (line + 1 < arr.length) {
      symbol |= hasSymbol(arr[line + 1].toCharArray(), start, end);
    }
    if (start - 1 >= 0) {
      symbol |= isSymbol(arr[line].charAt(start - 1));
    }
    if (end + 1 < arr[line].length()) {
      symbol |= isSymbol(arr[line].charAt(end));
    }
    return symbol;
  }

  private static boolean hasGear(String[] arr, int line, int start, int end) {

    boolean symbol = false;

    if (line + 1 < arr.length) {
      symbol |= hasGear(arr[line + 1].toCharArray(), start, end);
    }
    if (end + 1 < arr[line].length()) {
      symbol |= arr[line].charAt(end) == '*';
    }
    return symbol;
  }

  private static boolean hasGear(char[] arr, int start, int end) {
    start = Math.max(start - 1, 0);
    end = Math.min(end + 1, arr.length);

    for (int i = start; i < end; i++) {
      if (arr[i] == '*') {
        return true;
      }
    }
    return false;
  }

  private static boolean hasSymbol(char[] arr, int start, int end) {
    start = Math.max(start - 1, 0);
    end = Math.min(end + 1, arr.length);

    for (int i = start; i < end; i++) {
      if (isSymbol(arr[i])) {
        return true;
      }
    }
    return false;
  }

  private static boolean isSymbol(char c) {
    return !Character.isDigit(c) && c != '.';
  }

  private static int getEndNumberIndex(char[] arr, int start) {
    for (int i = start; i < arr.length; i++) {
      if (!Character.isDigit(arr[i])) {
        return i;
      }
    }
    return arr.length;
  }

  private static int getNumber(String num) {
    int number = 0;
    switch (num) {
      case "one":
        number = 1;
        break;
      case "two":
        number = 2;
        break;
      case "three":
        number = 3;
        break;
      case "four":
        number = 4;
        break;
      case "five":
        number = 5;
        break;
      case "six":
        number = 6;
        break;
      case "seven":
        number = 7;
        break;
      case "eight":
        number = 8;
        break;
      case "nine":
        number = 9;
        break;
      default:
        number = Integer.parseInt(num);
    }

    return number;
  }

  private static BufferedReader getReader(String filePath) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    return reader;
  }

  private static float getDayPart(String[] args) {
    float day_part = 0.0f;
    if (args != null) {
      day_part = Float.parseFloat(args[0]);
    }
    return day_part;
  }
}
