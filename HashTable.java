
/*
 * Author: George Sigety - sigetyg@bc.edu
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.lang.Math;

public class HashTable<K extends Comparable<K>, V> implements Map<K, V> {

    // preliminary stuff
    private enum Resize {UP, DOWN}
    private static final int INITIAL_CAPACITY = 17;
    private static final double MAX_LOAD_FACTOR = 0.7;
    private static final double MIN_LOAD_FACTOR = 0.2;
    private static final int PRIME = 109345121;
    // mutable data members
    private int capacity = INITIAL_CAPACITY;
    private ArrayList<Entry<K, V>>[] table;
    private int scale;
    private int shift;
    private int size;
    private double loadFactor = (double)size/capacity;

    // constructor
    public HashTable(boolean repeatable) {
        Random random = repeatable ? new Random(1) : new Random();
        scale = random.nextInt(PRIME - 1) + 1;
        shift = random.nextInt(PRIME);
        table = createTable();
    }

    public HashTable() {
        this(true);
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Entry<K, V>>[] createTable() {
        size = 0;
        ArrayList<Entry<K, V>>[] table = (ArrayList<Entry<K, V>>[]) new ArrayList[capacity];
        return table;
    }

    private int findIndex(K key) {
        int theIndex = (((scale * (Math.abs(key.hashCode()))) + shift) % PRIME) % capacity;
        return theIndex;
    }

    public V get(K key) {
        int index = findIndex(key);
        if (table[index] == null) {
            return null;
        }
        for (int i = 0; i < table[index].size(); i++) {
            if (table[index].get(i).getKey().compareTo(key) == 0) {
                return table[index].get(i).getValue();
            }
        }
        return null;
    }

    private V put(K key, V value, ArrayList<Entry<K, V>>[] indexArray) {
        int my_index = findIndex(key);
        if (table[my_index] != null) {
            for (int i = 0; i < table[my_index].size(); i++) {
                if (table[my_index].get(i).getKey().compareTo(key) == 0) {

                    V old_val = table[my_index].get(i).getValue();
                    table[my_index].get(i).setValue(value);
                    return old_val;
                }
            }
        } else {
            ArrayList<Entry<K, V>> newArray = new ArrayList<Entry<K, V>>();
            table[my_index] = newArray;
        }
        Entry<K, V> newEntry = new Entry<K, V>(key, value);
        int size = table[my_index].size();
        table[my_index].add(newEntry);
        size++;
        loadFactor = (double)size / (double) capacity;
        return newEntry.getValue();
    }

    public V put(K key, V value) {
        V new_val = put(key, value, table);
        if (loadFactor > MAX_LOAD_FACTOR) {
            resizeTable(Resize.UP);
        }
        return new_val;
    }

    public void resizeTable(Resize direction) {
        size = 0;
        if (direction == Resize.UP) {
            capacity *= 2;
        } else {
            capacity /= 2;
        }
        PrimeFinder finder = new PrimeFinder((int) Math.ceil(capacity * 1.5));
        int Prime = 2;
        while (finder.hasNext() && Prime < capacity) {
            Prime = finder.next();
        }
        capacity = Prime;
        ArrayList<Entry<K, V>>[] newTable = createTable();
        for (ArrayList<Entry<K, V>> index : table) {
            if (index != null) {
                for (Entry<K, V> entry : index) {
                    put(entry.getKey(), entry.getValue(), newTable);
                }
            }
        }
        table = newTable;
    }

    public V remove(K key) {
        int index = findIndex(key);
        if (table[index] == null) {
            return null;
        }
        for (int i = 0; i < table[index].size(); i++) {
            if (table[index].get(i).getKey().compareTo(key) == 0) {
                size--;
                loadFactor = (double)size / (double) capacity;
                V my_valpiss = table[index].remove(i).getValue();
                if (table[index].isEmpty()) {
                  table[index] = null;
                }
                if (loadFactor < MIN_LOAD_FACTOR) {
                    resizeTable(Resize.DOWN);
                }
                return my_valpiss;
            }
        }
        return null;
    }

    public Iterable<MapEntry<K, V>> entrySet() {
        ArrayList<MapEntry<K, V>> snapshot = new ArrayList<>();
        for (int index = 0; index < capacity; ++index) {
            if (table[index] != null) {
                for (Entry<K, V> entry : table[index]) {
                    snapshot.add(entry);
                }
            }
        }
        return snapshot;
    }

    public Iterable<K> keySet() {
        KeyIterable iterable = new KeyIterable();
        return iterable;
    }

    @Override
    public Iterable<V> values() {
        ValueIterable iterator = new ValueIterable();
        return iterator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static class Entry<K extends Comparable<K>, V> implements MapEntry<K, V>, Comparable<Entry<K, V>> {

        private K key;
        private V value;

        // constructor
        public Entry(K key, V value) {
            this.value = value;
            this.key = key;
        }

        // methods
        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public int compareTo(Entry<K, V> Entry) {
            return key.compareTo(Entry.getKey());
        }

        @Override
        public String toString() {
            return key.toString() + ": " + value.toString();
        }
    }

    private class KeyIterator implements Iterator<K> {

        Iterator<MapEntry<K, V>> entries = entrySet().iterator();

        @Override
        public K next() {
            return entries.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return entries.hasNext();
        }
    }

    private class KeyIterable implements Iterable<K> {

        public KeyIterator iterator() {
            KeyIterator NewIterator = new KeyIterator();
            return NewIterator;
        }
    }

    private class ValueIterator implements Iterator<V> {

        Iterator<MapEntry<K, V>> entries = entrySet().iterator();

        @Override
        public V next() {
            return entries.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return entries.hasNext();
        }
    }

    private class ValueIterable implements Iterable<V> {

        public ValueIterator iterator() {
            ValueIterator Newiterator = new ValueIterator();
            return Newiterator;
        }
    }
}
