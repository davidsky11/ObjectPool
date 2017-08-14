package com.kv.pool;

/**
 * AbstractPool
 *
 * @author KVLT
 * @date 2017-08-14.
 */
public abstract class AbstractPool<T> implements Pool<T> {

    /**
     * 一个理想的release方法应该先尝试检查下这个客户端返回的对象是否还能重复使用。
     *  如果是的话再把它扔回池里，如果不是，就舍弃掉这个对象。
     * @param t the object to return to the pool
     */
    public final void release(T t) {
        if (isValid(t)) {
            returnToPool(t);
        } else {
            handleInvalidReturn(t);
        }
    }

    /**
     * 判断对象是否有效
     * @param t
     * @return
     */
    protected abstract boolean isValid(T t);
    protected abstract void handleInvalidReturn(T t);
    protected abstract void returnToPool(T t);
}
