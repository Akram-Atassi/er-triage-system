/**
 * HashT_LookUp
 *
 * A generic hash table implementation for storing key-value pairs.
 * Used for instant patient lookup in the ER system.
 *
 * Example:
 *   HashT_LookUp<String, Patient> patientIndex = new HashT_LookUp<>();
 *   patientIndex.insert(patient.getId(), patient);
 *   Patient found = patientIndex.search("P001");
 *
 * Collision strategy: separate chaining with a singly-linked list in each bucket.
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public class HashT_LookUp<K, V> {


    private static final int DEFAULT_CAPACITY = 0; // Example
    private static final double MAX_LOAD_FACTOR = 0; // Example

    /**
     * A single key-value pair stored in one bucket chain.
     */
    private class HashNode {
        K key; // This is the key
        V value; // This is the value inserting
        HashNode next;

        HashNode(K key, V value) { // constructor
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private HashNode[] buckets;
    private int size;

    /**
     * Constructs an empty hash table with default capacity.
     */
    public HashT_LookUp() {
        this(DEFAULT_CAPACITY); /* This creates an empty hash table with
        the default capacity which is (n) buckets */

    }

    /**
     * Constructs an empty hash table with a custom starting capacity.
     *
     * @param capacity Initial number of buckets
     */
    @SuppressWarnings("unchecked")
    public HashT_LookUp(int capacity) { // Creates the hash table with the buckets specified there
        if (capacity <= 0) { // ofc it has to be + capacity
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        this.buckets = (HashNode[]) new HashT_LookUp.HashNode[capacity];
        this.size = 0;
    }

    /**
     * Inserts a key-value pair into the hash table.
     * If the key already exists, the value is updated.
     *
     * @param key   The lookup key, such as a patient ID
     * @param value The value associated with the key, such as a Patient
     */
    public void insert(K key, V value) { // Adds KVP to the table.
        if (key == null) { // Sanity Check
            throw new IllegalArgumentException("Key cannot be null.");
        }
        if (value == null) { // Sanity Check if u want them to be System.out.println you can change them
            throw new IllegalArgumentException("Value cannot be null.");
        }

        if (loadFactor() >= MAX_LOAD_FACTOR) {
            resize();
        }

        int index = getBucketIndex(key);
        HashNode current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        HashNode newNode = new HashNode(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
    }

    /**
     * Alias for insert, useful if you prefer map-style naming.
     */
    public void put(K key, V value) {
        insert(key, value);
    }

    /**
     * Searches for a value by key.
     *
     * @param key The key to search for
     * @return The matching value, or null if no entry exists
     */
    public V search(K key) { // This is the get method previously seen
        if (key == null) {
            return null;
        }

        int index = getBucketIndex(key);
        HashNode current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Checks whether a key exists in the hash table.
     *
     * @param key The key to check
     * @return true if the key exists, false otherwise
     */
    public boolean containsKey(K key) { // This is the contains method previously seen
        if (key == null) {
            return false;
        }

        int index = getBucketIndex(key);
        HashNode current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /**
     * Deletes a key-value pair from the hash table.
     *
     * @param key The key to delete
     * @return true if the key was found and deleted, false otherwise
     */
    public boolean delete(K key) { // This is the delete method previously seen
        if (key == null) {
            return false;
        }

        int index = getBucketIndex(key);
        HashNode current = buckets[index];
        HashNode previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }

            previous = current;
            current = current.next;
        }

        return false;
    }

    /**
     * Returns the number of key-value pairs stored in the table.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the hash table has no entries.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all entries but keeps the current bucket capacity.
     */
    @SuppressWarnings("unchecked")
    public void clear() { // Clears the table
        this.buckets = (HashNode[]) new HashT_LookUp.HashNode[buckets.length];
        this.size = 0;
    }
    /**
     * Converts a key hash code into a valid bucket index.
     */
    private int getBucketIndex(K key) { // This is the hashing method bascially.
        return Math.abs(key.hashCode() % buckets.length);
    }

    /**
     * Returns the current load factor.
     */
    private double loadFactor() { // Interesting method that AI gave me, might wanna take a look but if you think
        // It's unecessary delete it.
        return (double) size / buckets.length;
    }

    /**
     * Doubles the number of buckets and re-inserts existing entries.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        // Same thing that resizing the array when full, but with the HashTable.
        HashNode[] oldBuckets = buckets;
        buckets = (HashNode[]) new HashT_LookUp.HashNode[oldBuckets.length * 2];

        int oldSize = size;
        size = 0;

        for (int i = 0; i < oldBuckets.length; i++) {
            HashNode current = oldBuckets[i];
            while (current != null) {
                insert(current.key, current.value);
                current = current.next;
            }
        }

        size = oldSize;
    }
}
