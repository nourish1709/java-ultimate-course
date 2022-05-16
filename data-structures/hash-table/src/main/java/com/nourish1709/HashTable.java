package com.nourish1709;

/**
 * A simple implementation of the Hash Table that allows storing a generic key-value pair. The table itself is based
 * on the array of {@link Node} objects.
 * <p>
 * An initial array capacity is 16.
 * <p>
 * Every time a number of elements is equal to the array size that tables gets resized
 * (it gets replaced with a new array that it twice bigger than before). E.g. resize operation will replace array
 * of size 16 with a new array of size 32. PLEASE NOTE that all elements should be reinserted to the new table to make
 * sure that they are still accessible  from the outside by the same key.
 *
 * @param <K> key type parameter
 * @param <V> value type parameter
 */
public class HashTable<K, V> {

    private static final int INITIAL_CAPACITY = 16;

    private Node<K, V>[] headNodes;
    private int size = 0;

    public HashTable() {
        headNodes = new Node[INITIAL_CAPACITY];
    }

    /**
     * Puts a new element to the table by its key. If there is an existing element by such key then it gets replaced
     * with a new one, and the old value is returned from the method. If there is no such key then it gets added and
     * null value is returned.
     *
     * @param key   element key
     * @param value element value
     * @return old value or null
     */
    public V put(K key, V value) {
        int arrayIndex = Math.abs(
                key.hashCode() % headNodes.length);

        if (headNodes[arrayIndex] == null) {
            headNodes[arrayIndex] = new Node<>(key, value);
            changeSize();
            return null;
        }

        Node<K, V> currentNode = headNodes[arrayIndex];

        while (currentNode.key != key &&
                currentNode.next != null) {
            currentNode = currentNode.next;
        }

        if (currentNode.key == key) {
            var previousValue = currentNode.value;
            currentNode.value = value;
            return previousValue;
        }

        currentNode.next = new Node<>(key, value);
        changeSize();
        return null;
    }

    private void changeSize() {
        if (++size >= headNodes.length) {
            resizeArray();
        }
    }

    private void resizeArray() {
        int newCapacity = headNodes.length * 2;
        Node<K, V>[] nodeCopies = new Node[headNodes.length];
        System.arraycopy(headNodes, 0, nodeCopies, 0, headNodes.length);

        headNodes = new Node[newCapacity];
        size = 0;

        for (Node<K, V> headNode : nodeCopies) {
            var currentNode = headNode;

            while (currentNode != null) {
                put(currentNode.key, currentNode.value);
                currentNode = currentNode.next;
            }
        }
    }

    /**
     * Prints a content of the underlying table (array) according to the following format:
     * 0: key1:value1 -> key2:value2
     * 1:
     * 2: key3:value3
     * ...
     */
    public void printTable() {
        for (int i = 0; i < headNodes.length; i++) {
            System.out.print(i + ": ");

            var currentNode = headNodes[i];
            while (currentNode != null) {
                System.out.print(currentNode.key + ":" + currentNode.value);
                currentNode = currentNode.next;
                if (currentNode != null) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }
}