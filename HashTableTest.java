/*
 * @author Amittai Aviram - aviram@bc.edu
 */

import java.util.Scanner;

public class HashTableTest {

  public static <K, V> void printMap(Map<K, V> map) {
    for (MapEntry<K, V> entry : map.entrySet()) {
      System.out.println(entry);
    }
  }

  public static void main(String[] args) {
    Map<Integer, String> map = new HashTable<>(true);
    Scanner scanner = new Scanner(System.in);
    System.out.println("Adding elements.");
    System.out.print("> ");
    while (scanner.hasNextInt()) {
      map.put(scanner.nextInt(), scanner.next());
      System.out.print("> ");
    }
    scanner.next();
    printMap(map);
    System.out.println("");
    System.out.println("Retrieving elements.");
    System.out.print("> ");
    while (scanner.hasNextInt()) {
      System.out.println(map.get(scanner.nextInt()));
      System.out.print("> ");
    }
    scanner.next();
    System.out.println("");
    System.out.println("Removing elements.");
    System.out.print("> ");
    while (scanner.hasNextInt()) {
      System.out.println(map.remove(scanner.nextInt()));
      System.out.print("> ");
    }
    printMap(map);
  }
}
