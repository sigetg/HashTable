/*
 * Author: Amittai Aviram - aviram@bc.edu
 */

public interface Map<K, V> {

  V get(K key);
  V put(K key, V value);
  V remove(K key);
  Iterable<K> keySet();
  Iterable<V> values();
  Iterable<MapEntry<K, V>> entrySet();
  int size();
  boolean isEmpty();
}
