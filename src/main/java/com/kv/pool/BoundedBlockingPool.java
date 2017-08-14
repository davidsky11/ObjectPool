package com.kv.pool;

import java.util.concurrent.*;

/**
 * BoundedBlockingPool
 *  基于LinkedBlockingQueue实现
 * @author KVLT
 * @date 2017-08-14.
 */
public final class BoundedBlockingPool<T> extends AbstractPool<T> implements BlockingPool<T> {

    private int size;
    private BlockingQueue<T> objects;
    private Validator validator;
    private ObjectFactory objectFactory;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private volatile boolean shutdownCalled;

    public BoundedBlockingPool (int size, Validator validator, ObjectFactory objectFactory) {
        super();
        this.objectFactory = objectFactory;
        this.size = size;
        this.validator = validator;
        objects = new LinkedBlockingQueue(size);
        initializeObjects();
        shutdownCalled = false;
    }

    public T get(long timeOut, TimeUnit unit) {
        if (!shutdownCalled) {
            T t = null;
            try {
                t = objects.poll(timeOut, unit);
                return t;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return t;
        }
        throw new IllegalStateException("Object pool is already shutdown");
    }

    public T get() {
        if (!shutdownCalled) {
            T t = null;
            try {
                t = objects.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return t;
        }
        throw new IllegalStateException("Object pool is already shutdown");
    }

    public void shutdown() {
        shutdownCalled = true;
        executor.shutdown();
        clearResources();
    }

    private void clearResources() {
        for (T t : objects) {
            validator.invalidate(t);
        }
    }

    protected void returnToPool(T t) {
        if (validator.isValid(t)) {
            executor.submit(new ObjectReturner(objects, t));
        }
    }

    protected void handleInvalidReturn(T t) {

    }

    protected boolean isValid(T t) {
        return validator.isValid(t);
    }

    private void initializeObjects() {
        for (int i=0; i<size; i++) {
            objects.add((T)objectFactory.createNew());
        }
    }

    private class ObjectReturner<E> implements Callable {
        private BlockingQueue queue;
        private E e;

        public ObjectReturner(BlockingQueue queue, E e) {
            this.queue = queue;
            this.e = e;
        }

        public Void call() {
            while (true) {
                try {
                    queue.put(e);
                    break;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return null;
        }
    }
}
