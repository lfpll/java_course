import java.util.Iterator;

public class Deque<Item>  {
    private class Node
    {
        // Generic type name meaning I can put anything in the queue
        Item item;
        Node next;
        Node previous;
    }
    private Node last;
    private Node first;
    private int size;
    // construct an empty deque
    public Deque()
    {
        last = null;
        first = null;
        size = 0;
    }

    private void errorIfNull(Item item){
        if(item == null){
         throw new IllegalArgumentException("Item is null");
        }
    }
    private void errorIfEmpty(){
        if (isEmpty()){
            throw new java.util.NoSuchElementException();
        }
    }
    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        size += 1;
        errorIfNull(item);
        if (first == null){
            first = new Node();
            last = first;
            first.item = item;
        }
        else{
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
        }

    }

    // add the item to the back
    public void addLast(Item item){
        size += 1;
        errorIfNull(item);
        if (last == null){
            last = new Node();
            first = last;
            last.item = item;
        }
        else{
            Node oldLast = last;
            last = new Node();
            last.item = item;
            oldLast.next = last;
            last.previous = oldLast;
        }
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        errorIfEmpty();
        Item returnItem;
        size -= 1;
        if(last == first){
            returnItem = first.item;
            first = null;
            last = null;
        }
        else{
            returnItem = first.item;
            first = first.next;
            first.previous = null;
        }
        return returnItem;
    }

    // remove and return the item from the back
    public Item removeLast(){
        Item returnItem;
        errorIfEmpty();
        size -= 1;
        if(last == first){
            returnItem = last.item;
            first = null;
            last = null;
        }
        else{
            returnItem = last.item;
            last = last.previous;
            last.next = null;

        }
        return returnItem;
    }

    // return an iterator over items in order from front to back
//    public Iterator<Item> iterator(){}

    // unit testing (required)
    public static void main(String[] args){
        Deque tmpDeque = new Deque();
        tmpDeque.addFirst(1);
        tmpDeque.addLast(2);
        tmpDeque.addFirst(3);
        tmpDeque.removeFirst();
        tmpDeque.removeFirst();
        tmpDeque.removeLast();

    }

}
