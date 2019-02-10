package sample;

import javafx.collections.ModifiableObservableListBase;

import java.util.Iterator;

public class LinkedQueue<E> extends ModifiableObservableListBase<E> implements Iterable<E>, SimpleQueue<E> {

    private LinkedQueueNode<E> front;

    private LinkedQueueNode<E> rear;

    private int size = 0;


    @Override
    public E get(int index) {

        LinkedQueueNode<E> tmp = front;
        int count = 0;
        boolean found = false;
        while (tmp != null){
            if(count++ == index){
                found = true;
                break;
            }
            tmp = tmp.getNext();
        }
        if(!found){
            throw new IndexOutOfBoundsException();
        }
        return tmp.getData();
    }

    @Override
    public void enqueue(E element) {
        LinkedQueueNode<E> node = new LinkedQueueNode<E>(element, null);
        if(rear == null){
            rear = node;
            front = node;
        }else {
            rear.setNext(node);
            rear = node;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if(front != null){
            E data = front.getData();
            front = front.getNext();
            size--;
            return data;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void doAdd(int index, E element) {
        LinkedQueueNode<E> tmp = front;
        int count = 0;
        boolean found = false;
        LinkedQueueNode<E> prev = null;
        while (tmp != null){
            if(count++ == index){
                found = true;
                break;
            }
            prev = tmp;
            tmp = tmp.getNext();
        }
        if(index == 0 && !isEmpty()){
            found = true;
        }

        if(index == size){
            found = true;
        }
        if(!found){
            throw new IndexOutOfBoundsException();
        }
        LinkedQueueNode<E> node = new LinkedQueueNode<E>(element, null);
        if(prev == null){
            node.setNext(front);
            prev = node;
            front = prev;
        }else {
            node.setNext(tmp);
            prev.setNext(node);
            if(index == size-1){
                rear = node;
            }
        }
        size++;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size() && get(currentIndex) != null;
            }

            @Override
            public E next() {
                return get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    @Override
    protected E doSet(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected E doRemove(int index) {
        LinkedQueueNode<E> tmp = front;
        int count = 0;
        boolean found = false;
        LinkedQueueNode<E> prev = null;
        while (tmp != null){
            if(count++ == index){
                found = true;
                break;
            }
            prev = tmp;
            tmp = tmp.getNext();
        }

        if(index == 0 && !isEmpty()){
            found = true;
        }
        if(!found){
            throw new IndexOutOfBoundsException();
        }

        if(tmp != null) {
            E data = tmp.getData();
            if (index == size - 1) {
                prev.setNext(null);
                rear = prev;
            } else{
                if(index == 0){
                    front = front.getNext();
                }else {
                    prev.setNext(tmp.getNext());
                }
            }
            size--;
            return data;
        }else {
            throw new IndexOutOfBoundsException();
        }
    }
}
