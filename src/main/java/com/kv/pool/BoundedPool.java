package com.kv.pool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * BoundedPool - 非阻塞的对象池
 * 即使对象不可用，它也不会让客户端阻塞，而是直接返回nul。
 *
 * @author KVLT
 * @date 2017-08-14.
 */
public class BoundedPool<T> extends AbstractPool<T> {

    private int size;
    private Queue<T> objects;
    private Validator<T> validator;
    private ObjectFactory<T> objectFactory;
    private Semaphore permits;
    private volatile boolean shutdownCalled;

    public BoundedPool(int size, Validator<T> validator, ObjectFactory<T> objectFactory) {
        super();
        this.objectFactory = objectFactory;
        this.size = size;
        this.validator = validator;
        objects = new LinkedList<T>();
        initializeObjects();
        shutdownCalled = false;
    }

    public T get() {
        T t = null;

        if (!shutdownCalled) {
            if (permits.tryAcquire()) {
                t = objects.poll();
            }
        } else {
            throw new IllegalStateException("Object pool already shutdown");
        }
        return t;
    }

    public void shutdown() {
        shutdownCalled = true;
        clearResources();
    }

    private void clearResources() {
        for (T t : objects) {
            validator.invalidate(t);
        }
    }

    @Override
    protected void returnToPool(T t) {
        boolean added = objects.add(t);
        if (added) {
            permits.release();
        }
    }

    @Override
    protected void handleInvalidReturn(T t) {
    }

    @Override
    protected boolean isValid(T t) {
        return validator.isValid(t);
    }

    private void initializeObjects() {
        for (int i = 0; i < size; i++) {
            objects.add(objectFactory.createNew());
        }
    }

}
