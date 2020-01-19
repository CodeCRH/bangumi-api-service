package com.tokyo3rd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create by CodeCRH
 * Redis缓存读取、写入工具类
 */
public class RedisCacheUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisCacheUtil.class);

    private static JedisPool jedisPool;

    private static RedisCacheUtil redisCacheUtil;

    private static String redisHost;

    private static Integer redisPort;


    //不允许直接构造连接池，防止错误初始化
    private RedisCacheUtil() {
    }

    private RedisCacheUtil(String host, Integer port, String password) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(1000);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPool = new JedisPool(jedisPoolConfig, host, port, 10000, password, 0);
        jedisPool = new JedisPool(jedisPoolConfig, host, port, 10000, password, 0);
    }

    //使用单例模式初始化,避免使用构造函数时每次生成都要生成一个连接池，太Low
    public static RedisCacheUtil getRedisCacheUtil(String host, Integer port, String password) {
        try {
            //防止由于某些配置或错误，以空串为密码传输导致错误，redis是不接受空串为密码的，空格可以
            if ("".equals(password)) {
                password = null;
            }

            if (redisCacheUtil == null) {
                redisCacheUtil = new RedisCacheUtil(host, port, password);
                redisHost = host;
                redisPort = port;
            } else if (redisHost != null && redisHost.equals(host) && redisPort != null && redisPort.equals(port)) {
                return redisCacheUtil;
            } else {
                redisCacheUtil = new RedisCacheUtil(host, port, password);
                redisHost = host;
                redisPort = port;
            }
        } catch (Exception e) {
            logger.error("获取redis连接发生异常 " + e.getMessage());
            e.printStackTrace();
        }
        return redisCacheUtil;
    }

    //订阅
    public boolean publish(String channel, String value) {
        boolean base = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            Long result = jedis.publish(channel, value);   //从 mychannel 的频道上推送消息
            logger.info("订阅数量 " + result);
            base = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close(); // 释放资源还给连接池
            }
        }
        return base;
    }

    //操作set
    public boolean sadd(String key, String value) {
        boolean base = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.sadd(key, value);
            base = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close(); // 释放资源还给连接池
            }
        }
        return base;
    }


    public boolean append(String key, String value) {
        boolean base = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.append(key, value);
            base = true;
        } catch (Exception e) {
            logger.error("Redis缓存append异常 :" + e.getMessage() + " Key = " + key + " value = " + value);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    public boolean set(String key, String value) {
        boolean base = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            base = true;
        } catch (Exception e) {
            logger.error("Redis缓存set异常 :" + e.getMessage() + " Key = " + key + " value = " + value);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    public boolean hmset(String key, HashMap<String, String> map) {
        boolean base = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hmset(key, map);
            base = true;
        } catch (Exception e) {
            logger.error("Redis缓存hmset异常 :" + e.getMessage() + " Key = " + key);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    public String get(String key) {
        String base = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            base = jedis.get(key);
        } catch (Exception e) {
            logger.error("Redis缓存get异常 :" + e.getMessage() + " Key = " + key);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    public String hget(String key, String field) {
        String base = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            base = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("Redis缓存hget异常 :" + e.getMessage() + " Key = " + key);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    public Map<String, String> hgetAll(String key) {
        Map<String, String> base = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            base = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("Redis缓存hgetAll异常 :" + e.getMessage() + " Key = " + key);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    public Object eval(String lua, List<String> keys, List<String> values) {
        Object base = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            base = jedis.eval(lua, keys, values);
        } catch (Exception e) {
            logger.error("Redis缓存eval异常 :" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    //自增操作
    public Long incr(String key) {
        Long base = -1L;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            base = jedis.incr(key);
        } catch (Exception e) {
            logger.error("Redis缓存incr异常 :" + e.getMessage() + " Key = " + key);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    //设置过期时间
    public Long expire(String key, int seconds) {
        Long base = -1L;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            base = jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error("Redis缓存incr异常 :" + e.getMessage() + " Key = " + key);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    //判断key是否存在
    public boolean exists(String key) {
        boolean base = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            base = jedis.exists(key);
        } catch (Exception e) {
            logger.error("Redis缓存exists异常 :" + e.getMessage() + " Key = " + key);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    //模糊匹配redis key
    public Set<String> keys(String pattern) {
        Set<String> base = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            base = jedis.keys(pattern);
        } catch (Exception e) {
            logger.error("Redis缓存获取key列表异常 :" + e.getMessage() + " pattern = " + pattern);
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return base;
    }

    //操作set
    public boolean rename(String oldKey, String newKey) {
        boolean base = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rename(oldKey, newKey);
            base = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close(); // 释放资源还给连接池
            }
        }
        return base;
    }

    //删除map中某个field值
    public boolean hdel(String key, String field) {
        boolean base = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hdel(key, field);
            base = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close(); // 释放资源还给连接池
            }
        }
        return base;
    }


    //删除某个key值
    public boolean del(String key) {
        boolean base = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            base = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close(); // 释放资源还给连接池
            }
        }
        return base;
    }

}
