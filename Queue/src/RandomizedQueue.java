import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int itemsSize;
    private Item type;

    // construct an empty randomized queue
    public RandomizedQueue() {
        itemsSize = 0;
        Item[] items = (Item[]) new Object[1];
    }

    private void resizeArray(int currentSize, int newSize) {
        Item[] tmpArray = (Item[]) new Object[newSize];
        for (int i = 0; i < currentSize; i++) {
            tmpArray[i] = items[i];
        }
        items = tmpArray;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return itemsSize == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return itemsSize;
    }

    // add the item
    public void enqueue(Item item) {
        int queueSize = items.length;
        // Expand array if needed
        if (queueSize < itemsSize + 1) {
            resizeArray(queueSize, queueSize * 2);
        }
        items[itemsSize] = item;
        itemsSize += 1;
    }

    // remove and return a random item
    public Item dequeue() {
        int queueSize = items.length;
        itemsSize -= 1;
        if (queueSize > itemsSize / 4) {
            resizeArray(queueSize, queueSize / 2);
        }
        // Randomizing queue items
        StdRandom.shuffle(items);
        Item tempItem = items[itemsSize];
        items[itemsSize] = null;
        return tempItem;

    }

    private class ListIterator implements Iterator<Item> {
        private int current_index = 0;

        public boolean hasNext() {
            return items[current_index + 1] != null;
        }

        public Item next() {
            Item resultItem = items[current_index];
            current_index += 1;
            return resultItem;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        StdRandom.shuffle(items);
        return items[itemsSize];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}