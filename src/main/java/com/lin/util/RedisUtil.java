package com.lin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    // =============================common============================
    /**
     * @title expire
     * @description 指定缓存失效时间
     * @author xiaolin
     * @param: key
     * @param: time
     * @updateTime 2020/6/16 20:48
     * @return: boolean
     */
    public boolean expire(String key, long time) {
        boolean result;
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * @description 根据key 获取过期时间
     * @author xiaolin
     * @param: key 不能为null
     * @updateTime 2020/6/16 20:49
     * @return: 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * @title hasKey
     * @description 根据key 获取过期时间
     * @author xiaolin
     * @updateTime 2020/6/16 20:50
     * @param: key
     * @return: 根据key 获取过期时间
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title del
     * @description  删除缓存
     * @param key   可以传一个值 或多个
     * @return void
     * @author xiaolin
     * @updateTime 2020/6/16 21:41
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    // ============================String=============================
    /**
     * @title get
     * @description  普通缓存获取
     * @param key
     * @return java.lang.Object
     * @author xiaolin
     * @updateTime 2020/6/16 21:36
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * @title set
     * @description
     * @param key
     * @param value
     * @return boolean true成功 false失败
     * @author xiaolin
     * @updateTime 2020/6/16 22:35
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title set
     * @description
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:35
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title incr
     * @description
     * @param key     键
     * @param delta   要增加几(大于0)
     * @return long
     * @author xiaolin
     * @updateTime 2020/6/16 22:36
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * @title decr
     * @description
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return long
     * @author xiaolin
     * @updateTime 2020/6/16 22:37
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================


    /**
     * @title hget
     * @description   hash的注入
     * @param key    键 不能为null
     * @param item   项 不能为null
     * @return java.lang.Object
     * @author xiaolin
     * @updateTime 2020/6/16 22:37
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * @title hmget
     * @description  获取hashKey对应的所有键值
     * @param key
     * @return  对应的多个键值
     * @author xiaolin
     * @updateTime 2020/6/16 22:38
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * @title hmset
     * @description
     * @param key   键
     * @param map   对应多个键值
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:38
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title hmset
     * @description  HashSet 并设置时间
     * @param key   对应多个键值
     * @param map   时间(秒)
     * @param time
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:39
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title hset
     * @description  向一张hash表中放入数据,如果不存在将创建
     * @param key        键
     * @param item       项
     * @param value      值
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:40
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title hset
     * @description  向一张hash表中放入数据,如果不存在将创建
     * @param key       键
     * @param item      项
     * @param value     值
     * @param time      时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:40
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title hdel
     * @description   删除hash表中的值
     * @param key   键 不能为null
     * @param item  项 可以使多个 不能为null
     * @return void
     * @author xiaolin
     * @updateTime 2020/6/16 22:41
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * @title hHasKey
     * @description  判断hash表中是否有该项的值
     * @param key   键 不能为null
     * @param item  项 不能为null
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:42
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * @title hincr
     * @description  hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key   键
     * @param item  项
     * @param by    要增加几(大于0)
     * @return double
     * @author xiaolin
     * @updateTime 2020/6/16 22:42
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * @title hdecr
     * @description  hash递减
     * @param key   键
     * @param item  项
     * @param by    要减少记(小于0)
     * @return double
     * @author xiaolin
     * @updateTime 2020/6/16 22:43
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================


    /**
     * @title sGet
     * @description  根据key获取Set中的所有值
     * @param key
     * @return Set
     * @author xiaolin
     * @updateTime 2020/6/16 22:43
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @title sHasKey
     * @description  根据value从一个set中查询,是否存在
     * @param key
     * @param value
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:44
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title sSet
     * @description     将数据放入set缓存
     * @param key       键
     * @param values    值 可以是多个
     * @return 成功个数
     * @author xiaolin
     * @updateTime 2020/6/16 22:44
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @title sSetAndTime
     * @description     将set数据放入缓存
     * @param key       键
     * @param time      时间(秒)
     * @param values    值 可以是多个
     * @return long     成功个数
     * @author xiaolin
     * @updateTime 2020/6/16 22:44
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @title sGetSetSize
     * @description  获取set缓存的长度
     * @param key
     * @return long
     * @author xiaolin
     * @updateTime 2020/6/16 22:45
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @title setRemove
     * @description     移除值为value的
     * @param key       键
     * @param values    值 可以是多个
     * @return long     移除的个数
     * @author xiaolin
     * @updateTime 2020/6/16 22:45
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================list=================================

    /**
     * @title lGet
     * @description  获取list缓存的内容
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return java.util.List<java.lang.Object>
     * @author xiaolin
     * @updateTime 2020/6/16 22:50
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @title lGetListSize
     * @description  取list缓存的长度
     * @param key
     * @return long
     * @author xiaolin
     * @updateTime 2020/6/16 22:50
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @title lGetIndex
     * @description 通过索引 获取list中的值
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return java.lang.Object
     * @author xiaolin
     * @updateTime 2020/6/16 22:50
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @title lSet
     * @description
     * @param key   键
     * @param value 值
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:49
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title lSet
     * @description
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:48
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @title lSet
     * @description  将list放入缓存
     * @param key   键
     * @param value 值
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:48
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @title lSet
     * @description 将list放入缓存,设置时间
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:47
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title lUpdateIndex
     * @description     根据索引修改list中的某条数据
     * @param key       键
     * @param index     索引
     * @param value     值
     * @return boolean
     * @author xiaolin
     * @updateTime 2020/6/16 22:47
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @title lRemove
     * @description     移除N个值为value
     * @param key       键
     * @param count     移除多少个
     * @param value     值
     * @return long     移除的个数
     * @author xiaolin
     * @updateTime 2020/6/16 22:46
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

}