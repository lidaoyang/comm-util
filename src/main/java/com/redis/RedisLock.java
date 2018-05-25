package com.redis;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisLock {

	private static Logger logger = LoggerFactory.getLogger(RedisLock.class);
	private static String LOCK_KEY = "lock_";
	private static int EXPIRED_TIME = 180;

	/**
	 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
	 * 
	 * @param subKey
	 * @param timeout 如果timeout=0,取不到锁时,不等待,直接返回,单位毫秒
	 * @param retryWaitTime 重试等待时间，单位毫秒
	 * @return true:加锁成功，false，加锁失败
	 */
	public static boolean lock(String subKey, long timeout, long retryWaitTime) {
		TimeUnit unit = TimeUnit.MILLISECONDS;
		String key = LOCK_KEY + subKey;
		Jedis jedis = null;
		try {
			jedis = RedisClient.getJedis();
			if (jedis == null) {
				return false;
			}
			long nano = System.nanoTime();
			do {
				logger.debug("try lock key: " + key);
				Long i = jedis.setnx(key, "1");
				if (i == 1) {
					jedis.expire(key, EXPIRED_TIME);
					logger.debug("get lock, key: " + key + " , expire in " + EXPIRED_TIME + " seconds.");
					return true;
				} else { // 存在锁
					if (logger.isDebugEnabled()) {
						String desc = jedis.get(key);
						logger.debug("key: " + key + " locked by another business：" + desc);
					}
				}
				if (timeout == 0) { // 取不到锁时,不等待,直接返回.
					break;
				}
				Thread.sleep(retryWaitTime);
			} while ((System.nanoTime() - nano) < unit.toNanos(timeout));// 取不到锁时等待,直到timeout
			return false;
		} catch (JedisConnectionException je) {
			logger.error(je.getMessage(), je);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			RedisClient.returnRes(jedis);
		}
		return false;
	}

	/**
	 * @param subKey
	 * @param retryCount 锁等待重试次数，-1不限制
	 * @param retryWaitTime 重试等待时间，单位毫秒
	 * @return true:加锁成功，false，加锁失败
	 */
	public static boolean lock(int retryCount, long retryWaitTime,String subKey) {
		String key = LOCK_KEY + subKey;
		Jedis jedis = null;
		boolean isLock = false;
		try {
			jedis = RedisClient.getJedis();
			if (jedis == null) {
				return isLock;
			}
			int count = 0;
			while (retryCount == -1 || count <= retryCount) {
				logger.debug("lock key: " + key);
				Long i = jedis.setnx(key, "1");
				if (i == 1) {
					jedis.expire(key, EXPIRED_TIME);
					logger.debug("get lock, key: " + key + " , expire in " + EXPIRED_TIME + " seconds.");
					isLock = true;
				} else {
					if (logger.isDebugEnabled()) {
						String desc = jedis.get(key);
						logger.debug("key: " + key + " locked by another business：" + desc);
					}
					isLock = false;
				}
				Thread.sleep(retryWaitTime);
				count++;
			}
		} catch (JedisConnectionException je) {
			logger.error(je.getMessage(), je);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			RedisClient.returnRes(jedis);
		}
		return isLock;
	}

	/**
	 * 释放锁
	 * 
	 * @param subKey
	 */
	public static void unLock(String subKey) {
		String key = LOCK_KEY + subKey;
		RedisClient.del_value(key);
	}

	public static void main(String[] args) throws Exception {
		long nano = System.nanoTime();
		System.out.println(nano);
		Thread.sleep(120);
		System.out.println((System.nanoTime()-nano)+"\r"+TimeUnit.MILLISECONDS.toNanos(2000));

	}

}
