package com.kv.pool;

/**
 * Pool
 *  一个对象池需要完成哪些功能:
 *      1、如果有可用的对象，对象池应当能返回给客户端。
 *      2、客户端把对象放回池里后，可以对这些对象进行重用。
 *      3、对象池能够创建新的对象来满足客户端不断增长的需求。
 *      4、需要有一个正确关闭池的机制来确保关闭后不会发生内存泄露。
 * @author KVLT
 * @date 2017-08-14.
 */
public interface Pool<T> {

    /**
     * Returns an instance from the pool.
     * The call may be a blocking one or a non-blocking one
     * and that is determined by the internal implementation.
     * If the call is a blocking call,
     * the call returns immediately with a valid object
     * if available, else the thread is made to wait
     * until an object becomes available.
     */
    T get();

    /**
     * Releases the object and puts it back to the pool.
     * The mechanism of putting the object back to the pool is
     * generally asynchronous,
     * however future implementations might differ.
     * @param t the object to return to the pool
     */
    void release(T t);

    /**
     * Shuts down the pool. In essence this call will not
     * accept any more requests
     * and will release all resources.
     */
    void shutdown();

    /**
     *      “池”的接口和抽象类都是设计成支持通用对象的，因此具体的实现不知道该如何验证对象的有效性（对象是泛型的）。
     *  因此，这里需要借助工具类来完成对象有效性的校验。
     *      只有在对象池里使用的时候才有意义
     * @param <T>
     */
    public static interface Validator<T> {
        /**
         * Checks whether the object is valid.
         * @param t
         * @return
         */
        public boolean isValid(T t);

        /**
         * Performs any cleanup activities
         * before discarding the object.
         * For example before discarding
         * database connection objects,
         * the pool will want to close the connections.
         * This is done via the
         * <code>invalidate()</code> method.
         * @param t
         */
        public void invalidate(T t);
    }
}
