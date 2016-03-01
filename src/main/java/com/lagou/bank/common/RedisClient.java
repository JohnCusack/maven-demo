/**
 * 
 */
package com.lagou.bank.common;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author Peker
 * @time 2015年12月17日下午6:26:06
 */
public class RedisClient {
	private static JedisPool pool;
	private static ShardedJedisPool shardedPool;
	private static Jedis jedis;
	private static ShardedJedis shardedJedis;
	// 初始化
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("jedis");
		if (bundle == null) {
			throw new IllegalArgumentException("[jedis.properties] is not found!");
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
		config.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
		config.setMaxWaitMillis(Integer.valueOf(bundle.getString("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));
		pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")));
		jedis = pool.getResource();
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo(bundle.getString("redis.ip"), Integer.valueOf(bundle
				.getString("redis.port")));
//		JedisShardInfo jedisShardInfo2 = new JedisShardInfo(bundle.getString("redis2.ip"), Integer.valueOf(bundle
//				.getString("redis2.port")));
		List<JedisShardInfo> linkedList = new LinkedList<JedisShardInfo>();
		linkedList.add(jedisShardInfo1);
//		linkedList.add(jedisShardInfo2);
		shardedPool = new ShardedJedisPool(config, linkedList);
		shardedJedis = shardedPool.getResource();
	}

	public static void main(String[] args) {
		show();
		// simpleJedis();
	}

	public static void shardedPoolTest() {

	}

	public static void poolTest() {

		String keys = "name";
		jedis.del(keys);
		jedis.set(keys, "peker");
		System.out.println(jedis.get(keys));
		pool.returnResource(jedis);
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
	}

	public static void simpleJedis() {
		// connect local Redis
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server successfully");
		System.out.println("Server is running: " + jedis.ping());

		// 设置 redis 字符串数据
		jedis.set("w3ckey", "Redis tutorial");
		// 获取存储的数据并输出
		System.out.println("Stored string in redis:: " + jedis.get("w3ckey"));

		// 存储数据到列表中
		jedis.lpush("tutorial-list", "Redis");
		jedis.lpush("tutorial-list", "Mongodb");
		jedis.lpush("tutorial-list", "Mysql");
		// 获取存储的数据并输出
		List<String> list = jedis.lrange("tutorial-list", 0, 5);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Stored string in redis:: " + list.get(i));
		}
	}

	public static void show() {
		// key检测
		testKey();
		// string检测
		testString();
		// list检测
		testList();
		// set检测
		testSet();
		// sortedSet检测
		testSortedSet();
		// hash检测
		testHash();
		shardedPool.returnResource(shardedJedis);
	}

	private static void testKey() {
		System.out.println("=============key==========================");
		// 清空数据
		System.out.println(jedis.flushDB());
		System.out.println(jedis.echo("foo"));
		// 判断key否存在
		System.out.println(shardedJedis.exists("foo"));
		shardedJedis.set("key", "values");
		System.out.println(shardedJedis.exists("key"));
	}

	private static void testString() {
		System.out.println("=============String==========================");
		// 清空数据
		System.out.println(jedis.flushDB());
		// 存储数据
		shardedJedis.set("foo", "bar");
		System.out.println(shardedJedis.get("foo"));
		// 若key不存在，则存储
		shardedJedis.setnx("foo", "foo not exits");
		System.out.println(shardedJedis.get("foo"));
		// 覆盖数据
		shardedJedis.set("foo", "foo update");
		System.out.println(shardedJedis.get("foo"));
		// 追加数据
		shardedJedis.append("foo", " hello, world");
		System.out.println(shardedJedis.get("foo"));
		// 设置key的有效期，并存储数据
		shardedJedis.setex("foo", 2, "foo not exits");
		System.out.println(shardedJedis.get("foo"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		System.out.println(shardedJedis.get("foo"));
		// 获取并更改数据
		shardedJedis.set("foo", "foo update");
		System.out.println(shardedJedis.getSet("foo", "foo modify"));
		// 截取value的值
		System.out.println(shardedJedis.getrange("foo", 1, 3));
		System.out.println(jedis.mset("mset1", "mvalue1", "mset2", "mvalue2", "mset3", "mvalue3", "mset4", "mvalue4"));
		System.out.println(jedis.mget("mset1", "mset2", "mset3", "mset4"));
		System.out.println(jedis.del(new String[] { "foo", "foo1", "foo3" }));
	}

	private static void testList() {
		System.out.println("=============list==========================");
		// 清空数据
		System.out.println(jedis.flushDB());
		// 添加数据
		shardedJedis.lpush("lists", "vector");
		shardedJedis.lpush("lists", "ArrayList");
		shardedJedis.lpush("lists", "LinkedList");
		// 数组长度
		System.out.println(shardedJedis.llen("lists"));
		// 排序
		// System.out.println(shardedJedis.sort("lists"));
		// 字串
		System.out.println(shardedJedis.lrange("lists", 0, 3));
		// 修改列表中单个值
		shardedJedis.lset("lists", 0, "hello list!");
		// 获取列表指定下标的值
		System.out.println(shardedJedis.lindex("lists", 1));
		// 删除列表指定下标的值
		System.out.println(shardedJedis.lrem("lists", 1, "vector"));
		// 删除区间以外的数据
		System.out.println(shardedJedis.ltrim("lists", 0, 1));
		// 列表出栈
		System.out.println(shardedJedis.lpop("lists"));
		// 整个列表值
		System.out.println(shardedJedis.lrange("lists", 0, -1));
	}

	private static void testSet() {
		System.out.println("=============set==========================");
		// 清空数据
		System.out.println(jedis.flushDB());
		// 添加数据
		shardedJedis.sadd("sets", "HashSet");
		shardedJedis.sadd("sets", "SortedSet");
		shardedJedis.sadd("sets", "TreeSet");
		// 判断value是否在列表中
		System.out.println(shardedJedis.sismember("sets", "TreeSet"));
		;
		// 整个列表值
		System.out.println(shardedJedis.smembers("sets"));
		// 删除指定元素
		System.out.println(shardedJedis.srem("sets", "SortedSet"));
		// 出栈
		System.out.println(shardedJedis.spop("sets"));
		System.out.println(shardedJedis.smembers("sets"));
		//
		shardedJedis.sadd("sets1", "HashSet1");
		shardedJedis.sadd("sets1", "SortedSet1");
		shardedJedis.sadd("sets1", "TreeSet");
		shardedJedis.sadd("sets2", "HashSet2");
		shardedJedis.sadd("sets2", "SortedSet1");
		shardedJedis.sadd("sets2", "TreeSet1");
		// 交集
		System.out.println(jedis.sinter("sets1", "sets2"));
		// 并集
		System.out.println(jedis.sunion("sets1", "sets2"));
		// 差集
		System.out.println(jedis.sdiff("sets1", "sets2"));
	}

	private static void testSortedSet() {
		System.out.println("=============zset==========================");
		// 清空数据
		System.out.println(jedis.flushDB());
		// 添加数据
		shardedJedis.zadd("zset", 10.1, "hello");
		shardedJedis.zadd("zset", 10.0, ":");
		shardedJedis.zadd("zset", 9.0, "zset");
		shardedJedis.zadd("zset", 11.0, "zset!");
		// 元素个数
		System.out.println(shardedJedis.zcard("zset"));
		// 元素下标
		System.out.println(shardedJedis.zscore("zset", "zset"));
		// 集合子集
		System.out.println(shardedJedis.zrange("zset", 0, -1));
		// 删除元素
		System.out.println(shardedJedis.zrem("zset", "zset!"));
		System.out.println(shardedJedis.zcount("zset", 9.5, 10.5));
		// 整个集合值
		System.out.println(shardedJedis.zrange("zset", 0, -1));
	}

	private static void testHash() {
		System.out.println("=============hash==========================");
		// 清空数据
		System.out.println(jedis.flushDB());
		// 添加数据
		shardedJedis.hset("hashs", "entryKey", "entryValue");
		shardedJedis.hset("hashs", "entryKey1", "entryValue1");
		shardedJedis.hset("hashs", "entryKey2", "entryValue2");
		// 判断某个值是否存在
		System.out.println(shardedJedis.hexists("hashs", "entryKey"));
		// 获取指定的值
		System.out.println(shardedJedis.hget("hashs", "entryKey"));
		// 批量获取指定的值
		System.out.println(shardedJedis.hmget("hashs", "entryKey", "entryKey1"));
		// 删除指定的值
		System.out.println(shardedJedis.hdel("hashs", "entryKey"));
		// 为key中的域 field 的值加上增量 increment
		System.out.println(shardedJedis.hincrBy("hashs", "entryKey", 123l));
		// 获取所有的keys
		System.out.println(shardedJedis.hkeys("hashs"));
		// 获取所有的values
		System.out.println(shardedJedis.hvals("hashs"));
	}

}
