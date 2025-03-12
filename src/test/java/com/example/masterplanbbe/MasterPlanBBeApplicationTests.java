package com.example.masterplanbbe;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@ActiveProfiles("test")
class MasterPlanBBeApplicationTests {

//	@TestConfiguration
//	public static class TestRedisConfig {
//
//		@Bean
//		public RedisConnectionFactory redisConnectionFactory() {
//			return Mockito.mock(RedisConnectionFactory.class);
//		}
//	}

	@Test
	void contextLoads() {
	}

}
