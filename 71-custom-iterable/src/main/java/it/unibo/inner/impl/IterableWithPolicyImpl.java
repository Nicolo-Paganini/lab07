package it.unibo.inner.impl;

import java.util.List;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    
    private final List<T> elements;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] elements){
        this(
            elements,
            new Predicate<T>() {
                @Override
                public boolean test(T elem){
                    return true;
                }
            } 
        );
    }

    public IterableWithPolicyImpl(final T[] elements, final Predicate<T> filter){
        this.elements = List.of(elements);
        this.filter = filter;
    }

    public Iterator<T> iterator(){
        return new BasicIterator();
    }

    public void setIterationPolicy(Predicate<T> filter){
        this.filter = filter;
    }

    private class BasicIterator implements Iterator<T>{
        private int currentIndex = 0;

        public boolean hasNext(){
            while(currentIndex < elements.size()){
                if(filter.test(elements.get(currentIndex))){
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        public T next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return elements.get(currentIndex++);
        }
    }
}
