import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

    // average number of entries per map before we grow the map
    private static final double ALPHA = 1.0;
    // average number of entries per map before we shrink the map
    private static final double BETA = .25;

    // resizing factor: (new size) = (old size) * (resize factor)
    private static final double SHRINK_FACTOR = 0.5, GROWTH_FACTOR = 2.0;

    private static final int MIN_BUCKETS = 16;

    // array of buckets
    protected LinkedList<Entry>[] buckets;
    private int size = 0;

    public MyHashMap() {
        initBuckets(MIN_BUCKETS);
    }

    public class Entry implements Map.Entry<K, V> {
        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            value = newValue;
            return value;
        }
    }

    /**
     * given a key, return the bucket where the `K, V` pair would be stored if it were in the map.
     */
    private LinkedList<Entry> chooseBucket(Object key) {
        return buckets[key.hashCode() % buckets.length];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * return true if key is in map
     */
    @Override
    public boolean containsKey(Object key) {
        LinkedList<Entry> bucket = chooseBucket(key);
        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * return true if value is in map
     */
    @Override
    public boolean containsValue(Object value) {
        for (LinkedList<Entry> bucket : buckets) {
            for (Entry entry : bucket) {
                V eVal = entry.getValue();
                // First check necessary for if value is null
                if (value == eVal || (eVal != null && eVal.equals(value))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        LinkedList<Entry> bucket = chooseBucket(key);
        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * add a new key-value pair to the map. Grow if needed, according to `ALPHA`.
     * @return the value associated with the key if it was previously in the map, otherwise null.
     */
    @Override
    public V put(K key, V value) {
        // Grow if necessary
        if (size == buckets.length * ALPHA) {
            // Time to grow!
            rehash(GROWTH_FACTOR);
        }
        LinkedList<Entry> bucket = chooseBucket(key);
        V oldValue = null;
        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                oldValue = entry.getValue();
                entry.setValue(value);
                break;
            }
        }
        if (oldValue == null) {
            bucket.add(new Entry(key, value));
            ++size;
        }
        return oldValue;
    }

    /**
     * Remove the key-value pair associated with the given key. Shrink if needed, according to `BETA`.
     * Make sure the number of buckets doesn't go below `MIN_BUCKETS`. Do nothing if the key is not in the map.
     * @return the value associated with the key if it was in the map, otherwise null.
     */
    @Override
    public V remove(Object key) {
        LinkedList<Entry> bucket = chooseBucket(key);
        V value = null;
        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                value = entry.getValue();
                bucket.remove(entry);
                --size;
                break;
            }
        }
        // Shrink if necessary
        if (buckets.length > MIN_BUCKETS && size == buckets.length * BETA) {
            // Time to shrink!
            rehash(SHRINK_FACTOR);
        }
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Changes the number of buckets and rehashes the existing entries.
     * If growthFactor is 2, the number of buckets doubles. If it is 0.25,
     * the number of buckets is divided by 4.
     */
    private void rehash(double growthFactor) {
        // Save all the old values
        Set<Map.Entry<K, V>> items = entrySet();
        // Resize the array
        initBuckets((int)(buckets.length * growthFactor));
        // Place the items back in the buckets
        for (Map.Entry<K, V> item : items) {
            this.put(item.getKey(), item.getValue());
        }
    }

    private void initBuckets(int size) {
        buckets = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = new LinkedList<>();
        }
        this.size = 0;
    }

    public void clear() {
        initBuckets(buckets.length);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Map.Entry<K, V> e : entrySet()) {
            keys.add(e.getKey());
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Map.Entry<K, V> e : entrySet()) {
            values.add(e.getValue());
        }
        return values;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (LinkedList<Entry> map : buckets) {
            set.addAll(map);
        }
        return set;
    }
}