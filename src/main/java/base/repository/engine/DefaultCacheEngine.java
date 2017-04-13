package base.repository.engine;

import java.io.Serializable;
import java.util.*;

/**
 * 默认缓存实现类
 * 
 * Time: 12-6-26 下午3:08
 */
public class DefaultCacheEngine implements CacheEngine {
    private Map<Serializable, Object> cache = new Hashtable<Serializable, Object>(); //域 cache

    /**
     * @param key   the key
     * @param value the value
     */
    public void add(Serializable key, Object value) {
        this.cache.put(key, value);
    }

    /**
     * @param fqn   the fqn
     * @param key   the key
     * @param value the value
     */
    @SuppressWarnings("unchecked")
	public void add(String fqn, Serializable key, Object value) {
        Map<Serializable, Object> m = (Map<Serializable, Object>) this.cache.get(fqn);
        if (m == null) {
            m = new HashMap<Serializable, Object>();
        }
        m.put(key, value);
        this.cache.put(fqn, m);
    }

    public void add(String fqn, Serializable key, Object value, long expireTime) {
        this.add(fqn,key,value);
    }

    /**
     * @param fqn the fqn
     * @param key the key
     * @return
     */
    public Object get(String fqn, Serializable key) {
        Map<?, ?> m = (Map<?, ?>) this.cache.get(fqn);
        if (m == null) {
            return null;
        }
        return m.get(key);
    }

    /**
     * @param fqn the fqn
     * @return
     */
    public Object get(String fqn) {
        return this.cache.get(fqn);
    }

    /**
     * @param fqn the fqn
     * @return
     */
    @SuppressWarnings("rawtypes")
	public Collection<?> getValues(String fqn) {
        Map<?, ?> m = (Map<?, ?>) this.cache.get(fqn);
        if (m == null) {
            return new ArrayList();
        }
        return m.values();
    }

    /**
     *
     */
    public synchronized void init() {
        this.cache = new HashMap<Serializable, Object>();
    }

    /**
     *
     */
    public synchronized void stop() {
    }

    /**
     * @param fqn the fqn
     * @param key the key
     */
    public void remove(String fqn, Serializable key) {
        Map<?, ?> m = (Map<?, ?>) this.cache.get(fqn);
        if (m != null) {
            m.remove(key);
        }
    }

    /**
     * @param fqn the fqn
     */
    public void remove(String fqn) {
        this.cache.remove(fqn);
    }

    /**
     * @param fqn
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<?, ?> getMap(String fqn) {
        Map m = (Map) this.cache.get(fqn);
        return Collections.unmodifiableMap(m);
    }

    /**
     * @return
     */
    public String getClientName() {
        return null;
    }

    /**
     * @param clientName
     */
    public void setClientName(String clientName) {
    }
}
