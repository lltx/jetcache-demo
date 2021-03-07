package com.pig4cloud.jetcache.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.pig4cloud.jetcache.model.Demo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author lengleng
 * @date 2021/3/6
 */
@Slf4j
@Service
public class DemoService {
	@Cached(name = "DemoCache", key = "#username", expire = 3600, cacheType = CacheType.BOTH)
	public Demo findByUserName(String username) {
		log.warn("支持DB查询逻辑 {}", username);
		return new Demo(username, "123456");
	}

	@CacheInvalidate(name = "DemoCache", key = "#username")
	public void deleteByUserName(String username) {
	}
}
