package com.nourish1709;

import java.util.Stack;

public class ReversedLinkedList {

    public static void main(String[] args) {
        var head = createLinkedList(4, 3, 9, 1);
        printReversedRecursively(head);
        System.out.println();
        printReversedUsingStack(head);
    }

    /**
     * Creates a list of linked {@link Node} objects based on the given array of elements and returns a head of the list.
     *
     * @param elements an array of elements that should be added to the list
     * @param <T>      elements type
     * @return head of the list
     */
    public static <T> Node<T> createLinkedList(T... elements) {
        final var head = new Node<>(elements[0]);

        var currentNode = head;
        for (int i = 1; i < elements.length; i++) {
            currentNode.next = new Node<>(elements[i]);
            currentNode = currentNode.next;
        }

        return head;
    }

    /**
     * Prints a list in a reserved order using a recursion technique. Please note that it should not change the list,
     * just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedRecursively(Node<T> head) {
        var nextNode = head.next;
        if (nextNode != null) {
            printReversedRecursively(nextNode);
            System.out.print(" -> ");
        }

        System.out.print(head.element);
    }

    /**
     * Prints a list in a reserved order using a {@link java.util.Stack} instance. Please note that it should not change
     * the list, just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedUsingStack(Node<T> head) {
        var nodes = new Stack<T>();

        var currentNode = head;
        do {
            nodes.push(currentNode.element);
            currentNode = currentNode.next;
        } while (currentNode != null);

        while (!nodes.empty()) {
            var elemOnTop = nodes.pop();
            System.out.print(elemOnTop);
            if (!nodes.empty()) {
                System.out.print(" -> ");
            }
        }
    }

    private static class Node<T> {
        T element;
        Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }
}
