package com.kv.pool;

import java.util.concurrent.TimeUnit;

/**
 * BlockingPool - 阻塞的对象池
 *  如果在超时时间到来前有对象可用则返回，如果超时了就返回null而不是一直等待下去。类似Java并发库里的LinkedBlockingQueue。
 * @author KVLT
 * @date 2017-08-14.
 */
public interface BlockingPool<T> extends Pool<T> {

    T get();

    T get(long time, TimeUnit unit) throws InterruptedException;
}
