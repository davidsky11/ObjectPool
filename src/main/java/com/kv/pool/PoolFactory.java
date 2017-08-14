package com.kv.pool;

/**
 * PoolFactory
 *
 * @author KVLT
 * @date 2017-08-14.
 */
public final class PoolFactory {

    private PoolFactory(){}

    public static <T> Pool<T> newBoundedBlockingPool(int size, ObjectFactory factory, Pool.Validator validator) {
        return new BoundedBlockingPool<T>(size, validator, factory);
    }

    public static < T > Pool < T > newBoundedNonBlockingPool(int size, com.kv.pool.ObjectFactory< T > factory, Pool.Validator< T > validator)
    {
        return new com.kv.pool.BoundedPool< T >(size, validator, factory);
    }

}
