package com.pig4cloud.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMethodCache(basePackages = "com.pig4cloud.jetcache")
@EnableCreateCacheAnnotation
@SpringBootApplication
public class JetcacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JetcacheDemoApplication.class, args);
	}

}
