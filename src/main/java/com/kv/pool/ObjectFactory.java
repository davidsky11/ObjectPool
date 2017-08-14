package com.kv.pool;

/**
 * ObjectFactory
 *
 * @author KVLT
 * @date 2017-08-14.
 */
public interface ObjectFactory<T> {

    /**
     * Returns a new instance of an object of type T.
     * @return
     */
    public abstract T createNew();
}
