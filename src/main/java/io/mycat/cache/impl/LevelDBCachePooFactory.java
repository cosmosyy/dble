package io.mycat.cache.impl;


import java.io.File;
import java.io.IOException;

import static org.iq80.leveldb.impl.Iq80DBFactory.factory;

import io.mycat.cache.CacheService;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import io.mycat.cache.CachePool;
import io.mycat.cache.CachePoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelDBCachePooFactory extends CachePoolFactory {
	private static final Logger logger = LoggerFactory.getLogger(LevelDBCachePooFactory.class);
	@Override
	public CachePool createCachePool(String poolName, int cacheSize,
			int expireSeconds) {
		Options options = new Options();
		options.cacheSize(1048576L * cacheSize);//cacheSize M 大小
		options.createIfMissing(true);
		DB db = null;
		String filePath = "leveldb\\" + poolName;
		try {
			db = factory.open(new File(filePath), options);
			// Use the db in here....
		} catch (IOException e) {
			logger.info("factory try to open file " + filePath + " failed ");
			// Make sure you close the db to shutdown the
			// database and avoid resource leaks.
			// db.close();
		}
		return new LevelDBPool(poolName, db, cacheSize);
	}

}
