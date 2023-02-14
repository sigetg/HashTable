/*
 * Author: Amittai Aviram - aviram@bc.edu
 */

public interface MapEntry<K, V> {

  K getKey();
  V getValue();
  void setValue(V value);
}
