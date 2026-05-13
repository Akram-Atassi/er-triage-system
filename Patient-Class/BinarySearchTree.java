/**
 * BinarySearchTree
 *
 * A generic Binary Search Tree implementation for storing key-value pairs.
 * Used to maintain a database of patients (key: "name|age", value: patient ID).
 *
 * Note: This implementation does NOT perform balancing (no rebalancing on insert/delete).
 *
 * @param <K> Key type (must be Comparable)
 * @param <V> Value type
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    /**
     * Node class representing a single entry in the BST.
     */
    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    /**
     * Constructs an empty Binary Search Tree.
     */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a key-value pair into the BST.
     * If the key already exists, the value is updated.
     *
     * @param key   The key to insert
     * @param value The value associated with the key
     */
    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        root = insertHelper(root, key, value);
    }

    /**
     * Helper method for recursive insertion.
     */
    private Node insertHelper(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }

        int comparison = key.compareTo(node.key);

        if (comparison < 0) {
            // Insert in left subtree
            node.left = insertHelper(node.left, key, value);
        } else if (comparison > 0) {
            // Insert in right subtree
            node.right = insertHelper(node.right, key, value);
        } else {
            // Key already exists, update value
            node.value = value;
        }

        return node;
    }

    /**
     * Searches for a value by key.
     *
     * @param key The key to search for
     * @return The value associated with the key, or null if not found
     */
    public V search(K key) {
        if (key == null) {
            return null;
        }
        return searchHelper(root, key);
    }

    /**
     * Helper method for recursive search.
     */
    private V searchHelper(Node node, K key) {
        if (node == null) {
            return null;
        }

        int comparison = key.compareTo(node.key);

        if (comparison < 0) {
            // Search in left subtree
            return searchHelper(node.left, key);
        } else if (comparison > 0) {
            // Search in right subtree
            return searchHelper(node.right, key);
        } else {
            // Key found
            return node.value;
        }
    }

    /**
     * Checks if a key exists in the BST.
     *
     * @param key The key to check
     * @return true if the key exists, false otherwise
     */
    public boolean containsKey(K key) {
        return search(key) != null;
    }

    /**
     * Deletes a key-value pair from the BST.
     *
     * @param key The key to delete
     * @return true if the key was found and deleted, false otherwise
     */
    public boolean delete(K key) {
        if (key == null || !containsKey(key)) {
            return false;
        }
        root = deleteHelper(root, key);
        size--;
        return true;
    }

    /**
     * Helper method for recursive deletion.
     */
    private Node deleteHelper(Node node, K key) {
        if (node == null) {
            return null;
        }

        int comparison = key.compareTo(node.key);

        if (comparison < 0) {
            // Delete from left subtree
            node.left = deleteHelper(node.left, key);
        } else if (comparison > 0) {
            // Delete from right subtree
            node.right = deleteHelper(node.right, key);
        } else {
            // Node to delete found

            // Case 1: Node has no children (leaf node)
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2: Node has one child
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            // Case 3: Node has two children
            // Find the in-order successor (smallest key in right subtree)
            Node successor = findMin(node.right);
            node.key = successor.key;
            node.value = successor.value;
            // Delete the successor node from right subtree
            node.right = deleteHelper(node.right, successor.key);
            size++; // Compensate for the extra decrement in delete()
        }

        return node;
    }

    /**
     * Finds the node with the minimum key in a subtree.
     */
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Returns the number of key-value pairs in the BST.
     *
     * @return The size of the BST
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the BST is empty.
     *
     * @return true if the BST contains no entries, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears all entries from the BST.
     */
    public void clear() {
        root = null;
        size = 0;
    }
}
