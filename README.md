## 什么是 JetCache

![](https://minio.pigx.vip/oss/1615125703.jpg)

JetCache 是一个基于 Java 的缓存系统封装，提供统一的 API 和注解来简化缓存的使用。 JetCache 提供了比 SpringCache 更加强大的注解，**可以原生的支持 TTL、两级缓存、分布式自动刷新，还提供了 Cache 接口用于手工缓存操作**。 当前有四个实现，RedisCache、TairCache（此部分未在 github 开源）、CaffeineCache(in memory)和一个简易的 LinkedHashMapCache(in memory)，要添加新的实现也是非常简单的。

## 快速使用

- maven 依赖

```xml
		<dependency>
			<groupId>com.alicp.jetcache</groupId>
			<artifactId>jetcache-starter-redis-lettuce</artifactId>
			<version>2.6.0</version>
		</dependency>
```

- 缓存配置

```yaml
jetcache:
  statIntervalMinutes: 1
  areaInCacheName: false
  local:
    default:
      type: linkedhashmap
      keyConvertor: fastjson
  remote:
    default:
      type: redis.lettuce
      keyConvertor: fastjson
      uri: redis://127.0.0.1:6379/  # 指定redis 配置地址
```

- Main 方法开启

```java
@EnableMethodCache(basePackages = "com.pig4cloud.jetcache")
@EnableCreateCacheAnnotation
@SpringBootApplication
public class JetcacheDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(JetcacheDemoApplication.class, args);
	}
}
```

- 业务使用

使用@Cached 注解可以为一个方法添加缓存，@CacheUpdate 用于更新缓存，@CacheInvalidate 用于移除缓存元。

```java
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
```

## 单元测试

```java
@SpringBootTest
class JetcacheDemoApplicationTests {

	@Autowired
	private DemoService demoService;

	@Test
	void findByUserNameTest() {
		demoService.findByUserName("a");
		demoService.findByUserName("a");
		demoService.findByUserName("a");
	}
}

```

- 由于 `findByUserName` 接口声明了缓存注解，则只会输出一次

```
2021-03-07 22:09:11.828  WARN 53501 --- [           main] c.p.jetcache.service.DemoService         : 支持DB查询逻辑 a
```

- 且由于缓存类型指定的是本地&远程的二级缓存，则只会走一次 redis 查询

## 缓存统计

- statIntervalMinutes 参数指定的统计缓存信息的时间周期，**当设置为 0 则说明不需要统计**。

```
jetcache:
  statIntervalMinutes: 1
```

- 自动输出如下统计信息

```
2021-03-07 22:13:00.032  INFO 53564 --- [DefaultExecutor] c.alicp.jetcache.support.StatInfoLogger  : jetcache stat from 2021-03-07 22:12:13,773 to 2021-03-07 22:13:00,024
cache|       qps|   rate|           get|           hit|          fail|        expire|avgLoadTime|maxLoadTime
-----+----------+-------+--------------+--------------+--------------+--------------+-----------+-----------
-----+----------+-------+--------------+--------------+--------------+--------------+-----------+-----------
```
