package com.pig4cloud.jetcache;

import com.pig4cloud.jetcache.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

	@Test
	void deleteByUserNameTest() {
		demoService.findByUserName("a");
		demoService.deleteByUserName("a");
		demoService.findByUserName("a");
	}

}
